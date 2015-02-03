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

import com.github.gwtd3.api.Arrays;
import com.github.gwtd3.api.Coords;
import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.D3Event;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.arrays.ForEachCallback;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.geom.Quadtree.Callback;
import com.github.gwtd3.api.geom.Quadtree.Node;
import com.github.gwtd3.api.geom.Quadtree.RootNode;
import com.github.gwtd3.api.scales.IdentityScale;
import com.github.gwtd3.api.svg.Brush;
import com.github.gwtd3.api.svg.Brush.BrushEvent;
import com.github.gwtd3.demo.client.DemoCase;
import com.github.gwtd3.demo.client.Factory;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.FlowPanel;

public class BrushTransitionsDemo extends FlowPanel implements DemoCase {

    public interface Bundle extends ClientBundle {
        public static final Bundle INSTANCE = GWT.create(Bundle.class);

        @Source("BrushTransitionsDemo.css")
        public MyResources css();
    }

    interface MyResources extends CssResource {
        String bt();

        String point();

        String selected();

        String brush();
    }

    private MyResources css;
    private Brush brush;
    private Selection svg;
    private Selection point;

    public BrushTransitionsDemo() {
        super();
        this.css = Bundle.INSTANCE.css();
        this.css.ensureInjected();
        this.addStyleName(css.bt());
    }

    public static class Point extends Coords {
        protected Point() {
        }

        public native final void setSelected(boolean isSelected)/*-{
			this.selected = isSelected;
        }-*/;

        public native final boolean isSelected()/*-{
			return this.selected;
        }-*/;

    }

    @Override
    public void start() {
        final int width = 960, height = 500;

        @SuppressWarnings("unchecked")
        final Array<Array<Double>> defaultExtent = Array.fromObjects(Array.fromDoubles(100, 100),
                Array.fromDoubles(300, 300));

        Array<Point> data = Arrays.range(5000.0).map(new ForEachCallback<Point>() {
            @Override
            public Point forEach(final Object thisArg, final Value element, final int index, final Array<?> array) {
                return Coords.create(Math.random() * width, Math.random() * width).cast();
            }
        });

        final RootNode<Point> quadtree = D3.geom().quadtree()
                .extent(-1, -1, width + 1, height + 1)
                .x(Coords.X_ACCESSOR)
                .y(Coords.Y_ACCESSOR)
                .apply(data);

        IdentityScale x = D3.scale.identity().domain(0, width);
        IdentityScale y = D3.scale.identity().domain(0, height);

        brush = D3.svg().brush()
                .x(x)
                .y(y)
                .extent(defaultExtent)
                .on(BrushEvent.BRUSH, new DatumFunction<Void>() {
                    @Override
                    public Void apply(final Element context, final Value d, final int index) {
                        Array<Array<Double>> extent = brush.extent();
                        point.each(new DatumFunction<Void>() {
                            @Override
                            public Void apply(final Element context, final Value d, final int index) {
                                d.<Point> as().setSelected(false);
                                return null;
                            }
                        });
                        search(quadtree,
                                extent.get(0).getNumber(0),
                                extent.get(0).getNumber(1),
                                extent.get(1).getNumber(0),
                                extent.get(1).getNumber(1));
                        point.classed(css.selected(), new DatumFunction<Boolean>() {
                            @Override
                            public Boolean apply(final Element context, final Value d, final int index) {
                                return d.<Point> as().isSelected();
                            }
                        });
                        return null;
                    }
                })
                .on(BrushEvent.BRUSH_END, new DatumFunction<Void>() {
                    @Override
                    public Void apply(final Element context, final Value d, final int index) {
                        if (D3.<D3Event> event().sourceEvent() == null) {
                            return null; // only transition after input
                        }
                        D3.select(context).transition()
                        .duration(brush.empty() ? 0 : 750)
                        .call(brush.extent(defaultExtent))
                        .call(brush.event());
                        return null;
                    }
                });

        svg = D3.select(this).append("svg")
                .attr("width", width)
                .attr("height", height);

        point = svg.selectAll("." + css.point())
                .data(data)
                .enter().append("circle")
                .attr("class", css.point())
                .attr("cx", new DatumFunction<Double>() {
                    @Override
                    public Double apply(final Element context, final Value d, final int index) {
                        return d.<Coords> as().x();
                    }
                })
                .attr("cy", new DatumFunction<Double>() {
                    @Override
                    public Double apply(final Element context, final Value d, final int index) {
                        return d.<Coords> as().y();
                    }
                })
                .attr("r", 4);

        svg.append("g")
        .attr("class", css.brush())
        .call(brush)
        .call(brush.event());

    }

    // Find the nodes within the specified rectangle.
    private void search(final RootNode<Point> quadtreeRoot, final double x0, final double y0, final double x3,
            final double y3) {
        quadtreeRoot.visit(new Callback<Point>() {
            @Override
            public boolean visit(final Node<Point> node, final double x1, final double y1, final double x2,
                    final double y2) {
                Point p = node.point();
                if (p != null) {
                    p.setSelected((p.x() >= x0) && (p.x() < x3) && (p.y() >= y0) && (p.y() < y3));
                }
                return x1 >= x3 || y1 >= y3 || x2 < x0 || y2 < y0;
            }
        });
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
                return new BrushTransitionsDemo();
            }
        };
    }

}
