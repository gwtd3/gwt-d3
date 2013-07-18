package com.github.gwtd3.demo.client.democases;

import com.github.gwtd3.api.Coords;
import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.Size;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.arrays.ForEachCallback;
import com.github.gwtd3.api.core.Selection;
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
import com.google.gwt.core.client.JsArrayNumber;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.FlowPanel;

public class TreeDemo
    extends FlowPanel
    implements DemoCase {

    final int width = 960;
    final int height = 600;
    static int i = 0;
    final int duration = 750;
    static Node root = null;
    static TreeLayout tree = null;
    static Diagonal diagonal = null;
    static Selection svg = null;
    final MyResources css = Bundle.INSTANCE.css();

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

        String data = "{\n\"children\": [\n{\n\"children\": [\n{},\n{},\n{\n\"children\": [\n{}\n]\n},\n{}\n]\n},\n{}\n]\n}";

        Size size = Size.create(width, height);

        tree = D3.layout().tree().size(size);
        diagonal = D3.svg().diagonal()
                .projection(new DatumFunction<Array<JsArrayNumber>>() {
                    @Override
                    public Array<JsArrayNumber> apply(Element context, Value d, int index) {
                        Node data = d.<Node> as();
                        return data.getCoords();
                    }
                });

        svg = D3.select(this).append("svg")
                .attr("width", width + 20)
                .attr("height", height + 280)
                .append("g")
                .attr("transform", "translate(10, 140)");

        root = JSONParser.parseLenient(data).isObject().getJavaScriptObject().<Node> cast();
        root.setAttr("x0", (width - 20) / 2);
        root.setAttr("y0", 0);
        if (root.hasChildren() != null) {
            root.hasChildren().forEach(new Collapse());
        }
        update(root);
    }

    @Override
    public void stop() {
    }

    private void update(final Node source) {
        Array<Node> nodes = tree.nodes(root).reverse();
        Array<Link> links = tree.links(nodes);

        nodes.forEach(new ForEachCallback<Void>() {
            @Override
            public Void forEach(Object thisArg, Value element, int index, Array<?> array) {
                Node datum = element.<Node> as();
                datum.setAttr("y", datum.getDepth() * 180);
                return null;
            }
        });

        UpdateSelection node = svg.selectAll("g." + css.node())
                .data(nodes, new KeyFunction<Integer>() {
                    @Override
                    public Integer map(Element context, Array<?> newDataArray, Value datum, int index) {
                        Node d = datum.<Node> as();
                        return (d.hasId() == -1) ? d.setAttr("id", ++i) : d.hasId();
                    }
                });

        Selection nodeEnter = node.enter().append("g")
                .attr("class", css.node())
                .attr("transform", "translate(" + source.getNumAttr("x0") + "," + source.getNumAttr("x0") + ")")
                .on("click", new Click());

        nodeEnter.append("circle")
                .attr("r", 1e-6)
                .style("fill", new DatumFunction<String>() {
                    @Override
                    public String apply(Element context, Value d, int index) {
                        return (d.<Node> as().getObjAttr("_children") != null) ? "lightsteelblue" : "#fff";
                    }
                });

        Selection nodeUpdate = node.transition()
                .duration(duration)
                .attr("transform", new DatumFunction<String>() {
                    @Override
                    public String apply(Element context, Value d, int index) {
                        Node data = d.<Node> as();
                        return "translate(" + data.x() + "," + data.y() + ")";
                    }
                });

        nodeUpdate.select("circle")
                .attr("r", 4.5)
                .style("fill", new DatumFunction<String>() {
                    @Override
                    public String apply(Element context, Value d, int index) {
                        return (d.<Node> as().getObjAttr("_children") != null) ? "lightsteelblue" : "#fff";
                    }
                });

        Selection nodeExit = node.exit().transition()
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

        UpdateSelection link = svg.selectAll("path." + css.link())
                .data(links, new KeyFunction<Integer>() {
                    @Override
                    public Integer map(Element context, Array<?> newDataArray, Value datum, int index) {
                        return datum.<Link> as().getTarget().hasId();
                    }
                });

        link.enter().insert("svg:path", "g")
                .attr("class", css.link())
                .attr("d", new DatumFunction<String>() {
                    @Override
                    public String apply(Element context, Value d, int index) {
                        Coords o = Coords.create(source.getNumAttr("x0"), source.getNumAttr("y0"));
                        return diagonal.apply(o, o);
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
                        return diagonal.apply(o, o);
                    }
                })
                .remove();

        nodes.forEach(new ForEachCallback<Void>() {
            @Override
            public Void forEach(Object thisArg, Value element, int index, Array<?> array) {
                Node data = element.<Node> as();
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
            Node datum = element.<Node> as();
            Array<Node> children = datum.hasChildren();
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
            Node node = d.<Node> as();
            if (node.hasChildren() != null) {
                node.setAttr("_children", node.hasChildren());
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
}
