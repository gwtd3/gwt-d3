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
package com.github.gwtd3.api.core;

import com.github.gwtd3.api.D3;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * Constructing visualizations often involves working with colors.
 * <p>
 * Even though your browser understands a lot about colors, it doesn't offer
 * much help in manipulating colors through JavaScript.
 * <p>
 * So D3 provides representations for both RGB and HSL colors, allowing
 * interpolation in both color spaces, and making colors brighter or darker. For
 * more about color manipulation, see the Wikipedia entries on RGB and HSL.
 * <p>
 * Note: while you can work with colors directly, you might also want to take a
 * look at D3's built-in {@link D3#interpolateRgb}, {@link D3#interpolateHsl} and {@link D3#scale}.
 * <p>
 * If you are looking for color palettes, see the ordinal scales reference.
 * <p>
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Color extends JavaScriptObject {

	protected Color() {
		super();
	}

	/**
	 * Converts to a RGB hexadecimal string, such as "#f7eaba".
	 * 
	 * @return hexa representation of the color
	 */
	public final native String toHexaString()/*-{
		return this.toString();
	}-*/;

}
