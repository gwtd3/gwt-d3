package com.github.gwtd3.demo.client.testcases.selection;

import com.github.gwtd3.api.core.Datum;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.functions.DatumFunction;
import com.google.gwt.core.client.debug.JsoInspector;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class TestSelectionStyle extends AbstractSelectionTest {

	/**
	 * 
	 */
	private static final String VALUE = "end";
	private static final String STYLE_CAMEL = "textAlign";
	private static final String STYLE_HTML = "text-align";

	@Override
	public void doTest(final ComplexPanel sandbox) {
		testSetterConstantString();
		testSetterConstantDouble();
		testSetterFunction();
		testSetterFunctionImportant();
		testGetter();

	}

	protected void testSetterFunction() {
		// works with single selection
		Selection selection = givenASimpleSelection(new Label());
		final double value = 0.5;
		selection.style("opacity", new DatumFunction<Double>() {
			@Override
			public Double apply(final Element context, final Datum datum, final int index) {
				return value;
			}
		});
		assertEquals(value, getElementAttribute(0, "opacity"));

		// works with multiple selection
		Selection selection2 = givenAMultipleSelection(new Label(), new Label(), new Label());
		selection2.attr("opacity", new DatumFunction<Double>() {
			@Override
			public Double apply(final Element context, final Datum datum, final int index) {
				return value;
			}
		});
		assertEquals(value, getElementAttribute(0, "opacity"));
		assertEquals(value, getElementAttribute(1, "opacity"));
		assertEquals(value, getElementAttribute(2, "opacity"));

	}

	protected void testSetterFunctionImportant() {
		// works with single selection
		Selection selection = givenASimpleSelection(new Label());
		final double value = 1.54;
		selection.style(TestSelectionStyle.STYLE_CAMEL, new DatumFunction<Double>() {
			@Override
			public Double apply(final Element context, final Datum datum, final int index) {
				return value;
			}
		}, true);
		assertEquals("1.54 !important", getElementAttribute(0, TestSelectionStyle.STYLE_CAMEL));

		// works with multiple selection
		Selection selection2 = givenAMultipleSelection(new Label(), new Label(), new Label());
		selection2.attr(TestSelectionStyle.STYLE_CAMEL, new DatumFunction<Double>() {
			@Override
			public Double apply(final Element context, final Datum datum, final int index) {
				return value;
			}
		});
		assertEquals("1.54 !important", getElementAttribute(0, TestSelectionStyle.STYLE_CAMEL));
		assertEquals("1.54 !important", getElementAttribute(1, TestSelectionStyle.STYLE_CAMEL));
		assertEquals("1.54 !important", getElementAttribute(2, TestSelectionStyle.STYLE_CAMEL));

	}

	protected void testSetterConstantString() {
		// works with single selection
		Selection selection = givenASimpleSelection(new Label());
		String value = "end";
		selection.style(TestSelectionStyle.STYLE_HTML, value);
		assertEquals(value, getElementStyle(0, TestSelectionStyle.STYLE_CAMEL));

		// works with multiple selection
		Selection selection2 = givenAMultipleSelection(new Label(), new Label(), new Label());
		selection2.style(TestSelectionStyle.STYLE_HTML, value);
		assertEquals(value, getElementStyle(0, TestSelectionStyle.STYLE_CAMEL));
		assertEquals(value, getElementStyle(1, TestSelectionStyle.STYLE_CAMEL));
		assertEquals(value, getElementStyle(2, TestSelectionStyle.STYLE_CAMEL));

	}

	protected void testSetterConstantDouble() {
		// works with single selection
		Selection selection = givenASimpleSelection(new Label());
		double value = 0.5;
		selection.style("opacity", value);
		assertEquals(value, Double.parseDouble(getElementStyle(0, "opacity")));

		// works with multiple selection
		Selection selection2 = givenAMultipleSelection(new Label(), new Label(), new Label());
		selection2.style("opacity", value);
		assertEquals(value, Double.parseDouble(getElementStyle(0, "opacity")));
		assertEquals(value, Double.parseDouble(getElementStyle(1, "opacity")));
		assertEquals(value, Double.parseDouble(getElementStyle(2, "opacity")));
	}

	protected void testGetter() {
		// with single selection
		Label label = new Label();
		label.getElement().getStyle().setProperty(TestSelectionStyle.STYLE_CAMEL, TestSelectionStyle.VALUE);
		Selection selection = givenASimpleSelection(label);
		Object object = JsoInspector.convertToInspectableObject(label.getElement().getStyle());
		assertEquals(TestSelectionStyle.VALUE, selection.style(TestSelectionStyle.STYLE_HTML));

		// with multiple selection, should return the first element
		Selection selection2 = givenAMultipleSelection(
				createLabel(TestSelectionStyle.STYLE_CAMEL, TestSelectionStyle.VALUE),
				createLabel(TestSelectionStyle.STYLE_CAMEL, "start"),
				createLabel(TestSelectionStyle.STYLE_CAMEL, "blah"));
		object = JsoInspector.convertToInspectableObject(selection2.node());

		assertEquals(TestSelectionStyle.VALUE, selection2.attr(TestSelectionStyle.STYLE_HTML));

	}

	private Widget createLabel(final String style, final String value) {
		Label l = new Label();
		l.getElement().getStyle().setProperty(style, value);
		return l;
	}
}
