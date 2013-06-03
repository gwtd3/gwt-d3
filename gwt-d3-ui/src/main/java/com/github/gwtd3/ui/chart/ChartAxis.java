/**
 * 
 */
package com.github.gwtd3.ui.chart;

import org.vectomatic.dom.svg.OMSVGRect;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.core.Datum;
import com.github.gwtd3.api.core.Formatter;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.scales.Scale;
import com.github.gwtd3.api.svg.Axis;
import com.github.gwtd3.api.svg.Axis.Orientation;
import com.github.gwtd3.ui.event.RangeChangeEvent;
import com.github.gwtd3.ui.event.RangeChangeEvent.RangeChangeHandler;
import com.github.gwtd3.ui.model.AxisModel;
import com.github.gwtd3.ui.svg.GContainer;
import com.github.gwtd3.ui.svg.Text;
import com.google.gwt.dom.client.Element;
import com.google.gwt.i18n.client.NumberFormat;

/**
 * Represents a vertical or horizontal axis in a chart,
 * and consists in a line representing domain values,
 * with a title label, and ticks represented.
 * <p>
 * 
 * <p>
 * An {@link AxisModel} must be provided to define what values are displayed in ticks.
 * 
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class ChartAxis<S extends Scale<S>> extends GContainer {

	private final Orientation tickOrientation;
	private final Axis generator;
	private final AxisModel<S> model;
	private final Text titleLabel;

	public ChartAxis(final AxisModel<S> model, final Orientation tickOrientation) {
		super();
		getElement().getStyle().setProperty("minHeight", "50px");
		getElement().getStyle().setProperty("minWidth", "50px");
		this.model = model;
		model.addRangeChangeHandler(new RangeChangeHandler() {
			@Override
			public void onRangeChange(final RangeChangeEvent event) {
				redraw();
			}
		});
		this.tickOrientation = tickOrientation;
		this.generator = D3.svg().axis().scale(model.scale()).orient(tickOrientation);
		this.titleLabel = createTitle();
		add(titleLabel);
	}

	/**
	 * @return
	 */
	private Text createTitle() {
		Text t = new Text();
		// FIXME: put that in the chart
		// t.addStyleName(chart.styles().label(), true);
		// t.addStyleName(chart.styles().axis(), true);
		t.getElement().getStyle().setProperty("textAnchor", "end");
		if (tickOrientation.isVerticalAxis()) {
			// FIXME: in the chart
			// label.classed(chart.styles().y(), true)
			t.transform().rotate(-90);
			t.y("6");
			t.dy(".71em");
		}
		else {
			// position under the tick labels...
			// or at the "end" of the axis?
			// FIXME X label
			// label.classed(chart.styles().x(), true);
		}
		return t;
	}

	/**
	 * Provides the generator used to draw the axis for deeper customization.
	 * 
	 * @return the axis generator
	 */
	public Axis generator() {
		return generator;
	}

	public AxisModel<S> model() {
		return model;
	}

	// ======== label ===================

	/**
	 * Set the text of the label to be displayed for the axis.
	 * 
	 * @param text
	 *            the text to set, null to remove the axis
	 * @return the axis
	 */
	public ChartAxis<S> title(final String text) {
		titleLabel.setText(text);
		return this;
	}

	/**
	 * @return the titleLabel
	 */
	public Text getTitleLabel() {
		return titleLabel;
	}

	// ======== ticks formatter ========
	/**
	 * Use the given {@link Formatter} to format the tick labels.
	 * 
	 * @param formatter
	 *            the d3 formatter
	 * @return the chart
	 */
	public ChartAxis<S> formatter(final Formatter formatter) {
		generator().tickFormat(formatter);
		return this;
	}

	/**
	 * Use the given {@link NumberFormat} to format the tick labels.
	 * 
	 * @param format
	 *            the formatter to be used
	 * @return the chart
	 */
	public ChartAxis<S> formatter(final NumberFormat format) {
		formatter(new DatumFunction<String>() {
			@Override
			public String apply(final Element context, final Datum d, final int index) {
				String format2 = format.format(d.asDouble());
				return format2;
			}
		});
		return this;
	}

	/**
	 * Use the given {@link DatumFunction} to format tick labels, as specified
	 * in {@link Axis#tickFormat(DatumFunction)}.
	 * 
	 * @param formatFunction
	 *            the format function to be used
	 * @return the chart
	 */
	public ChartAxis<S> formatter(final DatumFunction<String> formatFunction) {
		generator().tickFormat(formatFunction);
		return this;
	}

	// ======= business =========

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.gwtd3.ui.svg.Container#redraw()
	 */
	@Override
	public void redraw() {
		super.redraw();
		// update the range of the scale to fit new size if necessary
		OMSVGRect bBox = getGElement().getBBox();
		if (tickOrientation.isHorizontalAxis()) {
			// model.scale().range(0, (int) bBox.getWidth());
			model.scale().range(0, 500);
		}
		else {
			// model.scale().range(0, (int) bBox.getHeight());
			model.scale().range(0, 500);
		}
		// apply the generator on the axis
		select().call(generator);
	}

	/**
	 * Set the length in pixel the axis extends.
	 * 
	 * @param size
	 */
	public void setLength(final int length) {
		model.scale().range(0, length);
	}
}
