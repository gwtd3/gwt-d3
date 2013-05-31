package com.github.gwtd3.api.arrays;

import com.github.gwtd3.api.core.Value;

public interface ForEachCallback<T> {
    /**
     * Executed for each element of the array with an assigned value.
     * <p>
     * If a thisArg parameter is provided to {@link Array#forEach(ForEachCallback, Object)}, it will be used as the
     * thisArg parameter for each callback invocation.
     * <p>
     * If thisArg is undefined or null, the this value within the function depends on whether the function is in strict
     * mode or not (passed value if in strict mode, global object if in non-strict mode).
     * 
     * @see https://developer.mozilla.org/en-US/docs/JavaScript/Reference/Global_Objects/Array/forEach
     * 
     * @param thisArg
     * @param element
     * @param index
     * @param array
     */
    T forEach(Object thisArg, Value element, int index, Array<?> array);
}
