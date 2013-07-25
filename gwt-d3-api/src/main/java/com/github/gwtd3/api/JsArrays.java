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
/**
 * 
 */
package com.github.gwtd3.api;

import java.util.Collection;

import com.github.gwtd3.api.arrays.Array;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayInteger;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.core.client.JsArrayNumber;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.core.client.JsArrayUtils;

/**
 * Convient methods to complete the methods of {@link JsArrayUtils}.
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class JsArrays {

	//=============== D3 API =======================



	//=========== creation =======================

	public static native JsArrayString asJsArray(String str1) /*-{
		return [ str1 ];
	}-*/;

	public static native JsArrayString asJsArray(String str1, String str2) /*-{
		return [ str1, str2 ];
	}-*/;

	public static native JsArrayString asJsArray(String str1, String str2, String str3) /*-{
		return [ str1, str2, str3 ];
	}-*/;

	public static native JsArrayString asJsArray(String str1, String str2, String str3, String str4) /*-{
		return [ str1, str2, str3, str4 ];
	}-*/;

	public static native <T> JsArrayMixed asJsArray(T a1) /*-{
		return [ a1 ];
	}-*/;

	public static native <T> JsArrayMixed asJsArray(T a1, T a2) /*-{
		return [ a1, a2 ];
	}-*/;

	public static native <T> JsArrayMixed asJsArray(T a1, T a2, T a3) /*-{
		return [ a1, a2, a3 ];
	}-*/;

	/**
	 * @param data
	 * @return
	 */
	public static <T> Array<T> asJsArray(final T[] array) {
		if (GWT.isScript()) {
			return arrayAsJsArrayForProdMode(array).cast();
		}
		Array<T> dest = JavaScriptObject.createArray().cast();
		for (int i = 0; i < array.length; ++i) {
			dest.push(array[i]);
		}
		return dest;
	}

	/**
	 * @param data
	 * @return
	 */
	public static <T> Array<T> asJsArray(final Collection<T> iterable) {
		if (GWT.isScript()) {
			return arrayAsJsArrayForProdMode(iterable.toArray()).cast();
		}
		Array<T> dest = JavaScriptObject.createArray().cast();
		for (T t : iterable) {
			dest.push(t);
		}
		return dest;
	}

	public static native JsArrayInteger asJsArray(int a1) /*-{
		return [ a1 ];
	}-*/;

	public static native JsArrayInteger asJsArray(int a1, int a2) /*-{
		return [ a1, a2 ];
	}-*/;

	public static native JsArrayInteger asJsArray(int a1, int a2, int a3) /*-{
		return [ a1, a2, a3 ];
	}-*/;

	public static native JsArrayInteger asJsArray(int a1, int a2, int a3, int a4) /*-{
		return [ a1, a2, a3, a4 ];
	}-*/;

	public static JsArrayInteger asJsArray(final byte[] array) {
		return JsArrayUtils.readOnlyJsArray(array);
	}

	public static JsArrayNumber asJsArray(final double[] array) {
		return JsArrayUtils.readOnlyJsArray(array);
	}

	public static JsArrayNumber asJsArray(final float[] array) {
		return JsArrayUtils.readOnlyJsArray(array);
	}

	public static JsArrayInteger asJsArray(final int[] array) {
		return JsArrayUtils.readOnlyJsArray(array);
	}

	public static JsArrayNumber asJsArray(final long[] array) {
		return JsArrayUtils.readOnlyJsArray(array);
	}

	public static JsArrayInteger asJsArray(final short[] array) {
		return JsArrayUtils.readOnlyJsArray(array);
	}

	/**
	 * @param data
	 * @return
	 */
	public static JsArrayString asJsArray(final String[] array) {
		if (GWT.isScript()) {
			return arrayAsJsArrayForProdMode(array).cast();
		}
		JsArrayString dest = JavaScriptObject.createArray().cast();
		for (int i = 0; i < array.length; ++i) {
			dest.push(array[i]);
		}
		return dest;
	}

	/**
	 * @param data
	 * @return
	 */
	public static JsArrayInteger asJsArray(final char[] array) {
		if (GWT.isScript()) {
			return arrayAsJsArrayForProdMode(array).cast();
		}
		JsArrayInteger dest = JavaScriptObject.createArray().cast();
		for (int i = 0; i < array.length; ++i) {
			dest.push(array[i]);
		}
		return dest;
	}

	/**
	 * In production mode, Java arrays really are JS arrays, so just return it.
	 * 
	 * @param array
	 *            must be a Java array of some type
	 * @return a JavaScriptObject, which should be used as the appropriate type
	 *         of JS array depending on the input array type
	 */
	private static native JavaScriptObject arrayAsJsArrayForProdMode(Object array) /*-{
		return array;
	}-*/;

}
