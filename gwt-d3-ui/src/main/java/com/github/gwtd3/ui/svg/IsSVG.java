/**
 * 
 */
package com.github.gwtd3.ui.svg;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public interface IsSVG extends IsWidget {

	/**
	 * @return the SVG document this widget belongs to.
	 */
	ISVGDocument getDocument();

	/**
	 * @return the root SVG document this widget belongs to.
	 */
	ISVGDocument getRootDocument();
}
