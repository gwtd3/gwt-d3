package com.github.gwtd3.demo.client.testcases;

import org.assertj.core.api.Assertions;
import com.github.gwtd3.demo.client.pageobjects.DemoApplication;
import com.github.gwtd3.demo.client.pageobjects.DemoDragMultiple.Circle;
import org.junit.Test;
import org.openqa.selenium.interactions.Actions;

public class TestDragMultiples extends AbstractSeleniumTest {

	@Test
	public void testDragWorks() throws Exception {
		// go to dragMultiples
		Circle circle = DemoApplication.getInstance(driver).revealDemoDragMultiple()
				// get the nth circle
				.getCircle(5);
		// assert it's black
		assertThat(circle.fillColor()).isNull();
		// get its initial position
		double x = circle.cx();
		double y = circle.cy();
		// hold mouse down on the circle
		Actions a = new Actions(driver);
		a.moveToElement(circle.getElement()).clickAndHold().build().perform();
		// assert the circle is red
		assertThat(circle.fillColor()).isEqualTo("red");
		// hold mmouse down, mouse move
		a.moveByOffset(10, 10).perform();
		// assert the circle is green
		assertThat(circle.fillColor()).isEqualTo("green");
		// assert the current circle position is diff than initial position
		Assertions.assertThat(circle.cx()).isEqualTo(x + 10);
		Assertions.assertThat(circle.cy()).isEqualTo(y + 10);
		x = circle.cx();
		y = circle.cy();
		// release mouse
		a.release().perform();
		// assert the circle is black
		assertThat(circle.fillColor()).isNull();
		// assert the current position is equals to previous
		Assertions.assertThat(circle.cx()).isEqualTo(x);
		Assertions.assertThat(circle.cy()).isEqualTo(y);
		// move the mouse
		a.moveByOffset(10, 10).perform();
		// assert the positoin did not changed
		Assertions.assertThat(circle.cx()).isEqualTo(x);
		Assertions.assertThat(circle.cy()).isEqualTo(y);

	}
}
