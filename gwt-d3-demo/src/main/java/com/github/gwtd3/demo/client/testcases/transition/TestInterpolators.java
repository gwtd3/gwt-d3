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
package com.github.gwtd3.demo.client.testcases.transition;

import com.github.gwtd3.api.Coords;
import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.core.HSLColor;
import com.github.gwtd3.api.core.RGBColor;
import com.github.gwtd3.api.core.Transform;
import com.github.gwtd3.api.interpolators.AbstractInterpolatorFactory;
import com.github.gwtd3.api.interpolators.CallableInterpolator;
import com.github.gwtd3.api.interpolators.Interpolator;
import com.github.gwtd3.api.interpolators.InterpolatorFactory;
import com.github.gwtd3.demo.client.test.AbstractTestCase;
import com.google.gwt.user.client.ui.ComplexPanel;

public class TestInterpolators extends AbstractTestCase {

	@Override
	public void doTest(final ComplexPanel sandbox) {
		testD3InterpolateNumber();
		testD3InterpolateRound();
		testD3InterpolateString();
		testD3InterpolateRgb();
		testD3InterpolateHsl();
		// testD3InterpolateHcl();
		// testD3InterpolateLab();
		testD3InterpolateObject();
		testD3InterpolateArray();
		testD3InterpolateTransform();

		testD3Interpolators();
	}

	private void testD3InterpolateTransform() {
		Interpolator<Transform> interpolator = D3.interpolateTransform(Transform.parse("rotate(40)"),Transform.parse("rotate(80)"));
		assertEquals(40d, interpolator.interpolate(0).rotate(), 0.0001d);
		assertEquals(60d, interpolator.interpolate(0.5).rotate(), 0.0001d);
		assertEquals(80d, interpolator.interpolate(1).rotate(), 0.0001d);
	}

	private void testD3InterpolateArray() {
		Interpolator<Array<?>> interpolator = D3.interpolateArray(Array.fromInts(10, 100), Array.fromInts(20, 200, 2000));
		assertEquals(15d, interpolator.interpolate(0.5).getNumber(0));
		assertEquals(150d, interpolator.interpolate(0.5).getNumber(1));
		assertEquals(2000d, interpolator.interpolate(0.5).getNumber(2));
	}

	private void testD3InterpolateObject() {
		// use coords
		Coords a = Coords.create(10, 100);
		Coords b = Coords.create(20, 200);
		// add an additional property with value 30
		b.<Array<?>> cast().setLength(30);
		Interpolator<Coords> interpolator = D3.interpolateObject(a, b);

		assertEquals(15.0, interpolator.interpolate(0.5).x());
		assertEquals(150.0, interpolator.interpolate(0.5).y());
		assertEquals(30, interpolator.interpolate(0.5).<Array> cast().length());
	}

	private void testD3InterpolateRgb() {
		String a = "#ff0000";
		String b = "#0000ff";
		// string variant
		Interpolator<RGBColor> rgb = D3.interpolateRgb(a, b);
		assertEquals(255, rgb.interpolate(0).r());
		assertEquals(0, rgb.interpolate(0).g());
		assertEquals(0, rgb.interpolate(0).b());

		//
		assertEquals(0, rgb.interpolate(1).r());
		assertEquals(0, rgb.interpolate(1).g());
		assertEquals(255, rgb.interpolate(1).b());

		// color variant
		rgb = D3.interpolateRgb(D3.rgb(255, 0, 0), D3.rgb(0, 0, 255));
		assertEquals(255, rgb.interpolate(0).r());
		assertEquals(0, rgb.interpolate(0).g());
		assertEquals(0, rgb.interpolate(0).b());

		assertEquals(0, rgb.interpolate(1).r());
		assertEquals(0, rgb.interpolate(1).g());
		assertEquals(255, rgb.interpolate(1).b());

		// interpolator factory string variant
		// rgb = D3.interpolateRgb.create(a, b);
		// assertEquals(255, rgb.interpolate(0).r());
		// assertEquals(0, rgb.interpolate(0).g());
		// assertEquals(0, rgb.interpolate(0).b());
		//
		// assertEquals(0, rgb.interpolate(1).r());
		// assertEquals(0, rgb.interpolate(1).g());
		// assertEquals(255, rgb.interpolate(1).b());
		//
		// // interpolator factory color variant
		// rgb = D3.interpolateRgb.create(D3.rgb(255, 0, 0), D3.rgb(0, 0, 255));
		// assertEquals(255, rgb.interpolate(0).r());
		// assertEquals(0, rgb.interpolate(0).g());
		// assertEquals(0, rgb.interpolate(0).b());
		//
		// assertEquals(0, rgb.interpolate(1).r());
		// assertEquals(0, rgb.interpolate(1).g());
		// assertEquals(255, rgb.interpolate(1).b());

	}

