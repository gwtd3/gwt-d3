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
package com.github.gwtd3.api.core;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Nesting allows elements in an array to be grouped into a hierarchical tree
 * structure; think of it like the GROUP BY operator in SQL, except you can have
 * multiple levels of grouping, and the resulting output is a tree rather than a
 * flat table.
 * <p>
 * The levels in the tree are specified by key functions. The leaf
 * nodes of the tree can be sorted by value, while the internal nodes can be
 * sorted by key.
 * <p>
 * An optional rollup function will collapse the elements in each
 * leaf node using a summary function.
 * <p>
 * The nest operator (the object returned by
 * {@link D3#nest(}) is reusable, and does not retain any references to the data that is
 * nested.
 * <p>
 * For example, consider the following tabular data structure of Barley yields,
 * from various sites in Minnesota during 1931-2:
 * 
 * var yields = [{yield: 27.00, variety: "Manchuria", year: 1931, site:
 * "University Farm"}, {yield: 48.87, variety: "Manchuria", year: 1931, site:
 * "Waseca"}, {yield: 27.43, variety: "Manchuria", year: 1931, site: "Morris"},
 * ...] To facilitate visualization, it may be useful to nest the elements first
 * by year, and then by variety, as follows:
 * 
 * var nest = d3.nest() .key(function(d) { return d.year; }) .key(function(d) {
 * return d.variety; }) .entries(yields); This returns a nested array. Each
 * element of the outer array is a key-values pair, listing the values for each
 * distinct key:
 * 
 * [{key: 1931, values: [ {key: "Manchuria", values: [ {yield: 27.00, variety:
 * "Manchuria", year: 1931, site: "University Farm"}, {yield: 48.87, variety:
 * "Manchuria", year: 1931, site: "Waseca"}, {yield: 27.43, variety:
 * "Manchuria", year: 1931, site: "Morris"}, ...]}, {key: "Glabron", values: [
 * {yield: 43.07, variety: "Glabron", year: 1931, site: "University Farm"},
 * {yield: 55.20, variety: "Glabron", year: 1931, site: "Waseca"}, ...]}, ...]},
 * {key: 1932, values: ...}] The nested form allows easy iteration and
 * generation of hierarchical structures in SVG or HTML.
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Nest extends JavaScriptObject {

	protected Nest() {
	}


}
