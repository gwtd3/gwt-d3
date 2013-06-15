package com.github.gwtd3.api.time;

import com.google.gwt.core.client.JsArray;

public class TimeScale extends com.github.gwtd3.api.scales.Scale<TimeScale> {

    protected TimeScale() {
        super();
    }

    /**
     * Set the scale's output range.
     * <p>
     * Sets the scale's output range to the specified array of values. The array must contain two or more values, to
     * match the cardinality of the input domain. The elements in the given array need not be numbers; any value that is
     * supported by the underlying interpolator will work. However, numeric ranges are required for the invert operator.
     * 
     * @param values the array of values.
     * @return the current scale
     */
    public final native TimeScale range(JsArray<?> values) /*-{
		return this.range(values);
    }-*/;

}
