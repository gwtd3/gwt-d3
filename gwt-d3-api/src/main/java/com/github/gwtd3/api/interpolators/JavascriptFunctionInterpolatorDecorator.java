package com.github.gwtd3.api.interpolators;

import com.github.gwtd3.api.core.Value;

import com.google.gwt.core.client.JavaScriptObject;

public class JavascriptFunctionInterpolatorDecorator<T> implements Interpolator<T> {

    protected JavascriptFunctionInterpolator delegate;

    public JavascriptFunctionInterpolatorDecorator(final JavascriptFunctionInterpolator delegate) {
        super();
        this.delegate = delegate;
    }

    @Override
    public JavaScriptObject asJSOFunction() {
        return delegate.asJSOFunction();
    }

    @Override
    public T interpolate(final double t) {
        return cast(delegate.interpolate(t));
    }

    /**
     * Cast the given value to the correct type.
     * @param v
     * @return
     */
    public T cast(final Value v) {
        return v.as();
    }
}
