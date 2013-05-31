/**
 * 
 */
package com.github.gwtd3.demo.client.ui;

import com.github.gwtd3.api.core.Selection;

/**
 * A {@link D3Widget} that add a <code>svg</code> and <code>g</code> tag.
 * Use the g {@link Selection} field to play in the svg.
 * 
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class SVGD3Widget extends D3Widget {

	protected final Selection svg;
	protected final Selection g;
	private Selection defsSelection;

	/**
	 * 
	 */
	public SVGD3Widget() {
		super();
		svg = selection().append("svg:svg");
		g = svg.append("svg:g");
	}

	public SVGD3Widget(final int translateX, final int translateY) {
		this();
		translate(translateX, translateY);
	}

	public SVGD3Widget(final int width, final int height, final int translateX, final int translateY) {
		this(translateX, translateY);
		setPixelSize(width, height);
	}

	public Selection g() {
		return g;
	}

	/**
	 * @return the selection representing the svg node
	 */
	public Selection svg() {
		return svg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.user.client.ui.UIObject#setWidth(java.lang.String)
	 */
	@Override
	public void setWidth(final String width) {
		svg.attr("width", width);
	}

	@Override
	public void setHeight(final String height) {
		svg.attr("height", height);
	}

	public void translate(final int x, final int y) {
		g.attr("transform", "translate(" + x + "," + y + ")");
	}

	/**
	 * @return a D3 selection representing the &lt;defs&gt; element.
	 */
	public Selection defs() {
		if (defsSelection == null) {
			defsSelection = svg.prepend("defs");
		}
		return defsSelection;
	}

	/**
	 * Find an element with the given id in the &lt;defs&gt; element
	 * 
	 * @param id
	 * @return
	 */
	public Selection getDefById(final String id) {
		return defs().select("#" + id);
	}
}
