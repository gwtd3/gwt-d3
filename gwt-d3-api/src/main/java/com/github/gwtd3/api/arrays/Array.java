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
package com.github.gwtd3.api.arrays;

import java.util.Iterator;
import java.util.List;

import com.github.gwtd3.api.core.Value;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayInteger;
import com.google.gwt.core.client.JsArrayMixed;
import com.google.gwt.core.client.JsArrayNumber;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.core.client.JsArrayUtils;

/**
 * An Javascript object wrapping an array-like structure.
 * <p>
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 * @param <T>
 */
public class Array<T> extends JavaScriptObject {
	protected Array() {
		super();
	}

	// ============== factories methods ==============

	/**
	 * Create a new Array<T> from the given iterable.
	 * 
	 * @param iterable
	 *            the iterable
	 * @return the array
	 */
	public static <T> Array<T> create(final Iterable<T> iterable) {
		Array<T> dest = JavaScriptObject.createArray().cast();
		for (T t : iterable) {
			dest.push(t);
		}
		return dest;
	}

	/**
	 * Create a new empty array.
	 * 
	 * @return the array
	 */
	public static native final <R> Array<R> create()/*-{
		return [];
	}-*/;

	public static final Array<Integer> fromInts(final int... args) {
		return JsArrayUtils.readOnlyJsArray(args).cast();
	}

	public static final Array<Byte> fromBytes(final byte... args) {
		return JsArrayUtils.readOnlyJsArray(args).cast();
	}

	public static final Array<Double> fromDoubles(final double... args) {
		return JsArrayUtils.readOnlyJsArray(args).cast();
	}

	public static final Array<Float> fromFloats(final float... args) {
		return JsArrayUtils.readOnlyJsArray(args).cast();
	}

	public static final Array<Long> fromLongs(final long... args) {
		return JsArrayUtils.readOnlyJsArray(args).cast();
	}

	public static final Array<Short> fromShorts(final short... args) {
		return JsArrayUtils.readOnlyJsArray(args).cast();
	}

	public static final <R> Array<R> fromObjects(final R... args) {
		if (GWT.isScript()) {
			return arrayAsJsArrayForProdMode(args).cast();
		}
		Array<R> dest = JavaScriptObject.createArray().cast();
		for (int i = 0; i < args.length; ++i) {
			dest.push(args[i]);
		}
		return dest;

	}

	/**
	 * In production mode, Java arrays really are JS arrays, so just return it.
	 * 
	 * @param array
	 *            must be a Java array of some type
	 * @return a JavaScriptObject, which should be used as the appropriate type of JS array depending on the input array type
	 */
	private static native JavaScriptObject arrayAsJsArrayForProdMode(Object array) /*-{
		return array;
	}-*/;

	// ============== typecasting methods ==============

	public final native JsArrayNumber asJsArrayNumber()/*-{
		return this;
	}-*/;

	public final native JsArrayMixed asJsArrayMixed()/*-{
		return this;
	}-*/;

	public final native <R extends JavaScriptObject> JsArray<R> asJsArray()/*-{
		return this;
	}-*/;

	public final native JsArrayInteger asJsArrayInteger()/*-{
		return this;
	}-*/;

	public final native JsArrayString asJsArrayString()/*-{
		return this;
	}-*/;

	// ============== get methods ==============

	/**
	 * Return the element at the specified index as the parameterized type.
	 * 
	 * @param index
	 *            the index of the object
	 * @return the
	 */
	public final native T get(int index)/*-{
		return this[index];
	}-*/;

	/**
	 * Return the element at the given index wrapped by a {@link Value} object.
	 * 
	 * @param i
	 *            the index
	 * @return the element as a Value
	 */
	public native final Value getValue(int i) /*-{
		return {
			datum : this[i]
		};
	}-*/;

	/**
	 * Return the item at the index i.
	 * 
	 * @param i
	 *            the index of the item
	 * @return the item
	 */
	public native final T getObject(int i) /*-{
		return this[i];
	}-*/;

	/**
	 * Return the item at the index i.
	 * 
	 * @param i
	 *            the index of the item
	 * @return the item
	 */
	public native final boolean getBoolean(int i) /*-{
		return this[i] instanceof Boolean ? this[i].valueOf() : !!this[i];
	}-*/;

	/**
	 * Return the item at the index i.
	 * 
	 * @param i
	 *            the index of the item
	 * @return the item
	 */
	public native final String getString(int i) /*-{
		return this[i];
	}-*/;

