package com.github.gwtd3.api.xhr;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Overlay type for an XmlHttpRequest error.
 * 
 * @author <a href="mailto:eric.citaire@gmail.com">Eric Citaire</a>
 *
 */
public class XmlHttpRequest extends JavaScriptObject {
    protected XmlHttpRequest() {
        super();
    }
    
    public final native String statusText() /*-{
        return this.statusText;
    }-*/;
    
    public final native int status() /*-{
        return this.status;
    }-*/;
    
    public final native String response() /*-{
        return this.response;
    }-*/;
}
