package com.github.gwtd3.ui.chart;

import java.util.List;

import com.github.gwtd3.api.core.Datum;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.ui.data.DefaultSelectionUpdater;
import com.github.gwtd3.ui.model.DomainFilter;
import com.github.gwtd3.ui.model.PointBuilder;
import com.github.gwtd3.ui.model.ValueProvider;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;

/**
 * A {@link LineRenderer} appends to a selection a svg path element constructed
 * from a {@link PointBuilder}.
 * 
 * 
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 * @param <T>
 */
public class LineRenderer<T> extends DefaultSelectionUpdater {

	private LineGenerator<T> generator;
	private String styleName;

	public LineRenderer(final PointBuilder<T> builder, final String styleName) {
		this(builder, styleName, null);
	}

	/**
	 * 
	 * @param builder
	 *            the object converting values to X Y coords
	 * @param styles
	 *            the styles to apply to the new created line
	 * @param xDomainFilter
	 *            the filter
	 */
	public LineRenderer(final PointBuilder<T> builder, final String styleName, final DomainFilter<T> xDomainFilter) {
		// FIXME: let the subclass decide which element
		super("." + styleName);
		this.styleName = styleName;
		// create the line generator which will use the scale functions
		this.generator = new LineGenerator<T>(builder, xDomainFilter);

	}

	@Override
	public String getElementName() {
		// creates a SVG path
		return "path";
	}

	@Override
	public void afterEnter(final Selection selection) {
		super.afterEnter(selection);
		// configure the new created path
		selection.classed(styleName, true);
	}

	@Override
	public void onJoinEnd(final Selection selection) {
		super.onJoinEnd(selection);

		// setting the attribute d of the path
		selection.attr("d", new DatumFunction<String>() {
			@SuppressWarnings("unchecked")
			@Override
			public String apply(final Element context, final Datum d, final int index) {
				List<T> values = asProvider(d).getValues();
				GWT.log("LineRenderer : " + values.size() + " points");
				Object data = d.as();
				if (data instanceof DomainFilter) {
					generator.setFilter((DomainFilter<T>) data);
				}
				return generator.generate(values);
			}
		});
	}

	private ValueProvider<T> asProvider(final Value v) {
		return v.as();
	}
}
