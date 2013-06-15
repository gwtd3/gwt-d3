package com.github.gwtd3.demo.client.testcases.svg;

import java.util.ArrayList;
import java.util.List;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.JsArrays;
import com.github.gwtd3.api.core.Datum;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.svg.Line;
import com.github.gwtd3.api.svg.Line.InterpolationMode;
import com.github.gwtd3.demo.client.test.AbstractTestCase;
import com.google.gwt.dom.client.Element;

public class TestLine extends AbstractTestCase {

	@Override
	public void doTest(final com.google.gwt.user.client.ui.ComplexPanel sandbox) {
		Line line = D3.svg().line();

		// interpolator
		assertEquals(InterpolationMode.LINEAR, line.interpolate());
		line.interpolate(InterpolationMode.CARDINAL);
		assertEquals(InterpolationMode.CARDINAL, line.interpolate());

		final List<Double> xCapture = new ArrayList<Double>();
		final List<Double> yCapture = new ArrayList<Double>();

		// default x and y function (data must be a
		String d = line.generate(JsArrays.asJsArray(JsArrays.asJsArray(0, 0), JsArrays.asJsArray(1, 1),
				JsArrays.asJsArray(2, 2)));

		// x and y
		line.x(new DatumFunction<Double>() {
			@Override
			public Double apply(final Element context, final Datum d, final int index) {
				xCapture.add(d.<Coords> as().x);
				return d.<Coords> as().x;
			}
		});

		line.y(new DatumFunction<Double>() {
			@Override
			public Double apply(final Element context, final Datum d, final int index) {
				yCapture.add(d.<Coords> as().y);
				return d.<Coords> as().y;
			}
		});

		d = line.generate(JsArrays.asJsArray(new Coords(1, 1), new Coords(2, 2), new Coords(3, 3)));

		assertEquals(1.0, xCapture.get(0));
		assertEquals(2.0, xCapture.get(1));
		assertEquals(3.0, xCapture.get(2));

		assertEquals(1.0, yCapture.get(0));
		assertEquals(2.0, yCapture.get(1));
		assertEquals(3.0, yCapture.get(2));

		d = line.generate(JsArrays.asJsArray(new Coords(1, 1), new Coords(2, 2), new Coords(3, 3)));
		// System.out.println("defined : " + d);

		// x and y constants
		d = line.x(50).y(30).generate(JsArrays.asJsArray(new Coords(1, 1), new Coords(2, 2), new Coords(3, 3)));
		// System.out.println("defined : " + d);

		// TODO: tension

		line.defined(new DatumFunction<Boolean>() {
			@Override
			public Boolean apply(final Element context, final Datum d, final int index) {
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
			public Double apply(final Element context, final Datum d, final int index) {
				counter.y = (counter.y + 1);
				yCapture.add(d.<Coords> as().y);
				return d.<Coords> as().y;
			}
		});

		// does not assertEquals(2, counter.y);

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
