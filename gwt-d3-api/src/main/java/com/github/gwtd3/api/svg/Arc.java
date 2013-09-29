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
package com.github.gwtd3.api.svg;

import com.github.gwtd3.api.arrays.Array;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * A generator to create an arc by defining the inner radius, the outer radius,
 * the start angle, and the end angle.
 * <p>
 * Four forms are possible:
 * <ul>
 * <li>a circle, when the inner radius is zero and the angular span is greater
 * than or equal to 2π
 * <li>a circular sector (when the inner radius is zero and the angular span is
 * less than 2π),
 * <li>an annulus (when the inner radius is non-zero and the angular span is
 * greater than or equal to 2π),
 * <li>an annular sector (when the inner radius is non-zero and the angular span
 * is less than 2π).
 * </ul>
 * <p>
 * Default innerRadius-, outerRadius-, startAngle- and endAngle-accessor
 * functions assume the input data is an object with named attributes matching
 * the accessors.
 * <p>
 * While the default accessors assume that the arc dimensions are all specified
 * dynamically, it is very common to set one or more of the dimensions as a
 * constant, such as setting the inner radius to zero for a pie chart.
 * <p>
 * The Arc function generates path data for a closed solid arc, as in a pie or
 * donut chart.
 * 
 * @see <a
 *      href="https://github.com/mbostock/d3/wiki/SVG-Shapes#wiki-arc">Official
 *      API</a>
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Arc extends PathDataGenerator {

	protected Arc() {
	}

	/**
	 * @return the inner radius of the arc.
	 */
	public final native double innerRadius()/*-{
		return $wnd.d3.functor(this.innerRadius)();
	}-*/;

	/**
	 * Set the inner radius of the Arc.
	 * 
	 * @param radius
	 * @return the current arc generator
	 */
	public final Arc innerRadius(final double innerRadius) {
		return setOrInvokeSetter("innerRadius", innerRadius);
	}

	/**
	 * @return the outerRadius of the arc.
	 */
	public final native double outerRadius()/*-{
		return $wnd.d3.functor(this.outerRadius)();
	}-*/;

	/**
	 * Set the outerRadius of the Arc.
	 * 
	 * @param radius
	 * @return the current arc generator
	 */
	public final Arc outerRadius(final double outerRadius) {
		return setOrInvokeSetter("outerRadius", outerRadius);
	}

	/**
	 * @return the startAngle of the arc.
	 */
	public final native double startAngle()/*-{
		return $wnd.d3.functor(this.startAngle)();
	}-*/;

	/**
	 * Set the start angle in radians of the Arc.
	 * <p>
	 * Angles are specified in radians, even though SVG typically uses degrees.
	 * The angle 0 corresponds to 12 o'clock (negative y) and continues
	 * clockwise repeating at 2xPI.
	 * 
	 * @param startAngle
	 *            the angle in radians
	 * @return the current arc generator
	 */
	public final Arc startAngle(final double startAngle) {
		return setOrInvokeSetter("startAngle", startAngle);
	}

	/**
	 * @return the endAngle of the arc.
	 */
	public final native double endAngle()/*-{
		return $wnd.d3.functor(this.endAngle)();
	}-*/;

	/**
	 * Set the end angle in radians of the Arc.
	 * <p>
	 * Angles are specified in radians, even though SVG typically uses degrees.
	 * The angle 0 corresponds to 12 o'clock (negative y) and continues
	 * clockwise repeating at 2xPI.
	 * 
	 * @param endAngle
	 *            the angle in radians
	 * @return the current arc generator
	 */
	public final Arc endAngle(final double endAngle) {
		return setOrInvokeSetter("endAngle", endAngle);
	}

	public final native Arc setOrInvokeSetter(String propName, double value)/*-{
		if (typeof this[propName] === 'function') {
			return this[propName](value);
		} else {
			this[propName] = value;
			return this;
		}
	}-*/;

	/**
	 * Computes the centroid of the arc that would be generated from the
	 * specified input arguments; typically, the arguments are the current datum
	 * (d), and optionally the current index (i).
	 * <p>
	 * The centroid is defined as the midpoint in polar coordinates of the inner
	 * and outer radius, and the start and end angle. This provides a convenient
	 * location for arc labels.
	 * <p>
	 * 
	 * @param datum
	 * @param index
	 * @return
	 */
	public final native Array<Double> centroid(JavaScriptObject datum, int index)/*-{
		return this.centroid(datum, index);
	}-*/;

	/**
	 * Create an instance of {@link Arc} with all properties initialized to 0.
	 * 
	 * Useful to define an arc with constants.
	 * 
	 * @return an arc object with defaults.
	 */
	public static native Arc constantArc() /*-{
		return {
			innerRadius : 0,
			outerRadius : 1,
			startAngle : 0,
			endAngle : 0
		};
	}-*/;

	/**
	 * Create a new arc with properties initialized with the given arc
	 * 
	 * @param arc
	 *            the arc to copy
	 * @return the new copy
	 */
	public static final native Arc copy(Arc oldArc)/*-{
		return {
			innerRadius : oldArc.innerRadius,
			outerRadius : oldArc.outerRadius,
			startAngle : oldArc.startAngle,
			endAngle : oldArc.endAngle
		};
	}-*/;

}
