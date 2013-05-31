/**
 * 
 */
package com.github.gwtd3.demo.client.test;

import junit.framework.Assert;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public abstract class AbstractTestCase extends Assert implements TestCase {
	protected ComplexPanel sandbox;

	@SuppressWarnings("unchecked")
	public <T extends Widget> T getWidget(final int index) {
		return (T) sandbox.getWidget(index);
	}

	@SuppressWarnings("unchecked")
	public <T extends Element> T getElement(final int index) {
		return (T) sandbox.getWidget(index).getElement();
	}

	public String getElementAttribute(final int index, final String attribute) {
		return getElement(index).getAttribute(attribute);
	}

	public String getElementClassAttribute(final int index) {
		return getElement(index).getClassName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.github.gwtd3.demo.client.tests.UnitTest#tearDown(com.google.gwt.user.client
	 * .ui .RootPanel)
	 */
	@Override
	public void tearDown(final ComplexPanel sandbox) {
		clearSandbox();
	}

	protected void clearSandbox() {
		sandbox.clear();
		sandbox.getElement().setInnerHTML("");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.github.gwtd3.demo.client.tests.UnitTest#setUp(com.google.gwt.user.client
	 * .ui.RootPanel )
	 */
	@Override
	public void setUp(final ComplexPanel sandbox) {
		this.sandbox = sandbox;

	}
}
