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
public class WebDriverFilter2 implements WebDriver, HasInputDevices {
    private WebDriver base;

    public WebDriverFilter2(final WebDriver base) {
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