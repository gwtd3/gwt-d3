package com.github.gwtd3.api.layout;

import com.github.gwtd3.api.Sort;
import com.github.gwtd3.api.core.Datum;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayNumber;

public class ChordLayout extends Layout<ChordLayout> {
	protected ChordLayout() {
		super();
	}

	public final native ChordLayout padding(double padding) /*-{
		return this.padding(padding);
	}-*/;

	public final native ChordLayout sortSubgroups(Sort sort) /*-{
		return this.sortSubgroups(sort);
	}-*/;

	public final native ChordLayout matrix(JsArray<JsArrayNumber> matrix) /*-{
		return this.matrix(matrix);
	}-*/;

	public final native JsArray<Group> groups() /*-{
		return this.groups;
	}-*/;

	public final native JavaScriptObject chords() /*-{
		return this.chords;
	}-*/;
	
	public static class Group extends JavaScriptObject {
		protected Group() {
			super();
		}
		
		public final native int index() /*-{
			return this.index;
		}-*/;
		
		public final native double startAngle() /*-{
			return this.startAngle;
		}-*/;
		
		public final native double endAngle() /*-{
			return this.endAngle;
		}-*/;
		
		public final native double value() /*-{
			return this.value;
		}-*/;
	}
    
    public static class Chord extends Datum {
    	protected Chord() {
    		super();
    	}

		public final native Group source() /*-{
			return this.source;
		}-*/;

		public final native Group target() /*-{
			return this.target;
		}-*/;
    }
}
