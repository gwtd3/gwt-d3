package com.github.gwtd3.demo.client.conditions;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;

/**
 * A css color
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class CssColorPropertyEquals extends CssPropertyCondition {

    private String expectedColor;

    public CssColorPropertyEquals(final String cssColorProperty, final String expectedColor) {
        super(cssColorProperty);
        this.expectedColor = expectedColor;
    }

    public CssColorPropertyEquals(final WebElement element, final String cssColorProperty, final String expectedColor) {
        super(element, cssColorProperty);
        this.expectedColor = expectedColor;
    }

    @Override
    public boolean match(final String actualValue) {
        Color actualColor = Color.fromString(actualValue);
        Color expectedColor = Color.fromString(this.expectedColor);
        return actualColor.equals(expectedColor);
    }

}
