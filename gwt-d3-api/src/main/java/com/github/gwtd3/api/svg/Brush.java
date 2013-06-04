package com.github.gwtd3.api.svg;

import com.github.gwtd3.api.IsFunction;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.events.EventListener;
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
     * @param scale the x-scale.
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
     * @param handler
     *            the handler.
     * @return the current brush.
     */
    public final native Brush on(BrushEvent event, EventListener listener) /*-{
		return this
				.on(
						event.@com.github.gwtd3.api.svg.Brush.BrushEvent::getValue()(),
						function() {
							listener.@com.github.gwtd3.api.events.EventListener::onEvent()();
						});
    }-*/;

    /**
     * Returns true if and only if the brush extent is empty.
     * <p>
     * When a brush is created, it is initially empty; the brush may also become empty with a single click on the
     * background without moving, or if the extent is cleared.
     * <p>
     * A brush is considered empty if it has zero-width or zero-height. When the brush is empty, its extent is not
     * strictly defined.
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
