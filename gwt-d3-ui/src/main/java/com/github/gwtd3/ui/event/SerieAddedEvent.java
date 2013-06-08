package com.github.gwtd3.ui.event;

import com.github.gwtd3.ui.event.SerieAddedEvent.SerieAddedHandler;
import com.github.gwtd3.ui.model.Serie;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class SerieAddedEvent<T> extends GwtEvent<SerieAddedHandler<T>> {

    public static Type<SerieAddedHandler<?>> TYPE = new Type<SerieAddedHandler<?>>();
    private final Serie<T> serie;

    public interface SerieAddedHandler<T> extends EventHandler {
        void onSerieAdded(SerieAddedEvent<T> event);
    }

    public interface SerieAddedHasHandlers<T> extends HasHandlers {
        HandlerRegistration addSerieAddedHandler(SerieAddedHandler<T> handler);
    }

    public SerieAddedEvent(final Serie<T> serie) {
        super();
        this.serie = serie;
    }

    public Serie<T> getSerie() {
        return serie;
    }

    @Override
    protected void dispatch(final SerieAddedHandler<T> handler) {
        handler.onSerieAdded(this);
    }

    // The instance knows its BeforeSelectionHandler is of type I, but the TYPE
    // field itself does not, so we have to do an unsafe cast here.
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public final Type<SerieAddedHandler<T>> getAssociatedType() {
        return (Type) TYPE;
    }

    public static Type<SerieAddedHandler<?>> getType() {
        return TYPE;
    }

    public static <T> void fire(final HasHandlers source, final Serie<T> serie) {
        source.fireEvent(new SerieAddedEvent<T>(serie));
    }
}
