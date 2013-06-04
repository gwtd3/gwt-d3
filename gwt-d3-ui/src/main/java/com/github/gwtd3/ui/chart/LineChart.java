package com.github.gwtd3.ui.chart;

import java.util.Collection;
import java.util.Set;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.JsArrays;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.arrays.ForEachCallback;
import com.github.gwtd3.api.core.Datum;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.UpdateSelection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.scales.LinearScale;
import com.github.gwtd3.api.scales.Scale;
import com.github.gwtd3.api.svg.Axis.Orientation;
import com.github.gwtd3.api.svg.Line;
import com.github.gwtd3.api.svg.Line.InterpolationMode;
import com.github.gwtd3.ui.model.AxisModel;
import com.github.gwtd3.ui.model.Model;
import com.github.gwtd3.ui.model.PointBuilder;
import com.github.gwtd3.ui.model.Serie;
import com.github.gwtd3.ui.model.Serie.NamedRange;
import com.github.gwtd3.ui.svg.SVGDocumentContainer;
import com.google.common.collect.Range;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;

/**
 * A line chart displaying several series on the same amount.
 * <p>
 * You can customize the styling by providing your own {@link Styles} instance during construction of the chart.
 * <p>
 * You can configure the chart behaviour using {@link #options()}.
 * <p>
 * User is able by default to navigate accross the X dimension domain. Call {@link Options#enableXNavigation(boolean)} with false to disable it.
 * <p>
 * FIXME: styling lines (colors, etc...) FIXME: styling serie label (position, font, etc...)
 * 
 * FIXME: customize scaling functions (linear, log, etc...)
 * 
 * FIXME: configuring ticks
 * 
 * FIXME: slide in X => events to grab new data
 * 
 * FIXME: caching hidden data if slide is possible by presending events
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 * @param <T>
 */
public class LineChart<T> extends SVGDocumentContainer {

	public class Options {
		private final LineChart<T> chart;

		public Options(final LineChart<T> chart) {
			super();
			this.chart = chart;
		}

		public Options enableXNavigation(final boolean enable) {
			if (enable) {
				dragSupport.enable();
			}
			else {
				dragSupport.disable();
			}
			return this;
		}

	}

	/**
	 * FIXME: remove this ugly thing
	 * 
	 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
	 * 
	 */
	private static class margin {
		static int top = 20;
		static int bottom = 30;
		static int left = 50;
		static int right = 40;
	}

	private static Resources createDefaultResources() {
		return GWT.create(Resources.class);
	}

	public static interface Resources extends SVGDocumentContainer.Resources {
		@Override
		@Source("LineChart.css")
		Styles getStyles();
	}

	public static interface Styles extends SVGDocumentContainer.Styles {
		/**
		 * @return the classname applied to any axis
		 */
		public String axis();

		/**
		 * A class name applied to the axis major ticks. (non obfuscated)
		 * 
		 * @return
		 */
		public String major();

		/**
		 * A class name applied to the axis major ticks. (non obfuscated)
		 * 
		 * @return
		 */
		public String minor();

		/**
		 * A class name applied to the axis ticks. (non obfuscated)
		 * 
		 * @return
		 */
		public String tick();

		/**
		 * will be applied to any line serie.
		 * 
		 * @return
		 */
		public String line();

		/**
		 * a classna;e applied to series lines.
		 * 
		 * @return
		 */
		public String serie();

		/**
		 * class applied to all labels
		 * 
		 * @return
		 */
		public String label();

		/**
		 * class applied to all element on the y axis
		 * 
		 * @return
		 */
		public String y();

		/**
		 * class applied to all element on the x axis
		 * 
		 * @return
		 */
		public String x();

		/**
		 * class applied to range of values defined in any {@link NamedRange}.
		 * 
		 * @return the named class
		 */
		public String namedRange();

	}

	/**
	 * The model defining this chart
	 */
	private final Model<T> model = new Model<T>(this);

	/**
	 * Support for x or y sliding
	 */
	private final DragSupport dragSupport = new DragSupport(this.xModel);

	/**
	 *  
	 */
	private final Options options = new Options(this);

	/**
	 * the g selectio in which we draw everything else
	 */
	private Selection g;

	private AxisModel<LinearScale> xModel;

	private final ChartAxis<? extends Scale<?>> xAxis = new ChartAxis<LinearScale>(xModel, Orientation.BOTTOM);

	private AxisModel<LinearScale> yModel;

	private final ChartAxis<? extends Scale<?>> yAxis = new ChartAxis<LinearScale>(yModel, Orientation.LEFT);

	public LineChart() {
		this(createDefaultResources());
	}

	public LineChart(final Resources resources) {
		super(resources);
		init();
	}

