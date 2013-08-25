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
package com.github.gwtd3.demo.client.democases.geom;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Transition.EventType;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.geo.ConicProjection;
import com.github.gwtd3.api.geom.Polygon;
import com.github.gwtd3.demo.client.DemoCase;
import com.github.gwtd3.demo.client.Factory;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * Original demo is <a href="http://bl.ocks.org/mbostock/3808218">here</a>
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class ShapeTweeningDemo extends FlowPanel implements DemoCase {

	public interface Bundle extends ClientBundle {
		public static final Bundle INSTANCE = GWT.create(Bundle.class);

		@Source("ShapeTweeningDemo.css")
		public MyResources css();

		@Source("california.json")
		public TextResource californiaJson();
	}

	interface MyResources extends CssResource {
		String demo();

		String intersection();
	}

	private Selection svg;
	private ConicProjection projection;
	private Selection path;

	/**
	 * 
	 */
	public ShapeTweeningDemo() {
		super();

		Bundle.INSTANCE.css().ensureInjected();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.gwtd3.demo.client.D3Demo#start()
	 */
	@Override
	public void start() {
		int width = 960, height = 500;

		projection = D3.geo().albers().rotate(120, 0).center(0, 37.7)
				.scale(2700);

		svg = D3.select(this).append("svg").attr("width", width)
				.attr("height", height)
				.attr("class", Bundle.INSTANCE.css().demo());

		Array<Array<Double>> coordinates0 = parseJSONToArray();
		Array<Array<Double>> coordinates1 = circle(coordinates0);

		path = svg.append("path");
		String d0 = "M" + join(coordinates0,"L") + "Z";
		String d1 = "M" + join(coordinates1,"L") + "Z";


		// FIXME: polygon.clip exemple
		// Polygon intersection = D3.geom().polygon(coordinates0)
		// .clip(coordinates1);
		// System.out.println(intersection.length());
		// String d2 = "M" + intersection.join("L") + "Z";
		// System.out.println(d2);
		// svg.append("path").attr("d", d2)
		// .attr("class", Bundle.INSTANCE.css().intersection());

		loop(d0, d1);
	}

	private String join(final Array<Array<Double>> coords, final String delimiter) {
		String s = "";
		for (int i = 0; i<(coords.length()-1); i++) {
			s += coords.get(i).getNumber(0) + "," + coords.get(i).getNumber(1) + "L";
		}
		s += coords.get(coords.length()-1).getNumber(0) + "," + coords.get(coords.length()-1).getNumber(1);
		return s;
	}

	private Array<Array<Double>> parseJSONToArray() {
		JSONValue value = JSONParser.parseLenient(Bundle.INSTANCE
				.californiaJson().getText());
		JSONArray array = value.isObject().get("coordinates").isArray();
		return array.get(0).isArray().getJavaScriptObject().<Array<?>> cast()
				.map(projection);

	}

	private void loop(final String d0, final String d1) {

		path.attr("d", d0).transition().duration(5000).attr("d", d1)
		.transition().delay(5000).attr("d", d0)
		.each(EventType.END, new DatumFunction<Void>() {
			@Override
			public Void apply(final Element context, final Value d, final int index) {
				loop(d0, d1);
				return null;
			}
		});

	}

	/**
	 * Transform the provided array of coords to an array of coords that reflect
	 * a circle
	 * 
	 * @param coordinates
	 * @return the circle coordinates
	 */
	private Array<Array<Double>> circle(final Array<Array<Double>> coordinates) {
		Array<Array<Double>> circle = Array.create();
		int length = 0;
		Array<Integer> lengths = Array.fromInts(length);
		Polygon polygon = D3.geom().polygon(coordinates);
		Array<Double> p0 = coordinates.getValue(0).<Array<Double>> as();
		int i = 0;
		int n = coordinates.length();

		// Compute the distances of each coordinate.
		while (++i < n) {
			Array<Double> p1 = coordinates.getValue(i).as();
			double x = p1.getNumber(0) - p0.getNumber(0);
			double y = p1.getNumber(1) - p0.getNumber(1);
			lengths.push(length += Math.sqrt((x * x) + (y * y)));
			p0 = p1;
		}

		double area = polygon.area();
		double radius = Math.sqrt(Math.abs(area) / Math.PI);
		Array<?> centroid = polygon.centroid(-1 / (6 * area));
		double angleOffset = -Math.PI / 2;
		double k = (2 * Math.PI) / lengths.getNumber(lengths.length() - 1);

		// Compute points along the circle’s circumference at equivalent
		// distances.
		i = -1;
		while (++i < n) {
			double angle = angleOffset + (lengths.getNumber(i) * k);
			circle.push(Array.fromDoubles(
					centroid.getNumber(0) + (radius * Math.cos(angle)),
					centroid.getNumber(1) + (radius * Math.sin(angle))));
		}

		return circle;
	}

	@Override
	public void stop() {
	}

	public static Factory factory() {
		return new Factory() {
			@Override
			public DemoCase newInstance() {
				return new ShapeTweeningDemo();
			}
		};
	}
}
