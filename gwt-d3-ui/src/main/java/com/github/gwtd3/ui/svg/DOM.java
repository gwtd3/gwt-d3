package com.github.gwtd3.ui.svg;

import com.google.gwt.user.client.Element;

public class DOM {

    public static native final Element createSVGElement(String tag)/*-{
		return document.createElementNS("http://www.w3.org/2000/svg", tag);
    }-*/;

    public static native final Element getSVGAttribute(Element e, String name)/*-{
		return e.getAttributeNS("http://www.w3.org/2000/svg", name);
    }-*/;
}
