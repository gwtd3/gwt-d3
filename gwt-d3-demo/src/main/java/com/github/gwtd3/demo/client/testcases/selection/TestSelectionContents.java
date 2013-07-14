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
package com.github.gwtd3.demo.client.testcases.selection;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.core.Selection;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Label;

public class TestSelectionContents extends AbstractSelectionTest {

	@Override
	public void doTest(final ComplexPanel sandbox) {
		testRemove();
		testInsert();
		testAppend();
	}

	/**
	 * 
	 */
	private void testRemove() {
		clearSandbox();
		Selection selection = givenAMultipleSelection(new Label(), new Label(), new Label());
		assertEquals(3, sandbox.getWidgetCount());
		// we gonna insert sub elements into the divs
		Selection insertedElements = selection.insert("blah", "*");
		assertEquals(3, insertedElements.size());
		assertEquals(1, selection.node().getChildCount());
		insertedElements.remove();
		// there is still off screen elements in the selection
		assertEquals(3, insertedElements.size());
		assertEquals(0, selection.node().getChildCount());

		// GIVEN 3 divs, the last div is a blahblah class
		clearSandbox();
		for (int i = 0; i < 3; i++) {
			selection = D3.select(sandbox).append("div");
		}
		selection.classed("blahblah", true);
		assertEquals(1, selection.size());
		assertEquals(3, sandbox.getElement().getChildCount());
		// WHEN I remove the blahblah div from the selection
		selection = D3.select(sandbox).selectAll(".blahblah");
		selection.remove();
		// THEN it remove from the DOM
		assertEquals(2, sandbox.getElement().getChildCount());
		// BUT remains in the selection
		assertEquals(1, selection.size());
	}

	/** 
	 * 
	 */
	private void testInsert() {
		Selection selection = givenAMultipleSelection(new Label());
		assertEquals(1, selection.size());
		assertEquals(0, selection.node().getChildCount());
		assertEquals("div", selection.node().getTagName().toLowerCase());
		Selection insertedElements = selection.insert("blah", "*");
		assertEquals(1, insertedElements.size());
		// the inserted blah nodes has been inserted into the labels div
		// insert before unexisting element => idem to append
		assertEquals(selection.node(), insertedElements.node().getParentElement());

		// insert a span before the existing divs
		Selection spans = selection.insert("span", "blah");
		assertEquals("span", spans.node().getTagName().toLowerCase());
		assertEquals(2, selection.node().getChildCount());
	}

	/** 
	 * 
	 */
	private void testAppend() {
		Selection selection = givenAMultipleSelection(new Label(), new Label());
		assertEquals(2, selection.size());
		assertEquals(sandbox.getElement(), selection.node().getParentElement());
		Selection selection2 = selection.append("div");
		assertEquals(2, selection.size());
		assertNotNull(selection.node());
		assertNotNull(selection2.node());
		assertEquals(selection.node(), selection2.node().getParentElement());
		assertEquals(2, selection2.size());// returned selection contains only the appended nodes
		//
		assertEquals("div", selection2.node().getTagName().toLowerCase());

		// insert before the existing divs
		Selection selection3 = selection2.append("span");
		assertEquals(2, selection.size());
		assertEquals(2, selection2.size());
		assertEquals(2, selection3.size());
		assertNotNull(selection.node());
		assertEquals("span", selection3.node().getTagName().toLowerCase());
		assertEquals(selection2.node(), selection3.node().getParentElement());
	}

}
