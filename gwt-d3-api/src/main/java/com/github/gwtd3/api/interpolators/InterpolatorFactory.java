/**
 * 
 */
package com.github.gwtd3.api.interpolators;

/**
 * TODO
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public interface InterpolatorFactory {

	Interpolator<Object> create(int a, int b);
}
