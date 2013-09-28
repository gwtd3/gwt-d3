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
package com.github.gwtd3.demo.client.democases;

import com.github.gwtd3.api.Arrays;
import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.arrays.ForEachCallback;
import com.github.gwtd3.api.arrays.NumericForEachCallback;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.dsv.DsvCallback;
import com.github.gwtd3.api.dsv.DsvObjectAccessor;
import com.github.gwtd3.api.dsv.DsvRow;
import com.github.gwtd3.api.dsv.DsvRows;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.scales.LinearScale;
import com.github.gwtd3.api.svg.Area;
import com.github.gwtd3.api.svg.Area.InterpolationMode;
import com.github.gwtd3.api.svg.Axis;
import com.github.gwtd3.api.svg.Axis.Orientation;
import com.github.gwtd3.api.svg.Brush;
import com.github.gwtd3.api.svg.Brush.BrushEvent;
import com.github.gwtd3.api.time.TimeFormat;
import com.github.gwtd3.api.time.TimeScale;
import com.github.gwtd3.demo.client.DemoCase;
import com.github.gwtd3.demo.client.Factory;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.FlowPanel;

public class FocusAndContext extends FlowPanel implements DemoCase {

	public interface Bundle extends ClientBundle {
		public static final Bundle INSTANCE = GWT.create(Bundle.class);

		@Source("FocusAndContextStyles.css")
		public MyResources css();
	}

	interface MyResources extends CssResource {

		String brush();

		String axis();
	}

	public FocusAndContext() {
		super();
		Bundle.INSTANCE.css().ensureInjected();
	}

	@Override
	public void start() {

		final MyResources css = Bundle.INSTANCE.css();

		final Margin margin = new Margin(10, 10, 100, 40);
		final Margin margin2 = new Margin(430, 10, 20, 40);
		final int width = 960 - margin.left - margin.right;
		final int height = 500 - margin.top - margin.bottom;
		final int height2 = 500 - margin2.top - margin2.bottom;

		final TimeFormat dateFormat = D3.time().format("%b %Y");

		final TimeScale x = D3.time().scale().range(Array.fromInts(0, width));
		final TimeScale x2 = D3.time().scale().range(Array.fromInts(0, width));
		final LinearScale y = D3.scale.linear()
				.range(Array.fromInts(height, 0));
		final LinearScale y2 = D3.scale.linear().range(
				Array.fromInts(height2, 0));

		final Axis xAxis = D3.svg().axis().scale(x).orient(Orientation.BOTTOM);
		final Axis xAxis2 = D3.svg().axis().scale(x2)
				.orient(Orientation.BOTTOM);
		final Axis yAxis = D3.svg().axis().scale(y).orient(Orientation.LEFT);

		final Area area = D3.svg().area()
				.interpolate(InterpolationMode.MONOTONE)
				.x(new DatumFunction<Double>() {
					@Override
					public Double apply(final Element context, final Value d,
							final int index) {
						return x.apply(d.<Data> as().getDate()).asDouble();
					}
				}).y0(height).y1(new DatumFunction<Double>() {
					@Override
					public Double apply(final Element context, final Value d,
							final int index) {
						return y.apply(d.<Data> as().getPrice()).asDouble();
					}
				});

		final Area area2 = D3.svg().area()
				.interpolate(InterpolationMode.MONOTONE)
				.x(new DatumFunction<Double>() {
					@Override
					public Double apply(final Element context, final Value d,
							final int index) {
						return x2.apply(d.<Data> as().getDate()).asDouble();
					}
				}).y0(height2).y1(new DatumFunction<Double>() {
					@Override
					public Double apply(final Element context, final Value d,
							final int index) {
						return y2.apply(d.<Data> as().getPrice()).asDouble();
					}
				});

		Selection svg = D3.select(FocusAndContext.this).append("svg")
				.attr("width", width + margin.left + margin.right)
				.attr("height", height + margin.top + margin.bottom);

		svg.append("defs").append("clipPath").attr("id", "clip").append("rect")
				.attr("width", width).attr("height", height);

		final Selection focus = svg.append("g").attr("transform",
				"translate(" + margin.left + "," + margin.top + ")");

		final Selection context = svg.append("g").attr("transform",
				"translate(" + margin2.left + "," + margin2.top + ")");

		final Brush brush = D3.svg().brush().x(x2);
		brush.on(BrushEvent.BRUSH, new DatumFunction<Void>() {
			@Override
			public Void apply(Element context, Value d, int index) {
				x.domain(brush.empty() ? x2.domain() : brush.extent());
				focus.select("path").attr("d", area);
				focus.select(".x." + css.axis()).call(xAxis);
				return null;
			};
		});

		D3.csv("demo-data/sp500.csv", new DsvObjectAccessor<Data>() {
			@Override
			public Data apply(final DsvRow row, final int index) {
				return new Data(dateFormat.parse(row.get("date").asString()),
						row.get("price").asDouble());
			}
		}, new DsvCallback<Data>() {
			@Override
			public void get(final JavaScriptObject error,
					final DsvRows<Data> data) {
				x.domain(Arrays.extent(data.map(new ForEachCallback<JsDate>() {
					@Override
					public JsDate forEach(final Object thisArg, final Value d,
							final int index, final Array<?> array) {
						return d.as(Data.class).getDate();
					}
				})));
				y.domain(Array.fromDoubles(0,
						Arrays.max(data, new NumericForEachCallback() {
							@Override
							public double forEach(final Object thisArg,
									final Value d, final int index,
									final Array<?> array) {
								return d.as(Data.class).getPrice();
							}
						}).asInt()));
				x2.domain(x.domain());
				y2.domain(y.domain());

				focus.append("path").datum(data)
						.attr("clip-path", "url(#clip)").attr("d", area);

				focus.append("g").attr("class", "x " + css.axis())
						.attr("transform", "translate(0," + height + ")")
						.call(xAxis);

				focus.append("g").attr("class", "y " + css.axis()).call(yAxis);

				context.append("path").datum(data).attr("d", area2);

				context.append("g").attr("class", "x " + css.axis())
						.attr("transform", "translate(0," + height2 + ")")
						.call(xAxis2);

				context.append("g").attr("class", "x " + css.brush())
						.call(brush).selectAll("rect").attr("y", -6)
						.attr("height", height2 + 7);

			}
		});

	}

	@Override
	public void stop() {
	}

	public final static native String dump(JavaScriptObject obj) /*-{
		var dump = "";
		for ( var p in obj) {
			dump += p + " : " + obj[p] + "\n";
		}
		return dump;
	}-*/;

	private static class Margin {
		public final int top;
		public final int right;
		public final int bottom;
		public final int left;

		public Margin(final int top, final int right, final int bottom,
				final int left) {
			super();
			this.top = top;
			this.right = right;
			this.bottom = bottom;
			this.left = left;
		}
	}

	private static class Data {
		private final JsDate date;

		private final double price;

		public Data(final JsDate date, final double price) {
			super();
			this.date = date;
			this.price = price;
		}

		public JsDate getDate() {
			return date;
		}

		public double getPrice() {
			return price;
		}
	}

	public static Factory factory() {
		return new Factory() {
			@Override
			public DemoCase newInstance() {
				return new FocusAndContext();
			}
		};
	}
}
