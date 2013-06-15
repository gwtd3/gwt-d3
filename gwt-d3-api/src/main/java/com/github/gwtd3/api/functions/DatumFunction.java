/**
 * 
 */
package com.github.gwtd3.api.functions;

import com.github.gwtd3.api.core.Datum;
import com.github.gwtd3.api.core.Selection;
import com.google.gwt.dom.client.Element;

/**
 * A function taking an element and the index of the element in the selection,
 * returning a value of type T.
 * <p>
 * This function must be passed to mutator functions of {@link Selection} when
 * you knows that nodes are not bound to any data.
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public interface DatumFunction<T> {

	/**
	 * Apply the function for the given {@link Element} at the specified index
	 * of the {@link Selection} mapped to the given {@link Datum}.
	 * <p>
	 * Note that if no datum is mapped to the element,
	 * {@link Datum#isUndefined()} will return true.
	 * 
	 * @param context
	 *            the current element
	 * @param d
	 *            the datum
	 * @param index
	 *            the index of the element in the selection
	 * @return a result to be applied
	 */
	public T apply(Element context, Datum d, int index);
}
