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

/**
 * A color defined by red, green and blue components.
 * 
 * @see <a href="https://github.com/mbostock/d3/wiki/Colors">official API</a>
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class RGBColor extends Color {

	protected RGBColor() {
		super();
	}

	/**
	 * @return the red component in the range [0;255]
	 */
	public native final int r()/*-{
		return this.r;
	}-*/;

	/**
	 * @return the green component in the range [0;255]
	 */
	public native final int g()/*-{
		return this.g;
	}-*/;

	/**
	 * @return the blue component in the range [0;255]
	 */
	public native final int b()/*-{
		return this.b;
	}-*/;

	/**
	 * Returns the equivalent color in HSL space; see d3.hsl for details on the returned object. The conversion from HSL to RGB is described in CSS3 Color Module Level 3; this is
	 * the equivalent reverse operation.
	 * 
	 * @return
	 */
	public native final HSLColor hsl()/*-{
		return this.hsl();
	}-*/;

	/**
	 * Returns a brighter copy of this color with a gamma of 1.
	 * 
	 * @return the new copy
	 */
	public native final RGBColor brighter()/*-{
		return this.brighter();
	}-*/;

	/**
	 * Returns a brighter copy of this color.
	 * <p>
	 * Each channel is multiplied by 0.7 ^ -k.
	 * 
	 * @param k
	 *            the gamma value
	 * @return the new copy
	 */
	public native final RGBColor brighter(int k)/*-{
		return this.brighter(k);
	}-*/;

	/**
	 * Returns a darker copy of this color with a gamma of 1.
	 * 
	 * @return the new copy
	 */
	public native final RGBColor darker()/*-{
		return this.darker();
	}-*/;

	/**
	 * Returns a darker copy of this color.
	 * <p>
	 * Each channel is multiplied by 0.7 ^ k.
	 * 
	 * @param k
	 *            the gamma value
	 * @return the new copy
	 */
	public native final RGBColor darker(int k)/*-{
		return this.darker(k);
	}-*/;

}
