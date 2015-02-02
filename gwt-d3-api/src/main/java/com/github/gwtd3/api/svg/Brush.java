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
package com.github.gwtd3.api.svg;

import com.github.gwtd3.api.IsFunction;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Transition;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.scales.ContinuousQuantitativeScale;
import com.github.gwtd3.api.scales.OrdinalScale;
import com.github.gwtd3.api.scales.QuantitativeScale;
import com.github.gwtd3.api.scales.Scale;
import com.google.gwt.core.client.JavaScriptObject;

public class Brush extends JavaScriptObject implements IsFunction {
    public static enum BrushEvent {
        /**
         * on mousedown.
         */
        BRUSH_START("brushstart"),
        /**
         * on mousemove, if the brush extent has changed.
         */
        BRUSH("brush"),
        /**
         * on mouseup.
         */
        BRUSH_END("brushend");

        private final String value;

        private BrushEvent(final String value) {
            this.value = value;
        }

        /**
         * @return the value
         */
        public String getValue() {
            return value;
        }
    }

    protected Brush() {
        super();
    }

    /**
     * Set the brush’s x-scale.
     * <p>
     * The scale is typically defined as a {@link QuantitativeScale}, in which case the {@link #extent()} is in data
     * space from the scale's {@link Scale#domain()}; however, it may instead be defined as an {@link OrdinalScale},
     * where the extent is in pixel space from the scale's {@link OrdinalScale#rangeExtent()}.
     * <p>
     *
     * @param scale
     *            the x-scale.
     * @return the current brush.
     */
    public final native Brush x(Scale<?> scale) /*-{
		return this.x(scale);
    }-*/;

    /**
     * Get the brush’s x-scale, which defaults to null.
     * <p>
     * The scale is typically defined as a {@link QuantitativeScale}, in which case the {@link #extent()} is in data
     * space from the scale's {@link Scale#domain()}; however, it may instead be defined as an {@link OrdinalScale},
     * where the extent is in pixel space from the scale's {@link OrdinalScale#rangeExtent()}.
     * <p>
     *
     * @return the brush’s x-scale.
     */
    public final native <T extends Scale<T>> T x() /*-{
		return this.x();
    }-*/;

    /**
     * Set the brush’s y-scale.
     * *
     * <p>
     * The scale is typically defined as a {@link QuantitativeScale}, in which case the {@link #extent()} is in data
     * space from the scale's {@link Scale#domain()}; however, it may instead be defined as an {@link OrdinalScale},
     * where the extent is in pixel space from the scale's {@link OrdinalScale#rangeExtent()}.
     * <p>
     *
     * @param scale
     *            the y-scale.
     * @return the current brush.
     */
    public final native Brush y(Scale<?> scale) /*-{
		return this.y(scale);
    }-*/;

    /**
     * Get the brush’s y-scale, which defaults to null.
     * * *
     * <p>
     * The scale is typically defined as a {@link QuantitativeScale}, in which case the {@link #extent()} is in data
     * space from the scale's {@link Scale#domain()}; however, it may instead be defined as an {@link OrdinalScale},
     * where the extent is in pixel space from the scale's {@link OrdinalScale#rangeExtent()}.
     * <p>
     *
     * @return the brush’s y-scale.
     */
    public final native <T extends Scale<T>> T y() /*-{
		return this.y();
    }-*/;

    /**
     * Draws or redraws this brush into the specified selection of elements.
     * <p>
     * The brush may be drawn into multiple elements simultaneously, but note that these brushes would share the same
     * backing extent; typically, a brush is drawn into only one element at a time.
     * <p>
     *
     * @param selection the selection to draw the brush in
     * @return the current brush
     */
    public final native Brush apply(Selection selection) /*-{
		this(selection);
    }-*/;

    /**
     * Draws or redraws this brush into the specified transition of elements. The brush will perform an automatic
     * transition. Use {@link #event(Transition)} to dispatch brush events during the transition for
     * animated brushing.
     * <p>
     * The brush may be drawn into multiple elements simultaneously, but note that these brushes would share the same
     * backing extent; typically, a brush is drawn into only one element at a time.
     * <p>
     *
     * @param transition the transition to draw the brush in
     * @return the current brush
     */
    public final native Brush apply(Transition transition) /*-{
		this(transition);
    }-*/;

