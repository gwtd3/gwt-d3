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
package com.github.gwtd3.ui.chart;

import java.util.List;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.JsArrays;
import com.github.gwtd3.api.core.Value;
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

	public LineGenerator(final PointBuilder<T> builder, final DomainFilter<T> filter) {
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
					public Double apply(final Element context, final Value d, final int index) {
						int x = (int) builder.x(d.<T> as());
						// System.out.println(x);
						return (double) x;
					}
				})
				// convert the domain to a pixel distance
				.y(new DatumFunction<Double>() {
					@Override
					public Double apply(final Element context, final Value d, final int index) {
						int y = (int) builder.y(d.<T> as());
						// System.out.println(y);
						return (double) y;
					}
				})
				.defined(new DatumFunction<Boolean>() {
					@Override
					public Boolean apply(final Element context, final Value d, final int index) {
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
	public void setFilter(final DomainFilter<T> filter) {
		this.filter = filter;
	}

	public String generate(final List<T> values) {
		return generator.generate(JsArrays.asJsArray(values));
	}
}
