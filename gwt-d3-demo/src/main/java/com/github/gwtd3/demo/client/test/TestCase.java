/**
 * 
 */
package com.github.gwtd3.demo.client.test;

import com.google.gwt.user.client.ui.ComplexPanel;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public interface TestCase {

	public void setUp(ComplexPanel sandbox);

	public void doTest(ComplexPanel sandbox);

	public void tearDown(ComplexPanel sandbox);
	
}
