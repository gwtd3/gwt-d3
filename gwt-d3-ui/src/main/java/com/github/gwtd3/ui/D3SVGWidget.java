package com.github.gwtd3.ui;

import com.github.gwtd3.api.core.Selection;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.Shared;

/**
 * A d3 widget is basically a SVG element wrapped in a D3 {@link Selection}.
 * <p>
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class D3SVGWidget extends D3Widget {

    protected Resources resources;
    protected Styles styles;
    private Selection svg;
    private Selection defsSelection;

    private static Resources createDefaultResources() {
        return GWT.create(Resources.class);
    }

    public static interface Resources extends ClientBundle {
        @Source("AbstractSVGD3Widget.css")
        Styles getStyles();
    }

    @Shared
    public static interface Styles extends CssResource {
        /**
         * The class to apply to the svg element
         * 
         * @return
         */
        public String svg();
    }

    public D3SVGWidget() {
        this(createDefaultResources());

    }

    public D3SVGWidget(final Resources resources) {
        super();
        // Why a DIV ?
        setElement(Document.get().createElement("div"));
        this.svg = select().append("svg");
        if (resources != null) {
            this.resources = resources;
            this.styles = this.resources.getStyles();
            inject(styles);
            svg.classed(styles.svg(), true);
        }
    }

    @Override
    public void setWidth(final String width) {
        super.setWidth(width);

        svg.attr("width", width);
    }

    @Override
    public void setHeight(final String height) {
        super.setHeight(height);

        svg.attr("height", height);
    }

    /**
     * @return a D3 selection representing the &lt;defs&gt; element.
     */
    public Selection defs() {
        if (defsSelection == null) {
            defsSelection = svg.prepend("defs");
        }
        return defsSelection;
    }

    public Selection svg() {
        return svg;
    }

    /**
     * Inject the given stylesheet into the svg defs style attribute. If a
     * previous stylesheet was injected before, it will be replaced by the new
     * one.
     * 
     * @param resource
     *            the css stylesheet to inject
     */
    protected void inject(final CssResource resource) {
        String styleContents = "\n" + resource.getText() + "\n";
        // String styleContents = "\n<![CDATA[\n" + resource.getText() +
        // "\n]]>\n";
        defs().append("style").attr("type", "text/css").html(styleContents);
    }

}