	/**
	 * Return the item at the index i.
	 * 
	 * @param i
	 *            the index of the item
	 * @return the item
	 */
	public final native double getNumber(int index) /*-{
		return Number(this[index]);
	}-*/;

	// ============== pop methods ==============

	/**
	 * Removes the last element of an array, and returns that element.
	 * 
	 * Note: This method changes the length of an array.
	 * 
	 * @return the item
	 */
	public native final T pop() /*-{
		return this.pop();
	}-*/;

	/**
	 * Removes the last element of an array, and returns that element.
	 * 
	 * Note: This method changes the length of an array.
	 * 
	 * @return the item
	 */
	public native final boolean popBoolean() /*-{
		return this.pop();
	}-*/;

	/**
	 * Removes the last element of an array, and returns that element.
	 * 
	 * Note: This method changes the length of an array.
	 * 
	 * @return the item
	 */
	public native final String popString() /*-{
		return this.pop();
	}-*/;

	/**
	 * Removes the last element of an array, and returns that element.
	 * 
	 * Note: This method changes the length of an array.
	 * 
	 * @return the item
	 */
	public final native double popNumber() /*-{
		return Number(this.pop());
	}-*/;

	/**
	 * Removes the last element of an array, and returns that element.
	 * 
	 * Note: This method changes the length of an array.
	 * 
	 * @return the item
	 */
	public final native <R extends JavaScriptObject> R popObject() /*-{
		var r = this.pop();
		return r != null ? Object(r) : null;
	}-*/;

	// ============== indexOF methods ==============

	/**
	 * Search the array for the specified item, and returns its position, or -1 of the item is not found.
	 * <p>
	 * The search will start at the index 0, and end the search at the end of the array. <br>
	 * 
	 * @param item
	 *            the item to search for.
	 * @return the index of the item or -1.
	 */
	public final native <R> int indexOf(R item) /*-{
		return this.indexOf(item);
	}-*/;

	/**
	 * Search the array for the specified item, and returns its position, or -1 of the item is not found.
	 * <p>
	 * The search will start at the specified position, and end the search at the end of the array. <br>
	 * Negative values will start at the given position counting from the end, and search to the end.
	 * 
	 * @param item
	 *            the item to search for.
	 * @param startIndex
	 *            the start of the search
	 * @return the index of the item or -1.
	 */
	public final native <R> int indexOf(R item, int startIndex) /*-{
		return this.indexOf(item, startIndex);
	}-*/;

	/**
	 * Search the array for the specified item, and returns its position, or -1 of the item is not found.
	 * <p>
	 * The search will start at the end of the array, and end the search at the beginning of the array. <br>
	 * 
	 * @param item
	 *            the item to search for.
	 * @return the index of the item or -1.
	 */
	public final native <R> int lastIndexOf(R item) /*-{
		return this.lastIndexOf(item);
	}-*/;

	/**
	 * Search the array for the specified item, and returns its position, or -1 of the item is not found.
	 * <p>
	 * The search will start at the specified position, and end the search at the beginning of the array. <br>
	 * Negative values will start at the given position counting from the end, and search to the beginning.
	 * 
	 * @param item
	 *            the item to search for.
	 * @param startIndex
	 *            the start of the search
	 * @return the index of the item or -1.
	 */
	public final native <R> int lastIndexOf(R item, int startIndex) /*-{
		return this.lastIndexOf(item, startIndex);
	}-*/;

	// ============== concat methods ==============

	/**
	 * Return a new array containing the values of this array and the given array.
	 * 
	 * @param array1
	 * @return the new array
	 */
	public native final Array<?> concat(Array<?> array1)/*-{
		return this.concat(array1);
	}-*/;

	/**
	 * Return a new array containing the values of this array and the given array.
	 * 
	 * @param array1
	 * @param array2
	 * @return
	 */
	public native final Array<?> concat(Array<?> array1, Array<?> array2)/*-{
		return this.concat(array1, array2);
	}-*/;

	/**
	 * Return a new array containing the values of this array and the given array.
	 * 
	 * @param array1
	 * @param array2
	 * @param array3
	 * @return
	 */
	public native final Array<?> concat(Array<?> array1, Array<?> array2, Array<?> array3)/*-{
		return this.concat(array1, array2, array3);
	}-*/;

