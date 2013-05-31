package com.saucelabs.selenium.client.embedded_rc;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.HasInputDevices;
import org.openqa.selenium.Keyboard;
import org.openqa.selenium.Mouse;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * 
 */
/**
 * FIXME: this is a copy-paste from
 * https://github.com/infradna/selenium-client-factory/blob/17b465eb1541b3303641c4ed647f8da1cf171ad9/selenium-embedded-
 * rc-driver/src/main/java/com/saucelabs/selenium/client/embedded_rc/WebDriverFilter.java
 * <p>
 * Please see https://github.com/infradna/selenium-client-factory/issues/3
 * 
 * This is waiting the fix to be released
 * 
 * @author Ross Rowe
 * 
 */
public class WebDriverFilter implements WebDriver, HasInputDevices {
    private WebDriver base;

    public WebDriverFilter(final WebDriver base) {
        this.base = base;
    }

    @Override
    public void get(final String s) {
        base.get(s);
    }

    @Override
    public String getCurrentUrl() {
        return base.getCurrentUrl();
    }

    @Override
    public String getTitle() {
        return base.getTitle();
    }

    @Override
    public List<WebElement> findElements(final By by) {
        return base.findElements(by);
    }

    @Override
    public WebElement findElement(final By by) {
        return base.findElement(by);
    }

    @Override
    public String getPageSource() {
        return base.getPageSource();
    }

    @Override
    public void close() {
        base.close();
    }

    @Override
    public void quit() {
        base.quit();
    }

    @Override
    public Set<String> getWindowHandles() {
        return base.getWindowHandles();
    }

    @Override
    public String getWindowHandle() {
        return base.getWindowHandle();
    }

    @Override
    public TargetLocator switchTo() {
        return base.switchTo();
    }

    @Override
    public Navigation navigate() {
        return base.navigate();
    }

    @Override
    public Options manage() {
        return base.manage();
    }

    @Override
    public Keyboard getKeyboard() {
        if (base instanceof HasInputDevices) {
            return ((HasInputDevices) base).getKeyboard();
        } else {
            return null;
        }
    }

    @Override
    public Mouse getMouse() {
        if (base instanceof HasInputDevices) {
            return ((HasInputDevices) base).getMouse();
        } else {
            return null;
        }
    }
}