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
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.time.TimeScale;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayUtils;

/**
 * {@link QuantitativeScale} with a continuous range:
 * <ul>
 * <li>{@link LinearScale} have a linear interpolator
 * <li>{@link LogScale} have apply a log function to the domain
 * <li>{@link PowScale}
 * <li>{@link IdentityScale}
 * <li>{@link TimeScale} are linear scale for timestamp
 * </ul>
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public abstract class ContinuousQuantitativeScale<S extends ContinuousQuantitativeScale<S>>
		extends QuantitativeScale<S> {

	protected ContinuousQuantitativeScale() {

	}

	// =========== rangeRound ==========
	/**
	 * Sets the scale's output range to the specified array of values, while
	 * also setting the scale's interpolator to {@link D3#interpolateRound()}.
	 * <p>
	 * This is a convenience routine for when the values output by the scale
	 * should be exact integers, such as to avoid antialiasing artifacts. It is
	 * also possible to round the output values manually after the scale is
	 * applied.
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
		return this.rangeRound(Array.fromObjects(numbers));
	}

	// =========== invert ==========

	/**
	 * Returns the value in the input domain x for the corresponding value in
	 * the output range y.
	 * <p>
	 * This represents the inverse mapping from range to domain. For a valid
	 * value y in the output range, apply(scale.invert(y)) equals y; similarly,
	 * for a valid value x in the input domain, scale.invert(apply(x)) equals x.
	 * <p>
	 * Equivalently, you can construct the invert operator by building a new
	 * scale while swapping the domain and range. The invert operator is
	 * particularly useful for interaction, say to determine the value in the
	 * input domain that corresponds to the pixel location under the mouse.
	 * <p>
	 * Note: the invert operator is only supported if the output range is
	 * numeric! D3 allows the output range to be any type; under the hood,
	 * d3.interpolate or a custom interpolator of your choice is used to map the
	 * normalized parameter t to a value in the output range. Thus, the output
	 * range may be colors, strings, or even arbitrary objects. As there is no
	 * facility to "uninterpolate" arbitrary types, the invert operator is
	 * currently supported only on numeric ranges.
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
	 * returns whether or not the scale currently clamps values to within the
	 * output range.
	 * 
	 * @return true if clamping is enabled, false otherwise.
	 */
	public native final boolean clamp()/*-{
		return this.clamp();
	}-*/;

	/**
	 * Enables or disables clamping accordingly.
	 * <p>
	 * By default, clamping is disabled, such that if a value outside the input
	 * domain is passed to the scale, the scale may return a value outside the
	 * output range through linear extrapolation.
	 * <p>
	 * For example, with the default domain and range of [0,1], an input value
	 * of 2 will return an output value of 2.
	 * <p>
	 * If clamping is enabled, the normalized domain parameter t is clamped to
	 * the range [0,1], such that the return value of the scale is always within
	 * the scale's output range.
	 * <p>
	 * 
	 * @return the current scale for chaining
	 */
	public native final S clamp(boolean clamping)/*-{
		return this.clamp(clamping);
	}-*/;

	// ======== interpolate ========
	// TODO : WORK IN PROGRESS
	// /**
	// * If factory is specified, sets the scale's output interpolator using the
	// * specified factory.
	// * <p>
	// * The interpolator factory defaults to {@link Interpolators#interpolate},
	// * and is used to map the normalized domain parameter t in [0,1] to the
	// * corresponding value in the output range. The interpolator factory will
	// be
	// * used to construct interpolators for each adjacent pair of values from
	// the
	// * output range.
	// *
	// * @param factory
	// * the factory used to create an interpolator
	// * @return the scale
	// */
	// public final S interpolate(InterpolatorFactory<?> factory) {
	// return interpolate0(factory.asJSOFunction());
	// }
	//
	// /**
	// * Return the scale's interpolator factory.
	// *
	// * @return the factory
	// */
	// public final InterpolatorFactory<?> interpolate() {
	// return this.interpolate0();
	// }
	//
	// protected final native JSNIInterpolatorFactory<?> interpolate0()/*-{
	// return this.interpolate();
	// }-*/;
	//
	// protected final native S interpolate0(JavaScriptObject factory)/*-{
	// return this.interpolate(factory);
	// }-*/;

}
