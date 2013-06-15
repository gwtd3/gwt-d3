package com.github.gwtd3.api.functions;

import com.github.gwtd3.api.core.Value;
import com.google.gwt.dom.client.Element;

public interface NestedDatumFunction<T> {
	public T apply(Element context, Value datum, int index, int rowIndex, int squareIndex);
}