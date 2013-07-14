package com.github.gwtd3.api.core;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 *
 */
public class Random extends JavaScriptObject{

	protected Random() {
	}
	
	public static final native Random get()/*-{
		return $wnd.d3.random;
	}-*/;
	
	
}
