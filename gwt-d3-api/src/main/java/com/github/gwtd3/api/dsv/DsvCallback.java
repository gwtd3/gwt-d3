package com.github.gwtd3.api.dsv;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * When the CSV data is available, the specified callback will be invoked with
 * the parsed rows as the argument. If an error occurs, the callback function
 * will instead be invoked with null.
 * 
 * @author <a href="mailto:eric.citaire@gmail.com">Eric Citaire</a>
 * 
 * @param <T>
 *            the type of the parsed DSV line (if no accessor is provided, the
 *            type will be {@link DsvRow})
 */
public interface DsvCallback<T> {
	/**
	 * When the CSV data is available, the specified callback will be invoked
	 * with the parsed rows as the argument. If an error occurs, the callback
	 * function will instead be invoked with null.
	 * 
	 * @param error the error, if any occurs.
	 * @param rows the rows values, or null if an error occurs.
	 */
	void get(JavaScriptObject error, DsvRows<T> data);
}
