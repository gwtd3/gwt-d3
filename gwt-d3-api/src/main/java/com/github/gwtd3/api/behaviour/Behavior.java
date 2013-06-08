/**
 * 
 */
package com.github.gwtd3.api.behaviour;

import com.github.gwtd3.api.core.Selection;
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
	 * Create a new {@link Drag} behavior, that you will conigure and apply to a
	 * {@link Selection}.
	 * 
	 * @return the drag behaviour
	 */
	public final native Drag drag()/*-{
		return this.drag();
	}-*/;

}
