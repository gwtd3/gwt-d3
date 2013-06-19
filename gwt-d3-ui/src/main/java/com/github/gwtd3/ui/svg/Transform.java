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

/**
 * Wrap a SVG transformation to be used in a {@link TransformList} to be used in a {@link HasTransform} widget.
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Transform {
    /**
	 * 
	 */
    private static final String TRANSLATE = "translate";
    private static final String SCALE = "scale";
    private static final String ROTATE = "rotate";
    final String command;

    protected Transform(final String command) {
        super();
        this.command = command;
    }

    public boolean isTranslate() {
        return command.startsWith(Transform.TRANSLATE);
    }

    public boolean isRotate() {
        return command.startsWith(Transform.ROTATE);
    }

    public boolean isScale() {
        return command.startsWith(Transform.SCALE);
    }

    /**
     * Create a translation by x and y
     * 
     * @param x
     * @param y
     * @return the {@link Transform}.
     */
    public static Transform translate(final int x, final int y) {
        return new Transform(Transform.TRANSLATE + "(" + x + "," + y + ")");
    }

    /**
     * Create a translation by x and 0.
     * 
     * @param x
     *            the x
     * @return the {@link Transform}
     */
    public static Transform translate(final int x) {
        return new Transform(Transform.TRANSLATE + "(" + x + ")");
    }

    /**
     * Create a scale operation by x and y
     * 
     * @param x
     * @param y
     * @return the {@link Transform}.
     */
    public static Transform scale(final int x, final int y) {
        return new Transform(Transform.SCALE + "(" + x + "," + y + ")");
    }

    /**
     * Create a scale operation by x and by y = x.
     * 
     * @param x
     *            the x
     * @return the {@link Transform}
     */
    public static Transform scale(final int x) {
        return new Transform(Transform.SCALE + "(" + x + ")");
    }

    /**
     * Create a rotation of angle degrees around the point (0,0).
     * 
     * @param angle
     *            in degrees
     * @return the {@link Transform}
     */
    public static Transform rotate(final double angle) {
        return new Transform(Transform.ROTATE + "(" + angle + ")");
    }

    /**
     * Create a rotation of angle degrees around the point (cx,cy).
     * 
     * @param angle
     *            in degrees
     * @param cx
     *            the x of the center
     * @param cy
     *            the y of the center
     * @return
     */
    public static Transform rotate(final double angle, final int cx, final int cy) {
        return new Transform(Transform.ROTATE + "(" + angle + "," + cx + "," + cy + ")");
    }
}