package com.github.gwtd3.ui.data;

import com.github.gwtd3.api.core.Selection;

/**
 * Base implementation of {@link SelectionUpdater} that always append new elements and always remove old elements.
 * <p>
 * 
 * @author SCHIOCA
 * 
 */
public abstract class DefaultSelectionUpdater<T> implements SelectionUpdater<T> {

    private final String selector;

    /**
     * Specify the selector that will be used to select the right elements
     * in the parent.
     * Note: class or id selectors are advised compare to tag names
     * @param selector
     */
    public DefaultSelectionUpdater(final String selector) {
        super();
        this.selector = selector;
    }

    @Override
    public String getSelector() {
        return selector;
    }

    @Override
    public boolean beforeEnter(final Selection selection) {
        return true;
    }

    @Override
    public void afterEnter(final Selection selection) {}

    @Override
    public boolean beforeExit(final Selection selection) {
        return true;
    }

    @Override
    public void afterExit(final Selection selection) {}

    @Override
    public void onJoinStart(final Selection selection) {}

    @Override
    public void onJoinEnd(final Selection selection) {

    }

    @Override
    public String getKey(final T datum, final int index) {
        return Integer.toString(index);
    }

    public static final void debugSelection(final String message, final Selection selection) {
        System.out.println(message + ": " + selection.count());
    }

}
