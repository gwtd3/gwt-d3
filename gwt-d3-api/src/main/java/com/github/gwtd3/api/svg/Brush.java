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
package com.github.gwtd3.api.svg;

import com.github.gwtd3.api.IsFunction;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.scales.Scale;
import com.google.gwt.core.client.JavaScriptObject;

public class Brush extends JavaScriptObject implements IsFunction {
	public static enum BrushEvent {
		/**
		 * on mousedown.
		 */
		BRUSH_START("brushstart"),
		/**
		 * on mousemove, if the brush extent has changed.
		 */
		BRUSH("brush"),
		/**
		 * on mouseup.
		 */
		BRUSH_END("brushend");

		private final String value;

		private BrushEvent(final String value) {
			this.value = value;
		}

		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}
	};

	protected Brush() {
		super();
	}

	/**
	 * Set the brush’s x-scale.
	 * 
	 * @param scale
	 *            the x-scale.
	 * @return the current brush.
	 */
	public final native Brush x(Scale<?> scale) /*-{
		return this.x(scale);
	}-*/;

	/**
	 * Respond to events when the brush is moved.
	 * <p>
	 * Brushes support three types of events:
	 * <ul>
	 * <li>brushstart - on mousedown
	 * <li>brush - on mousemove, if the brush extent has changed
	 * <li>brushend - on mouseup
	 * </ul>
	 * 
	 * @param event
	 *            the event.
	 * @param listener
	 *            the event listener.
	 * @return the current brush.
	 */
	public final native Brush on(BrushEvent event, DatumFunction<Void> listener) /*-{
		return this
				.on(
						event.@com.github.gwtd3.api.svg.Brush.BrushEvent::getValue()(),
						function(d, i) {
							listener.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
						});
	}-*/;

	/**
	 * Returns true if and only if the brush extent is empty.
	 * <p>
	 * When a brush is created, it is initially empty; the brush may also become
	 * empty with a single click on the background without moving, or if the
	 * extent is cleared.
	 * <p>
	 * A brush is considered empty if it has zero-width or zero-height. When the
	 * brush is empty, its extent is not strictly defined.
	 * 
	 * @return true if the brush extent is empty.
	 */
	public final native boolean empty() /*-{
		return !!this.empty();
	}-*/;

	/**
	 * @return the brush’s extent.
	 */
	public final native <T> Array<T> extent() /*-{
		return this.extent();
	}-*/;
}
