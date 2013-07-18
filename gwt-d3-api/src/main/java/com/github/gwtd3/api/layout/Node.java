package com.github.gwtd3.api.layout;

import com.github.gwtd3.api.arrays.Array;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayNumber;

/**
 * Provides Java access to nodes in d3js trees
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
     * @return the coordinates as a 2 element array
     */
    public final native Array<JsArrayNumber> getCoords() /*-{
        return [this.x, this.y];
    }-*/;

    /**
     * Sets the attribute of the underlying JavaScript Object
     * 
     * @param name of attribute as a string
     * @param value of any javascript object
     */
    public final native void setAttr(String name, JavaScriptObject value) /*-{
        this[name] = value;
    }-*/;

    /**
     * Sets the attribute of the underlying JavaScript Object
     * 
     * @param name of attribute as a string
     * @param value as a dobule
     */
    public final native int setAttr(String name, double value) /*-{
       return this[name] = value;
    }-*/;

    /**
     * @return array of node objects or null
     */
    public final native Array<Node> hasChildren() /*-{
      return this.children;
    }-*/;

    /**
     * @param name of attribute as a string
     * @return javascript object corresponding to attribute
     */
    public final native JavaScriptObject getObjAttr(String name) /*-{
     return this[name];
    }-*/;

    /**
     * @param name of attribute as a string
     * @return javascript number corresponding to attribute
     */
    public final native double getNumAttr(String name) /*-{
       return this[name];
    }-*/;

    /**
     * @return the node's depth, root depth = 0
     */
    public final native double getDepth() /*-{
      return this.depth;
    }-*/;

    /**
     * @return the node's id, else -1
     */
    public final native int hasId() /*-{
        return this.id || -1;
    }-*/;
}
