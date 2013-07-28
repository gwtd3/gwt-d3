package com.github.gwtd3.api.scales;

import com.github.gwtd3.api.arrays.Array;

/**
 * {@link QuantitativeScale} with a discrete output range.
 * <p>
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 * @param <S>
 */
public abstract class DiscreteQuantitativeScale<S extends DiscreteQuantitativeScale<S>>
		extends QuantitativeScale<S> {

	protected DiscreteQuantitativeScale() {

	}

	/**
	 * Returns the extent of values in the input domain [x0, x1] for the
	 * corresponding value in the output range y, representing the inverse
	 * mapping from range to domain.
	 * <p>
	 * This method is useful for interaction, say to determine the value in the
	 * input domain that corresponds to the pixel location under the mouse.
	 * <p>
	 * 
	 * @param y
	 *            the output value to be converted to a domain range
	 * @return the array of doubles
	 */
	public final native Array<Double> invertExtent(double y)/*-{
		return this.invertExtent(y);
	}-*/;

}
