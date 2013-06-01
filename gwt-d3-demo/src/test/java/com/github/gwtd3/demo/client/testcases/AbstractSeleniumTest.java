package com.github.gwtd3.demo.client.testcases;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.Color;

import com.github.gwtd3.demo.client.assertions.ColorAssert;

public class AbstractSeleniumTest {

    private static final String RUN_SELENIUM_LOCALHOST = "selenium.localhost";
    private static final String WEBAPP_URL = "selenium.webapp.url";
    protected WebDriver driver;

    @Before
    public void setup() throws MalformedURLException {
        String webAppUrl = System.getProperty(WEBAPP_URL);
        System.setProperty("SELENIUM_STARTING_URL", "http://127.0.0.1:8888/D3Demo.html?gwt.codesvr=127.0.0.1:9997");

        // separate localhost and saucelabls/cloudbees env
        if (System.getProperty(RUN_SELENIUM_LOCALHOST) != null) {
            // ensure the Firefox with default profile is loaded
            // in order to use the Firefox instance with GWT dev plugin
            System.setProperty("webdriver.firefox.profile", "default");
            driver = new FirefoxDriver();
            webAppUrl = "http://127.0.0.1:8888/D3Demo.html?gwt.codesvr=127.0.0.1:9997";
        }
        else {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("version", readPropertyOrEnv("SELENIUM_VERSION", "4"));
            capabilities.setCapability("platform", readPropertyOrEnv("SELENIUM_PLATFORM", "XP"));
            capabilities.setCapability("browserName", readPropertyOrEnv("SELENIUM_BROWSER", "firefox"));
            String username = readPropertyOrEnv("SAUCE_USER_NAME", "");
            String accessKey = readPropertyOrEnv("SAUCE_API_KEY", "");
            driver = new RemoteWebDriver(
                    new URL("http://" + username + ":" + accessKey + "@ondemand.saucelabs.com:80/wd/hub"),
                    capabilities);

            String sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
            System.out.println("SauceOnDemandSessionID=" + sessionId);

            webAppUrl = "http://hourly.gwt-d3.appspot.com/";
        }
        driver.get(webAppUrl);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        driver = null;
    }

    protected ColorAssert assertThat(final Color color) {
        return new ColorAssert(color, ColorAssert.class);
    }

    public static String readPropertyOrEnv(final String key, final String defaultValue) {
        String v = System.getProperty(key);
        if (v == null) {
            v = System.getenv(key);
        }
        if (v == null) {
            v = defaultValue;
        }
        return v;
    }
}
