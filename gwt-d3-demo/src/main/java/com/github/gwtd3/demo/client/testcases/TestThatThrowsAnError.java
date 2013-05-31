/**
 * 
 */
package com.github.gwtd3.demo.client.testcases;

import com.github.gwtd3.demo.client.test.AbstractTestCase;

import com.google.gwt.user.client.ui.ComplexPanel;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class TestThatThrowsAnError extends AbstractTestCase {

	@Override
	public void doTest(final ComplexPanel sandbox) {
		throw new NullPointerException("fake NPE to test");
	}

}
