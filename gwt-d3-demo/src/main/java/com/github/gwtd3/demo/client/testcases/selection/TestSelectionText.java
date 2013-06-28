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

import com.github.gwtd3.api.core.Datum;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.functions.DatumFunction;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class TestSelectionText extends AbstractSelectionTest {

	@Override
	public void doTest(final ComplexPanel sandbox) {
		testGetter();
		testSetterConstantString();
		testSetterFunction();

	}

	protected void testSetterFunction() {
		// works with single selection
		Selection selection = givenASimpleSelection(new Label());
		final String value = "foo bar";
		selection.text(new DatumFunction<String>() {
			@Override
			public String apply(final Element context, final Datum datum, final int index) {
				return value + index;
			}
		});
		assertEquals(value + "0", getElementInnerText(0));

		// works with multiple selection
		selection = givenAMultipleSelection(new Label(), new Label(), new Label());
		selection.text(new DatumFunction<String>() {
			@Override
			public String apply(final Element context, final Datum datum, final int index) {
				return value + index;
			}
		});
		assertEquals(value + "0", getElementInnerText(0));
		assertEquals(value + "1", getElementInnerText(1));
		assertEquals(value + "2", getElementInnerText(2));

	}

	protected void testSetterConstantString() {
		// works with single selection
		Selection selection = givenASimpleSelection(new Label());
		String value = "foo bar";
		selection.text(value);
		assertEquals(value, getElementInnerText(0));

		// works with multiple selection
		selection = givenAMultipleSelection(new Label(), new Label(), new Label());
		selection.text(value);
		assertEquals(value, getElementInnerText(0));
		assertEquals(value, getElementInnerText(1));
		assertEquals(value, getElementInnerText(2));

	}

	protected void testGetter() {
		// with single selection
		Label label = new Label();
		String value = "foo";
		label.getElement().setInnerText(value);
		Selection selection = givenASimpleSelection(label);
		assertEquals(value, selection.text());

		// with multiple selection, should return the first element
		selection = givenAMultipleSelection(createLabel(value), createLabel(value), createLabel(value));
		assertEquals(value, selection.text());
	}

	private Widget createLabel(final String textValue) {
		Label l = new Label();
		l.getElement().setInnerText(textValue);
		return l;
	}
}