	/**
	 * Return a new array containing the values of this array and the given array.
	 * 
	 * @param array1
	 * @param array2
	 * @param array3
	 * @param array4
	 * @return
	 */
	public native final Array<?> concat(Array<?> array1, Array<?> array2, Array<?> array3, Array<?> array4)/*-{
		return this.concat(array1, array2, array3, array4);
	}-*/;

	/**
	 * Return a new array containing the values of this array and the given array.
	 * 
	 * @param array1
	 * @param array2
	 * @param array3
	 * @param array4
	 * @param array5
	 * @return
	 */
	public native final Array<?> concat(Array<?> array1, Array<?> array2, Array<?> array3, Array<?> array4, Array<?> array5)/*-{
		return this.concat(array1, array2, array3, array4, array5);
	}-*/;

	// ============== iteration methods ==============

	/**
	 * Executes the provided callback once for each element of the array with an assigned value. It is not invoked for indexes which have
	 * been deleted or which have been initialized to undefined.
	 * 
	 * @param callback
	 * @return this instance
	 */
	public native final void forEach(ForEachCallback<Void> callback) /*-{
		this
				.forEach(function(element, index, array) {
					callback.@com.github.gwtd3.api.arrays.ForEachCallback::forEach(Ljava/lang/Object;Lcom/github/gwtd3/api/core/Value;ILcom/github/gwtd3/api/arrays/Array;)(this,{datum:element}, index, array);
				});
	}-*/;

	/**
	 * Executes the provided callback once for each element of the array with an assigned value. It is not invoked for indexes which have
	 * been deleted or which have been initialized to undefined.
	 * 
	 * @param callback
	 * @return this instance
	 */
	public native final void forEach(ForEachCallback<Void> callback, Object thisArg) /*-{
		this
				.forEach(
						function(element, index, array) {
							callback.@com.github.gwtd3.api.arrays.ForEachCallback::forEach(Ljava/lang/Object;Lcom/github/gwtd3/api/core/Value;ILcom/github/gwtd3/api/arrays/Array;)(this,{datum:element}, index, array);
						}, thisArg);
	}-*/;

	/**
	 * 
	 * Executes the provided callback function once for each element present in the array until it finds one where callback returns a falsy
	 * value. If such an element is found, the every method immediately returns false. Otherwise, if callback returned a true value for all
	 * elements, every will return true.
	 * <p>
	 * callback is invoked only for indexes of the array which have assigned values; it is not invoked for indexes which have been deleted
	 * or which have never been assigned values.
	 * <p>
	 * 
	 * @param callback
	 *            the callback to be called for each element
	 * @return true if the callback returned true for all elements, false otherwise.
	 */
	public native final boolean every(ForEachCallback<Boolean> callback) /*-{
		return this
				.every(function(element, index, array) {
					var b = callback.@com.github.gwtd3.api.arrays.ForEachCallback::forEach(Ljava/lang/Object;Lcom/github/gwtd3/api/core/Value;ILcom/github/gwtd3/api/arrays/Array;)(this,{datum:element}, index, array);
					b = (b == null) ? false : b;
					return b.@java.lang.Boolean::booleanValue()();
				});
	}-*/;

	/**
	 * 
	 * Executes the provided callback function once for each element present in the array until it finds one where callback returns a falsy
	 * value. If such an element is found, the every method immediately returns false. Otherwise, if callback returned a true value for all
	 * elements, every will return true.
	 * <p>
	 * callback is invoked only for indexes of the array which have assigned values; it is not invoked for indexes which have been deleted
	 * or which have never been assigned values.
	 * <p>
	 * 
	 * @param callback
	 *            the callback to be called for each element
	 * @param thisArg
	 *            an argument to be passed to each callback invocations.
	 * @return true if the callback returned true for all elements, false otherwise.
	 */
	public native final boolean every(ForEachCallback<Boolean> callback, Object thisArg) /*-{
		return this
				.every(
						function(element, index, array) {
							var b = callback.@com.github.gwtd3.api.arrays.ForEachCallback::forEach(Ljava/lang/Object;Lcom/github/gwtd3/api/core/Value;ILcom/github/gwtd3/api/arrays/Array;)(this,{datum:element}, index, array);
							b = (b == null) ? false : b;
							return b.@java.lang.Boolean::booleanValue()();
						}, thisArg);
	}-*/;

