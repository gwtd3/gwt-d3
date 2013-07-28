package com.github.gwtd3.api.scales;

/**
 * Quantize scales are a variant of linear scales with a discrete rather than
 * continuous range.
 * <p>
 * The input domain is still continuous, and divided into uniform segments based
 * on the number of values in (the cardinality of) the output range.
 * <p>
 * The mapping is linear in that the output range value y can be expressed as a
 * linear function of the input domain value x: <code>
 * <pre>
 * 		y = mx + b.
 * </pre>
 * </code>
 * <p>
 * The input domain is typically a dimension of the data that you want to
 * visualize, such as the height of students (measured in meters) in a sample
 * population.
 * <p>
 * The output range is typically a dimension of the desired output
 * visualization, such as the height of bars (measured in pixels) in a
 * histogram.
 * <p>
 * The default quantize scale has the default domain [0,1] and the default range
 * [0,1]. Thus, the default quantize scale is equivalent to the round function
 * for numbers; for example quantize(0.49) returns 0, and quantize(0.51) returns
 * 1.
 * <p>
 * If the domain is set with an array containing more than two numbers, only the
 * first and last number are used.
 * <p>
 * If the elements in the domain array are not numbers, they will be coerced to
 * numbers; this coercion happens similarly when the scale is called.
 * <p>
 * Thus, a quantize scale can be used to encode any type that can be converted
 * to numbers.
 * <p>
 * 
 */
public class QuantizeScale extends DiscreteQuantitativeScale<QuantizeScale> {

	protected QuantizeScale() {

	}

}
