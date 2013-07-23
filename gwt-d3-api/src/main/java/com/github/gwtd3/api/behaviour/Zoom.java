package com.github.gwtd3.api.behaviour;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.IsFunction;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.functions.DatumFunction;
import com.google.gwt.core.client.JavaScriptObject;

public class Zoom extends JavaScriptObject implements IsFunction {

	protected Zoom() {

	}

	/**
	 * Type of scroll event to listen to.
	 */
	public static enum ZoomEventType {
		/**
		 * Registers the specified listener to receive events of the specified
		 * type from the zoom behavior. Currently, only the "zoom" event is
		 * supported.
		 */
		zoom
	}

	/**
	 * Registers the specified listener to receive events of the specified type
	 * from the zoom behavior.
	 * <p>
	 * See {@link ZoomEventType} for more information.
	 * 
	 * @param type
	 * @param listener
	 * @return
	 */
	public final native Zoom on(ZoomEventType type, DatumFunction<Void> listener)/*-{
		return this
				.on(
						type.@com.github.gwtd3.api.behaviour.Zoom.ZoomEventType::name()(),
						function(d, index) {
							listener.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},index);
						});
	}-*/;

	/**
	 * Specifies an x-scale whose domain should be automatically adjusted when
	 * zooming. If not specified, returns the current x-scale, which defaults to
	 * null. If the scale's domain or range is modified programmatically, this
	 * function should be called again.
	 * 
	 * @param o
	 * @return
	 */
	public final native Zoom x(int scale)/*-{
		return this.x(scale);
	}-*/;

	/**
	 * Specifies an y-scale whose domain should be automatically adjusted when
	 * zooming. If not specified, returns the current y-scale, which defaults to
	 * null. If the scale's domain or range is modified programmatically, this
	 * function should be called again.
	 * 
	 * @param o
	 * @return Zoom object
	 */

	public final native Zoom y(int scale)/*-{
		return this.y(scale);
	}-*/;

	/**
	 * Specifies the zoom scale's allowed range as a two-element array,
	 * [*minimum*, maximum]. If not specified, returns the current scale extent,
	 * which defaults to [0, Infinity].
	 * 
	 * @param o
	 * @return Zoom object
	 */
	public final native Zoom scaleExtent(double[] scale)/*-{
		return this.scaleExtent(scale);
	}-*/;

	/**
	 * Specifies the current zoom scale. If not specified, returns the current
	 * zoom scale, which defaults to 1.
	 * 
	 * @param o
	 * @return Zoom object
	 */
	public final native Zoom scale(int scale)/*-{
		return this.scale(scale);
	}-*/;

	/**
	 * Specifies the current zoom translation vector. If not specified, returns
	 * the current translation vector, which defaults to [0, 0].
	 * 
	 * @param o
	 * @return Zoom object
	 */
	public final native Zoom translate(double[] vector)/*-{
		return this.translate(vector);
	}-*/;

	/**
	 * Provide access to the properties of a zoom event.
	 * <p>
	 * Use {@link D3#zoomEvent()} from within a
	 * {@link Zoom#on(ZoomEventType, DatumFunction)} listener.
	 * <p>
	 * 
	 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
	 * 
	 */

	public static class ZoomEvent extends JavaScriptObject {

		protected ZoomEvent() {

		}

		/**
		 * The scale of the zoom
		 * <p>
		 * 
		 * @return the scale of the zoom
		 */

		public native final double scale()/*-{
			return this.scale;
		}-*/;

		/**
		 * A two-element array representing the current translation vector.
		 * 
		 * @return the translation vector
		 */

		public native final Array<Double> translate()/*-{
			return this.translate;
		}-*/;

		/**
		 * Shortcut to translate().getNumber(0).
		 * <p>
		 * 
		 * @return the translation x coord
		 */

		public native final double translateX()/*-{

			return this.translate[0];

		}-*/;

		/**
		 * 
		 * Shortcut to translate().getNumber(1).
		 * 
		 * <p>
		 * 
		 * 
		 * 
		 * @return the translation y coord
		 */

		public native final double translateY()/*-{

			return this.translate[1];

		}-*/;

	}
}
