package com.github.gwtd3.ui.model;


/**
 * Interface for an object that can generate x and y values to construct a
 * point.
 * <p>
 * The result of {@link #x()} and {@link #y()} functions are not necessarily
 * coordinates in terms of pixel space, but rather coords in terms of domain
 * space.
 * <p>
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public interface PointBuilder<T> {

	double x(T value);

	double y(T value);
}
