package com.github.gwtd3.api.dsv;

import com.google.gwt.core.client.JsArrayString;

/**
 * This accessor function is invoked for each row in a DSV file, being passed
 * the current row and index as two arguments. The return value of the function
 * replaces the element in the returned array of rows; if the function returns
 * null, the row is stripped from the returned array of rows. In effect, the
 * accessor is similar to applying a map and filter operator to the returned
 * rows. The accessor function is used by parse to convert each row to an object
 * with named attributes.
 * 
 * @author <a href="mailto:eric.citaire@gmail.com">Eric Citaire</a>
 * 
 * @param <T>
 *            the type of the parsed DSV line
 * 
 * @see Dsv#parseRows(String)
 */
public interface DsvArrayAccessor<T> {
	/**
	 * An accessor function may invoked for each row in a DSV file, being passed
	 * the current row and index as two arguments. The return value of the
	 * function replaces the element in the returned array of rows; if the
	 * function returns null, the row is stripped from the returned array of
	 * rows. In effect, the accessor is similar to applying a map and filter
	 * operator to the returned rows. The accessor function is used by parse to
	 * convert each row to an object with named attributes.
	 * 
	 * @param row the current row as an array
	 * @param index the index of the current row
	 * @return
	 */
	T parse(JsArrayString row, int index);
}
