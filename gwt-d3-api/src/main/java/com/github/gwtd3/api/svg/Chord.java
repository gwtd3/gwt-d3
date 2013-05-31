package com.github.gwtd3.api.svg;

import com.github.gwtd3.api.IsFunction;

import com.google.gwt.core.client.JavaScriptObject;

public class Chord extends PathDataGenerator {
	protected Chord() {
		super();
	}

	public final native Chord radius(double radius) /*-{
		return this.radius(radius);
	}-*/;
}
