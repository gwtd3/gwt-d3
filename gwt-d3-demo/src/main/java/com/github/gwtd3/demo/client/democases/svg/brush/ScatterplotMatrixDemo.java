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

import java.util.HashMap;
import java.util.Map;

import com.github.gwtd3.api.Arrays;
import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.arrays.ForEachCallback;
import com.github.gwtd3.api.core.ObjectAccessor;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.dsv.DsvCallback;
import com.github.gwtd3.api.dsv.DsvRow;
import com.github.gwtd3.api.dsv.DsvRows;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.scales.LinearScale;
import com.github.gwtd3.api.scales.OrdinalScale;
import com.github.gwtd3.api.svg.Axis;
import com.github.gwtd3.api.svg.Axis.Orientation;
import com.github.gwtd3.api.svg.Brush;
import com.github.gwtd3.api.svg.Brush.BrushEvent;
import com.github.gwtd3.demo.client.DemoCase;
import com.github.gwtd3.demo.client.Factory;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * FIXME find another Slider component
 *
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 *
 */
public class ScatterplotMatrixDemo extends FlowPanel implements DemoCase {

    public interface Bundle extends ClientBundle {
        public static final Bundle INSTANCE = GWT.create(Bundle.class);

        @Source("ScatterPlotMatrixDemo.css")
        public MyResources css();
    }

    interface MyResources extends CssResource {
        String spm();

        String axis();

        String frame();

        String hidden();

    }

    private MyResources css;

    private Brush brush;

    private LinearScale x;

    private LinearScale y;

    private Map<String, JavaScriptObject> domainByTrait;

    private Selection svg;

    private double padding;

    private int size;

    private OrdinalScale color;

    private Element brushCell;

    public ScatterplotMatrixDemo() {
        super();
        this.css = Bundle.INSTANCE.css();
        this.css.ensureInjected();
        this.addStyleName(css.spm());
    }

    @Override
    public void start() {

        size = 150;
        padding = 19.5;

        x = D3.scale.linear()
                .range(padding / 2, size - padding / 2);

        y = D3.scale.linear()
                .range(size - padding / 2, padding / 2);

        final Axis xAxis = D3.svg().axis()
                .scale(x)
                .orient(Orientation.BOTTOM)
                .ticks(5);

        final Axis yAxis = D3.svg().axis()
                .scale(y)
                .orient(Orientation.LEFT)
                .ticks(5);

        color = D3.scale.category10();

        D3.csv("demo-data/flowers.csv", new DsvCallback<DsvRow>() {

            @Override
            public void get(final JavaScriptObject error, final DsvRows<DsvRow> data) {
                if (error != null) {
                    GWT.log("Cannot load flowers.csv: " + error);
                    return;
                }
                domainByTrait = new HashMap<String, JavaScriptObject>();
                Array<String> traits = D3.keys(data.get(0)).filter(new ForEachCallback<Boolean>() {
                    @Override
                    public Boolean forEach(final Object thisArg, final Value element, final int index,
                            final Array<?> array) {
                        return !"species".equals(element.asString());
                    }
                });
                final int n = traits.length();

                traits.forEach(new ForEachCallback<Void>() {
                    @Override
                    public Void forEach(final Object thisArg, final Value trait, final int index, final Array<?> array) {
                        // for the current trait, get the extent=domain=(min and max), and save it in the map
                        // save the domain
                        domainByTrait.put(trait.asString(), Arrays.extent(data, new ObjectAccessor<DsvRow, Double>() {
                            @Override
                            public Double apply(final DsvRow data, final int index) {
                                return data.get(trait.asString()).asDouble();
                            }
                        }));
                        return null;
                    }
                });

                xAxis.tickSize(size * n);
                yAxis.tickSize(-size * n);

                brush = D3.svg().brush()
                        .x(x)
                        .y(y)
                        .on(BrushEvent.BRUSH_START, new DatumFunction<Void>() {
                            @Override
                            public Void apply(final Element context, final Value d, final int index) {
                                brushstart(context, d.<Point> as());
                                return null;
                            }
                        })
                        .on(BrushEvent.BRUSH, new DatumFunction<Void>() {
                            @Override
                            public Void apply(final Element context, final Value d, final int index) {
                                brushmove(d.<Point> as());
                                return null;
                            }
                        })
                        .on(BrushEvent.BRUSH_END, new DatumFunction<Void>() {
                            @Override
                            public Void apply(final Element context, final Value d, final int index) {
                                brushend();
                                return null;
                            }
                        });

                svg = D3.select(ScatterplotMatrixDemo.this).append("svg")
                        .attr("width", size * n + padding)
                        .attr("height", size * n + padding)
                        .append("g")
                        .attr("transform", "translate(" + padding + "," + padding / 2 + ")");

                svg.selectAll(".x." + css.axis())
                .data(traits)
                .enter().append("g")
                .attr("class", "x " + css.axis())
                .attr("transform", new DatumFunction<String>() {
                    @Override
                    public String apply(final Element context, final Value d, final int index) {
                        return "translate(" + (n - index - 1) * size + ",0)";
                    }
                })
                .each(new DatumFunction<Void>() {
                    @Override
                    public Void apply(final Element context, final Value d, final int index) {
                        x.domain(domainByTrait.get(d.asString()));
                        D3.select(context).call(xAxis);
                        return null;
                    }
                });

                svg.selectAll(".y." + css.axis())
                .data(traits)
                .enter().append("g")
                .attr("class", "y " + css.axis())
                .attr("transform", new DatumFunction<String>() {
                    @Override
                    public String apply(final Element context, final Value d, final int index) {
                        return "translate(0," + index * size + ")";
                    }
                })
                .each(new DatumFunction<Void>() {
                    @Override
                    public Void apply(final Element context, final Value d, final int index) {
                        y.domain(domainByTrait.get(d.asString()));
                        D3.select(context).call(yAxis);
                        return null;
                    }
                });

                Selection cell = svg.selectAll(".cell")
                        .data(cross(traits, traits))
                        .enter().append("g")
                        .attr("class", "cell")
                        .attr("transform", new DatumFunction<String>() {
                            @Override
                            public String apply(final Element context, final Value d, final int index) {
                                return "translate(" + (n - d.<Point> as().i - 1) * size + "," + d.<Point> as().j * size
                                        + ")";
                            }
                        })
                        .each(new DatumFunction<Void>() {
                            @Override
                            public Void apply(final Element context, final Value d, final int index) {
                                plot(context, d.<Point> as(), data);
                                return null;
                            }
                        });

                // Titles for the diagonal.
                cell
                .filter(new DatumFunction<Element>() {
                    @Override
                    public Element apply(final Element context, final Value d, final int index) {
                        return d.<Point> as().i == d.<Point> as().j ? context : null;
                    }
                })
                .append("text")
                .attr("x", padding)
                .attr("y", padding)
                .attr("dy", ".71em")
                .text(new DatumFunction<String>() {
                    @Override
                    public String apply(final Element context, final Value d, final int index) {
                        return d.<Point> as().x;
                    }
                });

                cell.call(brush);

                D3.select(ScatterplotMatrixDemo.this.getElement()).style("height", size * n + padding + 20 + "px");
            }
        });

    }

