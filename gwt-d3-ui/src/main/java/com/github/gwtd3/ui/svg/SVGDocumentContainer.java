package com.github.gwtd3.ui.svg;

import static com.github.gwtd3.ui.svg.SVGContainer.*;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.core.Selection;
import com.google.common.base.Preconditions;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.Shared;

/**
 * A widget serving as a container for SVG graphics.
 * <p>
 * Use this widget when you want to create SVG graphics from outside of the widget itself, for instance.
 * <p>
 * 
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class SVGDocumentContainer extends SVGContainer implements ISVGDocument {

	protected Resources resources;
	protected Styles styles;
	private Element defs;

	/**
	 * Create an instance of the default {@link Resources}.
	 * 
	 * @return the resources
	 */
	private static Resources createDefaultResources() {
		return GWT.create(Resources.class);
	}

	/**
	 * The client bundle of this widget
	 * 
	 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
	 * 
	 */
	public static interface Resources extends ClientBundle {
		@Source("SVGCanvas.css")
		Styles getStyles();
	}

	@Shared
	public static interface Styles extends CssResource {
		/**
		 * The class to apply to the svg element
		 * 
		 * @return the class name
		 */
		public String svg();
	}

	public SVGDocumentContainer() {
		this(createDefaultResources());

	}

	public SVGDocumentContainer(final Resources resources) {
		super("svg");
		Preconditions.checkNotNull(resources);
		this.resources = resources;
		this.styles = this.resources.getStyles();
	}

	@Override
	public void setWidth(final String width) {
		getElement().setAttribute("width", width);
	}

	@Override
	public void setHeight(final String height) {
		getElement().setAttribute("height", height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.gwtd3.ui.D3Widget#onSelectionAttached(com.github.gwtd3.api.core.Selection)
	 */
	protected void onSelectionAttached(final Selection selection) {
		// in

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.user.client.ui.Widget#onLoad()
	 */
	@Override
	protected void onLoad() {
		super.onLoad();
		if (resources != null) {
			// inject(styles);
			select().classed(styles.svg(), true);
		}
	}

	/**
	 * @return a D3 selection representing the &lt;defs&gt; element.
	 */
	@Override
	public Selection defs() {
		if (defs == null) {
			defs = getElement().insertFirst(createSVGElement("defs")).cast();
		}
		return D3.select(defs);
	}

	/**
	 * Inject the given stylesheet as a new <code>style</code> element
	 * in the <code>defs</code> of this document.
	 * 
	 * @param resource
	 *            the css stylesheet to inject
	 */
	@Override
	public void inject(final CssResource resource) {
		String styleContents = "\n" + resource.getText() + "\n";
		// String styleContents = "\n<![CDATA[\n" + resource.getText() +
		// "\n]]>\n";
		defs().append("style").attr("type", "text/css").html(styleContents);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.gwtd3.ui.HasD3Selection#select()
	 */
	@Override
	public Selection select() {
		return D3.select(this);
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
