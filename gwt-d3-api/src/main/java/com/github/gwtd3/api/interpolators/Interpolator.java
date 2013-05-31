/**
 * 
 */
package com.github.gwtd3.api.interpolators;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public interface Interpolator<T> {

    public T interpolate(double t);

    public JavaScriptObject asJSOFunction();
}
