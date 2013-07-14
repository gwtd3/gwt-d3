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
			public Boolean apply(final Element context, final Value datum, final int index) {
				return true;
			}
		});
		assertEquals("foo bar", getElementClassAttribute(0));

		// works with multiple selection
		selection = givenAMultipleSelection(createLabel(""), createLabel(""), createLabel(""));
		selection.classed("foo bar", new DatumFunction<Boolean>() {
			@Override
			public Boolean apply(final Element context, final Value datum, final int index) {
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
