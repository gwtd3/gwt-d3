package com.github.gwtd3.api.scales;

/**
 * Linear scales are the most common scale, and a good default choice to map a continuous input domain to a continuous output range.
 * <p>
 * The mapping is linear in that the output range value y can be expressed as a linear function of the input domain value x: y = mx + b.
 * <p>
 * The input domain is typically a dimension of the data that you want to visualize, such as the height of students (measured in meters) in a sample population.
 * <p>
 * The output range is typically a dimension of the desired output visualization, such as the height of bars (measured in pixels) in a histogram.
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class LinearScale extends ContinuousQuantitativeScale<LinearScale> {

	protected LinearScale() {
	}

}
