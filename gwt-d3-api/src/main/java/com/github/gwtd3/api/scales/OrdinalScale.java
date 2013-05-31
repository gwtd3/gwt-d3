package com.github.gwtd3.api.scales;

import com.google.gwt.core.client.JsArrayInteger;

public class OrdinalScale extends Scale<OrdinalScale> {

    protected OrdinalScale() {}

	public native final OrdinalScale rangeRoundBands(JsArrayInteger interval, double padding) /*-{
		return this.rangeRoundBands(interval, padding);
	}-*/;

	public native final double rangeBand() /*-{
		return this.rangeBand();
	}-*/;

}
