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
package com.github.gwtd3.api.functions;

import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.UpdateSelection;
import com.github.gwtd3.api.core.Value;
import com.google.gwt.dom.client.Element;

/**
 * A function used to control how data is joined to elements.
 * <p>
 * Single argument {@link Selection#data} methods use a default by-index behavior to map data with elements. This interface can be passed to
 * {@link Selection#data(com.google.gwt.core.client.JavaScriptObject, KeyFunction)} and variants to map data to elements using a different criteria.
 * <p>
 * The {@link #apply(Element, Datum, int)} method will be called once for each element in the new data array, and once again for each existing element in the selection. The result
 * will be used to map the passed data to the passed element.
 * <p>
 * 
 * @see #apply(Element, Datum, int)
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public interface KeyFunction<T> {

    /**
     * Invoked once for each datum in the new data array (A),
     * then once for each element in the current selection (B).
     * <p>
	 * The returned value is used as a key to map the new data array elements to existing (or future) elements of the selection:
     * <ul>
	 * <li>when the value returned by A matches a return value of B, the new datum is associated to the corresponding element
	 * <li>when the value returned by A does not match any return value of B, the new datum is associated to a placeholder for a new element ( the entering selection) and can be
	 * retrieved via {@link UpdateSelection#enter()}
	 * <li>when the value returned by B does not match any return value of A, the element is put in the exiting selection and can be retrieved using {@link UpdateSelection#exit()}.
     * </ul>
     * <p>
     * 
	 * @see Selection#data(com.google.gwt.core.client.JavaScriptObject, KeyFunction)
	 * @param context
	 *            the current element when this method is invoked for the existing elements of the selection,
	 *            or null when this method is invoked for the new data array
	 * @param newDataArray
	 *            the new data array when this method is invoked for the elements in the new data array,
	 *            or null when this method is invoked for the existing elements of the selection.
	 * @param datum
	 *            the datum element of the new data array, or the datum of the current element context
	 * @param index
	 *            the index of the datum in the new data array, or the index of the element in the selection
	 * 
     * @return a key to be used to map new datum to existing elements.
     */
	public T map(Element context, Array<?> newDataArray, Value datum, int index);
}
