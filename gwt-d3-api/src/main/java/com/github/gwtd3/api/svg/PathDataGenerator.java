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

import java.util.List;

import com.github.gwtd3.api.IsFunction;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.functions.DatumFunction;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * 
 * A {@link PathDataGenerator} is an object containing information to simplify
 * the construction of the <code>d</code> attribute for SVG <code>path</code>
 * element, allowing users to generate complex shapes.
 * <p>
 * The d attribute of SVG <code>path</code> defines the path data, which is a
 * mini-language of path commands, such as moveto (M), lineto (L) and closepath
 * (Z). {@link PathDataGenerator}s are Javascript functions that generate these
 * commands. <br>
 * From a Javascript point of view, a {@link PathDataGenerator} is both an
 * object containing properties and a function that can be called to generate
 * the path.
 * <p>
 * When passing the generator to the <code>d</code> attribute of a
 * <code>path</code> selection, the function represented by the generator is
 * called for each datum of the selection data. The function takes the datum in
 * argument and return the path.
 * <p>
 * Each generator specifies a default way of using the datum to create the path,
 * but generally speaking, the default behaviour can be overriden by providing
 * {@link DatumFunction} for each generator attribute. Please refer to generator
 * subclass documention for more information.
 * <p>
 * 
 * Usage:
 * <p>
 * 
 * <pre>
 * {@code 
 * PathDataGenerator pathGenerator = ... 
 * Selection g = D3.select("body").append("svg").append("g"); 
 * g.append("path").attr("d", pathGenerator); 
 * 
 * }
 * </pre>
 * <p>
 * 
 * @see <a
 *      href="https://github.com/mbostock/d3/wiki/SVG-Shapes#path-data-generators">Official
 *      API</a>
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public abstract class PathDataGenerator extends JavaScriptObject implements
		IsFunction {

	protected PathDataGenerator() {
	}

	/**
	 * Generate the path data as String using the given data object in argument.
	 * <p>
	 * The data object must contains valid attributes for the corresponding
	 * generator to work (see each subclass documentation).
	 * <p>
	 * 
	 * 
	 * @param data
	 *            an array of data
	 * @return the generated path data
	 */
	public final native String generate(JavaScriptObject data) /*-{
		return this(data);
	}-*/;

	/**
	 * Alias for {@link #generate(JavaScriptObject)}.
	 * 
	 * @param data
	 *            the data
	 * @return generated path data
	 */
	public final String generate(List<?> data) {
		return generate(Array.fromIterable(data));
	}

	/**
	 * Alias for {@link #generate(JavaScriptObject)}.
	 * 
	 * @param data
	 *            an array of data
	 * @return the generated path data
	 */
	public final String generate(double... data) {
		return generate(Array.fromDoubles(data));
	}

	/**
	 * Generate the path data as String using the given data object in argument.
	 * <p>
	 * The data object must contains valid attributes for the corresponding
	 * generator to work (see each subclass documentation).
	 * <p>
	 * 
	 * Apply the function using the given object in argument.
	 * <p>
	 * The object argument must provide missing attributes expected by the path
	 * generator. For instance, if this path generator is a {@link Line}, the
	 * provided object could be an array of {x,y} objects. Please refer to
	 * subclass documentation for more information.
	 * 
	 * 
	 * @param data
	 *            the array of data
	 * @param index
	 *            an index to be passed to each accessor functions
	 * @return the generated path data
	 */
	public final native String generate(JavaScriptObject data, int index) /*-{
		return this(data, index);
	}-*/;
}
