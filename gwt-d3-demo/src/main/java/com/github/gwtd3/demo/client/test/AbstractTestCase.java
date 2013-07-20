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
package com.github.gwtd3.demo.client.test;

import junit.framework.Assert;

import com.github.gwtd3.api.core.Value;
import com.google.gwt.core.client.JsDate;
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

	public String getElementInnerText(final int index) {
		return getElement(index).getInnerText();
	}

	public String getElementInnerHtml(final int index) {
		return getElement(index).getInnerHTML();
	}

	public String getElementClassAttribute(final int index) {
		return getElement(index).getClassName();
	}

	public String getElementStyle(final int index, final String style) {
		return getElement(index).getStyle().getProperty(style);
	}

	protected Value getElementProperty(final int index, final String property) {
		return Value.create(getElement(index), property);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.github.gwtd3.demo.client.tests.UnitTest#tearDown(com.google.gwt.user
	 * .client .ui .RootPanel)
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
	 * com.github.gwtd3.demo.client.tests.UnitTest#setUp(com.google.gwt.user
	 * .client .ui.RootPanel )
	 */
	@Override
	public void setUp(final ComplexPanel sandbox) {
		this.sandbox = sandbox;

	}

	public void assertDateEquals(double expected, double actual) {
		assertDateEquals(null, expected, actual);
	}

	public void assertDateEquals(String message, double expected, double actual) {
		double delta = .01;
		if (Double.compare(expected, actual) == 0)
			return;
		if (!(Math.abs(expected-actual) <= delta))
			failNotEquals(message, JsDate.create(expected), JsDate.create(actual));
	}
}
