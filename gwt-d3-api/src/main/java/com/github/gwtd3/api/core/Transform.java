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

import com.github.gwtd3.api.arrays.Array;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * A Transform is a representation of a SVG <a
 * href="http://www.w3.org/TR/SVG/coords.html#TransformAttribute">transform
 * attribute</a>.
 * <p>
 * It allows parsing the svg transform attribute with {@link #parse(String)},
 * manipulation using setters, such as {@link #rotate(double)},
 * then generation of the transform attribute with {@link #toString()}
 * <p>
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Transform extends JavaScriptObject {

	protected Transform() {
	}

	/**
	 * Parses the given 2D affine transform string, as defined by SVG's <a
	 * href="http://www.w3.org/TR/SVG/coords.html#TransformAttribute">transform
	 * attribute</a>.
	 * <p>
	 * The transform is then decomposed to a standard representation of
	 * translate, rotate, x-skew and scale.
	 * <p>
	 * This behavior is standardized by CSS: see <a
	 * href="http://www.w3.org/TR/css3-2d-transforms/#matrix-decomposing">matrix
	 * decomposition for animation</a>.
	 * <p>
	 * 
	 * @param transformString
	 *            the transform string as in <a href=
	 *            "http://www.w3.org/TR/SVG/coords.html#TransformAttribute"
	 *            >SVG</a>
	 * @return the Transform object
	 */
	public static final native Transform parse(String transformString)/*-{
		return $wnd.d3.transform(transformString);
	}-*/;

	/**
	 * Returns the rotation angle θ of this transform, in degrees.
	 * 
	 * @return the rotation angle
	 */
	public native final double rotate()/*-{
		return this.rotate;
	}-*/;

	/**
	 * Set the rotation angle of this transform
	 * 
	 * @param degrees
	 *            the rotate degree
	 * @return the modified transformation
	 */
	public native final Transform rotate(double degrees)/*-{
		this.rotate = degrees;
		return this;
	}-*/;

	/**
	 * Returns the [dx, dy] translation of this transform, as a two-element
	 * array in local coordinates (typically pixels).
	 * <p>
	 * 
	 * @return translation coords
	 */
	public native final Array<Double> translate()/*-{
		return this.translate;
	}-*/;

	/**
	 * Create a translation by x and y
	 * 
	 * @param x
	 * @param y
	 * @return the {@link Transform}.
	 */
	public native final Transform translate(final int x, final int y)/*-{
		this.translate = [ x, y ];
		return this;
	}-*/;

	/**
	 * Create a translation by x and 0.
	 * 
	 * @param x
	 *            the x
	 * @return the {@link Transform}
	 */
	public native final Transform translate(final int x)/*-{
		this.translate = [ x, 0 ];
		return this;
	}-*/;

	/**
	 * Create a scale operation by x and y
	 * 
	 * @param x
	 * @param y
	 * @return the {@link Transform}.
	 */
	public native final Transform scale(final int x, final int y)/*-{
		return this.scale = [ x, y ];
	}-*/;

	/**
	 * Create a scale operation by x and by y = x.
	 * 
	 * @param x
	 *            the x
	 * @return the {@link Transform}
	 */
	public native final Transform scale(final int x)/*-{
		this.scale = [ x, 0 ];
		return this;
	}-*/;

	/**
	 * Returns the x-skew φ of this transform, in degrees.
	 * <p>
	 * 
	 * @return the x skew
	 */
	public native final double skew()/*-{
		return this.skew;
	}-*/;

	public native final Transform skew(double x)/*-{
		this.skew = x;
		return this;
	}-*/;

	/**
	 * Returns the [kx, ky] scale of this transform, as a two-element array.
	 * <p>
	 * 
	 * @return translation coords
	 */
	public native final Array<Double> scale()/*-{
		return this.scale;
	}-*/;

}
