package com.github.gwtd3.ui.chart;

import java.text.NumberFormat;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.core.Datum;
import com.github.gwtd3.api.core.Formatter;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.scales.LinearScale;
import com.github.gwtd3.api.svg.Axis;
import com.github.gwtd3.api.svg.Axis.Orientation;

import com.github.gwtd3.ui.event.RangeChangeEvent;
import com.github.gwtd3.ui.event.RangeChangeEvent.RangeChangeHandler;
import com.github.gwtd3.ui.event.RangeChangeEvent.RangeChangeHasHandlers;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * FIXME: Convert to an Element or to a D3Widget to ensure lifecycle is consistent with GWT widget lifecycles
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class AxisComponent implements RangeChangeHasHandlers {
    private Selection selection;
    private final LinearScale scale;
    private Orientation tickOrientation = Orientation.BOTTOM;
    private final Axis generator;
    private Selection label;
    private final LineChart<?> chart;

    private HandlerManager manager = new HandlerManager(this);

    /**
     * Indicate the component has been added to the DOM .
     */
    private boolean attached = false;

    public AxisComponent(final LineChart<?> chart, final Orientation tickOrientation) {
        super();
        this.chart = chart;
        this.tickOrientation = tickOrientation;

        // create scale and generator
        scale = D3.scale.linear();
        generator = D3.svg().axis().scale(scale).orient(tickOrientation);

        updateDomain();
    }

    protected boolean isVertical() {
        return (tickOrientation == Orientation.LEFT) || (tickOrientation == Orientation.RIGHT);
    }

    protected boolean isHorizontal() {
        return !isVertical();
    }

    /**
     * A scale function to map domain X values to X axis pixels
     */
    public LinearScale scale() {
        return scale;
    }

    private Selection selection() {
        if (selection == null) {
            // append the axis to the svg
            selection = chart.g().append("g")
                    .classed(chart.styles().axis(), true)
                    .call(generator);
            attached = true;
        }
        return selection;
    }

    public Axis generator() {
        return generator;
    }

    public AxisComponent appendClass(final String className) {
        selection().classed(className, true);
        return this;
    }

    /**
     * update the drawing of the axis with current domain and range.
     * 
     */
    public void redraw() {
        updateDomain();
        selection().call(generator);
    }

    /**
     * update the domain with the visible range defined by the model.
     */
    private void updateDomain() {
        if (chart.model() != null) {
            if (isHorizontal()) {
                scale().domain(chart.model().visibleXRange().lowerEndpoint().doubleValue(),
                        chart.model().visibleXRange().upperEndpoint().doubleValue());
            }
            else {
                scale().domain(chart.model().visibleYRange().lowerEndpoint().doubleValue(),
                        chart.model().visibleYRange().upperEndpoint().doubleValue());
            }
        }
    }

    public Value valueToPixel(final double domainValue) {
        return scale.apply(domainValue);
    }

    // ===================== label formatter =====================

    /**
     * Set the text label to be displayed for the axis and update the rendering
     * 
     * @param text
     *            the text to set, null to remove the axis
     * @return the axis
     */
    public AxisComponent labelText(final String text) {
        if (text != null) {
            if (this.label == null) {
                createLabel();
            }
            label.text(text);
        }
        else {
            label.remove();
            label = null;
        }
        return this;
    }

    protected void createLabel() {
        this.label = selection()
                .append("text")
                .classed(chart.styles().label(), true)
                .classed(chart.styles().axis(), true)
                .style("text-anchor", "end");
        if (isVertical()) {
            label.classed(chart.styles().y(), true)
                    .attr("transform", "rotate(-90)")
                    .attr("y", 6)
                    .attr("dy", ".71em");
        }
        else {
            // FIXME X label
            label.classed(chart.styles().x(), true);
        }
    }

    /**
     * Use the given {@link Formatter} to format the tick labels.
     * 
     * @param formatter
     *            the d3 formatter
     * @return the chart
     */
    public AxisComponent formatter(final Formatter formatter) {
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
    public AxisComponent formatter(final NumberFormat format) {
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
    public AxisComponent formatter(final DatumFunction<String> formatFunction) {
        generator().tickFormat(formatFunction);
        return this;
    }

    // firing events
    @Override
    public void fireEvent(final GwtEvent<?> event) {
        // range changed
        updateDomain();
        chart.redraw();
        manager.fireEvent(event);
    }

    @Override
    public HandlerRegistration addRangeChangeHandler(final RangeChangeHandler handler) {
        return manager.addHandler(RangeChangeEvent.getType(), handler);
    }

    /**
     * Return true if the domain value is inside the bounds of the current scale
     * domain of the axis.
     * 
     * @param domainValue
     *            the domain value to test
     * @return true if inside, false if outside the bounds.
     */
    public boolean contains(final double domainValue) {
        return (scale().domain().getNumber(0) >= domainValue) && (scale().domain().getNumber(1) <= domainValue);
    }

    public AxisComponent translate(final int x, final int y) {
        selection().attr("transform", "translate(" + x + "," + y + ")");
        return this;
    }

}