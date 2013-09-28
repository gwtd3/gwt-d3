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
import com.github.gwtd3.api.core.Formatter;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.dsv.DsvCallback;
import com.github.gwtd3.api.dsv.DsvObjectAccessor;
import com.github.gwtd3.api.dsv.DsvRow;
import com.github.gwtd3.api.dsv.DsvRows;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.scales.LinearScale;
import com.github.gwtd3.api.scales.OrdinalScale;
import com.github.gwtd3.api.svg.Axis;
import com.github.gwtd3.api.svg.Axis.Orientation;
import com.github.gwtd3.demo.client.DemoCase;
import com.github.gwtd3.demo.client.Factory;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.FlowPanel;

public class BarChart extends FlowPanel implements DemoCase {

	public interface Bundle extends ClientBundle {
		public static final Bundle INSTANCE = GWT.create(Bundle.class);

		@Source("BarChartStyles.css")
		public MyResources css();
	}

	interface MyResources extends CssResource {

		String axis();

		String x();

		String y();

		String bar();

	}

	public BarChart() {
		super();
		Bundle.INSTANCE.css().ensureInjected();
	}

	@Override
	public void start() {

		final MyResources css = Bundle.INSTANCE.css();

		final Margin margin = new Margin(20, 20, 30, 40);
		final int width = 960 - margin.left - margin.right;
		final int height = 500 - margin.top - margin.bottom;

		final Formatter formatPercent = D3.format(".0%");

		final OrdinalScale x = D3.scale.ordinal().rangeRoundBands(
				Array.fromDoubles(0, width), .1);

		final LinearScale y = D3.scale.linear()
				.range(Array.fromInts(height, 0));

		final Axis xAxis = D3.svg().axis().scale(x).orient(Orientation.BOTTOM);

		final Axis yAxis = D3.svg().axis().scale(y).orient(Orientation.LEFT)
				.tickFormat(formatPercent);

		final Selection svg = D3
				.select(this)
				.append("svg")
				.attr("width", width + margin.left + margin.right)
				.attr("height", height + margin.top + margin.bottom)
				.append("g")
				.attr("transform",
						"translate(" + margin.left + "," + margin.top + ")");

		D3.tsv("demo-data/data.tsv", new DsvObjectAccessor<Data>() {
			@Override
			public Data apply(final DsvRow row, final int index) {
				return new Data(row.get("letter").asString(), row.get(
						"frequency").asDouble());
			}
		}, new DsvCallback<Data>() {
			@Override
			public void get(final JavaScriptObject error,
					final DsvRows<Data> data) {
				x.domain(data.map(new ForEachCallback<String>() {
					@Override
					public String forEach(final Object thisArg,
							final Value element, final int index,
							final Array<?> array) {
						return element.as(Data.class).getLetter();
					}
				}));
				y.domain(Array.fromDoubles(0,
						Arrays.max(data, new NumericForEachCallback() {
							@Override
							public double forEach(final Object thisArg,
									final Value element, final int index,
									final Array<?> array) {
								return element.as(Data.class).getFrequency();
							}
						}).asDouble()));

				svg.append("g").attr("class", css.x() + " " + css.axis())
						.attr("transform", "translate(0," + height + ")")
						.call(xAxis);

				svg.append("g").attr("class", css.y() + " " + css.axis())
						.call(yAxis).append("text")
						.attr("transform", "rotate(-90)").attr("y", 6)
						.attr("dy", ".71em").style("text-anchor", "end")
						.text("Frequency");

				svg.selectAll("." + css.bar())
						.data(data.cast())
						.enter()
						.append("rect")
						.attr("class", css.bar())
						.attr("x", new DatumFunction<Double>() {
							@Override
							public Double apply(final Element context,
									final Value d, final int index) {
								return x.apply(d.<Data> as().getLetter())
										.asDouble();
							}
						}).attr("width", x.rangeBand())
						.attr("y", new DatumFunction<Double>() {
							@Override
							public Double apply(final Element context,
									final Value d, final int index) {
								return y.apply(d.<Data> as().getFrequency())
										.asDouble();
							}
						}).attr("height", new DatumFunction<Double>() {
							@Override
							public Double apply(final Element context,
									final Value d, final int index) {
								return height
										- y.apply(d.<Data> as().getFrequency())
												.asDouble();
							}
						});
			}

		});
	}

	@Override
	public void stop() {
	}

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
		private final String letter;
		private final double frequency;

		public Data(final String letter, final double frequency) {
			super();

			this.letter = letter;
			this.frequency = frequency;
		}

		public String getLetter() {
			return letter;
		}

		public double getFrequency() {
			return frequency;
		}
	}

	public static Factory factory() {
		return new Factory() {
			@Override
			public DemoCase newInstance() {
				return new BarChart();
			}
		};
	}
}
