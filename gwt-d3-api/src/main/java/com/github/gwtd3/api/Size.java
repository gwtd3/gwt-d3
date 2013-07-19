package com.github.gwtd3.api;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayNumber;

public class Size
    extends JsArray<JsArrayNumber> {

    protected Size() {

    }
    
    public static final native Size create (double width, double height) /*-{
        return [width, height];
    }-*/;

    /**
     * @return the width 
     */
    public final native double width()/*-{
        return this[0];
    }-*/;

    /**
     * @return the height 
     */
    public final native double height()/*-{
        return this[1];
    }-*/;

    /**
     * set the width
     * 
     * @return the width 
     */
    public final native Size width(double width)/*-{
        this[0] = width;
        return this;
    }-*/;

    /**
     * set the height
     * 
     * @param height
     * @return
     */
    public final native Size height(double height)/*-{
        this[1] = height;
        return this;
    }-*/;
}
