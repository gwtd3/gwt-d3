package com.github.gwtd3.api.svg;

/**
 * Factory class for lines.
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Lines {
	/**
	 * Constructs a new radial line generator with the default radius- and
	 * angle-accessor functions (that assume the input data is a two-element
	 * array of numbers; see below for details), and linear interpolation.
	 * <p>
	 * The returned function generates path data for an open piecewise linear
	 * curve, or polyline, as with the Cartesian line generator.
	 * <p>
	 * 
	 * @return the {@link RadialLine}.
	 */
	public final native RadialLine radial()/*-{
		return this.radial();
	}-*/;

	public static final native Lines get()/*-{
		return D3.svg.line;
	}-*/;
}