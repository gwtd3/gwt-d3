/**
 * 
 */
package com.github.gwtd3.api.scales;


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

}
