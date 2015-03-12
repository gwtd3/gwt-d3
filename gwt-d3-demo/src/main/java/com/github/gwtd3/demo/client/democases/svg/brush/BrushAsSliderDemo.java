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
/**
 *
 */
package com.github.gwtd3.demo.client.democases.svg.brush;

import com.github.gwtd3.api.Colors;
import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.D3Event;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.scales.LinearScale;
import com.github.gwtd3.api.svg.Axis.Orientation;
import com.github.gwtd3.api.svg.Brush;
import com.github.gwtd3.api.svg.Brush.BrushEvent;
import com.github.gwtd3.demo.client.DemoCase;
import com.github.gwtd3.demo.client.Factory;
import com.github.gwtd3.demo.client.democases.Margin;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * FIXME find another Slider component
 *
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 *
 */
public class BrushAsSliderDemo extends FlowPanel implements DemoCase {

    public interface Bundle extends ClientBundle {
        public static final Bundle INSTANCE = GWT.create(Bundle.class);

        @Source("BrushAsSliderDemo.css")
        public MyResources css();
    }

    interface MyResources extends CssResource {
        String axis();

        String domain();

        String slider();

        String handle();

        String halo();
    }

    private MyResources css;
    private Brush brush;
    private LinearScale x;
    private Selection handle;

    public BrushAsSliderDemo() {
        super();
        this.css = Bundle.INSTANCE.css();
        this.css.ensureInjected();
    }

    @Override
    public void start() {
        Margin margin = new Margin(200, 50, 200, 50);
        int width = 960 - margin.left - margin.right, height = 500 - margin.bottom - margin.top;

        x = D3.scale.linear()
                .domain(0, 180)
                .range(0, width)
                .clamp(true);

        brush = D3.svg().brush()
                .x(x)
                .extent(0, 0)
                .on(BrushEvent.BRUSH, new DatumFunction<Void>() {
                    @Override
                    public Void apply(final Element context, final Value d, final int index) {
                        brushed(context);
                        return null;
                    }
                });

        Selection svg = D3.select(this)
                // .style("width", width + margin.left + margin.right + "px")
                .append("svg")
                .attr("width", width + margin.left + margin.right)
                .attr("height", height + margin.top + margin.bottom)
                .append("g")
                .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

        svg.append("g")
                .attr("class", "x " + css.axis())
                .attr("transform", "translate(0," + height / 2 + ")")
                .call(D3.svg().axis()
                        .scale(x)
                        .orient(Orientation.BOTTOM)
                        .tickFormat(new DatumFunction<String>() {
                            @Override
                            public String apply(final Element context, final Value d, final int index) {
                                return d.asString() + "°";
                            }
                        })
                        .tickSize(0)
                        .tickPadding(12))
                .select("." + css.domain())
                .select(new DatumFunction<Element>() {
                    @Override
                    public Element apply(final Element context, final Value d, final int index) {
                        Node cloneNode = context.cloneNode(true);
                        context.getParentNode().appendChild(cloneNode);
                        return cloneNode.cast();
                    }
                })
                .attr("class", css.halo());

        Selection slider = svg.append("g")
                .attr("class", css.slider())
                .call(brush);

        slider.selectAll(".extent,.resize")
                .remove();

        slider.select(".background")
                .attr("height", height);

        handle = slider.append("circle")
                .attr("class", css.handle())
                .attr("transform", "translate(0," + height / 2 + ")")
                .attr("r", 9);

        slider.call(brush.event())
        .transition() // gratuitous intro!
        .duration(750)
        .call(brush.extent(70, 70))
        .call(brush.event());

    }

    private void brushed(final Element context) {
        double value = brush.extent().getNumber(0);

        if (D3.<D3Event> event().sourceEvent() != null) { // not a programmatic event
            value = x.invert(D3.mouse(context).get(0)).asDouble();
            brush.extent(value, value);
        }

        handle.attr("cx", x.apply(value).asString());
        D3.select(this).style("background-color", Colors.hsl((int) value, .8, .8).toHexaString());
    }

    @Override
    public void stop() {

    }

    /**
     * @return
     */
    public static Factory factory() {
        return new Factory() {
            @Override
            public DemoCase newInstance() {
                return new BrushAsSliderDemo();
            }
        };
    }

}
