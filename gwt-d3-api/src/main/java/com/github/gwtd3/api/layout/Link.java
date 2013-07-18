package com.github.gwtd3.api.layout;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Provides Java access to links in d3js trees
 * 
 * @author <a href="mailto:evanshi09@gmail.com">Evan Shi</a>
 *
 */
public class Link
    extends JavaScriptObject {
    protected Link() {
        super();
    }
    /**
     * @return the end node
     */
    public final native Node getTarget() /*-{
        return this.target;
    }-*/;
    
    /**
     * @return the start node
     */
    public final native Node getSource() /*-{
        return this.source;
    }-*/;
}
