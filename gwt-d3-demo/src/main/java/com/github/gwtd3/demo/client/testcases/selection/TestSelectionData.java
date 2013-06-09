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
	public void doTest(ComplexPanel sandbox) {

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
			public String apply(Element context, Datum d, int index) {
				return d.asString();
			}
		});
		System.out.println(panel.toString());
		System.out.println("creating second level divs");
		// second level must be joined with a data function
		UpdateSelection secondLevel = secondLevelDiv.data(new NestedDatumFunction<Array<String>>() {
			@Override
			public Array<String> apply(Element context, Value datum, int index, int rowIndex, int squareIndex) {
				System.out.println(context + " " + datum.asString() + " " + index + " " + rowIndex + " " + squareIndex);
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
			public String apply(Element context, Datum d, int index) {
				return d.asString();
			}
		});
		System.out.println(panel.toString());

		System.out.println("mapping 3rd level divs to leaf data values");
		thirdLevelDiv.data(new NestedDatumFunction<String>() {
			@Override
			public String apply(Element context, Value datum, int index, int rowIndex, int squareIndex) {
				System.out.println(context + " " + datum.asString() + " " + index + " " + rowIndex + " " + squareIndex);
				return datum.asString();
			}
		});
		System.out.println(panel.toString());
		thirdLevelDiv.attr("name", new DatumFunction<String>() {
			@Override
			public String apply(Element context, Datum d, int index) {
				return d.asString();
			}
		});
		System.out.println(panel.toString());
	}
}
