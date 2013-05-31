package com.github.gwtd3.demo.client.testcases.selection;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.core.Datum;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.svg.Arc;
import com.github.gwtd3.api.svg.PathDataGenerator;
import com.github.gwtd3.demo.client.test.AbstractTestCase;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class TestSelectionAttr extends AbstractSelectionTest {

	private static final String ATTRIBUTE = "myattr";

	@Override
	public void doTest(final ComplexPanel sandbox) {
		testGetter();
		testSetterConstantBoolean();
		testSetterConstantDouble();
		testSetterConstantString();
		testSetterPathDataGenerator();
		testSetterFunction();

	}

	protected void testSetterFunction() {
		// works with single selection
		Selection selection = givenASimpleSelection(new Label());
		final String value = "1";
		selection.attr(TestSelectionAttr.ATTRIBUTE, new DatumFunction<String>() {
			@Override
			public String apply(final Element context, final Datum datum, final int index) {
				return value;
			}
		});
		assertEquals(value, getElementAttribute(0, TestSelectionAttr.ATTRIBUTE));

		// works with multiple selection
		Selection selection2 = givenAMultipleSelection(new Label(), new Label(), new Label());
		selection2.attr(TestSelectionAttr.ATTRIBUTE, new DatumFunction<String>() {
			@Override
			public String apply(final Element context, final Datum datum, final int index) {
				return value;
			}
		});
		assertEquals(value, getElementAttribute(0, TestSelectionAttr.ATTRIBUTE));
		assertEquals(value, getElementAttribute(1, TestSelectionAttr.ATTRIBUTE));
		assertEquals(value, getElementAttribute(2, TestSelectionAttr.ATTRIBUTE));

	}

	protected void testSetterConstantString() {
		// works with single selection
		Selection selection = givenASimpleSelection(new Label());
		String value = "1";
		selection.attr(TestSelectionAttr.ATTRIBUTE, value);
		assertEquals(value, getElementAttribute(0, TestSelectionAttr.ATTRIBUTE));

		// works with multiple selection
		Selection selection2 = givenAMultipleSelection(new Label(), new Label(), new Label());
		selection2.attr(TestSelectionAttr.ATTRIBUTE, value);
		assertEquals(value, getElementAttribute(0, TestSelectionAttr.ATTRIBUTE));
		assertEquals(value, getElementAttribute(1, TestSelectionAttr.ATTRIBUTE));
		assertEquals(value, getElementAttribute(2, TestSelectionAttr.ATTRIBUTE));

	}

	protected void testSetterConstantDouble() {
		// works with single selection
		Selection selection = givenASimpleSelection(new Label());
		double value = 3.56;
		selection.attr(TestSelectionAttr.ATTRIBUTE, value);
		assertEquals("3.56", getElementAttribute(0, TestSelectionAttr.ATTRIBUTE));

		// works with multiple selection
		Selection selection2 = givenAMultipleSelection(new Label(), new Label(), new Label());
		selection2.attr(TestSelectionAttr.ATTRIBUTE, value);
		assertEquals("3.56", getElementAttribute(0, TestSelectionAttr.ATTRIBUTE));
		assertEquals("3.56", getElementAttribute(1, TestSelectionAttr.ATTRIBUTE));
		assertEquals("3.56", getElementAttribute(2, TestSelectionAttr.ATTRIBUTE));
	}

	protected void testSetterConstantBoolean() {
		boolean value = true;
		String expectedValue = "true";
		// works with single selection
		Selection selection = givenASimpleSelection(new Label());
		selection.attr(TestSelectionAttr.ATTRIBUTE, value);
		assertEquals(expectedValue, getElementAttribute(0, TestSelectionAttr.ATTRIBUTE));

		// works with multiple selection
		Selection selection2 = givenAMultipleSelection(new Label(), new Label(), new Label());
		selection2.attr(TestSelectionAttr.ATTRIBUTE, value);
		assertEquals(expectedValue, getElementAttribute(0, TestSelectionAttr.ATTRIBUTE));
		assertEquals(expectedValue, getElementAttribute(1, TestSelectionAttr.ATTRIBUTE));
		assertEquals(expectedValue, getElementAttribute(2, TestSelectionAttr.ATTRIBUTE));
	}

	protected void testSetterPathDataGenerator() {
		PathDataGenerator generator = D3.svg().arc().innerRadius(1).outerRadius(2).startAngle(0).endAngle(2);
		// works with single selection
		Selection selection = givenASimpleSelection(new Label());
		selection.attr(TestSelectionAttr.ATTRIBUTE, generator);
		String expectedValue = generator.generate(JavaScriptObject.createArray());
		assertEquals(expectedValue, getElementAttribute(0, TestSelectionAttr.ATTRIBUTE));

		// works with multiple selection
		Selection selection2 = givenAMultipleSelection(new Label(), new Label(), new Label());
		selection2.attr(TestSelectionAttr.ATTRIBUTE, generator);
		assertEquals(expectedValue, getElementAttribute(0, TestSelectionAttr.ATTRIBUTE));
		assertEquals(expectedValue, getElementAttribute(1, TestSelectionAttr.ATTRIBUTE));
		assertEquals(expectedValue, getElementAttribute(2, TestSelectionAttr.ATTRIBUTE));
	}

	protected void testGetter() {
		// with single selection
		Label label = new Label();
		label.getElement().setAttribute(TestSelectionAttr.ATTRIBUTE, "foo");
		Selection selection = givenASimpleSelection(label);
		assertEquals("foo", selection.attr(TestSelectionAttr.ATTRIBUTE));

		// with multiple selection, should return the first element
		Selection selection2 = givenAMultipleSelection(createLabel(TestSelectionAttr.ATTRIBUTE, "1"), createLabel(TestSelectionAttr.ATTRIBUTE, "2"),
				createLabel(TestSelectionAttr.ATTRIBUTE, "3"));
		assertEquals("1", selection2.attr(TestSelectionAttr.ATTRIBUTE));

	}

	private Widget createLabel(final String attr, final String value) {
		Label l = new Label();
		l.getElement().setAttribute(attr, value);
		return l;
	}
}
