/**
 * 
 */
package com.github.gwtd3.ui.svg;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.ui.Drawable;
import com.github.gwtd3.ui.HasD3Selection;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * A container svf
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Container extends ComplexPanel implements HasD3Selection, Drawable {

	protected Container(final Element e) {
		super();
		setElement(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.user.client.ui.Panel#add(com.google.gwt.user.client.ui.Widget)
	 */
	@Override
	public void add(final Widget child) {
		add(child, getElement());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.user.client.ui.Widget#onLoad()
	 */
	@Override
	protected void onLoad() {
		super.onLoad();
		onSelectionAttached();
		redraw();
	}

	/**
	 * In this method you can safely call {@link #select()} to get and modify the selection.
	 * 
	 */
	protected void onSelectionAttached() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.gwtd3.ui.Drawable#redraw()
	 */
	@Override
	public void redraw() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.gwtd3.ui.HasD3Selection#select()
	 */
	@Override
	public Selection select() {
		if (!isAttached()) {
			throw new IllegalStateException("cannot select a widget before it is attached");
		}
		return D3.select(this);
	}
}
