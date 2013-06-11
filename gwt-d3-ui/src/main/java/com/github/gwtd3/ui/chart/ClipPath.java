package com.github.gwtd3.ui.chart;

import com.github.gwtd3.api.core.Datum;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.functions.DatumFunction;
import com.google.gwt.dom.client.Element;

/**
 * Represent a clip path in a SVG document.
 * <p>
 * 
 * 
 * @author SCHIOCA
 * 
 */
public class ClipPath {

    private final String id;

    public ClipPath(final String id) {
        super();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    /**
     * Apply the clip path on the given node.
     * 
     * @param e
     */
    public void apply(final Element e) {
        e.setAttribute("clip-path", "url(#" + getId() + ")");
    }

    /**
     * Apply the clip path on the elements of the selection.
     * 
     * @param e
     */
    public void apply(final Selection s) {
        s.attr("clip-path", new DatumFunction<String>() {
            @Override
            public String apply(final Element context, final Datum d, final int index) {
                return "url(#" + getId() + ")";
            }
        });
    }
}
