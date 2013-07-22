package com.github.gwtd3.api.layout;

import com.github.gwtd3.api.Size;
import com.github.gwtd3.api.Sort;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.functions.DatumFunction;

/**
 * Provides Java access to d3js' tree layout and its customization options
 * 
 * @author <a href="mailto:evanshi09@gmail.com">Evan Shi</a>
 * 
 */
public class TreeLayout
    extends Layout<TreeLayout> {
    protected TreeLayout() {
        super();
    }

    /**
     * @param root of the the tree
     * @return array of nodes in the tree stemming from root
     */
    public final native Array<Node> nodes(Node r) /*-{
		return this.nodes(r);
    }-*/;

    /**
     * @param array of nodes in tree
     * @return array of links connecting nodes
     */
    public final native Array<Link> links(Array<Node> n) /*-{
		return this.links(n);
    }-*/;

    /**
     * @param s 2 element array of width and height of tree
     * @return this tree object
     */
    public final native TreeLayout size(Size s) /*-{
		return this.size(s);
    }-*/;

    /**
     * @param s 2 element array of width, height of nodes
     * @return this tree object
     */
    public final native TreeLayout nodeSize(Size s) /*-{
		return this.nodeSize(s);
    }-*/;

    /**
     * @param sort a predefined sorting convention
     * @return this tree object
     */
    public final native TreeLayout sort(Sort sort) /*-{
		return this.sort(sort);
    }-*/;

    /**
     * @param df a datum function describing how the tree will be traversed
     * @return this tree object
     */
    public final native TreeLayout sort(DatumFunction<?> df) /*-{
		return this
				.sort(function(d, i) {
					return df.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
    }-*/;

    /**
     * @param df a datum function describing how to calculate separation of nodes
     * @return this tree object
     */
    public final native TreeLayout separation(DatumFunction<?> df) /*-{
		return this
				.separation(function(d, i) {
					return df.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
    }-*/;

    /**
     * @param df a datum function describing how to compute children
     * @return this tree object
     */
    public final native TreeLayout children(DatumFunction<?> df) /*-{
		return this
				.children(function(d, i) {
					return df.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
    }-*/;

    /**
     * @param df a datum function describing how to access node values
     * @return this tree object
     */
    public final native TreeLayout value(DatumFunction<?> df) /*-{
		return this
				.value(function(d, i) {
					return df.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
    }-*/;

    /**
     * @return the datum function registered for calculating node values
     */
    public final native DatumFunction<?> value() /*-{
		return this.value();
    }-*/;
}
