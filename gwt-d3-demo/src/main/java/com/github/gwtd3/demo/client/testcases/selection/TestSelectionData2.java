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

import java.util.Arrays;
import java.util.Comparator;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.UpdateSelection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.functions.KeyFunction;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Label;

public class TestSelectionData2 extends AbstractSelectionTest {

	@Override
	public void doTest(final ComplexPanel sandbox) {

		// testSelectionExit();
		testSelectionDatumGetter();
		testSelectionDatumSetterConstant();
		testSelectionDatumSetterFunction();
		testSelectionFilterString();
		testSelectionFilterFunction();
		testSelectionSort();
		testSelectionOrder();
	}

	/**
	 * 
	 */
	private void testSelectionExit() {
		clearSandbox();
		// GIVEN 6 divs elements with their textContents filled with the fibonacci numbers
		// AND joined to the same data
		int[] data = new int[] { 1, 2, 3, 5, 8, 13 };
		Selection sandBoxSelection = D3.select(sandbox);
		Selection dataJoinedSelection = sandBoxSelection.selectAll("div").data(data).enter().append("div")
				.attr("blah", new DatumFunction<String>() {
					@Override
					public String apply(final Element context, final Value d, final int index) {
						return d.asString();
					}
				});
		assertEquals(6, dataJoinedSelection.size());
		// WHEN I put new data in the current selection
		int[] newData = new int[] { 1, 2, 3, 4, 5, 6 };
		// AND I join the new data on the elements with their old data
		UpdateSelection updateSelection = dataJoinedSelection.data(Arrays.asList(newData), new KeyFunction<Integer>() {
			@Override
			public Integer map(final Element context, final Array<?> newDataArray, final Value d, final int index) {
				System.out.println("yo");
				if (context != null) {
					System.out.println("appending div " + d.asInt() + " blah: " + context.getAttribute("blah") + " index: " + index);
				}
				else {
					System.out.println("appending div " + d.asInt() + " blah: " + d + " index: " + index);
				}

				return d.asInt();
			}
		});
		Selection newElements = updateSelection.enter().append("div");
		assertEquals(2, newElements.size());
		// AND I remove the exit selection
		Selection removedSelection = updateSelection.exit().remove();
		// THEN The dom contains only the elements corresponding to the intersection of old and new data
		Selection remaining = D3.select(sandbox).selectAll("div");
		assertEquals(4, remaining.size());
		remaining.each(new DatumFunction<Void>() {
			private int i = 1;

			@Override
			public Void apply(final Element context, final Value d, final int index) {
				assertEquals(i, d.asInt());
				i++;
				return null;
			}
		});

	}

	/**
	 * 
	 */
	private void testSelectionDatumSetterFunction() {
		// GIVEN a multiple selection
		Selection selection = givenAMultipleSelection(new Label(), new Label(), new Label());
		// WHEN I call selection.datum() with a function depending on the index
		selection.datum(new DatumFunction<Double>() {
			@Override
			public Double apply(final Element context, final Value d, final int index) {
				return (index % 2) == 0 ? 5.0 : 2.0;
			}
		});
		// THEN each element has a the corresponding data
		selection.each(new DatumFunction<Void>() {
			@Override
			public Void apply(final Element context, final Value d, final int index) {
				assertEquals(((index % 2) == 0) ? 5.0 : 2.0, d.asDouble());
				return null;
			}
		});
	}

	/**
	 * 
	 */
	private void testSelectionDatumSetterConstant() {
		// GIVEN a multiple selection
		Selection selection = givenAMultipleSelection(new Label(), new Label(), new Label());
		// WHEN I call selection.datum() with a constant "blah"
		selection.datum("blah");
		// THEN all data has a datum of "blah"
		selection.each(new DatumFunction<Void>() {
			@Override
			public Void apply(final Element context, final Value d, final int index) {
				assertEquals("blah", d.asString());
				return null;
			}
		});
		// WHEN I call selection.datum() with a constant NULL
		selection.datum(null);
		// THEN all elements has a null data
		selection.each(new DatumFunction<Void>() {
			@Override
			public Void apply(final Element context, final Value d, final int index) {
				assertNull(d.asString());
				return null;
			}
		});
	}

