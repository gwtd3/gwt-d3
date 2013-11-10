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
package com.github.gwtd3.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.behaviour.Behavior;
import com.github.gwtd3.api.behaviour.Drag;
import com.github.gwtd3.api.behaviour.Zoom;
import com.github.gwtd3.api.behaviour.Zoom.ZoomEvent;
import com.github.gwtd3.api.core.Formatter;
import com.github.gwtd3.api.core.Prefix;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Transition;
import com.github.gwtd3.api.dsv.Dsv;
import com.github.gwtd3.api.dsv.DsvCallback;
import com.github.gwtd3.api.dsv.DsvObjectAccessor;
import com.github.gwtd3.api.functions.TimerFunction;
import com.github.gwtd3.api.geo.Geography;
import com.github.gwtd3.api.geom.Geometry;
import com.github.gwtd3.api.layout.Layout;
import com.github.gwtd3.api.svg.SVG;
import com.github.gwtd3.api.time.Time;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.core.client.JsArrayNumber;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.WidgetCollection;

/**
 * Entry point for D3 api modules. A lot of methods of this class allow access
 * to other classes:
 * <ul>
 * <li>  {@link D3#select(Element)} methods and {@link Selection} - manipulate
 * elements in the current document.
 * <li>The array methods are in {@link Array} and {@link Arrays} classes.
 * <li>The interpolators methods are in {@link Array} and {@link Arrays}
 * classes.
 * <li>Interpolation methods are in {@link Interpolators}.
 * <p>
 * 
 * 
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class D3 extends JavaScriptObject {

	static {

		// inject the script according to the compiler mode
		// FIXME: rely on deferred binding to not include both script in the
		// compiled application
		// if (GWT.isProdMode()) {
		// ScriptInjector.fromString(((D3Bundle)
		// GWT.create(D3Bundle.class)).mainJs().getText());
		//
		// }
		// else {
		// ScriptInjector.fromString(((D3PrettyBundle)
		// GWT.create(D3PrettyBundle.class)).mainJs().getText());
		// }

	}
	// =========== scales ==============
	/**
	 * The scale factory module.
	 */
	public static final Scales scale = Scales.get();

	protected D3() {
	}

	/**
	 * @return the version of the d3 API
	 */
	public static final native String version()/*-{
		return $wnd.d3.version;
	}-*/;

	// =========== select ==============
	/**
	 * Selects the first element that matches the specified selector string,
	 * returning a single-element selection. If no elements in the current
	 * document match the specified selector, returns the empty selection. If
	 * multiple elements match the selector, only the first matching element (in
	 * document traversal order) will be selected.
	 * <p>
	 * The selector is a valid CSS3 selector. For example, you can select by tag
	 * ("div"), class (".awesome"), unique identifier ("#foo"), attribute
	 * ("[color=red]"), or containment ("parent child"). Selectors can also be
	 * intersected (".this.that" for logical AND) or unioned (".this, .that" for
	 * logical OR)
	 * 
	 * @param selector
	 *            a CSS3 selector
	 * @return the {@link Selection}
	 */
	public static final native Selection select(String selector)/*-{
		return $wnd.d3.select(selector);
	}-*/;

	/**
	 * Selects the specified element. This is useful if you already have a
	 * reference to an element, or a global such as {@link Document#getBody()}
	 * 
	 * @param element
	 *            the element to select
	 * @return the {@link Selection}
	 */
	public static final native Selection select(Element element)/*-{
		return $wnd.d3.select(element);
	}-*/;

	/**
	 * Selects the specified widget. This is useful if you already have a
	 * reference to a widget, or a global such as {@link RootPanel#get()}
	 * 
	 * @param widget
	 *            the widget to select
	 * @return the {@link Selection}
	 */
	public static final Selection select(final Widget widget) {
		return select(widget.getElement());
	}

	// ================ selectAll ================
	/**
	 * Selects all elements that match the specified selector. The elements will
	 * be selected in document traversal order (top-to-bottom). If no elements
	 * in the current document match the specified selector, returns the empty
	 * selection.
	 * 
	 * @param selector
	 * @return
	 */
	public static final native Selection selectAll(String selector)/*-{
		return $wnd.d3.selectAll(selector);
	}-*/;

	/**
	 * Selects the list of elements.
	 * 
	 * @param nodes
	 *            the elements
	 * @return the selection
	 */
	public static final native Selection selectAll(NodeList<?> nodes)/*-{
		return $wnd.d3.selectAll(nodes);
	}-*/;

	/**
	 * Selects the specified array of elements.
	 * 
	 * @param nodes
	 *            the elements
	 * @return the selection
	 */
	public static final native Selection selectAll(Array<Element> nodes)/*-{
		return $wnd.d3.selectAll(nodes);
	}-*/;

	/**
	 * Selects the specified array of elements.
	 * 
	 * @param nodes
	 *            the elements
	 * @return the selection
	 */
	public static final Selection selectAll(final Element... nodes) {
		return selectAll(Array.fromObjects(nodes));
	}

	/**
	 * Selects the specified collection of elements.
	 * 
	 * @param nodes
	 *            the elements
	 * @return the selection
	 */
	public static final Selection selectAll(final Collection<Element> nodes) {
		return selectAll(Array.fromIterable(nodes));
	}

	/**
	 * Selects the elements corresponding to the root elements of the widgets in
	 * the specified array.
	 * 
	 * @param nodes
	 *            the elements
	 * @return the selection
	 */
	public static final Selection selectAll(final Widget... nodes) {
		List<Element> elements = new ArrayList<Element>();
		for (Widget widget : nodes) {
			elements.add(widget.getElement());
		}
		return selectAll(elements);
	}

	/**
	 * Selects the specified collection of elements.
	 * 
	 * @param nodes
	 *            the elements
	 * @return the selection
	 */
	public static final Selection selectAll(final WidgetCollection widgets) {
		List<Element> elements = new ArrayList<Element>();
		for (Widget widget : widgets) {
			elements.add(widget.getElement());
		}
		return selectAll(elements);
	}

	/**
	 * Create an animated transition.
	 * <p>
	 * This is equivalent to {@link Selection#transition()
	 * D3.select(document).transition()}. This method is used rarely, as it is
	 * typically easier to derive a transition from an existing selection,
	 * rather than deriving a selection from an existing transition.
	 * 
	 * 
	 * @return the transition the new transition
	 */
	public static final native Transition transition()/*-{
		return $wnd.d3.transition();
	}-*/;

	// /**
	// * Create an animated transition. In the context of
	// * {@link Transition#each(com.github.gwtd3.api.functions.DatumFunction)},
	// * this method will create a new transition for the specified selection
	// that
	// * inherits the delay, duration and other properties of the parent
	// * transition. This is useful for implementing reusable components that
	// can
	// * be called either on selections or on transitions, in the latter case
	// * supporting deriving concurrent transitions. An example of this is D3’s
	// * axis component.
	// * <p>
	// * Outside of this context, this method is a no-op and should not be used.
	// * <p>
	// *
	// * @param selection
	// * @return the transition the new transition
	// */
	// public static final native Transition transition(Selection selection)/*-{
	// return $wnd.d3.transition(selection);
	// }-*/;

	// =========== Math ==============

	// =========== shuffle ==============

	/**
	 * Randomly shuffle the list of objects provided.
	 * <p>
	 * <i>Note: {@link Collections#shuffle(List)} is not GWT compatible</i>
	 * 
	 * @param objects
	 *            the list to shuffle
	 */
	public static final void shuffle(final List<?> objects) {
		for (int i = objects.size(); i > 1; i--) {
			swap(objects, i - 1, Random.nextInt(i));
		}
	}

	private static final <T> void swap(final List<T> list, final int idx1,
			final int idx2) {
		T temp = list.get(idx1);
		list.set(idx1, list.get(idx2));
		list.set(idx2, temp);
	}

	public static final void shuffle(final int[] input) {
		// --- Shuffle by exchanging each element randomly
		for (int i = 0; i < input.length; i++) {
			int randomPosition = Random.nextInt(input.length);
			int temp = input[i];
			input[i] = input[randomPosition];
			input[randomPosition] = temp;
		}
	}

	public static final void shuffle(final char[] input) {
		// --- Shuffle by exchanging each element randomly
		for (int i = 0; i < input.length; i++) {
			int randomPosition = Random.nextInt(input.length);
			char temp = input[i];
			input[i] = input[randomPosition];
			input[randomPosition] = temp;
		}
	}

	// ================ Colors ================

	// =========== svg ==============
	/**
	 * @return the svg module
	 */
	public static final native SVG svg()/*-{
		return $wnd.d3.svg;
	}-*/;

	// =========== layouts ==============
	/**
	 * @return the layout module
	 */
	public static final native Layout layout()/*-{
		return $wnd.d3.layout;
	}-*/;

	/**
	 * @return the {@link Geometry} module
	 */
	public static final native Geometry geom()/*-{
		return $wnd.d3.geom;
	}-*/;

	/**
	 * @return the {@link Geography} module
	 */
	public static final native Geography geo()/*-{
		return $wnd.d3.geo;
	}-*/;

	// =========== interpolation ==============

	// =========== ease ==============
	// cf Easing

	// =========== timer ==============

	/**
	 * Alias for {@link #timer(TimerFunction, int)} with a delay equals to 0.
	 * 
	 * @param command
	 *            the command to be executed until it returns true.
	 * @param delayMillis
	 *            the delay to expires before the command should start being
	 *            invoked (may be negative if markMillis is in the future)
	 */
	public static final native void timer(TimerFunction command) /*-{
		return $wnd.d3
				.timer(function() {
					return command.@com.github.gwtd3.api.functions.TimerFunction::execute()();
				});
	}-*/;

	/**
	 * Alias for {@link #timer(TimerFunction, int, int)} with a mark equals to
	 * the "now" timestamp (i.e <code>new Date().getTime()</code>).
	 * 
	 * @param command
	 *            the command to be executed until it returns true.
	 * @param delayMillis
	 *            the delay to expires before the command should start being
	 *            invoked (may be negative if markMillis is in the future)
	 */
	public static final native void timer(TimerFunction command, int delayMillis) /*-{
		return $wnd.d3
				.timer(
						function() {
							return command.@com.github.gwtd3.api.functions.TimerFunction::execute()();
						}, delayMillis);
	}-*/;

	/**
	 * Start a custom animation timer, invoking the specified
	 * {@link TimerFunction} repeatedly until it returns true.
	 * <p>
	 * There is no way to cancel the timer after it starts, so make sure your
	 * timer function returns true when done!
	 * 
	 * The optional numeric delay [unit: integer, millisecond] may be specified
	 * when the given function should only start to be invoked after delay
	 * milliseconds have expired after the specified mark timestamp [unit:
	 * integer, milliseconds since epoch].
	 * <p>
	 * When mark is omitted, Date.now() is assumed instead. Otherwise, you may
	 * use Date.getTime to convert your Date object to a suitable mark
	 * timestamp.
	 * <p>
	 * You may use delay and mark to specify relative and absolute moments in
	 * time when the function should start being invoked, e.g. a calendar-based
	 * event might be coded as
	 * 
	 * <pre>
	 * {@code
	 * 	Date appointment = new Date(2012, 09, 29, 14, 0, 0); // @ 29/sep/2012, 1400 hours
	 * ...
	 * // flash appointment on screen when it's due in 4 hours or less: note that negative (delay) is okay!
	 * d3.timer(flash_appointments_due, -4 * 3600 * 1000, appointment);
	 * }
	 * </pre>
	 * 
	 * @param command
	 *            the command to be executed until it returns true.
	 * @param delayMillis
	 *            the delay to expires before the command should start being
	 *            invoked (may be negative if markMillis is in the future)
	 * @param markMillis
	 *            the timestamp from which the delay starts
	 */
	public static final native void timer(TimerFunction command,
			int delayMillis, int markMillis) /*-{
		return $wnd.d3
				.timer(
						function() {
							return command.@com.github.gwtd3.api.functions.TimerFunction::execute()();
						}, delayMillis, markMillis);
	}-*/;

	/**
	 * Immediately execute (invoke once) any active timers.
	 * <p>
	 * Normally, zero-delay transitions are executed after an instantaneous
	 * delay (<10ms).
	 * <p>
	 * This can cause a brief flicker if the browser renders the page twice:
	 * once at the end of the first event loop, then again immediately on the
	 * first timer callback.
	 * <p>
	 * By flushing the timer queue at the end of the first event loop, you can
	 * run any zero-delay transitions immediately and avoid the flicker.
	 * <p>
	 * Note: the original method is d3.timer.flush() but has been pushed here
	 * because the timer API is limited to this method
	 * 
	 */
	public static final native void timerFlush()/*-{
		return $wnd.d3.timer.flush();
	}-*/;

	// =========== time ==============

	/**
	 * @return the time module
	 */
	public static final native Time time() /*-{
		return $wnd.d3.time;
	}-*/;

	// ========= events and interactions ============
	/**
	 * Retrieve the current event, if any.
	 * <p>
	 * This global variable exists during an event listener callback registered
	 * with the on operator. The current event is reset after the listener is
	 * notified in a finally block. This allows the listener function to have
	 * the same form as other operator functions, being passed the current datum
	 * d and index i.
	 * <p>
	 * The {@link D3#event()} object is a DOM event and implements the standard
	 * event fields like timeStamp and keyCode as well as methods like
	 * preventDefault() and stopPropagation(). While you can use the native
	 * event's pageX and pageY, it is often more convenient to transform the
	 * event position to the local coordinate system of the container that
	 * received the event. For example, if you embed an SVG in the normal flow
	 * of your page, you may want the event position relative to the top-left
	 * corner of the SVG image. If your SVG contains transforms, you might also
	 * want to know the position of the event relative to those transforms.
	 * <p>
	 * Use the d3.mouse operator for the standard mouse pointer, and use
	 * d3.touches for multitouch events on iOS.
	 * 
	 * @return
	 */
	public static final native Event event()/*-{
		return $wnd.d3.event;
	}-*/;

	/**
	 * Retrieve the current event if any, as a {@link Coords} object containing
	 * the x and y of the mouse. This is useful when using {@link Drag}
	 * behavior.
	 * 
	 * @return the current event as a Coords object
	 */
	public static final native Coords eventAsCoords()/*-{
		return $wnd.d3.event;
	}-*/;

	/**
	 * Retrieve the current event if any, as a {@link Coords} object containing
	 * the dx and dy representing the element's coordinates relative to its
	 * position at the beginning of the gesture. This is useful when using
	 * {@link Drag} behavior.
	 * 
	 * @return the current event as a Coords object
	 */
	public static final native Coords eventAsDCoords()/*-{
		return {
			x : $wnd.d3.event.dx,
			y : $wnd.d3.event.dy
		};
	}-*/;

	/**
	 * Returns the x and y coordinates of the current d3.event, relative to the
	 * specified container. The container may be an HTML or SVG container
	 * element, such as an svg:g or svg:svg. The coordinates are returned as a
	 * two-element array [ x, y].
	 * 
	 * @param container
	 * @return
	 */
	public static final native JsArrayNumber mouse(Node container)/*-{
		return $wnd.d3.mouse(container);
	}-*/;

	/**
	 * Returns the x and y coordinates of the current d3.event, relative to the
	 * specified container. The container may be an HTML or SVG container
	 * element, such as an svg:g or svg:svg. The coordinates are returned as a
	 * two-element array [ x, y].
	 * 
	 * @param container
	 * @return
	 */
	public static final native Coords mouseAsCoords(Node container)/*-{
		var m = $wnd.d3.mouse(container);
		return {
			x : m[0],
			y : m[1],
		};
	}-*/;

	/**
	 * Returns the x coordinate of the current d3.event, relative to the
	 * specified container. The container may be an HTML or SVG container
	 * element, such as an svg:g or svg:svg. The coordinates are returned as a
	 * two-element array [ x, y].
	 * 
	 * @param container
	 * @return
	 */
	public static final native double mouseX(Node container)/*-{
		return $wnd.d3.mouse(container)[0];
	}-*/;

	/**
	 * Returns the y coordinate of the current d3.event, relative to the
	 * specified container. The container may be an HTML or SVG container
	 * element, such as an svg:g or svg:svg. The coordinates are returned as a
	 * two-element array [ x, y].
	 * 
	 * @param container
	 * @return
	 */
	public static final native double mouseY(Node container)/*-{
		return $wnd.d3.mouse(container)[1];
	}-*/;

	/**
	 * Returns the x and y coordinates of each touch associated with the current
	 * d3.event, based on the touches attribute, relative to the specified
	 * container.
	 * <p>
	 * The container may be an HTML or SVG container element, such as an svg:g
	 * or svg:svg.
	 * <p>
	 * The coordinates are returned as an array of two-element arrays [ [ x1,
	 * y1], [ x2, y2], … ].
	 * <p>
	 * 
	 * @param container
	 *            the node to get the coords relative to
	 * @return an array of array of 2 elements.
	 */
	public static final native JsArrayMixed touches(Node container)/*-{
		return $wnd.d3.touches(container);
	}-*/;

	// =========== csv ==============

	/**
	 * @return the CSV module
	 */
	public static final native <T> Dsv<T> csv() /*-{
		return $wnd.d3.csv;
	}-*/;

	/**
	 * Issues an HTTP GET request for the comma-separated values (CSV) file at
	 * the specified url.
	 * <p>
	 * The file contents are assumed to be RFC4180-compliant. The mime type of
	 * the request will be "text/csv". The request is processed asynchronously,
	 * such that this method returns immediately after opening the request. When
	 * the CSV data is available, the specified callback will be invoked with
	 * the parsed rows as the argument. If an error occurs, the callback
	 * function will instead be invoked with null.
	 */
	public static final native <T> Dsv<T> csv(String url,
			DsvCallback<T> callback) /*-{
		return $wnd.d3
				.csv(
						url,
						function(error, rows) {
							callback.@com.github.gwtd3.api.dsv.DsvCallback::get(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/github/gwtd3/api/dsv/DsvRows;)(error, rows);
						});
	}-*/;

	/**
	 * Issues an HTTP GET request for the comma-separated values (CSV) file at
	 * the specified url.
	 * <p>
	 * The file contents are assumed to be RFC4180-compliant. The mime type of
	 * the request will be "text/csv". The request is processed asynchronously,
	 * such that this method returns immediately after opening the request. When
	 * the CSV data is available, the specified callback will be invoked with
	 * the parsed rows as the argument. If an error occurs, the callback
	 * function will instead be invoked with null. The accessor may be
	 * specified, which is then passed to d3.csv.parse; the accessor may also be
	 * specified by using the return request object’s row function.
	 */
	public static final native <T> Dsv<T> csv(String url,
			DsvObjectAccessor<T> accessor, DsvCallback<T> callback)/*-{
		return $wnd.d3
				.csv(
						url,
						function(row, index) {
							return accessor.@com.github.gwtd3.api.core.ObjectAccessor::apply(Ljava/lang/Object;I)(row, index);
						},
						function(error, rows) {
							callback.@com.github.gwtd3.api.dsv.DsvCallback::get(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/github/gwtd3/api/dsv/DsvRows;)(error, rows);
						});
	}-*/;

	/**
	 * Issues an HTTP GET request for the comma-separated values (CSV) file at
	 * the specified url.
	 * <p>
	 * The file contents are assumed to be RFC4180-compliant. The mime type of
	 * the request will be "text/csv". The request is processed asynchronously,
	 * such that this method returns immediately after opening the request. When
	 * the CSV data is available, the specified callback will be invoked with
	 * the parsed rows as the argument. If an error occurs, the callback
	 * function will instead be invoked with null. The accessor may be
	 * specified, which is then passed to d3.csv.parse; the accessor may also be
	 * specified by using the return request object’s row function.
	 */
	public static final native <T> Dsv<T> csv(String url,
			DsvObjectAccessor<T> accessor)/*-{
		return $wnd.d3
				.csv(
						url,
						function(row, index) {
							return accessor.@com.github.gwtd3.api.core.ObjectAccessor::apply(Ljava/lang/Object;I)(row, index);
						});
	}-*/;

	/**
	 * Issues an HTTP GET request for the comma-separated values (CSV) file at
	 * the specified url.
	 * <p>
	 * The file contents are assumed to be RFC4180-compliant. The mime type of
	 * the request will be "text/csv". The request is processed asynchronously,
	 * such that this method returns immediately after opening the request.
	 */
	public static final native <T> Dsv<T> csv(String url)/*-{
		return $wnd.d3.csv(url);
	}-*/;

	// =========== tsv ==============

	/**
	 * @return the TSV module
	 */
	public static final native <T> Dsv<T> tsv() /*-{
		return $wnd.d3.tsv;
	}-*/;

	/**
	 * Issues an HTTP GET request for the comma-separated values (TSV) file at
	 * the specified url.
	 * <p>
	 * The file contents are assumed to be RFC4180-compliant. The mime type of
	 * the request will be "text/tsv". The request is processed asynchronously,
	 * such that this method returns immediately after opening the request. When
	 * the TSV data is available, the specified callback will be invoked with
	 * the parsed rows as the argument. If an error occurs, the callback
	 * function will instead be invoked with null.
	 */
	public static final native <T> Dsv<T> tsv(String url,
			DsvCallback<T> callback) /*-{
		return $wnd.d3
				.tsv(
						url,
						function(error, rows) {
							callback.@com.github.gwtd3.api.dsv.DsvCallback::get(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/github/gwtd3/api/dsv/DsvRows;)(error, rows);
						});
	}-*/;

	/**
	 * Issues an HTTP GET request for the comma-separated values (TSV) file at
	 * the specified url.
	 * <p>
	 * The file contents are assumed to be RFC4180-compliant. The mime type of
	 * the request will be "text/tsv". The request is processed asynchronously,
	 * such that this method returns immediately after opening the request. When
	 * the TSV data is available, the specified callback will be invoked with
	 * the parsed rows as the argument. If an error occurs, the callback
	 * function will instead be invoked with null. The accessor may be
	 * specified, which is then passed to d3.tsv.parse; the accessor may also be
	 * specified by using the return request object’s row function.
	 */
	public static final native <T> Dsv<T> tsv(String url,
			DsvObjectAccessor<T> accessor, DsvCallback<T> callback)/*-{
		return $wnd.d3
				.tsv(
						url,
						function(row, index) {
							return accessor.@com.github.gwtd3.api.core.ObjectAccessor::apply(Ljava/lang/Object;I)(row, index);
						},
						function(error, rows) {
							callback.@com.github.gwtd3.api.dsv.DsvCallback::get(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/github/gwtd3/api/dsv/DsvRows;)(error, rows);
						});
	}-*/;

	/**
	 * Issues an HTTP GET request for the comma-separated values (TSV) file at
	 * the specified url.
	 * <p>
	 * The file contents are assumed to be RFC4180-compliant. The mime type of
	 * the request will be "text/tsv". The request is processed asynchronously,
	 * such that this method returns immediately after opening the request. When
	 * the TSV data is available, the specified callback will be invoked with
	 * the parsed rows as the argument. If an error occurs, the callback
	 * function will instead be invoked with null. The accessor may be
	 * specified, which is then passed to d3.tsv.parse; the accessor may also be
	 * specified by using the return request object’s row function.
	 */
	public static final native <T> Dsv<T> tsv(String url,
			DsvObjectAccessor<T> accessor)/*-{
		return $wnd.d3
				.tsv(
						url,
						function(row, index) {
							return accessor.@com.github.gwtd3.api.core.ObjectAccessor::apply(Ljava/lang/Object;I)(row, index);
						});
	}-*/;

	/**
	 * Issues an HTTP GET request for the comma-separated values (TSV) file at
	 * the specified url.
	 * <p>
	 * The file contents are assumed to be RFC4180-compliant. The mime type of
	 * the request will be "text/tsv". The request is processed asynchronously,
	 * such that this method returns immediately after opening the request.
	 */
	public static final native <T> Dsv<T> tsv(String url)/*-{
		return $wnd.d3.tsv(url);
	}-*/;

	// ============= json ============

	// ============= array ============

	// =================== format methods ====================

	/**
	 * Returns a new {@link Formatter} function with the given string specifier.
	 * A format function takes a number as the only argument, and returns a
	 * string representing the formatted number. Please see {@link Formatter}
	 * javadoc for the specifier specification.
	 * 
	 * @see <a
	 *      href="https://github.com/mbostock/d3/wiki/Formatting#wiki-d3_format">D3.js
	 *      official documentation</a>
	 * @param specifier
	 *            the given string specifier.
	 * @return the format function.
	 */
	public static final native Formatter format(String specifier) /*-{
		return $wnd.d3.format(specifier);
	}-*/;

	/**
	 * Return the SI Prefix for the specified value at the specified precision.
	 * <p>
	 * This method is used by {@link #format(String)} for the %s format.
	 * 
	 * @param value
	 *            the value to be prefixed
	 * @param precision
	 *            the precision
	 * @return the prefix
	 */
	public static final native Prefix formatPrefix(double value,
			double precision)/*-{
		return $wnd.d3.formatPrefix(value, precision);
	}-*/;

	/**
	 * Returns the value x rounded to n digits after the decimal point. If n is
	 * omitted, it defaults to zero. The result is a number. Values are rounded
	 * to the closest multiple of 10 to the power minus n; if two multiples are
	 * equally close, the value is rounded up in accordance with the built-in
	 * round function. Note that the resulting number when converted to a string
	 * may be imprecise due to IEEE floating point precision; to format a number
	 * to a string with a fixed number of decimal points, use d3.format instead.
	 * 
	 * @param value
	 * @param digits
	 * @return
	 */
	public static final native double round(double value, int digits)/*-{
		return $wnd.d3.round(value, digits);
	}-*/;

	/**
	 * Returns a quoted (escaped) version of the specified string such that the
	 * string may be embedded in a regular expression as a string literal.
	 * 
	 * @param string
	 *            the input string
	 * @return the regexp escaped string
	 */
	public static final native String requote(String string)/*-{
		return $wnd.d3.requote(string);
	}-*/;

	// =========== behaviours ==============
	/**
	 * @return the behaviour module
	 */
	public static final native Behavior behavior()/*-{
		return $wnd.d3.behavior;
	}-*/;

	/**
	 * 
	 * Get the ZoomEvent from within a {@link Zoom} listener.
	 * 
	 * @return the zoom event
	 */

	public static final ZoomEvent zoomEvent() {

		return event().cast();

	}

	// =========== misc ==========
	/**
	 * Return the identity function:
	 * <p>
	 * <code>function(d) { return d; }</code>
	 * 
	 * @return the identity function
	 */
	public static final native JavaScriptObject identity()/*-{
		return function(d) {
			return d;
		};
	}-*/;
}
