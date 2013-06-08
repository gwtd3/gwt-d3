package com.github.gwtd3.ui.model;

import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.arrays.NumericForEachCallback;
import com.github.gwtd3.api.core.Value;

/**
 * Provides base for a {@link PointBuilder}.
 * 
 * @author SCHIOCA
 * 
 * @param <T>
 */
public abstract class BasePointBuilder<T> implements PointBuilder<T> {

    private final NumericForEachCallback xAccessor = new NumericForEachCallback() {
        @Override
        public double forEach(final Object thisArg, final Value element, final int index, final Array<?> array) {
            return x(element.<T> as());
        }
    };

    private final NumericForEachCallback yAccessor = new NumericForEachCallback() {
        @Override
        public double forEach(final Object thisArg, final Value element, final int index, final Array<?> array) {
            return y(element.<T> as());
        }
    };

    public NumericForEachCallback getXAccessor() {
        return xAccessor;
    }

    public NumericForEachCallback getYAccessor() {
        return yAccessor;
    }
}
