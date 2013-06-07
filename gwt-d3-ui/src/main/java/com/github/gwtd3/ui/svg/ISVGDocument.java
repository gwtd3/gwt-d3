/**
 * 
 */
package com.github.gwtd3.ui.svg;

import com.github.gwtd3.api.core.Selection;
import com.google.gwt.resources.client.CssResource;

/**
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public interface ISVGDocument {

    /**
     * Inject the given CSS stylesheet
     * into the document.
     * 
     * 
     * @param resource
     */
    public void inject(CssResource resource);

    /**
     * @return a selection containing the defs element
     */
    public Selection defs();

    /**
     * Set the dimension of the user space.
     * 
     * @param x the upper left corner x
     * @param y the upper left corner y
     * @param width the width of the box
     * @param height the height of the box
     */
    public void setViewBox(int x, int y, int width, int height);

}
