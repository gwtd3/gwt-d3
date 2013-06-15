package com.github.gwtd3.demo.client.testcases.selection;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.core.Selection;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Label;

public class TestSelectionControls extends AbstractSelectionTest {

	private static final String ATTRIBUTE = "myattr";

	@Override
	public void doTest(final ComplexPanel sandbox) {
		testEmpty();// 1
		testNode();// 2

	}

	/**
	 * 
	 */
	private void testNode() {
		Selection selection = givenAMultipleSelection(new Label("1"), new Label("2"));
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
