package com.github.gwtd3.demo.client.testcases.svg;

import java.util.ArrayList;
import java.util.List;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.JsArrays;
import com.github.gwtd3.api.core.Datum;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.svg.Area;
import com.github.gwtd3.api.svg.Area.InterpolationMode;
import com.github.gwtd3.demo.client.test.AbstractTestCase;

import com.google.gwt.dom.client.Element;

public class TestArea extends AbstractTestCase {

    @Override
    public void doTest(final com.google.gwt.user.client.ui.ComplexPanel sandbox) {
        Area area = D3.svg().area();

        // interpolator
        assertEquals(InterpolationMode.LINEAR, area.interpolate());
        area.interpolate(InterpolationMode.CARDINAL);
        assertEquals(InterpolationMode.CARDINAL, area.interpolate());

        final List<Double> xCapture = new ArrayList<Double>();
        final List<Double> yCapture = new ArrayList<Double>();

        // default x and y function (data must be a
        String d = area.apply(JsArrays.asJsArray(JsArrays.asJsArray(0, 0), JsArrays.asJsArray(1, 1),
                JsArrays.asJsArray(2, 2)));

        // x and y
        area.x(new DatumFunction<Double>() {
            @Override
            public Double apply(final Element context, final Datum d, final int index) {
                xCapture.add(d.<Coords> as().x);
                return d.<Coords> as().x;
            }
        });

        area.x0(new DatumFunction<Double>() {
            @Override
            public Double apply(final Element context, final Datum d, final int index) {
                xCapture.add(d.<Coords> as().x);
                return d.<Coords> as().x;
            }
        });

        area.x1(new DatumFunction<Double>() {
            @Override
            public Double apply(final Element context, final Datum d, final int index) {
                return d.<Coords> as().x;
            }
        });

        area.y(new DatumFunction<Double>() {
            @Override
            public Double apply(final Element context, final Datum d, final int index) {
                return d.<Coords> as().y;
            }
        });

        area.y0(new DatumFunction<Double>() {
            @Override
            public Double apply(final Element context, final Datum d, final int index) {
                yCapture.add(d.<Coords> as().y);
                return d.<Coords> as().y;
            }
        });

        area.y1(new DatumFunction<Double>() {
            @Override
            public Double apply(final Element context, final Datum d, final int index) {
                return d.<Coords> as().y;
            }
        });

        d = area.apply(JsArrays.asJsArray(new Coords(1, 1), new Coords(2, 2), new Coords(3, 3)));
        assertEquals(3, xCapture.size());
        assertEquals(1.0, xCapture.get(0));
        assertEquals(2.0, xCapture.get(1));
        assertEquals(3.0, xCapture.get(2));

        assertEquals(3, yCapture.size());
        assertEquals(1.0, yCapture.get(0));
        assertEquals(2.0, yCapture.get(1));
        assertEquals(3.0, yCapture.get(2));

        d = area.apply(JsArrays.asJsArray(new Coords(1, 1), new Coords(2, 2), new Coords(3, 3)));
//        System.out.println("defined : " + d);

        // x and y constants
        d = area.x(50).y(30).x0(20)
                .x1(22).y0(27).y1(35).apply(JsArrays.asJsArray(new Coords(1, 1), new Coords(2, 2), new Coords(3, 3)));
//        System.out.println("defined : " + d);

        // TODO: tension

        // defined : it does not seem to work
        area.defined(new DatumFunction<Boolean>() {
            @Override
            public Boolean apply(final Element context, final Datum d, final int index) {
//                System.out.println(context);
//                System.out.println(d);
//                System.out.println(index);
                return index == 1 ? false : true;
            }
        });
        final Coords counter = new Coords(0, 0);
        // not called
        area.y(new DatumFunction<Double>() {
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
