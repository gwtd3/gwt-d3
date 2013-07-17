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
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.CountFunction;
import com.github.gwtd3.api.functions.DatumFunction;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;

/**
 * Testing the internal structure of the selections and subselections.
 * 
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class TestSubselections extends AbstractSelectionTest {

	@Override
	public void doTest(final ComplexPanel sandbox) {
		testD3Select();
		testD3SelectAll();
		testD3SelectThenSelect();
		testD3SelectThenSelectAll();
		// testSelectAllSelectByString();
		// testSelectAllSelectAllByString();

		// testSelectSelectByFunction();
		// testSelectAllSelectByFunction();
	}

	protected void assertParentNodeIsRootHtml(final Selection s) {
		Element parentNode = s.parentNode(0);
		assertNotNull(parentNode);
		assertEquals("html", parentNode.getTagName().toLowerCase());

	}

	/**
	 * 
	 */
	private void testD3Select() {
		clearSandbox();
		Selection select = D3.select(sandbox);
		assertEquals(sandbox.getElement(), select.node());

		// internal structure
		Array<Array<Element>> elements = select.asElementArray();
		assertEquals(1, elements.length());
		assertEquals(1, elements.get(0).length());
		assertEquals(1, select.groupCount());
		assertParentNodeIsRootHtml(select);

		// appending returns a new selection with the same parent node (html)
		Selection child = select.append("blah");
		assertParentNodeIsRootHtml(child);
		assertEquals(1, child.size());
		assertEquals(1, D3.selectAll("blah").size());

	}

	/**
	 * 
	 */
	private void testD3SelectAll() {
		clearSandbox();

		sandbox.getElement().setInnerHTML(
				"<div><blah>foo</blah></div>" +
						"<div><blah>bar</blah></div>" +
						"<div><blah>zing</blah></div>"
				);

		Selection blahs = D3.selectAll("blah");

		// internal structure
		assertEquals(3, blahs.size());
		assertEquals(1, blahs.groupCount());
		assertEquals(1, blahs.asElementArray().length());
		assertEquals(3, blahs.asElementArray().get(0).length());

		// parentNode
		assertParentNodeIsRootHtml(blahs);

		// appending an element to the blah tags are easy
		Selection barfoos = blahs.append("barfoo");
		assertEquals(3, barfoos.size());
		assertEquals(1, barfoos.groupCount());
		assertEquals(1, barfoos.asElementArray().length());
		assertEquals(3, barfoos.asElementArray().get(0).length());
		assertParentNodeIsRootHtml(barfoos);

		// also test variant of selectAll
		Selection selection = D3.selectAll(sandbox.getElement().getChildNodes());
		assertEquals(3, selection.size());

		selection = D3.selectAll(sandbox.getElement().getChild(0).<Element> cast(),
				sandbox.getElement().getChild(1).<Element> cast(),
				sandbox.getElement().getChild(2).<Element> cast());
		assertEquals(3, selection.size());

	}

	/**
	 * 
	 */
	private void testD3SelectThenSelect() {
		clearSandbox();
		// given 3 DIV>SPAN elements in the sandbox
		clearSandbox();
		sandbox.getElement().setInnerHTML(
				"<div><zorg>foo</zorg><zorg>foo2</zorg></div>" +
						"<div><zorg>bar</zorg><zorg>bar2</zorg></div>" +
						"<div><zorg>zing</zorg><zorg>zing2</zorg></div>"
				);
		Selection sandboxSelection = D3.select(sandbox);

		// for each element in the current selection, select the first descendant matching the selector
		Selection divs = sandboxSelection.selectAll("div");
		Selection firstZorgs = sandboxSelection.select("zorg");
		assertEquals(1, firstZorgs.size());
		assertEquals(1, firstZorgs.groupCount());
		assertParentNodeIsRootHtml(firstZorgs);
		Array<Array<Element>> array = firstZorgs.asElementArray();
		assertEquals(1, array.length());
		assertEquals(1, array.get(0).length());
		// if multiple elements match the selector, only the first matching element in document traversal order will be selected.

		// if no element match for a element, the element at the current index will be null in the returned selection
		// a) operators skip null element , thereby preserving the index of the existing selection
		clearSandbox();
		sandbox.getElement().setInnerHTML(
				"<div></div>" +
						"<div></div>" +
						"<div><zorg>zing</zorg><zorg>zing2</zorg></div>"
				);
		sandboxSelection = D3.select(sandbox);
		divs = sandboxSelection.selectAll("div");
		firstZorgs = sandboxSelection.select("zorg");
		assertEquals(1, firstZorgs.size());
		assertEquals(1, firstZorgs.groupCount());
		assertParentNodeIsRootHtml(firstZorgs);

		CountFunction countFunction = new CountFunction();
		firstZorgs.each(countFunction.reset());
		assertEquals(1, countFunction.getCount());

		// if the current element has associated data, this data is inherited by the returned subselection and automatically bound to the newly selected elements
		clearSandbox();
		sandbox.getElement().setInnerHTML(
				"<div><zorg>foo</zorg><zorg>foo2</zorg></div>" +
						"<div><zorg>bar</zorg><zorg>bar2</zorg></div>" +
						"<div><zorg>zing</zorg><zorg>zing2</zorg></div>"
				);
		divs = D3.select(sandbox).selectAll("div");
		divs.asElementArray().get(0).get(0).setPropertyInt(Selection.DATA_PROPERTY, 6);
		divs.asElementArray().get(0).get(1).setPropertyInt(Selection.DATA_PROPERTY, 2);
		divs.asElementArray().get(0).get(2).setPropertyInt(Selection.DATA_PROPERTY, 4);
		Selection select = divs.select("zorg");
		Array<Object> data = select.data();
		assertEquals(6.0, data.getNumber(0));
		assertEquals(2.0, data.getNumber(1));
		assertEquals(4.0, data.getNumber(2));

		// select as a function returning element or null

		// select by function
		Selection selection = sandboxSelection.select(new DatumFunction<Element>() {
			@Override
			public Element apply(final Element context, final Value d, final int index) {
				// maybe it can be any node ??? even a non child node
				return Document.get().getBody();
			}
		});
		assertEquals(1, selection.size());
		assertEquals(1, selection.groupCount());
		assertEquals(Document.get().getBody(), selection.node());
	}

	/**
	 * 
	 */
	private void testD3SelectThenSelectAll() {
		clearSandbox();
		// given 3 DIV elements in the sandbox
		sandbox.getElement().setInnerHTML(
				"<div><span></span></div>" +
						"<div><span></span></div>" +
						"<div><span></span></div>"
				);
		//
		assertEquals(3, sandbox.getElement().getChildCount());
		Selection spans = D3.select(sandbox).selectAll("span");
		assertEquals(3, spans.size());
		assertEquals(1, spans.groupCount());
		assertEquals(sandbox.getElement(), spans.parentNode(0));
		// appending a node append it to each span nodes, and the parent node of the new selection is the same as before
		Selection appended = spans.append("foobar");
		assertEquals(3, appended.size());
		assertEquals(1, appended.groupCount());
		assertEquals(sandbox.getElement(), appended.parentNode(0));

		// where does the new nodes have been appended ? in the span ? or in the sanbox ?
		assertEquals(3, sandbox.getElement().getChildCount());
		// it seems in the span elements
		D3.select(sandbox).selectAll("span").each(new DatumFunction<Void>() {
			@Override
			public Void apply(final Element context, final Value d, final int index) {
				assertEquals(1, context.getChildCount());
				return null;
			}
		});

	}

	private void testSelectAllSelectByString() {
		clearSandbox();
		// given 3 DIV>SPAN elements in the sandbox
		sandbox.getElement().setInnerHTML(
				"<div><span>foo</span></div>" +
						"<div><span>bar</span></div>" +
						"<div><span>zing</span></div>"
				);
		// when selecting the sandbox then selecting the div
		// I got only 1 node in the selection
		// Selection sandboxSelection = D3.select(sandbox);
		// assertEquals(1, sandboxSelection.count());
		Selection divs = D3.select(sandbox).selectAll("div");
		assertEquals(3, divs.size());
		Selection spans = divs.select("span");
		assertEquals(3, spans.size());
 
	}

}
