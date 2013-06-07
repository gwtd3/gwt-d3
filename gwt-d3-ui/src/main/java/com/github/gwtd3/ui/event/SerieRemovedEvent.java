package com.github.gwtd3.ui.event;

import com.github.gwtd3.ui.event.SerieRemovedEvent.SerieRemovedHandler;
import com.github.gwtd3.ui.model.Serie;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class SerieRemovedEvent<T> extends GwtEvent<SerieRemovedHandler<T>> {

    public static Type<SerieRemovedHandler<?>> TYPE = new Type<SerieRemovedHandler<?>>();
    private final Serie<T> serie;

    public interface SerieRemovedHandler<T> extends EventHandler {
        void onSerieRemoved(SerieRemovedEvent<T> event);
    }

    public interface SerieRemovedHasHandlers<T> extends HasHandlers {
        HandlerRegistration addSerieRemovedHandler(SerieRemovedHandler<T> handler);
    }

    public SerieRemovedEvent(final Serie<T> serie) {
        super();
        this.serie = serie;
    }

    public Serie<T> getSerie() {
        return serie;
    }

    @Override
    protected void dispatch(final SerieRemovedHandler<T> handler) {
        handler.onSerieRemoved(this);
    }

    // The instance knows its BeforeSelectionHandler is of type I, but the TYPE
    // field itself does not, so we have to do an unsafe cast here.
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public final Type<SerieRemovedHandler<T>> getAssociatedType() {
        return (Type) TYPE;
    }

    public static Type<SerieRemovedHandler<?>> getType() {
        return TYPE;
    }

    public static <T> void fire(final HasHandlers source, final Serie<T> serie) {
        source.fireEvent(new SerieRemovedEvent<T>(serie));
    }
}
