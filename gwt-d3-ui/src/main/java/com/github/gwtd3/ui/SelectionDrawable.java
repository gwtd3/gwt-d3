package com.github.gwtd3.ui;

import com.github.gwtd3.api.core.Selection;

/**
 * A component that can draw in any provided {@link Selection}.
 * 
 * @author SCHIOCA
 * 
 */
public interface SelectionDrawable {

    /**
     * Draw in any selection.
     * 
     * @param selection
     */
    public void redraw(Selection selection);
}
