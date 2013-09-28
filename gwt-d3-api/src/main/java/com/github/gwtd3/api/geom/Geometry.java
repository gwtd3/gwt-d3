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

import java.util.List;

import com.github.gwtd3.api.arrays.Array;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * The geometry module.
 * <p>
 * <ul>
 * <li>
 * <li>
 * <li>
 * <li>
 * </ul>
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Geometry extends JavaScriptObject {

	protected Geometry() {

	}

	/**
	 * Create a new hull layout with the default x- and y-accessors.
	 * <p>
	 * 
	 * @return the hull
	 */
	public native final Hull hull()/*-{
		return this.hull();
	}-*/;

	/**
	 * Compute the hull with default x- and y-accessors. See
	 * {@link Hull#apply(Array)}.
	 * <p>
	 * 
	 * @return the convex hull as an array of vertices
	 */
	public final native <T> Array<T> hull(Array<T> vertices)/*-{
		return this.hull(vertices);
	}-*/;

	/**
	 * Create a new hull layout with the default x- and y-accessors for the
	 * given array of vertices. See {@link Hull#apply(List)}.
	 * <p>
	 * 
	 * @return the convex hull as a list of vertices
	 */
	public final <T> List<T> hull(final List<T> vertices) {
		return this.hull(Array.fromIterable(vertices)).asList();
	}

	/**
	 * Returns a polygon object, which is the array of vertices with additional
	 * methods added to it.
	 * <p>
	 * 
	 * @return the {@link Polygon} object
	 */
	public native final Polygon polygon(Array<Array<Double>> vertices)/*-{
		return this.polygon(vertices);
	}-*/;

	/**
	 * Creates a new quadtree factory with the default x-accessor and y-accessor
	 * (that assume the input data is a two-element array of numbers; see below
	 * for details) and extent.
	 * <p>
	 * The default extent is null, such that it will be computed automatically
	 * from the input points.
	 * <p>
	 * 
	 * @return the {@link Quadtree} factory
	 */
	public native final Quadtree quadtree()/*-{
		return this.quadtree();
	}-*/;

}
