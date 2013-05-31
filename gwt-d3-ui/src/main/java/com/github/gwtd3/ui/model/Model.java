package com.github.gwtd3.ui.model;

import java.util.HashSet;
import java.util.Set;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.JsArrays;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.arrays.NumericForEachCallback;
import com.github.gwtd3.api.core.Value;

import com.github.gwtd3.ui.chart.LineChart;
import com.github.gwtd3.ui.event.RangeChangeEvent;
import com.google.common.collect.Range;

/**
 * Model for a {@link LineChart}.
 * <p>
 * A model is constituted of a list of {@link Serie} that represents the same kind of data in one single universe.
 * <p>
 * D
 * 
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 * @param <T>
 */
public class Model<T> {
    private final LineChart<T> chart;

    private final Set<Serie<T>> series = new HashSet<Serie<T>>();

    /**
     * Provide the x domain value of an object of the serie
     */
    private final NumericForEachCallback valueToDomainX = new NumericForEachCallback() {
        @Override
        public double forEach(final Object thisArg, final Value element, final int index, final Array<?> array) {
            T e = element.as();
            return pointBuilder().x(e);
        }
    };

    /**
     * Provide the y domain vlaue of an object of the serie.
     */
    private final NumericForEachCallback valueToDomainY = new NumericForEachCallback() {
        @Override
        public double forEach(final Object thisArg, final Value element, final int index, final Array<?> array) {
            T e = element.as();
            return pointBuilder().y(e);
        }
    };
    /**
     * Used to convert instance of T to x and y domain values.
     * 
     */
    private PointBuilder<T> pointBuilder;

    private Range<Double> visibleXRange;

    private Range<Double> visibleYRange;

    public Model(final LineChart<T> chart) {
        super();
        this.chart = chart;
    }

    public boolean isEmpty() {
        return series.isEmpty();
    }

    public Set<Serie<T>> series() {
        return series;
    }

    public Array<Serie<T>> seriesAsArray() {
        return JsArrays.asJsArray(series());
    }

    /**
     * Return the maximum value among all series using the given {@link NumericForEachCallback}.
     * 
     * @param domainValueGetter
     *            the numeric for each callback to be used
     * @return the maximum
     */
    private double maxSeriesValue(final NumericForEachCallback domainValueGetter) {
        if (seriesAsArray().length() == 0) {
            return 1.0;
        }
        return D3.max(seriesAsArray(), new NumericForEachCallback() {
            @Override
            public double forEach(final Object thisArg, final Value element, final int index, final Array<?> array) {
                Serie<T> serie = element.as();
                return D3.max(serie.valuesAsArray(), domainValueGetter).asDouble();
            }
        }).asDouble();
    }

    /**
     * Return the minimum value among all series using the given {@link NumericForEachCallback}.
     * 
     * @param domainValueGetter
     *            the numeric for each callback to be used
     * @return the maximum
     */
    private double minSeriesValue(final NumericForEachCallback domainValueGetter) {
        if (seriesAsArray().length() == 0) {
            return 0.0;
        }
        return D3.min(seriesAsArray(), new NumericForEachCallback() {
            @Override
            public double forEach(final Object thisArg, final Value element, final int index, final Array<?> array) {
                Serie<T> serie = element.as();
                return D3.min(serie.valuesAsArray(), domainValueGetter).asDouble();
            }
        }).asDouble();
    }

    public double minX() {
        return minSeriesValue(valueToDomainX);
    }

    public double maxX() {
        return maxSeriesValue(valueToDomainX);
    }

    public double minY() {
        return minSeriesValue(valueToDomainY);
    }

    public double maxY() {
        return maxSeriesValue(valueToDomainY);
    }

    public Model<T> pointBuilder(final PointBuilder<T> pointBuilder) {
        this.pointBuilder = pointBuilder;
        return this;
    }

    public PointBuilder<T> pointBuilder() {
        return pointBuilder;
    }

    public Model<T> addSerie(final Serie<T> serie) {
        this.series.add(serie);
        return this;
    }

    public LineChart<T> update() {
        this.chart.redraw();
        return chart;
    }

    /**
     * @return the range of values currently visible on the y axis
     */
    public Range<Double> visibleYRange() {
        if (visibleYRange == null) {
            return Range.closed(minY(), maxY());
        }
        return visibleYRange;
    }

    /**
     * @return the range of values currently visible on the x axis
     */
    public Range<Double> visibleXRange() {
        if (visibleXRange == null) {
            return Range.closed(minX(), maxX());
        }
        return visibleXRange;
    }

    /**
     * @return the range of values currently visible on the x axis
     */
    public Model<T> visibleXRange(final Range<Double> range, final boolean sendEvents) {
        Range<Double> oldRange = visibleXRange;
        visibleXRange = range;
        if (sendEvents) {
            RangeChangeEvent.fire(chart.xAxis(), range, oldRange);
        }
        return this;
    }

    /**
     * Alias for {@link #visibleXRange(Range, false)}
     * 
     * @return the range of values currently visible on the x axis
     */
    public Model<T> visibleXRange(final Range<Double> range) {
        return visibleXRange(range, false);
    }

    /**
     * @return the range of values currently visible on the y axis
     */
    public Model<T> visibleYRange(final Range<Double> range, final boolean sendEvents) {
        Range<Double> oldRange = visibleYRange;
        visibleYRange = range;
        if (sendEvents) {
            RangeChangeEvent.fire(chart.yAxis(), range, oldRange);
        }
        return this;
    }

    /**
     * Alias for {@link #visibleYRange(Range, false)}
     * 
     * @return the range of values currently visible on the y axis
     */
    public Model<T> visibleYRange(final Range<Double> range) {
        return visibleYRange(range, false);
    }

    /**
     * Return the chart associated to this model.
     * 
     * @return the chart
     */
    public LineChart<T> chart() {
        return chart;
    }

}