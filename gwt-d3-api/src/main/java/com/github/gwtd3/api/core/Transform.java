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
