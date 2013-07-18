package com.github.gwtd3.api.layout;

import com.google.gwt.core.client.JavaScriptObject;

public class Link
    extends JavaScriptObject {
    protected Link() {
        super();
    }
    
    public final native Node getTarget() /*-{
        return this.target;
    }-*/;
}
