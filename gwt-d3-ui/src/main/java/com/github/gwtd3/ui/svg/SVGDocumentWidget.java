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
package com.github.gwtd3.ui.svg;

import org.vectomatic.dom.svg.OMSVGRect;
import org.vectomatic.dom.svg.impl.SVGSVGElement;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.core.Selection;
import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.CssResource;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class SVGDocumentWidget extends SVGWidget implements ISVGDocument {

    private Element defs;
    protected SVGResources resources;
    protected SVGStyles styles;

    /**
     * @param e
     */
    protected SVGDocumentWidget() {
        super("svg");
    }

    /**
     * @param e
     */
    protected SVGDocumentWidget(final SVGResources resources) {
        super("svg");
        this.resources = resources;
        this.styles = resources.svgStyles();
        this.addStyleName(styles.svg());
    }

    @Override
    protected void onSelectionAttached(final Selection selection) {
        super.onSelectionAttached(selection);
        if (styles != null) {
            inject(styles);
        }
    }

    /**
     * @return a D3 selection representing the &lt;defs&gt; element.
     */
    @Override
    public Selection defs() {
        if (defs == null) {
            defs = getElement().insertFirst(createSVGElement("defs")).cast();
        }
        return D3.select(defs);
    }

    /**
     * Inject the given stylesheet as a new <code>style</code> element
     * in the <code>defs</code> of this document.
     * 
     * @param resource
     *            the css stylesheet to inject
     */
    @Override
    public void inject(final CssResource resource) {
        // String styleContents = "\n" + resource.getText() + "\n";
        // defs().append("style").attr("type", "text/css").text(styleContents);
        resource.ensureInjected();
    }

    public SVGSVGElement getSVGElement() {
        return getElement().cast();
    }

    @Override
    public void setViewBox(final int x, final int y, final int width, final int height) {
        OMSVGRect viewBox = getSVGElement().getViewBox().getBaseVal();
        viewBox.setX(x);
        viewBox.setY(y);
        viewBox.setWidth(width);
        viewBox.setHeight(height);
    }

}
