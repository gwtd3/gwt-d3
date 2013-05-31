package com.github.gwtd3.demo.client.testcases;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.openqa.selenium.By;

import com.github.gwtd3.demo.client.pageobjects.DemoApplication;
import com.github.gwtd3.demo.client.pageobjects.TestSuiteScreen;

public class TestCases extends AbstractSeleniumTest {

    @Test
    public void basic() throws Exception {
        DemoApplication application = DemoApplication.getInstance(driver);
        Assertions.assertThat(driver.getTitle()).isEqualTo("GWT-D3 Demo");
    }

    @Test
    public void testCasesPass() {
        driver.findElement(By.xpath("div . mybutton")).click();

        DemoApplication application = DemoApplication.getInstance(driver);

        TestSuiteScreen testSuite = application.testCaseButton().click();
        testSuite.clickRun().waitTestsAreAllDone();
        Assertions.assertThat(testSuite.unitTestsAreSuccess()).isTrue();
    }

}
