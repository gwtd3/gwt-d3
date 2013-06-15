package com.github.gwtd3.api.scales;

import com.github.gwtd3.api.Scales;
import com.google.gwt.core.client.JsArrayInteger;

/**
 * Ordinal {@link Scales} have a discrete domain, such as a set of names or categories.
 * 
 * @author SCHIOCA
 * 
 */
public class OrdinalScale extends Scale<OrdinalScale> {

    protected OrdinalScale() {}

    public native final OrdinalScale rangeRoundBands(JsArrayInteger interval, double padding) /*-{
		return this.rangeRoundBands(interval, padding);
    }-*/;

    public native final double rangeBand() /*-{
		return this.rangeBand();
    }-*/;

}
