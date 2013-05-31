package com.github.gwtd3.api.dsv;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;

public class Dsv<T> extends JavaScriptObject {
    protected Dsv() {
        super();
    }

    /**
     * Defines a callback to invoke on request response.
     * <p>
     * When the CSV data is available, the specified callback will be invoked with the parsed rows as the argument. If
     * an error occurs, the callback function will instead be invoked with null.
     * 
     * @param callback
     *            the callback.
     * @return the CSV module.
     */
    public native final Dsv<T> get(DsvCallback<T> callback) /*-{
		return this
				.get(function(error, rows) {
					callback.@com.github.gwtd3.api.dsv.DsvCallback::get(Lcom/google/gwt/core/client/JavaScriptObject;Lcom/github/gwtd3/api/dsv/DsvRows;)(error, rows);
				});
    }-*/;

    /**
     * Defines an accessor to invoke on each rows.
     * <p>
     * An optional accessor function may be specified, which is then passed to d3.csv.parse; the accessor may also be
     * specified by using the return request object’s row function.
     * 
     * @param accessor
     *            the accessor.
     * @return the CSV module.
     */
    public native final Dsv<T> row(DsvObjectAccessor<T> accessor) /*-{
		return this
				.row(function(row, index) {
					return accessor.@com.github.gwtd3.api.core.ObjectAccessor::apply(Ljava/lang/Object;I)(row, index);
				});
    }-*/;

    /**
     * Parse a CSV string into objects using the header row.
     * <p>
     * Parses the specified string, which is the contents of a CSV file, returning an array of objects representing the
     * parsed rows. The string is assumed to be RFC4180-compliant. Unlike the parseRows method, this method requires
     * that the first line of the CSV file contain a comma-separated list of column names; these column names become the
     * attributes on the returned objects.
     * 
     * @param csvContent
     *            the content of a CSV file.
     * @return the rows.
     */
    public native final DsvRows<DsvRow> parse(String csvContent) /*-{
		return this.parse(csvContent);
    }-*/;

    /**
     * Parse a CSV string into objects using the header row.
     * <p>
     * Parses the specified string, which is the contents of a CSV file, returning an array of objects representing the
     * parsed rows. The string is assumed to be RFC4180-compliant. Unlike the parseRows method, this method requires
     * that the first line of the CSV file contain a comma-separated list of column names; these column names become the
     * attributes on the returned objects.
     * 
     * @param csvContent
     *            the content of a CSV file.
     * @param accessor
     *            the accessor.
     * @return the rows.
     */
    public native final DsvRows<T> parse(String csvContent,
            DsvObjectAccessor<T> accessor) /*-{
		return this
				.parse(
						csvContent,
						function(row, index) {
							return accessor.@com.github.gwtd3.api.core.ObjectAccessor::apply(Ljava/lang/Object;I)(row, index);
						});
    }-*/;

    /**
     * Parses the specified string, which is the contents of a CSV file,
     * returning an array of arrays representing the parsed rows.
     * <p>
     * The string is assumed to be RFC4180-compliant. Unlike the parse method, this method treats the header line as a
     * standard row, and should be used whenever the CSV file does not contain a header. Each row is represented as an
     * array rather than an object. Rows may have variable length.
     * 
     * @param csvContent
     *            the contents of a CSV file
     * @return CSV rows
     */
    public native final DsvRows<JsArrayString> parseRows(String csvContent) /*-{
		return this.parseRows(csvContent);
    }-*/;

    /**
     * Parses the specified string, which is the contents of a CSV file,
     * returning an array of arrays representing the parsed rows.
     * <p>
     * The string is assumed to be RFC4180-compliant. Unlike the parse method, this method treats the header line as a
     * standard row, and should be used whenever the CSV file does not contain a header. Each row is represented as an
     * array rather than an object. Rows may have variable length.
     * 
     * @param csvContent
     *            the contents of a CSV file
     * @param accessor
     *            the accessor
     * @return CSV rows
     */
    public native final DsvRows<T> parseRows(String csvContent,
            DsvArrayAccessor<T> accessor) /*-{
		return this
				.parseRows(
						csvContent,
						function(row, i) {
							return accessor.@com.github.gwtd3.api.dsv.DsvArrayAccessor::parse(Lcom/google/gwt/core/client/JsArrayString;I)(row, i);
						});
    }-*/;
}
