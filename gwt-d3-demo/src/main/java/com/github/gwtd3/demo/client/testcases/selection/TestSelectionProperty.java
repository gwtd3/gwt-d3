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
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class TestSelectionProperty extends AbstractSelectionTest {

	private static final String PROPERTY = "checked";

	@Override
	public void doTest(final ComplexPanel sandbox) {
		testGetter();
		testSetterConstantBoolean();
		testSetterConstantDouble();
		testSetterConstantString();
		testSetterConstantJavascriptObject();
		testSetterFunction();
		testSetterGetter();
	}

	/**
	 * 
	 */
	private void testSetterGetter() {
		// works with single selection
		Selection selection = givenASimpleSelection(new Label());
		final double value = 1.56;
		selection.property(TestSelectionProperty.PROPERTY, new DatumFunction<Double>() {
			@Override
			public Double apply(final Element context, final Value datum, final int index) {
				return value;
			}
		});
		assertEquals(value, selection.property(TestSelectionProperty.PROPERTY).asDouble());

		// works with multiple selection
		Selection selection2 = givenAMultipleSelection(new Label(), new Label(), new Label());
		selection2.property(TestSelectionProperty.PROPERTY, new DatumFunction<Double>() {
			@Override
			public Double apply(final Element context, final Value datum, final int index) {
				return value;
			}
		});
		assertEquals(value, selection.property(TestSelectionProperty.PROPERTY).asDouble());

	}

	protected void testSetterFunction() {
		// works with single selection
		Selection selection = givenASimpleSelection(new Label());
		final String value = "1";
		selection.property(TestSelectionProperty.PROPERTY, new DatumFunction<String>() {
			@Override
			public String apply(final Element context, final Value datum, final int index) {
				return value;
			}
		});
		assertEquals(value, getElementProperty(0, TestSelectionProperty.PROPERTY).asString());

		// works with multiple selection
		Selection selection2 = givenAMultipleSelection(new Label(), new Label(), new Label());
		selection2.property(TestSelectionProperty.PROPERTY, new DatumFunction<String>() {
			@Override
			public String apply(final Element context, final Value datum, final int index) {
				return value;
			}
		});
		assertEquals(value, getElementProperty(0, TestSelectionProperty.PROPERTY).asString());
		assertEquals(value, getElementProperty(1, TestSelectionProperty.PROPERTY).asString());
		assertEquals(value, getElementProperty(2, TestSelectionProperty.PROPERTY).asString());
	}

	protected void testSetterConstantString() {
		// works with single selection
		Selection selection = givenASimpleSelection(new Label());
		String value = "1";
		selection.property(TestSelectionProperty.PROPERTY, value);
		assertEquals(value, getElementProperty(0, TestSelectionProperty.PROPERTY).asString());

		// works with multiple selection
		Selection selection2 = givenAMultipleSelection(new Label(), new Label(), new Label());
		selection2.property(TestSelectionProperty.PROPERTY, value);
		assertEquals(value, getElementProperty(0, TestSelectionProperty.PROPERTY).asString());
		assertEquals(value, getElementProperty(1, TestSelectionProperty.PROPERTY).asString());
		assertEquals(value, getElementProperty(2, TestSelectionProperty.PROPERTY).asString());

	}

	protected void testSetterConstantDouble() {
		// works with single selection
		Selection selection = givenASimpleSelection(new Label());
		double value = 3.56;
		selection.property(TestSelectionProperty.PROPERTY, value);
		assertEquals(value, getElementProperty(0, TestSelectionProperty.PROPERTY).asDouble());

		// works with multiple selection
		Selection selection2 = givenAMultipleSelection(new Label(), new Label(), new Label());
		selection2.property(TestSelectionProperty.PROPERTY, value);
		assertEquals(value, getElementProperty(0, TestSelectionProperty.PROPERTY).asDouble());
		assertEquals(value, getElementProperty(1, TestSelectionProperty.PROPERTY).asDouble());
		assertEquals(value, getElementProperty(2, TestSelectionProperty.PROPERTY).asDouble());
	}

	protected void testSetterConstantBoolean() {
		boolean value = true;
		// works with single selection
		Selection selection = givenASimpleSelection(new Label());
		selection.property(TestSelectionProperty.PROPERTY, value);
		assertEquals(value, getElementProperty(0, TestSelectionProperty.PROPERTY).asBoolean());

		// works with multiple selection
		Selection selection2 = givenAMultipleSelection(new Label(), new Label(), new Label());
		selection2.property(TestSelectionProperty.PROPERTY, value);
		assertEquals(value, getElementProperty(0, TestSelectionProperty.PROPERTY).asBoolean());
		assertEquals(value, getElementProperty(1, TestSelectionProperty.PROPERTY).asBoolean());
		assertEquals(value, getElementProperty(2, TestSelectionProperty.PROPERTY).asBoolean());

	}

	protected void testSetterConstantJavascriptObject() {
		// any object
		JavaScriptObject value = JavaScriptObject.createArray();
		// works with single selection
		Selection selection = givenASimpleSelection(new Label());
		String propName = "__test__";
		selection.property(propName, value);
		assertEquals(value, getElementProperty(0, propName).as());

		// works with multiple selection
		Selection selection2 = givenAMultipleSelection(new Label(), new Label(), new Label());
		selection2.property(propName, value);
		assertEquals(value, getElementProperty(0, propName).as(Object.class));
		assertEquals(value, getElementProperty(1, propName).as(Object.class));
		assertEquals(value, getElementProperty(2, propName).as(Object.class));

	}

	protected void testGetter() {
		// try a boolean
		CheckBox cb = new CheckBox();
		Selection sel = givenASimpleSelection(cb);
		cb.getElement().setPropertyBoolean("checked", true);
		assertEquals(true, sel.property("checked").asBoolean());

		// with multiple selection, should return the first element
		Selection selection2 = givenAMultipleSelection(createLabel(TestSelectionProperty.PROPERTY, "true"), createLabel(TestSelectionProperty.PROPERTY, "false"),
				createLabel(TestSelectionProperty.PROPERTY, "false"));
		assertEquals("true", selection2.property(TestSelectionProperty.PROPERTY).asString());
	}

	private Widget createLabel(final String attr, final String value) {
		Label l = new Label();
		l.getElement().setPropertyString(attr, value);
		return l;
	}
}
