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
package com.github.gwtd3.demo.client.democases.geom;

import com.github.gwtd3.api.Arrays;
import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.arrays.ForEachCallback;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.UpdateSelection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.functions.KeyFunction;
import com.github.gwtd3.api.geom.Voronoi;
import com.github.gwtd3.demo.client.DemoCase;
import com.github.gwtd3.demo.client.Factory;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * Original demo is <a href="http://bl.ocks.org/mbostock/3808218">here</a>
 *
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 *
 */
public class VoronoiTessellationDemo extends FlowPanel implements DemoCase {

    public interface Bundle extends ClientBundle {
        public static final Bundle INSTANCE = GWT.create(Bundle.class);

        @Source("VoronoiTessellationDemo.css")
        public MyResources css();
    }

    interface MyResources extends CssResource {
        String vt();
    }

    private MyResources css;
    private Selection path;
    private Voronoi voronoi;
    private Array<Array<Double>> vertices;

    /**
     *
     */
    public VoronoiTessellationDemo() {
        super();

        this.css = Bundle.INSTANCE.css();
        this.css.ensureInjected();

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.github.gwtd3.demo.client.D3Demo#start()
     */
    @Override
    public void start() {
        final int width = 960, height = 500;

        vertices = Arrays.range(100).map(new ForEachCallback<Array<Double>>() {
            @Override
            public Array<Double> forEach(final Object thisArg, final Value element, final int index, final Array<?> array) {
                return Array.fromDoubles(Math.random() * width, Math.random() * height);
            }
        });
        voronoi = D3.geom().voronoi()
                .clipExtent(0, 0, width, height);
        Selection svg = D3.select(this).append("svg")
                .classed(css.vt(), true)
                .attr("width", width)
                .attr("height", height)
                .on(BrowserEvents.MOUSEMOVE, new DatumFunction<Void>() {

                    @Override
                    public Void apply(final Element context, final Value d, final int index) {
                        vertices.set(0, D3.mouse(context));
                        redraw();
                        return null;
                    }
                });

        path = svg.append("g").selectAll("path");

        svg.selectAll("circle")
                .data(vertices.slice(1))
                .enter().append("circle")
                .attr("transform", new DatumFunction<String>() {
                    @Override
                    public String apply(final Element context, final Value d, final int index) {
                        return "translate(" + d.asString() + ")";
                    }
                })
                .attr("r", 1.5);

        redraw();

    }

    private void redraw() {
        UpdateSelection upd = this.path.data(voronoi.apply(vertices), new KeyFunction<String>() {
            @Override
            public String map(final Element context, final Array<?> newDataArray, final Value datum, final int index) {
                String polygon = polygon(datum);
                return polygon;
            }
        });

        upd.exit().remove();

        upd.enter().append("path")
                .attr("class", new DatumFunction<String>() {
                    @Override
                    public String apply(final Element context, final Value d, final int index) {
                        return "q" + index % 9 + "-9";
                    }
                })
                .attr("d", new DatumFunction<String>() {
                    @Override
                    public String apply(final Element context, final Value d, final int index) {
                        return polygon(d);
                    }
                });

        upd.order();
        this.path = upd;
    }

    private String polygon(final Value datum) {
        return "M" + datum.<Array<?>> as().join("L") + "Z";
    }

    @Override
    public void stop() {
    }

    public static Factory factory() {
        return new Factory() {
            @Override
            public DemoCase newInstance() {
                return new VoronoiTessellationDemo();
            }
        };
    }
}
