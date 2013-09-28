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
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Label;

public class TestSelectionControls extends AbstractSelectionTest {

	private static final String ATTRIBUTE = "myattr";

	@Override
	public void doTest(final ComplexPanel sandbox) {
		testEmpty();// 1
		testNode();// 2
		testEach();
		testCount();
		testCall();
		testInterrupt();
	}

	private void testInterrupt() {
		Selection selection = givenAMultipleSelection(new Label("1"),
				new Label("2"));
		selection.interrupt();
	}

	/**
	 * 
	 */
	private void testCall() {
		// nothing to test before any other use cases of using selection.call-
		// maybe by providing a SelectionCallback interface with one method
		// call(Selection) ?

	}

	/**
	 * 
	 */
	private void testEach() {
		Selection selection = givenAMultipleSelection(new Label("1"),
				new Label("2"));
		final StringBuilder sb = new StringBuilder();
		selection.each(new DatumFunction<Void>() {
			@Override
			public Void apply(final Element context, final Value d,
					final int index) {
				sb.append(context.getInnerText());
				return null;
			}
		});
		assertEquals("12", sb.toString());
	}

	/**
	 * 
	 */
	private void testCount() {
		Selection selection = givenAMultipleSelection(new Label("1"),
				new Label("2"));
		assertEquals(2, selection.size());
	}

	/**
	 * 
	 */
	private void testNode() {
		Selection selection = givenAMultipleSelection(new Label("1"),
				new Label("2"));
		assertEquals("1", selection.node().getInnerText());
		selection = selection.selectAll("unknown");
		assertNull(selection.node());
	}

	protected void testEmpty() {
		Selection selection = D3.select(sandbox);

		selection.append("myelement");

		assertEquals(false, selection.select("myelement").empty());
		assertEquals(true, selection.select("unknown").empty());

	}

}
