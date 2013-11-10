package com.github.gwtd3.api.functions;

import com.github.gwtd3.api.core.Value;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;

/**
 * A convenient {@link DatumFunction} which returns the value of a specified property
 * for each datum.
 * <p>
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 *
 * @param <T>
 */
public class PropertyValueFunction<T> implements DatumFunction<T> {

	private final String propertyName;

	/**
	 * Create a {@link PropertyValueFunction} which returns the value of the property
	 * with the given name
	 * @param propertyName the name of the property the value should be returned of
	 * @return the new function
	 */
	public static <X> PropertyValueFunction<X> forProperty(final String propertyName){
		return new PropertyValueFunction<X>(propertyName);
	}

	public PropertyValueFunction(final String propertyName) {
		super();
		this.propertyName = propertyName;
	}

	@Override
	public T apply(final Element context, final Value d, final int index) {
		Value value = getProperty(propertyName, d);
		GWT.log("value" + value.<T>as());
		return value.as();
	}

	private static final native Value getProperty(String propName, Value v)/*-{
		return {datum:v.datum[propName]};
	}-*/;
}
