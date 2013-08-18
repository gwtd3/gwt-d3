package com.github.gwtd3.api.geom;

import com.github.gwtd3.api.arrays.Array;

public class Polygon extends Array<Array<Double>> {

	protected Polygon() {

	}

	/**
	 * Returns the signed area of this polygon.
	 * <p>
	 * If the vertices are in counterclockwise order, the area is positive,
	 * otherwise it is negative.
	 * 
	 * @return the signed area
	 */
	public native final double area()/*-{
		return this.area();
	}-*/;

	/**
	 * Returns a two-element array representing the centroid of this polygon.
	 * <p>
	 * 
	 * @return a two-element array representing the centroid of this polygon.
	 */
	public native final Array<?> centroid()/*-{
		return this.centroid();
	}-*/;

	/**
	 * Returns a two-element array representing the centroid of this polygon.
	 * <p>
	 * 
	 * @param k
	 *            a scale factor
	 * @return a two-element array representing the centroid of this polygon.
	 */
	public native final Array<?> centroid(double k) /*-{
		return this.centroid(k);
	}-*/;

	/**
	 * Clips the subject polygon against this polygon.
	 * <p>
	 * In other words, returns a polygon representing the intersection of this
	 * polygon and the subject polygon.
	 * <p>
	 * Assumes the clip polygon is counterclockwise and convex.
	 * <p>
	 * 
	 * @param subject
	 *            the polygon to clip against
	 * @return the clip polygon
	 */
	public native final Polygon clip(Array<Array<Double>> subject)/*-{
		return this.clip(subject);
	}-*/;

}
