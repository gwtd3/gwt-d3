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
