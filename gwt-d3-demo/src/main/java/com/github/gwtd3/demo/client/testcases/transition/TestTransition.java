package com.github.gwtd3.demo.client.testcases.transition;

import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Transition;
import com.github.gwtd3.demo.client.testcases.selection.AbstractSelectionTest;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Label;

public class TestTransition extends AbstractSelectionTest {

	@Override
	public void doTest(final ComplexPanel sandbox) {
		Selection selection = givenASimpleSelection(new Label("blah"));
		//
		Transition transition = selection.transition();

		Transition duration = transition.duration(300);

	}


}
