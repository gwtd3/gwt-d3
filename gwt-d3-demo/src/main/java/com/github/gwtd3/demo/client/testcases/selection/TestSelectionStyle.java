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

import com.github.gwtd3.api.core.Value;
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
	private static final String VALUE = "0.75";
	private static final String STYLE_CAMEL = "opacity";
	private static final String STYLE_HTML = "opacity";

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
		selection.style(STYLE_HTML, new DatumFunction<Double>() {
			@Override
			public Double apply(final Element context, final Value datum, final int index) {
				return value;
			}
		});
		assertEquals(Double.toString(value), getElementStyle(0, STYLE_CAMEL));

		// works with multiple selection
		Selection selection2 = givenAMultipleSelection(new Label(), new Label(), new Label());
		selection2.style(STYLE_HTML, new DatumFunction<Double>() {
			@Override
			public Double apply(final Element context, final Value datum, final int index) {
				return value;
			}
		});
		assertEquals(Double.toString(value), getElementStyle(0, STYLE_CAMEL));
		assertEquals(Double.toString(value), getElementStyle(1, STYLE_CAMEL));
		assertEquals(Double.toString(value), getElementStyle(2, STYLE_CAMEL));

	}

	protected void testSetterFunctionImportant() {
		// works with single selection
		Selection selection = givenASimpleSelection(new Label());
		final double value = 1.54;
		selection.style(TestSelectionStyle.STYLE_HTML, new DatumFunction<Double>() {
			@Override
			public Double apply(final Element context, final Value datum, final int index) {
				return value;
			}
		}, true);
		assertEquals("1.54", getElementStyle(0, TestSelectionStyle.STYLE_CAMEL));

		// works with multiple selection
		Selection selection2 = givenAMultipleSelection(new Label(), new Label(), new Label());
		selection2.style(TestSelectionStyle.STYLE_HTML, new DatumFunction<Double>() {
			@Override
			public Double apply(final Element context, final Value datum, final int index) {
				return value;
			}
		});
		assertEquals("1.54", getElementStyle(0, TestSelectionStyle.STYLE_CAMEL));
		assertEquals("1.54", getElementStyle(1, TestSelectionStyle.STYLE_CAMEL));
		assertEquals("1.54", getElementStyle(2, TestSelectionStyle.STYLE_CAMEL));

	}

	protected void testSetterConstantString() {
		// works with single selection
		Selection selection = givenASimpleSelection(new Label());
		String value = "1.54";
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

		assertEquals(TestSelectionStyle.VALUE, selection2.style(TestSelectionStyle.STYLE_HTML));

	}

	private Widget createLabel(final String style, final String value) {
		Label l = new Label();
		l.getElement().getStyle().setProperty(style, value);
		return l;
	}
}