	private void testD3InterpolateHsl() {
		String a = "#ff0000";
		String b = "#0000ff";
		// string variant
		Interpolator<HSLColor> interpolator = D3.interpolateHsl(a, b);
		assertEquals(0, interpolator.interpolate(0).h());
		assertEquals(1.0, interpolator.interpolate(0).s());
		assertEquals(0.5, interpolator.interpolate(0).l());

		//
		assertEquals(240, interpolator.interpolate(1).h());
		assertEquals(1.0, interpolator.interpolate(1).s());
		assertEquals(0.5, interpolator.interpolate(1).l());

		// color variant
		interpolator = D3.interpolateHsl(D3.rgb(255, 0, 0), D3.rgb(0, 0, 255));
		assertEquals(0, interpolator.interpolate(0).h());
		assertEquals(1.0, interpolator.interpolate(0).s());
		assertEquals(0.5, interpolator.interpolate(0).l());

		assertEquals(240, interpolator.interpolate(1).h());
		assertEquals(1.0, interpolator.interpolate(1).s());
		assertEquals(0.5, interpolator.interpolate(1).l());

		// interpolator factory string variant
		// rgb = D3.interpolateRgb.create(a, b);
		// assertEquals(255, rgb.interpolate(0).r());
		// assertEquals(0, rgb.interpolate(0).g());
		// assertEquals(0, rgb.interpolate(0).b());
		//
		// assertEquals(0, rgb.interpolate(1).r());
		// assertEquals(0, rgb.interpolate(1).g());
		// assertEquals(255, rgb.interpolate(1).b());
		//
		// // interpolator factory color variant
		// rgb = D3.interpolateRgb.create(D3.rgb(255, 0, 0), D3.rgb(0, 0, 255));
		// assertEquals(255, rgb.interpolate(0).r());
		// assertEquals(0, rgb.interpolate(0).g());
		// assertEquals(0, rgb.interpolate(0).b());
		//
		// assertEquals(0, rgb.interpolate(1).r());
		// assertEquals(0, rgb.interpolate(1).g());
		// assertEquals(255, rgb.interpolate(1).b());

	}

	private void testD3Interpolators() {
		Array<InterpolatorFactory<?>> interpolators = D3.interpolators();
		assertEquals(1, interpolators.length());
		InterpolatorFactory<?> factory = interpolators.get(0);
		Interpolator<?> interpolator = factory.create("10px", "20px");
		String interpolated = interpolator.interpolate(0.5).toString();
		assertEquals("15px", interpolated);

		InterpolatorFactory<Coords> newInterpolator = new AbstractInterpolatorFactory<Coords>() {
			@Override
			public <I> Interpolator<Coords> create(final I a, final I b) {
				if (b instanceof Coords) {
					return new CallableInterpolator<Coords>() {
						@Override
						public Coords interpolate(final double t) {
							return Coords.create(15, 20);
						}
					};
				} else {
					return null;
				}
			}
		};
		// // push one
		// FIXME : adding a new interpolator does not work:
		// cause D3.js cannot call the Java InterpolatorFactory-
		// we should find a way to add the JSO function instead
		// see https://github.com/mbostock/d3/blob/master/src/interpolate/interpolate.js
		// D3.interpolators().push(newInterpolator);
		// assertEquals(2, D3.interpolators().length());
	}

	private void testD3InterpolateString() {
		// string
		Interpolator<String> strInterpolator = D3.interpolateString("Saw 10 (movie)", "Saw 20 (movie)");
		assertEquals("Saw 15 (movie)", strInterpolator.interpolate(0.5));

	}

