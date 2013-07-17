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
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class HSLColor extends Color {

	protected HSLColor() {
		super();
	}

	/**
	 * @return the hue component in the range [0;360]
	 */
	public native final int h()/*-{
		return this.h;
	}-*/;

	/**
	 * @return the saturation component in the range [0;1]
	 */
	public native final double s()/*-{
		return this.s;
	}-*/;

	/**
	 * @return the lightness component in the range [0;1]
	 */
	public native final double l()/*-{
		return this.l;
	}-*/;

	/**
	 * Returns the equivalent color in RGB space; see d3.rgb for details on the
	 * returned object. The conversion from HSL to RGB is described in CSS3
	 * Color Module Level 3.
	 * 
	 * @return
	 */
	public native final RGBColor rgb()/*-{
		return this.rgb();
	}-*/;

	/**
	 * Returns a brighter copy of this color yith a gamme of 1.
	 * 
	 * @return the new copy
	 */
	public native final HSLColor brighter()/*-{
		return this.brighter();
	}-*/;

	/**
	 * Returns a brighter copy of this color.
	 * <p>
	 * The lightness channel is multiplied by 0.7 ^ -k.
	 * 
	 * @param k
	 *            the gamma value
	 * @return the new copy
	 */
	public native final HSLColor brighter(int k)/*-{
		return this.brighter(k);
	}-*/;

	/**
	 * Returns a darker copy of this color yith a gamme of 1.
	 * 
	 * @return the new copy
	 */
	public native final HSLColor darker()/*-{
		return this.darker();
	}-*/;

	/**
	 * Returns a darker copy of this color.
	 * <p>
	 * The lightness channel is multiplied by 0.7 ^ k.
	 * 
	 * @param k
	 *            the gamma value
	 * @return the new copy
	 */
	public native final HSLColor darker(int k)/*-{
		return this.darker(k);
	}-*/;

}
