/**
 * 
 */
package com.github.gwtd3.ui.svg;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.core.Selection;
import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.CssResource;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class SVGDocumentWidget extends SVGWidget implements ISVGDocument {

    private Element defs;
    protected SVGResources resources;
    protected SVGStyles styles;

    /**
     * @param e
     */
    protected SVGDocumentWidget() {
        super("svg");
    }

    /**
     * @param e
     */
    protected SVGDocumentWidget(final SVGResources resources) {
        super("svg");
        this.resources = resources;
        this.styles = resources.styles();
        this.addStyleName(styles.svg());
    }

    @Override
    protected void onSelectionAttached(final Selection selection) {
        super.onSelectionAttached(selection);
        if (styles != null) {
            inject(styles);
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
}
