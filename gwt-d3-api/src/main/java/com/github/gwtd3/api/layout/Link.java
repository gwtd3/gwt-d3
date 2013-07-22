package com.github.gwtd3.api.layout;

import com.github.gwtd3.api.Coords;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * A link in d3js' tree layout, see <a href="https://github.com/mbostock/d3/wiki/Tree-Layout#wiki-links">d3 docs on
 * link</a>. Provides accessors and setters for a link's two key attributes source and target.
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
     * Create a basic link object starting at one coordinate and ending at another
     * 
     * @param the starting coordinates
     * @param the ending coordinates
     * @return the link object
     */
    public static final native Link create(Coords source, Coords target) /*-{
		return {
			source : source,
			target : target
		};
    }-*/;

    /**
     * @return the end node
     */
    public final native Node target() /*-{
		return this.target;
    }-*/;

    /**
     * @return the start node
     */
    public final native Node source() /*-{
		return this.source;
    }-*/;
}
