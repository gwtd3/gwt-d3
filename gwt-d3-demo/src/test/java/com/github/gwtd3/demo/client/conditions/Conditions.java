package com.github.gwtd3.demo.client.conditions;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import com.google.common.base.Predicate;

/**
 * Convenient methods for creating conditions.
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Conditions {

	/**
	 * Return a condition that check if the given element's background color
	 * property is equals to the given expected color.
	 * 
	 * @param element
	 * @param expectedColor
	 * @return
	 */
	public static CssColorPropertyEquals backgroundColor(
			final WebElement element, final String expectedColor) {
		return new CssColorPropertyEquals(element, "background-color",
				expectedColor);
	}

	/**
	 * Return a condition that check if background color property is equals to
	 * the given expected color.
	 * 
	 * @param expectedColor
	 * @return
	 */
	public static CssColorPropertyEquals backgroundColor(
			final String expectedColor) {
		return new CssColorPropertyEquals("background-color", expectedColor);
	}

	/**
	 * Return a condition that applies the given condition on all the elements
	 * provided.
	 * 
	 * @param elements
	 * @param condition
	 * @return
	 */
	public static ExpectedCondition<List<WebElement>> onElements(
			final List<WebElement> elements, final WebElementCondition condition) {
		return new ExpectedCondition<List<WebElement>>() {
			@Override
			public boolean equals(final Object obj) {
				return super.equals(obj);
			}

			@Override
			public List<WebElement> apply(final WebDriver input) {
				for (WebElement webElement : elements) {
					condition.setElement(webElement);
					if (condition.apply(input) == null) {
						return null;
					}
				}
				return elements;
			}
		};
	}

	public static Predicate<List<WebElement>> toPredicate(
			ExpectedCondition<List<WebElement>> condition) {
		return new Predicate<List<WebElement>>() {
			@Override
			public boolean apply(List<WebElement> input) {
				return input != null;
			}

			@Override
			public boolean equals(Object obj) {
				return super.equals(obj);
			}
		};
	}

	/**
	 * An expectation with the logical opposite condition of the given
	 * condition. In case of null, it will return false.
	 */
	public static WebElementCondition or(final WebElementCondition condition1,
			final WebElementCondition condition2) {
		return new OrCondition(condition1, condition2);
	}

	/**
	 * An expectation with the logical opposite condition of the given
	 * condition. In case of null, it will return false.
	 */
	public static WebElementCondition and(final WebElementCondition condition1,
			final WebElementCondition condition2) {
		return new AndCondition(condition1, condition2);
	}

}
