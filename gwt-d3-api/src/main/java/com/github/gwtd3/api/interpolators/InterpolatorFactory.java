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
package com.github.gwtd3.api.interpolators;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Represents an object that can create an {@link Interpolator}.
 * <p>
 * A factory should return null when no interpolators support the input arguments.
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public interface InterpolatorFactory<O> {

	/**
	 * Create an interpolator appropriate for interpolating the input value, or return null if the factory does not support the two
	 * specified input values.
	 * <p>
	 * 
	 * @param a
	 *            the start input param
	 * @param b
	 *            the end input param
	 * 
	 * @return an appropriate interpolator or null if the factory does not support the input values.
	 */
	<I> Interpolator<O> create(I a, I b);

	/**
	 * Return a one-arg JS function wrapping the interpolation.
	 * <p>
	 * Consider using {@link CallableInterpolator} for your own interpolation.
	 * <p>
	 * 
	 * @return the JS function
	 */
	public JavaScriptObject asJSOFunction();
}
