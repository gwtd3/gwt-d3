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

import com.github.gwtd3.api.core.Value;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class ContinuousQuantitativeScale<S extends ContinuousQuantitativeScale<S>> extends QuantitativeScale<S> {

	protected ContinuousQuantitativeScale() {

	}

	/**
	 * Returns the value in the input domain x for the corresponding value in
	 * the output range y.
	 * <p>
	 * This represents the inverse mapping from range to domain. For a valid value y in the output range, linear(linear.invert(*y*)) equals y; similarly, for a valid value x in the
	 * input domain, linear.invert(linear(*x*)) equals x.
	 * <p>
	 * Equivalently, you can construct the invert operator by building a new scale while swapping the domain and range. The invert operator is particularly useful for interaction,
	 * say to determine the value in the input domain that corresponds to the pixel location under the mouse.
	 * 
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
	 * TODO todo totest; tocomment
	 * 
	 * @return
	 */
	public native final S ticks(int count)/*-{
		return this.ticks(count);
	}-*/;

	/**
	 * TODO todo totest; tocomment
	 * 
	 * @return
	 */
	public native final S tickFormat(int count)/*-{
		return this.ticks(count);
	}-*/;

	/**
	 * TODO todo totest; tocomment
	 * 
	 * @return
	 */
	public native final S tickFormat(int count, String formatSpecifier)/*-{
		return this.ticks(count, formatSpecifier);
	}-*/;
}
