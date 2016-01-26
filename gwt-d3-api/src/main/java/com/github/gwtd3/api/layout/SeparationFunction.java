package com.github.gwtd3.api.layout;

/**
 * Separation function used in {@link #separation(Object, Object)} method.
 *
 * @param <T>
 */
public interface SeparationFunction<T> {
    /**
     * Return the separation to be used between the 2 nodes
     * 
     * @param a
     * @param b
     * @return
     */
    double separation(T a, T b);
}