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

/**
 * Quantize scales are a variant of linear scales with a discrete rather than
 * continuous range.
 * <p>
 * The input domain is still continuous, and divided into uniform segments based
 * on the number of values in (the cardinality of) the output range.
 * <p>
 * The mapping is linear in that the output range value y can be expressed as a
 * linear function of the input domain value x: <code>
 * <pre>
 * 		y = mx + b.
 * </pre>
 * </code>
 * <p>
 * The input domain is typically a dimension of the data that you want to
 * visualize, such as the height of students (measured in meters) in a sample
 * population.
 * <p>
 * The output range is typically a dimension of the desired output
 * visualization, such as the height of bars (measured in pixels) in a
 * histogram.
 * <p>
 * The default quantize scale has the default domain [0,1] and the default range
 * [0,1]. Thus, the default quantize scale is equivalent to the round function
 * for numbers; for example quantize(0.49) returns 0, and quantize(0.51) returns
 * 1.
 * <p>
 * If the domain is set with an array containing more than two numbers, only the
 * first and last number are used.
 * <p>
 * If the elements in the domain array are not numbers, they will be coerced to
 * numbers; this coercion happens similarly when the scale is called.
 * <p>
 * Thus, a quantize scale can be used to encode any type that can be converted
 * to numbers.
 * <p>
 * 
 */
public class QuantizeScale extends DiscreteQuantitativeScale<QuantizeScale> {

	protected QuantizeScale() {

	}

}