	/**
	 * 
	 */
	private void testSelectionDatumGetter() {
		// GIVEN a multiple selection with data join
		Selection selection = givenAMultipleSelection(new Label(), new Label(), new Label());
		selection.data(Arrays.asList("54", "2", "10"));
		// WHEN I call selection.datum()
		Value v = selection.datum();
		// THEN I get the datum of the first non null elemenet
		assertEquals("54", v.asString());

	}

	/**
	 * 
	 */
	private void testSelectionFilterFunction() {
		// GIVEN a selection with n elements
		Selection selection = givenAMultipleSelection(new Label("0"), new Label("1"), new Label("2"));
		assertEquals(3, selection.size());
		// WHEN i call filter(":nth-child(odd)")
		Selection filtered = selection.filter(new DatumFunction<Element>() {
			@Override
			public Element apply(final Element context, final Value d, final int index) {
				return (index % 2) == 0 ? context : null;
			}
		});
		// THEN the returned selection contains 2 elements (css is 1-based index)
		assertEquals(2, filtered.size());
		assertEquals("0", filtered.node().getInnerText());
		assertEquals(3, selection.size());
	}

	/**
	 * 
	 */
	private void testSelectionFilterString() {
		// GIVEN a selection with n elements
		Selection selection = givenAMultipleSelection(new Label("0"), new Label("1"), new Label("2"));
		assertEquals(3, selection.size());
		// WHEN i call filter(":nth-child(odd)")
		Selection filtered = selection.filter(":nth-child(odd)");
		// THEN the returned selection contains 2 elements (css is 1-based index)
		assertEquals(2, filtered.size());
		assertEquals("0", filtered.node().getInnerText());
		assertEquals(3, selection.size());
	}

	/**
	 * 
	 */
	private void testSelectionSort() {
		// GIVEN a selection with elements ordered differently than in the DOM
		Label l1 = new Label("blah2"), l2 = new Label("blah1");
		Selection selection = givenAMultipleSelection(l1, l2);
		assertEquals("blah2", selection.asElementArray().get(0).get(0).getInnerText());
		assertEquals("blah1", selection.asElementArray().get(0).get(1).getInnerText());
		assertEquals("blah2", ((Element) sandbox.getElement().getChild(0)).getInnerText());
		assertEquals("blah1", ((Element) sandbox.getElement().getChild(1)).getInnerText());
		// bind integers 1 and 2 on the elements
		selection.data(Arrays.asList(2, 1));
		// WHEN calling selection.order
		selection = selection.sort(new Comparator<Value>() {
			@Override
			public int compare(final Value o1, final Value o2) {
				Integer d1 = o1.<Integer> as();
				Integer d2 = o2.<Integer> as();
				System.out.println("sorting: " + d1 + " " + d2);
				return d1.compareTo(d2);
			}
		});
		// THEN the elements are reordered in the DOM in the order of the selection
		assertEquals("blah1", selection.asElementArray().get(0).get(0).getInnerText());
		assertEquals("blah2", selection.asElementArray().get(0).get(1).getInnerText());
		assertEquals("blah1", ((Element) sandbox.getElement().getChild(0)).getInnerText());
		assertEquals("blah2", ((Element) sandbox.getElement().getChild(1)).getInnerText());
	}

	/**
	 * 
	 */
	private void testSelectionOrder() {
		// GIVEN a selection with elements ordered differently than in the DOM
		Label l1 = new Label("blah1"), l2 = new Label("blah2");
		Selection selection = givenAMultipleSelection(l1, l2);
		sandbox.remove(l1);
		sandbox.add(l1);
		assertEquals("blah1", selection.asElementArray().get(0).get(0).getInnerText());
		assertEquals("blah2", selection.asElementArray().get(0).get(1).getInnerText());
		assertEquals("blah2", ((Element) sandbox.getElement().getChild(0)).getInnerText());
		assertEquals("blah1", ((Element) sandbox.getElement().getChild(1)).getInnerText());
		// WHEN calling selection.order
		selection.order();
		// THEN the elements are reordered in the DOM in the order of the selection
		assertEquals("blah1", selection.asElementArray().get(0).get(0).getInnerText());
		assertEquals("blah2", selection.asElementArray().get(0).get(1).getInnerText());
		assertEquals("blah1", ((Element) sandbox.getElement().getChild(0)).getInnerText());
		assertEquals("blah2", ((Element) sandbox.getElement().getChild(1)).getInnerText());

	}

}
