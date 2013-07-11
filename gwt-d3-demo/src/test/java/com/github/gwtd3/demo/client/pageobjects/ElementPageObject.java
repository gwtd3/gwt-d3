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
package com.github.gwtd3.demo.client.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;

/**
 * A page object that is represented by a {@link WebElement}.
 * <p>
 * findMethods search into the element children.
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class ElementPageObject<P extends PageObject<?>> extends PageObject<P> {

	protected final WebElement element;

	public ElementPageObject(P parent, WebElement element) {
		super(parent);
		this.element = element;
	}

	public ElementPageObject(P parent, By by) {
		super(parent);
		this.element = parent.find(by);
	}

	public WebElement getElement() {
		return element;
	}

	protected void clickOnElement() {
		element.click();
	}

	/**
	 * Return a double representing the value of the given attribute.
	 * 
	 * @param attributeName
	 * @return a double or {@link Double#NaN} if the attribute does not contains
	 *         a parseable double value.
	 */
	public double getAttributeAsDouble(String attributeName) {
		try {
			return Double.parseDouble(element.getAttribute(attributeName));
		} catch (NumberFormatException e) {
			return Double.NaN;
		}
	}

	/**
	 * Return the given attribute as a color or null if the attribute is not
	 * specified.
	 * 
	 * @param attributeName
	 * @return
	 */
	public Color getAttributeAsColor(String attributeName) {
		try {
			return Color.fromString(element.getAttribute(attributeName));
		} catch (Exception e) {
			return null;
		}
	}

	public String getAttribute(String attributeName) {
		return element.getAttribute(attributeName);
	}

	@Override
	protected WebElement find(By by) {
		return element.findElement(by);
	}

	@Override
	protected List<WebElement> findAll(By by) {
		return element.findElements(by);
	}
}
