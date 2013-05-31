package com.github.gwtd3.api.arrays;

public interface MapNumbersCallback<R> {
	R map(double element, int index, Array<Double> array);
}
