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
package com.github.gwtd3.api.core;

import com.github.gwtd3.api.interpolators.Interpolator;
import com.github.gwtd3.api.tweens.TweenFunction;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * 
 * A transition is a special type of selection where the operators apply
 * smoothly over time rather than instantaneously. You derive a transition from
 * a selection using the transition operator. While transitions generally
 * support the same operators as selections (such as attr and style), not all
 * operators are supported; for example, you must append elements before a
 * transition starts. A remove operator is provided for convenient removal of
 * elements when the transition ends.
 * <p>
 * Transitions may have per-element delays and durations, computed using functions of data similar to other operators.
 * This makes it easy to stagger a transition for different elements, either based on data or index. For example, you
 * can sort elements and then stagger the transition for better perception of element reordering during the transition.
 * For more details on these techniques, see "Animated Transitions in Statistical Data Graphics" by Heer & Robertson.
 * <p>
 * D3 has many built-in interpolators to simplify the transitioning of arbitrary values. For instance, you can
 * transition from the font string "500 12px sans-serif" to "300 42px sans-serif", and D3 will find the numbers embedded
 * within the string, interpolating both font size and weight automatically. You can even interpolate arbitrary nested
 * objects and arrays or SVG path data. D3 allows custom interpolators should you find the built-in ones insufficient,
 * using the attrTween and styleTween operators. D3's interpolators provide the basis for scales and can be used outside
 * of transitions; an interpolator is a function that maps a parametric value t in the domain [0,1] to a color, number
 * or arbitrary value.
 * <p>
 * Only one transition may be active on a given element at a given time. However, multiple transitions may be scheduled
 * on the same element; provided they are staggered in time, each transition will run in sequence. If a newer transition
 * runs on a given element, it implicitly cancels any older transitions, including any that were scheduled but not yet
 * run. This allows new transitions, such as those in response to a new user event, to supersede older transitions even
 * if those older transitions are staged or have staggered delays. Multi-stage transitions (transitions that are created
 * during the "end" event of an earlier transition) are considered the same "age" as the original transition; internally
 * this is tracked by monotonically-increasing unique IDs which are inherited when multi-stage transitions are created.
 * <p>
 * For more on transitions, read the Working with Transitions tutorial.
 * 
 * TODO:
 * <ul>
 * <li>duration function version
 * <li>ease
 * </ul>
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Transition extends Selection {

    protected Transition() {}

    /**
     * Specifies per-element duration in milliseconds of all elements. The
     * default duration is 250ms.
     * 
     * @param milliseconds
     *            the transition duration in milliseconds
     * @return the current transition
     */
    public native final Transition duration(int milliseconds)/*-{
		return this.duration(milliseconds);
    }-*/;

    // public native final Transition attrTween(String name, TweenInt tweenFunction)/*-{
    // return this.attrTween(name, function(d, i, a) {
    // var result =
    // tweenFunction.@com.gwtd3.api.core.TweenInt::tween(Lcom/google/gwt/dom/client/Element;III)(this,d,i,a);
    // return result;
    // });
    // }-*/;

    /**
     * Transitions the value of the attribute with the specified name according to the specified tween function.
     * <p>
     * The starting and ending value of the transition are determined by tweenFunction; the tween function is invoked
     * when the transition starts on each element, being passed the current DOM element, the datum d, the current index
     * i and the current attribute value a.
     * <p>
     * The return value of tween must be an interpolator: a function that maps a parametric value t in the domain [0,1]
     * to a color, number or arbitrary value.
     * 
     * @see <a href="https://github.com/mbostock/d3/wiki/Transitions#wiki-attrTween">Offical API</a>
     * 
     * @param name the name of the attribute to transition
     * @param tweenFunction the function used to create an interpolator
     */
    public native final Transition attrTween(String name, TweenFunction<?> tweenFunction)/*-{
		return this.attrTween(name, function(d, i, a) {
			var interpolator = tweenFunction.@com.github.gwtd3.api.tweens.TweenFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;ILcom/github/gwtd3/api/core/Value;)(this,{datum:d},i,{datum:a});
			return @com.github.gwtd3.api.core.Transition::trampolineInterpolator(Lcom/github/gwtd3/api/interpolators/Interpolator;)(interpolator);
		});
    }-*/;

    private static JavaScriptObject trampolineInterpolator(final Interpolator<?> interpolator) {
        return interpolator.asJSOFunction();
    }
    /**
     * @param name
     * @param tweenObject
     */
    // public native final Transition attrTween(String name, String s)/*-{
    // return this.attrTween(name, function(d, i, a) {
    //
    // return s;
    // });
    // }-*/;

}
