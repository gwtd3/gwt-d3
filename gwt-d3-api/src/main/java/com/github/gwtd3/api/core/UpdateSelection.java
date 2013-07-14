/**
 * Copyright (c) 2013, Anthony Schiochet and Eric Citaire
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * The names Anthony Schiochet and Eric Citaire may not be used to endorse or promote products
 *   derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL MICHAEL BOSTOCK BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY
 * OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
/**
 * 
 */
package com.github.gwtd3.api.core;

/**
 * The result of the {@link Selection#data} methods. This represents the selected DOM elements that were successfully
 * bound to the specified data elements.
 * <p>
 * The update selection also contains a reference to the {@link #enter()} and {@link #exit()} selections, for adding and removing nodes in correspondence with data. For example, if
 * the default by-index key is used, and the existing selection contains fewer elements than the specified data, then the enter selection will contain placeholders for the new
 * data. On the other hand, if the existing contains more elements than the data, then the exit selection will contain the extra elements. And, if the existing selection exactly
 * matches the data, then both the enter and exit selections will be empty, with all nodes in the update selection.
 * <p>
 * For more details, see the short tutorial <a href="http://bost.ocks.org/mike/join/">Thinking With Joins</a>.
 * <p>
 * If a key function is specified, the data operator also affects the index of nodes; this index is passed as the second argument i to any operator function values. However, note
 * that existing DOM elements are not automatically reordered; use {@link Selection#sort(java.util.Comparator)} or {@link Selection#order()} as needed.
 * 
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class UpdateSelection extends Selection {

	protected UpdateSelection() {
	};

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
	 * The exiting selection defines all the normal operators, though typically the main one you'll want to use is {@link Selection#remove()}; the other operators exist primarily
	 * so you can define an exiting transition as desired.
	 * <p>
	 * Note that the exit operator merely returns a reference to the exiting selection, and it is up to you to remove the new nodes.
	 * 
	 * @return the exiting selection
	 */
	public final native Selection exit()/*-{
		return this.exit();
	}-*/;
}
