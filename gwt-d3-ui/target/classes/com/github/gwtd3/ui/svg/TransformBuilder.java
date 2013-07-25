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

import com.google.common.base.Preconditions;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class TransformBuilder extends TransformList {

    private final HasTransform widget;

    /**
	 * 
	 */
    private TransformBuilder(final HasTransform widget) {
        this.widget = widget;
        String transformAttribute = widget.asWidget().getElement().getAttribute("transform");
        parse(this, transformAttribute);
    }

    /**
     * Parse the transform attribute of the selection
     * into a stack of {@link Transform}.
     */
    public static TransformBuilder parse(final HasTransform widget) {
        Preconditions.checkNotNull(widget);
        return new TransformBuilder(widget);
    }

    /**
     * Set the stack of transforms into the transform
     * attribute of the selection
     */
    private void applyTransform() {
        widget.asWidget().getElement().setAttribute("transform", toString());
    }

    /**
     * Overriden to call {@link #applyTransform()} each time a new transform is pushed.
     * 
     * @see com.github.gwtd3.ui.svg.TransformList#push(com.github.gwtd3.ui.svg.Transform)
     */
    @Override
    public void push(final Transform transform) {
        super.push(transform);
        applyTransform();
    }

    /**
     * Push a translation by x and y
     * 
     * @param x
     * @param y
     * @return this instance
     */
    public TransformBuilder translate(final int x, final int y) {
        push(Transform.translate(x, y));
        return this;

    }

    /**
     * Push a translation by x and 0.
     * 
     * @param x
     *            the x
     * @return this instance
     */
    public TransformBuilder translate(final int x) {
        push(Transform.translate(x));
        return this;
    }

    /**
     * Push a scale operation by x and y
     * 
     * @param x
     * @param y
     * @return this instance
     */
    public TransformBuilder scale(final int x, final int y) {
        push(Transform.scale(x, y));
        return this;

    }

    /**
     * Push a scale operation by x and by y = x.
     * 
     * @param x
     *            the x
     * @return this instance
     */
    public TransformBuilder scale(final int x) {
        push(Transform.scale(x));
        return this;
    }

    /**
     * Push a rotation of angle degrees around the point (0,0).
     * 
     * @param angle
     *            in degrees
     * @return this instance
     */
    public TransformBuilder rotate(final double angle) {
        push(Transform.rotate(angle));
        return this;
    }

    /**
     * Push a rotation of angle degrees around the point (cx,cy).
     * 
     * @param angle
     *            in degrees
     * @param cx
     *            the x of the center
     * @param cy
     *            the y of the center
     * @return
     */
    public TransformBuilder rotate(final double angle, final int cx, final int cy) {
        push(Transform.rotate(angle, cx, cy));
        return this;
    }

    public TransformBuilder removeAll() {
        clear();
        return this;
    }
}