	protected void init() {
		// TODO: function
		// parseDate = D3.time().format("%Y%m%d").parse;
		// create the g selection which will contain everything
		// add the things in the graph
		// FIXME
		g = select().append("g").attr("transform", "translate(" + margin.left + "," + margin.top + ")");

		// defines scale functions that convert domain values into a value in
		// pixel
		// FIXME: let the user customize it
		// FIXME: listen for resize to update the includeRange

		// TODO: implement ordinal sclaes of color
		// color = D3.scale().category10();

		// append x axis
		createXAxis();
		createYAxis();
		// interactions
		dragSupport.registerListeners(select()).enable();
		redraw();
	}

	/**
	 * Wraps the logic of generating a path data appropriate for displaying
	 * ranges of values.
	 * 
	 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
	 * 
	 * @param <T>
	 */
	protected static class PathGenerator<T> {

		private Line lineGenerator;

		private AxisModel<?> xModel;
		private AxisModel<?> yModel;
		private PointBuilder<T> pointBuilder;

		private Range<Double> includeRange;
		private Set<Range<Double>> excludeRanges;

		public PathGenerator(final AxisModel<?> xModel, final AxisModel<?> yModel, final PointBuilder<T> pointBuilder) {
			super();
			this.xModel = xModel;
			this.yModel = yModel;
			this.pointBuilder = pointBuilder;

			// create the line generator which will use the scale functions
			lineGenerator = D3.svg().line().interpolate(InterpolationMode.BASIS)
					.x(new DatumFunction<Double>() {
						@Override
						public Double apply(final Element context, final Datum d, final int index) {
							// transform the x returned by the data into a pixel
							// value via
							// scales
							T t = d.<T> as();
							double xDomainValue = pointBuilder.x(t);
							return (double) xModel.getPixel(xDomainValue);
						}
					})
					.y(new DatumFunction<Double>() {
						@Override
						public Double apply(final Element context, final Datum d, final int index) {
							// transform the x returned by the data into a pixel
							// value via
							// scales
							T t = d.<T> as();
							double yDomainValue = pointBuilder.y(t);
							return (double) yModel.getPixel(yDomainValue);
						}
					})
					.defined(new DatumFunction<Boolean>() {
						@Override
						public Boolean apply(final Element context, final Datum d, final int index) {
							// evict the datum that are outside the visible includeRange
							T t = d.<T> as();
							double xDomainValue = pointBuilder.x(t);
							// if the value is contained in of the excludeRanges, it is undefined
							if (excludeRanges != null) {
								// System.out.println("iexcludeRanges:" + includeRange + " "
								// + excludeRanges.contains(xDomainValue));
								for (Range<Double> range : excludeRanges) {
									if (range.contains(xDomainValue) && !range.lowerEndpoint().equals(xDomainValue)) {
										return false;
									}
								}
							}
							System.out.println("included range:" + includeRange + " "
									+ includeRange.contains(xDomainValue));
							// return true;
							// defined if the value is inside the includeRange (if specified)
							return (includeRange == null ? true : includeRange.contains(xDomainValue)
									|| includeRange.upperEndpoint().equals(xDomainValue)
									|| includeRange.lowerEndpoint().equals(xDomainValue));
						}
					}
					);
		}

		public PathGenerator<T> includeRange(final Range<Double> range) {
			this.includeRange = range;
			return this;
		}

		public PathGenerator<T> excludeRanges(final Set<Range<Double>> range) {
			this.excludeRanges = range;
			return this;
		}

		public String generatePath(final Collection<T> values) {
			return lineGenerator.apply(JsArrays.asJsArray(values.toArray()));
		}

	}

	private void createXAxis() {
		// FIXME
		// xAxis.setPixelSize(0, chartWidth());
		xAxis.addStyleName(styles().x());
		// FIXME let the user configure position at center ?
		// FIXME: or automate the process by a Y domain neg and pos
		// should be yRange.apply(0) instead of chart height ?
		xAxis.transform().translate(0, chartHeight());
	}

	private void createYAxis() {
		// FIXME
		// yAxis.scale().range(chartHeight(), 0);
		// tickSize(6, 4, 2).

		yAxis.generator().ticks(4);// .tickSubdivide(1).tickSize(12, 6, 3);
		// append the axis to the svg
		// change styling, position, (left, right)
		// text label position / orientation
		yAxis.addStyleName(styles().y());
	}

	// ==================== redraw methods ================
	@Override
	public void redraw() {
		redrawAxis();
		redrawSeries();
	}

	private void redrawAxis() {
		xAxis.redraw();
		yAxis.redraw();
	}

