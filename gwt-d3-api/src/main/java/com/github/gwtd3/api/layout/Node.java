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
package com.github.gwtd3.api.layout;

import com.github.gwtd3.api.arrays.Array;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * A node used in a HierarchyLayout
 */
public class Node<T>
        extends JavaScriptObject {
    /**
     * Create a new node containing the given user datum
     *
     * @param userDatum
     * @return
     */
    static final native <T> Node<T> create(T userDatum)/*-{
		return {
			datum : userDatum
		};
    }-*/;

    protected Node() {
        super();
    }

    /**
     * @return array of {@link Node} objects or null
     */
    public final native Array<Node<T>> children() /*-{
		return this.children;
    }-*/;

    /**
     * @return parent node
     */
    public final native Node<T> parent() /*-{
		return this.parent;
    }-*/;

    /**
     * @return the node's depth, root depth = 0
     */
    public final native int depth() /*-{
		return this.depth;
    }-*/;

    /**
     * @return the user datum of this node
     */
    public final native T datum()/*-{
		return this.datum;
    }-*/;

    /**
     * @param datum the user datum of this node
     */
    public final native void datum(final T datum)/*-{
		this.datum = datum;
    }-*/;
}
