package com.github.gwtd3.demo.client.conditions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import com.google.common.base.Predicate;

/**
 * An {@link ExpectedCondition} on a {@link WebElement}.
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public abstract class WebElementCondition implements ExpectedCondition<WebElement> {
    protected WebElement element;

    public WebElementCondition() {

    }

    public WebElementCondition(final WebElement element) {
        this.element = element;
    }

    @Override
    public boolean equals(final Object obj) {
        return super.equals(obj);
    }

    public void setElement(final WebElement element) {
        this.element = element;
    }

    public Predicate<WebElement> toPredicate(final WebDriver driver) {
        return new Predicate<WebElement>() {
            @Override
            public boolean apply(final WebElement input) {
                return WebElementCondition.this.apply(driver) != null;
            }

            @Override
            public boolean equals(final Object object) {
                return object != null;
            }
        };
    }
}
