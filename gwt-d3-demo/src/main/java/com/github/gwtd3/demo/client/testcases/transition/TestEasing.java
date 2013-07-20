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
