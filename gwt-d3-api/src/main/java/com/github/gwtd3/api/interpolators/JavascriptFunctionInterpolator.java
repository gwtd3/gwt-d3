package com.github.gwtd3.api.interpolators;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.core.Value;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * An interpolator used when the interpolation function is provided by the
 * Javascript side.
 * <p>
 * This class is used by {@link D3} to allow java code to invoke built-in interpolators. You should not use this object
 * unless you know what you are doing.
 * <p>
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class JavascriptFunctionInterpolator extends JavaScriptObject implements Interpolator<Value> {

    protected JavascriptFunctionInterpolator() {
        super();
    }

    @Override
    public final native Value interpolate(final double t)/*-{
		var result = this(t);
		return {
			datum : result
		};
    }-*/;

    @Override
    public final JavaScriptObject asJSOFunction() {
        return this;
    }

}
