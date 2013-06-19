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
package com.github.gwtd3.ui.svg;

import com.github.gwtd3.api.JsArrays;
import com.github.gwtd3.ui.D3Widget;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

public class SVGWidget extends D3Widget implements IsSVG {

	protected SVGWidget(final Element e) {
		super(e);
	}

	protected SVGWidget(final String tag) {
		this(createSVGElement(tag));
	}

	protected static native final Element createSVGElement(String tag)/*-{
		return document.createElementNS("http://www.w3.org/2000/svg", tag);
	}-*/;

	@Override
	public void setWidth(final String width) {
		getElement().setAttribute("width", width);
	}

	@Override
	public void setHeight(final String height) {
		getElement().setAttribute("height", height);
	}

	// =============== overriding style names ==========
	// SVG DOM className is not a String but a complex object
	// so all Element.getClassName is not working....
	// =======

	@Override
	public String getStyleName() {
		return getElement().getAttribute("class");
	}

	@Override
	public void addStyleName(final String style) {
		select().classed(style, true);
	}

	@Override
	public void setStyleName(final String style) {
		getElement().setAttribute("class", style);
	}

	@Override
	public void setStyleName(final String style, final boolean add) {
		select().classed(style, add);
	}

	@Override
	public String getStylePrimaryName() {
		String classNames = select().attr("class");
		classNames = classNames == null ? "" : classNames;
		String[] split = classNames.split(" ");
		if (split.length > 0) {
			return split[0].trim();
		}
		return "";
	}

	@Override
	public void setStylePrimaryName(String newPrimaryName) {
		String oldPrimaryName = getStylePrimaryName();
		newPrimaryName = newPrimaryName == null ? "" : newPrimaryName.trim();
		if (oldPrimaryName.length() == 0) {
			// previous empty class name
			setStyleName(newPrimaryName);
		}
		else {
			// previous empty class name
			String classNames = select().attr("class");
			String[] split = classNames.split(" ");
			split[0] = newPrimaryName;
			for (int i = 1; i < split.length; i++) {
				String name = split[i];
				// dependant stylename case => replace with new primaryname + suffix
				if (isStyleDependantName(name, oldPrimaryName)) {
					String suffix = name.substring(name.indexOf('-'));
					split[i] = newPrimaryName + suffix;
				}
			}
			// join the splits
			String newStyle = JsArrays.asJsArray(split).join();// join array
			setStyleName(newStyle);
		}
	}

	protected static boolean isStyleDependantName(final String name, final String primaryName) {
		return (primaryName.length() > 0) && name.startsWith(primaryName)
				&& (name.length() > primaryName.length())
				&& (name.indexOf('-') == primaryName.length());
	}

	@Override
	public void removeStyleName(final String style) {
		select().classed(style, false);
	}

	// =============== document ===========
	@Override
	public SVGDocumentContainer getDocument() {
		Widget parent = this;
		while (parent != null) {
			if (parent instanceof SVGDocumentContainer) {
				return (SVGDocumentContainer) parent;
			}
			parent = parent.getParent();
		}
		return null;
	}

	@Override
	public SVGDocumentContainer getRootDocument() {
		Widget parent = this;
		SVGDocumentContainer root = null;
		while (parent != null) {
			if (parent instanceof SVGDocumentContainer) {
				root = (SVGDocumentContainer) parent;
			}
			parent = parent.getParent();
		}
		return root;
	}
}
