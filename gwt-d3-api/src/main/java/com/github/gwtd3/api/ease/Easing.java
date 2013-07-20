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
package com.github.gwtd3.api.ease;


/**
 * Creates an <a href="http://easings.net/">easing function</a>.
 * <p>
 * The following easing types are supported:
 * <ul>
 * <li>linear - the identity function, t.
 * <li>poly(k) - raises t to the specified power k (e.g., 3).
 * <li>quad - equivalent to poly(2).
 * <li>cubic - equivalent to poly(3).
 * <li>sin - applies the trigonometric function sin.
 * <li>exp - raises 2 to a power based on t.
 * <li>circle - the quarter circle.
 * <li>elastic(a, p) - simulates an elastic band; may extend slightly beyond 0
 * and 1.
 * <li>back(s) - simulates backing into a parking space.
 * <li>bounce - simulates a bouncy collision.
 * </ul>
 * <p>
 * These built-in types may be extended using a variety of {@link Mode}.
 * <p>
 * The default easing function is "cubic-in-out" which provides suitable <a href="http://en.wikipedia.org/wiki/12_basic_principles_of_animation#Slow_in_and_slow_out">slow-in
 * slow-out</a> animation.
 * <p>
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 */
public class Easing {

	/**
	 * The identity function
	 * @return the function
	 */
	public static final EasingFunction linear() {
		return linear(Mode.IN);
	}

	/**
	 * The identity function
	 * 
	 * @param mode
	 *            the mode
	 * @return the function
	 */
	public static final EasingFunction linear(final Mode mode) {
		return ease("linear-" + mode.getValue());
	}

	/**
	 * Raises t to the specified power k (e.g., 3).
	 * 
	 * @param k
	 *            the power for raising t
	 * @return the function
	 */
	public static final EasingFunction poly(final int k) {
		return poly(Mode.IN, k);
	}

	/**
	 * Raises t to the specified power k (e.g., 3).
	 * 
	 * @param mode
	 *            the mode
	 * @param k
	 *            the power
	 * @return the function
	 */
	public static final EasingFunction poly(final Mode mode, final int k) {
		return ease("poly-" + mode.getValue(), k);
	}

	/**
	 * Equivalent to poly(2).
	 * 
	 * @return the function
	 */
	public static final EasingFunction quad() {
		return quad(Mode.IN);
	}

	/**
	 * Equivalent to poly(2).
	 * 
	 * @param mode
	 *            the mode
	 * @return the function
	 */
	public static final EasingFunction quad(final Mode mode) {
		return ease("quad-" + mode.getValue());
	}

	/**
	 * Equivalent to poly(3).
	 * 
	 * @return the function
	 */
	public static final EasingFunction cubic() {
		return cubic(Mode.IN);
	}

	/**
	 * Equivalent to poly(3).
	 * 
	 * @param mode
	 *            the mode
	 * @return the function
	 */
	public static final EasingFunction cubic(final Mode mode) {
		return ease("cubic-" + mode.getValue());
	}

	/**
	 * Applies the trigonometric function sin.
	 * <p>
	 * 
	 * @return the function
	 */
	public static final EasingFunction sin() {
		return sin(Mode.IN);
	}

	/**
	 * Applies the trigonometric function sin.
	 * <p>
	 * 
	 * @param mode
	 *            the mode
	 * @return the function
	 */
	public static final EasingFunction sin(final Mode mode) {
		return ease("sin-" + mode.getValue());
	}

	/**
	 * Raises 2 to a power based on t.
	 * <p>
	 * 
	 * @return the function
	 */
	public static final EasingFunction exp() {
		return exp(Mode.IN);
	}

	/**
	 * Raises 2 to a power based on t.
	 * <p>
	 * 
	 * @param mode
	 *            the mode
	 * @return the function
	 */
	public static final EasingFunction exp(final Mode mode) {
		return ease("exp-" + mode.getValue());
	}

	/**
	 * The quarter circle.
	 * <p>
	 * 
	 * @return the function
	 */
	public static final EasingFunction circle() {
		return circle(Mode.IN);
	}

	/**
	 * The quarter circle
	 * <p>
	 * 
	 * @param mode
	 *            the mode
	 * @return the function
	 */
	public static final EasingFunction circle(final Mode mode) {
		return ease("circle-" + mode.getValue());
	}

	/**
	 * Simulates an elastic band; may extend slightly beyond 0 and 1.
	 * <p>
	 * @param a the amplitude
	 * @param p the period
	 * @return the function
	 */
	public static final EasingFunction elastic(final double a, final double p) {
		return elastic(Mode.IN, a, p);
	}

	/**
	 * Simulates an elastic band; may extend slightly beyond 0 and 1.
	 * <p>
	 * @param mode
	 *            the mode
	 * @param a the amplitude
	 * @param p the period
	 * @return the function
	 */
	public static final EasingFunction elastic(final Mode mode, final double a, final double p) {
		return ease("elastic-" + mode.getValue(), a, p);
	}

	/**
	 * Simulates backing into a parking space.
	 * 
	 * @param s the overshoot
	 * @return the function
	 */
	public static final EasingFunction back(final double s) {
		return back(Mode.IN, s);
	}

	/**
	 * Simulates backing into a parking space.
	 * 
	 * @param mode
	 *            the mode
	 * @param s the overshoot
	 * @return the function
	 */
	public static final EasingFunction back(final Mode mode, final double s) {
		return ease("back-" + mode.getValue(), s);
	}

	/**
	 * Simulates a bouncy collision.
	 * 
	 * @return the function
	 */
	public static final EasingFunction bounce() {
		return bounce(Mode.IN);
	}

	/**
	 * Simulates a bouncy collision.
	 * 
	 * @param mode
	 *            the mode
	 * @return the function
	 */
	public static final EasingFunction bounce(final Mode mode) {
		return ease("bounce-" + mode.getValue());
	}

	private static final native JavascriptEasingFunction ease(String type)/*-{
		return $wnd.d3.ease(type);
	}-*/;

	private static final native JavascriptEasingFunction ease(String type, double a)/*-{
		return $wnd.d3.ease(type, a);
	}-*/;

	private static final native JavascriptEasingFunction ease(String type, double a, double b)/*-{
		return $wnd.d3.ease(type, a, b);
	}-*/;
}
