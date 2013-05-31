package com.github.gwtd3.api.dsv;

import com.github.gwtd3.api.core.Value;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Each row of a DSV file is represented by a {@link DsvRow}.
 * 
 * @author <a href="mailto:eric.citaire@gmail.com">Eric Citaire</a>
 * 
 */
public class DsvRow extends JavaScriptObject {
	protected DsvRow() {
		super();
	}

	/**
	 * Generic method to get the value of a named field.
	 * 
	 * @param field
	 * @return
	 */
	public final native Value get(String field) /*-{
		return {
			datum : this[field]
		};
	}-*/;
}
