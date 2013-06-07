package com.github.gwtd3.api.arrays;

import java.util.Iterator;

/**
 * Internal iterator from a JsArray.
 * 
 * The access method is abstract to allow casting
 * the returned type.
 * 
 * 
 * @author SCHIOCA
 * 
 * @param <S>
 */
public abstract class ArrayIterator<T> implements Iterator<T> {
    /**
     * 
     */
    private final Array<T> array;

    /**
     * @param array
     */
    public ArrayIterator(final Array<T> array) {
        this.array = array;
    }

    private int index = 0;
    private int lastReturnedIndex;

    @Override
    public boolean hasNext() {
        return index < this.array.length();
    }

    @Override
    public T next() {
        lastReturnedIndex = index;
        return accessObject(array, index++);
    }

    @Override
    public void remove() {
        if ((lastReturnedIndex >= 0) && (lastReturnedIndex < this.array.length())) {
            this.array.splice(lastReturnedIndex, 1);
        }
    }

    public abstract T accessObject(Array<T> array, int index);
}