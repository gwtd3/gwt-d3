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
import com.github.gwtd3.api.core.Value;
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
			public String apply(final Element context, final Value datum, final int index) {
				return value;
			}
		});
		assertEquals(value, getElementAttribute(0, TestSelectionAttr.ATTRIBUTE));

		// works with multiple selection
		Selection selection2 = givenAMultipleSelection(new Label(), new Label(), new Label());
		selection2.attr(TestSelectionAttr.ATTRIBUTE, new DatumFunction<String>() {
			@Override
			public String apply(final Element context, final Value datum, final int index) {
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
