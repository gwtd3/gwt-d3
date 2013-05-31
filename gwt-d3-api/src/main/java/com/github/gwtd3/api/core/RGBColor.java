/**
 * 
 */
package com.github.gwtd3.api.core;

/**
 * A color defined by red, green and blue components.
 * 
 * @see <a href="https://github.com/mbostock/d3/wiki/Colors">official API</a>
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class RGBColor extends Color {

	protected RGBColor() {
		super();
	}

	/**
	 * @return the red component in the range [0;255]
	 */
	public native final int r()/*-{
		return this.r;
	}-*/;

	/**
	 * @return the green component in the range [0;255]
	 */
	public native final int g()/*-{
		return this.g;
	}-*/;

	/**
	 * @return the blue component in the range [0;255]
	 */
	public native final int b()/*-{
		return this.b;
	}-*/;

	/**
	 * Returns the equivalent color in HSL space; see d3.hsl for details on the returned object. The conversion from HSL to RGB is described in CSS3 Color Module Level 3; this is
	 * the equivalent reverse operation.
	 * 
	 * @return
	 */
	public native final HSLColor hsl()/*-{
		return this.hsl();
	}-*/;

	/**
	 * Returns a brighter copy of this color yith a gamme of 1.
	 * 
	 * @return the new copy
	 */
	public native final RGBColor brighter()/*-{
		return this.brighter();
	}-*/;

	/**
	 * Returns a brighter copy of this color.
	 * <p>
	 * Each channel is multiplied by 0.7 ^ -k.
	 * 
	 * @param k
	 *            the gamma value
	 * @return the new copy
	 */
	public native final RGBColor brighter(int k)/*-{
		return this.brighter(k);
	}-*/;

	/**
	 * Returns a darker copy of this color yith a gamma of 1.
	 * 
	 * @return the new copy
	 */
	public native final RGBColor darker()/*-{
		return this.darker();
	}-*/;

	/**
	 * Returns a darker copy of this color.
	 * <p>
	 * Each channel is multiplied by 0.7 ^ k.
	 * 
	 * @param k
	 *            the gamma value
	 * @return the new copy
	 */
	public native final RGBColor darker(int k)/*-{
		return this.darker(k);
	}-*/;

}
