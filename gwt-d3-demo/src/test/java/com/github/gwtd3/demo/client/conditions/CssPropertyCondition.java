package com.github.gwtd3.demo.client.conditions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * A {@link WebElementCondition} on any css property.
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public abstract class CssPropertyCondition extends WebElementCondition {
    protected String cssProperty;

    public CssPropertyCondition(final String cssProperty) {
        super();
        this.cssProperty = cssProperty;
    }

    public CssPropertyCondition(final WebElement element, final String cssProperty) {
        super(element);
        this.cssProperty = cssProperty;
    }

    @Override
    public WebElement apply(final WebDriver input) {
        String actualValue = element.getCssValue(cssProperty);
        return match(actualValue) ? element : null;
    }

    public abstract boolean match(final String actualValue);
}