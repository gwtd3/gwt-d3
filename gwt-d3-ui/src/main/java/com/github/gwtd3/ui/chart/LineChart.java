package com.github.gwtd3.ui.chart;

import com.github.gwtd3.api.scales.LinearScale;
import com.github.gwtd3.api.scales.Scale;
import com.github.gwtd3.api.svg.Axis.Orientation;
import com.github.gwtd3.ui.data.SelectionDataJoiner;
import com.github.gwtd3.ui.data.SelectionUpdater;
import com.github.gwtd3.ui.event.RangeChangeEvent;
import com.github.gwtd3.ui.event.RangeChangeEvent.RangeChangeHandler;
import com.github.gwtd3.ui.event.SerieAddedEvent;
import com.github.gwtd3.ui.event.SerieAddedEvent.SerieAddedHandler;
import com.github.gwtd3.ui.event.SerieChangeEvent;
import com.github.gwtd3.ui.event.SerieChangeEvent.SerieChangeHandler;
import com.github.gwtd3.ui.event.SerieRemovedEvent;
import com.github.gwtd3.ui.event.SerieRemovedEvent.SerieRemovedHandler;
import com.github.gwtd3.ui.model.AxisCoordsBuilder;
import com.github.gwtd3.ui.model.AxisModel;
import com.github.gwtd3.ui.model.Model;
import com.github.gwtd3.ui.model.PointBuilder;
import com.github.gwtd3.ui.model.PointViewer;
import com.github.gwtd3.ui.model.Serie.NamedRange;
import com.github.gwtd3.ui.svg.GContainer;
import com.github.gwtd3.ui.svg.SVGDocumentContainer;
import com.github.gwtd3.ui.svg.SVGResources;
import com.github.gwtd3.ui.svg.SVGStyles;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.debug.JsoInspector;

