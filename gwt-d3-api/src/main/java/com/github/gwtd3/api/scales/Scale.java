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

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.interpolators.Interpolator;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * <p>
 * Scales are functions that map from an input domain to an output range. There
 * are 2 kinds of scales:
 * <ul>
 * <li>Quantitative Scales - for continuous input domains, such as numbers.
 * <li>Ordinal Scales - for discrete input domains, such as names or categories.
 * </ul>
 * <p>
 * Scales are an optional feature in D3; you don't have to use them, if you
 * prefer to do the math yourself. However, using scales can greatly simplify
 * the code needed to map a dimension of data to a visual representation.
 * <p>
 * 
 * @param <S>
 *            the subclass of scale
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 */
public abstract class Scale<S extends Scale<S>> extends JavaScriptObject {

	protected Scale() {
	};

	// ==================== domain ====================

	/**
	 * Sets the scale's input domain to the specified array of numbers.
	 * <p>
	 * 
	 * @param numbers
	 *            the array of numbers
	 * @return the current scale
	 */
	public final S domain(double... numbers) {
		return domain(Array.fromDoubles(numbers));
	}

	/**
	 * Sets the scale's input domain to the specified array of strings.
	 * <p>
	 * 
	 * @param strings
	 *            the array of strings
	 * @return the current scale
	 */
	public final S domain(String... strings) {
		return domain(Array.fromObjects(strings));
	}

	/**
	 * Sets the scale's input domain to the specified array.
	 * <p>
	 * 
	 * @param strings
	 *            the array
	 * @return the current scale
	 */
	public final native S domain(JavaScriptObject array)/*-{
		return this.domain(array);
	}-*/;

	/**
	 * Returns the current scale's input domain.
	 * <p>
	 * 
	 * @return the current domain
	 */
	public final native <T> Array<T> domain()/*-{
		return this.domain();
	}-*/;

	// ==================== range ====================

	/**
	 * Set the scale's output range.
	 * <p>
	 * Scale subclasses may interpret the output range differently. Please refer
	 * to subclass documentation.
	 * 
	 * @param numbers
	 *            the array of values.
	 * @return the current scale for chaining
	 */
	public final S range(double... numbers) {
		return this.range(Array.fromDoubles(numbers));
	}

	/**
	 * Set the scale's output range.
	 * <p>
	 * Scale subclasses may interpret the output range differently. Please refer
	 * to subclass documentation.
	 * 
	 * @param strings
	 *            the array of values.
	 * @return the current scale for chaining
	 */
	public final S range(String... strings) {
		return this.range(Array.fromObjects(strings));
	}

	/**
	 * Set the scale's output range.
	 * <p>
	 * Scale subclasses may interpret the output range differently. Please refer
	 * to subclass documentation.
	 * 
	 * @param array
	 *            the array of values.
	 * @return the current scale for chaining
	 */
	public final native S range(JavaScriptObject array)/*-{
		return this.range(array);
	}-*/;

	/**
	 * Return the current output range of this scale.
	 * <p>
	 * 
	 * @return the current output range
	 */
	public final native <T> Array<T> range()/*-{
		return this.range();
	}-*/;

	// ==================== copy ====================

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

	// ==================== apply ====================
	/**
	 * Given a value x in the input domain, returns the corresponding value in
	 * the output range.
	 * <p>
	 * Note: some {@link Interpolator}s reuse return values. For example, if the
	 * domain values are arbitrary objects, then
	 * {@link D3#interpolateObject(JavaScriptObject, JavaScriptObject)} is
	 * automatically applied and the scale reuses the returned object.
	 * <p>
	 * Often, the return value of a scale is immediately used to set an
	 * attribute or style, and you don’t have to worry about this; however, if
	 * you need to store the scale’s return value, use string coercion or create
	 * a copy as appropriate.
	 * <p>
	 * 
	 * @param d
	 *            the input value
	 * @return the output value
	 */
	public final native Value apply(JavaScriptObject d)/*-{
		return {
			datum : this(d)
		};
	}-*/;

	/**
	 * Given a value x in the input domain, returns the corresponding value in
	 * the output range.
	 * <p>
	 * Note: some {@link Interpolator}s reuse return values. For example, if the
	 * domain values are arbitrary objects, then
	 * {@link D3#interpolateObject(JavaScriptObject, JavaScriptObject)} is
	 * automatically applied and the scale reuses the returned object.
	 * <p>
	 * Often, the return value of a scale is immediately used to set an
	 * attribute or style, and you don’t have to worry about this; however, if
	 * you need to store the scale’s return value, use string coercion or create
	 * a copy as appropriate.
	 * <p>
	 * 
	 * @param d
	 *            the input value
	 * @return the output value
	 */
	public final native Value apply(double d)/*-{
		return {
			datum : this(d)
		};
	}-*/;

	/**
	 * Given a value x in the input domain, returns the corresponding value in
	 * the output range.
	 * <p>
	 * Note: some {@link Interpolator}s reuse return values. For example, if the
	 * domain values are arbitrary objects, then
	 * {@link D3#interpolateObject(JavaScriptObject, JavaScriptObject)} is
	 * automatically applied and the scale reuses the returned object.
	 * <p>
	 * Often, the return value of a scale is immediately used to set an
	 * attribute or style, and you don’t have to worry about this; however, if
	 * you need to store the scale’s return value, use string coercion or create
	 * a copy as appropriate.
	 * <p>
	 * 
	 * @param d
	 *            the input value
	 * @return the output value
	 */
	public final native Value apply(String d)/*-{
		return {
			datum : this(d)
		};
	}-*/;
}
