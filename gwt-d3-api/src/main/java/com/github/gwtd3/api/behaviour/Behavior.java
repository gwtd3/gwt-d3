/**
 * 
 */
package com.github.gwtd3.api.behaviour;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * The behaviour module.
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Behavior extends JavaScriptObject {

	protected Behavior() {
	}

	/**
	 * Create a new drag behaviour.
	 * 
	 * @return the drag
	 */
	public final native Drag drag()/*-{
		return this.drag();
	}-*/;

}
