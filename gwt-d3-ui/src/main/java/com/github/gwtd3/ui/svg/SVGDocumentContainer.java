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
package com.github.gwtd3.ui.svg;

import org.vectomatic.dom.svg.OMSVGAnimatedLength;
import org.vectomatic.dom.svg.OMSVGLength;
import org.vectomatic.dom.svg.OMSVGRect;
import org.vectomatic.dom.svg.impl.SVGSVGElement;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.core.Selection;
import com.google.common.base.Preconditions;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.CssResource;

/**
 * A widget serving as a container for SVG graphics.
 * <p>
 * Use this widget when you want to create SVG graphics from outside of the widget itself, for instance.
 * <p>
 * FIXME: % sizing does not allow to update the widget
 * <p>
 * FIXME: cannot get viewport size...
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class SVGDocumentContainer extends SVGContainer implements ISVGDocument {

    protected SVGResources resources;
    protected SVGStyles styles;
    private Element defs;

    public SVGDocumentContainer() {
        this((SVGResources) GWT.create(SVGResources.class));

    }

    public SVGDocumentContainer(final SVGResources resources) {
        super("svg");
        Preconditions.checkNotNull(resources);
        this.resources = resources;
        this.styles = this.resources.svgStyles();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.github.gwtd3.ui.HasD3Selection#select()
     */
    @Override
    public Selection select() {
        return D3.select(this);
    }

    public SVGSVGElement getSVGElement() {
        return getElement().cast();
    }

    @Override
    public void setWidth(final String width) {
        getElement().setAttribute("width", width);
        scheduleRedraw();
    }

    @Override
    public void setHeight(final String height) {
        getElement().setAttribute("height", height);
        scheduleRedraw();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.github.gwtd3.ui.D3Widget#onSelectionAttached(com.github.gwtd3.api.core.Selection)
     */
    @Override
    protected void onSelectionAttached() {
        super.onSelectionAttached();
        if (resources != null) {
            inject(styles);
            select().classed(styles.svg(), true);
        }

    }

    // =============== DEFS ===============

    /**
     * @return a D3 selection representing the &lt;defs&gt; element.
     */
    @Override
    public Selection defs() {
        if (defs == null) {
            defs = getElement().insertFirst(DOM.createSVGElement("defs")).cast();
        }
        return D3.select(defs);
    }

    /**
     * Find an element with the given id in the &lt;defs&gt; element
     * 
     * @param id
     * @return
     */
    public Selection getDefById(final String id) {
        return defs().select("#" + id);
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

    // =============== SIZE attributes ==> TO BE REMOVED ===============
    @Override
    public void setViewBox(final int x, final int y, final int width, final int height) {
        OMSVGRect viewBox = getSVGElement().getViewBox().getBaseVal();
        viewBox.setX(x);
        viewBox.setY(y);
        viewBox.setWidth(width);
        viewBox.setHeight(height);

    }

    /**
     * @return the width attribute value of the svg element
     */
    public int getWidth() {
        OMSVGAnimatedLength width = getSVGElement().getWidth();
        if (width != null) {
            OMSVGLength baseVal = width.getBaseVal();
            return (int) baseVal.getValue();
        }
        return 0;
    }

    /**
     * @return the height attribute value of the svg element
     */
    public int getHeight() {
        OMSVGAnimatedLength height = getSVGElement().getHeight();
        if (height != null) {
            OMSVGLength baseVal = height.getBaseVal();
            return (int) baseVal.getValue();
        }
        return 0;
    }

}
