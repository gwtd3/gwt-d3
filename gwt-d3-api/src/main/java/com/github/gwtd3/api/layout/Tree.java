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
 * Per the <a href="https://github.com/mbostock/d3/wiki/Tree-Layout">d3 API
 * reference</a>, the tree layout produces tidy node-link diagrams of trees
 * using the Reingold–Tilford “tidy” algorithm.
 *
 * <p>
 * Like most other layouts, the object returned by d3.layout.tree is both an object and a function. That is: you can
 * call the layout like any other function, and the layout has additional methods that change its behavior. Like other
 * classes in D3, layouts follow the method chaining pattern where setter methods return the layout itself, allowing
 * multiple setters to be invoked in a concise statement.
 * <p>
 *
 *
 */
public class Tree<T> extends HierarchicalLayout<Tree<T>, T, Tree.Node<T>> {

    /**
     * A node in the tree layout.
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

    protected Tree() {
        super();
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
    public final native Tree<T> size(double width, double height) /*-{
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
    public final native Tree<T> nodeSize(double width, double height) /*-{
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
    public final native Tree<T> separation(SeparationFunction<Tree.Node<T>> separation) /*-{
		return this
				.separation(function(a, b) {
					return separation.@com.github.gwtd3.api.layout.SeparationFunction::separation(*)(a,b);
				});
    }-*/;

}
