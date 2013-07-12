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
package com.github.gwtd3.api.scales;

import com.github.gwtd3.api.JsArrays;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.time.TimeScale;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayUtils;

public class Scale<S extends Scale<S>> extends JavaScriptObject {

	protected Scale() {
	};

	// ========= Domain functions ===============

	/**
	 * Returns the scale's current input domain.
	 * 
	 * @return the scale current input domain
	 */
	public native final <T> Array<T> domain()/*-{
		return this.domain();
	}-*/;

	/**
	 * Sets the scale's input domain to the specified array of numbers.
	 * <p>
	 * The array must contain two or more numbers.
	 * <p>
	 * If the elements in the given array are not numbers, they will be coerced to numbers; this coercion happens similarly when the scale is called. Thus, a linear scale can be
	 * used to encode types such as date objects that can be converted to numbers; however, it is often more convenient to use {@link TimeScale} for dates.
	 * <p>
	 * (You can implement your own convertible number objects using valueOf.)
	 * <p>
	 * Although linear scales typically have just two numeric values in their domain, you can specify more than two values for a polylinear scale.
	 * <p>
	 * In this case, there must be an equivalent number of values in the output range. A polylinear scale represents multiple piecewise linear scales that divide a continuous
	 * domain and range. This is particularly useful for defining diverging quantitative scales. For example, to interpolate between white and red for negative values, and white
	 * and green for positive values, say:
	 * 
	 * <pre>
	 * {@code
	 * 
	 * 
	 *        var color = d3.scale.linear()
	 *        .domain([-1, 0, 1])
	 *        .range(["red", "white", "green"]);
	 * 
	 *        }
	 * </pre>
	 * 
	 * The resulting value of color(-.5) is rgb(255, 128, 128), and the value of color(.5) is rgb(128, 192, 128).
	 * <p>
	 * Internally, polylinear scales perform a binary search for the output interpolator corresponding to the given domain value. By repeating values in both the domain and range,
	 * you can also force a chunk of the input domain to map to a constant in the output range.
	 * <p>
	 * 
	 * @param array
	 *            the array of number
	 * @return this scale instance for chaining
	 */
	public native final S domain(JavaScriptObject array) /*-{
		return this.domain(array);
	}-*/;

	/**
	 * Shortcut to {@link #domain(JavaScriptObject)} for 2 numbers.
	 * <p>
	 * 
	 * @param a
	 *            the first bound of the domain
	 * @param b
	 *            the last bound of the domain
	 * @return this scale instance for chaining
	 */
	public native final S domain(double a, double b) /*-{
		return this.domain([ a, b ]);
	}-*/;

	/**
	 * Shortcut to {@link #domain(JavaScriptObject)} for 2 number-coercable strings,
	 * such as colors.
	 * <p>
	 * 
	 * @param a
	 *            the first bound of the domain
	 * @param b
	 *            the last bound of the domain
	 * @return this scale instance for chaining
	 */
	public native final S domain(String a, String b) /*-{
		return this.domain([ a, b ]);
	}-*/;

	/**
	 * Shortcut to {@link #domain(JavaScriptObject)} for an array of double.
	 * <p>
	 * 
	 * @param numbers
	 *            an array of numbers
	 * @return this scale instance for chaining
	 */
	public final S domain(final double... numbers) {
		return this.domain(JsArrayUtils.readOnlyJsArray(numbers));
	}

	// ========= Range functions ===============

	/**
	 * Returns the scale's current output range.
	 * 
	 * @return the range
	 */
	public native final <T> Array<T> range()/*-{
		return this.range();
	}-*/;

	/**
	 * Set the scale's output range.
	 * <p>
	 * Sets the scale's output range to the specified array of values. The array must contain two or more values, to match the cardinality of the input domain. The elements in the
	 * given array need not be numbers; any value that is supported by the underlying interpolator will work. However, numeric ranges are required for the invert operator.
	 * 
	 * @param values
	 *            the array of values.
	 * @return the current scale for chaining
	 */
	public final native S range(JavaScriptObject values) /*-{
		return this.range(values);
	}-*/;

	/**
	 * See {@link #range(JavaScriptObject)}.
	 * 
	 * @param numbers
	 * @return the current scale for chaining
	 */
	public final S range(final double... numbers) {
		return this.range(JsArrayUtils.readOnlyJsArray(numbers));
	}

	/**
	 * See {@link #range(JavaScriptObject)}.
	 * 
	 * @param numbers
	 * @return the current scale for chaining
	 */
	public final S range(final String... numbers) {
		return this.range(JsArrays.asJsArray(numbers));
	}

	// ========= Copy functions ===============

	/**
	 * Returns an exact copy of this scale.
	 * <p>
	 * Changes to this scale will not affect the returned scale, and vice versa.
	 * 
	 * @return the copy
	 */
	public native final S copy()/*-{
		return this.copy();
	}-*/;

	// ========= Apply functions ===============

	/**
	 * Given a value x in the input domain, returns the corresponding value in
	 * the output range.
	 * 
	 * @param d
	 *            the input value
	 * @return the output value
	 */
	public native final Value apply(JavaScriptObject d)/*-{
		return {
			datum : this(d)
		};
	}-*/;

	/**
	 * Given a value x in the input domain, returns the corresponding value in
	 * the output range.
	 * 
	 * @see #apply(JavaScriptObject)
	 * @param d
	 *            the input value
	 * @return the output value
	 */
	public native final Value apply(double d)/*-{
		return {
			datum : this(d)
		};
	}-*/;

	/**
	 * @see #apply(JavaScriptObject)
	 * @param d
	 *            the input value
	 * @return the output value
	 */
	public native final Value apply(String d)/*-{
		return {
			datum : this(d)
		};
	}-*/;
}
