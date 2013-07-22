package com.github.gwtd3.api.svg;

import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.layout.Link;
import com.github.gwtd3.api.layout.Node;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * Diagonal generator. Assumes the input data is an object with named attributes matching the accessors
 * {@link Diagonal#source()} and {@link Diagonal#target()}. The returned function generates the path data for a cubic
 * Bezier connecting the source and target points; the tangents are specified to produce smooth fan-in and fan-out when
 * connecting nodes, as in a node-link diagram.
 * 
 * Diagonals default to Cartesian orientations, but can be used in radial and other orientations using
 * {@link Diagonal#projection(DatumFunction)}.
 * 
 * @author <a href="mailto:evanshi09@gmail.com">Evan Shi</a>
 * 
 */
public class Diagonal
    extends PathDataGenerator {
    protected Diagonal() {
        super();
    }

    /**
     * Computes the path data string for the specified datum
     * 
     * @param the datum
     * @return the path data string
     */
    public final native String apply(Link datum) /*-{
		return this.call(this, datum);
    }-*/;

    /**
     * Computes the path data string for the specified datum, passing in the specified index to the accessor functions
     * 
     * @param the datum
     * @param index of the datum
     * @return the path data string
     */
    public final native String apply(Link datum, int index) /*-{
		this.call(this, datum, index);
    }-*/;

    /**
     * @return the callback function registered with the diagonal generator
     */
    public final native DatumFunction<?> projection() /*-{
		return this.projection();
    }-*/;

    /**
     * Registers a callback datum function for diagonal's projection
     * 
     * @param df the callback
     * @return this diagonal object
     */
    public final native Diagonal projection(DatumFunction<?> df) /*-{
		return this
				.projection(function(d, i) {
					return df.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
    }-*/;

    /**
     * @return the current source accessor
     */
    public final native JavaScriptObject source() /*-{
		return this.source();
    }-*/;

    /**
     * @return the current target accessor
     */
    public final native JavaScriptObject target() /*-{
		return this.target();
    }-*/;

    /**
     * Sets the source accessor to be constant
     * 
     * @param a constant source
     * @return the diagonal object
     */
    public final native Diagonal source(Node source) /*-{
		return this.source(source);
    }-*/;

    /**
     * Sets the target accessor to be constant
     * 
     * @param a constant target
     * @return the diagonal object
     */
    public final native Diagonal target(Node target) /*-{
		return this.target(target);
    }-*/;

    /**
     * Sets the source accessor function per datum
     * 
     * @param the source accessor function
     * @return the diagonal object
     */
    public final native Diagonal source(DatumFunction<?> df) /*-{
		return this
				.source(function(d, i) {
					return df.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
    }-*/;

    /**
     * Sets the target accessor function per datum
     * 
     * @param the target accessor function
     * @return the diagonal object
     */
    public final native Diagonal target(DatumFunction<?> df) /*-{
		return this
				.target(function(d, i) {
					return df.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
    }-*/;
}
