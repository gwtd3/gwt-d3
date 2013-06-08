/**
 * 
 */
package com.github.gwtd3.ui;

/**
 * A component or widget that is able to be drawn from a data model.
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public interface Drawable {

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
