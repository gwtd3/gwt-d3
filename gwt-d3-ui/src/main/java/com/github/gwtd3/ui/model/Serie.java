package com.github.gwtd3.ui.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.github.gwtd3.ui.event.SerieChangeEvent;
import com.github.gwtd3.ui.event.SerieChangeEvent.SerieChangeHandler;
import com.github.gwtd3.ui.event.SerieChangeEvent.SerieChangeHasHandlers;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Range;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class Serie<T> implements SerieChangeHasHandlers<T>, ValueProvider<T> {

    private final HandlerManager eventManager = new HandlerManager(this);

    private final String id;
    // private String name;

    private List<T> values = new ArrayList<T>();

    private final Map<String, NamedRange> namedRanges = new HashMap<String, NamedRange>();

    private final PointBuilder<T> domainBuilder;

    /**
     * A NamedRange provides a way of logically grouping contiguous values of a {@link Serie}, in order to apply on
     * these values specific formatting or
     * behavior.
     * <p>
     * The NamedRange is a {@link Predicate} allowing to filter domain values.
     * 
     * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
     * 
     */
    public class NamedRange implements ValueProvider<T>, DomainFilter<T> {
        private final String id;
        private final Range<Double> range;
        private final Serie<T> serie;
        private int startIndex;
        private int endIndex;

        protected NamedRange(final Serie<T> serie, final String id, final Range<Double> range) {
            super();
            this.id = id;
            this.range = range;
            this.serie = serie;
        }

        public String id() {
            return id;
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
            return "'" + id + "'[" + range.toString() + "]";
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
            result = (prime * result) + ((id == null) ? 0 : id.hashCode());
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
            NamedRange other = (NamedRange) obj;
            if (id == null) {
                if (other.id != null) {
                    return false;
                }
            } else if (!id.equals(other.id)) {
                return false;
            }
            return true;
        }

        public Serie<T> serie() {
            return serie;
        }

        @Override
        public List<T> getValues() {
            return values;
        }

        @Override
        public boolean accept(final T value) {
            return range.contains(domainBuilder.x(value));
        }
    }

    public Serie(final String id, final PointBuilder<T> domainBuilder) {
        super();
        this.id = id;
        this.domainBuilder = domainBuilder;
    }

    // =========== id ===============

    public String id() {
        return id;
    }

    // =========== name===============
    // public String name() {
    // return name;
    // }
    //
    // public Serie<T> name(final String name) {
    // this.name = name;
    // return this;
    // }

    // =========== values ===============

    @Override
    public List<T> getValues() {
        return values;
    }

    public Serie<T> values(final List<T> t) {
        this.values = t;
        fireEvent(new SerieChangeEvent<T>(this));
        return this;
    }

    // ============

    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public String toString() {
        String s = "[" + id + "]:";
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
     * @param name
     *            the name of the range
     * @param newRange
     *            the range
     * @throws IllegalArgumentException
     *             if the new range intersects any existing range with other
     *             name
     * @return this serie
     */
    public Serie<T> putNamedRange(final String name, final Range<Double> newRange) {
        Preconditions.checkNotNull(name, "name cannot be null");
        Preconditions.checkNotNull(newRange, "newRange cannot be null");
        // remove any existing range
        namedRanges.remove(name);

        // assert the range does not intersect with a previous range
        assertNotIntersectingExistingRanges(newRange);

        namedRanges.put(name, new NamedRange(this, name, newRange));
        fireEvent(new SerieChangeEvent<T>(this));
        return this;
    }

    /**
     * Return the {@link NamedRange} for the specified name.
     * 
     * @param name
     *            the name of the {@link NamedRange}
     * @return the {@link NamedRange} corresponding to the given name
     */
    // public NamedRange getRange(final String name) {
    // return namedRanges.get(name);
    // }

    /**
     * Assert the given range is not intersecting any existing range.
     * 
     * @throws IllegalArgumentException
     *             if the given range intersects
     * @param newRange
     */
    private void assertNotIntersectingExistingRanges(final Range<Double> newRange) {
        Set<Entry<String, NamedRange>> ranges = namedRanges.entrySet();
        for (Entry<String, NamedRange> range : ranges) {
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
    public List<NamedRange> namedRanges() {
        Collection<NamedRange> ranges = namedRanges.values();
        return Collections.unmodifiableList(new ArrayList<NamedRange>(ranges));
    }

    /**
     * Return a {@link List} containing only the {@link NamedRange}s that are
     * fully or partially enclosed by the given range.
     * 
     * @param
     * @return
     */
    public List<NamedRange> getOverlappingRanges(final Range<Double> range) {
        List<NamedRange> result = new ArrayList<NamedRange>();
        Collection<NamedRange> ranges = namedRanges.values();
        for (NamedRange namedRange : ranges) {
            if (range.isConnected(namedRange.range())) {
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
        result = (prime * result) + ((id == null) ? 0 : id.hashCode());
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
        Serie<?> other = (Serie<?>) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public void fireEvent(final GwtEvent<?> event) {
        eventManager.fireEvent(event);
    }

    @Override
    public HandlerRegistration addSerieChangeHandler(final SerieChangeHandler<T> handler) {
        return eventManager.addHandler(SerieChangeEvent.TYPE, handler);
    }

}