	protected void redrawSeries() {
		// create/remove g > path elements for each series

		// JOIN series to G elements
		UpdateSelection serie = g.selectAll("." + styles().serie())
				.data(model.seriesAsArray());

		// create missing series
		Selection bnewG = serie.enter()
				.append("g").attr("class", styles().serie());
		// we gonna add as path
		// for each includeRange,
		bnewG.append("path").classed(styles().line(), true);
		// remove series that does not exist anymore
		serie.exit().remove();

		// update the d attr with the correct data
		// for the main line
		serie.select("." + styles().line())
				.attr("d", new DatumFunction<String>() {
					@Override
					public String apply(final Element context, final Datum d, final int index) {
						// take the values of the serie
						Serie<T> serie = d.<Serie<T>> as();
						// apply the values on the line generator
						return new PathGenerator<T>(xModel, yModel, model.pointBuilder())
								.includeRange(model.visibleXRange())
								.excludeRanges(serie.namedRanges())
								.generatePath(serie.values());
					}
				});

		// get an array of NamedRange in for each
		// Join each NamedRange of a serie to elements path with class namedRange
		UpdateSelection namedRangePath = serie.selectAll("path." + styles().namedRange())
				.data(new DatumFunction<Array<NamedRange<T>>>() {
					@SuppressWarnings("unchecked")
					@Override
					public Array<NamedRange<T>> apply(final Element context, final Datum d, final int index) {
						Serie<T> serie = d.<Serie<T>> as();
						// only bind the visible namedRanges, ignore those that are outside
						Set<NamedRange<T>> ranges = serie.visibleNamedRanges(model.visibleXRange());
						return JsArrays.asJsArray(ranges);
					}
				});
		// create the path made visibles and add a class for them
		namedRangePath.enter().append("path").classed(styles().namedRange(), true).classed(styles().line(), true);
		// remove unvisible paths
		namedRangePath.exit().remove();
		namedRangePath.attr("d", new DatumFunction<String>() {
			@Override
			public String apply(final Element context, final Datum d, final int index) {
				// take the values of the serie
				NamedRange<T> range = d.<NamedRange<T>> as();
				// Set<NamedRange<T>> visibleNamedRanges = range.serie().visibleNamedRanges(range.range());
				// apply the values on the line generator
				return new PathGenerator<T>(xModel, yModel, model.pointBuilder())
						.includeRange(range.range())
						// .excludeRanges()
						.generatePath(range.serie().values());
			}
		});
		//

		// now create a path data for each namedrange "connected" to the visible range
		model.seriesAsArray().map(new ForEachCallback<Serie<T>>() {
			@Override
			public Serie<T> forEach(final Object thisArg, final Value element, final int index, final Array<?> array) {
				return null;
			}
		});

		// select labels and associte serie.name
		// UpdateSelection serieLabel = serie.selectAll("text")
		// .data(new DatumFunction<String>() {
		// @Override
		// public String apply(Element context, Datum d, int index) {
		// return d.<Serie> as().name();
		// }
		// });
		bnewG.append("text")
				.text(new DatumFunction<String>() {
					@Override
					public String apply(final Element context, final Datum d, final int
							index) {
						Serie<T> serie = d.<Serie<T>> as();
						if (serie.isEmpty()) {
							return "";
						}
						return serie.name();
					}
				}).attr("x", 3).attr("dy", ".35em");

		serie.selectAll("text")
				.attr("transform", new DatumFunction<Object>() {
					@Override
					public Object apply(final Element context, final Datum d, final int
							index) {
						// take the last value of the serie
						Serie<T> serie = d.<Serie<T>> as();
						if (serie.isEmpty()) {
							return "";
						}
						else {
							T value = serie.values().get(serie.values().size() - 1);
							return "translate(" +
									xModel.getPixel(model.pointBuilder().x(value)) + "," +
									yModel.getPixel(model.pointBuilder().y(value)) + ")";
						}
					}
				});

		// FIXME : color of the serie
		// .style("stroke", function(d) { return color(d.name); });

		// serieLabel.exit().remove();

	}

	// ============= getters =============

	public Styles styles() {
		return (Styles) styles;
	}

	public Model<T> model() {
		return model;
	}

	public ChartAxis<? extends Scale<?>> xAxis() {
		return xAxis;
	}

	public ChartAxis<?> yAxis() {
		return yAxis;
	}

	/**
	 * The selection containing the main g element of the svg.
	 * 
	 * @return the selection
	 */
	public Selection g() {
		return g;
	}

	public Options options() {
		return options;
	}

	/**
	 * Width of the area displaying series data,
	 * bounded by axis.
	 * (excluding space for Y axis labels or legend)
	 * 
	 * @return
	 */
	public int chartWidth() {
		return getElement().getClientWidth() - margin.left - margin.right;
	}

	/**
	 * Width of the area displaying series data,
	 * bounded by axis.
	 * 
	 * @return
	 */
	public int chartHeight() {
		return getElement().getClientHeight() - margin.top - margin.bottom;
	}
}
