/**
 * 
 */
package com.github.gwtd3.api;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * A Javascript object containing x and y properties.
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Coords extends JavaScriptObject {

	protected Coords() {

	}

	public static final native Coords create(double x, double y)/*-{
		return {
			x : x,
			y : y
		};
	}-*/;

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
	 * set the x coords
	 * 
	 * @return the x coordinates
	 */
	public final native Coords x(double x)/*-{
		this.x = x;
		return this;
	}-*/;

	/**
	 * set the y coords
	 * 
	 * @param y
	 * @return
	 */
	public final native Coords y(double y)/*-{
		this.y = y;
		return this;
	}-*/;
}
