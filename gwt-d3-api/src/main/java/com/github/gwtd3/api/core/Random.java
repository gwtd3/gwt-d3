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
package com.github.gwtd3.api.core;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * A pseudorandom number generation function.
 * <p>
 * Random instances are created using static factory methods of this class, such
 * as {@link #normal()}.
 * <p>
 * The {@link #generate()} method is then used to generate numbers.
 * <p>
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Random extends JavaScriptObject {

	protected Random() {
	}

	/**
	 * Returns a function for generating random number with a <a
	 * href="http://en.wikipedia.org/wiki/Irwin%E2%80%93Hall_distribution"
	 * >Irwin-Hall</a> distribution.
	 * <p>
	 * 
	 * @param count
	 *            the number of independant variables
	 * @return the generator
	 */
	public static final native Random irwinHall(int count)/*-{
		return $wnd.d3.random.irwinHall(count);
	}-*/;

	/**
	 * Returns a function for generating random number with a <a
	 * href="http://en.wikipedia.org/wiki/Normal_distribution">normal (Gaussian)
	 * distribution</a>, with a mean of 0 and a deviation of 1.
	 * <p>
	 * 
	 * @return the generator
	 */
	public static final native Random normal()/*-{
		return $wnd.d3.random.normal();
	}-*/;

	/**
	 * Returns a function for generating random number with a <a
	 * href="http://en.wikipedia.org/wiki/Normal_distribution">normal (Gaussian)
	 * distribution</a>, with the given mean, and a deviation of 1.
	 * <p>
	 * The expected value of the generated pseudorandom numbers is mean, with
	 * the standard deviation of 1.
	 * 
	 * @param mean
	 *            the mean
	 * @return the generator
	 */
	public static final native Random normal(double mean)/*-{
		return $wnd.d3.random.normal(mean);
	}-*/;

	/**
	 * Returns a function for generating random number with a <a
	 * href="http://en.wikipedia.org/wiki/Normal_distribution">normal (Gaussian)
	 * distribution</a>, with the given mean, and the given deviation.
	 * <p>
	 * 
	 * @param mean
	 *            the mean
	 * @param deviation
	 *            the deviation
	 * @return the generator
	 */
	public static final native Random normal(double mean, double deviation)/*-{
		return $wnd.d3.random.normal(mean, deviation);
	}-*/;

	/**
	 * Returns a function for generating random number with a <a
	 * href="http://en.wikipedia.org/wiki/Log-normal_distribution">log-normal
	 * distribution</a>, with a mean of 0 and a deviation of 1.
	 * <p>
	 * 
	 * @return the generator
	 */
	public static final native Random logNormal()/*-{
		return $wnd.d3.random.logNormal();
	}-*/;

	/**
	 * Returns a function for generating random number with a <a
	 * href="http://en.wikipedia.org/wiki/Log-normal_distribution">log-normal
	 * distribution</a>, with the given mean, and a deviation of 1.
	 * <p>
	 * The expected value of the generated pseudorandom numbers is mean, with
	 * the standard deviation of 1.
	 * 
	 * @param mean
	 *            the mean
	 * @return the generator
	 */
	public static final native Random logNormal(double mean)/*-{
		return $wnd.d3.random.logNormal(mean);
	}-*/;

	/**
	 * Returns a function for generating random number with a <a
	 * href="http://en.wikipedia.org/wiki/Log-normal_distribution">log-normal
	 * distribution</a>, with the given mean, and the given deviation.
	 * <p>
	 * 
	 * @param mean
	 *            the mean
	 * @param deviation
	 *            the deviation
	 * @return the generator
	 */
	public static final native Random logNormal(double mean, double deviation)/*-{
		return $wnd.d3.random.logNormal(mean, deviation);
	}-*/;

	/**
	 * Generate a new number with this distribution.
	 * 
	 * @return the new generated number.
	 */
	public native final double generate()/*-{
		return this();
	}-*/;

}
