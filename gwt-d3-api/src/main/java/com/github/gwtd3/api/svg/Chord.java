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
package com.github.gwtd3.api.svg;

import com.github.gwtd3.api.functions.DatumFunction;

/**
 * Constructs a new chord generator with the default accessor functions (that
 * assume the input data is an object with named attributes matching the
 * accessors; see below for details).
 * <p>
 * While the default accessors assume that the chord dimensions are all
 * specified dynamically, it is very common to set one or more of the dimensions
 * as a constant, such as the radius. The returned function generates path data
 * for a closed shape connecting two arcs with quadratic Bézier curves, as in a
 * chord diagram:
 * 
 * <p>
 * <img src="https://github.com/mbostock/d3/wiki/chord.png">
 * <p>
 * 
 * A chord generator is often used in conjunction with an {@link Arc} generator,
 * so as to draw annular segments at the start and end of the chords. In
 * addition, the {@link com.github.gwtd3.api.layout.Chord} layout is useful for
 * generating objects that describe a set of grouped chords from a matrix,
 * compatible with the default accessors.
 * 
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Chord extends PathDataGenerator {
    protected Chord() {
	super();
    }

    /**
     * Set the source accessor.
     * <p>
     * The purpose of the source accessor is to return an object that describes
     * the starting arc of the chord. The returned object is subsequently passed
     * to the {@link #radius(DatumFunction)}, {@link #startAngle(DatumFunction)}
     * and {@link #endAngle(DatumFunction)} accessors.
     * <p>
     * This allows these other accessors to be reused for both the source and
     * target arc descriptions.
     * <p>
     * The default accessor assumes that the input data is a JavaScriptObject
     * with suitably-named attributes.
     * <p>
     * The source-accessor is invoked in the same manner as other value
     * functions in D3.
     * <p>
     * 
     * @param accessor
     *            the function returning the source arc object
     * @return the current chord generator
     */
    public final native Chord source(final DatumFunction<?> accessor)/*-{
		return this
				.source(function(d, i) {
					return accessor.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
    }-*/;

    /**
     * Set the target accessor.
     * <p>
     * The purpose of the target accessor is to return an object that describes
     * the ending arc of the chord. The returned object is subsequently passed
     * to the {@link #radius(DatumFunction)}, {@link #startAngle(DatumFunction)}
     * and {@link #endAngle(DatumFunction)} accessors.
     * <p>
     * This allows these other accessors to be reused for both the source and
     * target arc descriptions.
     * <p>
     * The default accessor assumes that the input data is a JavaScriptObject
     * with suitably-named attributes.
     * <p>
     * The target-accessor is invoked in the same manner as other value
     * functions in D3.
     * <p>
     * 
     * @param accessor
     *            the function returning the target arc object
     * @return the current chord generator
     */
    public final native Chord target(final DatumFunction<?> accessor)/*-{
		return this
				.target(function(d, i) {
					return accessor.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
    }-*/;

    /**
     * Set the radius accessor. The accessor will be invoked passing the source
     * or target in the value parameter. The accessor must return the radius.
     * <p>
     * The default accessor assumes that the input source or target description
     * is a JavaScriptObject with suitably-named attributes.
     * <p>
     * 
     * @param accessor
     *            the function returning the radius
     * @return the current chord generator
     */
    public final native Chord radius(final DatumFunction<Double> accessor)/*-{
		return this
				.radius(function(d, i) {
					return accessor.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
    }-*/;

    /**
     * Set the radius as a constant.
     * <p>
     * 
     * @param radius
     *            the radius
     * @return the current chord generator
     */
    public final native Chord radius(final double radius)/*-{
		return this.radius(radius);
    }-*/;

    /**
     * Set the start angle accessor. The accessor will be invoked passing the
     * source or target in the value parameter. The accessor must return the
     * start angle in radians.
     * <p>
     * Angles are specified in radians, even though SVG typically uses degrees.
     * <p>
     * The default accessor assumes that the input source or target description
     * is a JavaScriptObject with suitably-named attributes.
     * <p>
     * 
     * @param accessor
     *            the function returning the start angle
     * @return the current chord generator
     */
    public final native Chord startAngle(final DatumFunction<Double> accessor)/*-{
		return this
				.startAngle(function(d, i) {
					return accessor.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
    }-*/;

    /**
     * Set the start angle as a constant in radians.
     * <p>
     * Angles are specified in radians, even though SVG typically uses degrees.
     * <p>
     * 
     * @param startAngle
     *            the angle in radians
     * @return the current chord generator
     */
    public final native Chord startAngle(final double startAngle)/*-{
		return this.startAngle(startAngle);
    }-*/;

    /**
     * Set the end angle accessor. The accessor will be invoked passing the
     * source or target in the value parameter. The accessor must return the end
     * angle in radians.
     * <p>
     * Angles are specified in radians, even though SVG typically uses degrees.
     * <p>
     * The default accessor assumes that the input source or target description
     * is a JavaScriptObject with suitably-named attributes.
     * <p>
     * 
     * @param accessor
     *            the function returning the end angle
     * @return the current chord generator
     */
    public final native Chord endAngle(final DatumFunction<Double> accessor)/*-{
		return this
				.endAngle(function(d, i) {
					return accessor.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
    }-*/;

    /**
     * Set the end angle as a constant in radians.
     * <p>
     * Angles are specified in radians, even though SVG typically uses degrees.
     * <p>
     * 
     * @param endAngle
     *            the angle in radians
     * @return the current chord generator
     */
    public final native Chord endAngle(final double endAngle)/*-{
		return this.endAngle(endAngle);
    }-*/;
}
