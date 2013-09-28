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
import com.github.gwtd3.api.functions.DatumFunction;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * A <a href="http://en.wikipedia.org/wiki/Convex_hull">convex hull</a>.
 * 
 * @see <a href="http://bl.ocks.org/mbostock/4341699">this example</a>
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Hull extends JavaScriptObject {

	protected Hull() {

	}

	/**
	 * Sets the x-coordinate accessor.
	 * <p>
	 * The default accessor consider datum as a two element array and returns
	 * the first element.
	 * 
	 * @param xAccessor
	 *            the x accessor
	 * @return the current hull
	 */
	public final native Hull x(DatumFunction<Double> xAccessor)/*-{
		return this
				.x(function(d, i) {
					return xAccessor.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
	}-*/;

	/**
	 * Sets the y-coordinate accessor.
	 * <p>
	 * The default accessor consider datum as a two element array and returns
	 * the first element.
	 * 
	 * @param yAccessor
	 *            the y accessor
	 * @return the current hull
	 */

	public final native Hull y(DatumFunction<Double> yAccessor)/*-{
		return this
				.y(function(d, i) {
					return yAccessor.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
	}-*/;

	/**
	 * Returns the convex hull for the specified vertices array, using the
	 * current x- and y-coordinate accessors.
	 * <p>
	 * The returned convex hull is represented as an array containing a subset
	 * of the input vertices, arranged in counterclockwise order (for
	 * consistency with polygon.clip).
	 * <p>
	 * 
	 * @param vertices
	 *            the array of vertices
	 * @return the convex hull as an array of vertices
	 */
	public final native <T> Array<T> apply(Array<T> vertices)/*-{
		return this(vertices);
	}-*/;

	/**
	 * Returns the convex hull for the specified vertices array, using the
	 * current x- and y-coordinate accessors.
	 * <p>
	 * The returned convex hull is represented as an array containing a subset
	 * of the input vertices, arranged in counterclockwise order (for
	 * consistency with polygon.clip).
	 * <p>
	 * 
	 * @param vertices
	 *            the array of vertices
	 * @return the convex hull as an array of vertices
	 */
	public final <T> List<T> apply(List<T> vertices) {
		return this.apply(Array.fromIterable(vertices)).asList();
	}

}
