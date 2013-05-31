/**
 * 
 */
package com.github.gwtd3.demo.client.testcases.d3;

import com.github.gwtd3.api.D3;
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
	 * com.github.gwtd3.demo.client.tests.UnitTest#doTest(com.google.gwt.user.client
	 * .ui. RootPanel)
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
		HSLColor hsl = D3.hsl("red");
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

		HSLColor hsl2 = D3.hsl(120, 1, 0.5);
		System.out.println(hsl2.toHexaString());
	}

	/**
	 * 
	 */
	private void rgb() {
		RGBColor rgb = D3.rgb("#ff0000");
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

		RGBColor rgb2 = D3.rgb(0, 0, 255);
		assertEquals(0, rgb2.r());
		assertEquals(0, rgb2.g());
		assertEquals(255, rgb2.b());
		System.out.println(rgb2.toHexaString());
	}

}
