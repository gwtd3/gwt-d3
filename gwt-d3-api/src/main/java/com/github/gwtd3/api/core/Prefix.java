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
package com.github.gwtd3.api.core;

import com.github.gwtd3.api.D3;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * A <a href="http://en.wikipedia.org/wiki/Metric_prefix">SI Prefix</a>, as
 * returned by {@link D3#formatPrefix()}
 * <p>
 * Example:
 * 
 * {@code
 * var prefix = d3.formatPrefix(1.21e9);
 * 
 * console.log(prefix.symbol);
 * 
 * console.log(prefix.scale(1.21e9)); // 1.21
 * }
 * <p>
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Prefix extends JavaScriptObject {

	protected Prefix() {
	}

	/**
	 * Returns the prefix symbol, such as "M" for millions.
	 * <p>
	 * 
	 * @return the prefix symbol
	 */
	public native final String symbol()/*-{
		return this.symbol;
	}-*/;

	/**
	 * Convert the number to the appropriate prefixed scale.
	 * <p>
	 * 
	 * @param input
	 *            the number to convert
	 * @return the converted number
	 */
	public native final double scale(double input)/*-{
		return this.scale(input)
	}-*/;
}
