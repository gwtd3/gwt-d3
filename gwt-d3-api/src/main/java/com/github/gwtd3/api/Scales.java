/**
 * 
 */
package com.github.gwtd3.api;

import com.github.gwtd3.api.scales.LinearScale;
import com.github.gwtd3.api.scales.OrdinalScale;
import com.github.gwtd3.api.scales.Scale;
import com.github.gwtd3.api.scales.ThresholdScale;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Factory for {@link Scale}s.
 * <p>
 * Scales are functions that map from an input domain to an output range. There is 3 kind of scales:
 * <ul>
 * <li>Quantitative Scales - for continuous input domains, such as numbers.
 * <li>Ordinal Scales - for discrete input domains, such as names or categories.
 * <li>Time Scales - for time domains. See {@link D3#time()}.
 * </ul>
 * <p>
 * <h2>Quantitative scales</h2>
 * Quantitative scales have a continuous domain, such as the set of real numbers, or dates. There are also ordinal scales, which have a discrete domain, such as a set of names or
 * categories. Scales are an optional feature in D3; you don't have to use them, if you prefer to do the math yourself. However, using scales can greatly simplify the code needed
 * to map a dimension of data to a visual representation. A scale object, such as that returned by d3.scale.linear, is both an object and a function. That is: you can call the
 * scale like any other function, and the scale has additional methods that change its behavior. Like other classes in D3, scales follow the method chaining pattern where setter
 * methods return the scale itself, allowing multiple setters to be invoked in a concise statement.
 * <p>
 * 
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Scales extends JavaScriptObject {
	protected Scales() {

	}

	/**
	 * Constructs a new linear scale with the default domain [0,1] and the default range [0,1].
	 * Thus, the default linear scale is equivalent to the identity function for numbers; for example linear(0.5) returns 0.5.
	 * 
	 * @return a new default linear scale
	 */
	public final native LinearScale linear()/*-{
		return this.linear();
	}-*/;

	public final native OrdinalScale ordinal()/*-{
		return this.ordinal();
	}-*/;

	public final native ThresholdScale threshold()/*-{
		return this.threshold();
	}-*/;

	/**
	 * @return
	 */
	public static final native Scales get()/*-{
		return $wnd.d3.scale;
	}-*/;

}