/**
 * Copyright (c) 2013, Anthony Schiochet and Eric Citaire
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * The names Anthony Schiochet and Eric Citaire may not be used to endorse or promote products
 *   derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL MICHAEL BOSTOCK BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY
 * OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
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