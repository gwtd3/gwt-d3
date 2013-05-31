package com.github.gwtd3.demo.client.testcases.d3;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.interpolators.Interpolator;
import com.github.gwtd3.demo.client.test.AbstractTestCase;

import com.google.gwt.user.client.ui.ComplexPanel;

public class TestInterpolators extends AbstractTestCase {

	@Override
	public void doTest(final ComplexPanel sandbox) {
		// string
		Interpolator<String> strInterpolator = D3.interpolate("Saw 10 (movie)", "Saw 20 (movie)");
		assertEquals("Saw 15 (movie)", strInterpolator.interpolate(0.5));

		// boolean
		Interpolator<Boolean> booleanInterpolator = D3.interpolate(true, false);
		assertEquals(true, (boolean) booleanInterpolator.interpolate(0));
		assertEquals(false, (boolean) booleanInterpolator.interpolate(0.5));
		assertEquals(false, (boolean) booleanInterpolator.interpolate(1));

		// byte
		Interpolator<Byte> byteInterpolator = D3.interpolate((byte) 10, (byte) 20);
		assertEquals(10, (byte) byteInterpolator.interpolate(0));
		assertEquals(12, (byte) byteInterpolator.interpolate(0.25));
		assertEquals(20, (byte) byteInterpolator.interpolate(1));

		// char
		Interpolator<Character> charInterpolator = D3.interpolate('a', 'j');
		assertEquals('a', (char) charInterpolator.interpolate(0));
		assertEquals('c', (char) charInterpolator.interpolate(0.25));
		assertEquals('j', (char) charInterpolator.interpolate(1));

		// double
		Interpolator<Double> doubleInterpolator = D3.interpolate(0.1, 0.2);
		assertEquals(0.1, doubleInterpolator.interpolate(0), 0.000001);
		assertEquals(0.125, doubleInterpolator.interpolate(0.25), 0.000001);
		assertEquals(0.2, doubleInterpolator.interpolate(1), 0.000001);
		// float
		Interpolator<Float> floatInterpolator = D3.interpolate(0.1f, 0.2f);
		assertEquals(0.1f, floatInterpolator.interpolate(0), 0.000001);
		assertEquals(0.125f, floatInterpolator.interpolate(0.25), 0.000001);
		assertEquals(0.2f, floatInterpolator.interpolate(1), 0.000001);
		// int
		Interpolator<Integer> intInterpolator = D3.interpolate(10, 20);
		assertEquals(10, intInterpolator.interpolate(0).intValue());
		assertEquals(12, intInterpolator.interpolate(0.25).intValue());
		assertEquals(20, intInterpolator.interpolate(1).intValue());
		//
		// long
		Interpolator<Long> longInterpolator = D3.interpolate(10L, 20L);
		assertEquals(10L, (long) longInterpolator.interpolate(0));
		assertEquals(12L, (long) longInterpolator.interpolate(0.25));
		assertEquals(20L, (long) longInterpolator.interpolate(1));

		// short
		Interpolator<Short> shortInterpolator = D3.interpolate((short) 10, (short) 20);
		assertEquals(10, (short) shortInterpolator.interpolate(0));
		assertEquals(12, (short) shortInterpolator.interpolate(0.25));
		assertEquals(20, (short) shortInterpolator.interpolate(1));

	}
}
