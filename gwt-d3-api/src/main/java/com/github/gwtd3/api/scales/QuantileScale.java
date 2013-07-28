package com.github.gwtd3.api.scales;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;

/**
 * Quantile scales map an input domain to a discrete range.
 * <p>
 * Although the input domain is continuous and the scale will accept any
 * reasonable input value, the input domain is specified as a discrete set of
 * values.
 * <p>
 * The number of values in (the cardinality of) the output range determines the
 * number of quantiles that will be computed from the input domain. To compute
 * the quantiles, the input domain is sorted, and treated as a population of
 * discrete values. The input domain is typically a dimension of the data that
 * you want to visualize, such as the daily change of the stock market. The
 * output range is typically a dimension of the desired output visualization,
 * such as a diverging color scale.
 * <p>
 * A new quantile scale is invalid until both a domain and range are specified.
 * <p>
 * The input <strong>domain</strong> of the quantile scale is a set of
 * <strong>discrete numeric values</strong>.
 * <p>
 * The input domain array must not be empty, and must contain at least one
 * numeric value; NaN, null and undefined values are ignored and not considered
 * part of the sample population.
 * <p>
 * If the elements in the given array are not numbers, they will be coerced to
 * numbers; this coercion happens similarly when the scale is called.
 * <p>
 * A copy of the input array is sorted and stored internally. Thus, a quantile
 * scale can be used to encode any type that can be converted to numbers.
 * <p>
 * The output range array must not be empty, and may contain any type of value.
 * The number of values in (the cardinality, or length, of) the values array
 * determines the number of quantiles that are computed. For example, to compute
 * quartiles, values must be an array of four elements such as [0, 1, 2, 3].
 * <p>
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class QuantileScale extends DiscreteQuantitativeScale<QuantileScale> {

	protected QuantileScale() {

	}

	/**
	 * Returns the quantile thresholds.
	 * <p>
	 * If the output range contains n discrete values, the returned threshold
	 * array will contain n - 1 values. Values less than the first element in
	 * the thresholds array, quantiles()[0], are considered in the first
	 * quantile; greater values less than the second threshold are in the second
	 * quantile, and so on.
	 * <p>
	 * Internally, the thresholds array is used with {@link D3#bisect} to find
	 * the output quantile associated with the given input value.
	 * 
	 * @return the array of threshold values
	 */
	public native final <T> Array<T> quantiles()/*-{
		return this.quantiles();
	}-*/;
}
