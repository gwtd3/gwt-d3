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
