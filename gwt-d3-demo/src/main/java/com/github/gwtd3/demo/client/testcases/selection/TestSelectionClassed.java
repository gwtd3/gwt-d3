package com.github.gwtd3.demo.client.testcases.selection;

import com.github.gwtd3.api.core.Datum;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.functions.DatumFunction;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * Testing
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class TestSelectionClassed extends AbstractSelectionTest {

	@Override
	public void doTest(final ComplexPanel sandbox) {
		testGetter();
		testSetterConstantString();
		testSetterFunction();

	}

	protected void testSetterFunction() {
		// 1. add
		// works with single selection
		Selection selection = givenASimpleSelection(createLabel(""));
		selection.classed("foo bar", new DatumFunction<Boolean>() {
			@Override
			public Boolean apply(final Element context, final Datum datum, final int index) {
				return true;
			}
		});
		assertEquals("foo bar", getElementClassAttribute(0));

		// works with multiple selection
		selection = givenAMultipleSelection(createLabel(""), createLabel(""), createLabel(""));
		selection.classed("foo bar", new DatumFunction<Boolean>() {
			@Override
			public Boolean apply(final Element context, final Datum datum, final int index) {
				return (index % 2) == 0;
			}
		});
		assertEquals("foo bar", getElementClassAttribute(0));
		assertEquals("", getElementClassAttribute(1));
		assertEquals("foo bar", getElementClassAttribute(2));

	}

	protected void testSetterConstantString() {
		// 1 test add
		// works with single selection
		Selection selection = givenASimpleSelection(createLabel(""));
		selection.classed("foo bar", true);
		assertEquals("foo bar", getElementClassAttribute(0));

		// works with multiple selection
		selection = givenAMultipleSelection(createLabel(""), createLabel(""));
		selection.classed("barry lindon", true);
		assertEquals("barry lindon", getElementClassAttribute(0));
		assertEquals("barry lindon", getElementClassAttribute(1));

		// 2 test remove
		selection = givenASimpleSelection(createLabel("foo bar"));
		selection.classed("foo", false);
		assertEquals("bar", getElementClassAttribute(0));

		selection = givenASimpleSelection(createLabel("foo bar"));
		selection.classed("foo bar", false);
		assertEquals("", getElementClassAttribute(0));

		selection = givenASimpleSelection(createLabel("foo bar"));
		selection.classed("bar foo", false);
		assertEquals("", getElementClassAttribute(0));

		// works with multiple selection
		Selection selection2 = givenAMultipleSelection(createLabel("foo bar"), createLabel("bar lindon"));
		selection2.classed("bar", false);
		assertEquals("foo", getElementClassAttribute(0));
		assertEquals("lindon", getElementClassAttribute(1));

	}

	protected void testGetter() {
		// with single selection
		Selection selection = givenASimpleSelection(createLabel("foo bar"));
		assertEquals(true, selection.classed("bar"));
		assertEquals(true, selection.classed("foo"));
		assertEquals(true, selection.classed("foo bar"));
		assertEquals(true, selection.classed("bar foo"));
		assertEquals(false, selection.classed("bary"));

		Selection multipleSelection = givenAMultipleSelection(createLabel("bary doe"), createLabel("foo bar"));
		assertEquals(true, multipleSelection.classed("doe"));
		assertEquals(true, multipleSelection.classed("bary"));
		assertEquals(true, multipleSelection.classed("doe bary"));
		assertEquals(true, multipleSelection.classed("bary doe"));
		assertEquals(false, multipleSelection.classed("foo"));

	}

	private Widget createLabel(final String className) {
		Label l = new Label();
		l.setStyleName(className);
		return l;
	}
}