    @Override
    public void stop() {

    }

    private void plot(final Element context, final Point p, final DsvRows<DsvRow> data) {
        Selection cell = D3.select(context);

        x.domain(domainByTrait.get(p.x));
        y.domain(domainByTrait.get(p.y));

        cell.append("rect")
        .attr("class", css.frame())
        .attr("x", padding / 2)
        .attr("y", padding / 2)
        .attr("width", size - padding)
        .attr("height", size - padding);

        cell.selectAll("circle")
        .data(data)
        .enter().append("circle")
        .attr("cx", new DatumFunction<String>() {
            @Override
            public String apply(final Element context, final Value d, final int index) {
                Value v = d.<DsvRow> as().get(p.x);
                String asString = x.apply(v.asDouble()).asString();
                return asString;
            }
        })
        .attr("cy", new DatumFunction<String>() {
            @Override
            public String apply(final Element context, final Value d, final int index) {
                double asDouble = d.<DsvRow> as().get(p.y).asDouble();
                String asString = y.apply(asDouble).asString();
                return asString;
            }
        })
        .attr("r", 3)
        .style("fill", new DatumFunction<String>() {
            @Override
            public String apply(final Element context, final Value d, final int index) {
                return color.apply(d.<DsvRow> as().get("species").asString()).asString();
            }
        });
    }

    // Clear the previously-active brush, if any.
    private void brushstart(final Element context, final Point p) {
        // Clear the previously-active brush, if any.
        if (brushCell != context) {
            D3.select(brushCell).call(brush.clear());
            x.domain(domainByTrait.get(p.x));
            y.domain(domainByTrait.get(p.y));
            brushCell = context;
        }
    }

    // Highlight the selected circles.
    private void brushmove(final Point p) {
        final Array<Array<Double>> e = brush.extent();
        svg.selectAll("circle").classed(css.hidden(), new DatumFunction<Boolean>() {
            @Override
            public Boolean apply(final Element context, final Value d, final int index) {
                // the plot coords
                double px = d.<DsvRow> as().get(p.x).asDouble();
                double py = d.<DsvRow> as().get(p.y).asDouble();
                // the extent of the brush
                double ex0 = e.get(0).getNumber(0);
                double ey0 = e.get(0).getNumber(1);
                double ex1 = e.get(1).getNumber(0);
                double ey1 = e.get(1).getNumber(1);
                // hide it (returns true) if the plot is outside the brush extent
                boolean b = ex0 > px || px > ex1 || ey0 > py || py > ey1;
                return b;
            }
        });
    }

    // If the brush is empty, select all circles.
    private void brushend() {
        if (brush.empty()) {
            svg.selectAll(".hidden").classed(css.hidden(), false);
        }
    }

    private static class Point {
        String x, y;
        double i, j;

        public Point(final String x, final String y, final int i, final int j) {
            super();
            this.x = x;
            this.y = y;
            this.i = i;
            this.j = j;
        }

    }

    private Array<?> cross(final Array<String> a, final Array<String> b) {
        Array<Point> c = Array.create();
        int n = a.length();
        int m = b.length(), i, j;
        for (i = -1; ++i < n;) {
            for (j = -1; ++j < m;) {
                c.push(new Point(a.getString(i), b.getString(j), i, j));
            }
        }
        return c;
    }

    /**
     * @return
     */
    public static Factory factory() {
        return new Factory() {
            @Override
            public DemoCase newInstance() {
                return new ScatterplotMatrixDemo();
            }
        };
    }

}