	private void testD3InterpolateRound() {
		// char
		Interpolator<Character> charInterpolator = D3.interpolateRound('a', 'j');
		assertEquals('a', (char) charInterpolator.interpolate(0));
		assertEquals('c', (char) charInterpolator.interpolate(0.25));
		assertEquals('j', (char) charInterpolator.interpolate(1));

		// int
		Interpolator<Integer> intInterpolator = D3.interpolateRound(10, 20);
		assertEquals(10, (int) intInterpolator.interpolate(0));
		assertEquals(13, (int) intInterpolator.interpolate(0.25));
		assertEquals(20, (int) intInterpolator.interpolate(1));

		// short
		Interpolator<Short> shortInterpolator = D3.interpolateRound((short) 10, (short) 20);
		assertEquals(10, (short) shortInterpolator.interpolate(0));
		assertEquals(13, (short) shortInterpolator.interpolate(0.25));
		assertEquals(20, (short) shortInterpolator.interpolate(1));

		// byte
		Interpolator<Byte> byteInterpolator = D3.interpolateRound((byte) 10, (byte) 20);
		assertEquals(10, (byte) byteInterpolator.interpolate(0));
		assertEquals(13, (byte) byteInterpolator.interpolate(0.25));
		assertEquals(20, (byte) byteInterpolator.interpolate(1));

		// long
		Interpolator<Long> longInterpolator = D3.interpolateRound((long) 10, (long) 20);
		assertEquals(10l, (long) longInterpolator.interpolate(0));
		assertEquals(13l, (long) longInterpolator.interpolate(0.25));
		assertEquals(20l, (long) longInterpolator.interpolate(1));

		// double
		Interpolator<Long> doubleInterpolator = D3.interpolateRound((double) 10, (double) 20);
		assertEquals(10l, (long) doubleInterpolator.interpolate(0));
		assertEquals(13l, (long) doubleInterpolator.interpolate(0.25));
		assertEquals(20l, (long) doubleInterpolator.interpolate(1));

	}

	private void testD3InterpolateNumber() {

		// byte
		Interpolator<Double> byteInterpolator = D3.interpolateNumber((byte) 10, (byte) 20);
		assertEquals(10.0, byteInterpolator.interpolate(0));
		assertEquals(12.5, byteInterpolator.interpolate(0.25));
		assertEquals(20.0, byteInterpolator.interpolate(1));

		// double
		Interpolator<Double> doubleInterpolator = D3.interpolateNumber(0.1, 0.2);
		assertEquals(0.1, doubleInterpolator.interpolate(0), 0.000001);
		assertEquals(0.125, doubleInterpolator.interpolate(0.25), 0.000001);
		assertEquals(0.2, doubleInterpolator.interpolate(1), 0.000001);
		// float
		Interpolator<Double> floatInterpolator = D3.interpolateNumber(0.1f, 0.2f);
		assertEquals(0.1, floatInterpolator.interpolate(0), 0.000001);
		assertEquals(0.125, floatInterpolator.interpolate(0.25), 0.000001);
		assertEquals(0.2, floatInterpolator.interpolate(1), 0.000001);
		// int
		Interpolator<Double> intInterpolator = D3.interpolateNumber(10, 20);
		assertEquals(10.0, intInterpolator.interpolate(0));
		assertEquals(12.5, intInterpolator.interpolate(0.25));
		assertEquals(20.0, intInterpolator.interpolate(1));
		//
		// long
		Interpolator<Double> longInterpolator = D3.interpolateNumber(10L, 20L);
		assertEquals(10.0, longInterpolator.interpolate(0));
		assertEquals(12.5, longInterpolator.interpolate(0.25));
		assertEquals(20.0, longInterpolator.interpolate(1));

		// short
		Interpolator<Double> shortInterpolator = D3.interpolateNumber((short) 10, (short) 20);
		assertEquals(10.0, shortInterpolator.interpolate(0));
		assertEquals(12.5, shortInterpolator.interpolate(0.25));
		assertEquals(20.0, shortInterpolator.interpolate(1));
	}
}
