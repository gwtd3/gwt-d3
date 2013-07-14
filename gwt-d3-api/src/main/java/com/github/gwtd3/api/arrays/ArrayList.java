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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

import com.github.gwtd3.api.core.Value;

/**
 * A {@link List} wrapping a Javascript {@link Array}.
 * <p>
 * The backed array should contains the type of object specified by &lt;E&gt; or {@link ClassCastException} may be
 * raised.
 * <p>
 * Also this list cannot be used to wrap arrays of primitive wrapper (Boolean, Double, etc...).
 * <p>
 * TODO: fix the issue above by creating a specialized class a List<Value> with a specific accessor.
 * 
 * @author SCHIOCA
 * 
 * @param <E>
 */
public class ArrayList<E> implements List<E>, RandomAccess, Cloneable, java.io.Serializable {

    private final Array<E> array;

    public ArrayList(final Array<E> array) {
        super();
        this.array = array;
    }

    /**
     * 
     */
    private static final long serialVersionUID = 3029722246147578999L;

    @Override
    public int size() {
        return array.length();
    }

    @Override
    public boolean isEmpty() {
        return array.length() == 0;
    }

    private static class FindElement<E> implements ForEachCallback<Boolean> {
        private final Object o;
        private E objectFound;
        private int indexFound;

        public FindElement(final Object o) {
            super();
            this.o = o;
        }

        @Override
        public Boolean forEach(final Object thisArg, final Value element, final int index, final Array<?> array) {
            if (equals(o, element.as())) {
                objectFound = element.as();
                indexFound = index;
                return true;
            }
            return false;
        }

        private boolean equals(final Object o1, Object o2) {
            if (o2 instanceof Value) {
                o2 = ((Value) o2).as();
            }
            return o1 == null ? o2 == null : o1.equals(o2);
        }

        public E getFoundObject() {
            return objectFound;
        }

        public int getFoundIndex() {
            return indexFound;
        }
    }

    @Override
    public boolean contains(final Object o) {
        if (true) {
            throw new IllegalStateException("TO BE TESTED");
        }
        return array.some(new FindElement<E>(o));
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayIterator<E>(array) {
            @Override
            public E accessObject(final Array<E> array, final int index) {
                return array.getObject(index);
            }
        };
    }

    @Override
    public Object[] toArray() {
        throw new IllegalStateException("not yet implemented");
    }

    @Override
    public <T> T[] toArray(final T[] a) {
        throw new IllegalStateException("not yet implemented");
    }

    @Override
    public boolean add(final E e) {
        array.push(e);
        return true;
    }

    @Override
    public boolean remove(final Object o) {
        FindElement<E> e = new FindElement<E>(o);
        if (array.some(e)) {
            array.splice(e.getFoundIndex(), 1);
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        for (Object object : c) {
            if (!contains(object)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(final Collection<? extends E> c) {
        // TODO: optimize this with a concat
        for (E e : c) {
            array.push(e);
        }
        return true;
    }

    @Override
    public boolean addAll(final int index, final Collection<? extends E> c) {
        // TODO: optimize this with a batch splice?
        // or with concat + slice
        for (E e : c) {
            array.splice(index, 0, e);
        }
        return false;
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        throw new IllegalStateException("not yet implemented");
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        throw new IllegalStateException("not yet implemented");
    }

    @Override
    public void clear() {
        array.setLength(0);
    }

    @Override
    public E get(final int index) {
        return array.getObject(index);
    }

    @Override
    public E set(final int index, final E element) {
        E old = array.getObject(index);
        array.set(index, element);
        return old;
    }

    @Override
    public void add(final int index, final E element) {
        array.splice(index, 0, element);
    }

    @Override
    public E remove(final int index) {
        // TODO preconditions
        return array.splice(index, 1).getObject(0);
    }

    @Override
    public int indexOf(final Object o) {
        return array.indexOf(o);
    }

    @Override
    public int lastIndexOf(final Object o) {
        return array.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new IllegalArgumentException("not yet implemented");
    }

    @Override
    public ListIterator<E> listIterator(final int index) {
        throw new IllegalArgumentException("not yet implemented");
    }

    @Override
    public List<E> subList(final int fromIndex, final int toIndex) {
        throw new IllegalArgumentException("not yet implemented");
    }

}
