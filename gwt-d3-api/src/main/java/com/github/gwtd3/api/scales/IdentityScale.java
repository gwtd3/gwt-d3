package com.github.gwtd3.api.scales;

import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.core.Formatter;

/**
 * Identity scales are a special case of linear scales where the domain and
 * range are identical; the {@link #apply(double)} and its
 * {@link #invert(double)} method are both the identity function.
 * <p>
 * These scales are occasionally useful when working with pixel coordinates, say
 * in conjunction with the axis and brush components.
 * <p>
 * The methods {@link #domain(double...)} and {@link #range(double...)} have the
 * same effect of setting both the domain and range in the same time.
 * <p>
 * 
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class IdentityScale extends ContinuousQuantitativeScale<IdentityScale> {

	// =========== ticks ==========

	/**
	 * Returns approximately count representative values from the scale's input
	 * domain (or equivalently, output range).
	 * <p>
	 * The returned tick values are uniformly spaced, have human-readable values
	 * (such as multiples of powers of 10), and are guaranteed to be within the
	 * extent of the input domain.
	 * <p>
	 * Ticks are often used to display reference lines, or tick marks, in
	 * conjunction with the visualized data.
	 * <p>
	 * The specified count is only a hint; the scale may return more or fewer
	 * values depending on the input domain.
	 * <p>
	 * 
	 * @return the array of ticks
	 */
	public native final <T> Array<T> ticks(int count)/*-{
		return this.ticks(count);
	}-*/;

	// =========== tickFormat ==========

	/**
	 * Returns a number format function suitable for displaying a tick value.
	 * <p>
	 * The specified count should have the same value as the count that is used
	 * to generate the tick values you want to display.
	 * <p>
	 * You don't have to use the scale's built-in tick format, but it
	 * automatically computes the appropriate precision based on the fixed
	 * interval between tick values.
	 * <p>
	 * 
	 * @param the
	 *            number of ticks to take into account to create the
	 *            {@link Formatter}.
	 * @return a number format
	 */
	public native final Formatter tickFormat(int count)/*-{
		return this.tickFormat(count);
	}-*/;

	/**
	 * Returns a number format function suitable for displaying a tick value.
	 * <p>
	 * This is the same as {@link #tickFormat(int)}, except that the format
	 * argument allows a format specifier to be specified.
	 * <p>
	 * If the format specifier doesn’t have a defined precision, the precision
	 * will be set automatically by the scale, returning the appropriate format.
	 * <p>
	 * This provides a convenient, declarative way of specifying a format whose
	 * precision will be automatically set by the scale.
	 * <p>
	 * 
	 * @param the
	 *            number of ticks to take into account to create the
	 *            {@link Formatter}.
	 * @param the
	 *            format specified, as documented in {@link Formatter}, to be
	 *            used as a basis of the Formatter.
	 * @return a number format
	 */
	public native final Formatter tickFormat(int count, String formatSpecifier)/*-{
		return this.tickFormat(count, formatSpecifier);
	}-*/;
}
