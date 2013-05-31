package com.github.gwtd3.api.scales;

import com.github.gwtd3.api.core.Value;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayInteger;

public class Scale<S extends Scale<S>> extends JavaScriptObject {

	protected Scale() {
	};

	public final native LinearScale linear()/*-{
		return this.linear();
	}-*/;

	public final native OrdinalScale ordinal()/*-{
		return this.ordinal();
	}-*/;

	public final native ThresholdScale threshold()/*-{
		return this.threshold();
	}-*/;

	// ========= Domain functions ===============

	/*
	 * * @return the scale current input domain
	 */
	public native final Domain domain()/*-{
		return this.domain();
	}-*/;

	public native final S domain(JavaScriptObject d) /*-{
		return this.domain(d);
	}-*/;

	public native final S domain(double a, double b) /*-{
		if (!this.domain) {
			return this;
		}
		return this.domain([ a, b ]);
	}-*/;

	public native final S domain(String a, String b) /*-{
		return this.domain([ a, b ]);
	}-*/;

	/**
	 * Set the domain of this scale function with a domain that can be
	 * understood by Javascript.
	 * <p>
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public native final <T> S domain(T a, T b) /*-{
		return this.domain(a, b);
	}-*/;

	/**
	 * Extends the domain so that it starts and ends on nice round values.
	 * <p>
	 * This method typically modifies the scale's domain, and may only extend
	 * the bounds to the nearest round value.
	 * <p>
	 * The precision of the round value is dependent on the extent of the domain
	 * dx according to the following formula: exp(round(log(*dx*)) - 1).
	 * <p>
	 * Nicing is useful if the domain is computed from data and may be
	 * irregular.
	 * <p>
	 * For example, for a domain of [0.20147987687960267, 0.996679553296417],
	 * the nice domain is [0.2, 1]. If the domain has more than two values,
	 * nicing the domain only affects the first and last value.
	 * <p>
	 * 
	 * @return the current scale
	 */
	public native final S nice()/*-{
		return this.nice();
	}-*/;

	// ========= Range functions ===============

	/**
	 * Set the scale's output range.
	 * <p>
	 * <<<<<<< HEAD Sets the scale's output range to the specified array of
	 * values. The array must contain two or more values, to match the
	 * cardinality of the input domain. The elements in the given array need not
	 * be numbers; any value that is supported by the underlying interpolator
	 * will work. However, numeric ranges are required for the invert operator.
	 * ======= Sets the scale's output range to the specified array of values.
	 * The array must contain two or more values, to match the cardinality of
	 * the input domain. The elements in the given array need not be numbers;
	 * any value that is supported by the underlying interpolator will work.
	 * However, numeric ranges are required for the invert operator. >>>>>>>
	 * colorpalette
	 * 
	 * @param values
	 *            the array of values.
	 * @return the current scale
	 */
	public final native S range(JavaScriptObject values) /*-{
		return this.range(values);
	}-*/;

	/**
	 * See {@link #range(JsArrayInteger)}.
	 * 
	 * @param a
	 *            the first bound of the range
	 * @param b
	 *            the second bound of the range
	 * @return the current scale
	 */
	public final native S range(int a, int b) /*-{
		return this.range([ a, b ]);
	}-*/;

	/**
	 * Returns an exact copy of this scale.
	 * <p>
	 * Changes to this scale will not affect the returned scale, and vice versa.
	 * 
	 * @return the copy
	 */
	public native final S copy()/*-{
		return this.copy();
	}-*/;

	/**
	 * TODO todo totest; tocomment
	 * 
	 * @return
	 */
	public native final S ticks(int count)/*-{
		return this.ticks(count);
	}-*/;

	/**
	 * TODO todo totest; tocomment
	 * 
	 * @return
	 */
	public native final S tickFormat(int count)/*-{
		return this.ticks(count);
	}-*/;

	/**
	 * TODO todo totest; tocomment
	 * 
	 * @return
	 */
	public native final S tickFormat(int count, String formatSpecifier)/*-{
		return this.ticks(count);
	}-*/;

	/**
	 * Given a value x in the input domain, returns the corresponding value in
	 * the output range.
	 * 
	 * @param d
	 *            the input value
	 * @return the output value
	 */
	public native final Value apply(JavaScriptObject d)/*-{
		return {
			datum : this(d)
		};
	}-*/;

	/**
	 * Given a value x in the input domain, returns the corresponding value in
	 * the output range.
	 * 
	 * @see #apply(JavaScriptObject)
	 * @param d
	 *            the input value
	 * @return the output value
	 */
	public native final Value apply(double d)/*-{
		return {
			datum : this(d)
		};
	}-*/;

	/**
	 * @see #apply(JavaScriptObject)
	 * @param d
	 *            the input value
	 * @return the output value
	 */
	public native final Value apply(String d)/*-{
		return {
			datum : this(d)
		};
	}-*/;
}
