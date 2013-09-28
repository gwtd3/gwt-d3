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
package com.github.gwtd3.api.behaviour;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.IsFunction;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Transition;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.scales.QuantitativeScale;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * This behavior automatically creates event listeners to handle scroll gestures
 * and pan gestures on an element. Events including mouse, touch, and scroll are
 * supported.
 * <p>
 * Usage:
 * 
 * <pre>
 * {
 * 	&#064;code
 * 	Zoom zoom = D3.behavior.zoom().on(ZoomEventType.Zoom, new MyZoomListener());
 * 	mySelection.call(zoom);
 * }
 * </pre>
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a> <br />
 *         <a href="https://github.com/augbog">Augustus Yuan</a>
 * 
 */

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Zoom extends JavaScriptObject implements IsFunction {

	protected Zoom() {

	}

	/**
	 * Type of scroll event to listen to.
	 */
	public static enum ZoomEventType {

		/**
		 * at the start of a zoom gesture (e.g., touchstart).
		 */
		ZOOMSTART,

		/**
		 * when the view changes (e.g., touchmove).
		 */
		ZOOM,

		/**
		 * at the end of the current zoom gesture (e.g., touchend).
		 */
		ZOOMEND;
	}

	/**
	 * Registers the specified listener to receive events of the specified type
	 * from the zoom behavior.
	 * <p>
	 * See {@link ZoomEventType} for more information.
	 * <p>
	 * If an event listener was already registered for the same type, the
	 * existing listener is removed before the new listener is added. TODO: To
	 * register multiple listeners for the same event type, the type may be
	 * followed by an optional namespace, such as "zoom.foo" and "zoom.bar". To
	 * remove a listener, pass null as the listener.
	 * <p>
	 * For mousewheel events, which happen discretely with no explicit start and
	 * end reported by the browser, events that occur within 50 milliseconds of
	 * each other are grouped into a single zoom gesture. If you want more
	 * robust interpretation of these gestures, please petition your browser
	 * vendor of choice for better touch event support.
	 * <p>
	 * 
	 * @param type
	 * @param listener
	 * @return the current zoom instance
	 */
	public final native Zoom on(ZoomEventType type, DatumFunction<Void> listener)/*-{
		return this
				.on(
						type.@com.github.gwtd3.api.behaviour.Zoom.ZoomEventType::name()()
								.toLowerCase(),
						function(d, index) {
							listener.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},index);
						});
	}-*/;

	/**
	 * Return the current x-scale that is automatically adjusted when zooming,
	 * or null if no scale have been specified.
	 * <p>
	 * 
	 * @return the x-scale
	 */
	public final native QuantitativeScale<?> x()/*-{
		return this.x();
	}-*/;

	/**
	 * Specifies an x-scale whose domain should be automatically adjusted when
	 * zooming.
	 * <p>
	 * If the scale's domain or range is modified programmatically, this
	 * function should be called again.
	 * 
	 * @param scale
	 *            the scale
	 * @return Zoom the current Zoom object
	 */
	public final native Zoom x(QuantitativeScale<?> scale)/*-{
		return this.x(scale);
	}-*/;

	/**
	 * Return the current y-scale that is automatically adjusted when zooming,
	 * or null if no scale have been specified.
	 * <p>
	 * 
	 * @return the y-scale
	 */
	public final native QuantitativeScale<?> y()/*-{
		return this.y();
	}-*/;

	/**
	 * Specifies an y-scale whose domain should be automatically adjusted when
	 * zooming.
	 * <p>
	 * If not specified, returns the current y-scale, which defaults to null. If
	 * the scale's domain or range is modified programmatically, this function
	 * should be called again
	 * <p>
	 * 
	 * @param the
	 *            scale
	 * @return the current zoom object
	 */
	public final native Zoom y(QuantitativeScale<?> scale)/*-{
		return this.y(scale);
	}-*/;

	/**
	 * Return the zoom scale's allowed range as a two-element array, [*minimum*,
	 * maximum].
	 * <p>
	 * 
	 * @return the zoom scale's allowed range as a two-element array
	 */
	public final native Array<Double> scaleExtent()/*-{
		return this.scaleExtent();
	}-*/;

	/**
	 * Specifies the zoom scale's allowed range as a two-element array,
	 * [*minimum*, maximum]. If not specified, returns the current scale extent,
	 * which defaults to [0, Infinity].
	 * <p>
	 * 
	 * @param the
	 *            zoom scale's allowed range as a two-element array
	 * @return the current zoom object
	 */
	public final native Zoom scaleExtent(Array<Double> scale)/*-{
		return this.scaleExtent(scale);
	}-*/;

	/**
	 * If center is specified, sets the <a
	 * href="http://bl.ocks.org/mbostock/6226534"> focal point</a> [x, y] for
	 * mousewheel zooming and returns this zoom behavior.
	 * <p>
	 * A null center indicates that mousewheel zooming should zoom in and out
	 * around the current mouse location.
	 * <p>
	 * 
	 * @return the
	 */
	public final native Zoom center(double x, double y)/*-{
		return this.center([ x, y ]);
	}-*/;

	/**
	 * Returns the current focal point, which defaults to null.
	 * <p>
	 * A null center indicates that mousewheel zooming should zoom in and out
	 * around the current mouse location.
	 * <p>
	 * 
	 * @return the array of double
	 */
	public final native Array<Double> center()/*-{
		return this.center();
	}-*/;

	/**
	 * If size is specified, sets the viewport size to the specified dimensions
	 * [width, height] and returns this zoom behavior.
	 * <p>
	 * A size is needed to support smooth zooming during transitions.
	 * 
	 * @param width
	 *            the width of the viewport
	 * @param height
	 *            the height of the viewport
	 * @return the current zoom instance
	 */
	public final native Zoom size(int width, int height)/*-{
		return this.size([ width, height ]);
	}-*/;

	/**
	 * Returns the current viewport size which defaults to [960, 500].
	 * <p>
	 * 
	 * @return the size
	 */
	public final native Array<Double> size()/*-{
		return this.size();
	}-*/;

	/**
	 * Returns the current zoom scale, which defaults to 1.
	 * <p>
	 * 
	 * @return the current zoom scale
	 */
	public final native double scale()/*-{
		return this.scale();
	}-*/;

	/**
	 * Specifies the current zoom scale.
	 * <p>
	 * 
	 * @param scale
	 *            the zoom scale factor
	 * @return the current zoom object
	 */
	public final native Zoom scale(double scale)/*-{
		return this.scale(scale);
	}-*/;

	/**
	 * Returns the current translation vector, which defaults to [0, 0].
	 * <p>
	 * 
	 * @return the current translation vector
	 */
	public final native Array<Double> translate()/*-{
		return this.translate();
	}-*/;

	/**
	 * Specifies the current zoom translation vector.
	 * <p>
	 * 
	 * @param the
	 *            current zoom translation vector
	 * @return the current zoom object
	 */
	public final native Zoom translate(Array<Double> vector)/*-{
		return this.translate(vector);
	}-*/;

	/**
	 * Immediately dispatches a zoom gesture to registered listeners, as the
	 * three event sequence {@link ZoomEventType#ZOOMSTART},
	 * {@link ZoomEventType#ZOOM} and {@link ZoomEventType#ZOOMEND}.
	 * <p>
	 * This can be useful in triggering listeners after setting the
	 * {@link #translate(Array)} or {@link #scale(double)} programatically.
	 * <p>
	 * 
	 * @param selection
	 *            the selection to triggers the events to.
	 * @return the current zoom
	 */
	public final native Zoom event(Selection selection)/*-{
		return this.event(selection);
	}-*/;

	/**
	 * Registers the appropriate tweens so that the zoom behavior dispatches
	 * events over the course of the transition: a
	 * {@link ZoomEventType#ZOOMSTART} event when the transition starts from the
	 * previously-set view, {@link ZoomEventType#ZOOM} events for each tick of
	 * the transition, and finally a {@link ZoomEventType#ZOOMEND} event when
	 * the transition ends.
	 * <p>
	 * Note that the transition will be interrupted if the user starts zooming
	 * before the transition ends.
	 * <p>
	 * 
	 * @param selection
	 * @return
	 */
	public final native Zoom event(Transition selection)/*-{
		return this.event(selection);
	}-*/;

	/**
	 * Provide access to the properties of a zoom event.
	 * <p>
	 * Use {@link D3#zoomEvent()} from within a
	 * {@link Zoom#on(ZoomEventType, DatumFunction)} listener.
	 * <p>
	 * 
	 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
	 * 
	 */
	public static class ZoomEvent extends JavaScriptObject {

		protected ZoomEvent() {

		}

		/**
		 * The scale of the zoom
		 * <p>
		 * 
		 * @return the scale of the zoom
		 */

		public native final double scale()/*-{
			return this.scale;
		}-*/;

		/**
		 * A two-element array representing the current translation vector.
		 * 
		 * @return the translation vector
		 */

		public native final Array<Double> translate()/*-{
			return this.translate;
		}-*/;

		/**
		 * Shortcut to translate().getNumber(0).
		 * 
		 * @return the translation x coord
		 */

		public native final double translateX()/*-{

			return this.translate[0];

		}-*/;

		/**
		 * Shortcut to translate().getNumber(1).
		 * 
		 * @return the translation y coord
		 */

		public native final double translateY()/*-{

			return this.translate[1];

		}-*/;
	}

}
