package com.github.gwtd3.ui.event;

import com.github.gwtd3.ui.event.SerieChangeEvent.SerieChangeHandler;
import com.github.gwtd3.ui.model.Serie;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class SerieChangeEvent<T> extends GwtEvent<SerieChangeHandler<T>> {

    public static Type<SerieChangeHandler<?>> TYPE = new Type<SerieChangeHandler<?>>();
    private final Serie<T> serie;

    public interface SerieChangeHandler<T> extends EventHandler {
        void onSerieChange(SerieChangeEvent<T> event);
    }

    public interface SerieChangeHasHandlers<T> extends HasHandlers {
        HandlerRegistration addSerieChangeHandler(SerieChangeHandler<T> handler);
    }

    public SerieChangeEvent(final Serie<T> serie) {
        super();
        this.serie = serie;
    }

    public Serie<T> getSerie() {
        return serie;
    }

    @Override
    protected void dispatch(final SerieChangeHandler<T> handler) {
        handler.onSerieChange(this);
    }

    // The instance knows its BeforeSelectionHandler is of type I, but the TYPE
    // field itself does not, so we have to do an unsafe cast here.
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public final Type<SerieChangeHandler<T>> getAssociatedType() {
        return (Type) TYPE;
    }

    public static Type<SerieChangeHandler<?>> getType() {
        return TYPE;
    }

    public static <T> void fire(final HasHandlers source, final Serie<T> serie) {
        source.fireEvent(new SerieChangeEvent<T>(serie));
    }
}
