/**
 * 
 */
package com.github.gwtd3.api.scales;

import com.github.gwtd3.api.core.Value;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class QuantitativeScale<S extends QuantitativeScale<S>> extends Scale<S> {

	protected QuantitativeScale() {

	}

	/**
	 * Set the domain of the scale with the given values. Range must be set with
	 * the same amount of values.
	 * 
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	public native final S domain(double a, double b, double c) /*-{
		return this.domain([ a, b, c ]);
	}-*/;

	/**
	 * Returns the value in the input domain x for the corresponding value in
	 * the output range y.
	 * <p>
	 * This represents the inverse mapping from range to domain. For a valid
	 * value y in the output range, linear(linear.invert(*y*)) equals y;
	 * similarly, for a valid value x in the input domain,
	 * linear.invert(linear(*x*)) equals x.
	 * <p>
	 * Equivalently, you can construct the invert operator by building a new
	 * scale while swapping the domain and range. The invert operator is
	 * particularly useful for interaction, say to determine the value in the
	 * input domain that corresponds to the pixel location under the mouse.
	 * 
	 * Note: the invert operator is only supported if the output range is
	 * numeric! D3 allows the output range to be any type; under the hood,
	 * d3.interpolate or a custom interpolator of your choice is used to map the
	 * normalized parameter t to a value in the output range. Thus, the output
	 * range may be colors, strings, or even arbitrary objects. As there is no
	 * facility to "uninterpolate" arbitrary types, the invert operator is
	 * currently supported only on numeric ranges.
	 * 
	 * @return
	 */
	public native final Value invert(double d)/*-{
		return {
			datum : this.invert(d)
		};
	}-*/;
}
