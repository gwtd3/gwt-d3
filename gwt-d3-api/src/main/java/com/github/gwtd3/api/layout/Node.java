package com.github.gwtd3.api.layout;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
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
    
    public final native JsArray<JsArrayNumber> getCoords() /*-{
        return [this.x, this.y];
    }-*/;
}
