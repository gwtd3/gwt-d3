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
