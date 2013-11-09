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
package com.github.gwtd3.demo.client.testcases.svg;

import java.util.ArrayList;
import java.util.List;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.svg.Line;
import com.github.gwtd3.api.svg.Line.InterpolationMode;
import com.github.gwtd3.api.svg.RadialLine;
import com.github.gwtd3.demo.client.test.AbstractTestCase;
import com.google.gwt.dom.client.Element;

public class TestLine extends AbstractTestCase {

	@Override
	public void doTest(final com.google.gwt.user.client.ui.ComplexPanel sandbox) {
		Line line = D3.svg().line();

		// interpolation
		assertEquals(InterpolationMode.LINEAR, line.interpolate());
		line.interpolate(InterpolationMode.CARDINAL);
		assertEquals(InterpolationMode.CARDINAL, line.interpolate());

		final List<Double> xCapture = new ArrayList<Double>();
		final List<Double> yCapture = new ArrayList<Double>();

		// default x and y function (data must be a
		@SuppressWarnings("unused")
		String d = line.generate(Array.fromObjects(Array.fromInts(0, 0),
				Array.fromInts(1, 1), Array.fromInts(2, 2)));

		// x and y
		line.x(new DatumFunction<Double>() {
			@Override
			public Double apply(final Element context, final Value d,
					final int index) {
				xCapture.add(d.<Coords> as().x);
				return d.<Coords> as().x;
			}
		});

		line.y(new DatumFunction<Double>() {
			@Override
			public Double apply(final Element context, final Value d,
					final int index) {
				yCapture.add(d.<Coords> as().y);
				return d.<Coords> as().y;
			}
		});

		d = line.generate(Array.fromObjects(new Coords(1, 1), new Coords(2, 2),
				new Coords(3, 3)));

		assertEquals(1.0, xCapture.get(0));
		assertEquals(2.0, xCapture.get(1));
		assertEquals(3.0, xCapture.get(2));

		assertEquals(1.0, yCapture.get(0));
		assertEquals(2.0, yCapture.get(1));
		assertEquals(3.0, yCapture.get(2));

		d = line.generate(Array.fromObjects(new Coords(1, 1), new Coords(2, 2),
				new Coords(3, 3)));
		// System.out.println("defined : " + d);

		// x and y constants
		d = line.x(50)
				.y(30)
				.generate(
						Array.fromObjects(new Coords(1, 1), new Coords(2, 2),
								new Coords(3, 3)));
		// System.out.println("defined : " + d);

		line.defined(new DatumFunction<Boolean>() {
			@Override
			public Boolean apply(final Element context, final Value d,
					final int index) {
				// System.out.println(context);
				// System.out.println(d);
				// System.out.println(index);
				return index == 1 ? false : true;
			}
		});
		final Coords counter = new Coords(0, 0);
		// not called
		line.y(new DatumFunction<Double>() {
			@Override
			public Double apply(final Element context, final Value d,
					final int index) {
				counter.y = (counter.y + 1);
				yCapture.add(d.<Coords> as().y);
				return d.<Coords> as().y;
			}
		});

		// does not assertEquals(2, counter.y);
		// Smoke test radial line

		RadialLine radial = D3.svg().radialLine();
		radial.angle(3.5);
		radial.radius(56);

	}

	static class Coords {
		public double x;
		public double y;

		public Coords(final double x, final double y) {
			super();
			this.x = x;
			this.y = y;
		}

		public static Coords get(final double x, final double y) {
			return new Coords(x, y);
		}
	}
}
