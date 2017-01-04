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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.resources.client.impl.ImageResourcePrototype.Bundle;

public class Layout extends JavaScriptObject {
    protected Layout() {
        super();
    }

    /**
     * Creates a new bundle layout with the default settings.
     * <p>
     * 
     * @return the new bundle layout
     */
    public final native Bundle bundle() /*-{
		return this.bundle();
    }-*/;

    public final native Chord chord() /*-{
		return this.chord();
    }-*/;

    /**
     * Creates a new tree layout with the default settings: the default sort
     * order is null; the default children accessor assumes each input data is
     * an object with a children array; the default separation function uses one
     * node width for siblings, and two node widths for non-siblings; the
     * default size is 1×1.
     *
     * @return the tree layout object
     */
    public final native <T> Tree<T> tree() /*-{
		return this.tree();
    }-*/;

    /**
     * Creates a new cluster layout with the default settings: the default sort
     * order is null; the default children accessor assumes each input data is
     * an object with a children array; the default separation function uses one
     * node width for siblings, and two node widths for non-siblings; the
     * default size is 1×1.
     *
     * @return the new cluster generator
     */
    public final native <T> Cluster<T> cluster()/*-{
		return this.cluster();
    }-*/;

    /**
     * Constructs a new force-directed layout with the default settings: size
     * 1×1, link strength 1, friction 0.9, distance 20, charge strength -30,
     * gravity strength 0.1, and theta parameter 0.8. The default nodes and
     * links are the empty array, and when the layout is started, the internal
     * alpha cooling parameter is set to 0.1. The general pattern for
     * constructing force-directed layouts is to set all the configuration
     * properties, and then call start():
     *
     * @return the new force layout object
     */
    public final native Force force()/*-{
		return this.force();
    }-*/;
}
