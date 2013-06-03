/**
 * 
 */
package com.github.gwtd3.ui.svg;

import org.vectomatic.dom.svg.impl.SVGGElement;

import com.google.gwt.user.client.Element;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class GContainer extends Container implements HasTransform {

	/**
	 * 
	 */
	public GContainer() {
		super((Element) createSVGElement().cast());
	}

	public static native final SVGGElement createSVGElement()/*-{
		return document.createElementNS("http://www.w3.org/2000/svg", "g");
	}-*/;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.gwtd3.ui.svg.HasTransform#transform()
	 */
	@Override
	public TransformBuilder transform() {
		return TransformBuilder.parse(this);
	}

	public SVGGElement getGElement() {
		return this.getElement().cast();
	}
}
