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

import com.github.gwtd3.api.functions.DatumFunction;

/**
 * Generates path data for a closed piecewise linear curve, or polygon, as in an
 * area chart:
 * <p>
 * <img src="https://github.com/mbostock/d3/wiki/area.png"/>
 * <p>
 * Conceptually, the polygon is formed using two lines: the top line is formed
 * using the x- and y1-accessor functions, and proceeds from left-to-right; the
 * bottom line is added to this line, using the x- and y0-accessor functions,
 * and proceeds from right-to-left. By setting the transform attribute to rotate
 * the path element by 90 degrees, you can also generate vertical areas. By
 * changing the interpolation, you can also generate splines and step functions.
 * <p>
 * The area generator is designed to work in conjunction with the line
 * generator. For example, when producing an area chart, you might use an area
 * generator with a fill style, and a line generator with a stroke style to
 * emphasize the top edge of the area. Since the area generator is only used to
 * set the d attribute, you can control the appearance of the area using
 * standard SVG styles and attributes, such as fill.
 * <p>
 * To create streamgraphs (stacked area charts), use the stack layout. This
 * layout sets the y0 attribute for each value in a series, which can be used
 * from the y0- and y1-accessors. Note that each series must have the same
 * number of values per series, and each value must have the same x-coordinate;
 * if you have missing data or inconsistent x-coordinates per series, you must
 * resample and interpolate your data before computing the stacked layout.
 * <p>
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Area extends PathDataGenerator {

    protected Area() {
	super();
    }

    public static enum InterpolationMode {
	/**
	 * piecewise linear segments, as in a polyline.
	 */
	LINEAR("linear"),

	/**
	 * alternate between horizontal and vertical segments, as in a step
	 * function
	 */
	STEP("step"),

	/**
	 * alternate between vertical and horizontal segments, as in a step
	 * function.
	 */
	STEP_BEFORE("step-before"),

	/**
	 * alternate between horizontal and vertical segments, as in a step
	 * function
	 */
	STEP_AFTER("step-after"),

	/**
	 * a B-spline, with control point duplication on the ends.
	 */
	BASIS("basis"),
	/**
	 * an open B-spline; may not intersect the start or end.
	 */
	BASIS_OPEN("basis-open"),
	/**
	 * a Cardinal spline, with control point duplication on the ends.
	 */
	CARDINAL("cardinal"),
	/**
	 * an open Cardinal spline; may not intersect the start or end, but will
	 * intersect other control points.
	 */
	CARDINAL_OPEN("cardinal-open"),
	/**
	 * cubic interpolation that preserves monotonicity in y.
	 */
	MONOTONE("monotone");

	private final String value;

	private InterpolationMode(final String value) {
	    this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
	    return value;
	}

	public static InterpolationMode fromValue(final String value) {
	    return valueOf(value.toUpperCase().replace('-', '_'));
	}
    }

    /**
     * Returns the current interpolation mode.
     * 
     * @return the current interpolation mode.
     */
    public final native InterpolationMode interpolate() /*-{
        return @com.github.gwtd3.api.svg.Area.InterpolationMode::fromValue(Ljava/lang/String;)(this.interpolate());
    }-*/;

    /**
     * Set the current interpolation mode.
     * 
     * @param i
     *            the interpolation mode
     * @return the current area
     */
    public final native Area interpolate(final InterpolationMode i) /*-{
		return this
				.interpolate(i.@com.github.gwtd3.api.svg.Area.InterpolationMode::getValue()());
    }-*/;

    /**
     * Set the function used to compute x coordinates of points generated by
     * this area generator. The function is invoked for each element in the data
     * array passed to the area generator.
     * <p>
     * The default accessor assumes that each input element is a two-element
     * array of numbers.
     * 
     * @param datumFunction
     * @return
     */
    public final native Area x(final DatumFunction<Double> callback)/*-{
		return this
				.x(function(d, i) {
					return callback.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
    }-*/;

    // should create a JSO impl of DatumFunction calling himself
    // public final native DatumFunction<Double> x()/*-{
    // return this.x();
    // }-*/;

    /**
     * Set the x coordinates of points generated by this generator.
     * 
     * @param d
     * @return
     */
    public final native Area x(double d)/*-{
		return this.x(d);
    }-*/;

    /**
     * Set the x0 coordinates of points generated by this generator.
     * 
     * @param d
     * @return
     */
    public final native Area x0(double d) /*-{
		return this.x0(d);
    }-*/;

    /**
     * @see #x(DatumFunction).
     * @param callback
     * @return
     */
    public final native Area x0(final DatumFunction<Double> callback)/*-{
		return this
				.x0(function(d, i) {
					return callback.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
    }-*/;

    /**
     * Set the x1 coordinates of points generated by this generator.
     * 
     * @param d
     * @return
     */
    public final native Area x1(double d) /*-{
		return this.x1(d);
    }-*/;

    /**
     * @see #x(DatumFunction).
     * @param callback
     * @return
     */
    public final native Area x1(final DatumFunction<Double> callback)/*-{
		return this
				.x1(function(d, i) {
					return callback.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
    }-*/;

    /**
     * Set the y coordinates of points generated by this generator.
     * 
     * @param d
     * @return
     */
    public final native Area y(double d) /*-{
		return this.y(d);
    }-*/;

    /**
     * Set the y0 coordinates of points generated by this generator.
     * 
     * @param d
     * @return
     */
    public final native Area y0(double d) /*-{
		return this.y0(d);
    }-*/;

    /**
     * Set the y1 coordinates of points generated by this generator.
     * 
     * @param d
     * @return
     */
    public final native Area y1(double d) /*-{
		return this.y1(d);
    }-*/;

    /**
     * See {@link #x(DatumFunction)}.
     * <p>
     * Note that, like most other graphics libraries, SVG uses the top-left
     * corner as the origin and thus higher values of y are lower on the screen.
     * For visualization we often want the origin in the bottom-left corner
     * instead; one easy way to accomplish this is to invert the range of the
     * y-scale by using range([h, 0]) instead of range([0, h]).
     * 
     * @param callback
     * @return the current area
     */
    public final native Area y(final DatumFunction<Double> callback) /*-{
		return this
				.y(function(d, i) {
					return callback.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
    }-*/;

    /**
     * See {@link #y(DatumFunction)}.
     * <p>
     * Note that, like most other graphics libraries, SVG uses the top-left
     * corner as the origin and thus higher values of y are lower on the screen.
     * For visualization we often want the origin in the bottom-left corner
     * instead; one easy way to accomplish this is to invert the range of the
     * y-scale by using range([h, 0]) instead of range([0, h]).
     * 
     * @param callback
     * @return the current area
     */
    public final native Area y0(final DatumFunction<Double> callback) /*-{
		return this
				.y0(function(d, i) {
					return callback.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
    }-*/;

    /**
     * See {@link #y(DatumFunction)}.
     * <p>
     * Note that, like most other graphics libraries, SVG uses the top-left
     * corner as the origin and thus higher values of y are lower on the screen.
     * For visualization we often want the origin in the bottom-left corner
     * instead; one easy way to accomplish this is to invert the range of the
     * y-scale by using range([h, 0]) instead of range([0, h]).
     * 
     * @param callback
     * @return the current area
     */
    public final native Area y1(final DatumFunction<Double> callback) /*-{
		return this
				.y1(function(d, i) {
					return callback.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
    }-*/;

    /**
     * FIXME: D3 bug ??? Does not seem to work...
     * 
     * Sets the function used to controls where the area is defined.
     * <p>
     * The defined accessor can be used to define where the area is defined and
     * undefined, which is typically useful in conjunction with missing data;
     * the generated path data will automatically be broken into multiple
     * distinct subpaths, skipping undefined data.
     * <p>
     * 
     * @param callback
     * @return
     */
    public final native Line defined(final DatumFunction<Boolean> callback) /*-{
		return this
				.defined(function(d) {
					alert('yo');
					var result = callback.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(null,{datum:d}, 0);
					alert(result);
					return result;
				});
    }-*/;

    /**
     * Returns the current tension
     * 
     * @return the current tension
     */
    public final native double tension() /*-{
		return this.tension();
    }-*/;

    /**
     * Sets the Cardinal spline interpolation tension to the specified number in
     * the range [0, 1].
     * <p>
     * The tension only affects the Cardinal interpolation modes:
     * {@link InterpolationMode#CARDINAL},
     * {@link InterpolationMode#CARDINAL_OPEN} and
     * {@link InterpolationMode#CARDINAL_CLOSED}.
     * <p>
     * The default tension is 0.7.
     * <p>
     * In some sense, this can be interpreted as the length of the tangent; 1
     * will yield all zero tangents, and 0 yields a Catmull-Rom spline.
     * 
     * @see <a href="http://bl.ocks.org/1016220">live version</a>
     * @param tension
     *            the tension in the range [0, 1].
     * @return the current area generator
     */
    public final native Area tension(double tension) /*-{
		return this.tension(tension);
    }-*/;

    /**
     * Generate a piecewise linear area, as in an area chart.
     * <p>
     * Data must be an array-like structure. the type of the array elements
     * depends on the x and y functions. the default x and y functions assumes
     * that each input element is a two-element array of numbers.
     * 
     * @return
     */
    public final native <T> String apply(T data)/*-{
		return this(data);
    }-*/;

    /**
     * Generate a piecewise linear area, as in an area chart.
     * <p>
     * Data must be an array-like structure. the type of the array elements
     * depends on the x and y functions. the default x and y functions assumes
     * that each input element is a two-element array of numbers.
     * <p>
     * The index will be passed through to the line's accessor functions.
     * <p>
     * 
     * @param data
     * @param index
     * @return
     */
    public final native <T> String apply(T data, int index)/*-{
		return this(data, index);
    }-*/;

}