	/**
	 * 
	 ** Executes the provided callback function once for each element present in the array until it finds one where callback returns a true
	 * value. If such an element is found, the method immediately returns true. Otherwise, some will return false.
	 * <p>
	 * callback is invoked only for indexes of the array which have assigned values; it is not invoked for indexes which have been deleted
	 * or which have never been assigned values.
	 * <p>
	 * 
	 * @param callback
	 *            the callback to be called for each element
	 * @return true if the callback returned true for all elements, false otherwise.
	 */
	public native final boolean some(ForEachCallback<Boolean> callback) /*-{
		return this
				.some(function(element, index, array) {
					var b = callback.@com.github.gwtd3.api.arrays.ForEachCallback::forEach(Ljava/lang/Object;Lcom/github/gwtd3/api/core/Value;ILcom/github/gwtd3/api/arrays/Array;)(this,{datum:element}, index, array);
					b = (b == null) ? false : b;
					return b.@java.lang.Boolean::booleanValue()();
				});
	}-*/;

	/**
	 * Executes the provided callback function once for each element present in the array until it finds one where callback returns a true
	 * value. If such an element is found, the method immediately returns true. Otherwise, some will return false.
	 * <p>
	 * callback is invoked only for indexes of the array which have assigned values;<br>
	 * it is not invoked for indexes which have been deleted or which have never been assigned values.
	 * <p>
	 * 
	 * @param callback
	 *            the callback to be called for each element
	 * @param thisArg
	 *            an argument to be passed to each callback invocations.
	 * @return true if the callback returned true for one element, false otherwise.
	 */
	public native final boolean some(ForEachCallback<Boolean> callback, Object thisArg) /*-{
		return this
				.some(
						function(element, index, array) {
							var b = callback.@com.github.gwtd3.api.arrays.ForEachCallback::forEach(Ljava/lang/Object;Lcom/github/gwtd3/api/core/Value;ILcom/github/gwtd3/api/arrays/Array;)(this,{datum:element}, index, array);
							b = (b == null) ? false : b;
							return b.@java.lang.Boolean::booleanValue()();
						}, thisArg);
	}-*/;

	/**
	 * Calls the provided callback function once for each element in an array, and constructs a new array of all the values for which
	 * callback returns a true value. Array elements which do not pass the callback test are simply skipped, and are not included in the new
	 * array.
	 * <p>
	 * callback is invoked only for indexes of the array which have assigned values; it is not invoked for indexes which have been deleted
	 * or which have never been assigned values.
	 * <p>
	 * 
	 * @param callback
	 *            the callback to be called for each element
	 * @return a new array containing only the element for which the callback returned true.
	 */
	public native final Array<T> filter(ForEachCallback<Boolean> callback) /*-{
		return this
				.filter(function(element, index, array) {
					var b = callback.@com.github.gwtd3.api.arrays.ForEachCallback::forEach(Ljava/lang/Object;Lcom/github/gwtd3/api/core/Value;ILcom/github/gwtd3/api/arrays/Array;)(this,{datum:element}, index, array);
					b = (b == null) ? false : b;
					return b.@java.lang.Boolean::booleanValue()();
				});
	}-*/;

	/**
	 * Calls the provided callback function once for each element in an array, and constructs a new array of all the values for which
	 * callback returns a true value. Array elements which do not pass the callback test are simply skipped, and are not included in the new
	 * array.
	 * <p>
	 * callback is invoked only for indexes of the array which have assigned values; it is not invoked for indexes which have been deleted
	 * or which have never been assigned values.
	 * <p>
	 * 
	 * @param callback
	 *            the callback to be called for each element
	 * @param thisArg
	 *            an argument to be passed to each callback invocations.
	 * @return a new array containing only the element for which the callback returned true.
	 */
	public native final Array<T> filter(ForEachCallback<Boolean> callback, Object thisArg) /*-{
		return this
				.filter(
						function(element, index, array) {
							var b = callback.@com.github.gwtd3.api.arrays.ForEachCallback::forEach(Ljava/lang/Object;Lcom/github/gwtd3/api/core/Value;ILcom/github/gwtd3/api/arrays/Array;)(this,{datum:element}, index, array);
							b = (b == null) ? false : b;
							return b.@java.lang.Boolean::booleanValue()();
						}, thisArg);
	}-*/;

