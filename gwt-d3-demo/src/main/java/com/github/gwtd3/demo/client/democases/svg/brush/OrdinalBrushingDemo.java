/**
 * Copyright (c) 2013, Anthony Schiochet and Eric Citaire
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * The names Anthony Schiochet and Eric Citaire may not be used to endorse or promote products
 *   derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL MICHAEL BOSTOCK BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY
 * OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.github.gwtd3.demo.client.democases.svg.brush;

import java.util.Arrays;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.scales.OrdinalScale;
import com.github.gwtd3.api.svg.Axis.Orientation;
import com.github.gwtd3.api.svg.Brush;
import com.github.gwtd3.api.svg.Brush.BrushEvent;
import com.github.gwtd3.api.svg.Symbol;
import com.github.gwtd3.api.svg.Symbol.Type;
import com.github.gwtd3.demo.client.DemoCase;
import com.github.gwtd3.demo.client.Factory;
import com.github.gwtd3.demo.client.democases.Margin;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.FlowPanel;

public class OrdinalBrushingDemo extends FlowPanel implements DemoCase {

    public interface Bundle extends ClientBundle {
        public static final Bundle INSTANCE = GWT.create(Bundle.class);

        @Source("OrdinalBrushingDemo.css")
        public MyResources css();
    }

    interface MyResources extends CssResource {
        String ob();

        String selecting();

        String selected();

        String axis();

        String brush();
    }

    private MyResources css;
    private Selection svg;
    private Selection symbol;
    private OrdinalScale x;

    public OrdinalBrushingDemo() {
        super();
        this.css = Bundle.INSTANCE.css();
        this.css.ensureInjected();
        this.addStyleName(css.ob());
    }

    /**
     * @return
     */
    public static Factory factory() {
        return new Factory() {
            @Override
            public DemoCase newInstance() {
                return new OrdinalBrushingDemo();
            }
        };
    }

    @Override
    public void start() {
        Type[] data = Symbol.Type.values();

        Margin margin = new Margin(210, 10, 210, 10);
        int width = 960 - margin.right - margin.left;
        final int height = 500 - margin.top - margin.bottom;

        String[] array = Lists.transform(Arrays.asList(data), new Function<Type, String>() {
            @Override
            public String apply(final Type input) {
                return input.getValue();
            }
        }).toArray(new String[data.length]);
        x = D3.scale.ordinal()
                .domain(array)
                .rangePoints(0, width, 1);

        svg = D3.select(this)
                .append("svg")
                .attr("width", width + margin.right + margin.left)
                .attr("height", height + margin.top + margin.bottom)
                .append("g")
                .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

        svg.append("g")
                .attr("class", "x " + css.axis())
                .attr("transform", "translate(0," + height + ")")
                .call(D3.svg().axis().scale(x).orient(Orientation.BOTTOM));

        symbol = svg.append("g").selectAll("path")
                .data(data)
                .enter().append("path")
                .attr("transform", new DatumFunction<String>() {
                    @Override
                    public String apply(final Element context, final Value d, final int index) {
                        String value = d.<Type> as().getValue();
                        return "translate(" + x.apply(value).asDouble() + "," + (height / 2) + ")";
                    }
                })
                .attr("d", D3.svg().symbol().type(new DatumFunction<Type>() {
                    @Override
                    public Type apply(final Element context, final Value d, final int index) {
                        return d.<Type> as();
                    }
                }).size(200));

        svg.append("g")
                .attr("class", css.brush())
                .call(D3.svg().brush().x(x)
                        .on(BrushEvent.BRUSH_START, new DatumFunction<Void>() {
                            @Override
                            public Void apply(final Element context, final Value d, final int index) {
                                brushstart();
                                return null;
                            }
                        })
                        .on(BrushEvent.BRUSH, new DatumFunction<Void>() {
                            @Override
                            public Void apply(final Element context, final Value d, final int index) {
                                brushmove();
                                return null;
                            }
                        })
                        .on(BrushEvent.BRUSH_END, new DatumFunction<Void>() {
                            @Override
                            public Void apply(final Element context, final Value d, final int index) {
                                brushend();
                                return null;
                            }
                        }))
                .selectAll("rect")
                .attr("height", height);
    }

    @Override
    public void stop() {

    }

    private void brushstart() {
        svg.classed(css.selecting(), true);
    }

    private void brushmove() {
        final Array<Object> extent = D3.event().getEventTarget().<Brush> cast().extent();
        symbol.classed(css.selected(), new DatumFunction<Boolean>() {
            @Override
            public Boolean apply(final Element context, final Value d, final int index) {
                double value = x.apply(d.<Type> as().getValue()).asDouble();
                return extent.getNumber(0) <= value && value <= extent.getNumber(1);
            }
        });
    }

    private void brushend() {
        svg.classed(css.selecting(), !(D3.event().getEventTarget().<Brush> cast()).empty());
    }
}
