package com.github.gwtd3.demo.client.democases;

import com.github.gwtd3.api.Coords;
import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.arrays.ForEachCallback;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Transition;
import com.github.gwtd3.api.core.UpdateSelection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.functions.KeyFunction;
import com.github.gwtd3.api.layout.Link;
import com.github.gwtd3.api.layout.Node;
import com.github.gwtd3.api.layout.TreeLayout;
import com.github.gwtd3.api.svg.Diagonal;
import com.github.gwtd3.demo.client.DemoCase;
import com.github.gwtd3.demo.client.Factory;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * A demonstration of how to build a d3js tree from simple JSON data with
 * collapse functionality and transitions
 * 
 * @author <a href="mailto:evanshi09@gmail.com">Evan Shi </a>
 * 
 */
public class TreeDemo
    extends FlowPanel
    implements DemoCase {

    // constants of tree
    final int width = 960;
    final int height = 600;
    final int duration = 750;
    final MyResources css = Bundle.INSTANCE.css();

    // global references for demo
    static int i = 0;
    static TreeDemoNode root = null;
    static Selection svg = null;
    static TreeLayout tree = null;
    static Diagonal diagonal = null;

    public interface Bundle
        extends ClientBundle {
        public static final Bundle INSTANCE = GWT.create(Bundle.class);

        @Source("TreeDemoStyles.css")
        public MyResources css();
    }

    interface MyResources
        extends CssResource {

        String link();

        String node();

        String border();
    }

    public TreeDemo() {
        super();
        Bundle.INSTANCE.css().ensureInjected();
    }

    // plug into gwt-d3 demo case framework
    public static Factory factory() {
        return new Factory() {
            @Override
            public DemoCase newInstance() {
                return new TreeDemo();
            }
        };
    }

    @Override
    public void start() {

        // dummy data
        String data = "{\n\"children\": [\n{\n\"children\": [\n{},\n{},\n{\n\"children\": [\n{}\n]\n},\n{}\n]\n},\n{}\n]\n}";

        // get tree layout
        tree = D3.layout().tree().size(width, height);
        // set the global way to draw paths
        diagonal = D3.svg().diagonal()
                .projection(new DatumFunction<Array<Double>>() {
                    @Override
                    public Array<Double> apply(Element context, Value d, int index) {
                        TreeDemoNode data = d.<TreeDemoNode> as();
                        return Array.fromDoubles(data.x(), data.y());
                    }
                });

        // add the SVG
        svg = D3.select(this).append("svg")
                .attr("width", width + 20)
                .attr("height", height + 280)
                .append("g")
                .attr("transform", "translate(10, 140)");

        // get the root of the tree and initialize it
        root = JSONParser.parseLenient(data).isObject().getJavaScriptObject().<TreeDemoNode> cast();
        root.setAttr("x0", (width - 20) / 2);
        root.setAttr("y0", 0);
        if (root.children() != null) {
            root.children().forEach(new Collapse());
        }
        update(root);
    }

    @Override
    public void stop() {
    }

    // follows d3 general update pattern for handling exiting and entering nodes/paths
    private void update(final TreeDemoNode source) {
        Array<Node> nodes = tree.nodes(root).reverse();
        Array<Link> links = tree.links(nodes);

        // normalize depth
        nodes.forEach(new ForEachCallback<Void>() {
            @Override
            public Void forEach(Object thisArg, Value element, int index, Array<?> array) {
                TreeDemoNode datum = element.<TreeDemoNode> as();
                datum.setAttr("y", datum.depth() * 180);
                return null;
            }
        });

        // assign ids to nodes
        UpdateSelection node = svg.selectAll("g." + css.node())
                .data(nodes, new KeyFunction<Integer>() {
                    @Override
                    public Integer map(Element context, Array<?> newDataArray, Value datum, int index) {
                        TreeDemoNode d = datum.<TreeDemoNode> as();
                        return ((d.id() == -1) ? d.id(++i) : d.id());
                    }
                });

        // add click function on node click
        Selection nodeEnter = node.enter().append("g")
                .attr("class", css.node())
                .attr("transform", "translate(" + source.getNumAttr("x0") + "," + source.getNumAttr("y0") + ")")
                .on("click", new Click());

        // add circles to all entering nodes
        nodeEnter.append("circle")
                .attr("r", 1e-6)
                .style("fill", new DatumFunction<String>() {
                    @Override
                    public String apply(Element context, Value d, int index) {
                        return (d.<TreeDemoNode> as().getObjAttr("_children") != null) ? "lightsteelblue" : "#fff";
                    }
                });

        // transition entering nodes
        Transition nodeUpdate = node.transition()
                .duration(duration)
                .attr("transform", new DatumFunction<String>() {
                    @Override
                    public String apply(Element context, Value d, int index) {
                        TreeDemoNode data = d.<TreeDemoNode> as();
                        return "translate(" + data.x() + "," + data.y() + ")";
                    }
                });

        nodeUpdate.select("circle")
                .attr("r", 4.5)
                .style("fill", new DatumFunction<String>() {
                    @Override
                    public String apply(Element context, Value d, int index) {
                        return (d.<TreeDemoNode> as().getObjAttr("_children") != null) ? "lightsteelblue" : "#fff";
                    }
                });

        // transition exiting nodes
        Transition nodeExit = node.exit().transition()
                .duration(duration)
                .attr("transform", new DatumFunction<String>() {
                    @Override
                    public String apply(Element context, Value d, int index) {
                        return "translate(" + source.x() + "," + source.y() + ")";
                    }
                })
                .remove();

        nodeExit.select("circle")
                .attr("r", 1e-6);

        // update svg paths for new node locations
        UpdateSelection link = svg.selectAll("path." + css.link())
                .data(links, new KeyFunction<Integer>() {
                    @Override
                    public Integer map(Element context, Array<?> newDataArray, Value datum, int index) {
                        return datum.<Link> as().target().<TreeDemoNode> cast().id();
                    }
                });

        link.enter().insert("svg:path", "g")
                .attr("class", css.link())
                .attr("d", new DatumFunction<String>() {
                    @Override
                    public String apply(Element context, Value d, int index) {
                        Coords o = Coords.create(source.getNumAttr("x0"), source.getNumAttr("y0"));
                        return diagonal.generate(Link.create(o, o));
                    }
                });

        link.transition()
                .duration(duration)
                .attr("d", diagonal);

        link.exit().transition()
                .duration(duration)
                .attr("d", new DatumFunction<String>() {
                    @Override
                    public String apply(Element context, Value d, int index) {
                        Coords o = Coords.create(source.x(), source.y());
                        return diagonal.generate(Link.create(o, o));
                    }
                })
                .remove();

        // update locations on node
        nodes.forEach(new ForEachCallback<Void>() {
            @Override
            public Void forEach(Object thisArg, Value element, int index, Array<?> array) {
                TreeDemoNode data = element.<TreeDemoNode> as();
                data.setAttr("x0", data.x());
                data.setAttr("y0", data.y());
                return null;
            }
        });
    }

    private class Collapse
        implements ForEachCallback<Void> {
        @Override
        public Void forEach(Object thisArg, Value element, int index, Array<?> array) {
            TreeDemoNode datum = element.<TreeDemoNode> as();
            Array<Node> children = datum.children();
            if (children != null) {
                datum.setAttr("_children", children);
                datum.getObjAttr("_children").<Array<Node>> cast().forEach(this);
                datum.setAttr("children", null);
            }
            return null;
        }
    }

    private class Click
        implements DatumFunction<Void> {
        @Override
        public Void apply(Element context, Value d, int index) {
            TreeDemoNode node = d.<TreeDemoNode> as();
            if (node.children() != null) {
                node.setAttr("_children", node.children());
                node.setAttr("children", null);
            }
            else {
                node.setAttr("children", node.getObjAttr("_children"));
                node.setAttr("_children", null);
            }
            update(node);
            return null;
        }
    }

    // Perhaps a mutable JSO class would be a nice feature?
    private static class TreeDemoNode
        extends Node {
        protected TreeDemoNode() {
            super();
        }

        protected final native int id() /*-{
			return this.id || -1;
        }-*/;

        protected final native int id(int id) /*-{
			return this.id;
        }-*/;

        protected final native void setAttr(String name, JavaScriptObject value) /*-{
			this[name] = value;
        }-*/;

        protected final native double setAttr(String name, double value) /*-{
			return this[name] = value;
        }-*/;

        protected final native JavaScriptObject getObjAttr(String name) /*-{
			return this[name];
        }-*/;

        protected final native double getNumAttr(String name) /*-{
			return this[name];
        }-*/;
    }
}
