package com.github.gwtd3.demo.client.testcases;

import org.assertj.core.api.Assertions;
import com.github.gwtd3.demo.client.pageobjects.DemoApplication;
import com.github.gwtd3.demo.client.pageobjects.TestSuiteScreen;
import org.junit.Test;

public class TestCases extends AbstractSeleniumTest {

	private DemoApplication application;

	@Test
	public void basic() throws Exception {
		application = DemoApplication.getInstance(driver);
		Assertions.assertThat(driver.getTitle()).isEqualTo("GWT-D3 Demo");
	}

	@Test
	public void testCasesPass() {
		application = DemoApplication.getInstance(driver);

		TestSuiteScreen testSuite = application.testCaseButton().click();
		testSuite.clickRun().waitTestsAreAllDone();
		Assertions.assertThat(testSuite.unitTestsAreSuccess()).isTrue();
	}

}
