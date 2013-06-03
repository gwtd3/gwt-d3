/**
 * 
 */
package com.github.gwtd3.ui.model;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.scales.LinearScale;
import com.github.gwtd3.api.scales.Scale;
import com.github.gwtd3.ui.event.RangeChangeEvent;
import com.github.gwtd3.ui.event.RangeChangeEvent.RangeChangeHandler;
import com.github.gwtd3.ui.event.RangeChangeEvent.RangeChangeHasHandlers;
import com.google.common.collect.Range;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * Defines the model to render an axis.
 * <p>
 * The axis is defined by a {@link Scale} function used to convert domain values into pixel positions.
 * <p>
 * 
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class AxisModel<S extends Scale<S>> implements RangeChangeHasHandlers {

	private final S scale;
	private final HandlerManager manager = new HandlerManager(this);

	/**
	 * @return a new model driven by a linear scale.
	 */
	public static AxisModel<LinearScale> createLinear() {
		return new AxisModel<LinearScale>(D3.scale.linear());
	}

	public AxisModel(final S scale) {
		super();
		this.scale = scale;
	}

	/**
	 * Return the scale driving this model
	 * 
	 * @return the scale backing this model
	 */
	public S scale() {
		return scale;
	}

	/**
	 * @return the domain of the
	 */
	public Range<Double> visibleDomain() {
		return Range.closed(scale.domain().getNumber(0), scale.domain().getNumber(1));
	}

	/**
	 * Set the range of domain values currently displayed on the axis.
	 * Do not send events.
	 * 
	 * @param lower
	 * @param upper
	 */
	public AxisModel<S> setVisibleDomain(final double lower, final double upper) {
		return setVisibleDomain(lower, upper, false);
	}

	/**
	 * Set the range of domain values currently displayed on the axis.
	 * Send events if requested.
	 * 
	 * @param lower
	 * @param upper
	 * @param fireEvent
	 */
	public AxisModel<S> setVisibleDomain(final double lower, final double upper, final boolean fireEvent) {
		Range<Double> oldRange = visibleDomain();
		Range<Double> newRange = Range.closed(lower, upper);
		if (oldRange.equals(newRange)) {
			return this;
		}

		scale.domain(lower, upper);
		if (fireEvent) {
			RangeChangeEvent.fire(this, newRange, oldRange);
		}
		return this;
	}

	/**
	 * Return true if the domain value is inside the bounds of the current scale
	 * domain of the axis.
	 * 
	 * @param domainValue
	 *            the domain value to test
	 * @return true if inside, false if outside the bounds.
	 */
	public boolean isVisible(final double domainValue) {
		return (scale().domain().getNumber(0) >= domainValue) && (scale().domain().getNumber(1) <= domainValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.event.shared.HasHandlers#fireEvent(com.google.gwt.event.shared.GwtEvent)
	 */
	@Override
	public void fireEvent(final GwtEvent<?> event) {
		manager.fireEvent(event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.gwtd3.ui.event.RangeChangeEvent.RangeChangeHasHandlers#addRangeChangeHandler(com.github.gwtd3.ui.event.RangeChangeEvent.RangeChangeHandler)
	 */
	@Override
	public HandlerRegistration addRangeChangeHandler(final RangeChangeHandler handler) {
		return manager.addHandler(RangeChangeEvent.TYPE, handler);
	}

	public double getPixel(final double domainValue) {
		return scale.apply(domainValue).asDouble();
	}

}