	/**
	 * Calls the provided callback function once for each element in the array, and constructs a new array of all the values returned by the
	 * callback.
	 * <p>
	 * callback is invoked only for indexes of the array which have assigned values; it is not invoked for indexes which have been deleted
	 * or which have never been assigned values.
	 * <p>
	 * 
	 * @param callback
	 *            the callback to be called for each element
	 * @return a new array containing the elements returned by the callback
	 */
	public native final <R> Array<R> map(ForEachCallback<R> callback) /*-{
		return this
				.map(function(element, index, array) {
					return callback.@com.github.gwtd3.api.arrays.ForEachCallback::forEach(Ljava/lang/Object;Lcom/github/gwtd3/api/core/Value;ILcom/github/gwtd3/api/arrays/Array;)(this,{datum:element}, index, array);
				});
	}-*/;

	/**
	 * Calls the provided callback function once for each element in the array, and constructs a new array of all the values returned by the
	 * callback.
	 * <p>
	 * callback is invoked only for indexes of the array which have assigned values; it is not invoked for indexes which have been deleted
	 * or which have never been assigned values.
	 * <p>
	 * 
	 * @param callback
	 *            the callback to be called for each element
	 * @param thisArg
	 *            an argument to be passed to each callback invocations.
	 * @return a new array containing the elements returned by the callback
	 */
	public native final <R> Array<R> map(ForEachCallback<R> callback, Object thisArg) /*-{
		return this
				.map(
						function(element, index, array) {
							return callback.@com.github.gwtd3.api.arrays.ForEachCallback::forEach(Ljava/lang/Object;Lcom/github/gwtd3/api/core/Value;ILcom/github/gwtd3/api/arrays/Array;)(this,{datum:element}, index, array);
						}, thisArg);
	}-*/;

	// ============== join methods ==============

	/**
	 * Convert each element of the array to a String and join them with a comma separator. The value returned from this method may vary
	 * between browsers based on how JavaScript values are converted into strings.
	 */
	public final String join() {
		// As per JS spec
		return join(",");
	}

	/**
	 * Convert each element of the array to a String and join them with a comma separator. The value returned from this method may vary
	 * between browsers based on how JavaScript values are converted into strings.
	 */
	public final native String join(String separator) /*-{
		return this.join(separator);
	}-*/;

	// ============== length methods ==============

	/**
	 * Gets the length of the array.
	 * 
	 * @return the array length
	 */
	public final native int length() /*-{
		return this.length;
	}-*/;

	/**
	 * Reset the length of the array.
	 * 
	 * @param newLength
	 *            the new length of the array
	 */
	public final native void setLength(int newLength) /*-{
		this.length = newLength;
	}-*/;

	// ============== push methods ==============

	/**
	 * Pushes the given boolean onto the end of the array.
	 * 
	 * @param value
	 *            the item to push
	 * @return the new length of the array
	 */
	public final native int push(boolean value) /*-{
		return this.push(value);
	}-*/;

	/**
	 * Pushes the given double onto the end of the array.
	 * 
	 * @param value
	 *            the item to push
	 * @return the new length of the array
	 */
	public final native int push(double value) /*-{
		return this.push(value);
	}-*/;

	/**
	 * Pushes the given {@link JavaScriptObject} onto the end of the array.
	 * 
	 * @param value
	 *            the item to push
	 * @return the new length of the array
	 */
	public final native int push(JavaScriptObject value) /*-{
		return this.push(value);
	}-*/;

	/**
	 * Pushes the given item onto the end of the array.
	 * 
	 * @param value
	 *            the item to push
	 * @return the new length of the array
	 */
	public native final int push(T value) /*-{
		return this.push(value);
	}-*/;

	// ============== set methods ==============

	/**
	 * Sets the boolean value at a given index.
	 * 
	 * If the index is out of bounds, the value will still be set. The array's length will be updated to encompass the bounds implied by the
	 * added value.
	 * 
	 * @param index
	 *            the index to be set
	 * @param value
	 *            the boolean to be stored
	 */
	public final native void set(int index, boolean value) /*-{
		this[index] = value;
	}-*/;

	/**
	 * Sets the value at a given index.
	 * 
	 * If the index is out of bounds, the value will still be set. The array's length will be updated to encompass the bounds implied by the
	 * added value.
	 * 
	 * @param index
	 *            the index to be set
	 * @param value
	 *            the boolean to be stored
	 */
	public final native void set(int index, T value) /*-{
		this[index] = value;
	}-*/;

