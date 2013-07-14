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
package com.github.gwtd3.api.dsv;

import com.google.gwt.core.client.JsArrayString;

/**
 * This accessor function is invoked for each row in a DSV file, being passed
 * the current row and index as two arguments. The return value of the function
 * replaces the element in the returned array of rows; if the function returns
 * null, the row is stripped from the returned array of rows. In effect, the
 * accessor is similar to applying a map and filter operator to the returned
 * rows. The accessor function is used by parse to convert each row to an object
 * with named attributes.
 * 
 * @author <a href="mailto:eric.citaire@gmail.com">Eric Citaire</a>
 * 
 * @param <T>
 *            the type of the parsed DSV line
 * 
 * @see Dsv#parseRows(String)
 */
public interface DsvArrayAccessor<T> {
	/**
	 * An accessor function may invoked for each row in a DSV file, being passed
	 * the current row and index as two arguments. The return value of the
	 * function replaces the element in the returned array of rows; if the
	 * function returns null, the row is stripped from the returned array of
	 * rows. In effect, the accessor is similar to applying a map and filter
	 * operator to the returned rows. The accessor function is used by parse to
	 * convert each row to an object with named attributes.
	 * 
	 * @param row the current row as an array
	 * @param index the index of the current row
	 * @return
	 */
	T parse(JsArrayString row, int index);
}
