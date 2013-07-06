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
package com.github.gwtd3.api.functions;

import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Value;
import com.google.gwt.dom.client.Element;

/**
 * A {@link DatumFunction} that counts something, mainly used as a debugging purpose
 * in {@link Selection#each(DatumFunction)} method.
 * <p>
 * You may override the {@link #takeIntoAccount(Element, Datum, int)} to change when the count is incremented.
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class CountFunction implements DatumFunction<Void> {
	private int count = 0;

	@Override
	public Void apply(final Element context, final Value d, final int index) {
		if (takeIntoAccount(context, d, index)) {
			count++;
		}
		return null;
	}

	/**
	 * Return true by default.
	 * 
	 * @param context
	 * @param d
	 * @param index
	 * @return true to increment the count, false otherwise.
	 */
	private boolean takeIntoAccount(final Element context, final Value d, final int index) {
		return true;
	}

	public int getCount() {
		return count;
	}

	public CountFunction reset() {
		count = 0;
		return this;
	}
}