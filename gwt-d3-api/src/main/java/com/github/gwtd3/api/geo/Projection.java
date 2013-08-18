package com.github.gwtd3.api.geo;

import com.google.gwt.core.client.JavaScriptObject;

//TODO: conic projection subclass with parallels() accessors
public class Projection<P extends Projection<P>> extends JavaScriptObject {

	protected Projection() {

	}

	public final native P rotate(double longitude, double latitude)/*-{
		return this.rotate([ longitude, latitude ]);
	}-*/;

	public final native P center(double longitude, double latitude)/*-{
		return this.center([ longitude, latitude ]);
	}-*/;

	/**
	 * Sets the projection’s scale factor to the specified value and returns the
	 * projection.
	 * 
	 * @param factor
	 *            the scale
	 * @return the projection
	 */
	public final native P scale(double factor)/*-{
		return this.scale(factor);
	}-*/;
}
