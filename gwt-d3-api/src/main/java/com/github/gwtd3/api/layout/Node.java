package com.github.gwtd3.api.layout;

import com.github.gwtd3.api.arrays.Array;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * A node in d3j's tree layout, see <a href="https://github.com/mbostock/d3/wiki/Tree-Layout#wiki-nodes">d3 docs on
 * node</a>. The node class is used in {@link TreeLayout}, cluster, and partitions. The class provides accessors for the
 * nodes key attributes, its position, children, parent, and depth.
 * 
 * @author <a href="mailto:evanshi09@gmail.com">Evan Shi</a>
 * 
 */
public class Node
    extends JavaScriptObject {
    protected Node() {
        super();
    }

    /**
     * @return the x coordinates
     */
    public final native double x()/*-{
		return this.x;
    }-*/;

    /**
     * @return the y coordinates
     */
    public final native double y()/*-{
		return this.y;
    }-*/;

    /**
     * @return array of {@link Node} objects or null
     */
    public final native Array<Node> children() /*-{
		return this.children;
    }-*/;

    /**
     * @return parent node
     */
    public final native Node parent() /*-{
		return this.parent;
    }-*/;

    /**
     * @return the node's depth, root depth = 0
     */
    public final native double depth() /*-{
		return this.depth;
    }-*/;
}
