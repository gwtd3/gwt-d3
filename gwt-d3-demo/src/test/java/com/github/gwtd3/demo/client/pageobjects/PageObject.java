package com.github.gwtd3.demo.client.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Preconditions;

/**
 * Base class for a page object.
 * <p>
 * {@link PageObject}s are hierarchized in a tree like structure, where the top
 * level {@link PageObject} represents the application page.
 * <p>
 * PageObjects provides convenient find methods.
 * 
 * 
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 * @param <P>
 *            the parent of this {@link PageObject}
 */
public class PageObject<P extends PageObject<?>> {
	protected WebDriver driver;
	protected P parent;

	/**
	 * Construct a top-level page object, without any parent, i.e an
	 * application.
	 * 
	 * 
	 * @param driver
	 * @param parent
	 */
	@SuppressWarnings("unchecked")
	protected PageObject(final WebDriver driver) {
		super();
		this.driver = driver;
		this.parent = (P) this;
	}

	/**
	 * Construct a page object with a given parent.
	 * 
	 * @param parent
	 */
	protected PageObject(P parent) {
		super();
		Preconditions.checkNotNull(parent);
		this.parent = parent;
		this.driver = parent.driver;
	}

	protected WebElement find(final By by) {
		if (parent == this) {
			return driver.findElement(by);
		}
		else {
			return parent.find(by);
		}
	}

	protected List<WebElement> findAll(By by) {
		if (parent == this) {
			return driver.findElements(by);
		}
		else {
			return parent.findAll(by);
		}
	}

	public P getParent() {
		return parent;
	}

	protected WebElement findClickable(final By by, final int waitSec) {
		WebDriverWait w = new WebDriverWait(driver, waitSec);
		w.until(ExpectedConditions.elementToBeClickable(by));
		return driver.findElement(by);
	}

	protected WebElement findById(final String id) {
		return find(By.id(id));
	}

	protected WebElement findClickableById(final String id, final int waitSec) {
		return findClickable(By.id(id), waitSec);
	}

}
