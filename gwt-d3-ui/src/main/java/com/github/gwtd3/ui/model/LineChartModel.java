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
package com.github.gwtd3.ui.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.gwtd3.api.JsArrays;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.scales.Scale;
import com.github.gwtd3.ui.event.SerieAddedEvent;
import com.github.gwtd3.ui.event.SerieAddedEvent.SerieAddedHandler;
import com.github.gwtd3.ui.event.SerieAddedEvent.SerieAddedHasHandlers;
import com.github.gwtd3.ui.event.SerieRemovedEvent;
import com.github.gwtd3.ui.event.SerieRemovedEvent.SerieRemovedHandler;
import com.github.gwtd3.ui.event.SerieRemovedEvent.SerieRemovedHasHandlers;
import com.google.common.base.Preconditions;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class LineChartModel<T, S extends Scale<S>> extends BaseChartModel<T, S> implements SerieAddedHasHandlers<T>,
        SerieRemovedHasHandlers<T> {

    /**
     * Used to convert instance of T to x and y domain values.
     * 
     */
    private PointBuilder<T> coordsBuilder;

    private final Map<String, Serie<T>> series = new HashMap<String, Serie<T>>();

    public static <X, Y extends Scale<Y>> LineChartModel<X, Y> create(final AxisModel<Y> xModel,
            final AxisModel<Y> yModel, final PointBuilder<X> coordsBuilder) {
        return new LineChartModel<X, Y>(xModel, yModel, coordsBuilder);
    }

    public LineChartModel(final AxisModel<S> xModel, final AxisModel<S> yModel, final PointBuilder<T> coordsBuilder) {
        super(xModel, yModel);
        this.coordsBuilder = coordsBuilder;

    }

    // =========== coords builder ================

    public BaseChartModel<T, S> coordsBuilder(final PointBuilder<T> pointBuilder) {
        this.coordsBuilder = pointBuilder;
        return this;
    }

    public PointBuilder<T> coordsBuilder() {
        return coordsBuilder;
    }

    // =========== series methods ================

    public boolean isEmpty() {
        return series.isEmpty();
    }

    /**
     * Return an unmodifiable list of the series.
     * 
     * @return the list of series.
     */
    public List<Serie<T>> series() {
        return Collections.unmodifiableList(new ArrayList<Serie<T>>(series.values()));
    }

    public Array<Serie<T>> seriesAsArray() {
        return JsArrays.asJsArray(series());
    }

    /**
     * Returns the serie identified by the given id. If such a serie does not
     * exist, it is created.
     * <p>
     * 
     * @param serie
     * @return
     */
    public Serie<T> serie(final String id) {
        Preconditions.checkNotNull(coordsBuilder, "please define the PointBuilder first");
        Serie<T> serie = this.series.get(id);
        if (serie == null) {
            serie = new Serie<T>(id, coordsBuilder);
            this.series.put(id, serie);
            fireEvent(new SerieAddedEvent<T>(serie));
        }
        return serie;
    }

    public BaseChartModel<T, S> removeSerie(final String id) {
        Serie<T> serie = this.series.remove(id);
        if (serie != null) {
            fireEvent(new SerieRemovedEvent<T>(serie));
        }
        return this;
    }

    // =========== events methods ================

    @Override
    public HandlerRegistration addSerieAddedHandler(final SerieAddedHandler<T> handler) {
        return eventManager.addHandler(SerieAddedEvent.TYPE, handler);
    }

    @Override
    public HandlerRegistration addSerieRemovedHandler(final SerieRemovedHandler<T> handler) {
        return eventManager.addHandler(SerieRemovedEvent.TYPE, handler);
    }

}
