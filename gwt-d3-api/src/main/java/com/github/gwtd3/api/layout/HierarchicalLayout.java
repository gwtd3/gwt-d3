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

import java.util.List;

import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.svg.Diagonal;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;

public class HierarchicalLayout<L, T> extends JavaScriptObject {
    /**
     * A node in d3j's tree layout, see <a
     * href="https://github.com/mbostock/d3/wiki/Tree-Layout#wiki-nodes">d3 docs on
     * node</a>. The node class is used in {@link Tree}, cluster, and
     * partitions. The class provides accessors for the nodes key attributes, its
     * position, children, parent, and depth.
     *
     * @author <a href="mailto:evanshi09@gmail.com">Evan Shi</a>
     *
     */
    public static class Node<T>
            extends com.github.gwtd3.api.layout.Node<T> {

        /**
         * Create a new node containing the given user datum
         *
         * @param userDatum
         * @return
         */
        static final native <T> Node<T> create(T userDatum)/*-{
			return {
				datum : userDatum
			};
        }-*/;

        protected Node() {
            super();
        }

        /**
         * @return array of {@link Node} objects or null
         */
        public final native Array<Node<T>> children() /*-{
			return this.children;
        }-*/;

        /**
         * @return parent node
         */
        public final native Node<T> parent() /*-{
			return this.parent;
        }-*/;

        /**
         * @return the node's depth, root depth = 0
         */
        public final native int depth() /*-{
			return this.depth;
        }-*/;

    }

    protected HierarchicalLayout() {
    }

    /**
     * Runs the tree layout, returning the array of nodes associated with the
     * specified root node. The tree layout is part of D3's family of
     * hierarchical layouts. These layouts follow the same basic structure: the
     * input argument to layout is the root node of the hierarchy, and the
     * output return value is an array representing the computed positions of
     * all nodes. Several attributes are populated on each node:
     *
     * <ul>
     * <li>parent - the parent {@link Node}, or null for the root.
     * <li>children - the array of child nodes, or null for leaf nodes.
     * <li>depth - the depth of the node, starting at 0 for the root.
     * <li>x - the computed x-coordinate of the node position.
     * <li>y - the computed y-coordinate of the node position.
     * </ul>
     *
     * Although the layout has a size in x and y, this represents an arbitrary
     * coordinate system; for example, you can treat x as a radius and y as an
     * angle to produce a radial rather than Cartesian layout.
     *
     * @param root of the the tree
     * @return array of {@link Node} in the tree stemming from root
     */
    public final native Array<Node<T>> nodes(T data) /*-{
		return this.nodes({
			datum : data
		});
    }-*/;

    /**
     * Given the specified array of nodes, such as those returned by nodes,
     * returns an array of objects representing the from parent to child for
     * each node. Leaf nodes will not have any links. Each link is an object
     * with two attributes:
     *
     * <ul>
     * <li>source - the parent node (as described above).
     * <li>target - the child node.
     * </ul>
     *
     * This method is useful for retrieving a set of link descriptions suitable
     * for display, often in conjunction with the {@link Diagonal} shape
     * generator.
     *
     * @param array of nodes in tree
     * @return array of {@link Link} connecting nodes
     */
    public final native Array<Link<T>> links(Array<Node<T>> n) /*-{
		return this.links(n);
    }-*/;

    /**
     * Sets the specified children accessor function. The default children
     * accessor function assumes the input data is an object with a children
     * array. The children accessor is first invoked for root node in the
     * hierarchy. If the accessor returns null, then the node is assumed to be a
     * leaf node at the layout traversal terminates. Otherwise, the accessor
     * should return an array of data elements representing the child nodes.
     *
     * @param df a datum function describing how to compute children
     * @return this tree object
     */
    public final L children(final DatumFunction<List<T>> df) {
        return childrenImpl(new DatumFunction<Array<Node<T>>>() {
            @Override
            public Array<Node<T>> apply(final Element context, final Value d,
                    final int index) {
                // wrap the list of user values into an array of Nodes
                List<T> userValues = df.apply(context, d, index);
                Array<Node<T>> nodes = Array.create();
                for (T t : userValues) {
                    nodes.push(Node.create(t));
                }
                return nodes;
            }
        });

    }

    private final native L childrenImpl(DatumFunction<Array<Node<T>>> df) /*-{
		return this
				.children(function(node, depth) {
					return df.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:node.datum},depth);
				});
    }-*/;

    /**
     * Sets the value accessor to the specified function. The value accessor is
     * invoked for each input data element, and must return a number
     * representing the numeric value of the node.
     * <p>
     * For area-proportional layouts such as treemaps, this value is used to set the area of each node proportionally to
     * the value; for other hierarchical layouts, the value has no effect on the layout.
     *
     * @param df a datum function describing how to access node values
     * @return this tree object
     */
    public final native L value(DatumFunction<Integer> df) /*-{
		return this
				.value(function(d, i) {
					return df.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
    }-*/;

    /**
     * Returns the current value accessor which defaults to null, meaning that
     * the value attribute is not computed.
     *
     * @return the current datum function registered for calculating node values
     */
    public final native DatumFunction<Integer> value() /*-{
		return this.value();
    }-*/;
}
