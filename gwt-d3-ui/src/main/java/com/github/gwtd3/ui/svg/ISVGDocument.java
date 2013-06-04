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
}
