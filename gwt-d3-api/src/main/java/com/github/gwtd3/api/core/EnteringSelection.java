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
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.functions.DatumFunction;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;

/**
 * A selection returned by a call to {@link UpdateSelection#enter()}.
 * <p>
 * The entering selection only contains placeholder nodes for each data element for which no corresponding existing DOM element was found in the current selection.
 * <p>
 * In addition, the entering selection only defines append, insert and select operators; you must use these operators to instantiate the entering nodes before modifying any
 * content.
 * <p>
 * Note that the enter operator merely returns a reference to the entering selection, and it is up to you to add the new nodes.
 * <p>
 * The enter selection <strong>merges into the update selection</strong> when you append or insert. This approach reduces code duplication between enter and update. Rather than
 * applying operators to both the enter and update selection separately, you can now apply them to the update selection after entering the nodes. In the rare case that you want to
 * run operators only on the updating nodes, you can run them on the update selection before entering new nodes.
 * <p>
 * 
 * @see <a href="https://github.com/mbostock/d3/wiki/Selections#wiki-enter">Official API</a>
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class EnteringSelection extends JavaScriptObject {

	protected EnteringSelection() {
	}

	/**
	 * Appends a new element with the specified name as the last child of each
	 * element in the current selection.
	 * <p>
	 * Returns a new selection containing the appended elements.
	 * <p>
	 * Each new element inherits the data of the current elements, if any, in the same manner as select for subselections.
	 * <p>
	 * The name must be specified as a constant, though in the future we might allow appending of existing elements or a function to generate the name dynamically.
	 * 
	 * The element's tag name may have a namespace prefix, such as "svg:text" to create a "text" element in the SVG namespace. By default, D3 supports svg, xhtml, xlink, xml and
	 * xmlns namespaces. Additional namespaces can be registered by adding to d3.ns.prefix.
	 * 
	 * @param name
	 *            the name of the new element to append
	 * @return a new selection containing the appended elements
	 */
	public native final Selection append(String name)/*-{
		return this.append(name);
    }-*/;

	/**
	 * Insert a new element before any other existing children.
	 * <p>
	 * The method returns a new Selection containing the inserted element.
	 * 
	 * @param string
	 * @return a new selection containing the inserted element
	 */
	public final Selection prepend(final String name) {
		return insert(name, ":first-child");
	}

	/**
	 * For each element in the current selection, selects the first descendant
	 * element that matches the specified selector string.
	 * <p>
	 * If no element matches the specified selector for the current element, the element at the current index will be null in the returned selection; operators (with the exception
	 * of {@link Selection#data}) automatically skip null elements, thereby preserving the index of the existing selection.
	 * <p>
	 * If the current element has associated data, this data is inherited by the returned subselection, and automatically bound to the newly selected elements.
	 * <p>
	 * If multiple elements match the selector, only the first matching element in document traversal order will be selected.
	 * <p>
	 * 
	 * @param selector
	 *            the selector
	 * @return the returned selection, containing at most only one element
	 */
	public final native Selection select(String selector)/*-{
		return this.select(selector);
    }-*/;

	/**
	 * Creates a new selection containing the first non-null element returned by the given function.
	 * <p>
	 * The function is evaluated for each element in the current selection (in order), being passed the current datum d and the current index i.
	 * <p>
	 * 
	 * @param func
	 * @return
	 */
	public final native Selection select(DatumFunction<Element> func)/*-{
		return this
				.select(function(d, i) {
					return func.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
	}-*/;

	/**
	 * Inserts a new element with the specified name before the element matching
	 * the specified before selector, for each element in the current selection.
	 * <p>
	 * Returns a new selection containing the inserted elements.
	 * <p>
	 * If the before selector does not match any elements, then the new element will be the last child as with {@link #append(String)}.
	 * <p>
	 * Each new element inherits the data of the current elements (if any), in the same manner as select for subselections. The name and before selector must be specified as
	 * constants, though in the future we might allow inserting of existing elements or a function to generate the name or selector dynamically.
	 * <p>
	 * For instance, insert("div", ":first-child") will prepend child div nodes to the current selection.
	 * <p>
	 * The element's tag name may have a namespace prefix, such as "svg:text" to create a "text" element in the SVG namespace. By default, D3 supports svg, xhtml, xlink, xml and
	 * xmlns namespaces. Additional namespaces can be registered by adding to d3.ns.prefix.
	 * 
	 * @param name
	 * @param beforeSelector
	 *            a selector to select element to insert the new element before
	 * @return a new selection containing the inserted elements
	 */
	public native final Selection insert(String name, String beforeSelector)/*-{
		return this.insert(name, beforeSelector);
    }-*/;

	/**
	 * Returns true if the current selection is empty; a selection is empty if
	 * it contains no non-null elements.
	 * 
	 * @return true if selection is empty, false otherwise.
	 */
	public final native boolean empty()/*-{
		return this.empty();
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
	 * Return the parentNode property of the i-th group of the selection.
	 * <p>
	 * A parentNode is the node where element will be added using append.
	 * <p>
	 * When using {@link D3#select}, {@link D3#selectAll} there is only one group and its parent node is the document root element (the html tag).
	 * <p>
	 * When creating a selection from an initial selection using {@link Selection#select}, (TODO: check that) there may be more than one group, but the parent node of each group is
	 * the document root element (the html tag).
	 * <p>
	 * When creating a selection from an initial selection using {@link Selection#selectAll}, there may be more than one group, and the parent node of each group is the
	 * corresponding node of the initial selection.
	 * <p>
	 * 
	 * @param i
	 *            the index of the group
	 */
	public native final Element parentNode(int i)/*-{
		return this[i].parentNode;
	}-*/;

	/**
	 * Return the number of groups in the selection.
	 * <p>
	 * The notion of group is only relevant to <a href="http://bost.ocks.org/mike/nest/">nested selections</a>.
	 * 
	 * <p>
	 * Selections created by {@link D3#select} and {@link D3#selectAll} always have only 1 group.
	 * <p>
	 * Selections created from an initial selection with {@link Selection#select} or {@link Selection#selectAll} have a group count equals to the number of nodes in the initial
	 * selection.
	 * <p>
	 * 
	 * @return the number of groups
	 */
	public final int groupCount() {
		return asElementArray().length();
	}

	/**
	 * Return the internal structure of the selection.
	 * <p>
	 * 
	 * @return this selection as an array of array of elements
	 */
	public final Array<Array<Element>> asElementArray() {
		return this.cast();
	}
}
