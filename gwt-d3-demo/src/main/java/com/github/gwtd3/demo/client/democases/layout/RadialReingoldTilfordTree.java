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
package com.github.gwtd3.demo.client.democases.layout;

import java.util.ArrayList;
import java.util.List;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.layout.Cluster.Node;
import com.github.gwtd3.api.layout.HierarchicalLayout.Link;
import com.github.gwtd3.api.layout.SeparationFunction;
import com.github.gwtd3.api.layout.Tree;
import com.github.gwtd3.api.svg.Diagonal;
import com.github.gwtd3.demo.client.DemoCase;
import com.github.gwtd3.demo.client.Factory;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.dom.client.Element;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * Radial Reingold–Tilford Tree
 * Example inspired from original Javascript example available at http://bl.ocks.org/mbostock/4063550
 *
 * @author Eric Citaire
 */
public class RadialReingoldTilfordTree extends FlowPanel implements DemoCase {
    private static final String JSON_URL = "demo-data/flare.json";
    private final MyResources css;

    public interface Bundle extends ClientBundle {
        public static final Bundle INSTANCE = GWT.create(Bundle.class);

        @Source("RadialReingoldTilfordTree.css")
        public MyResources css();
    }

    interface MyResources extends CssResource {
        String link();

        String node();
    }

    public RadialReingoldTilfordTree() {
        css = Bundle.INSTANCE.css();
        css.ensureInjected();
    }

    @Override
    public void start() {
        double diameter = 960;

        final Tree<FlareNode> tree = D3.layout().tree();
        tree.children(new DatumFunction<List<FlareNode>>() {
            @Override
            public List<FlareNode> apply(final Element context, final Value d, final int index) {
                FlareNode node = d.as(FlareNode.class);
                return node.isLeaf() ? new ArrayList<FlareNode>() : node.children().asList();
            }
        })
                .size(360, diameter / 2 - 120)
                .separation(new SeparationFunction<Tree.Node<FlareNode>>() {
                    @Override
                    public double separation(final Tree.Node<FlareNode> a,
                            final Tree.Node<FlareNode> b) {
                        return (double) (a.parent() == b.parent() ? 1 : 2) / a.depth();
                    }
                });

        final Diagonal diagonal = D3.svg().radialDiagonal()
                .projection(new DatumFunction<Array<Double>>() {
                    @Override
                    public Array<Double> apply(final Element context, final Value value, final int index) {
                        return Array.fromDoubles(value.asCoords().y(), value.asCoords().x() / 180 * Math.PI);
                    }
                });

        final Selection svg = D3.select(this).append("svg")
                .attr("width", diameter)
                .attr("height", diameter - 150)
                .append("g")
                .attr("transform", "translate(" + diameter / 2 + "," + diameter / 2 + ")");

        // Send request to server and catch any errors.
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, JSON_URL);

        try {
            Request request = builder.sendRequest(null, new RequestCallback() {
                @Override
                public void onError(final Request request, final Throwable exception) {
                    Window.alert("Couldn't retrieve JSON");
                }

                @Override
                public void onResponseReceived(final Request request, final Response response) {
                    if (200 == response.getStatusCode()) {
                        FlareNode root = JsonUtils.safeEval(response.getText());
                        Array<Tree.Node<FlareNode>> nodes = tree.nodes(root);
                        Array<Link<FlareNode>> links = tree.links(nodes);

                        Selection link = svg.selectAll("." + css.link())
                                .data(links)
                                .enter().append("path")
                                .attr("class", css.link())
                                .attr("d", diagonal);

                        DatumFunction<String> transform = new DatumFunction<String>() {
                            @Override
                            public String apply(final Element context, final Value d, final int index) {
                                Tree.Node<FlareNode> node = d.<Tree.Node<FlareNode>> as();
                                double x = node.x();
                                double y = node.y();
                                return "rotate(" + (x - 90) + ")translate(" + y + ")";
                            }
                        };

                        Selection node = svg.selectAll("." + css.node())
                                .data(nodes)
                                .enter().append("g")
                                .attr("class", css.node())
                                .attr("transform", transform);

                        node.append("circle")
                                .attr("r", 4.5);

                        node.append("text")
                                .attr("dy", ".31em")
                                .attr("text-anchor", new DatumFunction<String>() {
                                    @Override
                                    public String apply(final Element context, final Value d, final int index) {
                                        Tree.Node<FlareNode> node = d.<Tree.Node<FlareNode>> as();
                                        return node.x() < 180 ? "start" : "end";
                                    }
                                })
                                .attr("transform", new DatumFunction<String>() {
                                    @Override
                                    public String apply(final Element context, final Value d, final int index) {
                                        Tree.Node<FlareNode> node = d.<Tree.Node<FlareNode>> as();
                                        return node.x() < 180 ? "translate(8)" : "rotate(180)translate(-8)";
                                    }
                                })
                                .text(new DatumFunction<String>() {
                                    @Override
                                    public String apply(final Element context, final Value d, final int index) {
                                        Node<FlareNode> node = d.<Node<FlareNode>> as();
                                        return node.datum().name();
                                    }
                                });

                    } else {
                        Window.alert("Couldn't retrieve JSON (" + response.getStatusText() + ")");
                    }
                }
            });

            D3.select(RadialReingoldTilfordTree.this).style("height", diameter - 150 + "px");
        } catch (RequestException e) {
            Window.alert("Couldn't retrieve JSON");
        }

    }

    @Override
    public void stop() {

    }

    public static Factory factory() {
        return new Factory() {
            @Override
            public DemoCase newInstance() {
                return new RadialReingoldTilfordTree();
            }
        };
    }

    public static class FlareNode extends JavaScriptObject {

        protected FlareNode() {

        }

        public final native String name() /*-{
                                          return this.name;
                                          }-*/;

        public final native int size() /*-{
                                       return this.size;
                                       }-*/;

        public final native Array<FlareNode> children()/*-{
                                                       return this.children;
                                                       }-*/;

        public final native boolean isLeaf()/*-{
                                            return !this.children;
                                            }-*/;
    }
}
