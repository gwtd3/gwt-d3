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
package com.github.gwtd3.api.behaviour;

import com.github.gwtd3.api.Coords;
import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.IsFunction;
import com.github.gwtd3.api.functions.DatumFunction;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * This behavior automatically creates event listeners to handle drag gestures
 * on an element. Both mouse events and touch events are supported.
 * <p>
 * Usage:
 * 
 * <pre>
 * {
 *     &#064;code
 *     Drag drag = D3.behavior.drag().on(DragEventType.drag, new MyDragListener());
 *     mySelection.call(drag);
 * }
 * </pre>
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Drag extends JavaScriptObject implements IsFunction {

    protected Drag() {

    }

    /**
     * Type of drag event to listen to.
     * 
     * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
     */
    public static enum DragEventType {
	/**
	 * Fired when a drag gesture is started
	 */
	DRAGSTART,
	/**
	 * Fired when the element is dragged. d3.event will contain "x" and "y"
	 * properties representing the current absolute drag coordinates of the
	 * element. It will also contain "dx" and "dy" properties representing
	 * the element's coordinates relative to its position at the beginning
	 * of the gesture.
	 */
	DRAG,
	/**
	 * Fired when the drag gesture has finished
	 */
	DRAGEND;
    }

    /**
     * Registers the specified listener to receive events of the specified type
     * from the drag behavior.
     * <p>
     * See {@link DragEventType} for more information.
     * 
     * @param type
     * @param listener
     * @return
     */
    public final native Drag on(DragEventType type, DatumFunction<Void> listener)/*-{
		return this
				.on(
						type.@com.github.gwtd3.api.behaviour.Drag.DragEventType::name()()
								.toLowerCase(),
						function(d, index) {
							listener.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},index);
						});
    }-*/;

    /**
     * Set the origin accessor to the specified function.
     * <p>
     * An origin accessor is used to determine the starting position (the
     * “origin?) of the element being dragged; this allows the drag behavior to
     * preserve the offset between the mouse position and the starting element
     * position during drag. If the origin accessor is null, then the element
     * position is set to the mouse position on drag; this can cause a
     * noticeable jump on large elements. If an origin accessor is specified,
     * the function is called on mousedown. The function is invoked in the same
     * manner as other operator functions, being passed the current datum d and
     * index i, with the this context as the clicked-on DOM element. To access
     * the current event, use the global {@link D3#event()}. The origin accessor
     * must return an object with x and y properties representing the starting
     * coordinates of the element being dragged.
     * <p>
     * Frequently the origin accessor is specified as Object, or
     * {@link D3#identity()}, which is equivalent to the identity function. This
     * is suitable when the datum bound to the dragged element is already an
     * object with x and y attributes representing its current position.
     * <p>
     * For example: <a
     * href="http://bl.ocks.org/1557377">http://bl.ocks.org/1557377</a>
     * 
     * @param o
     *            the origin accessor function, returning an object with x and y
     *            attributes.
     * @return the object Drag.
     */
    public final native Drag origin(JavaScriptObject o)/*-{
		return this.origin(o);
    }-*/;

    /**
     * Set the origin accessor to the specified function.
     * <p>
     * An origin accessor is used to determine the starting position (the
     * “origin?) of the element being dragged; this allows the drag behavior to
     * preserve the offset between the mouse position and the starting element
     * position during drag. If the origin accessor is null, then the element
     * position is set to the mouse position on drag; this can cause a
     * noticeable jump on large elements. If an origin accessor is specified,
     * the function is called on mousedown. The function is invoked in the same
     * manner as other operator functions, being passed the current datum d and
     * index i, with the this context as the clicked-on DOM element. To access
     * the current event, use the global {@link D3#event()}. The origin accessor
     * must return an object with x and y properties representing the starting
     * coordinates of the element being dragged.
     * <p>
     * Frequently the origin accessor is specified as Object, or
     * {@link D3#identity()}, which is equivalent to the identity function. This
     * is suitable when the datum bound to the dragged element is already an
     * object with x and y attributes representing its current position.
     * <p>
     * For example: <a
     * href="http://bl.ocks.org/1557377">http://bl.ocks.org/1557377</a>
     * 
     * @param o
     *            the origin accessor function, returning an object with x and y
     *            attributes.
     * @return the object Drag.
     */
    public final native Drag origin(DatumFunction<Coords> o)/*-{
		return this
				.origin(function(d, i) {
					return o.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
    }-*/;

}
