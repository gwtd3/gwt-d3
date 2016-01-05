package com.github.gwtd3.api.functions;

import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Value;
import com.google.gwt.dom.client.Element;

/**
 * A function taking an element and the index of the element in the selection,
 * returning a boolean primitive value.
 * <p>
 * Instances of this function are mainly used with mutator functions of {@link Selection}.
 *
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 *
 */
public interface BooleanDatumFunction {

    /**
     * Apply the function for the given {@link Element} at the specified index
     * of the {@link Selection} mapped to the given {@link Datum}.
     * <p>
     * Note that if no datum is mapped to the element, {@link Datum#isUndefined()} will return true.
     *
     * @param context
     *            the current element, may be irrelevant or null in some context.
     * @param d
     *            the datum
     * @param index
     *            the index of the element in the selection
     * @return a boolean value
     */
    boolean apply(Element context, Value d, int index);
}
