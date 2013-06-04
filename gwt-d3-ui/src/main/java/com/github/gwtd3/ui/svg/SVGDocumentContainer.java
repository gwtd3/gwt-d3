package com.github.gwtd3.ui.svg;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.core.Selection;
import com.google.common.base.Preconditions;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.CssResource;

/**
 * A widget serving as a container for SVG graphics.
 * <p>
 * Use this widget when you want to create SVG graphics from outside of the widget itself, for instance.
 * <p>
 * 
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class SVGDocumentContainer extends SVGContainer implements ISVGDocument {

    protected SVGResources resources;
    protected SVGStyles styles;
    private Element defs;

    public SVGDocumentContainer() {
        this((SVGResources) GWT.create(SVGResources.class));

    }

    public SVGDocumentContainer(final SVGResources resources) {
        super("svg");
        Preconditions.checkNotNull(resources);
        this.resources = resources;
        this.styles = this.resources.styles();
    }

    @Override
    public void setWidth(final String width) {
        getElement().setAttribute("width", width);
    }

    @Override
    public void setHeight(final String height) {
        getElement().setAttribute("height", height);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.github.gwtd3.ui.D3Widget#onSelectionAttached(com.github.gwtd3.api.core.Selection)
     */
    protected void onSelectionAttached(final Selection selection) {
        // in

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gwt.user.client.ui.Widget#onLoad()
     */
    @Override
    protected void onLoad() {
        super.onLoad();
        if (resources != null) {
            inject(styles);
            select().classed(styles.svg(), true);
        }
    }

    /**
     * @return a D3 selection representing the &lt;defs&gt; element.
     */
    @Override
    public Selection defs() {
        if (defs == null) {
            defs = getElement().insertFirst(createSVGElement("defs")).cast();
        }
        return D3.select(defs);
    }

    /**
     * Inject the given stylesheet as a new <code>style</code> element
     * in the <code>defs</code> of this document.
     * 
     * @param resource
     *            the css stylesheet to inject
     */
    @Override
    public void inject(final CssResource resource) {
        String styleContents = "\n" + resource.getText() + "\n";
        defs().append("style").attr("type", "text/css").text(styleContents);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.github.gwtd3.ui.HasD3Selection#select()
     */
    @Override
    public Selection select() {
        return D3.select(this);
    }

    /**
     * Find an element with the given id in the &lt;defs&gt; element
     * 
     * @param id
     * @return
     */
    public Selection getDefById(final String id) {
        return defs().select("#" + id);
    }

}
