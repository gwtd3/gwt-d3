/**
 * 
 */
package com.github.gwtd3.api.svg;

import com.github.gwtd3.api.IsFunction;
import com.github.gwtd3.api.functions.DatumFunction;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * 
 * A {@link PathDataGenerator} is an object containing information 
 * to simplify the construction of the <code>d</code> attribute for SVG <code>path</code> element,
 * allowing users to generate complex shapes.
 * <p>
 * The d attribute of SVG <code>path</code> defines the path data, which is a mini-language of path commands, 
 * such as moveto (M), lineto (L) and closepath (Z).
 * {@link PathDataGenerator}s are Javascript functions that generate these commands. <br>
 * From a Javascript point of view, a {@link PathDataGenerator} is both an object containing properties 
 * and a function that can be called to generate the path.
 * <p>
 * When passing the generator to the <code>d</code> attribute of a <code>path</code> selection, 
 * the function represented by the generator is called for each datum of the selection
 * data. The function takes the datum in argument and return the path.
 * <p>
 * Each generator specifies a default way of using the datum to create the path, but generally speaking, 
 * the default behaviour can be overriden by providing {@link DatumFunction}
 * for each generator attribute. Please refer to generator subclass documention for more information.
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
 * @see <a href="https://github.com/mbostock/d3/wiki/SVG-Shapes#path-data-generators">Official API</a>
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public abstract class PathDataGenerator extends JavaScriptObject implements IsFunction {

	protected PathDataGenerator() {
	}

	/**
	 * Generate the path data as String using the given data object in argument.
	 * <p>
	 * The data object must contains valid attributes for the corresponding generator to work (see each subclass documentation).
	 * <p>
	 * 
	 * 
	 * @param javaScriptObject
	 * @return the generated path data
	 */
	public final native String generate(JavaScriptObject data) /*-{
		return this(data);
	}-*/;

	/**
	 * Generate the path data as String using the given data object in argument.
	 * <p>
	 * The data object must contains valid attributes for the corresponding generator to work (see each subclass documentation).
	 * <p>
	 * 
	 * Apply the function using the given object in argument. 
	 * <p>
	 * The object argument must provide missing attributes expected 
	 * by the path generator. For instance, if this path generator
	 * is a {@link Line}, the provided object could be
	 * an array of {x,y} objects. Please refer to subclass documentation
	 * for more information.
	 * 
	 * 
	 * @param javaScriptObject
	 * @param index
	 *            an index to be passed to each accessor functions
	 * @return the generated path data
	 */
	public final native String generate(JavaScriptObject data, int index) /*-{
		return this(data, index);
	}-*/;
}
