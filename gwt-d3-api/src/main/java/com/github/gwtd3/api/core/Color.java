/**
 * 
 */
package com.github.gwtd3.api.core;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Color extends JavaScriptObject {

	protected Color() {
		super();
	}

	/**
	 * Converts to a RGB hexadecimal string, such as "#f7eaba".
	 * 
	 * @return hexa representation of the color
	 */
	public final native String toHexaString()/*-{
		return this.toString();
	}-*/;

}
