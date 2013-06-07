/**
 * 
 */
package com.github.gwtd3.api.core;

import java.util.List;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.IsFunction;
import com.github.gwtd3.api.JsArrays;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.functions.KeyFunction;
import com.github.gwtd3.api.svg.PathDataGenerator;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayUtils;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;

/**
 * A selection is an array of elements pulled from the current document. D3 uses
 * CSS3 to select elements. See {@link D3#select(String)} and {@link D3#select(com.google.gwt.dom.client.Node)} methods
 * for creating {@link Selection}.
 * <p>
 * <i>If your browser doesn't support selectors natively, you can include Sizzle before D3 for backwards-compatibility.
 * TODO: include Sizzle lib dynamically according to the user.agent property (ie6)</i>
 * <p>
 * After selecting elements, you apply operators to them to do stuff. These operators can get or set {@link #attr
 * attributes}, {@link #style styles}, {@link #property(String, String) properties}, {@link #html(String) HTML} and
 * {@link #text text} content. Attribute values and such are specified as either constants or functions; the latter are
 * evaluated for each element in the selection.
 * <p>
 * You can also join selections to {@link #data data}; this data is available to operators for data-driven
 * transformations. In addition, joining to data produces enter and exit subselections, so that you may add or remove
 * elements in response to changes in data.
 * <p>
 * You won't generally need to use for loops or recursive functions to modify the document with D3. That's because you
 * operate on entire selections at once, rather than looping over individual elements. TODO: However, you can still loop
 * over elements manually if you wish: there's an each operator which invokes an arbitrary function, and selections are
 * arrays, so elements can be accessed directly (e.g., selection[0][0]).
 * <p>
 * D3 supports method chaining for brevity when applying multiple operators: the operator return value is the selection.
 * <p>
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Selection extends EnteringSelection {

    protected Selection() {}

    /**
     * Returns the first non-null element in the current selection. If the
     * selection is empty, returns null.
     * 
     * @return the first non-null element in the current selection or null if
     *         the selection is empty.
     */
    public final native Element node()/*-{
		return this.node();
    }-*/;

    // ======== subselections ==========

    /**
     * For each element in the current selection, selects descendant elements
     * that match the specified selector string.
     * <p>
     * The returned selection is grouped by the ancestor node in the current selection. If no element matches the
     * specified selector for the current element, the group at the current index will be empty in the returned
     * selection.
     * <p>
     * The subselection <strong>does not</strong> inherit data from the current selection; however, if the data value is
     * specified as a function, this function will be based the data d of the ancestor node and the group index i.
     * <p>
     * Grouping by selectAll also affects subsequent entering placeholder nodes. Thus, to specify the parent node when
     * appending entering nodes, use select followed by selectAll: <code>
     * d3.select("body").selectAll("div") 
     * </code> You can see the parent node of each group by inspecting the parentNode property of each group array, such
     * as selection[0].parentNode.
     * 
     * <p>
     * TODO: The selector may also be specified as a function that returns an array of elements (or a NodeList), or the
     * empty array if there are no matching elements. In this case, the specified selector is invoked in the same manner
     * as other operator functions, being passed the current datum d and index i, with the this context as the current
     * DOM element.
     * 
     * @param selector
     * @return
     */
    public final native Selection selectAll(String selector)/*-{
		return this.selectAll(selector);
    }-*/;

    // ======== content ==========
    /**
     * Returns the value of the specified attribute for the first non-null
     * element in the selection. This is generally useful only if you know that
     * the selection contains exactly one element.
     * <p>
     * The specified name may have a namespace prefix, such as xlink:href, to specify an "href" attribute in the XLink
     * namespace. By default, D3 supports svg, xhtml, xlink, xml, and xmlns namespaces. Additional namespaces can be
     * registered by adding to d3.ns.prefix.
     * <p>
     * 
     * @param name
     *            the name of the attribute
     * @return the value of the attribute
     */
    public native final String attr(final String name)
    /*-{
		return this.attr(name);
    }-*/;

    /**
     * Sets the attribute with the specified name to the specified value on all
     * selected elements.
     * <p>
     * The specified name may have a namespace prefix, such as xlink:href, to specify an "href" attribute in the XLink
     * namespace. By default, D3 supports svg, xhtml, xlink, xml, and xmlns namespaces. Additional namespaces can be
     * registered by adding to d3.ns.prefix.
     * <p>
     * A null value will remove the specified attribute.
     * <p>
     * @param name
     *            the name of the attribute
     * @param value
     *            the new value to assign, or null to remove the attribute
     * @return the current selection
     */
    public native final <T> Selection attr(final String name, String value)
    /*-{
		return this.attr(name, value);
    }-*/;

    /**
     * Sets the attribute with the specified name to the specified {@link PathDataGenerator} value on all selected
     * elements.
     * <p>
     * <<<<<<< HEAD This method should always been used with a selection containing a svg &lt;path&gt; element by
     * specifying "d" for the name argument. ======= This method should always been used with a selection containing a
     * svg &lt;path&gt; element by specifying "d" for the name argument. >>>>>>> colorpalette
     * <p>
     * The specified name may have a namespace prefix, such as xlink:href, to specify an "href" attribute in the XLink
     * namespace. By default, D3 supports svg, xhtml, xlink, xml, and xmlns namespaces. Additional namespaces can be
     * registered by adding to d3.ns.prefix.
     * <p>
     * 
     * @param name
     *            the name of the attribute
     * @param value
     *            the new value to assign
     * @return the current selection
     */
    public native final Selection attr(final String name, PathDataGenerator value)
    /*-{
		return this.attr(name, value);
    }-*/;

    /**
     * See {@link #attr(String, String)}.
     * 
     * @param name
     * @param value
     * @return
     */
    public native final Selection attr(final String name, double value)
    /*-{
		return this.attr(name, value);
    }-*/;

    /**
     * See {@link #attr(String, String)}.
     * 
     * @param name
     * @param value
     * @return
     */
    public native final <T> Selection attr(final String name, boolean value)/*-{
		return this.attr(name, value);
    }-*/;

    /**
     * Sets the attribute with the specified name to the value returned by the
     * specified function on all selected elements.
     * <p>
     * The function is evaluated for each selected element (in order), being passed the current datum d and the current
     * index i. The function's return value is then used to set each element's attribute. A null value will remove the
     * specified attribute.
     * <p>
     * The specified name may have a namespace prefix, such as xlink:href, to specify an "href" attribute in the XLink
     * namespace. By default, D3 supports svg, xhtml, xlink, xml, and xmlns namespaces. Additional namespaces can be
     * registered by adding to d3.ns.prefix.
     * 
     * @param name
     *            the name of the attribute
     * @param callback
     *            the function used to compute the new value of the attribute
     * @return the current selection
     */
    public native final Selection attr(final String name, final DatumFunction<?> callback)
    /*-{
		return this
				.attr(
						name,
						function(d, i) {
							return callback.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Datum;I)(this,{datum:d},i);
						});
    }-*/;

    /**
     * Returns the current computed value of the specified style property for
     * the first non-null element in the selection.
     * <p>
     * This is generally useful only if you know the selection contains exactly one element.
     * <p>
     * Note that the computed value may be different than the value that was previously set, particularly if the style
     * property was set using a shorthand property (such as the "font" style, which is shorthand for "font-size",
     * "font-face", etc.).
     * 
     * @param name
     *            the name of the style to return
     * @return the style value
     */
    public native final String style(String name) /*-{
		return this.style(name);
    }-*/;

    /**
     * See {@link Selection#style(String, T, boolean)}.
     * 
     * @param name
     * @param value
     * @return
     */
    public native final Selection style(String name, String value) /*-{
		return this.style(name, value);
    }-*/;

    /**
     * See {@link Selection#style(String, T, boolean)}.
     * 
     * @param name
     * @param value
     * @return
     */
    public native final Selection style(String name, double value) /*-{
		return this.style(name, value);
    }-*/;

    /**
     * See {@link Selection#style(String, T, boolean)}.
     * 
     * @param name
     * @param value
     * @return
     */
    public native final Selection style(String name, DatumFunction<?> callback) /*-{
		return this
				.style(
						name,
						function(d, i) {
							return callback.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Datum;I)(this,{datum:d},i);
						});
    }-*/;

    /**
     * If value is specified, sets the CSS style property with the specified
     * name to the specified value on all selected elements. A null value will
     * remove the style property.
     * 
     * @param name
     *            the name of the style to set
     * @param value
     *            the value to set
     * @param important
     *            true if the style value should be marked as !important, false
     *            otherwise
     * @return the current selection
     */
    public native final <T> Selection style(String name, T value, boolean important)/*-{
		if (important) {
			return this.style(name, value, "important");
		} else {
			return this.style(name, value);
		}
    }-*/;

    /**
     * Sets the CSS style property with the specified name to the value returned
     * by the given function on all selected elements.
     * <p>
     * The function is evaluated for each selected element (in order), being passed the current datum d and the current
     * index i. The function's return value is then used to set each element's style property.
     * <p>
     * A null value will remove the style property.
     * 
     * @param name
     *            the name of the style to set
     * @param callback
     *            the function to be called on each element and returning the
     *            value of the style
     * @param important
     *            true if the style value should be marked as !important, false
     *            otherwise
     * @return the current selection
     */
    public native final Selection style(String name, DatumFunction<Object> callback, boolean important)/*-{
		var imp = important ? 'important' : null;
		return this
				.style(
						name,
						function(d, i) {
							return 
							callback.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Datum;I)
								(this,{datum:d},i);
						}, imp);
	}-*/;

    // ================ classed functions ================

    /**
     * Sets whether or not the specified class(es) is(are) associated with the
     * selected elements.
     * <p>
     * This operator is a convenience routine for setting the "class" attribute; it understands that the "class"
     * attribute is a set of tokens separated by spaces.
     * <p>
     * Under the hood, it will use the classList if available, for convenient adding, removing and toggling of CSS
     * classes.
     * <p>
     * If add is true, then all elements are assigned the specified class(es), if not already assigned; if false, then
     * the class(es) is(are) removed from all selected elements, if assigned.
     * 
     * @param className
     *            the className(s) to add or remove
     * @param add
     *            true to add false to remove the class(es) from all the
     *            elements of the selection
     * @return the selection
     */
    public native final Selection classed(String classNames, boolean add)/*-{
		return this.classed(classNames, add);
    }-*/;

    /**
     * Returns true if and only if the first non-null element in this selection
     * has the specified class. This is generally useful only if you know the
     * selection contains exactly one element.
     * 
     * @param className
     *            the className to test the presence
     * @return true if the first element of the selection has the given
     *         classname in the class attribute
     */
    public native final boolean classed(String classNames) /*-{
		return this.classed(classNames);
    }-*/;

    /**
     * Sets whether or not the class should be associated or not to the
     * elements, according to the return value of the given function.
     * <p>
     * he function is evaluated for each selected element (in order), being passed the current datum d and the current
     * index i, with the this context as the current DOM element. The function's return value is then used to assign or
     * unassign the specified class on each element.
     * <p>
     * This operator is a convenience routine for setting the "class" attribute; it understands that the "class"
     * attribute is a set of tokens separated by spaces.
     * <p>
     * Under the hood, it will use the classList if available, for convenient adding, removing and toggling of CSS
     * classes.
     * <p>
     * If the function returns true, then the element is assigned the specified class, if not already assigned; if it
     * returns false or null, then the class is removed from the element, if assigned.
     * 
     * @param className
     *            the class to assign or not
     * @param addFunction
     *            the function evaluated for each element and returning a
     *            boolean indicating to assign or not the class to the element
     * @return the selection
     */
    public native final Selection classed(String classNames, DatumFunction<Boolean> addFunction)/*-{
		return this
				.classed(
						classNames,
						function(d, i) {
							var r = addFunction.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Datum;I)(this,{datum:d},i);
							return r == null ? false
									: r.@java.lang.Boolean::booleanValue()();
						});
    }-*/;

    public native final Selection property(String styleName, String value)/*-{
		throw new UnsupportedOperationException("not yet implemented");
    }-*/;

    /**
     * Returns the value of the text content for the first non-null element in
     * the selection. This is generally useful only if you know that the
     * selection contains exactly one element.
     * 
     * @return the value of the text property
     */
    public native final String text()/*-{
		return this.text();
    }-*/;

    /**
     * Sets the text content of all selected elements to the given value.
     * <p>
     * The text operator is based on the textContent property; setting the text content will replace any existing child
     * elements.
     * 
     * @param value
     *            the new text value to set
     * @return the current selection
     */
    public native final <T> Selection text(T value)/*-{
		return this.text(value);
    }-*/;

    /**
     * Sets the text content to the value returned by the specified function on
     * all selected elements.
     * <p>
     * The function is evaluated for each selected element (in order), being passed the current datum d and the current
     * index i. The function's return value is then used to set each element's text content.
     * <p>
     * 
     * @param callback
     *            the function used to compute the new text property
     * @return the current selection
     */
    public native final Selection text(final DatumFunction<?> callback) /*-{
		return this
				.text(function(d, i) {
					return callback.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Datum;I)(this,{datum:d},i);
				});
    }-*/;

    public native final Selection html(String value)/*-{
		return this.html(value);
    }-*/;

    /**
     * Removes the elements in the current selection from the current document.
     * Generally speaking, you should stop using selections once you've removed
     * them, because there's not currently a way to add them back to the
     * document. (See the {@link #append(String)} and {@link #insert(String,String)} operators above for details.)
     * 
     * @return the current selection
     */
    public native final Selection remove()/*-{
		return this.remove();
    }-*/;

    // ================================ data functions ========
    /**
     * Joins the specified array of data with the current selection using the
     * default by-index key mapping.
     * <p>
     * 
     * 
     * @param data
     *            the data array to map to the selection
     * @return
     */
    public native final UpdateSelection data(JavaScriptObject array)/*-{
		return this.data(array);
    }-*/;

    /**
     * Same as #data(JavaScriptObject) for an {@link List} of objects.
     * 
     * @see #data(JavaScriptObject)
     * @param data
     * @return
     */
    public final UpdateSelection data(final List<?> data) {
        return this.data(JsArrays.asJsArray(data));
    }

    /**
     * Joins the specified array of data with the current selection by
     * controlling how the data is mapped to the selection's elements.
     * <p>
     * The specified values is an array of data values, such as an array of numbers or objects. Use {@link JsArrayUtils}
     * or {@link JsArrays} to turn your Java arrays into Javascript arrays (which has no overhead in prod mode).
     * <p>
     * The key function is used control how to map data to the selection. See {@link KeyFunction}'s documentation.
     * <p>
     * When data is assigned to an element, it is stored in the property __data__, thus making the data "sticky" so that
     * the data is available on re-selection.
     * <p>
     * The values array specifies the data for each group in the selection. Thus, if the selection has multiple groups
     * (such as a d3.selectAll followed by a selection.selectAll), then data should be specified as a function that
     * returns an array (assuming that you want different data for each group). For example, you may bind a
     * two-dimensional array to an initial selection, and then bind the contained inner arrays to each subselection. The
     * values function in this case is the identity function: it is invoked for each group of child elements, being
     * passed the data bound to the parent element, and returns this array of data.
     * <p>
     * The result of data operator is the {@link UpdateSelection}.
     * <p>
     * 
     * @param array
     *            the data array to map to the selection
     * @param keyFunction
     *            the function to control how data is mapped to the selection
     *            elements
     * @return the {@link UpdateSelection}
     */
    public native final UpdateSelection data(JavaScriptObject array, KeyFunction<?> keyFunction)/*-{
		return this
				.data(
						array,
						function(d, i) {
							var thisArg = this;
							if (this == array) {
								thisArg = null;
							}
							return keyFunction.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Datum;I)(thisArg,{datum:d},i);
						});
    }-*/;

    /**
     * @param callback
     * @return
     */
    public native final UpdateSelection data(DatumFunction<?> callback) /*-{
		return this
				.data(function(d, i) {
					return callback.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Datum;I)(this,{datum:d},i);
				});
    }-*/;

    /**
     * Sets the element's bound data to the specified value on all selected
     * elements. Unlike the {@link #data} methods, this method does not compute
     * a join (and thus does not compute enter and exit selections).
     * 
     * See <a
     * href="https://github.com/mbostock/d3/wiki/Selections#wiki-datum">datum
     * </a>
     * 
     * @param object
     * @return
     */
    public native final <T> Selection datum(T object)/*-{
		return this.datum(object);
    }-*/;

    // ==================== TRANSITION =======
    /**
     * Starts a transition for the current selection. Transitions behave much
     * like selections, except operators animate smoothly over time rather than
     * applying instantaneously.
     * 
     * @return the new transition
     */
    public native final Transition transition()/*-{
		return this.transition();
    }-*/;

    /**
     * Invokes the specified function once, passing in the current selection as
     * a single parameter.
     * 
     * @param jsFunction
     * @return the current selection
     */
    public native final Selection each(DatumFunction<Void> listener) /*-{
		return this
				.each(function(d, i) {
					listener.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Datum;I)(this,{datum:d},i);
				});
    }-*/;

    /**
     * Invokes the specified function once, passing in the current selection as
     * a single parameter.
     * 
     * @param jsFunction
     * @return the current selection
     */
    public native final Selection call(IsFunction jsFunction) /*-{
		return this.call(jsFunction);
    }-*/;

    /**
     * Same as {@link #on(String, DatumFunction, boolean)} with false for the
     * useCapture flag.
     * 
     * @param eventType
     * @param listener
     * @return
     */
    public native final Selection on(String eventType, DatumFunction<Void> listener) /*-{
		var l = listener == null ? null
				: function(d, i) {
					listener.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Datum;I)(this,{datum:d},i);
				};
		return this.on(eventType, l);
    }-*/;

    /**
     * Adds or removes an event listener to each element in the current
     * selection, for the specified type.
     * <p>
     * The type is a string event type name, such as "click", "mouseover", or "submit". You may use
     * {@link BrowserEvents} constants for convenience.
     * <p>
     * The specified listener is invoked in the same manner as other operator functions, being passed the current datum
     * d and index i, with the this context as the current DOM element.
     * <p>
     * To access the current event within a listener, use the global d3.event. The return value of the event listener is
     * ignored.
     * <p>
     * If an event listener was already registered for the same type on the selected element, the existing listener is
     * removed before the new listener is added. To register multiple listeners for the same event type, the type may be
     * followed by an optional namespace, such as "click.foo" and "click.bar".
     * <p>
     * To remove a listener, pass null as the listener. To remove all listeners for a particular event type, pass null
     * as the listener, and .type as the type, e.g. selection.on(".foo", null).
     * <p>
     * 
     * 
     * @param eventType
     *            the type of the event to listen to; prefix the type with a dot
     *            to remove all listeners (specifying null as the second
     *            parameter).
     * @param listener
     *            the listener to be added or to replace the previous one, or
     *            null to remove the previous listener(s)
     * @param useCapture
     *            a capture flag, which corresponds to the W3C useCapture flag:
     *            "After initiating capture, all events of the specified type
     *            will be dispatched to the registered EventListener before
     *            being dispatched to any EventTargets beneath them in the tree.
     *            Events which are bubbling upward through the tree will not
     *            trigger an EventListener designated to use capture."
     * @return the current selection
     */
    public native final Selection on(String eventType, DatumFunction<Void> listener, boolean useCapture) /*-{
		var l = (listener == null ? null
				: function(d, i) {
					listener.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Datum;I)(this,{datum:d},i);
				});
		return this.on(eventType, l, useCapture);
    }-*/;

    /**
     * Return the number of elements in the current selection.
     * 
     * @return the number of elements
     */
    public final int count() {
        CountFunction function = new CountFunction();
        each(function);
        return function.getCount();
    }

    protected static class CountFunction implements DatumFunction<Void> {
        private int count = 0;

        @Override
        public Void apply(final Element context, final Datum d, final int index) {
            count++;
            return null;
        }

        public int getCount() {
            return count;
        }
    }
}
