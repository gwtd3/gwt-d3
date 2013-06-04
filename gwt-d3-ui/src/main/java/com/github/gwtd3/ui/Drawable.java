/**
 * 
 */
package com.github.gwtd3.ui;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * A widget that is able to be drawn from a data model.
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public interface Drawable extends IsWidget {

	/**
	 * Update the drawing with the current data.
	 */
	public void redraw();

	/**
	 * Schedule a redraw once the GWT user code
	 * has finished executed.
	 */
	public void scheduleRedraw();
}