	/**
	 * Sets the double value at a given index.
	 * 
	 * If the index is out of bounds, the value will still be set. The array's length will be updated to encompass the bounds implied by the
	 * added value.
	 * 
	 * @param index
	 *            the index to be set
	 * @param value
	 *            the double to be stored
	 */
	public final native void set(int index, double value) /*-{
		this[index] = value;
	}-*/;

	/**
	 * Sets the object value at a given index.
	 * 
	 * If the index is out of bounds, the value will still be set. The array's length will be updated to encompass the bounds implied by the
	 * added object.
	 * 
	 * @param index
	 *            the index to be set
	 * @param value
	 *            the {@link JavaScriptObject} to be stored
	 */
	public final native void set(int index, JavaScriptObject value) /*-{
		this[index] = value;
	}-*/;

	/**
	 * Sets the String value at a given index.
	 * 
	 * If the index is out of bounds, the value will still be set. The array's length will be updated to encompass the bounds implied by the
	 * added String.
	 * 
	 * @param index
	 *            the index to be set
	 * @param value
	 *            the String to be stored
	 */
	public final native void set(int index, String value) /*-{
		this[index] = value;
	}-*/;

	// ============== shift methods ==============

	/**
	 * Shifts the first value off the array.
	 * 
	 * @return the shifted boolean
	 */
	public final native boolean shiftBoolean() /*-{
		return Boolean(this.shift());
	}-*/;

	/**
	 * Shifts the first value off the array.
	 * 
	 * @return the shifted double
	 */
	public final native double shiftNumber() /*-{
		return Number(this.shift());
	}-*/;

	/**
	 * Shifts the first value off the array.
	 * 
	 * @return the shifted {@link JavaScriptObject}
	 */
	public final native <R extends JavaScriptObject> R shiftObject() /*-{
		return Object(this.shift());
	}-*/;

	/**
	 * Shifts the first value off the array.
	 * 
	 * @return the shifted String
	 */
	public final native String shiftString() /*-{
		return String(this.shift());
	}-*/;

	// ============== unshift methods ==============

	/**
	 * Shifts a boolean onto the beginning of the array.
	 * 
	 * @param value
	 *            the value to the stored
	 */
	public final native int unshift(boolean value) /*-{
		return this.unshift(value);
	}-*/;

	/**
	 * Shifts a double onto the beginning of the array.
	 * 
	 * @param value
	 *            the value to store
	 */
	public final native int unshift(double value) /*-{
		return this.unshift(value);
	}-*/;

	/**
	 * Shifts a {@link JavaScriptObject} onto the beginning of the array.
	 * 
	 * @param value
	 *            the value to store
	 */
	public final native int unshift(JavaScriptObject value) /*-{
		return this.unshift(value);
	}-*/;

	/**
	 * Shifts a String onto the beginning of the array.
	 * 
	 * @param value
	 *            the value to store
	 */
	public final native int unshift(String value) /*-{
		return this.unshift(value);
	}-*/;

	// ============== reverse methods ==============

	/**
	 * Reverse the order of the elements in the array.
	 * 
	 * @return the array after it has been reversed
	 */
	public native final Array<T> reverse()/*-{
		return this.reverse();
	}-*/;

	// ============== slice methods ==============

	/**
	 * Selects the elements starting at the given startIndex, until the last element, and return the selected elements in a new array.
	 * 
	 * @param startIndex
	 * @return the new array
	 */
	public native final Array<T> slice(final int startIndex)/*-{
		return this.slice(startIndex);
	}-*/;

	/**
	 * Selects the elements starting at the given startIndex, extending at the element just before the endIndex, and return the selected
	 * elements in a new array.
	 * 
	 * @param startIndex
	 * @param endIndex
	 * @return the new array
	 */
	public native final Array<T> slice(final int startIndex, final int endIndex)/*-{
		return this.slice(startIndex, endIndex);
	}-*/;

	// ============== sort methods ==============

	/**
	 * Sort the elements alphabetically and ascending.
	 * 
	 * @return the array with sorted elements.
	 */
	public native final Array<?> sortAlphaAsc()/*-{
		return this.sort();
	}-*/;

	/**
	 * Sort the elements alphabetically and descending.
	 * 
	 * @return the array with sorted elements.
	 */
	public native final Array<?> sortAlphaDesc()/*-{
		return this.sort().reverse();
	}-*/;

