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

import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.core.Formatter;
import com.github.gwtd3.api.functions.DatumFunction;

/**
 * Log scales are similar to linear scales, except there's a logarithmic
 * transform that is applied to the input domain value before the output range
 * value is computed.
 * <p>
 * The mapping to the output range value y can be expressed as a function of the
 * input domain value x: y = m log(*x*) + b.
 * <p>
 * Log scales also support negative values, in which case the input value is
 * multiplied by -1, and the resulting output value is also multiplied by -1.
 * <p>
 * However, note that the domain of a log scale should never contain zero, as
 * log(0) is negative infinity.
 * <p>
 * As with {@link LinearScale}s, log scales can also accept more than two values
 * for the domain and range, thus resulting in polylog scale.
 * <p>
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class LogScale extends ContinuousQuantitativeScale<LogScale> {

	protected LogScale() {

	}

	// =========== ticks ==========

	/**
	 * Returns representative values from the scale's input domain.
	 * <p>
	 * The returned tick values are uniformly spaced within each power of ten,
	 * and are guaranteed to be within the extent of the input domain.
	 * <p>
	 * Ticks are often used to display reference lines, or tick marks, in
	 * conjunction with the visualized data.
	 * <p>
	 * Note that the number of ticks cannot be customized (due to the nature of
	 * log scales); however, you can filter the returned array of values if you
	 * want to reduce the number of ticks.
	 * 
	 * @return the current scale
	 */
	public native final <T> Array<T> ticks()/*-{
		return this.ticks();
	}-*/;

	// =========== tickFormat ==========

	/**
	 * Returns a number format function suitable for displaying a tick value.
	 * <p>
	 * The returned tick format is implemented as d.toPrecision(1).
	 * <p>
	 * 
	 * @return the number format
	 */
	public native final Formatter tickFormat()/*-{
		return this.tickFormat();
	}-*/;

	/**
	 * Returns a number format function suitable for displaying a tick value.
	 * <p>
	 * The returned tick format is implemented as d.toPrecision(1).
	 * <p>
	 * Some of the tick labels may not be displayed; this is useful if there is
	 * not enough room to fit all of the tick labels. However, note that the
	 * tick marks will still be displayed (so that the log scale distortion
	 * remains visible).
	 * <p>
	 * 
	 * 
	 * @param the
	 *            number of ticks to take into account to create the
	 *            {@link Formatter}.
	 * @return a number format
	 */
	public native final Formatter tickFormat(int count)/*-{
		return this.tickFormat(count);
	}-*/;

	/**
	 * Returns a number format function suitable for displaying a tick value.
	 * <p>
	 * The returned tick format is implemented as d.toPrecision(1).
	 * <p>
	 * Some of the tick labels may not be displayed; this is useful if there is
	 * not enough room to fit all of the tick labels. However, note that the
	 * tick marks will still be displayed (so that the log scale distortion
	 * remains visible).
	 * <p>
	 * The format argument allow to specify a format as a string. If the format
	 * specifier doesn’t have a defined precision, the precision will be set
	 * automatically by the scale, returning the appropriate format.
	 * <p>
	 * This provides a convenient, declarative way of specifying a format whose
	 * precision will be automatically set by the scale.
	 * <p>
	 * 
	 * @param the
	 *            number of ticks to take into account to create the
	 *            {@link Formatter}.
	 * @param the
	 *            format specified, as documented in {@link Formatter}, to be
	 *            used as a basis of the Formatter.
	 * @return a number format
	 */
	public native final Formatter tickFormat(int count, String formatSpecifier)/*-{
		return this.tickFormat(count, formatSpecifier);
	}-*/;

	/**
	 * Returns a number format function suitable for displaying a tick value.
	 * <p>
	 * The returned tick format is implemented as d.toPrecision(1).
	 * <p>
	 * Some of the tick labels may not be displayed; this is useful if there is
	 * not enough room to fit all of the tick labels. However, note that the
	 * tick marks will still be displayed (so that the log scale distortion
	 * remains visible).
	 * <p>
	 * The format argument allow to specify a format as a string. If the format
	 * specifier doesn’t have a defined precision, the precision will be set
	 * automatically by the scale, returning the appropriate format.
	 * <p>
	 * This provides a convenient, declarative way of specifying a format whose
	 * precision will be automatically set by the scale.
	 * <p>
	 * 
	 * @param count
	 *            the number of ticks to take into account to create the
	 *            {@link Formatter}.
	 * @param formatFunction
	 *            the function used to format the tick label
	 * @return a number format
	 */
	public native final Formatter tickFormat(int count,
			DatumFunction<String> formatFunction)/*-{
		return this
				.tickFormat(
						count,
						function(d) {
							return formatFunction.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(null,{datum:d},0);
						});
	}-*/;

	// =========== nice ==========

	/**
	 * Extends the domain so that it starts and ends on nice round values.
	 * <p>
	 * This method typically modifies the scale's domain, and may only extend
	 * the bounds to the nearest round value.
	 * <p>
	 * The nearest round value is based on the nearest power of ten.
	 * <p>
	 * Nicing is useful if the domain is computed from data and may be
	 * irregular.
	 * <p>
	 * For example, for a domain of [0.20147987687960267, 0.996679553296417],
	 * the nice domain is [0.1, 1]. If the domain has more than two values,
	 * nicing the domain only affects the first and last value.
	 * <p>
	 * 
	 * @return the current scale
	 */
	public native final LogScale nice()/*-{
		return this.nice();
	}-*/;

	// =========== base ==========
	/**
	 * Returns the current base, which defaults to 10.
	 * 
	 * @return the current base
	 */
	public native final int base()/*-{
		return this.base();
	}-*/;

	/**
	 * Sets the base for this logarithmic scale.
	 * 
	 * @return the current scale
	 */
	public native final LogScale base(int b)/*-{
		return this.base(b);
	}-*/;

}
