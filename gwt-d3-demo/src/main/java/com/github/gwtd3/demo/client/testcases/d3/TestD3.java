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
/**
 * 
 */
package com.github.gwtd3.demo.client.testcases.d3;

import java.util.Arrays;
import java.util.List;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.demo.client.test.AbstractTestCase;
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
	private void version() {
		D3.version();
	}

}
