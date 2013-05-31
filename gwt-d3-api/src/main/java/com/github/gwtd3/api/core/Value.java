package com.github.gwtd3.api.core;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsDate;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Value extends JavaScriptObject {

	protected Value() {
		super();
	}

	/**
	 * Cast and return the datum.
	 * <p>
	 * 
	 * @return the datum
	 */
	public final native boolean asBoolean()/*-{
		return this.datum instanceof Boolean ? this.datum.valueOf()
				: !!this.datum;
	}-*/;

	/**
	 * Cast and return the datum.
	 * <p>
	 * 
	 * @return the datum
	 */
	public final native byte asByte()/*-{
		return ~~this.datum;
	}-*/;

	/**
	 * Cast and return the datum.
	 * <p>
	 * 
	 * @return the datum
	 */
	public final native char asChar()/*-{
		return ~~this.datum;
	}-*/;

	public final native JsDate asJsDate()/*-{
		return this.datum instanceof Date ? this.datum : new Date(this.datum);
	}-*/;

	/**
	 * Cast and return the datum.
	 * <p>
	 * 
	 * @return the datum
	 */
	public final native double asDouble()/*-{
		return this.datum - 0;
	}-*/;

	/**
	 * Cast and return the datum.
	 * <p>
	 * 
	 * @return the datum
	 */
	public final native float asFloat()/*-{
		return this.datum - 0;
	}-*/;

	/**
	 * Cast and return the datum.
	 * <p>
	 * 
	 * @return the datum
	 */
	public final native int asInt()/*-{
		return ~~this.datum;
	}-*/;

	/**
	 * Cast and return the datum.
	 * <p>
	 * 
	 * @return the datum
	 */
	public final long asLong() {
		return (long) asDouble();
	}

	/**
	 * Cast and return the datum.
	 * <p>
	 * 
	 * @return the datum
	 */
	public final native short asShort()/*-{
		return ~~this.datum;
	}-*/;

    /**
     * Return the value casted to a String.
     * 
     * @return the datum
     */
    public final native String asString()/*-{
		return this.datum == null ? null : new String(this.datum);
	}-*/;

	/**
	 * Cast and return the datum, if possible.
	 * <p>
	 * 
	 * @throws ClassCastException
	 *             if the datum cannot be converted in T
	 * 
	 * @return the datum
	 */
	public final native <T> T as()/*-{
		return this.datum;
	}-*/;


    /**
     * Cast and return the value.
     * 
     * @param clazz
     *            the clazz to cast to
     * @return the casted instance
     */
    @SuppressWarnings("unchecked")
    public final <T> T as(final Class<T> clazz) {
        return (T) as();
    }

    /**
     * 
     * @return true if the value is not undefined in the Javascript sense
     * 
     */
    public final native boolean isDefined()/*-{
		return typeof (this.datum) != "undefined";
	}-*/;

	/**
	 * @return true if the value is undefined in the Javascript sense
	 */
	public final native boolean isUndefined()/*-{
		return typeof (this.datum) == "undefined";
	}-*/;

	public final native boolean isNull()/*-{
		return this.datum === null;
	}-*/;

	public final native boolean isString()/*-{
		return this.datum === string;
	}-*/;

	// public final native boolean isNumber()/*-{
	// //return this.datum === string;
	// var o = this.datum;
	// return !isNaN(o - 0) && o !== null && o !== "" && o !== false && o !==
	// true;
	// }-*/;

	public final native boolean isFunction()/*-{
		return typeof (this.datum) == 'function';
	}-*/;

	public final native boolean isBoolean()/*-{
		return this.datum == true || this.datum == false;
	}-*/;

}