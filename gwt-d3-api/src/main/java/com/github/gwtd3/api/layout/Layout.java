package com.github.gwtd3.api.layout;

import com.google.gwt.core.client.JavaScriptObject;

public class Layout<L extends Layout<L>> extends JavaScriptObject {
	protected Layout() {
		super();
	}

	public final native ChordLayout chord() /*-{
		return this.chord();
	}-*/;
}
