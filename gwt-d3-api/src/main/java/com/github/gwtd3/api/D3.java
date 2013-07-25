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
import com.github.gwtd3.api.arrays.ForEachCallback;
import com.github.gwtd3.api.arrays.NumericForEachCallback;
import com.github.gwtd3.api.behaviour.Behavior;
import com.github.gwtd3.api.behaviour.Drag;
import com.github.gwtd3.api.core.Color;
import com.github.gwtd3.api.core.Formatter;
import com.github.gwtd3.api.core.HSLColor;
import com.github.gwtd3.api.core.ObjectAccessor;
import com.github.gwtd3.api.core.Prefix;
import com.github.gwtd3.api.core.RGBColor;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Transform;
import com.github.gwtd3.api.core.Transition;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.dsv.Dsv;
import com.github.gwtd3.api.dsv.DsvCallback;
import com.github.gwtd3.api.dsv.DsvObjectAccessor;
import com.github.gwtd3.api.functions.TimerFunction;
import com.github.gwtd3.api.interpolators.Interpolator;
import com.github.gwtd3.api.interpolators.InterpolatorFactory;
import com.github.gwtd3.api.interpolators.JavascriptFunctionInterpolator;
import com.github.gwtd3.api.interpolators.JavascriptFunctionInterpolatorDecorator;
import com.github.gwtd3.api.layout.Layout;
import com.github.gwtd3.api.svg.SVG;
import com.github.gwtd3.api.time.Time;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.core.client.JsArrayNumber;
import com.google.gwt.core.client.UnsafeNativeLong;
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
 * <li> {@link Transition} - interpolate attributes and styles smoothly over
 * time.
 * <li> {@link Array} manipulate arrays and objects with ease.
 * <li> {@link Color} - parse and manipulate colors; work with color spaces.
 * <p>
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
		return selectAll(JsArrays.asJsArray(nodes));
	}

	/**
	 * Selects the specified collection of elements.
	 * 
	 * @param nodes
	 *            the elements
	 * @return the selection
	 */
	public static final Selection selectAll(final Collection<Element> nodes) {
		return selectAll(JsArrays.asJsArray(nodes));
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

	private static final <T> void swap(final List<T> list, final int idx1, final int idx2) {
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

	/**
	 * Constructs a new RGB color with the specified r, g and b channel values.
	 * Each channel must be specified as an integer in the range [0,255]. The
	 * channels are available as the r, g and b attributes of the returned
	 * object.
	 * 
	 * @param r
	 *            the red channel
	 * @param g
	 *            the green channel
	 * @param b
	 *            the blue channel
	 * @return the new color instance
	 */
	public static final native RGBColor rgb(int r, int g, int b)/*-{
		return $wnd.d3.rgb(r, g, b);
	}-*/;

	/**
	 * Constructs a new RGB color by parsing the specified color string. The
	 * color string may be in a variety of formats:
	 * <ul>
	 * <li>rgb decimal - "rgb(255,255,255)"
	 * <li>hsl decimal - "hsl(120,50%,20%)"
	 * <li>rgb hexadecimal - "#ffeeaa"
	 * <li>rgb shorthand hexadecimal - "#fea"
	 * <li>named - "red", "white", "blue"
	 * </ul>
	 * 
	 * @param color
	 *            the color string representation
	 * @return the new color
	 */
	public static final native RGBColor rgb(final String color)/*-{
		return $wnd.d3.rgb(color);
	}-*/;

	/**
	 * Construct a new RGB color from the existing color object.
	 * <p>
	 * 
	 * @param color
	 *            the existing color object
	 * @return the new color
	 */
	public static final native RGBColor rgb(final Color color)/*-{
		return $wnd.d3.rgb(color);
	}-*/;

	/**
	 * Constructs a new HSL color with the specified hue h, saturation s and
	 * lightness l. The hue must be a number in the range [0,360]. The
	 * saturation and lightness must be in the range 0,1. These values are
	 * available as the h, s and l attributes of the returned object.
	 * 
	 * @param h
	 *            the hue channel [0;360]
	 * @param s
	 *            the saturation channel [0;1]
	 * @param l
	 *            the light channel [0;1]
	 * @return the new color instance
	 */
	public static final native HSLColor hsl(int h, double s, double l)/*-{
		return $wnd.d3.hsl(h, s, l);
	}-*/;

	/**
	 * Constructs a new HSL color by parsing the specified color string. The
	 * color string may be in a variety of formats:
	 * <ul>
	 * <li>rgb decimal - "rgb(255,255,255)"
	 * <li>hsl decimal - "hsl(120,50%,20%)"
	 * <li>rgb hexadecimal - "#ffeeaa"
	 * <li>rgb shorthand hexadecimal - "#fea"
	 * <li>named - "red", "white", "blue"
	 * </ul>
	 * 
	 * @param color
	 *            the color string representation
	 * @return the new color
	 */
	public static final native HSLColor hsl(final String color)/*-{
		return $wnd.d3.hsl(color);
	}-*/;

	/**
	 * Constructs a new HSL color from an existing color object.
	 * <p>
	 * 
	 * @param color
	 *            the existing color object
	 * @return the new color
	 */
	public static final native HSLColor hsl(final RGBColor color)/*-{
		return $wnd.d3.hsl(color);
	}-*/;

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
	public static final native Layout<?> layout()/*-{
		return $wnd.d3.layout;
	}-*/;

	// =========== interpolation ==============

	/**
	 * Returns a string interpolator between the two strings a and b.
	 * <p>
	 * The string interpolator finds numbers embedded in a and b, where each
	 * number is of the form:
	 * 
	 * <pre>
	 * {@code
	 * 	/[-+]?(?:\d+\.?\d*|\.?\d+)(?:[eE][-+]?\d+)?/g
	 * }
	 * </pre>
	 * <p>
	 * For each number embedded in b, the interpolator will attempt to find a
	 * corresponding number in a. If a corresponding number is found, a numeric
	 * interpolator is created using interpolateNumber. The remaining parts of
	 * the string b are used as a template: the static parts of the string b
	 * remain constant for the interpolation, with the interpolated numeric
	 * values embedded in the template.
	 * <p>
	 * For example, if a is "300 12px sans-serif", and b is
	 * "500 36px Comic-Sans", two embedded numbers are found. The remaining
	 * static parts of the string are a space between the two numbers (" "), and
	 * the suffix ("px Comic-Sans").
	 * 
	 * The result of the interpolator at t = .5 is "400 24px Comic-Sans".
	 * 
	 * @param a
	 *            the start
	 * @param b
	 *            the end
	 * @return the interpolator
	 */
	public static final Interpolator<String> interpolateString(final String a, final String b) {
		return new JavascriptFunctionInterpolatorDecorator<String>(interpolate0(a, b));
	}

	/**
	 * Returns an RGB color space interpolator between the two colors a and b.
	 * <p>
	 * The colors a and b need not be in RGB, but they will be converted to RGB
	 * using {@link D3#rgb(String)}.
	 * <p>
	 * The red, green and blue channels are interpolated linearly in a manner
	 * equivalent to interpolateRound, as fractional channel values are not
	 * allowed.
	 * <p>
	 * Also note that the interpolator modifies the returned color instance, and
	 * thus a copy must be made if you wish to avoid modification.
	 * 
	 * @param a
	 *            the start color
	 * @param b
	 *            the end color
	 * @return the interpolator
	 */
	public static final Interpolator<RGBColor> interpolateRgb(final String a, final String b) {
		return new JavascriptFunctionInterpolatorDecorator<RGBColor>(interpolateRgb0(D3.rgb(a), D3.rgb(b))) {
			@Override
			public RGBColor cast(final Value v) {
				return D3.rgb(v.asString());
			}
		};
	};

	/**
	 * Returns an RGB color space interpolator between the two colors a and b.
	 * <p>
	 * The colors a and b need not be in RGB, but they will be converted to RGB
	 * using {@link D3#rgb(String)}.
	 * <p>
	 * The red, green and blue channels are interpolated linearly in a manner
	 * equivalent to interpolateRound, as fractional channel values are not
	 * allowed.
	 * <p>
	 * Also note that the interpolator modifies the returned color instance, and
	 * thus a copy must be made if you wish to avoid modification.
	 * 
	 * @param a
	 *            the start color
	 * @param b
	 *            the end color
	 * @return the interpolator
	 */
	public static final Interpolator<RGBColor> interpolateRgb(final Color a, final Color b) {
		return new JavascriptFunctionInterpolatorDecorator<RGBColor>(interpolateRgb0(a, b)) {
			@Override
			public RGBColor cast(final Value v) {
				return D3.rgb(v.asString());
			}
		};
	}

	/**
	 * Returns an HSL color space interpolator between the two colors a and b.
	 * <p>
	 * The colors a and b need not be in HSL, but they will be converted to HSL
	 * using {@link D3#hsl(String)}.
	 * <p>
	 * The hue, saturation and lightness are interpolated linearly in a manner
	 * equivalent to interpolateNumber. (The shortest path between the start and
	 * end hue is used.)
	 * <p>
	 * Also note that the interpolator modifies the returned color instance, and
	 * thus a copy must be made if you wish to avoid modification.
	 * 
	 * @param a
	 *            the start color
	 * @param b
	 *            the end color
	 * @return the interpolator
	 */
	public static final Interpolator<HSLColor> interpolateHsl(final String a, final String b) {
		return new JavascriptFunctionInterpolatorDecorator<HSLColor>(interpolateHsl0(D3.hsl(a), D3.hsl(b))) {
			@Override
			public HSLColor cast(final Value v) {
				return D3.hsl(v.asString());
			}
		};
	};

	/**
	 * Returns an RGB color space interpolator between the two colors a and b.
	 * <p>
	 * The colors a and b need not be in RGB, but they will be converted to RGB
	 * using {@link D3#rgb(String)}.
	 * <p>
	 * The red, green and blue channels are interpolated linearly in a manner
	 * equivalent to interpolateRound, as fractional channel values are not
	 * allowed.
	 * <p>
	 * Also note that the interpolator modifies the returned color instance, and
	 * thus a copy must be made if you wish to avoid modification.
	 * 
	 * @param a
	 *            the start color
	 * @param b
	 *            the end color
	 * @return the interpolator
	 */
	public static final Interpolator<HSLColor> interpolateHsl(final Color a, final Color b) {
		return new JavascriptFunctionInterpolatorDecorator<HSLColor>(interpolateHsl0(a, b)) {
			@Override
			public HSLColor cast(final Value v) {
				return D3.hsl(v.asString());
			}
		};
	}

	/**
	 * Returns a numeric interpolator between the two numbers a and b. The
	 * returned interpolator is equivalent to:
	 * 
	 * <pre>
	 * {@code
	 * 	function interpolate(t) {
	 *   		return a * (1 - t) + b * t;
	 * 	}
	 * }
	 * </pre>
	 * <p>
	 * Caution: avoid interpolating to or from the number zero when the
	 * interpolator is used to generate a string (such as with attr). Very small
	 * values, when stringified, may be converted to scientific notation and
	 * cause a temporarily invalid attribute or style property value. For
	 * example, the number 0.0000001 is converted to the string "1e-7". This is
	 * particularly noticeable when interpolating opacity values. To avoid
	 * scientific notation, start or end the transition at 1e-6, which is the
	 * smallest value that is not stringified in exponential notation.
	 * 
	 * @param a
	 *            the start
	 * @param b
	 *            the end
	 * @return the interpolator
	 */
	public static final Interpolator<Double> interpolateNumber(final double a, final double b) {
		return new JavascriptFunctionInterpolatorDecorator<Double>(interpolateNumber0(a, b)) {
			@Override
			public Double cast(final Value v) {
				return new Double(v.asDouble());
			}
		};
	}

	/**
	 * Returns an array interpolator between the two arrays a and b.
	 * <p>
	 * Internally, an array template is created that is the same length in b.
	 * 
	 * For each element in b, if there exists a corresponding element in a, a
	 * generic interpolator is created for the two elements using interpolate.
	 * 
	 * If there is no such element, the static value from b is used in the
	 * template.
	 * 
	 * Then, for the given parameter t, the template's embedded interpolators
	 * are evaluated. The updated array template is then returned.
	 * <p>
	 * For example, if a is the array [0, 1] and b is the array [1, 10, 100],
	 * 
	 * then the result of the interpolator for t = .5 is the array [.5, 5.5,
	 * 100].
	 * 
	 * <p>
	 * Note: no defensive copy of the template array is created; modifications
	 * of the returned array may adversely affect subsequent evaluation of the
	 * interpolator. No copy is made because interpolators should be fast, as
	 * they are part of the inner loop of animation.
	 * 
	 * @param a
	 *            the array a
	 * @param b
	 *            the array b
	 * @return the interpolator
	 */
	public static final Interpolator<Array<?>> interpolateArray(final Array<?> a, final Array<?> b) {
		return new JavascriptFunctionInterpolatorDecorator<Array<?>>(interpolateArray0(a, b)) {
			@Override
			public Array<?> cast(final Value v) {
				return v.as();
			}
		};
	}

	/**
	 * Returns an object interpolator between the two objects a and b.
	 * <p>
	 * Internally, an object template is created that has the same properties as
	 * b.
	 * <p>
	 * For each property in b, if there exists a corresponding property in a, a
	 * generic interpolator is created for the two elements using interpolate.
	 * <p>
	 * If there is no such property, the static value from b is used in the
	 * template.
	 * <p>
	 * Then, for the given parameter t, the template's embedded interpolators
	 * are evaluated and the updated object template is then returned.
	 * <p>
	 * For example, if a is the object {x: 0, y: 1} and b is the object {x: 1,
	 * y: 10, z: 100}, the result of the interpolator for t = .5 is the object
	 * {x: .5, y: 5.5, z: 100}.
	 * <p>
	 * Object interpolation is particularly useful for dataspace interpolation,
	 * where data is interpolated rather than attribute values. For example, you
	 * can interpolate an object which describes an arc in a pie chart, and then
	 * use d3.svg.arc to compute the new SVG path data.
	 * <p>
	 * Note: no defensive copy of the template object is created; modifications
	 * of the returned object may adversely affect subsequent evaluation of the
	 * interpolator. No copy is made because interpolators should be fast, as
	 * they are part of the inner loop of animation.
	 * 
	 * @param a
	 *            the object a
	 * @param b
	 *            the object b
	 * @return the interpolator
	 */
	public static final <T extends JavaScriptObject> Interpolator<T> interpolateObject(final T a, final T b) {
		return new JavascriptFunctionInterpolatorDecorator<T>(interpolateObject0(a, b)) {
			@Override
			public T cast(final Value v) {
				return v.<T> as();
			}
		};
	}

	/**
	 * Returns an interpolator between the two 2D affine transforms represented
	 * by a and b. Each transform is decomposed to a standard representation of
	 * translate, rotate, x-skew and scale; these component transformations are
	 * then interpolated. This behavior is standardized by CSS: see matrix
	 * decomposition for animation.
	 * 
	 * @param a
	 *            the object a
	 * @param b
	 *            the object b
	 * @return the interpolator
	 */
	public static final Interpolator<Transform> interpolateTransform(final Transform a, final Transform b) {
		return new JavascriptFunctionInterpolatorDecorator<Transform>(interpolateTransform0(a, b)) {
			@Override
			public Transform cast(final Value v) {
				return Transform.parse(v.asString());
			}
		};
	}

	/**
	 * See {@link #interpolateNumber(double, double)}.
	 * 
	 * @param a
	 *            the start
	 * @param b
	 *            the end
	 * @return the interpolator
	 */
	public static final Interpolator<Double> interpolateNumber(final int a, final int b) {
		return interpolateNumber((double) a, (double) b);
	}

	/**
	 * See {@link #interpolate(double, double)}.
	 * 
	 * @param a
	 *            the start
	 * @param b
	 *            the end
	 * @return the interpolator
	 */
	public static final Interpolator<Double> interpolateNumber(final byte a, final byte b) {
		return interpolateNumber((double) a, (double) b);
	}

	/**
	 * See {@link #interpolate(double, double)}.
	 * 
	 * @param a
	 *            the start
	 * @param b
	 *            the end
	 * @return the interpolator
	 */
	public static final Interpolator<Double> interpolateNumber(final float a, final float b) {
		return interpolateNumber((double) a, (double) b);
	}

	/**
	 * See {@link #interpolate(double, double)}.
	 * 
	 * @param a
	 *            the start
	 * @param b
	 *            the end
	 * @return the interpolator
	 */
	public static final Interpolator<Double> interpolateNumber(final long a, final long b) {
		return interpolateNumber((double) a, (double) b);
	}

	/**
	 * See {@link #interpolate(double, double)}.
	 * 
	 * @param a
	 *            the start
	 * @param b
	 *            the end
	 * @return the interpolator
	 */
	public static final Interpolator<Double> interpolateNumber(final short a, final short b) {
		return interpolateNumber((double) a, (double) b);
	}

	/**
	 * Returns a numeric interpolator between the two numbers a and b; the
	 * interpolator is similar to {@link #interpolate(double, double)}, except
	 * it will round the resulting value to the nearest integer.
	 * <p>
	 * 
	 * @param a
	 *            the start
	 * @param b
	 *            the end
	 * @return the interpolator
	 */
	public static final Interpolator<Long> interpolateRound(final double a, final double b) {
		return new JavascriptFunctionInterpolatorDecorator<Long>(interpolateRound0(a, b)) {
			@Override
			public Long cast(final Value v) {
				return new Long((long) v.asDouble());
			}
		};
	}

	/**
	 * See {@link #interpolateRound(double, double)}.
	 * 
	 * @param a
	 *            the start
	 * @param b
	 *            the end
	 * @return the interpolator
	 */
	public static final Interpolator<Byte> interpolateRound(final byte a, final byte b) {
		return new JavascriptFunctionInterpolatorDecorator<Byte>(interpolateRound0(a, b)) {
			@Override
			public Byte cast(final Value v) {
				return new Byte(v.asByte());
			}
		};
	}

	/**
	 * See {@link #interpolateRound(double, double)}.
	 * 
	 * @param a
	 *            the start
	 * @param b
	 *            the end
	 * @return the interpolator
	 */
	public static final Interpolator<Character> interpolateRound(final char a, final char b) {
		return new JavascriptFunctionInterpolatorDecorator<Character>(interpolateRound0(a, b)) {
			@Override
			public Character cast(final Value v) {
				return new Character(v.asChar());
			}
		};
	}

	/**
	 * See {@link #interpolateRound(double, double)}.
	 * 
	 * @param a
	 *            the start
	 * @param b
	 *            the end
	 * @return the interpolator
	 */
	public static final Interpolator<Integer> interpolateRound(final int a, final int b) {
		return new JavascriptFunctionInterpolatorDecorator<Integer>(interpolateRound0(a, b)) {
			@Override
			public Integer cast(final Value v) {
				return new Integer(v.asInt());
			}
		};
	}

	/**
	 * See {@link #interpolateRound(double, double)}.
	 * 
	 * @param a
	 *            the start
	 * @param b
	 *            the end
	 * @return the interpolator
	 */
	public static final Interpolator<Long> interpolateRound(final long a, final long b) {
		return new JavascriptFunctionInterpolatorDecorator<Long>(interpolateRound0(a, b)) {
			@Override
			public Long cast(final Value v) {
				// this will not work !!!
				// see
				// https://developers.google.com/web-toolkit/doc/latest/DevGuideCodingBasicsJSNI#important
				// v.asLong()
				return new Long((long) v.asDouble());
			}
		};
	}

	/**
	 * See {@link #interpolateRound(double, double)}.
	 * 
	 * @param a
	 *            the start
	 * @param b
	 *            the end
	 * @return the interpolator
	 */
	public static final Interpolator<Short> interpolateRound(final short a, final short b) {
		return new JavascriptFunctionInterpolatorDecorator<Short>(interpolateRound0(a, b)) {
			@Override
			public Short cast(final Value v) {
				return new Short(v.asShort());
			}
		};
	}

	// public static final <T> Interpolator<T> interpolate(final T a, final T b)
	// {
	// return new JavascriptFunctionInterpolatorDecorator<T>(interpolate0(a, b))
	// {
	// @Override
	// public T interpolate(final double t) {
	// return delegate.interpolate(t).as();
	// }
	// };
	// }

	/**
	 * Actual JSNI implementation; the result is auto-casted to a
	 * {@link JavascriptFunctionInterpolator} and can be used by more specific
	 * versions of the
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	@UnsafeNativeLong
	private static final native <T> JavascriptFunctionInterpolator interpolate0(T a, T b) /*-{
		var result = $wnd.d3.interpolate(a, b);
		return result;
	}-*/;

	@UnsafeNativeLong
	private static final native JavascriptFunctionInterpolator interpolateNumber0(double a, double b) /*-{
		return $wnd.d3.interpolateNumber(a, b);
	}-*/;

	@UnsafeNativeLong
	private static final native JavascriptFunctionInterpolator interpolateRound0(double a, double b) /*-{
		return $wnd.d3.interpolateRound(a, b);
	}-*/;

	@UnsafeNativeLong
	private static final native JavascriptFunctionInterpolator interpolateRgb0(Color a, Color b) /*-{
		return $wnd.d3.interpolateRgb(a, b);
	}-*/;

	private static final native JavascriptFunctionInterpolator interpolateHsl0(Color a, Color b) /*-{
		return $wnd.d3.interpolateHsl(a, b);
	}-*/;

	// private static final native JavascriptFunctionInterpolator
	// interpolateHcl0(String a, String b) /*-{
	// return $wnd.d3.interpolateHcl(a, b);
	// }-*/;
	//
	// private static final native JavascriptFunctionInterpolator
	// interpolateLab0(String a, String b) /*-{
	// return $wnd.d3.interpolateLab(a, b);
	// }-*/;

	private static final native JavascriptFunctionInterpolator interpolateObject0(JavaScriptObject a, JavaScriptObject b) /*-{
		return $wnd.d3.interpolateObject(a, b);
	}-*/;

	private static final native JavascriptFunctionInterpolator interpolateArray0(Array<?> a, Array<?> b) /*-{
		return $wnd.d3.interpolateArray(a, b);
	}-*/;

	private static final native JavascriptFunctionInterpolator interpolateTransform0(Transform a, Transform b) /*-{
		return $wnd.d3.interpolateTransform(a, b);
	}-*/;

	// FIXME access to interpolators does not work as expected
	// see issue #42

	// private static final native JavascriptFunctionInterpolator
	// interpolateTransform0(String a, String b) /*-{
	// return $wnd.d3.interpolateTransform(a, b);
	// }-*/;
	/**
	 * The interpolator factory used by
	 * {@link #interpolateNumber(double, double)}
	 */
	// public static final InterpolatorFactory<Double> interpolateNumber =
	// interpolateNumberFactory();
	/**
	 * The interpolator factory used by
	 * {@link #interpolateRound(double, double)}
	 */
	// public static final InterpolatorFactory<Long> interpolateRound =
	// interpolateRoundFactory();
	/**
	 * The interpolator factory used by
	 * {@link #interpolateString(String, String)}
	 */
	// public static final InterpolatorFactory<String> interpolateString =
	// interpolateStringFactory();
	/**
	 * The interpolator factory used by {@link #interpolateRgb(Color, Color)}
	 */
	// FIXME: providing access to the interpolator factory does not work as is
	// since it
	// public static final InterpolatorFactory<RGBColor> interpolateRgb =
	// interpolateRgbFactory();
	// public static final InterpolatorFactory<HCLColor> interpolateHcl =
	// interpolateHclFactory();
	/**
	 * The interpolator factory used by {@link #interpolateHsl(Color, Color)}
	 */
	// public static final InterpolatorFactory<HSLColor> interpolateHsl =
	// interpolateHslFactory();
	// public static final InterpolatorFactory<LabColor> interpolateLab =
	// interpolateLabFactory();
	/**
	 * The interpolator factory used by {@link #interpolateArray(Array, Array)}
	 */
	// public static final InterpolatorFactory<Array<?>> interpolateArray =
	// interpolateArrayFactory();
	/**
	 * The interpolator factory used by
	 * {@link #interpolateObject(JavaScriptObject, JavaScriptObject)}
	 */
	// public static final InterpolatorFactory<JavaScriptObject>
	// interpolateObject = interpolateObjectFactory();

	// public static final InterpolatorFactory<String> interpolateTransform =
	// interpolateTransformFactory();

	// private static final native JSNIInterpolatorFactory<Double>
	// interpolateNumberFactory()/*-{
	// return $wnd.d3.interpolateNumber;
	// }-*/;
	//
	// private static final native JSNIInterpolatorFactory<Long>
	// interpolateRoundFactory()/*-{
	// return $wnd.d3.interpolateRound;
	// }-*/;
	//
	// private static final native JSNIInterpolatorFactory<String>
	// interpolateStringFactory()/*-{
	// return $wnd.d3.interpolateString;
	// }-*/;
	//
	// private static final native JSNIInterpolatorFactory<RGBColor>
	// interpolateRgbFactory()/*-{
	// return $wnd.d3.interpolateRgb;
	// }-*/;
	//
	// private static final native JSNIInterpolatorFactory<HSLColor>
	// interpolateHslFactory()/*-{
	// return $wnd.d3.interpolateHsl;
	// }-*/;

	// private static final native JSNIInterpolatorFactory<HCLColor>
	// interpolateHclFactory()/*-{
	// return $wnd.d3.interpolateHcl;
	// }-*/;
	//
	// private static final native JSNIInterpolatorFactory<LabColor>
	// interpolatLabFactory()/*-{
	// return $wnd.d3.interpolateLab;
	// }-*/;

	// private static final native JSNIInterpolatorFactory<Array<?>>
	// interpolateArrayFactory()/*-{
	// return $wnd.d3.interpolateArray;
	// }-*/;
	//
	// private static final native JSNIInterpolatorFactory<JavaScriptObject>
	// interpolateObjectFactory()/*-{
	// return $wnd.d3.interpolateObject;
	// }-*/;

	// private static final native JSNIInterpolatorFactory<Transform>
	// interpolateTransformFactory()/*-{
	// return $wnd.d3.interpolateTransform;
	// }-*/;

	/**
	 * The array of built-in interpolator factories, as used by #interpolate().
	 * <p>
	 * Additional interpolator factories may be pushed onto the end of this
	 * array.
	 * <p>
	 * Each factory may return an interpolator, if it supports interpolating the
	 * two specified input values; otherwise, the factory should return a falsey
	 * value and other interpolators will be tried.
	 * 
	 * @return the array of interpolator factories
	 */
	public static final native Array<InterpolatorFactory<?>> interpolators()/*-{
		return $wnd.d3.interpolators;
	}-*/;

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
	public static final native void timer(TimerFunction command, int delayMillis, int markMillis) /*-{
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
	public static final native <T> Dsv<T> csv(String url, DsvCallback<T> callback) /*-{
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
	public static final native <T> Dsv<T> csv(String url, DsvObjectAccessor<T> accessor, DsvCallback<T> callback)/*-{
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
	public static final native <T> Dsv<T> csv(String url, DsvObjectAccessor<T> accessor)/*-{
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
	public static final native <T> Dsv<T> tsv(String url, DsvCallback<T> callback) /*-{
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
	public static final native <T> Dsv<T> tsv(String url, DsvObjectAccessor<T> accessor, DsvCallback<T> callback)/*-{
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
	public static final native <T> Dsv<T> tsv(String url, DsvObjectAccessor<T> accessor)/*-{
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

	// ============= math ============

	/**
	 * Returns the maximum value in the given array using natural order.
	 * <p>
	 * If the array is empty, returns undefined.
	 * <p>
	 * Unlike the built-in {@link Math#max}, this method ignores undefined
	 * values; this is useful for computing the domain of a scale while only
	 * considering the defined region of the data.
	 * <p>
	 * In addition, elements are compared using natural order rather than
	 * numeric order. For example, the maximum of ["20", "3"] is "3", while the
	 * maximum of [20, 3] is 20.
	 * 
	 * @param array
	 *            the array to be evaluated
	 * @return the maximum as a {@link Value} object
	 */
	public static final native Value max(JavaScriptObject array) /*-{
		return {
			datum : $wnd.d3.max(array)
		};
	}-*/;

	/**
	 * Transform the values in the given array using the specified
	 * {@link ForEachCallback} and returns the maximum value in the transformed
	 * values using natural order. For example, the maximum of ["20", "3"] is
	 * "3", while the maximum of [20, 3] is 20. If you want to ensure the
	 * numeric order, please consider using
	 * {@link #max(JavaScriptObject, NumericForEachCallback)}.
	 * <p>
	 * The given {@link ForEachCallback} is equivalent to calling
	 * array.map(accessor) before computing the maximum value.
	 * <p>
	 * If the array is empty, returns undefined.
	 * <p>
	 * Unlike the built-in {@link Math#max}, this method ignores undefined
	 * values; this is useful for computing the domain of a scale while only
	 * considering the defined region of the data.
	 * <p>
	 * 
	 * @param array
	 *            the array to be transformed
	 * @param accessor
	 *            the function used to convert each element in the original
	 *            array to a transformed value
	 * @return the maximum of the transformed values as a {@link Value} object
	 */
	public static final native Value max(JavaScriptObject array, ForEachCallback<?> accessor) /*-{
		var rs = $wnd.d3
				.max(
						array,
						function(d, i, a) {
							return accessor.@com.github.gwtd3.api.arrays.ForEachCallback::forEach(Ljava/lang/Object;Lcom/github/gwtd3/api/core/Value;ILcom/github/gwtd3/api/arrays/Array;)(this,{datum:d},i,a);
						});
		return {
			datum : rs
		};
	}-*/;

	/**
	 * Transform the values in the given array using the specified
	 * {@link ForEachCallback} and returns the maximum value in the transformed
	 * values using natural order.
	 * <p>
	 * The given {@link NumericForEachCallback} is equivalent to calling
	 * array.map(accessor) before computing the maximum value.
	 * <p>
	 * If the array is empty, returns undefined.
	 * <p>
	 * Unlike the built-in {@link Math#max}, this method ignores undefined
	 * values; this is useful for computing the domain of a scale while only
	 * considering the defined region of the data.
	 * <p>
	 * In addition, elements are compared using numeric order.
	 * 
	 * @param array
	 *            the array to be transformed
	 * @param accessor
	 *            the function used to convert each element in the original
	 *            array to a transformed value
	 * @return the maximum of the transformed values as a {@link Value} object
	 */
	public static final native Value max(JavaScriptObject array, NumericForEachCallback accessor) /*-{
		var rs = $wnd.d3
				.max(
						array,
						function(d, i, a) {
							return accessor.@com.github.gwtd3.api.arrays.NumericForEachCallback::forEach(Ljava/lang/Object;Lcom/github/gwtd3/api/core/Value;ILcom/github/gwtd3/api/arrays/Array;)(this,{datum:d},i,a);
						});
		return {
			datum : rs
		};
	}-*/;

	/**
	 * Returns the minimum value in the given array using natural order.
	 * <p>
	 * If the array is empty, returns undefined.
	 * <p>
	 * Unlike the built-in {@link Math#min}, this method ignores undefined
	 * values; this is useful for computing the domain of a scale while only
	 * considering the defined region of the data.
	 * <p>
	 * In addition, elements are compared using natural order rather than
	 * numeric order. For example, the minimum of ["20", "3"] is "20", while the
	 * minimum of [20, 3] is 3.
	 * 
	 * @param array
	 *            the array to be evaluated
	 * @return the minimum as a {@link Value} object
	 */
	public static final native Value min(JavaScriptObject array) /*-{
		return {
			datum : $wnd.d3.min(array)
		};
	}-*/;

	/**
	 * Transform the values in the given array using the specified
	 * {@link ForEachCallback} and returns the minimum value in the transformed
	 * values using natural order. For example, the minimum of ["20", "3"] is
	 * "20", while the minimum of [20, 3] is 3. If you want to ensure the
	 * numeric order, please consider using
	 * {@link #max(JavaScriptObject, NumericForEachCallback)}.
	 * <p>
	 * The given {@link ForEachCallback} is equivalent to calling
	 * array.map(accessor) before computing the minimum value.
	 * <p>
	 * If the array is empty, returns undefined.
	 * <p>
	 * Unlike the built-in {@link Math#min}, this method ignores undefined
	 * values; this is useful for computing the domain of a scale while only
	 * considering the defined region of the data.
	 * <p>
	 * In addition, elements are compared using natural order rather than
	 * numeric order.
	 * 
	 * @param array
	 *            the array to be transformed
	 * @param accessor
	 *            the function used to convert each element in the original
	 *            array to a transformed value
	 * @return the minimum of the transformed values as a {@link Value} object
	 */
	public static final native Value min(JavaScriptObject array, ForEachCallback<?> accessor) /*-{
		var rs = $wnd.d3
				.min(
						array,
						function(d, i, a) {
							return accessor.@com.github.gwtd3.api.arrays.ForEachCallback::forEach(Ljava/lang/Object;Lcom/github/gwtd3/api/core/Value;ILcom/github/gwtd3/api/arrays/Array;)(this,{datum:d},i,a);
						});
		return {
			datum : rs
		};
	}-*/;

	/**
	 * Transform the values in the given array using the specified
	 * {@link ForEachCallback} and returns the minimum value in the transformed
	 * values using numeric order.
	 * <p>
	 * The given {@link NumericForEachCallback} is equivalent to calling
	 * array.map(accessor) before computing the minimum value.
	 * <p>
	 * If the array is empty, returns undefined.
	 * <p>
	 * Unlike the built-in {@link Math#min}, this method ignores undefined
	 * values; this is useful for computing the domain of a scale while only
	 * considering the defined region of the data.
	 * <p>
	 * 
	 * @param array
	 *            the array to be transformed
	 * @param accessor
	 *            the function used to convert each element in the original
	 *            array to a transformed value
	 * @return the minimum of the transformed values as a {@link Value} object
	 */
	public static final native Value min(JavaScriptObject array, NumericForEachCallback accessor) /*-{
		var rs = $wnd.d3
				.min(
						array,
						function(d, i, a) {
							return accessor.@com.github.gwtd3.api.arrays.NumericForEachCallback::forEach(Ljava/lang/Object;Lcom/github/gwtd3/api/core/Value;ILcom/github/gwtd3/api/arrays/Array;)(this,{datum:d},i,a);
						});
		return {
			datum : rs
		};
	}-*/;

	/**
	 * Find the minimum and maximum value in an array. This is equivalent to
	 * calling d3.min and d3.max simultaneously.
	 * 
	 * @param array
	 *            the given array.
	 * @return the minimum and maximum value in the given array using natural
	 *         order.
	 */
	public static final native JsArrayMixed extent(JavaScriptObject array) /*-{
		return $wnd.d3.extent(array);
	}-*/;

	/**
	 * Find the minimum and maximum value in an array. This is equivalent to
	 * calling d3.min and d3.max simultaneously.
	 * 
	 * @param array
	 *            the given array.
	 * @return the minimum and maximum value in the given array using natural
	 *         order.
	 */
	public static final native <D, R> JsArrayMixed extent(JavaScriptObject array, ObjectAccessor<D, R> accessor) /*-{
																													var accessor = accessor.@com.github.gwtd3.api.core.ObjectAccessor::apply(Ljava/lang/Object;I);
																													return $wnd.d3.extent(array, function(d, i) {
																													return accessor.@com.github.gwtd3.api.core.ObjectAccessor::apply(Ljava/lang/Object;I)(d, i);
																													});
																													}-*/;

	/**
	 * Compare two values for sorting.
	 * <p>
	 * This is the comparator function for natural order.
	 * 
	 * @return
	 */
	public static final native Sort ascending() /*-{
		return $wnd.d3.ascending;
	}-*/;

	/**
	 * Compare two values for sorting.
	 * <p>
	 * This is the comparator function for reverse natural order.
	 * 
	 * @return
	 */
	public static final native Sort descending() /*-{
		return $wnd.d3.descending;
	}-*/;

	/**
	 * Generate a range of <code>stop-1</code> numeric values, stored in an
	 * array, going from 0 to stop (excluded).
	 * 
	 * @see D3#range(double, double, double)
	 * @param stop
	 *            the maximum value (excluded)
	 * @return the array
	 */
	public static final native Array<Integer> range(double stop) /*-{
		return $wnd.d3.range(stop);
	}-*/;

	/**
	 * Generate a range of numeric values, stored in an array, going from 0 to
	 * stop (exluded), separated by step (>0).
	 * <p>
	 * For instance, range(10, 3) would produce the array [0,3,6,9].
	 * 
	 * 
	 * @see D3#range(double, double, double)
	 * @param stop
	 *            the maximum value (excluded)
	 * @param step
	 *            the step between each value
	 * @return the array
	 */
	public static final native Array<?> range(double stop, double step) /*-{
		return $wnd.d3.range(stop, step);
	}-*/;

	/**
	 * Generate a range of numeric values.
	 * <p>
	 * Generates an array containing an arithmetic progression, similar to the
	 * Python built-in range. This method is often used to iterate over a
	 * sequence of numeric or integer values, such as the indexes into an array.
	 * Unlike the Python version, the arguments are not required to be integers,
	 * though the results are more predictable if they are due to floating point
	 * precision. If step is omitted, it defaults to 1. If start is omitted, it
	 * defaults to 0. The stop value is not included in the result. The full
	 * form returns an array of numbers [*start*, start + step, start + 2 *
	 * step, ...]. If step is positive, the last element is the largest start +
	 * i * step less than stop; if step is negative, the last element is the
	 * smallest start + i * step greater than stop. If the returned array would
	 * contain an infinite number of values, an error is thrown rather than
	 * causing an infinite loop.
	 * 
	 * @param start
	 *            the first value-
	 * @param stop
	 *            the maximum value (excluded)
	 * @param step
	 *            the step between each value
	 * @return
	 */
	public static final native JavaScriptObject range(double start, double stop, double step) /*-{
		return $wnd.d3.range(start, stop, step);
	}-*/;

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
	public static final native Prefix formatPrefix(double value, double precision)/*-{
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
	
	/**
	 * Converts a javascript object of attributes and values to an associative array for
	 * use with the data function
	 * 
	 * @param a javascript object with attributes and values
	 * @return an associative array of javascript objects for each attribute of the form { key : value }
	 */
	public static final native Array<JavaScriptObject> entries(JavaScriptObject obj) /*-{
	    return $wnd.d3.entries(obj);
	}-*/;
}
