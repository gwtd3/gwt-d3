package com.github.gwtd3.ui.chart;

import java.util.List;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.JsArrays;
import com.github.gwtd3.api.core.Datum;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.svg.Line;
import com.github.gwtd3.api.svg.Line.InterpolationMode;
import com.github.gwtd3.ui.chart.LineChart.Styles;
import com.github.gwtd3.ui.data.DefaultSelectionUpdater;
import com.github.gwtd3.ui.data.SelectionUpdater;
import com.github.gwtd3.ui.model.PointViewer;
import com.github.gwtd3.ui.model.Serie;
import com.google.gwt.dom.client.Element;

public class LineRenderer<T> extends DefaultSelectionUpdater implements SelectionUpdater {

	private final Styles styles;
	private Line lineGenerator;

	public LineRenderer(final PointViewer<T> builder, final Styles styles) {
		// FIXME: let the subclass decide which element
		super("." + styles.line());
		this.styles = styles;
		// create the line generator which will use the scale functions
		lineGenerator = D3.svg().line().interpolate(InterpolationMode.BASIS)
				// convert the domain object to a pixel distance
				.x(new DatumFunction<Double>() {
					@Override
					public Double apply(final Element context, final Datum d, final int index) {
						double x = builder.x(d.<T> as());
						// System.out.println(x);
						return x;
					}
				})
				// convert the domain to a pixel distance
				.y(new DatumFunction<Double>() {
					@Override
					public Double apply(final Element context, final Datum d, final int index) {
						double y = builder.y(d.<T> as());
						// System.out.println(y);
						return y;
					}
				})
				.defined(new DatumFunction<Boolean>() {
					@Override
					public Boolean apply(final Element context, final Datum d, final int index) {
						return builder.isVisible(d.<T> as());
					}
				}
				);

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
		selection.classed(styles.line(), true);
	}

	@Override
	public void onJoinEnd(final Selection selection) {
		super.onJoinEnd(selection);

		// setting the attribute d of the path
		selection.attr("d", new DatumFunction<String>() {
			@Override
			public String apply(final Element context, final Datum d, final int index) {
				List<T> values = asSerie(d).values();
				return lineGenerator.apply(JsArrays.asJsArray(values));
			}
		});
	}

	private Serie<T> asSerie(final Value v) {
		return v.as();
	}
}
