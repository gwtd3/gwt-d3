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
package com.github.gwtd3.api.scales;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.JsArrays;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.core.Formatter;
import com.github.gwtd3.api.core.Value;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayUtils;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class ContinuousQuantitativeScale<S extends ContinuousQuantitativeScale<S>> extends QuantitativeScale<S> {

	protected ContinuousQuantitativeScale() {

	}

	// =========== rangeRound ==========
	/**
	 * Sets the scale's output range to the specified array of values, while also setting the scale's interpolator to {@link D3#interpolateRound()}.
	 * <p>
	 * This is a convenience routine for when the values output by the scale should be exact integers, such as to avoid antialiasing artifacts. It is also possible to round the
	 * output values manually after the scale is applied.
	 * <p>
	 * 
	 * @param values
	 *            the array of values.
	 * @return the current scale for chaining
	 */
	public final native S rangeRound(JavaScriptObject values) /*-{
		return this.rangeRound(values);
	}-*/;

	/**
	 * See {@link #rangeRound(JavaScriptObject)}.
	 * 
	 * @param numbers
	 * @return the current scale for chaining
	 */
	public final S rangeRound(final double... numbers) {
		return this.rangeRound(JsArrayUtils.readOnlyJsArray(numbers));
	}

	/**
	 * See {@link #rangeRound(JavaScriptObject)}.
	 * 
	 * @param numbers
	 * @return the current scale for chaining
	 */
	public final S rangeRound(final String... numbers) {
		return this.rangeRound(JsArrays.asJsArray(numbers));
	}

	// =========== invert ==========

	/**
	 * Returns the value in the input domain x for the corresponding value in
	 * the output range y.
	 * <p>
	 * This represents the inverse mapping from range to domain. For a valid value y in the output range, linear(linear.invert(*y*)) equals y; similarly, for a valid value x in the
	 * input domain, linear.invert(linear(*x*)) equals x.
	 * <p>
	 * Equivalently, you can construct the invert operator by building a new scale while swapping the domain and range. The invert operator is particularly useful for interaction,
	 * say to determine the value in the input domain that corresponds to the pixel location under the mouse.
	 * <p>
	 * Note: the invert operator is only supported if the output range is numeric! D3 allows the output range to be any type; under the hood, d3.interpolate or a custom
	 * interpolator of your choice is used to map the normalized parameter t to a value in the output range. Thus, the output range may be colors, strings, or even arbitrary
	 * objects. As there is no facility to "uninterpolate" arbitrary types, the invert operator is currently supported only on numeric ranges.
	 * 
	 * @return
	 */
	public native final Value invert(double d)/*-{
		return {
			datum : this.invert(d)
		};
	}-*/;

	// =========== clamp ==========

	/**
	 * returns whether or not the scale currently clamps values to within the output range.
	 * 
	 * @return true if clamping is enabled, false otherwise.
	 */
	public native final boolean clamp()/*-{
		return this.clamp();
	}-*/;

	/**
	 * Enables or disables clamping accordingly.
	 * <p>
	 * By default, clamping is disabled, such that if a value outside the input domain is passed to the scale, the scale may return a value outside the output range through linear
	 * extrapolation.
	 * <p>
	 * For example, with the default domain and range of [0,1], an input value of 2 will return an output value of 2.
	 * <p>
	 * If clamping is enabled, the normalized domain parameter t is clamped to the range [0,1], such that the return value of the scale is always within the scale's output range.
	 * <p>
	 * 
	 * @return the current scale for chaining
	 */
	public native final S clamp(boolean clamping)/*-{
		return this.clamp(clamping);
	}-*/;

	// =========== nice ==========

	/**
	 * Extends the domain so that it starts and ends on nice round values.
	 * <p>
	 * This method typically modifies the scale's domain, and may only extend the bounds to the nearest round value.
	 * <p>
	 * The precision of the round value is dependent on the extent of the domain dx according to the following formula: exp(round(log(*dx*)) - 1).
	 * <p>
	 * Nicing is useful if the domain is computed from data and may be irregular.
	 * <p>
	 * For example, for a domain of [0.20147987687960267, 0.996679553296417], the nice domain is [0.2, 1]. If the domain has more than two values, nicing the domain only affects
	 * the first and last value.
	 * <p>
	 * 
	 * @return the current scale
	 */
	public native final S nice()/*-{
		return this.nice();
	}-*/;

	/**
	 * Same as {@link #nice()} but with more control.
	 * <p>
	 * The tick count argument allows greater control over the step size used to extend the bounds, guaranteeing that the ticks returned by {@link #ticks(int)} will exactly cover
	 * the domain.
	 * <p>
	 * 
	 * @return the current scale
	 */
	public native final S nice(int count)/*-{
		return this.nice(count);
	}-*/;

	// =========== ticks ==========

	/**
	 * Returns approximately count representative values from the scale's input domain.
	 * <p>
	 * The returned tick values are uniformly spaced, have human-readable values (such as multiples of powers of 10), and are guaranteed to be within the extent of the input
	 * domain.
	 * <p>
	 * Ticks are often used to display reference lines, or tick marks, in conjunction with the visualized data. The specified count is only a hint; the scale may return more or
	 * fewer values depending on the input domain.
	 * <p>
	 * 
	 * @return
	 */
	public native final <T> Array<T> ticks(int count)/*-{
		return this.ticks(count);
	}-*/;

	// =========== tickFormat ==========

	/**
	 * Returns a number format function suitable for displaying a tick value.
	 * <p>
	 * The specified count should have the same value as the count that is used to generate the tick values you want to display.
	 * <p>
	 * You don't have to use the scale's built-in tick format, but it automatically computes the appropriate precision based on the fixed interval between tick values.
	 * <p>
	 * 
	 * @param the
	 *            number of ticks to take into account to create the {@link Formatter}.
	 * @return a number format
	 */
	public native final Formatter tickFormat(int count)/*-{
		return this.tickFormat(count);
	}-*/;

	/**
	 * Returns a number format function suitable for displaying a tick value.
	 * <p>
	 * This is the same as {@link #tickFormat(int)}, except that the format argument allows a format specifier to be specified.
	 * <p>
	 * If the format specifier doesn’t have a defined precision, the precision will be set automatically by the scale, returning the appropriate format.
	 * <p>
	 * This provides a convenient, declarative way of specifying a format whose precision will be automatically set by the scale.
	 * <p>
	 * 
	 * @param the
	 *            number of ticks to take into account to create the {@link Formatter}.
	 * @param the
	 *            format specified, as documented in {@link Formatter}, to be used as a basis of the Formatter.
	 * @return a number format
	 */
	public native final Formatter tickFormat(int count, String formatSpecifier)/*-{
		return this.tickFormat(count, formatSpecifier);
	}-*/;
}
