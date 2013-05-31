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
