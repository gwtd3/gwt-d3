package com.github.gwtd3.api.time;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsDate;

public class Format extends JavaScriptObject {
	
	protected Format() {
		super();
	}
	
	/**
	 * Parse a string into a date.
	 * <p>
	 * Parses the specified string, returning the corresponding date object. If
	 * the parsing fails, returns null. Unlike "natural language" date parsers
	 * (including JavaScript's built-in parse), this method is strict: if the
	 * specified string does not exactly match the associated format specifier,
	 * this method returns null.
	 * 
	 * @param s the string to parse.
	 * @return the corresponding date object.
	 */
	public final native JsDate parse(String s) /*-{
		return this.parse(s);
	}-*/;

}
