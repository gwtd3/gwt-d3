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
package com.github.gwtd3.api.layout;

import com.github.gwtd3.api.arrays.Array;

/**
 * The cluster layout produces dendrograms: node-link diagrams that place leaf
 * nodes of the tree at the same depth.
 * <p>
 * For example, a cluster layout can be used to organize software classes in a package hierarchy: <img
 * src="https://github.com/mbostock/d3/wiki/cluster.png"/>
 * <p>
 * Like other classes in D3, layouts follow the method chaining pattern where setter methods return the layout itself,
 * allowing multiple setters to be invoked in a concise statement.
 *
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 *
 */
public class Cluster<T> extends HierarchicalLayout<Cluster<T>, T, Cluster.Node<T>> {

    protected Cluster() {
    }

    /**
     * A node in the cluster layout.
     *
     * @param <T>
     */
    public static class Node<T>
            extends HierarchicalLayout.Node<T> {
        protected Node() {
        }

        /**
         * @return the computed x-coordinate of the node position.
         */
        public final native double x()/*-{
			return this.x;
        }-*/;

        /**
         * sets the x coordinate
         */
        public final native void x(double x)/*-{
			this.x = x;
        }-*/;

        /**
         * @return the computed y-coordinate of the node position.
         */
        public final native double y()/*-{
			return this.y;
        }-*/;

        /**
         * sets the y coordinate
         */
        public final native void y(double y)/*-{
			this.y = y;
        }-*/;
    }

    /**
     * Sets the available layout size. The layout size is specified in x and y, but this is not limited screen
     * coordinates and may represent an arbitrary coordinate system. For example, to produce a radial layout where the
     * tree breadth (x) is measured in degrees, and the tree depth (y) is a radius r in pixels, say [360, r].
     * <p>
     * The size property is exclusive with {@link #nodeSize()}: setting {@link #size()} sets {@link #nodeSize()} to
     * null.
     * <p>
     *
     * @param width
     * @param height
     * @return this layout for chaining
     */
    public final native Cluster<T> size(double width, double height) /*-{
		return this.size([ width, height ]);
    }-*/;

    /**
     * Returns the current layout size, which defaults to 1×1.
     * <p>
     * The size property is exclusive with {@link #nodeSize()}: setting {@link #nodeSize()} sets {@link #size()} to
     * null.
     * <p>
     *
     * @return a two-element array representing the current size of the layout, or null if nodeSize is specified
     */
    public final native Array<Double> size() /*-{
		return this.size();
    }-*/;

    /**
     * Sets a fixed size for each node.
     * <p>
     * The nodesize property is exclusive with {@link #size()}: setting {@link #nodeSize()} sets {@link #size()} to
     * null.
     * <p>
     *
     * @param width
     * @param height
     *
     * @return this layout for chaining
     */
    public final native Cluster<T> nodeSize(double width, double height) /*-{
		return this.nodeSize([ width, height ]);
    }-*/;

    /**
     * Returns the current node size, which defaults to null, meaning that the
     * layout has an overall fixed size, which can be retrieved using {@link #size()}.
     * <p>
     * The nodeSize property is exclusive with {@link #size()}: setting {@link #size()} sets {@link #nodeSize()} to
     * null.
     * <p>
     *
     * @return a two element array representing the current size of nodes
     *
     */
    public final native Array<Double> nodeSize() /*-{
		return this.nodeSize();
    }-*/;

    /**
     * Uses the specified function to compute separation between neighboring
     * nodes.
     * <p>
     * The separation function is passed two neighboring nodes a and b, and must return the desired separation between
     * nodes. The nodes are typically siblings, though the nodes may also be cousins (or even more distant relations) if
     * the layout decides to place such nodes adjacent.
     * <p>
     * The default separation function is:
     *
     * <pre>
     * <code>
     * function separation(a, b) {
     *   return a.parent == b.parent ? 1 : 2;
     * }
     * </code>
     * </pre>
     * <p>
     * A variation that is more appropriate for radial layouts reduces the separation gap proportionally to the radius:
     * *
     *
     * <pre>
     * <code>
     * function separation(a, b) {
     *   return (a.parent == b.parent ? 1 : 2) / a.depth;
     * }
     * </code>
     * </pre>
     *
     *
     * @return this layout for chaining
     */
    public final native Cluster<T> separation(SeparationFunction<Cluster.Node<T>> separation) /*-{
		return this
				.separation(function(a, b) {
					return separation.@com.github.gwtd3.api.layout.SeparationFunction::separation(*)(a,b);
				});
    }-*/;

}
