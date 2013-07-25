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
package com.github.gwtd3.demo.client.testcases.transition;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.ease.Easing;
import com.github.gwtd3.api.ease.EasingFunction;
import com.github.gwtd3.api.ease.Mode;
import com.github.gwtd3.demo.client.test.AbstractTestCase;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.ComplexPanel;

public class TestEasing extends AbstractTestCase {

	private Element cobaye;

	@Override
	public void setUp(final ComplexPanel sandbox) {
		super.setUp(sandbox);
		// append an element in the sandbox
		cobaye = DOM.createDiv();
		sandbox.getElement().appendChild(cobaye);
	}

	@Override
	public void tearDown(final ComplexPanel sandbox) {
		super.tearDown(sandbox);
		clearSandbox();
	}

	@Override
	public void doTest(final ComplexPanel sandbox) {

		// built in
		testEasingFunction(Easing.back(4), Easing.back(Mode.IN_OUT, 4));
		testEasingFunction(Easing.bounce(), Easing.bounce(Mode.IN_OUT));
		testEasingFunction(Easing.circle(), Easing.circle(Mode.OUT));
		testEasingFunction(Easing.cubic(), Easing.cubic(Mode.IN_OUT));
		testEasingFunction(Easing.exp(), Easing.exp(Mode.OUT_IN));
		testEasingFunction(Easing.linear(), Easing.linear(Mode.IN_OUT));
		testEasingFunction(Easing.quad(), Easing.quad(Mode.IN_OUT));
		testEasingFunction(Easing.sin(), Easing.sin(Mode.IN_OUT));
		testEasingFunction(Easing.elastic(10, 0.5), Easing.elastic(Mode.IN_OUT, 10, 0.5));
		testEasingFunction(Easing.poly(3), Easing.poly(Mode.IN_OUT, 3));

		// custom
		EasingFunction f = new EasingFunction() {
			@Override
			public double ease(final double t) {
				return t;
			}
		};
		testEasingFunction(f, f);

		// pass it
	}

	protected void testEasingFunction(final EasingFunction e1, final EasingFunction e2) {
		// test the extreme values
		assertEquals(0.0, e1.ease(0));
		assertEquals(1.0, e1.ease(1));

		assertEquals(0.0, e2.ease(0));
		assertEquals(1.0, e2.ease(1));

		Selection selection = D3.select(sandbox).append("div");
		// pass it to Transition.ease1
		//		D3.select(sandbox).append("div")
		//		.attr("foo", "stuff")
		selection.transition().duration(1000).ease(e1)
		.attr("foobar", Integer.toString(Random.nextInt()))
		.transition()
		.ease(e2)
		.attr("foobar", Integer.toString(Random.nextInt()));
	}
}
