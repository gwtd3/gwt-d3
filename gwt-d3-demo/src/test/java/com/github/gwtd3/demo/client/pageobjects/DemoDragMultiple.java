package com.github.gwtd3.demo.client.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;

public class DemoDragMultiple extends AbstractDemoPage<DemoDragMultiple> {

	protected DemoDragMultiple(WebDriver driver, DemoApplication parent) {
		super(parent);
	}

	public static class Circle extends ElementPageObject<DemoDragMultiple> {

		public Circle(DemoDragMultiple parent, WebElement element) {
			super(parent, element);
		}

		/**
		 * @return the cx element of any
		 */
		public double cx() {
			return Double.parseDouble(element.getAttribute("cx"));
		}

		public double cy() {
			return Double.parseDouble(element.getAttribute("cy"));
		}

		public Color fillColor() {
			return getAttributeAsColor("fill");
		}
	}

	public Circle getCircle(int i) {
		// generate something like circle:first-child + circle + circle
		return new Circle(this, findAll(By.tagName("circle")).get(i));
	}

}
