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
package com.github.gwtd3.demo.client.testcases.geom;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.geom.Quadtree;
import com.github.gwtd3.api.geom.Quadtree.Callback;
import com.github.gwtd3.api.geom.Quadtree.Node;
import com.github.gwtd3.api.geom.Quadtree.RootNode;
import com.github.gwtd3.demo.client.test.AbstractTestCase;
import com.google.gwt.user.client.ui.ComplexPanel;

public class TestQuadTree extends AbstractTestCase {

	@Override
	public void doTest(ComplexPanel sandbox) {

		// test empty constructor with default accessors
		@SuppressWarnings("unchecked")
		Array<Array<Double>> points = Array.<Array<Double>> fromObjects(
				Array.fromDoubles(0, 0), Array.fromDoubles(5, 0),
				Array.fromDoubles(0, 5), Array.fromDoubles(5, 5));
		// RootNode<Array<Double>> node = D3.geom().quadtree(points);
		// RootNode<Array<Double>> node = D3.geom().quadtree(points);
		// Window.alert(node.toString());
		// Quadtree qt = node.cast();
		Quadtree qt = D3.geom().quadtree();
		RootNode<Array<Double>> rootnode = qt.apply(points);

		// add method is correct
		// RootNode<Array<Double>> node = qt.apply(Array.<Array<Double>>
		// create());
		// node.add(Array.fromDoubles(0, 0));
		// node.add(Array.fromDoubles(5, 0));
		// node.add(Array.fromDoubles(0, 5));
		// node.add(Array.fromDoubles(5, 5));

		// System.out.println();
		// assertEquals(0, qt.extent().get(0).get(0));
		// assertEquals(0, qt.extent().get(0).get(1));
		// assertEquals(5, qt.extent().get(1).get(0));
		// assertEquals(5, qt.extent().get(1).get(1));

		// check root node internal state
		assertFalse(rootnode.leaf());
		assertNull(rootnode.x());
		assertNull(rootnode.y());
		assertNull(rootnode.point());
		assertEquals(4, rootnode.nodes().length());

		// rootnode.add(Array.fromDoubles(7.0, 5.9));
		//
		// assertFalse(rootnode.leaf());
		// assertNull(rootnode.x());
		// assertNull(rootnode.y());
		// assertNull(rootnode.point());
		// assertEquals(5, rootnode.nodes().length());

		// visit: should be called 5 times
		rootnode.visit(new Callback<Array<Double>>() {
			@Override
			public boolean visit(Node<Array<Double>> node, double x1,
					double y1, double x2, double y2) {
				System.out.println(x1 + " " + y1 + " " + x2 + " " + y2);
				return false;
			}
		});

	}
}
