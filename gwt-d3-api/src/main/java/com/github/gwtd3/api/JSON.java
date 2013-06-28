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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

/**
 * A convenient builder to build {@link JSONObject}s instances. Use the
 * {@link #append} methods to appends simple property values.
 * <p>
 * Use the {@link #enter(String)} method to append a child {@link JSONObject} as
 * a property value. Use the {@link #exit()} method to go back to the parent
 * object.
 * <p>
 * For instance:
 * 
 * <pre>
 * {@code
 * JSON.create("start","value")
 * .enter("child")
 * .append("childProp","v")
 * .exit()
 * .append("end","value"); }
 * </pre>
 * <p>
 * will generate the following JSON:
 * 
 * <pre>
 * {@code
 * 	{
 * 	start:"value",
 * 	child:
 * 		{
 * 		childProp:"v"
 * 		},
 * 	end:"value"
 * 	}
 * </pre>
 * 
 * TODO: Jsonarray
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class JSON {
	private final JSON parent;

	private JSONObject object = new JSONObject();

	// private final Map<String, Object> properties = new HashMap<String,
	// Object>();

	/**
	 * Create a new child instance of the given parent
	 * 
	 * @param json
	 */
	protected JSON(final JSON json) {
		this.parent = json;
		object = new JSONObject();
	}

	/**
	 * 
	 */
	private JSON() {
		this(null);
	}

	public static final JSON create() {
		return new JSON();
	}

	public static final JSON create(final String propName, final String value) {
		return create().append(propName, value);
	}

	public static final JSON create(final String propName, final boolean value) {
		return create().append(propName, value);
	}

	public static final JSON create(final String propName, final double value) {
		return create().append(propName, value);
	}

	public JSON append(final String propName, final boolean value) {
		object.put(propName, JSONBoolean.getInstance(value));
		return this;
	}

	public JSON append(final String propName, final double value) {
		object.put(propName, new JSONNumber(value));
		return this;
	}

	public JSON append(final String propName, final String value) {
		object.put(propName, new JSONString(value));
		return this;
	}

	public JSON appendNull(final String propName) {
		object.put(propName, JSONNull.getInstance());
		return this;
	}

	/**
	 * Put a new {@link JSONObject} into the given property and return the child
	 * JSON builder.
	 * 
	 * @param propName
	 * @return
	 */
	public JSON enter(final String propName) {
		JSON child = new JSON(this);
		object.put(propName, child.object);
		return child;
	}

	/**
	 * @return the parent JSON builder
	 */
	public JSON exit() {
		if (parent == null) {
			throw new IllegalStateException("Cannot exit a top-level JSON");
		}
		return parent;
	}

	/**
	 * @return a new instance of {@link JSONObject}.
	 */
	public JSONObject build() {
		return cloneObject();
	}

	/**
	 * @return a new instance of {@link JSONObject} casted to
	 *         {@link JavaScriptObject}.
	 */
	public JavaScriptObject toJSO() {
		return build().getJavaScriptObject();
	}

	protected JSONObject cloneObject() {
		return new JSONObject(object.getJavaScriptObject());
	}

}
