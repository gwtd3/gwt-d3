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

import com.github.gwtd3.api.scales.IdentityScale;
import com.github.gwtd3.api.scales.LinearScale;
import com.github.gwtd3.api.scales.LogScale;
import com.github.gwtd3.api.scales.OrdinalScale;
import com.github.gwtd3.api.scales.PowScale;
import com.github.gwtd3.api.scales.QuantileScale;
import com.github.gwtd3.api.scales.QuantizeScale;
import com.github.gwtd3.api.scales.Scale;
import com.github.gwtd3.api.scales.ThresholdScale;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * Factory for {@link Scale}s.
 * <p>
 * Scales are functions that map from an input domain to an output range. There
 * is 3 kind of scales:
 * <ul>
 * <li>Quantitative Scales - for continuous input domains, such as numbers.
 * <li>Ordinal Scales - for discrete input domains, such as names or categories.
 * <li>Time Scales - for time domains. See {@link D3#time()}.
 * </ul>
 * <p>
 * <h2>Quantitative scales</h2>
 * Quantitative scales have a continuous domain, such as the set of real
 * numbers, or dates. There are also ordinal scales, which have a discrete
 * domain, such as a set of names or categories. Scales are an optional feature
 * in D3; you don't have to use them, if you prefer to do the math yourself.
 * However, using scales can greatly simplify the code needed to map a dimension
 * of data to a visual representation. A scale object, such as that returned by
 * d3.scale.linear, is both an object and a function. That is: you can call the
 * scale like any other function, and the scale has additional methods that
 * change its behavior. Like other classes in D3, scales follow the method
 * chaining pattern where setter methods return the scale itself, allowing
 * multiple setters to be invoked in a concise statement.
 * <p>
 * 
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Scales extends JavaScriptObject {
	protected Scales() {

	}

	/**
	 * Constructs a new linear scale with the default domain [0,1] and the
	 * default range [0,1]. Thus, the default linear scale is equivalent to the
	 * identity function for numbers; for example linear(0.5) returns 0.5.
	 * 
	 * @return a new default linear scale
	 */
	public final native LinearScale linear()/*-{
		return this.linear();
	}-*/;

	/**
	 * Constructs a new identity scale with the default domain [0,1] and the
	 * default range [0,1]. An identity scale is always equivalent to the
	 * identity function.
	 * 
	 * @return a new identity scale
	 */
	public final native IdentityScale identity()/*-{
		return this.identity();
	}-*/;

	/**
	 * Constructs a new log scale with the default domain [1,10], the default
	 * range [0,1], and the base 10.
	 * 
	 * @return a new default log scale
	 */
	public final native LogScale log()/*-{
		return this.log();
	}-*/;

	/**
	 * Constructs a new power scale with the default domain [0,1], the default
	 * range [0,1], and the default exponent 1.
	 * <p>
	 * Thus, the default power scale is equivalent to the identity function for
	 * numbers; for example pow(0.5) returns 0.5.
	 * 
	 * @return a new default pow scale
	 */
	public final native PowScale pow()/*-{
		return this.pow();
	}-*/;

	/**
	 * Constructs a new power scale with the default domain [0,1], the default
	 * range [0,1], and the exponent .5. This method is shorthand for:
	 * 
	 * <pre>
	 * {@code
	 * 	D3.scale.pow().exponent(.5);
	 * }
	 * </pre>
	 * 
	 * The returned scale is a function that takes a single argument x
	 * representing a value in the input domain; the return value is the
	 * corresponding value in the output range. Thus, the returned scale is
	 * equivalent to the {@link Math#sqrt(double)} function for numbers; for
	 * example sqrt(0.25) returns 0.5.
	 * <p>
	 * 
	 * @return a new default pow scale with an exponent of 0.5.
	 */
	public final native PowScale sqrt()/*-{
		return this.sqrt();
	}-*/;

	/**
	 * Constructs a new quantize scale with the default domain [0,1] and the
	 * default range [0,1].
	 * <p>
	 * Thus, the default quantize scale is equivalent to the round function for
	 * numbers; for example quantize(0.49) returns 0, and quantize(0.51) returns
	 * 1.
	 * <p>
	 * 
	 * @return the new scale
	 */
	public final native QuantizeScale quantize()/*-{
		return this.quantize();
	}-*/;

	/**
	 * Constructs a new quantile scale with an empty domain and an empty range.
	 * <p>
	 * The quantile scale is invalid until both a domain and range are
	 * specified.
	 * <p>
	 * 
	 * @return the new empty quantile scale
	 */
	public final native QuantileScale quantile()/*-{
		return this.quantile();
	}-*/;

	/**
	 * Constructs a new ordinal scale with an empty domain and an empty range.
	 * <p>
	 * The ordinal scale is invalid (always returning undefined) until an output
	 * range is specified.
	 * <p>
	 * 
	 * @return the new ordinal scale
	 */
	public final native OrdinalScale ordinal()/*-{
		return this.ordinal();
	}-*/;

	/**
	 * Constructs a new threshold scale with the default domain [.5] and the
	 * default range [0,1].
	 * <p>
	 * Thus, the default threshold scale is equivalent to the round function for
	 * numbers; for example threshold(0.49) returns 0, and threshold(0.51)
	 * returns 1.
	 * <p>
	 * 
	 * @return the new threshold scale
	 */
	public final native ThresholdScale threshold()/*-{
		return this.threshold();
	}-*/;

	/**
	 * <p>
	 * Constructs a new ordinal scale with a range of ten categorical colors:
	 * <b><font color="#1f77b4">#1f77b4</font> <font
	 * color="#ff7f0e">#ff7f0e</font> <font color="#2ca02c">#2ca02c</font> <font
	 * color="#d62728">#d62728</font> <font color="#9467bd">#9467bd</font> <font
	 * color="#8c564b">#8c564b</font> <font color="#e377c2">#e377c2</font> <font
	 * color="#7f7f7f">#7f7f7f</font> <font color="#bcbd22">#bcbd22</font> <font
	 * color="#17becf">#17becf</font></b>.
	 * </p>
	 * 
	 * @return the color palette as a {@link OrdinalScale}
	 */
	public final native OrdinalScale category10()/*-{
		return this.category10();
	}-*/;

	/**
	 * <p>
	 * Constructs a new ordinal scale with a range of twenty categorical colors:
	 * <b><font color="#1f77b4">#1f77b4</font> <font
	 * color="#aec7e8">#aec7e8</font> <font color="#ff7f0e">#ff7f0e</font> <font
	 * color="#ffbb78">#ffbb78</font> <font color="#2ca02c">#2ca02c</font> <font
	 * color="#98df8a">#98df8a</font> <font color="#d62728">#d62728</font> <font
	 * color="#ff9896">#ff9896</font> <font color="#9467bd">#9467bd</font> <font
	 * color="#c5b0d5">#c5b0d5</font> <font color="#8c564b">#8c564b</font> <font
	 * color="#c49c94">#c49c94</font> <font color="#e377c2">#e377c2</font> <font
	 * color="#f7b6d2">#f7b6d2</font> <font color="#7f7f7f">#7f7f7f</font> <font
	 * color="#c7c7c7">#c7c7c7</font> <font color="#bcbd22">#bcbd22</font> <font
	 * color="#dbdb8d">#dbdb8d</font> <font color="#17becf">#17becf</font> <font
	 * color="#9edae5">#9edae5</font></b>.
	 * </p>
	 * 
	 * @return the color palette as a {@link OrdinalScale}
	 */
	public final native OrdinalScale category20()/*-{
		return this.category20();
	}-*/;

	/**
	 * <p>
	 * Constructs a new ordinal scale with a range of twenty categorical colors:
	 * <b><font color="#393b79">#393b79</font> <font
	 * color="#5254a3">#5254a3</font> <font color="#6b6ecf">#6b6ecf</font> <font
	 * color="#9c9ede">#9c9ede</font> <font color="#637939">#637939</font> <font
	 * color="#8ca252">#8ca252</font> <font color="#b5cf6b">#b5cf6b</font> <font
	 * color="#cedb9c">#cedb9c</font> <font color="#8c6d31">#8c6d31</font> <font
	 * color="#bd9e39">#bd9e39</font> <font color="#e7ba52">#e7ba52</font> <font
	 * color="#e7cb94">#e7cb94</font> <font color="#843c39">#843c39</font> <font
	 * color="#ad494a">#ad494a</font> <font color="#d6616b">#d6616b</font> <font
	 * color="#e7969c">#e7969c</font> <font color="#7b4173">#7b4173</font> <font
	 * color="#a55194">#a55194</font> <font color="#ce6dbd">#ce6dbd</font> <font
	 * color="#de9ed6">#de9ed6</font></b>.
	 * </p>
	 * 
	 * @return the color palette as a {@link OrdinalScale}
	 */
	public final native OrdinalScale category20b()/*-{
		return this.category20b();
	}-*/;

	/**
	 * <p>
	 * Constructs a new ordinal scale with a range of twenty categorical colors:
	 * <b><font color="#3182bd">#3182bd</font> <font
	 * color="#6baed6">#6baed6</font> <font color="#9ecae1">#9ecae1</font> <font
	 * color="#c6dbef">#c6dbef</font> <font color="#e6550d">#e6550d</font> <font
	 * color="#fd8d3c">#fd8d3c</font> <font color="#fdae6b">#fdae6b</font> <font
	 * color="#fdd0a2">#fdd0a2</font> <font color="#31a354">#31a354</font> <font
	 * color="#74c476">#74c476</font> <font color="#a1d99b">#a1d99b</font> <font
	 * color="#c7e9c0">#c7e9c0</font> <font color="#756bb1">#756bb1</font> <font
	 * color="#9e9ac8">#9e9ac8</font> <font color="#bcbddc">#bcbddc</font> <font
	 * color="#dadaeb">#dadaeb</font> <font color="#636363">#636363</font> <font
	 * color="#969696">#969696</font> <font color="#bdbdbd">#bdbdbd</font> <font
	 * color="#d9d9d9">#d9d9d9</font></b>.
	 * </p>
	 * 
	 * @return the color palette as a {@link OrdinalScale}
	 */
	public final native OrdinalScale category20c()/*-{
		return this.category20c();
	}-*/;

	/**
	 * @return
	 */
	public static final native Scales get()/*-{
		return $wnd.d3.scale;
	}-*/;

}