package com.github.gwtd3.ui.data;

import java.util.List;

import com.github.gwtd3.api.core.Datum;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.UpdateSelection;
import com.github.gwtd3.api.functions.KeyFunction;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;

/**
 * Wraps the generic algorithm of joining data to a selection, easing the
 * process of creating and removing new elements.
 * <p>
 * Usage : create a SelectionUpdater and call the {@link #update(Selection, List, SelectionUpdater)} method providing
 * data.
 * 
 * FIXME: handle nested selections case
 * 
 * 
 * 
 * @author SCHIOCA
 * 
 * @param <T>
 *            the type of values joined to selection
 */
public class SelectionDataJoiner {

    private SelectionDataJoiner() {

    }

    /**
     * Update the given selection with the specified data, using the provided
     * updater.
     * <p>
     * The update pattern used is :
     * <ol>
     * <li>join data
     * <li>enter
     * <li>exit
     * <li>end
     * </ol>
     * 
     * @param parent
     *            the parent selection
     * @param data
     *            the data
     * @param updater
     *            the updater
     */
    public static <T> UpdateSelection update(final Selection parent, final List<T> data,
            final SelectionUpdater<T> updater) {
        // first create a selection for all child elements
        Selection child = parent.selectAll(updater.getSelector());
        // join data
        GWT.log("DATA: joining " + data.size() + " elements ");
        UpdateSelection updateSelection = child.data(data, new KeyFunction<String>() {
            @Override
            public String apply(final Element context, final Datum d, final int index) {
                return updater.getKey(d.<T> as(), index);
            }
        });

        updater.onJoinStart(updateSelection);
        // enter
        boolean shouldEnter = updater.beforeEnter(updateSelection);
        if (shouldEnter) {
            // (new elements)
            Selection newElements = updateSelection.enter().append(updater.getElementName());
            updater.afterEnter(newElements);
        }
        // update (existing + new + stales)

        boolean shouldExit = updater.beforeExit(updateSelection);
        // exit: remove states elements
        if (shouldExit) {
            Selection staleElements = updateSelection.exit();
            updater.afterExit(staleElements);
        }

        // final update
        updater.onJoinEnd(updateSelection);

        return updateSelection;
    }

}
