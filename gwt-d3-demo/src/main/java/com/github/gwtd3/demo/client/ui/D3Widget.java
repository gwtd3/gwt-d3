/**
 * 
 */
package com.github.gwtd3.demo.client.ui;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.core.Selection;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

/**
 * A base widget providing {@link D3} functionalities.
 * This widget is just a div.
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class D3Widget extends Widget {

	private final Selection selection;

	public D3Widget() {
		this(Document.get().createDivElement());
	}

	public D3Widget(final Element wrapped) {
		super();
		setElement(wrapped);
		selection = D3.select(wrapped);

	}

	/**
	 * Return the D3 {@link Selection} representing this widget,
	 * a DIV element,
	 * allowing users to work with the element widget.
	 * 
	 * @return the selection
	 */
	public Selection selection() {
		return selection;
	}

}
