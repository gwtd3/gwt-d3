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
package com.github.gwtd3.api.svg;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Provide access to svg routines.
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class SVG extends JavaScriptObject {

    protected SVG() {
    }

    /**
     * Create a new arc generator with default accessor functions. See
     * {@link Arc} for details.
     * 
     * @return the arc
     */
    public final native Arc arc()/*-{
		return this.arc();
    }-*/;

    /**
     * Create a new default {@link Area} generator.
     * 
     * @return the area
     */
    public final native Area area()/*-{
		return this.area();
    }-*/;

    /**
     * Create a new default {@link Axis} generator.
     * 
     * @return the axis
     */
    public final native Axis axis()/*-{
		return this.axis();
    }-*/;

    /**
     * Create a new default {@link Brush} generator.
     * 
     * @return the brush
     */
    public final native Brush brush() /*-{
		return this.brush();
    }-*/;

    /**
     * Create a new default {@link Chord} generator.
     * 
     * @return the chord
     */
    public final native Chord chord() /*-{
		return this.chord();
    }-*/;

    /**
     * Create a new default {@link Diagonal} generator.
     * 
     * @return the diagonal
     */
    public final native Diagonal diagonal() /*-{
		return this.diagonal();
    }-*/;

    /**
     * Create a new default {@link Line} generator.
     * 
     * @return the line
     */
    public final native Line line()/*-{
		return this.line();
    }-*/;

    /**
     * Constructs a new radial line generator with the default radius- and
     * angle-accessor functions (that assume the input data is a two-element
     * array of numbers; see below for details), and linear interpolation.
     * <p>
     * The returned function generates path data for an open piecewise linear
     * curve, or polyline, as with the Cartesian line generator.
     * <p>
     * 
     * @return the {@link RadialLine}.
     */
    public final native RadialLine radialLine()/*-{
		return this.line.radial();
    }-*/;

    /**
     * Create a new default {@link Symbol} generator.
     * 
     * @return the symbol
     */
    public final native Symbol symbol() /*-{
		return this.symbol();
    }-*/;

    /**
     * Create a new default {@link Diagonal} generator expressed in polar
     * coordinates.
     * 
     * @return the diagonal
     */
    public final native Diagonal radialDiagonal() /*-{
		return this.diagonal();
    }-*/;
}
