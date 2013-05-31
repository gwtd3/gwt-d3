/**
 * 
 */
package com.github.gwtd3.demo.client.test;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public abstract class AbstractBehaviourTest extends AbstractTestCase {


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.github.gwtd3.demo.client.test.TestCase#doTest(com.google.gwt.user.client
	 * .ui.ComplexPanel)
	 */
	@Override
	public void doTest(final ComplexPanel sandbox) {
		given(sandbox);
		when(sandbox);
		then(sandbox);
	}

	/**
	 * @param sandbox
	 */
	protected abstract void then(ComplexPanel sandbox);

	/**
	 * @param sandbox
	 */
	protected abstract void when(ComplexPanel sandbox);

	/**
	 * @param sandbox
	 */
	protected abstract void given(ComplexPanel sandbox);
}
