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
package com.github.gwtd3.api.xhr;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Overlay type for an XmlHttpRequest error.
 * 
 * @author <a href="mailto:eric.citaire@gmail.com">Eric Citaire</a>
 * 
 */
public class XmlHttpRequest extends JavaScriptObject {
	protected XmlHttpRequest() {
		super();
	}

	public static enum ResponseType {
		ARRAYBUFFER, BLOB, JSON, TEXT, DOCUMENT;

		public static ResponseType fromString(final String s) {
			if ((s == null) || s.trim().isEmpty()) {
				return null;
			}
			return valueOf(s.toUpperCase());
		}
	}

	public final native String statusText() /*-{
		return this.statusText;
	}-*/;

	public final native int status() /*-{
		return this.status;
	}-*/;

	public final native String response() /*-{
		return this.response;
	}-*/;

	public final native ResponseType responseType() /*-{
		return @com.github.gwtd3.api.xhr.XmlHttpRequest.ResponseType::fromString(Ljava/lang/String;)(this.responseType());
	}-*/;

	public final native XmlHttpRequest responseType(ResponseType type)/*-{
		if(type==null){
			return this.responseType("");
		}
		else{
			var stype = type.@com.github.gwtd3.api.xhr.XmlHttpRequest.ResponseType::name()();
			stype = stype.@java.lang.String::toLowerCase()();
			return this.responseType(stype);
		}
	}-*/;
}