    /**
     * Get the current brush’s extent.
     * <p>
     * The definition of the extent depends on the associated scales. <br>
     * If both an x- and y-scale are available, then the extent is the two-dimensional array [‍​[x0, y0], [x1, y1]​],
     * where x0 and y0 are the lower bounds of the extent, and x1 and y1 are the upper bounds of the extent. If only the
     * x-scale is available, then the extent is defined as the one-dimensional array [x0, x1]; likewise, if only the
     * y-scale is available, then the extent is [y0, y1]. If neither scale is available, then the extent is null.
     * <p>
     * When the extent is set to values, the resulting extent is preserved exactly. However, as soon as the brush is
     * moved by the user (on mousemove following a mousedown), then the extent will be recomputed by calling
     * {@link ContinuousQuantitativeScale#invert(double)}. Note that, in this case, the values may be slightly imprecise
     * due to the limited precision of pixels.
     * <p>
     * Note that this does not automatically redraw the brush or dispatch any events to listeners. To redraw the brush,
     * call {@link #apply(Selection)} or {@link #apply(Transition)}; to dispatch events, use {@link #event(Selection)}
     * or {@link #event(Transition)}.
     *
     * @return the current brush’s extent.
     */
    public final native <T> Array<T> extent() /*-{
		return this.extent();
    }-*/;

    // to be in according the Scale.domain methods:
    // missing string versions
    // missing Javascript versions

    /**
     * Set the current brush’s extent.
     * <p>
     * The definition of the extent depends on the associated scales. <br>
     * If both an x- and y-scale are available, then the extent is the two-dimensional array [‍​[x0, y0], [x1, y1]​],
     * where x0 and y0 are the lower bounds of the extent, and x1 and y1 are the upper bounds of the extent. If only the
     * x-scale is available, then the extent is defined as the one-dimensional array [x0, x1]; likewise, if only the
     * y-scale is available, then the extent is [y0, y1]. If neither scale is available, then the extent is null.
     * <p>
     * When the extent is set to values, the resulting extent is preserved exactly. However, as soon as the brush is
     * moved by the user (on mousemove following a mousedown), then the extent will be recomputed by calling
     * {@link ContinuousQuantitativeScale#invert(double)}. Note that, in this case, the values may be slightly imprecise
     * due to the limited precision of pixels.
     * <p>
     * Note that this does not automatically redraw the brush or dispatch any events to listeners. To redraw the brush,
     * call {@link #apply(Selection)} or {@link #apply(Transition)}; to dispatch events, use
     * {@link #on(BrushEvent, DatumFunction)}.
     * <p>
     *
     * @return the current brush
     */
    public final native <T> Brush extent(Array<T> array) /*-{
		return this.extent();
    }-*/;

    /**
     * Set the current brush’s extent for a one-dimensional brush (defined by its x scale, or by its y scale, but not
     * both).
     * <p>
     * When the extent is set to values, the resulting extent is preserved exactly. However, as soon as the brush is
     * moved by the user (on mousemove following a mousedown), then the extent will be recomputed by calling
     * {@link ContinuousQuantitativeScale#invert(double)}. Note that, in this case, the values may be slightly imprecise
     * due to the limited precision of pixels.
     * <p>
     * Note that this does not automatically redraw the brush or dispatch any events to listeners. To redraw the brush,
     * call {@link #apply(Selection)} or {@link #apply(Transition)}; to dispatch events, use {@link #event(Selection)}
     * or {@link #event(Transition)}.
     * <p>
     *
     * @param min the lowest value of the brush's extent
     * @param max the highest value of the brush's extent
     * @return the current brush
     */
    public final native <T> Brush extent(double min, double max) /*-{
		return this.extent([ min, max ]);
    }-*/;

    /**
     * Set the current brush’s extent for a two-dimensional brush (defined by both its x scale and its y scale.
     * <p>
     * When the extent is set to values, the resulting extent is preserved exactly. However, as soon as the brush is
     * moved by the user (on mousemove following a mousedown), then the extent will be recomputed by calling
     * {@link ContinuousQuantitativeScale#invert(double)}. Note that, in this case, the values may be slightly imprecise
     * due to the limited precision of pixels.
     * <p>
     * Note that this does not automatically redraw the brush or dispatch any events to listeners. To redraw the brush,
     * call {@link #apply(Selection)} or {@link #apply(Transition)}; to dispatch events, use {@link #event(Selection)}
     * or {@link #event(Transition)}.
     * <p>
     *
     * @param x0 the lowest value of the x scale
     * @param y0 the lowest value of the y scale
     * @param x1 the highest value of the x scale
     * @param y1 the highest value of the y scale
     * @return the current brush
     */
    public final native <T> Brush extent(double x0, double y0, double x1, double y1) /*-{
		return this.extent([ [ x0, y0 ], [ x1, y1 ] ]);
    }-*/;

