/**
 * 
 */
package com.github.gwtd3.api.core;

/**
 * The result of the {@link Selection#data} methods. This represents the selected DOM elements that were successfully
 * bound to the specified data elements.
 * <p>
 * The update selection also contains a reference to the {@link #enter()} and {@link #exit()} selections, for adding and
 * removing nodes in correspondence with data. For example, if the default by-index key is used, and the existing
 * selection contains fewer elements than the specified data, then the enter selection will contain placeholders for the
 * new data. On the other hand, if the existing contains more elements than the data, then the exit selection will
 * contain the extra elements. And, if the existing selection exactly matches the data, then both the enter and exit
 * selections will be empty, with all nodes in the update selection.
 * <p>
 * For more details, see the short tutorial <a href="http://bost.ocks.org/mike/join/">Thinking With Joins</a>.
 * <p>
 * If a key function is specified, the data operator also affects the index of nodes; this index is passed as the second
 * argument i to any operator function values. However, note that existing DOM elements are not automatically reordered;
 * use sort or order as needed.
 * 
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class UpdateSelection extends Selection {

    protected UpdateSelection() {};

    /**
     * Returns the {@link EnteringSelection}:
     * placeholder nodes for each data element for which no corresponding existing DOM element was found in the current
     * selection.
     * 
     * @see EnteringSelection
     * @return the entering selection
     */
    public final native EnteringSelection enter()/*-{
		return this.enter();
    }-*/;

    /**
     * Returns the exiting selection: existing DOM elements in the current selection for which no new data element was
     * found.
     * <p>
     * The exiting selection defines all the normal operators, though typically the main one you'll want to use is
     * {@link Selection#remove()}; the other operators exist primarily so you can define an exiting transition as
     * desired.
     * <p>
     * Note that the exit operator merely returns a reference to the exiting selection, and it is up to you to remove
     * the new nodes.
     * 
     * @return the exiting selection
     */
    public final native Selection exit()/*-{
		return this.exit();
    }-*/;
}
