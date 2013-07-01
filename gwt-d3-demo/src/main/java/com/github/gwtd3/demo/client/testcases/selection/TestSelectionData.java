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

public class TestSelectionData extends AbstractSelectionTest {

	@Override
	public void doTest(final ComplexPanel sandbox) {

		// testNestedSelection();
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
