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
package com.github.gwtd3.api.time;

import java.util.Date;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsDate;

public class TimeFormat extends JavaScriptObject {

    public static class Builder extends JavaScriptObject {
        protected Builder() {
            super();
        }

        /**
         * Constructs a new UTC time formatter using the given specifier. The specifier may contain the same directives
         * as the local time format. Internally, this time formatter is implemented using the UTC methods on the Date
         * object, such as <a
         * href="https://developer.mozilla.org/en/JavaScript/Reference/Global_Objects/Date/getUTCDate">getUTCDate</a>
         * and <a
         * href="https://developer.mozilla.org/en/JavaScript/Reference/Global_Objects/Date/setUTCDate">setUTCDate</a> in
         * place of <a
         * href="https://developer.mozilla.org/en/JavaScript/Reference/Global_Objects/Date/getDate">getDate</a> and <a
         * href="https://developer.mozilla.org/en/JavaScript/Reference/Global_Objects/Date/setDate">setDate</a>.
         * 
         * @param specifier
         *            the specifier string.
         * @return the format.
         */
        public final native TimeFormat utc(String specifier) /*-{
			return this.utc(specifier);
        }-*/;

        /**
         * The full <a href="http://en.wikipedia.org/wiki/ISO_8601">ISO 8601</a> UTC time format:
         * "%Y-%m-%dT%H:%M:%S.%LZ". Where available, this method will use
         * <a href="https://developer.mozilla.org/en-US/docs/JavaScript/Reference/Global_Objects/Date/toISOString">Date.
         * toISOString</a> to format and the Date constructor to parse strings. If you depend on strict validation of
         * the input format according to ISO 8601, you should construct a time format explicitly instead:<br>
         * <code>TimeFormat iso = D3.time().format().utc("%Y-%m-%dT%H:%M:%S.%LZ");</code>
         * 
         * @return the format.
         */
        public final native TimeFormat iso() /*-{
			return this.iso;
        }-*/;
    }

    protected TimeFormat() {
        super();
    }

    /**
     * Parse a string into a date.
     * <p>
     * Parses the specified string, returning the corresponding date object. If the parsing fails, returns null. Unlike
     * "natural language" date parsers (including JavaScript's built-in parse), this method is strict: if the specified
     * string does not exactly match the associated format specifier, this method returns null.
     * 
     * @param s the string to parse.
     * @return the corresponding date object.
     */
    public final native JsDate parse(String s) /*-{
		return this.parse(s);
    }-*/;

    /**
     * Formats the specified date, returning the corresponding string.
     * 
     * @param date The date to format.
     * @return The formatted string.
     */
    public final native String apply(JsDate date) /*-{
		return this(date);
    }-*/;

    /**
     * Formats the specified date, returning the corresponding string.
     * 
     * @param date The date to format.
     * @return The formatted string.
     */
    public final String apply(final Date date) {
        JsDate jsDate = JsDate.create(date.getTime());
        return apply(jsDate);
    }

}
