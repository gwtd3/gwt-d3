package com.github.gwtd3.demo.client.democases;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.JsArrays;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.arrays.NumericForEachCallback;
import com.github.gwtd3.api.core.Datum;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Transition;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.dsv.DsvCallback;
import com.github.gwtd3.api.dsv.DsvObjectAccessor;
import com.github.gwtd3.api.dsv.DsvRow;
import com.github.gwtd3.api.dsv.DsvRows;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.scales.LinearScale;
import com.github.gwtd3.api.svg.Area;
import com.github.gwtd3.api.svg.Axis;
import com.github.gwtd3.api.svg.Axis.Orientation;
import com.github.gwtd3.api.svg.Line;
import com.github.gwtd3.api.time.TimeFormat;
import com.github.gwtd3.api.time.TimeScale;
import com.github.gwtd3.api.xhr.XmlHttpRequest;
import com.github.gwtd3.demo.client.DemoCase;
import com.github.gwtd3.demo.client.Factory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;

public class AxisComponent extends FlowPanel implements DemoCase {

	public interface Bundle extends ClientBundle {
		public static final Bundle INSTANCE = GWT.create(Bundle.class);

		@Source("AxisComponentStyles.css")
		public MyResources css();
	}

	interface MyResources extends CssResource {

		String area();

		String axis();

		String x();

		String y();

		String line();

		String svg();

		// String minor();

	}

	public AxisComponent() {
		super();
		Bundle.INSTANCE.css().ensureInjected();
	}

	@Override
	public void start() {

		final MyResources css = Bundle.INSTANCE.css();

		final int[] m = new int[] { 80, 80, 80, 80 };
		final int w = 960 - m[1] - m[3];
		final int h = 500 - m[0] - m[2];
		final TimeFormat format = D3.time().format("%b %Y");

		// Scales and axes. Note the inverted domain for the y-scale: bigger is
		// up!
		final TimeScale x = D3.time().scale().range(0, w);
		final LinearScale y = D3.scale.linear().range(h, 0);
		final Axis xAxis =
				D3.svg().axis().scale(x).tickSize(-h).tickSubdivide(1);
		final Axis yAxis = D3.svg().axis().scale(y).orient(Orientation.RIGHT).ticks(4);

		// An area generator, for the light fill.
		final Area area = D3.svg().area()
				.interpolate(Area.InterpolationMode.MONOTONE)
				// .x(function(d) { return x(d.date); })
				.x(new DatumFunction<Double>() {
					@Override
					public Double apply(final Element context, final Datum d, final int index) {
						return x.apply(((Data) d.as()).getDate()).asDouble();
					}
				})
				.y0(h)
				// .y1(function(d) { return y(d.price); });
				.y1(new DatumFunction<Double>() {
					@Override
					public Double apply(final Element context, final Datum d, final int index) {
						return y.apply(((Data) d.as()).getPrice()).asDouble();
					}
				});

		// A line generator, for the dark stroke.
		final Line line = D3.svg().line()
				.interpolate(Line.InterpolationMode.MONOTONE)
				// .x(function(d) { return x(d.date); })
				.x(new DatumFunction<Double>() {
					@Override
					public Double apply(final Element context, final Datum d, final int index) {
						return x.apply(((Data) d.as()).getDate()).asDouble();
					}
				})
				// // .y(function(d) { return y(d.price); });
				.y(new DatumFunction<Double>() {
					@Override
					public Double apply(final Element context, final Datum d, final int index) {
						return y.apply(d.<Data> as().getPrice()).asDouble();
					}
				});

		D3.csv("demo-data/readme.csv", new DsvObjectAccessor<Data>() {
			@Override
			public Data apply(final DsvRow d, final int index) {
				Value value = d.get("symbol");
				if ("S&P 500".equals(value.asString())) {
					String symbol = d.get("symbol").asString();
					JsDate date = format.parse(d.get("date").asString());
					double price = d.get("price").asDouble();
					return new Data(symbol, date, price);
				} else {
					return null;
				}
			}
		}, new DsvCallback<Data>() {
			@Override
			public void get(final JavaScriptObject error, final DsvRows<Data> values) {

				if (error != null) {
					XmlHttpRequest xhrError = error.cast();
					String message = xhrError.status() + " (" + xhrError.statusText() + ")";
					Window.alert(message);
					throw new RuntimeException(message);
				}

				// // Compute the minimum and maximum date, and the maximum
				// price.
				x.domain(JsArrays.asJsArray(values.getObject(0).getDate(), values.getObject(values.length() - 1).getDate()));

				int maxY = D3.max(values, new NumericForEachCallback() {
					@Override
					public double forEach(final Object thisArg, final Value element, final int index,
							final Array<?> array) {
						return element.<Data> as().getPrice();
					}
				}).asInt();
				System.out.println("the max Y is " + maxY + " among " + values);
				y.domain(JsArrays.asJsArray(0, maxY)).nice();
				// Add an SVG element with the desired dimensions and margin.
				final Selection svg = D3.select(AxisComponent.this).append("svg:svg")
						.attr("class", css.svg())
						.attr("width", w + m[1] + m[3])
						.attr("height", h + m[0] + m[2])
						.append("svg:g")
						.attr("transform", "translate(" + m[3] + "," + m[0] + ")");

				// Add the clip path.
				svg.append("svg:clipPath")
						.attr("id", "clip")
						.append("svg:rect")
						.attr("width", w)
						.attr("height", h);

				// Add the area path.
				svg.append("svg:path")
						.attr("class", css.area())
						.attr("clip-path", "url(#clip)")
						.attr("d", area.apply(values));

				// Add the x-axis.
				svg.append("svg:g")
						.attr("class", css.x() + " " + css.axis())
						.attr("transform", "translate(0," + h + ")")
						.call(xAxis);

				// Add the y-axis.
				svg.append("svg:g")
						.attr("class", css.y() + " " + css.axis())
						.attr("transform", "translate(" + w + ",0)")
						.call(yAxis);

				// Add the line path.
				svg.append("svg:path")
						.attr("class", css.line())
						.attr("clip-path", "url(#clip)")
						.attr("d", line.apply(values));

				// Add a small label for the symbol name.
				svg.append("svg:text")
						.attr("x", w - 6)
						.attr("y", h - 6)
						.attr("text-anchor", "end")
						.text(values.getObject(0).getSymbol());

				// On click, update the x-axis.
				svg.on(BrowserEvents.CLICK, new DatumFunction<Void>() {
					@Override
					public Void apply(final Element context, final Datum d, final int index) {
						int n = values.length() - 1;
						int i = (int) Math.floor((Math.random() * n) / 2);
						int j = i + (int) Math.floor((Math.random() * n) / 2) + 1;
						x.domain(JsArrays.asJsArray(values.getObject(i).getDate(), values.getObject(j).getDate()));
						Transition transition = svg.transition().duration(750);
						transition.select("." + css.x() + "." + css.axis()).call(xAxis);
						transition.select("." + css.area()).attr("d", area.apply(values));
						transition.select("." + css.line()).attr("d", line.apply(values));
						return null;
					};
				});
			}
		});

	}

	@Override
	public void stop() {
	}

	private static class Data {
		private final String symbol;

		private final JsDate date;

		private final double price;

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "Data [date=" + date.getTime() + ", price=" + price + "]";
		}

		public Data(final String symbol, final JsDate date, final double price) {
			super();
			this.symbol = symbol;
			this.date = date;
			this.price = price;
		}

		public String getSymbol() {
			return symbol;
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
				return new AxisComponent();
			}
		};
	}

}
