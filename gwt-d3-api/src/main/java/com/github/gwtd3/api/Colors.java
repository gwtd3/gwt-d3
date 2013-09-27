package com.github.gwtd3.api;

import com.github.gwtd3.api.core.Color;
import com.github.gwtd3.api.core.HSLColor;
import com.github.gwtd3.api.core.RGBColor;

/**
 * Provides factory methods to create colors.
 * <p>
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Colors {

	/**
	 * Constructs a new RGB color with the specified r, g and b channel values.
	 * Each channel must be specified as an integer in the range [0,255]. The
	 * channels are available as the r, g and b attributes of the returned
	 * object.
	 * 
	 * @param r
	 *            the red channel
	 * @param g
	 *            the green channel
	 * @param b
	 *            the blue channel
	 * @return the new color instance
	 */
	public static final native RGBColor rgb(int r, int g, int b)/*-{
		return $wnd.d3.rgb(r, g, b);
	}-*/;

	/**
	 * Constructs a new RGB color by parsing the specified color string. The
	 * color string may be in a variety of formats:
	 * <ul>
	 * <li>rgb decimal - "rgb(255,255,255)"
	 * <li>hsl decimal - "hsl(120,50%,20%)"
	 * <li>rgb hexadecimal - "#ffeeaa"
	 * <li>rgb shorthand hexadecimal - "#fea"
	 * <li>named - "red", "white", "blue"
	 * </ul>
	 * 
	 * @param color
	 *            the color string representation
	 * @return the new color
	 */
	public static final native RGBColor rgb(final String color)/*-{
		return $wnd.d3.rgb(color);
	}-*/;

	/**
	 * Construct a new RGB color from the existing color object.
	 * <p>
	 * 
	 * @param color
	 *            the existing color object
	 * @return the new color
	 */
	public static final native RGBColor rgb(final Color color)/*-{
		return $wnd.d3.rgb(color);
	}-*/;

	/**
	 * Constructs a new HSL color with the specified hue h, saturation s and
	 * lightness l. The hue must be a number in the range [0,360]. The
	 * saturation and lightness must be in the range 0,1. These values are
	 * available as the h, s and l attributes of the returned object.
	 * 
	 * @param h
	 *            the hue channel [0;360]
	 * @param s
	 *            the saturation channel [0;1]
	 * @param l
	 *            the light channel [0;1]
	 * @return the new color instance
	 */
	public static final native HSLColor hsl(int h, double s, double l)/*-{
		return $wnd.d3.hsl(h, s, l);
	}-*/;

	/**
	 * Constructs a new HSL color by parsing the specified color string. The
	 * color string may be in a variety of formats:
	 * <ul>
	 * <li>rgb decimal - "rgb(255,255,255)"
	 * <li>hsl decimal - "hsl(120,50%,20%)"
	 * <li>rgb hexadecimal - "#ffeeaa"
	 * <li>rgb shorthand hexadecimal - "#fea"
	 * <li>named - "red", "white", "blue"
	 * </ul>
	 * 
	 * @param color
	 *            the color string representation
	 * @return the new color
	 */
	public static final native HSLColor hsl(final String color)/*-{
		return $wnd.d3.hsl(color);
	}-*/;

	/**
	 * Constructs a new HSL color from an existing color object.
	 * <p>
	 * 
	 * @param color
	 *            the existing color object
	 * @return the new color
	 */
	public static final native HSLColor hsl(final RGBColor color)/*-{
		return $wnd.d3.hsl(color);
	}-*/;

}
