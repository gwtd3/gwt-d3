/**
 * 
 */
package com.github.gwtd3.ui;

/**
 * A widget that is able to be drawn from a data model.
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public interface Drawable {

	/**
	 * Update the drawing with the current data.
	 */
	public void redraw();
}
