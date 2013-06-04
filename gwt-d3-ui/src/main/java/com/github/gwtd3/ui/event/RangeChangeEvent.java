package com.github.gwtd3.ui.event;

import com.google.common.collect.Range;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class RangeChangeEvent extends GwtEvent<RangeChangeEvent.RangeChangeHandler> {

    public static Type<RangeChangeHandler> TYPE = new Type<RangeChangeHandler>();
    private final Range<?> newRange;
    private final Range<?> oldRange;

    public interface RangeChangeHandler extends EventHandler {
        void onRangeChange(RangeChangeEvent event);
    }

    public interface RangeChangeHasHandlers extends HasHandlers {
        HandlerRegistration addRangeChangeHandler(RangeChangeHandler handler);
    }

    public RangeChangeEvent(final Range<?> newRange, final Range<?> oldRange) {
        this.newRange = newRange;
        this.oldRange = oldRange;
    }

    public Range<?> getNewRange() {
        return newRange;
    }

    public Range<?> getOldRange() {
        return oldRange;
    }

    @Override
    protected void dispatch(final RangeChangeHandler handler) {
        handler.onRangeChange(this);
    }

    @Override
    public Type<RangeChangeHandler> getAssociatedType() {
        return TYPE;
    }

    public static Type<RangeChangeHandler> getType() {
        return TYPE;
    }

    public static void fire(final HasHandlers source, final Range<?> newRange, final Range<?> oldRange) {
        source.fireEvent(new RangeChangeEvent(newRange, oldRange));
    }
}
