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
package com.github.gwtd3.api.layout;

import com.github.gwtd3.api.Sort;
import com.github.gwtd3.api.core.Value;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayNumber;

public class Chord extends JavaScriptObject {
	protected Chord() {
		super();
	}

	public final native Chord padding(double padding) /*-{
		return this.padding(padding);
	}-*/;

	public final native Chord sortSubgroups(Sort sort) /*-{
		return this.sortSubgroups(sort);
	}-*/;

	public final native Chord matrix(JsArray<JsArrayNumber> matrix) /*-{
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

	public static class ChordItem extends Value {
		protected ChordItem() {
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
