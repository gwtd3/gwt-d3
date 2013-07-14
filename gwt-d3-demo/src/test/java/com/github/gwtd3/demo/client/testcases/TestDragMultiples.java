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

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.openqa.selenium.interactions.Actions;

import com.github.gwtd3.demo.client.pageobjects.DemoApplication;
import com.github.gwtd3.demo.client.pageobjects.DemoDragMultiple.Circle;

public class TestDragMultiples extends AbstractSeleniumTest {

	@Test
	public void testDragWorks() throws Exception {
		// go to dragMultiples
		Circle circle = DemoApplication.getInstance(driver).revealDemoDragMultiple()
				// get the 5th circle
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