	/**
	 * Sort the elements numerically and ascending.
	 * 
	 * @return the array with sorted elements.
	 */
	public native final Array<?> sortNumericAsc()/*-{
		return this.sort(function(a, b) {
			return a - b;
		});
	}-*/;

	/**
	 * Sort the elements numerically and ascending.
	 * 
	 * @return the array with sorted elements.
	 */
	public native final Array<?> sortNumericDesc()/*-{
		return this.sort(function(a, b) {
			return b - a;
		});
	}-*/;

	// ============== splice methods ==============

	/**
	 * Remove count elements from startIndex.
	 * 
	 * @param startIndex
	 *            the index of the first element to be removed; negative values specify position from the end of the array
	 * @param count
	 *            the number of elements to be removed
	 * @return the removed elements
	 */
	public native final Array<T> splice(int startIndex, int count)/*-{
		return this.splice(startIndex, count);
	}-*/;

	/**
	 * Remove count elements from startIndex, and insert the given elements at this position.
	 * 
	 * @param startIndex
	 *            the index of the first element to be removed; negative values specify position from the end of the array
	 * @param count
	 *            the number of elements to be removed
	 * @param insert1
	 *            element to insert
	 * @return the removed elements
	 */
	public native final Array<T> splice(int startIndex, int count, Object insert1)/*-{
		return this.splice(startIndex, count, insert1);
	}-*/;

	/**
	 * Remove count elements from startIndex, and insert the given elements at this position.
	 * 
	 * @param startIndex
	 *            the index of the first element to be removed; negative values specify position from the end of the array
	 * @param count
	 *            the number of elements to be removed
	 * @param insert1
	 *            element to insert
	 * @param insert2
	 *            element to insert
	 * 
	 * @return the removed elements
	 */
	public native final Array<T> splice(int startIndex, int count, Object insert1, Object insert2)/*-{
		return this.splice(startIndex, count, insert1, insert2);
	}-*/;

	/**
	 * Remove count elements from startIndex, and insert the given elements at this position.
	 * 
	 * @param startIndex
	 *            the index of the first element to be removed; negative values specify position from the end of the array
	 * @param count
	 *            the number of elements to be removed
	 * @param insert1
	 *            element to insert
	 * @param insert2
	 *            element to insert
	 * @param insert3
	 *            element to insert
	 * 
	 * @return the removed elements
	 */
	public native final Array<T> splice(int startIndex, int count, Object insert1, Object insert2, Object insert3)/*-{
		return this.splice(startIndex, count, insert1, insert2, insert3);
	}-*/;

	/**
	 * Remove count elements from startIndex, and insert the given elements at this position.
	 * 
	 * @param startIndex
	 *            the index of the first element to be removed; negative values specify position from the end of the array
	 * @param count
	 *            the number of elements to be removed
	 * @param insert1
	 *            element to insert
	 * @param insert2
	 *            element to insert
	 * @param insert3
	 *            element to insert
	 * @param insert4
	 *            element to insert
	 * 
	 * @return the removed elements
	 */
	public native final Array<T> splice(int startIndex, int count, Object insert1, Object insert2, Object insert3, Object insert4)/*-{
		return this.splice(startIndex, count, insert1, insert2, insert3,
				insert4);
	}-*/;

	/**
	 * Remove count elements from startIndex, and insert the given elements at this position.
	 * 
	 * @param startIndex
	 *            the index of the first element to be removed; negative values specify position from the end of the array
	 * @param count
	 *            the number of elements to be removed
	 * @param insert1
	 *            element to insert
	 * @param insert2
	 *            element to insert
	 * @param insert3
	 *            element to insert
	 * @param insert4
	 *            element to insert
	 * @param insert5
	 *            element to insert
	 * 
	 * @return the removed elements
	 */
	public native final Array<T> splice(int startIndex, int count, Object insert1, Object insert2, Object insert3, Object insert4,
			Object insert5)/*-{
		return this.splice(startIndex, count, insert1, insert2, insert3,
				insert4, insert5);
	}-*/;

	// ======== bridge to Java Collections API ==============

	/**
	 * @return a list wrapping the array to access
	 */
	public final List<T> asList() {
		return new ArrayList<T>(this);
	}

	public final Iterable<T> asIterable() {
		return new Iterable<T>() {
			@Override
			public Iterator<T> iterator() {
				return new ArrayIterator<T>(Array.this) {
					@Override
					public T accessObject(final Array<T> array, final int index) {
						return array.getObject(index);
					}
				};
			}
		};

	}

}
