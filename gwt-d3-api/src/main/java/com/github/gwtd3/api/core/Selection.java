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

import java.util.Comparator;
import java.util.List;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.IsFunction;
import com.github.gwtd3.api.JsArrays;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.functions.CountFunction;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.functions.KeyFunction;
import com.github.gwtd3.api.svg.PathDataGenerator;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayUtils;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;

/**
 * A selection is an array of elements pulled from the current document. D3 uses
 * CSS3 to select elements. See {@link D3#select(String)} and
 * {@link D3#select(com.google.gwt.dom.client.Node)} methods for creating
 * {@link Selection}.
 * <p>
 * <i>If your browser doesn't support selectors natively, you can include Sizzle
 * before D3 for backwards-compatibility. TODO: include Sizzle lib dynamically
 * according to the user.agent property (ie6)</i>
 * <p>
 * After selecting elements, you apply operators to them to do stuff. These
 * operators can get or set {@link #attr attributes}, {@link #style styles},
 * {@link #property(String, String) properties}, {@link #html(String) HTML} and
 * {@link #text text} content. Attribute values and such are specified as either
 * constants or functions; the latter are evaluated for each element in the
 * selection.
 * <p>
 * You can also join selections to {@link #data data}; this data is available to
 * operators for data-driven transformations. In addition, joining to data
 * produces enter and exit subselections, so that you may add or remove elements
 * in response to changes in data.
 * <p>
 * You won't generally need to use for loops or recursive functions to modify
 * the document with D3. That's because you operate on entire selections at
 * once, rather than looping over individual elements. However, you can still
 * loop over elements manually if you wish: there's an
 * {@link #each(DatumFunction)} operator which invokes an arbitrary function,
 * and (TODO) selections are arrays, so elements can be accessed directly (e.g.,
 * selection[0][0]).
 * <p>
 * D3 supports method chaining for brevity when applying multiple operators: the
 * operator return value is the selection.
 * <p>
 * <h1>Creating selections</h1>
 * D3 provides two top-level methods for selecting elements:
 * {@link D3#select(String)} and {@link D3#selectAll(String)}. These methods
 * accept selector strings; the former selects only the first matching element,
 * while the latter selects all matching elements in document traversal order.
 * There are also variant of these methods which accept nodes, which is useful
 * to integrate with GWT {@link Element} API.
 * <p>
 * <h1>Operating on selections</h1>
 * Selections are arrays of elements—literally. D3 binds additional methods to
 * the array so that you can apply operators to the selected elements, such as
 * setting an attribute on all the selected elements. One nuance is that
 * selections are grouped: rather than a one-dimensional array, each selection
 * is an array of arrays of elements. This preserves the hierarchical structure
 * of subselections. Most of the time, you can ignore this detail, but that's
 * why a single-element selection looks like [[node]] rather than [node]. For
 * more on nested selections, see Nested Selections.
 * <p>
 * If you want to learn how selections work, try selecting elements
 * interactively using your browser's developer console. You can inspect the
 * returned array to see which elements were selected, and how they are grouped.
 * You can also then apply operators to the selected elements and see how the
 * page content changes.
 * <p>
 * <h1>Subselections</h1>
 * Whereas the top-level {@link D3#select(String)} methods query the entire
 * document, a selection's {@link #select(String)} and
 * {@link #selectAll(String)} operators restrict queries to descendants of each
 * selected element; we call this "subselection". For example,
 * d3.selectAll("p").select("b") returns the first bold ("b") elements in every
 * paragraph ("p") element. Subselecting via {@link #selectAll(String)} groups
 * elements by ancestor. Thus, d3.selectAll("p").selectAll("b") groups by
 * paragraph, while d3.selectAll("p b") returns a flat selection. Subselecting
 * via select is similar, but preserves groups and propagates data. Grouping
 * plays an important role in the data join, and functional operators may depend
 * on the numeric index of the current element within its group.
 * <p>
 * 
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Selection extends EnteringSelection {

	/**
	 * Name of the element property in which D3 stores the datum of an element.
	 */
	public static final String DATA_PROPERTY = "__data__";

	protected Selection() {
	}

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
	 * The returned selection is grouped by the ancestor node in the current
	 * selection. If no element matches the specified selector for the current
	 * element, the group at the current index will be empty in the returned
	 * selection.
	 * <p>
	 * The subselection <strong>does not</strong> inherit data from the current
	 * selection; however, if the data value is specified as a function, this
	 * function will be based the data d of the ancestor node and the group
	 * index i.
	 * <p>
	 * Grouping by selectAll also affects subsequent entering placeholder nodes.
	 * Thus, to specify the parent node when appending entering nodes, use
	 * select followed by selectAll: <code>
	 * d3.select("body").selectAll("div")
	 * </code>
	 * <p>
	 * You can see the parent node of each group by inspecting the parentNode
	 * property of each group array, such as selection[0].parentNode, or by
	 * using the {@link #parentNode(int)} method.
	 * <p>
	 * 
	 * <p>
	 * TODO: The selector may also be specified as a function that returns an
	 * array of elements (or a NodeList), or the empty array if there are no
	 * matching elements. In this case, the specified selector is invoked in the
	 * same manner as other operator functions, being passed the current datum d
	 * and index i, with the this context as the current DOM element.
	 * <p>
	 * TODO: check this method also work for EnteringSelections and pull it up
	 * in that case...
	 * 
	 * @param selector
	 * @return
	 */
	public final native Selection selectAll(String selector)/*-{
		return this.selectAll(selector);
	}-*/;

	// ======== attr functions ==========
	/**
	 * Returns the value of the specified attribute for the first non-null
	 * element in the selection. This is generally useful only if you know that
	 * the selection contains exactly one element.
	 * <p>
	 * The specified name may have a namespace prefix, such as xlink:href, to
	 * specify an "href" attribute in the XLink namespace. By default, D3
	 * supports svg, xhtml, xlink, xml, and xmlns namespaces. Additional
	 * namespaces can be registered by adding to d3.ns.prefix.
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
	 * The specified name may have a namespace prefix, such as xlink:href, to
	 * specify an "href" attribute in the XLink namespace. By default, D3
	 * supports svg, xhtml, xlink, xml, and xmlns namespaces. Additional
	 * namespaces can be registered by adding to d3.ns.prefix.
	 * <p>
	 * A null value will remove the specified attribute.
	 * <p>
	 * 
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
	 * Sets the attribute with the specified name to the specified
	 * {@link PathDataGenerator} value on all selected elements.
	 * <p>
	 * This method should always been used with a selection containing a svg
	 * &lt;path&gt; element by specifying "d" for the name argument.
	 * <p>
	 * The specified name may have a namespace prefix, such as xlink:href, to
	 * specify an "href" attribute in the XLink namespace. By default, D3
	 * supports svg, xhtml, xlink, xml, and xmlns namespaces. Additional
	 * namespaces can be registered by adding to d3.ns.prefix.
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
	 * The function is evaluated for each selected element (in order), being
	 * passed the current datum d and the current index i. The function's return
	 * value is then used to set each element's attribute. A null value will
	 * remove the specified attribute.
	 * <p>
	 * The specified name may have a namespace prefix, such as xlink:href, to
	 * specify an "href" attribute in the XLink namespace. By default, D3
	 * supports svg, xhtml, xlink, xml, and xmlns namespaces. Additional
	 * namespaces can be registered by adding to d3.ns.prefix.
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
							return callback.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
						});
	}-*/;

	// ================ style functions ================

	/**
	 * Returns the current computed value of the specified style property for
	 * the first non-null element in the selection.
	 * <p>
	 * This is generally useful only if you know the selection contains exactly
	 * one element.
	 * <p>
	 * Note that the computed value may be different than the value that was
	 * previously set, particularly if the style property was set using a
	 * shorthand property (such as the "font" style, which is shorthand for
	 * "font-size", "font-face", etc.).
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
		try {
			return this
					.style(
							name,
							function(d, i) {
								try {

									var r = callback.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
									//r.@java.lang.Object::toString()();
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
	 * Sets the CSS style property with the specified name to the value returned
	 * by the given function on all selected elements.
	 * <p>
	 * The function is evaluated for each selected element (in order), being
	 * passed the current datum d and the current index i.
	 * <p>
	 * The function's return value's {@link Object#toString()} method is then
	 * used to set each element's style property.
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

	// ================ classed functions ================

	/**
	 * Sets whether or not the specified class(es) is(are) associated with the
	 * selected elements.
	 * <p>
	 * This operator is a convenience routine for setting the "class" attribute;
	 * it understands that the "class" attribute is a set of tokens separated by
	 * spaces.
	 * <p>
	 * Under the hood, it will use the classList if available, for convenient
	 * adding, removing and toggling of CSS classes.
	 * <p>
	 * If add is true, then all elements are assigned the specified class(es),
	 * if not already assigned; if false, then the class(es) is(are) removed
	 * from all selected elements, if assigned.
	 * 
	 * @param className
	 *            the className(s) to add or remove
	 * @param add
	 *            true to add false to remove the class(es) from all the
	 *            elements of the selection
	 * @return the current selection
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
	 * he function is evaluated for each selected element (in order), being
	 * passed the current datum d and the current index i, with the this context
	 * as the current DOM element. The function's return value is then used to
	 * assign or unassign the specified class on each element.
	 * <p>
	 * This operator is a convenience routine for setting the "class" attribute;
	 * it understands that the "class" attribute is a set of tokens separated by
	 * spaces.
	 * <p>
	 * Under the hood, it will use the classList if available, for convenient
	 * adding, removing and toggling of CSS classes.
	 * <p>
	 * If the function returns true, then the element is assigned the specified
	 * class, if not already assigned; if it returns false or null, then the
	 * class is removed from the element, if assigned.
	 * 
	 * @param className
	 *            the class to assign or not
	 * @param addFunction
	 *            the function evaluated for each element and returning a
	 *            boolean indicating to assign or not the class to the element
	 * @return the current selection
	 */
	public native final Selection classed(String classNames, DatumFunction<Boolean> addFunction)/*-{
		return this
				.classed(
						classNames,
						function(d, i) {
							var r = addFunction.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
							return r == null ? false
									: r.@java.lang.Boolean::booleanValue()();
						});
	}-*/;

	// ================ property functions ================

	/**
	 * This method return the value of the specified property for the first
	 * non-null element in the selection. This is generally useful only if you
	 * know that the selection contains exactly one element.
	 * <p>
	 * Some HTML elements have special properties that are not addressable using
	 * standard attributes or styles. For example, form text fields have a value
	 * string property, and checkboxes have a checked boolean property, and
	 * underlying element has addressable fields, such as className. This method
	 * is used to address thoses properties.
	 * <p>
	 * 
	 * @param name
	 *            the name of the property
	 * @return the value of the property
	 */
	public native final Value property(final String name)/*-{
		return {
			datum : this.property(name)
		};
	}-*/;

	/**
	 * Sets the property with the specified name to the specified value on all
	 * selected elements.
	 * <p>
	 * Some HTML elements have special properties that are not addressable using
	 * standard attributes or styles. For example, form text fields have a value
	 * string property, and checkboxes have a checked boolean property, and
	 * underlying element has addressable fields, such as className. This method
	 * is used to address thoses properties.
	 * <p>
	 * A null value will remove the specified property.
	 * <p>
	 * 
	 * @param name
	 *            the name of the attribute
	 * @param value
	 *            the new value to assign, or null to remove the attribute
	 * @return the current selection
	 */
	public native final <T> Selection property(final String name, String value)
	/*-{
		return this.property(name, value);
	}-*/;

	/**
	 * See {@link #property(String, String)}.
	 * 
	 * @param name
	 *            the name of the property
	 * @param value
	 *            the value
	 * @return the current selection
	 */
	public native final Selection property(final String name, double value)
	/*-{
		return this.property(name, value);
	}-*/;

	/**
	 * See {@link #property(String, String)}.
	 * 
	 * @param name
	 *            the name of the property
	 * @param value
	 *            the value
	 * @return the current selection
	 */
	public native final <T> Selection property(final String name, JavaScriptObject value)/*-{
		return this.property(name, value);
	}-*/;

	/**
	 * See {@link #property(String, String)}.
	 * 
	 * @param name
	 *            the name of the property
	 * @param value
	 *            the value
	 * @return the current selection
	 */
	public native final <T> Selection property(final String name, boolean value)/*-{
		return this.property(name, value);
	}-*/;

	/**
	 * Sets the property with the specified name to the value returned by the
	 * specified function on all selected elements.
	 * <p>
	 * The function is evaluated for each selected element (in order), being
	 * passed the current datum d and the current index i. The function's return
	 * value is then used to set each element's attribute. A null value will
	 * remove the specified attribute.
	 * <p>
	 * The specified name may have a namespace prefix, such as xlink:href, to
	 * specify an "href" attribute in the XLink namespace. By default, D3
	 * supports svg, xhtml, xlink, xml, and xmlns namespaces. Additional
	 * namespaces can be registered by adding to d3.ns.prefix.
	 * <p>
	 * Note: if you provide a {@link DatumFunction<T>} parameterized with a
	 * wrapper type, such as java.lang.Double or java.lang.Boolean, when getting
	 * the property value ( {@link #property(String)}), you should use
	 * {@link Value#as(Class)} with the corresponding Class object, such as
	 * Value.as(Double) or Value.as(Boolean) to get the property value.
	 * 
	 * 
	 * @param name
	 *            the name of the attribute
	 * @param callback
	 *            the function used to compute the new value of the attribute
	 * @return the current selection
	 */
	public native final Selection property(final String name, final DatumFunction<?> callback)
	/*-{
		return this
				.property(
						name,
						function(d, i) {
							return callback.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
						});
	}-*/;

	// ================ text functions ================

	/**
	 * Returns the value of the text content for the first non-null element in
	 * the selection. This is generally useful only if you know that the
	 * selection contains exactly one element.
	 * <p>
	 * The text operator is based on the <code>textContent</code> property.
	 * 
	 * @return the value of the text property
	 */
	public native final String text()/*-{
		return this.text();
	}-*/;

	/**
	 * Sets the text content of all selected elements to the given value. A null
	 * value will clear the content.
	 * <p>
	 * The text operator is based on the textContent property; setting the text
	 * content will replace any existing child elements.
	 * 
	 * @param value
	 *            the new text value to set
	 * @return the current selection
	 */
	public native final <T> Selection text(String value)/*-{
		return this.text(value);
	}-*/;

	/**
	 * Sets the text content to the value returned by the specified function on
	 * all selected elements. A null value will clear the content.
	 * <p>
	 * The function is evaluated for each selected element (in order), being
	 * passed the current datum d and the current index i. The function's return
	 * value is then used to set each element's text content.
	 * <p>
	 * The text operator is based on the textContent property; setting the text
	 * content will replace any existing child elements.
	 * 
	 * @param callback
	 *            the function used to compute the new text property
	 * @return the current selection
	 */
	public native final Selection text(final DatumFunction<String> callback) /*-{
		return this
				.text(function(d, i) {
					return callback.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
	}-*/;

	// ================ html functions ================

	/**
	 * Sets the inner html content to the value returned by the specified
	 * function on all selected elements. A null value will clear the content.
	 * <p>
	 * The function is evaluated for each selected element (in order), being
	 * passed the current datum d and the current index i. The function's return
	 * value is then used to set each element's inner html content.
	 * <p>
	 * The html operator is based on the innerHTML property; setting the inner
	 * HTML content will replace any existing child elements. Also, you may
	 * prefer to use the {@link #append(String)} or
	 * {@link #insert(String, String)} operators to create HTML content in a
	 * data-driven way; this operator is intended for when you want a little bit
	 * of HTML, say for rich formatting.
	 * 
	 * @param callback
	 *            the function used to compute the new inner html property
	 * @return the current selection
	 */
	public native final Selection html(final DatumFunction<String> callback) /*-{
		return this
				.html(function(d, i) {
					return callback.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
	}-*/;

	/**
	 * Sets the inner html content of all selected elements to the specified
	 * value. A null value will clear the content.
	 * <p>
	 * The html operator is based on the innerHTML property; setting the inner
	 * HTML content will replace any existing child elements. Also, you may
	 * prefer to use the {@link #append(String)} or
	 * {@link #insert(String, String)} operators to create HTML content in a
	 * data-driven way; this operator is intended for when you want a little bit
	 * of HTML, say for rich formatting.
	 * 
	 * @param value
	 *            the new value to set
	 * @return the current selection
	 */
	public native final Selection html(String value)/*-{
		return this.html(value);
	}-*/;

	/**
	 * Returns the value of the inner HTML content for the first non-null
	 * element in the selection. This is generally useful only if you know that
	 * the selection contains exactly one element.
	 * <p>
	 * The html operator is based on the <code>innerHTML</code> property.
	 * 
	 * @return the value of the text property
	 */
	public native final String html()/*-{
		return this.html();
	}-*/;

	/**
	 * Removes the elements in the current selection from the current document.
	 * Returns the current selection (the same elements that were removed) which
	 * are now “off-screen”, detached from the DOM. Note that there is not
	 * currently a dedicated API to add removed elements back to the document;
	 * however, you can pass a function to selection.each or selection.select to
	 * re-add elements.
	 * <p>
	 * The elements are removed from the DOM but still remains in the selection.
	 * <p>
	 * 
	 * @see <a href="https://github.com/mbostock/d3/issues/1342">that issue</a>
	 *      for more information.
	 *      <p>
	 * 
	 * @return the current selection containing only the removed elements
	 */
	public native final Selection remove()/*-{
		return this.remove();
	}-*/;

	// ================ controls functions ================

	/**
	 * Return the number of elements in the current selection.
	 * 
	 * @return the number of elements
	 */
	public final int size() {
		CountFunction function = new CountFunction();
		each(function);
		return function.getCount();
	}

	/**
	 * Invokes the specified function for each element in the current selection,
	 * passing in the current datum d and index i.
	 * <p>
	 * This operator is used internally by nearly every other operator, and can
	 * be used to invoke arbitrary code for each selected element.
	 * <p>
	 * The each operator can be used to process selections recursively, by using
	 * d3.select(context) within the callback function.
	 * 
	 * @param func
	 *            the callback function
	 * @return the current selection
	 */
	public native final Selection each(DatumFunction<Void> func) /*-{
		return this
				.each(function(d, i) {
					func.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
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

	// ================================ data getter functions ========

	/**
	 * Returns the array of data for the first group in the selection.
	 * <p>
	 * The length of the returned array will match the length of the first
	 * group, and the index of each datum in the returned array will match the
	 * corresponding index in the selection.
	 * <p>
	 * If some of the elements in the selection are null, or if they have no
	 * associated data, then the corresponding element in the array will be
	 * undefined.
	 * 
	 * @return the array of data for the first group in the selection
	 */
	public native final <T> Array<T> data()/*-{
		return this.data();
	}-*/;

	// ================================ data setter functions with array
	// ========

	/**
	 * Joins the specified array of values with the current selection using the
	 * default by-index key mapping.
	 * <p>
	 * The specified values must be an array-like structure of data values, such
	 * as an array of numbers or objects. Use {@link JsArrayUtils} or
	 * {@link JsArrays} to turn your Java arrays into Javascript arrays (which
	 * has no overhead in prod mode), or use the variant {@link #data} methods.
	 * <p>
	 * The by-index key mapping means that the first datum in the array is
	 * assigned to the first element in the current selection, the second datum
	 * to the second selected element, and so on. If you want to control how
	 * data is mapped to elements, use the data methods that takes a
	 * {@link KeyFunction} parameter, such as
	 * {@link #data(JavaScriptObject, KeyFunction)}.
	 * <p>
	 * The given array specifies data for each group in the selection. Thus, if
	 * the selection has multiple groups (such as a {@link D3#selectAll}
	 * followed by a {@link Selection#selectAll}), and if you want different
	 * data for each group, you should rather use the method
	 * {@link #data(DatumFunction)}.
	 * <p>
	 * When data is assigned to an element, it is stored in the property
	 * {@link #DATA_PROPERTY}, thus making the data "sticky" so that the data is
	 * available on re-selection.
	 * <p>
	 * 
	 * @param array
	 *            the values array to map to the selection
	 * @return the update selection
	 */
	public native final UpdateSelection data(JavaScriptObject array)/*-{
		return this.data(array);
	}-*/;

	/**
	 * Same as {@link #data(JavaScriptObject)} but let the user control how to
	 * map data to the selection.
	 * <p>
	 * See {@link KeyFunction}'s documentation.
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
							var ctxEl, newDataArray = null;
							if (this == array) {
								newDataArray = this;
							} else {
								ctxEl = this;
							}
							return keyFunction.@com.github.gwtd3.api.functions.KeyFunction::map(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/arrays/Array;Lcom/github/gwtd3/api/core/Value;I)(ctxEl,newDataArray,{datum:d},i);
						});
	}-*/;

	/**
	 * Joins each array returned by the specified function to a group of the
	 * current selection, using the default by-index key mapping.
	 * <p>
	 * The specified callback must return an array-like structure of data
	 * values, such as an array of numbers or objects. Use {@link JsArrayUtils}
	 * or {@link JsArrays} to turn your Java arrays into Javascript arrays
	 * (which has no overhead in prod mode).
	 * <p>
	 * This method is appropriate to join data on a multi-group selection, like
	 * one returned by d3.selectAll followed by a call to selection.selectAll.
	 * <p>
	 * For example, you may bind a two-dimensional array to an initial
	 * selection, and then bind the contained inner arrays to each subselection.
	 * The values function in this case is the identity function: it is invoked
	 * for each group of child elements, being passed the data bound to the
	 * parent element, and returns this array of data.
	 * <p>
	 * 
	 * @param callback
	 *            the function called for each group in the selection, passing
	 *            the data of the parent node of the group.
	 * @return the {@link UpdateSelection}
	 */
	public native final <JSO extends JavaScriptObject> UpdateSelection data(DatumFunction<JSO> callback) /*-{
		return this
				.data(function(d, i) {
					var result = callback.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
					//alert(result);
					return result;
				});
	}-*/;

	/**
	 * Joins the specified array of data with the current selection using the
	 * default by-index key mapping.
	 * <p>
	 * 
	 * @param array
	 *            the data array to map to the selection
	 * @return the update selection
	 */
	public final UpdateSelection data(final Object[] array) {
		return data(JsArrays.asJsArray(array));
	}

	/**
	 * Same as {@link #data(JavaScriptObject, KeyFunction)}.
	 * <p>
	 * 
	 * @param array
	 *            the data array to map to the selection
	 * @param keyFunction
	 *            the function to control how data is mapped to the selection
	 *            elements
	 * @return the update selection
	 */
	public final UpdateSelection data(final Object[] array, final KeyFunction<?> keyFunction) {
		return data(JsArrays.asJsArray(array), keyFunction);
	}

	/**
	 * Joins the specified array of data with the current selection using the
	 * default by-index key mapping.
	 * <p>
	 * 
	 * @param array
	 *            the data array to map to the selection
	 * @return the update selection
	 */
	public final UpdateSelection data(final byte[] array) {
		return data(JsArrayUtils.readOnlyJsArray(array));
	}

	/**
	 * Same as {@link #data(JavaScriptObject, KeyFunction)}.
	 * <p>
	 * 
	 * @param array
	 *            the data array to map to the selection
	 * @param keyFunction
	 *            the function to control how data is mapped to the selection
	 *            elements
	 * @return the update selection
	 */
	public final UpdateSelection data(final byte[] array, final KeyFunction<?> keyFunction) {
		return data(JsArrayUtils.readOnlyJsArray(array), keyFunction);
	}

	/**
	 * Joins the specified array of data with the current selection using the
	 * default by-index key mapping.
	 * <p>
	 * 
	 * @param array
	 *            the data array to map to the selection
	 * @return the update selection
	 */
	public final UpdateSelection data(final double[] array) {
		return data(JsArrayUtils.readOnlyJsArray(array));
	}

	/**
	 * Same as {@link #data(JavaScriptObject, KeyFunction)}.
	 * <p>
	 * 
	 * @param array
	 *            the data array to map to the selection
	 * @param keyFunction
	 *            the function to control how data is mapped to the selection
	 *            elements
	 * @return the update selection
	 */
	public final UpdateSelection data(final double[] array, final KeyFunction<?> keyFunction) {
		return data(JsArrayUtils.readOnlyJsArray(array), keyFunction);
	}

	/**
	 * Joins the specified array of data with the current selection using the
	 * default by-index key mapping.
	 * <p>
	 * 
	 * @param array
	 *            the data array to map to the selection
	 * @return the update selection
	 */
	public final UpdateSelection data(final float[] array) {
		return data(JsArrayUtils.readOnlyJsArray(array));
	}

	/**
	 * Same as {@link #data(JavaScriptObject, KeyFunction)}.
	 * <p>
	 * 
	 * @param array
	 *            the data array to map to the selection
	 * @param keyFunction
	 *            the function to control how data is mapped to the selection
	 *            elements
	 * @return the update selection
	 */
	public final UpdateSelection data(final float[] array, final KeyFunction<?> keyFunction) {
		return data(JsArrayUtils.readOnlyJsArray(array), keyFunction);
	}

	/**
	 * Joins the specified array of data with the current selection using the
	 * default by-index key mapping.
	 * <p>
	 * 
	 * @param array
	 *            the data array to map to the selection
	 * @return the update selection
	 */
	public final UpdateSelection data(final int[] array) {
		return data(JsArrayUtils.readOnlyJsArray(array));
	}

	/**
	 * Same as {@link #data(JavaScriptObject, KeyFunction)}.
	 * <p>
	 * 
	 * @param array
	 *            the data array to map to the selection
	 * @param keyFunction
	 *            the function to control how data is mapped to the selection
	 *            elements
	 * @return the update selection
	 */
	public final UpdateSelection data(final int[] array, final KeyFunction<?> keyFunction) {
		return data(JsArrayUtils.readOnlyJsArray(array), keyFunction);
	}

	/**
	 * Joins the specified array of data with the current selection using the
	 * default by-index key mapping.
	 * <p>
	 * 
	 * @param array
	 *            the data array to map to the selection
	 * @return the update selection
	 */
	public final UpdateSelection data(final long[] array) {
		return data(JsArrayUtils.readOnlyJsArray(array));
	}

	/**
	 * Same as {@link #data(JavaScriptObject, KeyFunction)}.
	 * <p>
	 * 
	 * @param array
	 *            the data array to map to the selection
	 * @param keyFunction
	 *            the function to control how data is mapped to the selection
	 *            elements
	 * @return the update selection
	 */
	public final UpdateSelection data(final long[] array, final KeyFunction<?> keyFunction) {
		return data(JsArrayUtils.readOnlyJsArray(array), keyFunction);
	}

	/**
	 * Joins the specified array of data with the current selection using the
	 * default by-index key mapping.
	 * <p>
	 * 
	 * @param array
	 *            the data array to map to the selection
	 * @return the update selection
	 */
	public final UpdateSelection data(final short[] array) {
		return data(JsArrayUtils.readOnlyJsArray(array));
	}

	/**
	 * Same as {@link #data(JavaScriptObject, KeyFunction)}.
	 * <p>
	 * 
	 * @param array
	 *            the data array to map to the selection
	 * @param keyFunction
	 *            the function to control how data is mapped to the selection
	 *            elements
	 * @return the update selection
	 */
	public final UpdateSelection data(final short[] array, final KeyFunction<?> keyFunction) {
		return data(JsArrays.asJsArray(array), keyFunction);
	}

	/**
	 * Same as #data(JavaScriptObject) for an {@link List} of objects.
	 * 
	 * @see #data(JavaScriptObject)
	 * @param array
	 * @return the {@link UpdateSelection}
	 */
	public final UpdateSelection data(final List<?> array) {
		return this.data(JsArrays.asJsArray(array));
	}

	/**
	 * Same as {@link #data(JavaScriptObject, KeyFunction)} for an {@link List}
	 * of objects.
	 * 
	 * @see #data(JavaScriptObject)
	 * @param array
	 *            the array
	 * @param keyFunction
	 *            the key function
	 * @return the {@link UpdateSelection}
	 */
	public final UpdateSelection data(final List<?> array, final KeyFunction<?> keyFunction) {
		return this.data(JsArrays.asJsArray(array), keyFunction);
	}

	// ================================ datum functions ========

	/**
	 * Sets the bound data to the specified value on all selected elements.
	 * <p>
	 * Unlike the {@link #data} methods, this method does not compute a join
	 * (and thus does not compute enter and exit selections). This method is
	 * implemented on top of {@link #property(String)}.
	 * <p>
	 * All elements in the selection are given the same data.
	 * <p>
	 * A null value will delete the bound data. This operator has no effect on
	 * the index.
	 * <p>
	 * See <a
	 * href="https://github.com/mbostock/d3/wiki/Selections#wiki-datum">datum
	 * </a>
	 * 
	 * @param the
	 *            function providing the datum
	 * @return the current selection
	 */
	public native final <T> Selection datum(DatumFunction<T> datumFunction)/*-{
		if (datumFunction == null) {
			return this.datum(null);
		}
		return this
				.datum(function(d, i) {
					return datumFunction.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
	}-*/;

	/**
	 * Sets the bound data to the specified value on all selected elements.
	 * <p>
	 * Unlike the {@link #data} methods, this method does not compute a join
	 * (and thus does not compute enter and exit selections). This method is
	 * implemented on top of {@link #property(String)}.
	 * <p>
	 * All elements in the selection are given the same data.
	 * <p>
	 * A null value will delete the bound data. This operator has no effect on
	 * the index.
	 * <p>
	 * See <a
	 * href="https://github.com/mbostock/d3/wiki/Selections#wiki-datum">datum
	 * </a>
	 * 
	 * @param object
	 *            the datum
	 * @return the current selection
	 */
	public native final Selection datum(Object object)/*-{
		return this.datum(object);
	}-*/;

	/**
	 * Returns the bound datum for the first non-null element in the selection.
	 * <p>
	 * This is generally useful only if you know the selection contains exactly
	 * one element.
	 * 
	 * @return the datum of the first non null element
	 */
	public native final Value datum()/*-{
		return {
			datum : this.datum()
		};
	}-*/;

	/**
	 * Filters the selection, returning a new selection that contains only the
	 * elements for which the specified selector is true. Like the built-in
	 * array filter method, the returned selection does not preserve the index
	 * of the original selection; it returns a copy with elements removed.
	 * <p>
	 * 
	 * @param selector
	 *            the CSS3 selector to be used as a filter
	 * @return a new selection containing the filtered elements
	 */
	public native final Selection filter(String selector)/*-{
		return this.filter(selector);
	}-*/;

	/**
	 * Filters the selection, returning a new selection that contains only the
	 * elements returned by the given function.
	 * <p>
	 * The given function is called for each element in the current selection,
	 * with the datum corresponding to the current element. Like the built-in
	 * array filter method, the returned selection does not preserve the index
	 * of the original selection; it returns a copy with elements removed.
	 * <p>
	 * 
	 * @param datumFunction
	 *            the function to be used as a filter
	 * @return a new selection containing the filtered elements
	 */
	public native final Selection filter(final DatumFunction<Element> datumFunction)/*-{
		return this
				.filter(function(d, i) {
					return datumFunction.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
	}-*/;

	/**
	 * Sorts the elements in the current selection according to the specified
	 * comparator function.
	 * <p>
	 * The comparator function is passed two data elements a and b to compare,
	 * returning either a negative, positive, or zero value. If negative, then a
	 * should be before b; if positive, then a should be after b; otherwise, a
	 * and b are considered equal and the order is arbitrary.
	 * <p>
	 * Note that the sort is not guaranteed to be stable; however, it is
	 * guaranteed to have the same behavior as your browser's built-in
	 * {@link Array#sort} method on arrays.
	 * <p>
	 * 
	 * @param comparator
	 *            the comparator to be used
	 */
	public native final Selection sort(final Comparator<Value> comparator)/*-{
		return this
				.sort(function(o1, o2) {
					return comparator.@java.util.Comparator::compare(Ljava/lang/Object;Ljava/lang/Object;)({datum:o1},{datum:o2});
				});
	}-*/;

	/**
	 * Re-inserts elements into the document such that the document order
	 * matches the selection order.
	 * <p>
	 * This is equivalent to calling sort() if the data is already sorted, but
	 * much faster.
	 */
	public native final Selection order()/*-{
		return this.order();
	}-*/;

	// ==================== TRANSITION =======
	/**
	 * Starts a {@link Transition} for the current selection. Transitions behave
	 * much like selections, except operators animate smoothly over time rather
	 * than applying instantaneously.
	 * <p>
	 * 
	 * @return the new transition
	 */
	public native final Transition transition()/*-{
		return this.transition();
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
					listener.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				};
		return this.on(eventType, l);
	}-*/;

	/**
	 * Adds or removes an event listener to each element in the current
	 * selection, for the specified type.
	 * <p>
	 * The type is a string event type name, such as "click", "mouseover", or
	 * "submit". You may use {@link BrowserEvents} constants for convenience.
	 * <p>
	 * The specified listener is invoked in the same manner as other operator
	 * functions, being passed the current datum d and index i, with the this
	 * context as the current DOM element.
	 * <p>
	 * To access the current event within a listener, use the global d3.event.
	 * The return value of the event listener is ignored.
	 * <p>
	 * If an event listener was already registered for the same type on the
	 * selected element, the existing listener is removed before the new
	 * listener is added. To register multiple listeners for the same event
	 * type, the type may be followed by an optional namespace, such as
	 * "click.foo" and "click.bar".
	 * <p>
	 * To remove a listener, pass null as the listener. To remove all listeners
	 * for a particular event type, pass null as the listener, and .type as the
	 * type, e.g. selection.on(".foo", null).
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
					listener.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
		return this.on(eventType, l, useCapture);
	}-*/;

}
