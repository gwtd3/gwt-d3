/**
 * 
 */
package com.github.gwtd3.demo.client.testcases.selection;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.demo.client.test.AbstractBehaviourTest;
import com.github.gwtd3.demo.client.test.AbstractTestCase;

import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public abstract class AbstractSelectionTest extends AbstractTestCase {

	
	
	/**
	 * Clear the sandbox, add the given widget to the sandbox
	 * and return the D3 selection corresponding
	 * to the added widget.
	 * @param w
	 * @return the selection containing only the given widget
	 */
	protected Selection givenASimpleSelection(Widget w){
		clearSandbox();
		sandbox.add(w);
		return D3.select(w.getElement());
	}
	
	/**
	 * Clear the sandbox, add all the widgets and return
	 * a selection containing all the given widgets.
	 * @param widgets
	 * @return
	 */
	protected Selection givenAMultipleSelection(Widget...widgets){
		clearSandbox();
		for (Widget widget : widgets) {
			sandbox.add(widget);
		}
		return D3.select(sandbox).selectAll("*");
	}

}
