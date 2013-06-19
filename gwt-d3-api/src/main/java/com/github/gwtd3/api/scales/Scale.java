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

import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.core.Value;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayInteger;

public class Scale<S extends Scale<S>> extends JavaScriptObject {

    protected Scale() {};

    // ========= Domain functions ===============

    /*
     * * @return the scale current input domain
     */
    public native final <T> Array<T> domain()/*-{
		return this.domain();
    }-*/;

    public native final S domain(JavaScriptObject d) /*-{
		return this.domain(d);
    }-*/;

    public native final S domain(double a, double b) /*-{
		if (!this.domain) {
			return this;
		}
		return this.domain([ a, b ]);
    }-*/;

    public native final S domain(String a, String b) /*-{
		return this.domain([ a, b ]);
    }-*/;

    /**
     * Set the domain of this scale function with a domain that can be
     * understood by Javascript.
     * <p>
     * 
     * @param a
     * @param b
     * @return
     */
    public native final <T> S domain(T a, T b) /*-{
		return this.domain(a, b);
    }-*/;

    // ========= Range functions ===============

    /**
     * Returns the scale's current output range.
     * @return the range
     */
    public native final <T> Array<T> range()/*-{
		return this.range();
    }-*/;

    /**
     * Set the scale's output range.
     * <p>
     * Sets the scale's output range to the specified array of values. The array must contain two or more values, to
     * match the cardinality of the input domain. The elements in the given array need not be numbers; any value that is
     * supported by the underlying interpolator will work. However, numeric ranges are required for the invert operator.
     * 
     * @param values
     *            the array of values.
     * @return the current scale
     */
    public final native S range(JavaScriptObject values) /*-{
		return this.range(values);
    }-*/;

    /**
     * See {@link #range(JsArrayInteger)}.
     * 
     * @param a
     *            the first bound of the range
     * @param b
     *            the second bound of the range
     * @return the current scale
     */
    public final native S range(int a, int b) /*-{
		return this.range([ a, b ]);
    }-*/;

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
