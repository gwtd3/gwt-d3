package com.github.gwtd3.api.ease;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.core.Transition;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * An easing function takes the current parameterized time value t in the domain [0,1],
 * and maps it to another value in a similar range; it is typically used to set {@link Transition#ease()}.
 * <p>
 * EasingFunctions may be created using {@link D3#ease} builder.
 * <p>
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 *
 */
public class EasingFunction extends JavaScriptObject{

	/**
	 * Given a parametric time t in the range [0,1], returns the eased time.
	 * <p>
	 * The returned value is typically in the range [0,1] as well,
	 * but may extend slightly beyond this range for certain easing functions, such as "elastic".
	 * <p>
	 * @param t the parametric time
	 * @return the eased time
	 */
	public native final double ease(double t)/*-{
		return this(t);
	}-*/;
}
