package com.github.gwtd3.api.arrays;


/**
 * @author <a href="mailto:eric.citaire@gmail.com">Eric Citaire</a>
 *
 * @param <T> the type of the values in the array
 */
public interface FilterCallback<T> {
    /**
     * @param element
     * @param index
     * @param array
     * @return
     */
    boolean filter(T element, int index, Array<T> array);
}
