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

/**
 * {@link QuantitativeScale} with a discrete output range.
 * <p>
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 * @param <S>
 */
public abstract class DiscreteQuantitativeScale<S extends DiscreteQuantitativeScale<S>>
		extends QuantitativeScale<S> {

	protected DiscreteQuantitativeScale() {

	}

	/**
	 * Returns the extent of values in the input domain [x0, x1] for the
	 * corresponding value in the output range y, representing the inverse
	 * mapping from range to domain.
	 * <p>
	 * This method is useful for interaction, say to determine the value in the
	 * input domain that corresponds to the pixel location under the mouse.
	 * <p>
	 * 
	 * @param y
	 *            the output value to be converted to a domain range
	 * @return the array of doubles
	 */
	public final native Array<Double> invertExtent(double y)/*-{
		return this.invertExtent(y);
	}-*/;

	/**
	 * Returns the extent of values in the input domain [x0, x1] for the
	 * corresponding value in the output range y, representing the inverse
	 * mapping from range to domain.
	 * <p>
	 * This method is useful for interaction, say to determine the value in the
	 * input domain that corresponds to the pixel location under the mouse.
	 * <p>
	 * 
	 * @param y
	 *            the output value to be converted to a domain range
	 * @return the array of doubles
	 */
	public final native Array<Double> invertExtent(String y)/*-{
		return this.invertExtent(y);
	}-*/;

}
