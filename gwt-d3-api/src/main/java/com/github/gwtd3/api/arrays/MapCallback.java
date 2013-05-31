package com.github.gwtd3.api.arrays;

public interface MapCallback<T, R> {
	R map(T element, int index, Array<T> array);
}
