package com.github.gwtd3.api.interpolators;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * An interpolator that can be passed to JSNI.
 * <p>
 * This is useful when implementing an interpolator in Java and passed to a JSNI framework that's gonna invoke the
 * interpolation.
 * 
 * 
 * It returns
 * @author Anthony Schiochet (schiochetanthoni@gmail.com)
 * 
 * @param <T>
 */
public abstract class CallableInterpolator<T> implements Interpolator<T> {

    @Override
    public abstract T interpolate(double t);

    @Override
    public native final JavaScriptObject asJSOFunction() /*-{
		var self = this;
		//alert('yes');
		return function(t) {
			return self.@com.github.gwtd3.api.interpolators.CallableInterpolator::interpolate(D)(t);
			//return 0;
		}
    }-*/;

}
