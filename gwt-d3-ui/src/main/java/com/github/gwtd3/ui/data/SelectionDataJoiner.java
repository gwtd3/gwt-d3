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
package com.github.gwtd3.ui.data;

import java.util.List;

import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.UpdateSelection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.KeyFunction;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;

/**
 * Wraps the generic algorithm of joining data to a selection, easing the
 * process of creating and removing new elements.
 * <p>
 * Usage : create a SelectionUpdater and call the {@link #update(Selection, List, SelectionUpdater)} method providing data.
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
			public String map(final Element context, final Array<?> newDataArray, final Value d, final int index) {
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
