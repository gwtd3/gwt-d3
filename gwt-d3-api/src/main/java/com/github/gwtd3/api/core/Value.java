/**
 * Copyright (c) 2013, Anthony Schiochet and Eric Citaire
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * The names Anthony Schiochet and Eric Citaire may not be used to endorse or promote products
 *   derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL MICHAEL BOSTOCK BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY
 * OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
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
	 * Wraps the given object into a {@link Value}.
	 * 
	 * @param o
	 *            the object to be wrapped as a Value
	 * @return the value
	 */
	public static final native Value create(Object o)/*-{
		return {
			datum : o
		};
	}-*/;

	/**
	 * Create a {@link Value} instance from the value of the named property of the given object.
	 * 
	 * @param object
	 * @param propertyName
	 * @return
	 */
	public static final native Value create(JavaScriptObject object, String propertyName)/*-{
		return {
			datum : object[propertyName]
		};
	}-*/;

	/**
	 * Cast and return the value.
	 * <p>
	 * 
	 * @return the value
	 */
	public final native boolean asBoolean()/*-{
		return this.datum instanceof Boolean ? this.datum.valueOf()
				: !!this.datum;
	}-*/;

	/**
	 * Cast and return the value.
	 * <p>
	 * 
	 * @return the value
	 */
	public final native byte asByte()/*-{
		return ~~this.datum;
	}-*/;

	/**
	 * Cast and return the value.
	 * <p>
	 * 
	 * @return the value
	 */
	public final native char asChar()/*-{
		return ~~this.datum;
	}-*/;

	public final native JsDate asJsDate()/*-{
		return this.datum instanceof Date ? this.datum : new Date(this.datum);
	}-*/;

	/**
	 * Cast and return the value.
	 * <p>
	 * 
	 * @return the value
	 */
	public final native double asDouble()/*-{
		return this.datum - 0;
	}-*/;

	/**
	 * Cast and return the value.
	 * <p>
	 * 
	 * @return the value
	 */
	public final native float asFloat()/*-{
		return this.datum - 0;
	}-*/;

	/**
	 * Cast and return the value.
	 * <p>
	 * 
	 * @return the value
	 */
	public final native int asInt()/*-{
		return ~~this.datum;
	}-*/;

	/**
	 * Cast and return the value.
	 * <p>
	 * 
	 * @return the value
	 */
	public final long asLong() {
		return (long) asDouble();
	}

	/**
	 * Cast and return the value.
	 * <p>
	 * 
	 * @return the value
	 */
	public final native short asShort()/*-{
		return ~~this.datum;
	}-*/;

	/**
	 * Return the value casted to a String.
	 * 
	 * @return the value
	 */
	public final native String asString()/*-{
		return this.datum == null ? null : new String(this.datum);
	}-*/;

	/**
	 * Cast and return the value, if possible.
	 * <p>
	 * 
	 * @throws ClassCastException
	 *             if the value cannot be converted in T
	 * 
	 * @return the value
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
		return (typeof this.datum == 'string' || this.datum instanceof String);
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

	/**
	 * Return the property of this object as a {@link Value}.
	 * <p>
	 * This method result is never non-null. The returned value may then be tested for nullity (with {@link #isNull()}) or for undefinition
	 * (with {@link #isUndefined()}).
	 * <p>
	 * 
	 * @param propertyName
	 *            the name of the property to get
	 * @return the property value as a value.
	 */
	public final native Value getProperty(String propertyName)/*-{
		return {
			datum : this.datum[propertyName]
		};
	}-*/;

	/**
	 * The result of the typeof operator.
	 * <p>
	 * 
	 * @return the String returned by a call to typeof
	 */
	public final native String typeof()/*-{
		return typeof this.datum;
	}-*/;
}