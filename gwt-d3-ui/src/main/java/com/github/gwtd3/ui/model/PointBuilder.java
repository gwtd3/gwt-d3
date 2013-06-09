package com.github.gwtd3.ui.model;

/**
 * Interface for an object that can generate x and y values to construct a point
 * from a value.
 * <p>
 * The result of {@link #x()} and {@link #y()} functions are not necessarily
 * coordinates in terms of pixel space, but may represents coords in terms of
 * domain space.
 * <p>
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public interface PointBuilder<T> {

	/**
	 * Convert an object into an x.
	 * 
	 * @param value
	 * @return
	 */
	double x(T value);

	/**
	 * Convert an object into an y.
	 * 
	 * @param value
	 * @return
	 */
	double y(T value);

}
