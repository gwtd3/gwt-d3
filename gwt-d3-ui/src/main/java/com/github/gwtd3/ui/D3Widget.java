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
package com.github.gwtd3.ui;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.ui.svg.DrawableSupport;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

/**
 * Base class for widget that use {@link D3}.
 * <p>
 * A d3 {@link Selection} containing the element backed by this widget can be obtain with the {@link #select()} method.
 * <p>
 * Subclasses of {@link D3Widget} should initialize the selection using the {@link #onSelectionAttached(Selection)}
 * methods.
 * <p>
 * 
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class D3Widget extends Widget implements HasD3Selection, DrawableWidget {

    private final DrawableSupport support = new DrawableSupport(this);

    public D3Widget(final String tagName) {
        this(DOM.createElement(tagName));
    }

    public D3Widget(final Element element) {
        super();
        setElement(element);
    }

    /**
     * Returns a new d3 selection representing
     * the {@link Element} of this widget.
     * <p>
     * 
     * @return the selection
     */
    @Override
    public Selection select() {
        if (!isAttached()) {
            // throw new IllegalStateException("Cannot get the selection of a widget that is not attached.");
        }
        return D3.select(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gwt.user.client.ui.Widget#onLoad()
     */
    @Override
    protected void onLoad() {
        super.onLoad();
        onSelectionAttached(select());
    }

    /**
     * Call once the selection is created,
     * i.e once the element wrapping the selection
     * is attached to the DOM.
     * <p>
     * This method is the right place to initialize the selection.
     * 
     * 
     * @param selection
     *            the selection
     */
    protected void onSelectionAttached(final Selection selection) {

    }

    @Override
    public void redraw() {}

    @Override
    public void scheduleRedraw() {
        support.scheduleRedraw();
    }

}
