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

import com.github.gwtd3.demo.client.conditions.Conditions;
import com.github.gwtd3.demo.client.conditions.WebElementCondition;
import com.github.gwtd3.demo.client.test.ui.TestSessionContainer;
import com.github.gwtd3.demo.client.test.ui.UnitTestWidget;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestSuiteScreen extends PageObject<DemoApplication> {

	protected TestSuiteScreen(DemoApplication parent) {
		super(parent);
	}

	public TestSuiteScreen clickRun() {
		findClickable(By.id(TestSessionContainer.RUN_BUTTON_ID), 2).click();
		return this;
	}

	public static final WebElementCondition unitTestIsError = Conditions.backgroundColor(UnitTestWidget.ERROR_COLOR);
	public static final WebElementCondition unitTestIsSuccess = Conditions
			.backgroundColor(UnitTestWidget.SUCCESS_COLOR);
	public static final WebElementCondition unitTestIsDone = Conditions.or(unitTestIsError, unitTestIsSuccess);

	public TestSuiteScreen waitTestsAreAllDone() {
		List<WebElement> elements = getIconStatus();
		ExpectedCondition<List<WebElement>> unitTestsAreDone = Conditions.onElements(elements, unitTestIsDone);
		WebDriverWait wait = new WebDriverWait(driver, 3);
		//
		wait.until(ExpectedConditions.refreshed(unitTestsAreDone));
		return this;
	}

	/**
	 * Return a condition that check that all unit tests of the suite are
	 * successful
	 * 
	 * @return
	 */
	public ExpectedCondition<List<WebElement>> getUnitTestsAreSuccessfulCondition() {
		List<WebElement> elements = getIconStatus();
		return Conditions.onElements(elements, unitTestIsSuccess);
	}

	public List<WebElement> getIconStatus() {
		By name = By.name(UnitTestWidget.STATUS_INDICATOR_NAME);
		WebDriverWait wait = new WebDriverWait(driver, 3);
		wait.until(ExpectedConditions.visibilityOfElementLocated(name));
		List<WebElement> elements = driver.findElements(name);
		return elements;
	}

	public boolean unitTestsAreSuccess() {
		return getUnitTestsAreSuccessfulCondition().apply(driver) != null;
	}

}
