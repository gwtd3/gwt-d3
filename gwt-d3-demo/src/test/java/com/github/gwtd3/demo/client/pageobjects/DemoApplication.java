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
