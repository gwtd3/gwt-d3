package com.github.gwtd3.ui.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.gwtd3.api.JsArrays;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.scales.Scale;
import com.github.gwtd3.ui.chart.LineChart;
import com.github.gwtd3.ui.event.SerieAddedEvent;
import com.github.gwtd3.ui.event.SerieAddedEvent.SerieAddedHandler;
import com.github.gwtd3.ui.event.SerieAddedEvent.SerieAddedHasHandlers;
import com.github.gwtd3.ui.event.SerieRemovedEvent;
import com.github.gwtd3.ui.event.SerieRemovedEvent.SerieRemovedHandler;
import com.github.gwtd3.ui.event.SerieRemovedEvent.SerieRemovedHasHandlers;
import com.google.common.base.Preconditions;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * Model for a {@link LineChart}.
 * <p>
 * A model is constituted of a list of {@link Serie} that represents the same
 * kind of data in one single universe.
 * <p>
 * D
 * 
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 * @param <T>
 */
public class Model<T, S extends Scale<S>> implements SerieAddedHasHandlers<T>, SerieRemovedHasHandlers<T> {

	private final AxisModel<S> xModel;
	private final AxisModel<S> yModel;

	private final Map<String, Serie<T>> series = new HashMap<String, Serie<T>>();

	/**
	 * Used to convert instance of T to x and y domain values.
	 * 
	 */
	private PointBuilder<T> coordsBuilder;

	private final HandlerManager eventManager = new HandlerManager(this);

	public Model(final AxisModel<S> xModel, final AxisModel<S> yModel, final PointBuilder<T> coordsBuilder) {
		super();
		this.xModel = xModel;
		this.yModel = yModel;
		this.coordsBuilder = coordsBuilder;
	}

	// =========== delegate models ================

	public AxisModel<S> xModel() {
		return xModel;
	}

	public AxisModel<S> yModel() {
		return yModel;
	}

	// =========== coords builder ================

	public Model<T, S> coordsBuilder(final PointBuilder<T> pointBuilder) {
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

	public Model<T, S> removeSerie(final String id) {
		Serie<T> serie = this.series.remove(id);
		if (serie != null) {
			fireEvent(new SerieRemovedEvent<T>(serie));
		}
		return this;
	}

	// =========== events methods ================
	@Override
	public void fireEvent(final GwtEvent<?> event) {
		eventManager.fireEvent(event);
	}

	@Override
	public HandlerRegistration addSerieAddedHandler(final SerieAddedHandler<T> handler) {
		return eventManager.addHandler(SerieAddedEvent.TYPE, handler);
	}

	@Override
	public HandlerRegistration addSerieRemovedHandler(final SerieRemovedHandler<T> handler) {
		return eventManager.addHandler(SerieRemovedEvent.TYPE, handler);
	}

}