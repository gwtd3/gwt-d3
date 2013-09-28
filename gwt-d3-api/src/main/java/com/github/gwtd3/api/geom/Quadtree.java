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
 * A quadtree is a two-dimensional recursive spatial subdivision.
 * <p>
 * This implementation uses square partitions, dividing each square into four
 * equally-sized squares. Each point exists in a unique node; if multiple points
 * are in the same position, some points may be stored on internal nodes rather
 * than leaf nodes.
 * <p>
 * Quadtrees can be used to accelerate various spatial operations, such as the
 * Barnes-Hut approximation for computing n-body forces, or collision detection.
 * <p>
 * 
 * @see http://bl.ocks.org/mbostock/4343214#index.html
 * @see http://bl.ocks.org/mbostock/1804919
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Quadtree extends JavaScriptObject {

	protected Quadtree() {

	}

	/**
	 * Sets the x-coordinate accessor.
	 * <p>
	 * The default accessor consider datum as a two element array and returns
	 * the first element.
	 * 
	 * @param xAccessor
	 *            the x accessor
	 * @return the current Quadtree
	 */
	public final native Quadtree x(DatumFunction<Double> xAccessor)/*-{
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
	 * @return the current Quadtree
	 */

	public final native Quadtree y(DatumFunction<Double> yAccessor)/*-{
		return this
				.y(function(d, i) {
					return yAccessor.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
	}-*/;

	/**
	 * Constructs a new quadtree for the specified array of points.
	 * <p>
	 * The x and y coordinates of each point is determined using the default
	 * {@link #x(DatumFunction)} and {@link #y(DatumFunction)} accessors.
	 * <p>
	 * To build a quadtree by adding points incrementally, the specified points
	 * array can be empty, and points can be later added to the returned root
	 * node; in this case, you must also specify the {@link #extent()} of the
	 * quadtree.
	 * <p>
	 * Returns the root of the quadtree.
	 * 
	 * @param points
	 *            the points
	 * @return the root of the quadtree
	 */
	public native final <T> RootNode<T> apply(Array<T> points)/*-{
		return this(points);
	}-*/;

	/**
	 * Constructs a new quadtree for the specified list of points.
	 * <p>
	 * Returns the root of the quadtree.
	 * 
	 * @param points
	 *            the points
	 * @return the root of the quadtree
	 */
	public final <T> RootNode<T> apply(final List<T> points) {
		return this.apply(Array.fromIterable(points));
	}

	/**
	 * Returns the current extent, which defaults to null.
	 * 
	 * @return the current extent, which defaults to null.
	 */
	public final native Array<Array<Double>> extent()/*-{
		return this.extent();
	}-*/;

	/**
	 * If the specified extent is null, this causes the quadtree extent to be
	 * automatically computed for the initial array of points.
	 * <p>
	 * If the specified extent is a two-dimensional array [‍[ x0, y0 ], [ x1, y1
	 * ]], where x0 and y0 are the lower bounds of the extent, and x1 and y1 are
	 * the upper bounds of the extent, this sets the quadtree's extent.
	 * <p>
	 * 
	 * @param extent
	 *            null to compute the extent with the initial array of points,
	 *            or a 2-dim array [‍[ x0, y0 ], [ x1, y1 ]]
	 * @return the quadtree
	 */
	public final native Quadtree extent(Array<Array<Double>> extent)/*-{
		return this.extent(extent);
	}-*/;

	/**
	 * If the specified extent is a two-dimensional array [‍[ x0, y0 ], [ x1, y1
	 * ]], where x0 and y0 are the lower bounds of the extent, and x1 and y1 are
	 * the upper bounds of the extent, this sets the quadtree's extent.
	 * 
	 * @param x0
	 *            the lower x coordinate of the extent
	 * @param y0
	 *            the lower y coordinate of the extent
	 * @param x1
	 *            the upper x coordinate of the extent
	 * @param y1
	 *            the upper y coordinate of the extent
	 * @return the quadtree
	 */
	public final native Quadtree extent(double x0, double y0, double x1,
			double y1)/*-{
		return this.extent([‍[ x0, y0 ], [ x1, y1 ]]);
	}-*/;

	/**
	 * Node of the quadtree.
	 * <p>
	 * 
	 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
	 * 
	 * @param <T>
	 */
	public static class Node<T> extends JavaScriptObject {

		protected Node() {

		}

		/**
		 * @return the point associated with this node, if any (may apply to
		 *         either internal or leaf nodes)
		 */
		public native final T point()/*-{
			return this.point;
		}-*/;

		/**
		 * @return the x-coordinate of the associated point, if any
		 */
		public native final Double x()/*-{
			return this.x;
		}-*/;

		/**
		 * @return the y-coordinate of the associated point, if any
		 */
		public native final Double y()/*-{
			return this.y;
		}-*/;

		/**
		 * @return a boolean indicating whether this is a internal or leaf node
		 */
		public native final boolean leaf()/*-{
			return this.leaf;
		}-*/;

		/**
		 * @return a sparse array of the four child nodes in order: top-left,
		 *         top-right, bottom-left, bottom-right
		 */
		public native final Array<Node<T>> nodes()/*-{
			return this.nodes;
		}-*/;
	}

	/**
	 * The root node of the quadtree.
	 * <p>
	 * 
	 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
	 * 
	 * @param <T>
	 */
	public static class RootNode<T> extends Node<T> {
		protected RootNode() {

		}

		/**
		 * Adds a new point to the previously-computed quadtree.
		 * 
		 * @return this node
		 */
		public native final Quadtree add(T point)/*-{
			return this.add(point);
		}-*/;

		/**
		 * Call the given {@link Callback} for each quadtree node pre-order,
		 * provided the callback returns false.
		 * <p>
		 * If callback returns true for a node, then the children of that node
		 * are not visited.
		 * <p>
		 * 
		 * @param callback
		 *            the callback to use for visiting the nodes
		 * @return this node
		 */
		public native final RootNode<T> visit(Callback<T> callback)/*-{
			return this
					.visit(function(n, x1, y1, x2, y2) {
						return callback.@com.github.gwtd3.api.geom.Quadtree.Callback::visit(Lcom/github/gwtd3/api/geom/Quadtree$Node;DDDD)(n,x1,y1,x2,y2);
					});
		}-*/;
	}

	/**
	 * A callback to be passed to {@link RootNode#visit(Callback)}.
	 * <p>
	 * 
	 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
	 * 
	 * @param <T>
	 *            the type of the points objects.
	 */
	public static interface Callback<T> {

		/**
		 * Called for each node of the quadtree.
		 * <p>
		 * When true is returned for a node, then the children of that node are
		 * not visited.
		 * <p>
		 * 
		 * @param node
		 *            the visited node
		 * @param x1
		 *            the top left x coordinate
		 * @param y1
		 *            the top left y coordinate
		 * @param x2
		 *            the bottom right x coordinate
		 * @param y2
		 *            the bottom right y coordinate
		 * @return false if children should be visited, true otherwise
		 */
		public boolean visit(Node<T> node, double x1, double y1, double x2,
				double y2);
	}
}
