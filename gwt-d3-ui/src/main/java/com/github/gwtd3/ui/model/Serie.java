package com.github.gwtd3.ui.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.github.gwtd3.api.JsArrays;
import com.github.gwtd3.api.arrays.Array;

import com.google.common.base.Preconditions;
import com.google.common.collect.Range;
import com.google.gwt.event.shared.HandlerManager;

public class Serie<T> {

    private HandlerManager eventManager = new HandlerManager(this);

    private final String id;
    private String name;

    private List<T> values = new ArrayList<T>();

    private Map<String, NamedRange<T>> namedRanges = new HashMap<String, NamedRange<T>>();

    /**
     * A NamedRange provides a way of logically grouping contiguous values of a {@link Serie},
     * in order to apply on these values specific formatting or behavior.
     * <p>
     * 
     * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
     * 
     */
    public static class NamedRange<T> {
        private String name;
        private Range<Double> range;
        private Serie<T> serie;

        protected NamedRange(final Serie<T> serie, final String name, final Range<Double> range) {
            super();
            this.name = name;
            this.range = range;
            this.serie = serie;
        }

        public String name() {
            return name;
        }

        public Range<Double> range() {
            return range;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "'" + name + "'[" + range.toString() + "]";
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            return result;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof Serie.NamedRange)) {
                return false;
            }
            @SuppressWarnings("unchecked")
            NamedRange<T> other = (NamedRange<T>) obj;
            if (name == null) {
                if (other.name != null) {
                    return false;
                }
            } else if (!name.equals(other.name)) {
                return false;
            }
            return true;
        }

        public Serie<T> serie() {
            return serie;
        }

    }

    public Serie(final String id) {
        super();
        this.id = id;
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public Serie<T> name(final String name) {
        this.name = name;
        return this;
    }

    public List<T> values() {
        return values;
    }

    public Serie<T> values(final List<T> t) {
        this.values = t;
        return this;
    }

    public Array<T> valuesAsArray() {
        return JsArrays.asJsArray(values());
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public String toString() {
        String s = "[" + id + "]" + " '" + name + "'";
        for (T value : values) {
            s += value + ",";
        }
        return s;
    }

    /**
     * Specify a name for a specific range of values in the serie.
     * <p>
     * If a range already existed for the given name, the old range is replaced with the new one.
     * <p>
     * The given new range cannot intersect any existing range with another name.
     * 
     * @param name the name of the range
     * @param newRange the range
     * @throws IllegalArgumentException if the new range intersects any existing range with other name
     * @return this serie
     */
    public Serie<T> putNamedRange(final String name, final Range<Double> newRange) {
        Preconditions.checkNotNull(name, "name cannot be null");
        Preconditions.checkNotNull(newRange, "newRange cannot be null");
        // remove any existing range
        namedRanges.remove(name);

        // assert the range does not intersect with a previous range
        assertNotIntersectingExistingRanges(newRange);

        namedRanges.put(name, new NamedRange<T>(this, name, newRange));
        // fire event:::
        return this;
    }

    /**
     * Return the {@link NamedRange} for the specified name.
     * 
     * @param name the name of the {@link NamedRange}
     * @return the {@link NamedRange} corresponding to the given name
     */
    // public NamedRange getRange(final String name) {
    // return namedRanges.get(name);
    // }

    /**
     * Assert the given range is not intersecting any existing range.
     * @throws IllegalArgumentException if the given range intersects
     * @param newRange
     */
    private void assertNotIntersectingExistingRanges(final Range<Double> newRange) {
        Set<Entry<String, NamedRange<T>>> ranges = namedRanges.entrySet();
        for (Entry<String, NamedRange<T>> range : ranges) {
            Preconditions.checkArgument(range.getValue().range().intersection(newRange).isEmpty(),
                    "The given newRange %s intersect with the existing range %s",
                    newRange.toString(), range.getKey(), range.getValue().toString());
        }
    }

    /**
     * 
     * @return an unmodifiable list of {@link NamedRange}.
     */
    // public List<NamedRange> namedRanges() {
    // return new ArrayList<>(namedRanges.values());
    // }

    /**
     * 
     * @return an unmodifiable list of {@link NamedRange}.
     */
    public Set<Range<Double>> namedRanges() {
        Set<Range<Double>> result = new HashSet<Range<Double>>();
        Collection<NamedRange<T>> ranges = namedRanges.values();
        for (NamedRange<T> namedRange : ranges) {
            result.add(namedRange.range());
        }
        return result;
    }

    /**
     * Return a {@link Set} containing only the {@link Range}s
     * that are visible inside the given range.
     * More formally, a range r is returned in the set only
     * 
     * if visibleXRange.{@link Range#isConnected(Range) isConnected(r)} returns true.
     * 
     * @param visibleXRange
     * @return
     */
    public Set<NamedRange<T>> visibleNamedRanges(final Range<Double> visibleXRange) {
        Set<NamedRange<T>> result = new HashSet<NamedRange<T>>();
        Collection<NamedRange<T>> ranges = namedRanges.values();
        for (NamedRange<T> namedRange : ranges) {
            if (visibleXRange.isConnected(namedRange.range())) {
                result.add(namedRange);
            }
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Serie)) {
            return false;
        }
        Serie other = (Serie) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

}
