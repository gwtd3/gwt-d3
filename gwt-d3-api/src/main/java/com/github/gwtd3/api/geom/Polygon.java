/**
 * Copyright (c) 2013, Anthony Schiochet and Eric Citaire
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * The names Anthony Schiochet and Eric Citaire may not be used to endorse or promote products
 *   derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL MICHAEL BOSTOCK BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY
 * OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
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
