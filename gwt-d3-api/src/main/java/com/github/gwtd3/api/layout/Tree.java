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

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.Sort;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.functions.DatumFunction;

/**
 * Per the <a href="https://github.com/mbostock/d3/wiki/Tree-Layout">d3 API
 * reference</a>, the tree layout produces tidy node-link diagrams of trees
 * using the Reingold–Tilford “tidy” algorithm. Like most other layouts, the
 * object returned by d3.layout.tree is both an object and a function. That
 * is: you can call the layout like any other function, and the layout has
 * additional methods that change its behavior. Like other classes in D3,
 * layouts follow the method chaining pattern where setter methods return the
 * layout itself, allowing multiple setters to be invoked in a concise
 * statement.
 * 
 * @author <a href="mailto:evanshi09@gmail.com">Evan Shi</a>
 * 
 */
public class Tree extends HierarchicalLayout{
	protected Tree() {
		super();
	}


	/**
	 * Sets the available layout size to the specified two-element array of
	 * numbers representing x and y.
	 * 
	 * @param a two-element array of width and height of tree
	 * @return this tree object
	 */
	public final native Tree size(double width, double height) /*-{
		return this.size([ width, height ]);
    }-*/;

	/**
	 * Returns the current tree size, which defaults to 1×1. Although the layout
	 * has a size in x and y, this represents an arbitrary coordinate system.
	 * For example, to produce a radial layout where the tree breadth (*x*) is
	 * measured in degrees, and the tree depth (*y*) is a radius r in pixels,
	 * say [360, r].
	 * 
	 * @return a two-element array representing the current size of the tree
	 */
	public final native Array<Double> size() /*-{
		return this.size();
    }-*/;

	/**
	 * Sets a fixed size for each node as a two-element array of numbers
	 * representing x and y.
	 * 
	 * @param width
	 * @param height
	 * 
	 * @return this tree layout
	 */
	public final native Tree nodeSize(double width, double height) /*-{
        return this.nodeSize([ width, height ]);
    }-*/;

	/**
	 * Returns the current node size, which defaults to null, meaning that the
	 * layout has an overall fixed size, which
	 * can be retrieved using {@link #size()}.
	 * 
	 * @return a two element array representing the current size of nodes in the
	 * tree
	 */
	public final native Array<Double> nodeSize() /*-{
        return this.nodeSize();
    }-*/;

	/**
	 * Sets the sort order of sibling nodes for the layout using the specified
	 * comparator function. The comparator function is invoked for pairs of
	 * nodes, being passed the input data for each node. The default comparator
	 * is null, which disables sorting and uses tree traversal order. Sorting by
	 * the node's name or key is common and can be done easily using
	 * {@link D3#ascending()} or {@link D3#descending()}.
	 * 
	 * @param {@link Sort} a predefined sorting convention
	 * @return this tree object
	 */
	public final native Tree sort(Sort sort) /*-{
		return this.sort(sort);
    }-*/;

	/**
	 * Uses the specified function to compute separation between neighboring
	 * nodes. The separation function is passed two neighboring nodes a and b,
	 * and must return the desired separation between nodes. The nodes are
	 * typically siblings, though the nodes may also be cousins (or even more
	 * distant relations) if the layout decides to place such nodes adjacent.
	 * See <a
	 * href="https://github.com/mbostock/d3/wiki/Tree-Layout#wiki-separation"
	 * >wiki</a> for examples.
	 * 
	 * @param df a datum function describing how to calculate separation of
	 * nodes
	 * @return this tree object
	 */
	public final native Tree separation(Sort sort) /*-{
		return this.separation(sort);
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
	public final native Tree children(DatumFunction<Array<Node>> df) /*-{
		return this
				.children(function(d) {
					return df.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},0);
				});
    }-*/;

	/**
	 * Sets the value accessor to the specified function. The value accessor is
	 * invoked for each input data element, and must return a number
	 * representing the numeric value of the node. This value has no effect on
	 * the tree layout, but is generic functionality provided by hierarchy
	 * layouts.
	 * 
	 * @param df a datum function describing how to access node values
	 * @return this tree object
	 */
	public final native Tree value(DatumFunction<?> df) /*-{
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
	public final native DatumFunction<?> value() /*-{
		return this.value();
    }-*/;
}
