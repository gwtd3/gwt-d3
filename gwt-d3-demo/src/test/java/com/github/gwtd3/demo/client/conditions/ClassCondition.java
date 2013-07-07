/**
 * 
 */
package com.github.gwtd3.demo.client.conditions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Check condition
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class ClassCondition extends WebElementCondition {

	private final String className;

	public ClassCondition(final WebElement element, final String className) {
		super(element);
		this.className = className;
	}

	public ClassCondition(final String className) {
		super();
		this.className = className;
	}

	@Override
	public WebElement apply(final WebDriver input) {
		String actualValue = element.getAttribute("class");
		return actualValue.contains(className.trim()) ? element : null;
	}

}