    /**
     * Set the listener for the specified event type.
     * <p>
     * Brushes support three types of events:
     * <ul>
     * <li>{@link BrushEvent#BRUSH_START} - on mousedown
     * <li>{@link BrushEvent#BRUSH} - on mousemove, if the brush extent has changed
     * <li>{@link BrushEvent#BRUSH_END} - on mouseup
     * </ul>
     *
     * <p>
     * Note that when clicking on the background, a mousedown also triggers a "brush" event, since the brush extent is
     * immediately cleared to start a new extent.
     *
     * @param event
     *            the event.
     * @param listener
     *            the event listener.
     * @return the current brush.
     */
    public final native Brush on(BrushEvent event, DatumFunction<Void> listener) /*-{
		return this
				.on(
						event.@com.github.gwtd3.api.svg.Brush.BrushEvent::getValue()(),
						function(d, i) {
							listener.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
						});
    }-*/;

    /**
     * Dispatch a brush gesture to registered listeners as a three event sequence: {@link BrushEvent#BRUSH_START},
     * {@link BrushEvent#BRUSH}, and {@link BrushEvent#BRUSH_END}.
     * <p>
     * This can be useful in triggering listeners after setting the brush extent programatically. If selection is a
     * transition, registers the appropriate tweens so that the brush dispatches events over the course of the
     * transition: a brushstart event when the transition starts from the previously-set extent, brush events for each
     * tick of the transition, and finally a brushend event when the transition ends. Note that the transition will be
     * interrupted if the user starts brushing before the transition ends.
     *
     * @param selection
     * @return
     */
    public final native Brush event(Selection selection) /*-{
		return this.event(selection);
    }-*/;

    /**
     * Same as {@link #event(Selection)} or {@link #event(Transition)},
     * but allows the use of {@link Selection#call(IsFunction)}.
     * <p>
     *
     * @return a function object to pass to {@link Selection#call(IsFunction)}
     */
    public final native IsFunction event() /*-{
		return this.event;
    }-*/;

    /**
     * Registers the appropriate tweens so that the brush dispatches events over the course of the
     * transition: a {@link BrushEvent#BRUSH_START} event when the transition starts from the previously-set extent,
     * {@link BrushEvent#BRUSH} events for each
     * tick of the transition, and finally a {@link BrushEvent#BRUSH_END} event when the transition ends.
     * <p>
     * Note that the transition will be interrupted if the user starts brushing before the transition ends.
     *
     * @param transition
     * @return the Brush
     */
    public final native Brush event(Transition transition) /*-{
		return this.event(transition);
    }-*/;

    /**
     * Clears the extent, making the brush extent {@link #empty()}.
     *
     * @return the current {@link Brush}
     */
    public final native Brush clear() /*-{
		return this.clear();
    }-*/;

    /**
     * Sets the current clamping behavior., for a brush where only one of the x-scale and y-scale are available.
     * <p>
     *
     * @param clamp true if the one-dimensional extent should be clamped to its scale
     * @return the current brush
     */
    public final native Brush clamp(boolean clamp) /*-{
		return this.clamp([ shouldClamp ]);
    }-*/;

    /**
     * Sets the current clamping behavior, for a brush where both an x- and y-scale are available.
     * <p>
     *
     * @param clampX true if the x-extent should be clamped to its scale
     * @param clampY true if the Y-extent should be clamped to its scale
     * @return the current brush
     */
    public final native Brush clamp(boolean clampX, boolean clampY) /*-{
		return this.clamp([ clampX, clampY ]);
    }-*/;

    /**
     * Gets the current clamping behavior.
     * <p>
     * The clamping behavior definition depends on the associated scales. <br>
     * If both an x- and y-scale are available, then the clamping behavior is an array [ x, y ], where x and y are
     * booleans that determine whether the each dimension of the two-dimensional extent should be clamped to its
     * respective x- and y-scale. If only one of the x-scale and y-scale are available, then the clamping behavior is a
     * boolean referring to whether the one-dimensional extent should be clamped to that scale. If neither scale is
     * available, then the clamping behavior is null.
     *
     * @return the current brush
     */
    public final native Array<Boolean> clamp() /*-{
		return this.clamp();
    }-*/;

    /**
     * Returns true if and only if the brush extent is empty.
     * <p>
     * When a brush is created, it is initially empty; the brush may also become empty with a single click on the
     * background without moving, or if the extent is {@link #clear()}ed.
     * <p>
     * A brush is considered empty if it has zero-width or zero-height. When the brush is empty, its {@link #extent()}
     * is not strictly defined.
     * <p>
     *
     * @return true if the brush extent is empty.
     */
    public final native boolean empty() /*-{
		return !!this.empty();
    }-*/;

}
