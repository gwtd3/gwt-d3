package com.github.gwtd3.demo.client.conditions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OrCondition extends WebElementCondition {

    private WebElementCondition op1;
    private WebElementCondition op2;

    public OrCondition(final WebElementCondition op1, final WebElementCondition op2) {
        super();
        this.op1 = op1;
        this.op2 = op2;
    }

    public OrCondition(final WebElement element, final WebElementCondition op1, final WebElementCondition op2) {
        super(element);
        this.op1 = op1;
        this.op2 = op2;
    }

    @Override
    public WebElement apply(final WebDriver input) {
        WebElement el1 = op1.apply(input);
        return el1 != null ? el1 : op2.apply(input);
    }

    @Override
    public void setElement(final WebElement element) {
        super.setElement(element);
        op1.setElement(element);
        op2.setElement(element);
    }
}
