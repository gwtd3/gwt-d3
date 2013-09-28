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
package com.github.gwtd3.api.geo;

import com.google.gwt.core.client.JavaScriptObject;

public class Geography extends JavaScriptObject {

	protected Geography() {

	}

	/**
	 * An alias for {@link #conicEqualArea()}, with USA-centric defaults:
	 * <ul>
	 * <li>scale 1000,
	 * <li>translate [480, 250],
	 * <li>rotation [96°, 0°],
	 * <li>center ⟨-0.6°, 38.7°⟩
	 * <li>parallels [29.5°, 45.5°],
	 * </ul>
	 * making it suitable for displaying the United States, centered around <a
	 * href="https://maps.google.com/maps?q=Hutchinson,+Kansas&z=5">Hutchinson,
	 * Kansas</a> in a 960×500 area.
	 * <p>
	 * The central meridian and parallels are specified by the <a
	 * href="http://www.usgs.gov/">USGS</a> in the 1970 <a
	 * href="http://www.nationalatlas.gov/">National Atlas</a>.
	 * <p>
	 * 
	 * @return the projection
	 */
	public native final ConicProjection albers()/*-{
		return this.albers();
	}-*/;

	/**
	 * The Albers projection, as an <a
	 * href="http://en.wikipedia.org/wiki/Map_projection#Equal-area">equal-area
	 * projection</a>, is recommended for <a
	 * href="http://mbostock.github.com/d3/ex/choropleth.html">choropleths</a>
	 * as it preserves the relative areas of geographic features.
	 * <p>
	 * 
	 * @return the projection
	 */
	public native final ConicProjection conicEqualArea()/*-{
		return this.conicEqualArea();
	}-*/;
}
