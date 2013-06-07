package com.github.gwtd3.ui.data;

import java.util.List;

import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.UpdateSelection;

/**
 * Wraps the generic algorithm of joining data to a selection,
 * easing the process of creating and removing new elements.
 * <p>
 * Usage : create a SelectionUpdater and call the FIXME: handle nested selections case
 * 
 * 
 * @author SCHIOCA
 * 
 */
public class GenericUpdatePattern {

    private final SelectionUpdater updater;

    public GenericUpdatePattern(final SelectionUpdater updater) {
        super();
        this.updater = updater;
    }

    public void redraw(final Selection parent, final List<?> data) {
        // first create a selection for all child elements
        Selection child = parent.selectAll(updater.getSelector());
        // join data
        UpdateSelection updateSelection = child.data(data);

        updater.onJoinStart(updateSelection);
        // enter
        boolean shouldEnter = updater.beforeEnter(updateSelection);
        if (shouldEnter) {
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

    }

}
