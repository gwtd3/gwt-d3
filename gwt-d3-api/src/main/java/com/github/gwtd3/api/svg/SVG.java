/**
 * 
 */
package com.github.gwtd3.api.svg;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Provide access to svg routines.
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class SVG extends JavaScriptObject {

	protected SVG() {
	}

	/**
	 * Create a new arc with default accessor functions.
	 * See {@link Arc} for details.
	 * 
	 * @return the arc
	 */
	public final native Arc arc()/*-{
		return this.arc();
	}-*/;

	/**
	 * Create a new default axis.
	 * 
	 * @return the axis
	 */
	public final native Axis axis()/*-{
		return this.axis();
	}-*/;

	/**
	 * Create a new default line.
	 * 
	 * @return the line
	 */
	public final native Line line()/*-{
		return this.line();
	}-*/;

	/**
	 * Create a new default area.
	 * 
	 * @return the area
	 */
	public final native Area area()/*-{
		return this.area();
	}-*/;

	/**
	 * Create a new default brush.
	 * 
	 * @return the brush
	 */
	public final native Brush brush() /*-{
		return this.brush();
	}-*/;

	/**
	 * Create a new default chord.
	 * 
	 * @return the chord
	 */
	public final native Chord chord() /*-{
		return this.chord();
	}-*/;

	/**
	 * Create a new default {@link Symbol}.
	 * 
	 * @return the symbol
	 */
	public final native Symbol symbol() /*-{
		return this.symbol();
	}-*/;
}
