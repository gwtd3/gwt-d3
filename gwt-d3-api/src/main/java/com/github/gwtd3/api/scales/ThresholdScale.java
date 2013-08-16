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
 * Threshold scales are similar to quantize scales, except they allow you to map
 * arbitrary subsets of the domain to discrete values in the range.
 * <p>
 * The input domain is still continuous, and divided into slices based on a set
 * of threshold values.
 * <p>
 * The input domain is typically a dimension of the data that you want to
 * visualize, such as the height of students (measured in meters) in a sample
 * population.
 * <p>
 * The output range is typically a dimension of the desired output
 * visualization, such as a set of colors (represented as strings).
 * <p>
 * A new threshold scale has the default domain [.5] and the default range
 * [0,1]. Thus, the default thresold scale is equivalent to the round function
 * for numbers; for example threshold(0.49) returns 0, and threshold(0.51)
 * returns 1.
 * <p>
 * The values in the domain array must be in sorted ascending order, or the
 * behavior of the scale is undefined. The values are typically numbers, but any
 * naturally ordered values (such as strings) will work. Thus, a threshold scale
 * can be used to encode any type that is ordered. If the number of values in
 * the scale's range is N + 1, the number of values in the scale's domain must
 * be N. If there are fewer than N elements in the domain, the additional values
 * in the range are ignored. If there are more than N elements in the domain,
 * the scale may return undefined for some inputs.
 * <p>
 * The elements in the output range array need not be numbers; any value or type
 * will work.
 * <p>
 * 
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class ThresholdScale extends DiscreteQuantitativeScale<ThresholdScale> {

	protected ThresholdScale() {
	}

}
