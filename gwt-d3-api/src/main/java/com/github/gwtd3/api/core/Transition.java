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

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.IsFunction;
import com.github.gwtd3.api.ease.Easing;
import com.github.gwtd3.api.ease.EasingFunction;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.interpolators.Interpolator;
import com.github.gwtd3.api.svg.PathDataGenerator;
import com.github.gwtd3.api.tweens.TweenFunction;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;

/**
 * 
 * A transition is a special type of selection where the operators apply
 * smoothly over time rather than instantaneously. You derive a transition from
 * a selection using the {@link Selection#transition()} operator. While
 * transitions generally support the same operators as selections (such as {@link #attr(String)} and
 * {@link #style(String)}), not all operators are
 * supported; for example, you must {@link #append(String)} elements before a
 * transition starts. A {@link #remove()} operator is provided for convenient
 * removal of elements when the transition ends.
 * <p>
 * Transitions may have per-element #delay and {@link #duration(DatumFunction)}, computed using functions of data
 * similar to other operators. This makes it easy to stagger a transition for different elements, either based on data
 * or index. For example, you can sort elements and then stagger the transition for better perception of element
 * reordering during the transition. For more details on these techniques, see
 * "Animated Transitions in Statistical Data Graphics" by Heer & Robertson.
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
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Transition
    extends JavaScriptObject {

    protected Transition() {
    }

    // ================ creating transitions ================================

    /**
     * Specifies the transition delay in milliseconds.
     * <p>
     * All elements are given the same delay. The default delay is 0.
     * <p>
     * 
     * @param milliseconds
     * the transition duration in milliseconds
     * @return the current transition
     */
    public native final Transition delay(int milliseconds)/*-{
		return this.delay(milliseconds);
    }-*/;

    /**
     * Specifies the transition delay of each selected element.
     * <p>
     * The function is evaluated for each selected element (in order), being passed the current datum d and the current
     * index i, with the this context as the current DOM element.
     * <p>
     * The function's return value is then used to set each element's delay. The default delay is 0.
     * <p>
     * Setting the delay to be a multiple of the index i is a convenient way to stagger transitions for elements. For
     * example, if you used a fixed duration of duration, and have n elements in the current selection, you can stagger
     * the transition over 2 * duration by saying:
     * <p>
     * 
     * <pre>
	 * {@code
	 * .delay(function(d, i) { return i / n * duration; })
	 * }
	 * </pre>
     * <p>
     * You may also compute the delay as a function of the data, thereby creating a data-driven animation.
     * 
     * @param milliseconds
     * the transition duration in milliseconds
     * @return the current transition
     */
    public native final Transition delay(DatumFunction<Integer> func)/*-{
		return this
				.delay(function(d, i) {
					return func.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
    }-*/;

    /**
     * Specifies transition duration in milliseconds of all elements. The
     * default duration is 250ms.
     * 
     * @param milliseconds
     * the transition duration in milliseconds
     * @return the current transition
     */
    public native final Transition duration(int milliseconds)/*-{
		return this.duration(milliseconds);
    }-*/;

    /**
     * Specifies per-element duration in milliseconds of all elements, using the
     * given function. The default duration is 250ms.
     * 
     * @param func
     * the function returning a transition duration in milliseconds
     * @return the current transition
     */
    public native final Transition duration(DatumFunction<Integer> func)/*-{
		return this
				.duration(function(d, i) {
					return func.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
    }-*/;

    /**
     * Specifies the transition {@link EasingFunction} to be used.
     * <p>
     * Built-in easing functions can be created using {@link Easing} factory or you may provide a custom implementation.
     * <p>
     * Note that it is not possible to customize the easing function per-element or per-attribute; however, if you use
     * the "linear" easing function, you can apply custom easing inside your interpolator using
     * {@link #attrTween(String, TweenFunction)} or {@link #styleTween()}
     * <p>
     * 
     * @param callback
     * the easing function
     * @return the current transition
     */
    public native final Transition ease(EasingFunction callback)/*-{
		var r = this
				.ease(function(t) {
					return @com.github.gwtd3.api.core.Transition::trampolineCallEase(Lcom/github/gwtd3/api/ease/EasingFunction;D)(callback,t);
				});
		return r;
    }-*/;

    /**
     * Trampoline function to allow a EasingFunction to be called from JSNI.
     * <p>
     * 
     * @param function
     * @param t
     * @return
     */
    private static final double trampolineCallEase(final EasingFunction function, final double t) {
        return function.ease(t);
    }

    // ==================== attr =================================

    /**
     * Transition the attribute with the specified name to the specified value
     * on all selected elements.
     * <p>
     * The starting value of the transition is the current attribute value, and the ending value is the specified value.
     * <p>
     * A check is performed to see if the ending value represents a color of the form /^(#|rgb\(|hsl\()/, or one of the
     * CSS named colors; if so, the starting value is coerced to an RGB color and
     * {@link D3#interpolateRgb(Color, Color)} is used. Otherwise, interpolateString is used, which interpolates numbers
     * embedded within strings.
     * <p>
     * null values are not supported because the interpolator would be undefined; if you want to remove the attribute
     * after the transition finishes, listen to the end event.
     * <p>
     * 
     * @param name
     * the name of the attribute
     * @param value
     * the new value to transition to, must not be null
     * @return the current transition
     */
    public native final <T> Transition attr(final String name, String value)
    /*-{
		return this.attr(name, value);
    }-*/;

    /**
     * See {@link #attr(String, String)}.
     * <p>
     * The startValue will be coerced to a number and interpolateNumber will be used to transition the attribute value.
     * <p>
     * 
     * @param name
     * @param value
     * @return the current transition
     */
    public native final Transition attr(final String name, double value)
    /*-{
		return this.attr(name, value);
    }-*/;

    /**
     * Transitions the attributes with the specified name to the value returned by the {@link PathDataGenerator} for
     * each selected element.
     * 
     * @param name
     * the name of the attribute
     * @param value
     * the {@link PathDataGenerator} used to compute the new value of the attribute
     * @return the current transition
     */
    public native final Transition attr(final String name, PathDataGenerator value) /*-{
		return this.attr(name, value);
    }-*/;

    /**
     * Transitions the attribute with the specified name to the value returned
     * by the specified function on all selected elements.
     * <p>
     * The function is evaluated for each selected element (in order), being passed the current datum d and the current
     * index i. The function's return value is then used to set each element's attribute.
     * <p>
     * Null values are not supported because the interpolator would be undefined; if you want to remove the attribute
     * after the transition finishes, listen to the end event.
     * <p>
     * An interpolator is selected automatically based on the ending value. If the ending value is a number, the
     * starting value is coerced to a number and interpolateNumber is used. If the ending value is a string, a check is
     * performed to see if the string represents a color of the form /^(#|rgb\(|hsl\()/, or one of the CSS named colors;
     * if so, the starting value is coerced to an RGB color and interpolateRgb is used. Otherwise, interpolateString is
     * used, which interpolates numbers embedded within strings.
     * <p>
     * 
     * @param name
     * the name of the attribute
     * @param callback
     * the function used to compute the new value of the attribute
     * @return the current transition
     */
    public native final Transition attr(final String name, final DatumFunction<?> callback)
    /*-{
		return this
				.attr(
						name,
						function(d, i) {
							return callback.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
						});
    }-*/;

    /**
     * Transitions the value of the attribute with the specified name according
     * to the specified tween function.
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
     * @param name
     * the name of the attribute to transition
     * @param tweenFunction
     * the function used to create an interpolator
     */
    public native final Transition attrTween(String name, TweenFunction<?> tweenFunction)/*-{
		return this
				.attrTween(
						name,
						function(d, i, a) {
							var interpolator = tweenFunction.@com.github.gwtd3.api.tweens.TweenFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;ILcom/github/gwtd3/api/core/Value;)(this,{datum:d},i,{datum:a});
							return @com.github.gwtd3.api.core.Transition::trampolineInterpolator(Lcom/github/gwtd3/api/interpolators/Interpolator;)(interpolator);
						});
    }-*/;

    // ================ style functions ================

    /**
     * Transitions the value of the CSS style property with the specified name
     * to the specified value.
     * <p>
     * The starting value of the transition is the current computed style property value, and the ending value is the
     * specified value.
     * <p>
     * All elements are transitioned to the same style property value; Null values are not supported because the
     * interpolator would be undefined; if you want to remove the style property after the transition finishes, listen
     * to the end event.
     * 
     * An interpolator is selected automatically based on the ending value. If the ending value is a number, the
     * starting value is coerced to a number and interpolateNumber is used. If the ending value is a string, a check is
     * performed to see if the string represents a color of the form /^(#|rgb\(|hsl\()/, or one of the CSS named colors;
     * if so, the starting value is coerced to an RGB color and interpolateRgb is used. Otherwise, interpolateString is
     * used, which interpolates numbers embedded within strings.
     * 
     * Note that the computed starting value may be different than the value that was previously set, particularly if
     * the style property was set using a shorthand property (such as the "font" style, which is shorthand for
     * "font-size", "font-face", etc.). Moreover, computed dimensions such as "font-size" and "line-height" are always
     * in pixels, so you should specify the ending value in pixels too if appropriate.
     * <p>
     * 
     * @param name
     * the name of the style, such as font-size
     * @param value
     * the ending value
     * @return the current transition
     */
    public native final Transition style(String name, String value) /*-{
		return this.style(name, value);
    }-*/;

    /**
     * See {@link Transition#style(String, T, boolean)}.
     * 
     * @param name
     * the name of the style, such as font-size
     * @param value
     * the ending value
     * @return the current transition
     */
    public native final Transition style(String name, double value) /*-{
		return this.style(name, value);
    }-*/;

    /**
     * See {@link Transition#style(String, T, boolean)}.
     * 
     * @param name
     * the name of the style, such as font-size
     * @param callback
     * the callback to be called
     * @return the current transition
     */
    public native final Transition style(String name, DatumFunction<?> callback) /*-{
		try {
			return this
					.style(
							name,
							function(d, i) {
								try {
									var r = callback.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
									return r;
								} catch (e) {
									alert(e);
									return null;
								}
							});
		} catch (e) {
			alert(e);
			return null;
		}
    }-*/;

    /**
     * Transitions the value of the CSS style property with the specified name
     * to the specified value.
     * <p>
     * The starting value of the transition is the current computed style property value, and the ending value is the
     * specified value.
     * <p>
     * The function is evaluated for each selected element (in order), being passed the current datum d and the current
     * index i, with the this context as the current DOM element.
     * <p>
     * The function's return value is then used to transition each element's style property. Null values are not
     * supported because the interpolator would be undefined; if you want to remove the style property after the
     * transition finishes, listen to the end event.
     * <p>
     * An interpolator is selected automatically based on the ending value. If the ending value is a number, the
     * starting value is coerced to a number and interpolateNumber is used. If the ending value is a string, a check is
     * performed to see if the string represents a color of the form /^(#|rgb\(|hsl\()/, or one of the CSS named colors;
     * if so, the starting value is coerced to an RGB color and interpolateRgb is used. Otherwise, interpolateString is
     * used, which interpolates numbers embedded within strings.
     * <p>
     * Note that the computed starting value may be different than the value that was previously set, particularly if
     * the style property was set using a shorthand property (such as the "font" style, which is shorthand for
     * "font-size", "font-face", etc.). Moreover, computed dimensions such as "font-size" and "line-height" are always
     * in pixels, so you should specify the ending value in pixels too if appropriate.
     * <p>
     * 
     * @param name
     * the name of the style to set
     * @param callback
     * the function to be called on each element and returning the
     * value of the style
     * @param important
     * true if the style value should be marked as !important, false
     * otherwise
     * @return the current selection
     */
    public native final Selection style(String name, DatumFunction<?> callback, boolean important)/*-{
		var imp = important ? 'important' : null;
		return this
				.style(
						name,
						function(d, i) {
							var r =
							callback.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)
								(this,{datum:d},i);
							return r?r.@java.lang.Object::toString()():null;
						}, imp);
	}-*/;

    /**
     * Transitions the value of the CSS style property with the specified name
     * according to the specified tween function.
     * <p>
     * The starting and ending value of the transition are determined by tweenFunction; the tween function is invoked
     * when the transition starts on each element, being passed the current DOM element, the datum d, the current index
     * i and the current attribute value a.
     * <p>
     * The return value of tween must be an interpolator: a function that maps a parametric value t in the domain [0,1]
     * to a color, number or arbitrary value.
     * <p>
     * 
     * @param name
     * the name of the style to set
     * @param tweenFunction
     * the function to be called on each element and returning the
     * value of the style
     * @param important
     * true if the style value should be marked as !important, false
     * otherwise
     * @return the current selection
     */
    public native final Selection styleTween(String name, TweenFunction<?> tweenFunction, boolean important)/*-{
		var imp = important ? 'important' : null;
		return this
				.styleTween(
						name,
						function(d, i, a) {
							var interpolator = tweenFunction.@com.github.gwtd3.api.tweens.TweenFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;ILcom/github/gwtd3/api/core/Value;)(this,{datum:d},i,{datum:a});
							return @com.github.gwtd3.api.core.Transition::trampolineInterpolator(Lcom/github/gwtd3/api/interpolators/Interpolator;)(interpolator);
						}, imp);
    }-*/;

    // ==================== text content ==========================
    /**
     * The text operator is based on the textContent property; setting the text
     * content will replace any existing child elements.
     * <p>
     * Set the text content to the specified value on all selected elements when the transition starts. All elements are
     * given the same text content; a null value will clear the content.
     * 
     * @param value
     * the new text value to set
     * @return the current transition
     */
    public native final <T> Transition text(String value)/*-{
		return this.text(value);
    }-*/;

    /**
     * The text operator is based on the textContent property; setting the text
     * content will replace any existing child elements.
     * 
     * Set the text content to the specified value on all selected elements when
     * the transition starts.
     * <p>
     * The function is evaluated for each selected element (in order), being passed the current datum d and the current
     * index i, with the this context as the current DOM element.
     * <p>
     * The function's return value is then used to set each element's text content.
     * <p>
     * A null value will clear the content.
     * <p>
     * 
     * @param callback
     * the function used to compute the new text property
     * @return the current transition
     */
    public native final Transition text(final DatumFunction<String> callback) /*-{
		return this
				.text(function(d, i) {
					return callback.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
    }-*/;

    /**
     * Registers a custom tween for the specified name.
     * <p>
     * When the transition starts, the specified factory function will be invoked for each selected element in the
     * transition, so as to compute the tween function. If the factory returns null, then the tween is not run on the
     * selected element.
     * <p>
     * This method is used internally by the attr and style tweens, and can be used to interpolate other document
     * content.
     * 
     * @param name
     * the name of the property to register
     * @param factory
     * the function returning an {@link Interpolator}
     * @return the current transition
     */
    public native final Transition tween(String name, DatumFunction<Interpolator<?>> factory)/*-{
		return this
				.tween(
						name,
						function(d, i) {
							return factory.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
						});
    }-*/;

    /**
     * Remove the selected elements at the end of a transition.
     * <p>
     * If a newer transition is scheduled on any of the selected elements, these elements will not be removed; however,
     * the "end" event will still be dispatched.
     * 
     * 
     * @return the current transition
     */
    public native final Transition remove()/*-{
		return this.remove();
    }-*/;

    // ====================== subtransition ====================

    /**
     * For each element in the current selection, selects the first descendant
     * element that matches the specified selector string.
     * <p>
     * If no element matches the specified selector for the current element, the element at the current index will be
     * null in the returned transition; operators (with the exception of {@link Selection#data}) automatically skip null
     * elements, thereby preserving the index of the existing selection.
     * <p>
     * If the current element has associated data, this data is inherited by the returned subselection, and
     * automatically bound to the newly selected elements.
     * <p>
     * If multiple elements match the selector, only the first matching element in document traversal order will be
     * selected.
     * <p>
     * In addition, the returned new transition inherits easing, duration and delay from the current transition.
     * 
     * @param selector
     * the selector
     * @return the returned transition, containing at most only one element,
     * inheriting duration, delay and ease from the current transition
     */
    public final native Transition select(String selector)/*-{
		return this.select(selector);
    }-*/;

    /**
     * For each element in the current transition, selects descendant elements
     * that match the specified selector string.
     * <p>
     * The returned selection is grouped by the ancestor node in the current selection. If no element matches the
     * specified selector for the current element, the group at the current index will be empty in the returned
     * selection.
     * <p>
     * The subselection does not inherit data from the current selection; however, if data was previously bound to the
     * selected elements, that data will be available to operators.
     * <p>
     * In addition, the returned new transition inherits easing, duration and delay from the current transition. The
     * duration and delay for each subelement is inherited from the duration and delay of the parent element in the
     * current transition.
     * <p>
     * 
     * @param selector
     * @return the sub transition, inheriting
     */
    public final native Transition selectAll(String selector)/*-{
		return this.selectAll(selector);
    }-*/;

    // ================ filter ======================

    /**
     * Filters the transition, returning a new transition that contains only the
     * elements for which the specified selector is true.
     * <p>
     * Like the built-in array filter method, the returned selection does not preserve the index of the original
     * selection; it returns a copy with elements removed.
     * <p>
     * If you want to preserve the index, use select instead.
     * <p>
     * 
     * @param selector
     * the CSS3 selector to be used as a filter
     * @return a new transition containing the filtered elements
     */
    public native final Transition filter(String selector)/*-{
		return this.filter(selector);
    }-*/;

    /**
     * Filters the transition, returning a new transition that contains only the
     * elements for which the specified selector is true.
     * <p>
     * As with other operators, the function is passed the current datum d and index i, with the this context as the
     * current DOM element.
     * <p>
     * Like the built-in array filter method, the returned selection does not preserve the index of the original
     * selection; it returns a copy with elements removed. If you want to preserve the index, use select instead.
     * <p>
     * 
     * @param datumFunction
     * the function to be used as a filter
     * @return a new transition containing the filtered elements
     */
    public native final Transition filter(final DatumFunction<Element> datumFunction)/*-{
		return this
				.filter(function(d, i) {
					return datumFunction.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
    }-*/;

    /**
     * Creates a new transition on the same selected elements that starts with
     * this transition ends. The new transition inherits this transition’s
     * duration and easing. This can be used to define chained transitions
     * without needing to listen for "end" events.
     * <p>
     * 
     * @return the new transition
     */
    public native final Transition transition()/*-{
		return this.transition();
    }-*/;

    /**
     * Returns true if the current transition is empty; a transition is empty if
     * it contains no non-null elements.
     * 
     * @return true if the transition is empty, false otherwise.
     */
    public final native boolean empty()/*-{
		return this.empty();
    }-*/;

    /**
     * Returns the first non-null element in the current transition. If the
     * selection is empty, returns null.
     * 
     * @return the first non-null element in the current transition or null if
     * the selection is empty.
     */
    public final native Element node()/*-{
		return this.node();
    }-*/;

    private static JavaScriptObject trampolineInterpolator(final Interpolator<?> interpolator) {
        return interpolator.asJSOFunction();
    }

    /**
     * Type of transition event.
     * <p>
     * 
     * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
     * 
     */
    public static enum EventType {
        START,
        END;

        public String getType() {
            return name().toLowerCase();
        }
    }

    /**
     * Adds a listener for transition events. The listener will be invoked for
     * each individual element in the transition.
     * <p>
     * The start event is invoked during the first asynchronous callback (tick) of the transition, before any tweens are
     * invoked. For transitions with zero delay, this is typically about 17ms after the transition is scheduled. State
     * events are useful for triggering instantaneous changes to each element, such as changing attributes that cannot
     * be interpolated.
     * <p>
     * The end event is invoked during the last asynchronous callback (tick) after the transition duration and delay
     * expires, after all tweens are invoked with t=1. Note that if the transition is superseded by a later-scheduled
     * transition on a given element, no end event will be dispatched for that element; interrupted transitions do not
     * trigger end events. For example, transition.remove schedules each element to be removed when the transition ends,
     * but if the transition is interrupted, the element will not be removed. End events can be used as an alternative
     * to transition.transition to create chained transitions by selecting the current element, this, and deriving a new
     * transition.
     * <p>
     * Any transitions created during the end event will inherit the current transition parameters, including ID, time,
     * easing, delay and duration. Thus, new transitions created within a parent transition.each will not the parent
     * transition, similar to subtransitions.
     * <p>
     * 
     * @param type
     * the type of event
     * @param listener
     * the listener
     * @return the current transition
     */
    public final native Transition each(EventType type, DatumFunction<Void> listener)/*-{
		return this
				.each(
						type.@com.github.gwtd3.api.core.Transition.EventType::getType()(),
						function(d, i) {
							listener.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
						});
    }-*/;

    /**
     * Behaves similarly to {@link Selection#each(DatumFunction)}: immediately
     * invokes the specified function for each element in the current
     * transition, passing in the current datum d and index i, with the this
     * context of the current DOM element.
     * <p>
     * Any transitions created during the end event will inherit the current transition parameters, including ID, time,
     * easing, delay and duration.
     * <p>
     * Thus, new transitions created within a parent transition.each will not the parent transition, similar to
     * subtransitions.
     * 
     * The transition.each method can be used to chain transitions and apply shared timing across a set of transitions.
     * 
     * @param listener
     * @return
     */
    public final native Transition each(DatumFunction<Void> listener)/*-{
		return this
				.each(function(d, i) {
					listener.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
    }-*/;

    /**
     * Invokes the specified function once, passing in the current transition as
     * a single parameter.
     * 
     * @param jsFunction
     * @return the current transition
     */
    public native final Transition call(IsFunction jsFunction) /*-{
		return this.call(jsFunction);
    }-*/;
}
