package com.github.gwtd3.api.layout;

import com.github.gwtd3.api.arrays.Array;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayNumber;

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

    public final native void setAttr(String name, JavaScriptObject value) /*-{
                                                                          this[name] = value;
                                                                          }-*/;

    public final native int setAttr(String name, double value) /*-{
                                                               return this[name] = value;
                                                               }-*/;

    public final native Array<Node> hasChildren() /*-{
                                                  return this.children;
                                                  }-*/;

    public final native JavaScriptObject getObjAttr(String name) /*-{
                                                                 return this[name];
                                                                 }-*/;

    public final native double getNumAttr(String name) /*-{
                                                       return this[name];
                                                       }-*/;

    public final native double getDepth() /*-{
                                          return this.depth;
                                          }-*/;

    public final native int hasId() /*-{
                                    return this.id || -1;
                                    }-*/;
}
