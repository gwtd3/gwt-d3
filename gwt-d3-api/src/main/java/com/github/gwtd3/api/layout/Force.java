package com.github.gwtd3.api.layout;

import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.behaviour.Drag;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.functions.DatumFunction;
import com.google.gwt.core.client.JavaScriptObject;

public class Force extends JavaScriptObject {

    protected Force() {
    }

    public final native Force linkDistance(int x) /*-{
		return this.linkDistance(x);
    }-*/;

    public final native Array<? extends Node> nodes() /*-{
		return this.nodes();
    }-*/;

    public final native Force nodes(Array<? extends Node> nodes) /*-{
		return this.nodes(nodes);
    }-*/;

    public final native Array<? extends Link> links() /*-{
		return this.links();
    }-*/;

    public final native Force links(Array<? extends Link> links) /*-{
		return this.links(links);
    }-*/;

    public final native Array<Short> size() /*-{
		return this.size();
    }-*/;

    public final native Force size(Array<Short> width_height) /*-{
		return this.size(width_height);
    }-*/;

    public final native Drag drag() /*-{
		return this.drag();
    }-*/;

    public final native Force start() /*-{
		return this.start();
    }-*/;

    public native final Selection on(String name, DatumFunction<?> callback) /*-{
		try {
			return this
					.on(
							name,
							function(d, i) {
								try {

									var r = callback.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
									//r.@java.lang.Object::toString()();
									return r;
								} catch (e) {
									alert(e);
									return null;
								}
							});
		} catch (e) {
			alert(e);
			return null;
		}
    }-*/;
}