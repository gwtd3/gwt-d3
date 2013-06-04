package com.github.gwtd3.ui;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.ui.svg.DrawableSupport;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;

/**
 * Base class for widget that use {@link D3}.
 * <p>
 * A d3 {@link Selection} containing the element backed by this widget can be obtain with the {@link #select()} method.
 * <p>
 * Subclasses of {@link D3Widget} should initialize the selection using the {@link #onSelectionAttached(Selection)}
 * methods.
 * <p>
 * 
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class D3Widget extends Widget implements HasD3Selection, Drawable {

    private final DrawableSupport support = new DrawableSupport(this);

    public D3Widget(final String tagName) {
        this(DOM.createElement(tagName));
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
    @Override
    public Selection select() {
        if (!isAttached()) {
            // throw new IllegalStateException("Cannot get the selection of a widget that is not attached.");
        }
        return D3.select(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gwt.user.client.ui.Widget#onLoad()
     */
    @Override
    protected void onLoad() {
        super.onLoad();
        onSelectionAttached(select());
    }

    /**
     * Call once the selection is created,
     * i.e once the element wrapping the selection
     * is attached to the DOM.
     * <p>
     * This method is the right place to initialize the selection.
     * 
     * 
     * @param selection
     *            the selection
     */
    protected void onSelectionAttached(final Selection selection) {

    }

    @Override
    public void redraw() {}

    @Override
    public void scheduleRedraw() {
        support.scheduleRedraw();
    }

}
