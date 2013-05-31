package com.github.gwtd3.demo.client.testcases;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.Color;

import com.github.gwtd3.demo.client.assertions.ColorAssert;

public class AbstractSeleniumTest {

    private static final String RUN_SELENIUM_LOCALHOST = "selenium.localhost";
    private static final String WEBAPP_URL = "selenium.webapp.url";
    protected static WebDriver driver;

    @BeforeClass
    public static void setup() throws MalformedURLException {
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
        // construct the DesiredCapabilities using the environment variables set
        // by the Sauce CI plugin
        // DesiredCapabilities capabilities = new DesiredCapabilities();
        // capabilities.setCapability("version",
        // Utils.readPropertyOrEnv("SELENIUM_VERSION", "4"));
        // capabilities.setCapability("platform",
        // Utils.readPropertyOrEnv("SELENIUM_PLATFORM", "XP"));
        // capabilities.setCapability("browserName",
        // Utils.readPropertyOrEnv("SELENIUM_BROWSER", "firefox"));
        // String username = Utils.readPropertyOrEnv("SAUCE_USER_NAME", "");
        // String accessKey = Utils.readPropertyOrEnv("SAUCE_API_KEY", "");
        // driver = new RemoteWebDriver(new URL("http://" + username + ":" +
        // accessKey + "@localhost:4445/wd/hub"),
        // capabilities);
        else {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("version", readPropertyOrEnv("SELENIUM_VERSION", "4"));
            capabilities.setCapability("platform", readPropertyOrEnv("SELENIUM_PLATFORM", "XP"));
            capabilities.setCapability("browserName", readPropertyOrEnv("SELENIUM_BROWSER", "firefox"));
            String username = readPropertyOrEnv("SAUCE_USER_NAME", "");
            String accessKey = readPropertyOrEnv("SAUCE_API_KEY", "");
            System.out.println("SELENIUM_VERSION:" + readPropertyOrEnv("SELENIUM_VERSION", "unknown"));
            System.out.println("SELENIUM_PLATFORM:" + readPropertyOrEnv("SELENIUM_PLATFORM", "unknown"));
            System.out.println("SELENIUM_BROWSER:" + readPropertyOrEnv("SELENIUM_BROWSER", "unknown"));
            System.out.println("SAUCE_USER_NAME:" + readPropertyOrEnv("SAUCE_USER_NAME", "unknown"));
            System.out.println("SAUCE_API_KEY:" + readPropertyOrEnv("SAUCE_API_KEY", "unknown"));
            driver = new RemoteWebDriver(
                    new URL("http://" + username + ":" + accessKey + "@ondemand.saucelabs.com:80/wd/hub"),
                    capabilities);

            String sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
            System.out.println("SauceOnDemandSessionID=" + sessionId);

            // driver = SeleniumFactory.createWebDriver();
            // cloudbees/saucelabs config
            // DesiredCapabilities capabillities = DesiredCapabilities.firefox();
            // // bug on Actions.moveToElement ==>
            // //
            // http://support.saucelabs.com/entries/21400576-invalidelementstate-could-not-load-native-events-component
            // FirefoxProfile prof = new FirefoxProfile();
            // prof.setEnableNativeEvents(false);
            // capabillities.setCapability("firefox_profile", prof);
            // // capabillities.setCapability("version", "5.0");
            // capabillities.setCapability("platform", Platform.WINDOWS);
            // // 9be6b537-7d6b-4905-8594-fed1b970931f
            // String selenium_url =
            // "http://cb_anthonime:b7a2e206-1091-4556-99b4-6e5c6b3529d5@ondemand.saucelabs.com:80/wd/hub";
            // // String selenium_url =
            // // "http://127.0.0.1:8888/D3Demo.html?gwt.codesvr=127.0.0.1:9997";
            // driver = new RemoteWebDriver(
            // new URL(
            // selenium_url),
            // capabillities);
            // // FIXME:
            webAppUrl = "http://hourly.gwt-d3.appspot.com/";
        }
        // FIXME: change the URL and use the env variable to init the URL, the
        // platform etc...
        // first version
        driver.get(webAppUrl);
    }

    @AfterClass
    public static void tearDown() throws Exception {
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
