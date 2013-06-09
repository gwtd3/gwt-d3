package com.github.gwtd3.ui.chart;

import java.util.List;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.JsArrays;
import com.github.gwtd3.api.core.Datum;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.svg.Line;
import com.github.gwtd3.api.svg.Line.InterpolationMode;
import com.github.gwtd3.ui.model.DomainFilter;
import com.github.gwtd3.ui.model.PointBuilder;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;

public class LineGenerator<T> {

	private Line generator;
	protected PointBuilder<T> builder;
	protected DomainFilter<T> filter;

	public LineGenerator(PointBuilder<T> builder, DomainFilter<T> filter) {
		super();
		this.builder = builder;
		this.filter = filter;
		setup();
	}

	protected void setup() {
		generator = D3.svg().line().interpolate(InterpolationMode.BASIS)
				// convert the domain object to a pixel distance
				.x(new DatumFunction<Double>() {
					@Override
					public Double apply(final Element context, final Datum d, final int index) {
						int x = (int) builder.x(d.<T> as());
						// System.out.println(x);
						return (double) x;
					}
				})
				// convert the domain to a pixel distance
				.y(new DatumFunction<Double>() {
					@Override
					public Double apply(final Element context, final Datum d, final int index) {
						int y = (int) builder.y(d.<T> as());
						// System.out.println(y);
						return (double) y;
					}
				})
				.defined(new DatumFunction<Boolean>() {
					@Override
					public Boolean apply(final Element context, final Datum d, final int index) {
						T value = d.<T> as();
						// if (true) {
						// return true;
						// }
						boolean filtered = filter == null ? true : filter.accept(value);
						GWT.log("value " + value + " filter resulted:" + filtered);
						return filtered;
					}
				}
				);
	}

	/**
	 * Set the filter used to filter the T objects translated to x domain values
	 * that should not participate in building the lines.
	 * 
	 * @param filter
	 */
	public void setFilter(DomainFilter<T> filter) {
		this.filter = filter;
	}

	public String generate(List<T> values) {
		return generator.generate(JsArrays.asJsArray(values));
	}
}
