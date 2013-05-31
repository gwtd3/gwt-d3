package com.github.gwtd3.demo.client.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Entry point for the gwt-d3 Demo Application. The demo consists in a header
 * (with title and d3.js version), a navigation bar, and a main part.
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class DemoApplication extends PageObject<DemoApplication> {

    private static DemoApplication INSTANCE;

    private DemoApplication(final WebDriver driver) {
        super(driver);
    }

    /**
     * @param driver
     * @return the instance
     */
    public static DemoApplication getInstance(final WebDriver driver) {
        if (INSTANCE == null) {
            INSTANCE = new DemoApplication(driver);
        }
        DemoApplication.INSTANCE.driver = driver;
        return INSTANCE;
    }

    /**
     * @return the button "Test cases"
     */
    public TestCaseButton testCaseButton() {
        return new TestCaseButton(this);
    }

    public DemoDragMultiple revealDemoDragMultiple() {
        return new DemoDragMultiple(driver, this).reveal();
    }

    public WebElement getDemoContainer() {
        return findById("demoContainer");
    }

    public WebElement getCurrentDemo() {
        return getDemoContainer().findElement(By.cssSelector(":first-child"));
    }

}
