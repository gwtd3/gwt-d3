package com.github.gwtd3.demo.client.democases;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.Size;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.layout.Link;
import com.github.gwtd3.api.layout.Node;
import com.github.gwtd3.api.layout.TreeLayout;
import com.github.gwtd3.api.svg.Diagonal;
import com.github.gwtd3.demo.client.DemoCase;
import com.github.gwtd3.demo.client.Factory;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayNumber;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.FlowPanel;

public class TreeDemo
    extends FlowPanel
    implements DemoCase {

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

        final MyResources css = Bundle.INSTANCE.css();

        String data = "{\n\"children\": [\n{\n\"children\": [\n{},\n{},\n{\n\"children\": [\n{}\n]\n},\n{}\n]\n},\n{}\n]\n}";

        int width = 960;
        int height = 600;

        Size size = Size.create(width, height);
        JSONValue root = JSONParser.parseLenient(data);

        TreeLayout tree = D3.layout().tree().size(size);
        JsArray<Node> nodes = tree.nodes((Node) root.isObject().getJavaScriptObject());
        JsArray<Link> links = tree.links(nodes);

        Diagonal diagonal = D3.svg().diagonal()
                .projection(new DatumFunction<JsArray<JsArrayNumber>>() {
                    @Override
                    public JsArray<JsArrayNumber> apply(Element context, Value d, int index) {
                        Node data = d.<Node> as();
                        return data.getCoords();
                    }
                });

        final Selection svg = D3.select(this).append("svg")
                .attr("width", width + 10)
                .attr("height", height + 140)
                .append("g")
                .attr("transform", "translate(40, 10)");

        final Selection link = svg.selectAll("pathlink")
                .data(links)
                .enter()
                .append("path")
                .attr("class", css.link())
                .attr("d", diagonal);

        final Selection node = svg.selectAll("g.node")
                .data(nodes)
                .enter().append("circle")
                .attr("class", css.node())
                .attr("cx", new DatumFunction<Double>() {
                    @Override
                    public Double apply(Element context, Value d, int index) {
                        Node data = d.<Node> as();
                        return data.x();
                    }
                })
                .attr("cy", new DatumFunction<Double>() {

                    @Override
                    public Double apply(Element context, Value d, int index) {
                        Node data = d.<Node> as();
                        return data.y();
                    }
                })
                .attr("r", 4.5);
    }

    @Override
    public void stop() {
    }

    public void update() {

    }
}
