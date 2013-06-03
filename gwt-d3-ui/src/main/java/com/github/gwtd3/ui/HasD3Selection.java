/**
 * 
 */
package com.github.gwtd3.ui;

import com.github.gwtd3.api.core.Selection;

/**
 * Instances of this interface has the ability to provide a D3 {@link Selection}.
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public interface HasD3Selection {

	/**
	 * Returns a D3 {@link Selection}.
	 * 
	 * @return the selection
	 */
	public Selection select();

}
