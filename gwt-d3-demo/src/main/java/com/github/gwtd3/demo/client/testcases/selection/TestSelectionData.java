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
import java.util.List;

import com.github.gwtd3.api.JsArrays;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.core.Datum;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.UpdateSelection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.functions.NestedDatumFunction;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;

public class TestSelectionData extends AbstractSelectionTest {

	@Override
	public void doTest(final ComplexPanel sandbox) {

		// testNestedSelection();
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
	private void testSelectionDatumSetterFunction() {
		// GIVEN a multiple selection
		Selection selection = givenAMultipleSelection(new Label(), new Label(), new Label());
		// WHEN I call selection.datum() with a function depending on the index
		selection.datum(new DatumFunction<Double>() {
			@Override
			public Double apply(final Element context, final Datum d, final int index) {
				return (index % 2) == 0 ? 5.0 : 2.0;
			}
		});
		// THEN each element has a the corresponding data
		selection.each(new DatumFunction<Void>() {
			@Override
			public Void apply(final Element context, final Datum d, final int index) {
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
			public Void apply(final Element context, final Datum d, final int index) {
				assertEquals("blah", d.asString());
				return null;
			}
		});
		// WHEN I call selection.datum() with a constant NULL
		selection.datum(null);
		// THEN all elements has a null data
		selection.each(new DatumFunction<Void>() {
			@Override
			public Void apply(final Element context, final Datum d, final int index) {
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
		assertEquals(3, selection.nodeCount());
		// WHEN i call filter(":nth-child(odd)")
		Selection filtered = selection.filter(new DatumFunction<Element>() {
			@Override
			public Element apply(final Element context, final Datum d, final int index) {
				return (index % 2) == 0 ? context : null;
			}
		});
		// THEN the returned selection contains 2 elements (css is 1-based index)
		assertEquals(2, filtered.nodeCount());
		assertEquals("0", filtered.node().getInnerText());
		assertEquals(3, selection.nodeCount());
	}

	/**
	 * 
	 */
	private void testSelectionFilterString() {
		// GIVEN a selection with n elements
		Selection selection = givenAMultipleSelection(new Label("0"), new Label("1"), new Label("2"));
		assertEquals(3, selection.nodeCount());
		// WHEN i call filter(":nth-child(odd)")
		Selection filtered = selection.filter(":nth-child(odd)");
		// THEN the returned selection contains 2 elements (css is 1-based index)
		assertEquals(2, filtered.nodeCount());
		assertEquals("0", filtered.node().getInnerText());
		assertEquals(3, selection.nodeCount());
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
		selection = selection.sort(new Comparator<Datum>() {
			@Override
			public int compare(final Datum o1, final Datum o2) {
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

	private void testNestedSelection() {
		HTMLPanel panel = new HTMLPanel("") {
			@Override
			public String toString() {
				return getElement().getInnerHTML();
			}
		};
		Selection firstLevel = givenASimpleSelection(new HTMLPanel(""));
		final List<String> list = Arrays.asList("A", "B", "C");
		// given a multi level selection
		// say:
		// an array
		Array<String> firstLevelData = JsArrays.asJsArray(list);
		Selection secondLevelDiv = firstLevel.selectAll("div").data(firstLevelData).enter().append("div").text(new DatumFunction<String>() {
			@Override
			public String apply(final Element context, final Datum d, final int index) {
				return d.asString();
			}
		});
		System.out.println(panel.toString());
		System.out.println("creating second level divs");
		// second level must be joined with a data function
		UpdateSelection secondLevel = secondLevelDiv.data(new NestedDatumFunction<Array<String>>() {
			@Override
			public Array<String> apply(final Element context, final Value datum, final int index, final int rowIndex) {
				System.out.println(context + " " + datum.asString() + " " + index + " " + rowIndex);
				return JsArrays.asJsArray(Arrays.asList("J", "K", "L"));
			}
		});
		System.out.println(panel.toString());
		// at this moment,
		// we have a root div,
		// containing 3 divs A B and C,
		// each of them being joined to a data list of J K L
		System.out.println("creating thirs level divs");
		Selection thirdLevelDiv = secondLevel.enter().append("div").text(new DatumFunction<String>() {
			@Override
			public String apply(final Element context, final Datum d, final int index) {
				return d.asString();
			}
		});
		System.out.println(panel.toString());

		System.out.println("mapping 3rd level divs to leaf data values");
		thirdLevelDiv.data(new NestedDatumFunction<String>() {
			@Override
			public String apply(final Element context, final Value datum, final int index, final int rowIndex) {
				System.out.println(context + " " + datum.asString() + " " + index + " " + rowIndex);
				return datum.asString();
			}
		});
		System.out.println(panel.toString());
		thirdLevelDiv.attr("name", new DatumFunction<String>() {
			@Override
			public String apply(final Element context, final Datum d, final int index) {
				return d.asString();
			}
		});
		System.out.println(panel.toString());
	}
}
