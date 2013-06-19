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

import com.google.gwt.user.client.ui.HasText;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Text extends SVGWidget implements HasText, HasTransform {

    private String text;

    public Text() {
        this("");
    }

    public Text(final String text) {
        super("text");
        this.text = text;
    }

    /**
     * @param text
     *            the text to set
     */
    @Override
    public void setText(final String text) {
        this.text = text;
        scheduleRedraw();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gwt.user.client.ui.HasText#getText()
     */
    @Override
    public String getText() {
        return text;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.github.gwtd3.ui.svg.HasTransform#transform()
     */
    @Override
    public TransformBuilder transform() {
        return TransformBuilder.parse(this);
    }

    /**
     * @param x
     */
    public void x(final String x) {
        this.getElement().setAttribute("x", x);
    }

    /**
     * @param y
     */
    public void y(final String y) {
        this.getElement().setAttribute("y", y);
    }

    /**
     * @param dx
     */
    public void dx(final String dx) {
        this.getElement().setAttribute("dx", dx);
    }

    /**
     * @param dy
     */
    public void dy(final String dy) {
        this.getElement().setAttribute("dy", dy);
    }

    @Override
    public void redraw() {
        super.redraw();

        select().text(text);
    }

}
