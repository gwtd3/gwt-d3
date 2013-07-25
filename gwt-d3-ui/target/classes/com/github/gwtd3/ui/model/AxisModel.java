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
package com.github.gwtd3.ui.model;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.scales.LinearScale;
import com.github.gwtd3.api.scales.Scale;
import com.github.gwtd3.ui.event.RangeChangeEvent;
import com.github.gwtd3.ui.event.RangeChangeEvent.RangeChangeHandler;
import com.github.gwtd3.ui.event.RangeChangeEvent.RangeChangeHasHandlers;
import com.google.common.collect.Range;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * Defines the model to render an axis.
 * 
 * <p>
 * The axis is defined by a {@link Scale} function used to convert arbitrary domain values into pixel positions.
 * <p>
 * The mapping between values and positions are defined by two set of range:
 * <ul>
 * <li><b>the visible domain range</b>: the part of the domain values that will be displayed on the axis. The range of
 * domain values can be modified using the {@link #setVisibleDomain(double, double)} methods.
 * <p>
 * 
 * <li><b>the pixel range</b>: the range of pixels corresponding to the domain values. You should never direclty modify
 * the pixel range, since this is usually done automatically by the renderer using the model.
 * </ul>
 * <p>
 * For instance, setting a visible domain range of [20 .. 35.5] to a pixel range of [100..500] create an axis of 400
 * pixels long and map the value 20 on the pixel 100, the value 35.5 on the pixel 500, and all intermediate values on
 * intermediate pixels.
 * <p>
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
     * Return a copy of the scale driving this model.
     * <p>
     * Modifying the returned scale does not have any effect on the model
     * 
     * @return the scale backing this model
     */
    public S scale() {
        return scale.copy();
    }

    // ============= events handling ==================

    /**
     * @return the domain of the
     */
    public Range<Double> visibleDomain() {
        return Range.closed(scale.domain().getNumber(0), scale.domain().getNumber(1));
    }

    /**
     * Set the range of domain values currently displayed on the axis.
     * <p>
     * lower must be lower than upper.
     * 
     * @param lower
     * @param upper
     */
    public AxisModel<S> setVisibleDomain(final double lower, final double upper) {
        return setVisibleDomain(lower, upper, true);
    }

    /**
     * Set the range of domain values currently displayed on the axis. Send
     * events if requested.
     * 
     * @param lower
     * @param upper
     * @param fireEvent
     */
    private AxisModel<S> setVisibleDomain(final double lower, final double upper, final boolean fireEvent) {
        Range<Double> oldRange = visibleDomain();
        Range<Double> newRange = Range.closed(lower, upper);
        if (oldRange.equals(newRange)) {
            return this;
        }
        GWT.log("Setting visible domain to [" + lower + "," + upper + "]");
        scale.domain(lower, upper);
        if (fireEvent) {
            fireEvent(new RangeChangeEvent(newRange, oldRange));
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
        return visibleDomain().contains(domainValue);
    }

    // ============= events handling ==================
    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.gwt.event.shared.HasHandlers#fireEvent(com.google.gwt.event
     * .shared.GwtEvent)
     */
    @Override
    public void fireEvent(final GwtEvent<?> event) {
        manager.fireEvent(event);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.github.gwtd3.ui.event.RangeChangeEvent.RangeChangeHasHandlers#
     * addRangeChangeHandler(com.github.gwtd3.ui.event
     * .RangeChangeEvent.RangeChangeHandler)
     */
    @Override
    public HandlerRegistration addRangeChangeHandler(final RangeChangeHandler handler) {
        return manager.addHandler(RangeChangeEvent.TYPE, handler);
    }

    // ============= Mapping to pixels ==================

    /**
     * Set the bounds of the pixel range.
     * <p>
     * You should usually not call this method since this is done by components using the model. start is not
     * necessarily lower than end.
     * 
     * 
     * @param start
     * @param end
     */
    public void setPixelRange(final int start, final int end) {
        scale.range(start, end);
    }

    /**
     * Convert a given domain value into a distance (in pixels) from the origin
     * of the axis.
     * 
     * @param domainValue
     *            the value to convert
     * @return the pixel value the pixel
     */
    public int toPixel(final double domainValue) {
        return scale.apply(domainValue).asInt();
    }

    /**
     * Return the size in pixels of the given domain segment.
     * 
     * @param domainSize
     * @return the size in pixels of the domain segment
     */
    public int toPixelSize(final double domainSize) {
        double domainPoint = (visibleDomain().lowerEndpoint() + Math.abs(domainSize));

        return (int) Math.abs(toPixel(domainPoint) - scale.range().getNumber(0));
    }

}
