package com.github.gwtd3.ui;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.core.Selection;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

/**
 * Base class for widget that use {@link D3}.
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class D3Widget extends Widget {

    public D3Widget() {
        super();
    }

    public D3Widget(final Element element) {
        super();
        setElement(element);
    }

    /**
     * Returns a new d3 selection representing
     * the {@link Element} of this widget.
     * <p>
     * 
     * @return the selection
     */
    public Selection select() {
        return D3.select(this);
    }
}