/**
 * A line chart displaying several series on the same amount.
 * <p>
 * You can customize the styling by providing your own {@link Styles} instance
 * during construction of the chart.
 * <p>
 * You can configure the chart behaviour using {@link #options()}.
 * <p>
 * User is able by default to navigate accross the X dimension domain. Call
 * {@link Options#enableXNavigation(boolean)} with false to disable it.
 * <p>
 * FIXME: styling lines (colors, etc...) FIXME: styling serie label (position,
 * font, etc...)
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
public class LineChart<T> extends SVGDocumentContainer implements SerieAddedHandler<T>, SerieRemovedHandler<T>,
		SerieChangeHandler<T>, RangeChangeHandler {

	/**
	 * Configure the chart.
	 * 
	 * @author SCHIOCA
	 * 
	 */
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

	// ========== Resources and Styles classes =====================

	private static Resources createDefaultResources() {
		return GWT.create(Resources.class);
	}

	public static interface Resources extends SVGResources {
		@Source("LineChart.css")
		LineChart.Styles chartStyles();
	}

	public static interface XAxisResources extends ChartAxis.Resources {
		@Source("ChartAxis.css")
		ChartAxis.Styles xStyles();
	}

	public static interface YAxisResources extends ChartAxis.Resources {
		@Source("ChartAxis.css")
		ChartAxis.Styles yStyles();
	}

	public static interface Styles extends SVGStyles {
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
	 * Support for x or y sliding
	 */
	private final DragSupport dragSupport = new DragSupport(this.xModel);

	/**
	 *  
	 */
	private final Options options = new Options(this);

	private final AxisModel<LinearScale> xModel = AxisModel.createLinear();

	private final AxisModel<LinearScale> yModel = AxisModel.createLinear();

	/**
	 * The model defining this chart
	 */
	private final Model<T, LinearScale> model;

	private ChartAxis<? extends Scale<?>> yAxis;

	private ChartAxis<? extends Scale<?>> xAxis;

	private GContainer g;

	private LineChart.Styles styles;

	private SelectionUpdater drawer;

	private PointViewer<T> builder;

	public LineChart(final PointBuilder<T> pointBuilder) {
		this(pointBuilder, createDefaultResources());
	}

	public LineChart(final PointBuilder<T> pointBuilder, final Resources resources) {
		super(resources);

		getElement().setAttribute("viewBox", "0 0 500 400");
		styles = resources.chartStyles();
		styles.ensureInjected();
		this.model = new Model<T, LinearScale>(xModel, yModel, pointBuilder);
		initModel();
		createChildren();
	}

	private void initModel() {
		this.model.addSerieAddedHandler(this);
		this.model.addSerieRemovedHandler(this);

		// helper converting a domain object to x and y pixels
		builder = new AxisCoordsBuilder<T>(
				xModel,
				yModel,
				model.coordsBuilder());
		// a renderer that draws path lines with styles
		drawer = new LineRenderer<T>(builder, styles);

		// listens for range changed
		xModel.addRangeChangeHandler(this);
		yModel.addRangeChangeHandler(this);

	}

	// ============== initialization ========================

	private void createChildren() {
		// create G container
		g = new GContainer();
		add(g);
		g.transform().translate(margin.left, margin.top);

		// X AXIS
		xAxis = new ChartAxis<LinearScale>(xModel, Orientation.BOTTOM);
		// FIXME
		// xAxis.setPixelSize(0, chartWidth());
		xAxis.addStyleName(styles().x());
		// FIXME let the user configure position at center ?
		// FIXME: or automate the process by a Y domain neg and pos
		// should be yRange.apply(0) instead of chart height ?
		g.add(xAxis);

		// Y AXIS
		// FIXME
		// yAxis.scale().range(chartHeight(), 0);
		// tickSize(6, 4, 2).
		yAxis = new ChartAxis<LinearScale>(yModel, Orientation.LEFT);

		yAxis.generator().ticks(4);// .tickSubdivide(1).tickSize(12, 6, 3);
		// append the axis to the svg
		// change styling, position, (left, right)
		// text label position / orientation
		yAxis.addStyleName(styles().y());
		g.add(yAxis);

		// SERIES RENDERER

	}

	@Override
	protected void onSelectionAttached() {
		super.onSelectionAttached();

		// register x drag interaction
		dragSupport.registerListeners(select()).enable();
	}

	// ==================== redraw methods ================
	@Override
	public void redraw() {
		redrawAxis();
		redrawSeries();
	}

	private void redrawAxis() {

		// System.out.println("blah:" + getSVGElement().getViewport());
		// System.out.println("viewBox:" +
		// getSVGElement().getViewBox().getBaseVal().getWidth());
		// System.out.println("viewBox:" +
		// getSVGElement().getViewBox().getBaseVal().getWidth());
		// System.out.println("blah:" + getSVGElement().getViewportElement());
		// System.out.println("DIMS:" + chartWidth() + " " + chartHeight());
		// resizing
		xAxis.transform().removeAll().translate(0, chartHeight());
		xAxis.setLength(chartWidth());
		yAxis.setLength(chartHeight());

		// domain changes

		// values changes
		Object object = JsoInspector.convertToInspectableObject(getSVGElement().getChild(0));
		// System.out.println(object);
		// System.out.println(object);
		System.out.println("blah:" + getSVGElement().getViewport());
		System.out.println("blah:" + getSVGElement().getViewportElement());
	}

	protected void redrawSeries() {
		// create/remove g > path elements for each series
		// creates an intermediary g inside the g to group all series
		// seriesRenderer.setSelector("." + styles.serie());

		SelectionDataJoiner.update(g.select(), model.series(), drawer);

		// JOIN series to G elements
		// // update the d attr with the correct data
		// // for the main line
		// serie.select("." + styles().line())

		// bnewG.append("text")
		// .text(new DatumFunction<String>() {
		// @Override
		// public String apply(final Element context, final Datum d, final int
		// index) {
		// Serie<T> serie = d.<Serie<T>> as();
		// if (serie.isEmpty()) {
		// return "";
		// }
		// return serie.name();
		// }
		// }).attr("x", 3).attr("dy", ".35em");
		//
		// serie.selectAll("text")
		// .attr("transform", new DatumFunction<Object>() {
		// @Override
		// public Object apply(final Element context, final Datum d, final int
		// index) {
		// // take the last value of the serie
		// Serie<T> serie = d.<Serie<T>> as();
		// if (serie.isEmpty()) {
		// return "";
		// }
		// else {
		// T value = serie.values().get(serie.values().size() - 1);
		// return "translate(" +
		// xModel.toPixel(model.coordsBuilder().x(value)) + "," +
		// yModel.toPixel(model.coordsBuilder().y(value)) + ")";
		// }
		// }
		// });

		// FIXME : color of the serie
		// .style("stroke", function(d) { return color(d.name); });

		// serieLabel.exit().remove();

	}

	// ============= getters =============

	protected LineChart.Styles styles() {
		return styles;
	}

	public Model<T, LinearScale> model() {
		return model;
	}

	public ChartAxis<? extends Scale<?>> xAxis() {
		return xAxis;
	}

	public ChartAxis<?> yAxis() {
		return yAxis;
	}

	public Options options() {
		return options;
	}

	/**
	 * Width of the area displaying series data, bounded by axis. (excluding
	 * space for Y axis labels or legend)
	 * 
	 * @return
	 */
	public int chartWidth() {
		return getWidth() - margin.left - margin.right;
	}

	/**
	 * Width of the area displaying series data, bounded by axis.
	 * 
	 * @return
	 */
	public int chartHeight() {
		return getHeight() - margin.top - margin.bottom;
	}

	// =========== listens to model events ==============

	@Override
	public void onSerieRemoved(final SerieRemovedEvent<T> event) {
		event.getSerie().addSerieChangeHandler(this);
		redrawSeries();
	}

	@Override
	public void onSerieAdded(final SerieAddedEvent<T> event) {
		// attach listener on the serie
		event.getSerie().addSerieChangeHandler(this);
		redrawSeries();
	}

	@Override
	public void onSerieChange(final SerieChangeEvent<T> event) {
		// TODO: find a way to redraw only the serie that changed
		redrawSeries();
	}

	@Override
	public void onRangeChange(final RangeChangeEvent event) {
		redrawSeries();
	}
}
