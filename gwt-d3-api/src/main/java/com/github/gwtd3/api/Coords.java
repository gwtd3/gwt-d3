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
package com.github.gwtd3.api;

import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;

/**
 * A Javascript object containing x and y properties.
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Coords extends JavaScriptObject {

	protected Coords() {

	}

	public static final native Coords create(double x, double y)/*-{
		return {
			x : x,
			y : y
		};
	}-*/;

	/**
	 * @return the x coordinates
	 */
	public final native double x()/*-{
		return this.x;
	}-*/;

	/**
	 * @return the y coordinates
	 */
	public final native double y()/*-{
		return this.y;
	}-*/;

	/**
	 * set the x coords
	 * 
	 * @return the x coordinates
	 */
	public final native Coords x(double x)/*-{
		this.x = x;
		return this;
	}-*/;

	/**
	 * set the y coords
	 * 
	 * @param y
	 * @return
	 */
	public final native Coords y(double y)/*-{
		this.y = y;
		return this;
	}-*/;

	/**
	 * @return x and y comma-separated string
	 */
	public final native String toCommaSeparatedString()/*-{
		return this.x + "," + this.y;
	}-*/;

	/**
	 * Convenient {@link DatumFunction} that return the x component of a
	 * {@link Coords} datum.
	 */
	public static final DatumFunction<Double> X_ACCESSOR = new DatumFunction<Double>() {
		@Override
		public Double apply(Element context, Value d, int index) {
			return d.<Coords> as().x();
		}
	};

	/**
	 * Convenient {@link DatumFunction} that return the y component of a
	 * {@link Coords} datum.
	 */
	public static final DatumFunction<Double> Y_ACCESSOR = new DatumFunction<Double>() {
		@Override
		public Double apply(Element context, Value d, int index) {
			return d.<Coords> as().y();
		}
	};
}
