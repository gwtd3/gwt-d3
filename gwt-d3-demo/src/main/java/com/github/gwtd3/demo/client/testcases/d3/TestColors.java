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
/**
 * 
 */
package com.github.gwtd3.demo.client.testcases.d3;

import com.github.gwtd3.api.Colors;
import com.github.gwtd3.api.core.HSLColor;
import com.github.gwtd3.api.core.RGBColor;
import com.github.gwtd3.demo.client.test.AbstractTestCase;
import com.google.gwt.user.client.ui.ComplexPanel;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class TestColors extends AbstractTestCase {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.github.gwtd3.demo.client.tests.UnitTest#doTest(com.google.gwt.user
	 * .client .ui. RootPanel)
	 */
	@Override
	public void doTest(final ComplexPanel sandbox) {
		rgb();
		hsl();
	}

	/**
	 * 
	 */
	private void hsl() {
		HSLColor hsl = Colors.hsl("red");
		assertEquals(0, hsl.h());
		assertEquals(1.0, hsl.s());
		assertEquals(0.5, hsl.l());
		hsl = hsl.darker();
		assertTrue(hsl.h() < 255);
		hsl = hsl.brighter();
		assertEquals(0, hsl.h());
		RGBColor rgb = hsl.rgb();
		assertEquals(255, rgb.r());
		assertEquals(0, rgb.g());
		assertEquals(0, rgb.b());

		HSLColor hsl2 = Colors.hsl(120, 1, 0.5);
		System.out.println(hsl2.toHexaString());

		HSLColor hsl3 = Colors.hsl(rgb);
		assertEquals(0, hsl3.h());
		assertEquals(1.0, hsl3.s());
		assertEquals(0.5, hsl3.l());

	}

	/**
	 * 
	 */
	private void rgb() {
		RGBColor rgb = Colors.rgb("#ff0000");
		assertEquals(255, rgb.r());
		assertEquals(0, rgb.g());
		assertEquals(0, rgb.b());
		rgb = rgb.darker();
		assertTrue(rgb.r() < 255);
		rgb = rgb.brighter().brighter();
		assertEquals(255, rgb.r());
		HSLColor hsl = rgb.hsl();
		System.out.println(hsl.h());
		System.out.println(hsl.s());
		System.out.println(hsl.l());

		RGBColor rgb2 = Colors.rgb(0, 0, 255);
		assertEquals(0, rgb2.r());
		assertEquals(0, rgb2.g());
		assertEquals(255, rgb2.b());
		System.out.println(rgb2.toHexaString());

		RGBColor rgb3 = Colors.rgb(hsl);
		assertEquals(255, rgb3.r());
		assertEquals(0, rgb3.g());
		assertEquals(0, rgb3.b());

	}

}
