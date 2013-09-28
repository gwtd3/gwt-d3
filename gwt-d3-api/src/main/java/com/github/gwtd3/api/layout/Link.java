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
package com.github.gwtd3.api.layout;

import com.github.gwtd3.api.Coords;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * A link in d3js' tree layout, see <a
 * href="https://github.com/mbostock/d3/wiki/Tree-Layout#wiki-links">d3 docs on
 * link</a>. Provides accessors and setters for a link's two key attributes
 * source and target.
 * 
 * @author <a href="mailto:evanshi09@gmail.com">Evan Shi</a>
 * 
 */
public class Link
    extends JavaScriptObject {
    protected Link() {
        super();
    }

    /**
     * Create a basic link object starting at one coordinate and ending at
     * another
     * 
     * @param the starting coordinates
     * @param the ending coordinates
     * @return the link object
     */
    public static final native Link create(Coords source, Coords target) /*-{
		return {
			source : source,
			target : target
		};
    }-*/;

    /**
     * @return the end node
     */
    public final native Node target() /*-{
		return this.target;
    }-*/;

    /**
     * @return the start node
     */
    public final native Node source() /*-{
		return this.source;
    }-*/;
}
