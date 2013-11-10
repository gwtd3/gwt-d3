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
import com.github.gwtd3.api.svg.Diagonal;
import com.google.gwt.core.client.JavaScriptObject;

public class HierarchicalLayout extends JavaScriptObject{

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
	public final native Array<Node> nodes(Node r) /*-{
		return this.nodes(r);
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
	public final native Array<Link> links(Array<Node> n) /*-{
		return this.links(n);
    }-*/;
}
