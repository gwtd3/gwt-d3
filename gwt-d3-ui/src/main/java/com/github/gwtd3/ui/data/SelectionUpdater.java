package com.github.gwtd3.ui.data;

import com.github.gwtd3.api.core.Selection;

/**
 * Represents the process of joining
 * new data to an existing selection.
 * <p>
 * Lifecycle:
 * <ol>
 * <li>onJointStart
 * <li>beforeEnter
 * <li>afterEnter
 * <li>beforeExit
 * <li>afterExit
 * <li>onJoinEnd
 * </ol>
 * 
 * @author SCHIOCA
 * 
 */
public interface SelectionUpdater {

    /**
     * Returns the selector used to create the update pattern.
     * Note that this method may return the same as {@link #getElementName()} but this should be avoided to ensure there
     * is not conflicts with siblings.
     * 
     * @return the selector
     */
    public String getSelector();

    /**
     * @return the element name to be appended
     */
    public String getElementName();

    /**
     * Allow the update of existing elements
     * before adding new elements.
     * <p>
     * If the method returns false, no elements will be appended.
     * 
     * @param selection the selection containing only existing elements.
     * @return a flag indicating if the new elements should or not be created
     */
    public boolean beforeEnter(Selection selection);

    /**
     * Return a selection to the nodes that were just appended,
     * letting the user a chance to configure the new nodes.
     * <p>
     * The selection may be empty if no nodes were appended.
     * <p>
     * 
     * @param selection the selection containing only the new appended nodes.
     */
    public void afterEnter(final Selection selection);

    /**
     * Indicates if the not existing nodes should be exited
     * or not.
     * The provided selection contains both existing, new and candidates to deletion nodes.
     * 
     * <p>
     * 
     * @param selection
     * @return
     */
    public boolean beforeExit(final Selection selection);

    /**
     * Return a selection to the existing nodes.
     * 
     * @param selection
     */
    public void afterExit(final Selection selection);

    /**
     * Called when the data are joined.
     * <p>
     * The selection contains all the data mapped to old data (stale elements), <br>
     * to new data (new elements) or to non changing data (unchanging elements).
     * <p>
     * 
     * @param updateSelection
     */
    public void onJoinStart(Selection selection);

    /**
     * @param selection
     */
    public void onJoinEnd(Selection selection);

}
