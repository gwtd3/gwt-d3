package com.github.gwtd3.demo.client.testcases.transition;

import com.github.gwtd3.api.ease.Easing;
import com.github.gwtd3.api.ease.EasingFunction;
import com.github.gwtd3.api.ease.Mode;
import com.github.gwtd3.demo.client.test.AbstractTestCase;
import com.google.gwt.user.client.ui.ComplexPanel;

public class TestEasing extends AbstractTestCase{

	@Override
	public void doTest(final ComplexPanel sandbox) {
		testEasingFunction(Easing.back(4), Easing.back(Mode.IN_OUT, 4));
		testEasingFunction(Easing.bounce(), Easing.bounce(Mode.IN_OUT));
		testEasingFunction(Easing.circle(), Easing.circle(Mode.OUT));
		testEasingFunction(Easing.cubic(), Easing.cubic(Mode.IN_OUT));
		testEasingFunction(Easing.exp(), Easing.exp(Mode.OUT_IN));
		testEasingFunction(Easing.linear(), Easing.linear(Mode.IN_OUT));
		testEasingFunction(Easing.quad(), Easing.quad(Mode.IN_OUT));
		testEasingFunction(Easing.sin(), Easing.sin(Mode.IN_OUT));
		testEasingFunction(Easing.elastic(10,0.5), Easing.elastic(Mode.IN_OUT,10,0.5));
		testEasingFunction(Easing.poly(3), Easing.poly(Mode.IN_OUT,3));
	}

	protected void testEasingFunction(final EasingFunction e1, final EasingFunction e2){
		assertEquals(0.0, e1.ease(0));
		assertEquals(1.0, e1.ease(1));

		assertEquals(0.0, e2.ease(0));
		assertEquals(1.0, e2.ease(1));

	}

}
