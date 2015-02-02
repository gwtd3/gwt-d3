package com.github.gwtd3.api;

import com.google.gwt.user.client.Event;

public class D3Event extends Event {

    protected D3Event() {
        super();
    }

    /**
     * Returns the source event that triggered this instance.
     * <p>
     * This
     *
     * @return the source event
     */
    public native final <T extends Event> T sourceEvent() /*-{
		return this.sourceEvent;
    }-*/;
}
