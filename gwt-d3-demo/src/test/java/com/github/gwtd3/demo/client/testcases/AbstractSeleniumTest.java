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
        if (driver != null) {
        driver.quit();
        driver = null;
    }
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
