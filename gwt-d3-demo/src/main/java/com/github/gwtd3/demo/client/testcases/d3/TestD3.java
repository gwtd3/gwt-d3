/**
 * 
 */
package com.github.gwtd3.demo.client.testcases.d3;

import java.util.Arrays;
import java.util.List;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.demo.client.test.AbstractTestCase;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class TestD3 extends AbstractTestCase {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.gwtd3.demo.client.tests.UnitTest#doTest(com.google.gwt.user.client.ui. RootPanel)
	 */
	@Override
	public void doTest(final ComplexPanel sandbox) {
		// version
		version();
		select();
		selectAll();

		shuffle();
	}

	/**
	 * 
	 */
	private void shuffle() {
		// for char array
		char[] a = { 'a', 'b', 'c', 'd' };
		D3.shuffle(a);
		boolean sorted = true;
		char previous = 0;
		for (char c : a) {
			if (c < previous) {
				sorted = false;
				break;
			}
			previous = c;
		}
		if (sorted) {
			fail("shuffle did not work");
		}
		// for list
		List<String> array = Arrays.asList("1", "2", "3", "4");
		D3.shuffle(array);
		assertNotSorted(array);
	}

	private <T extends Comparable<T>> void assertNotSorted(final Iterable<T> array) {
		T previous = null;
		for (T comparable : array) {
			// when one element found is not sorted
			if ((previous != null) && (comparable.compareTo(previous) < 0)) {
				return;
			}
			previous = comparable;
		}
		fail("shuffle did not work");
	}

	private <T extends Comparable<T>> void assertNotSorted(final T[] array) {
		T previous = null;
		for (T comparable : array) {
			// when one element found is not sorted
			if ((previous != null) && (comparable.compareTo(previous) < 0)) {
				return;
			}
			previous = comparable;
		}
		fail("shuffle did not work");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.gwtd3.demo.client.test.AbstractTestCase#setUp(com.google.gwt.user.client.ui.ComplexPanel)
	 */
	@Override
	public void setUp(final ComplexPanel sandbox) {
		super.setUp(sandbox);
		sandbox.add(new Label("1"));
		sandbox.add(new Label("1"));
		sandbox.add(new Label("1"));
	}

	/**
	 * 
	 */
	private void selectAll() {

		NodeList<Element> labels = Document.get().getElementsByTagName("div");
		Selection selection = D3.selectAll(labels);
		assertFalse(selection.empty());

		Selection selection2 = D3.selectAll("div");
		assertFalse(selection2.empty());
	}

	/**
	 * 
	 */
	private void version() {
		D3.version();
	}

	/**
	 * 
	 */
	private void select() {
		Selection select = D3.select(sandbox);
		assertEquals(sandbox.getElement(), select.node());

		select = D3.select("div");
		assertFalse(select.empty());

	}

}
