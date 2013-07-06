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
/**
 * 
 */
package com.github.gwtd3.ui.svg;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.core.Color;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.UpdateSelection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.scales.Scale;
import com.google.gwt.dom.client.Element;

/**
 * Creates a parameterized SVG gradient.
 * <p>
 * Gradient works as a number of colors (the steps), each color can be converted into a color at a given offset (in percentage).
 * 
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class GradientBuilder {

	private final SVGDocumentContainer widget;
	private Selection gradientSelection;
	private int steps = 2;
	private final Scale<?> offsetScale = createOffsetScaleFromSteps();

	private final DatumFunction<String> offsetAccessor = new DatumFunction<String>() {
		@Override
		public String apply(final Element context, final Value d, final int index) {
			int step = d.asInt();
			// linearly set the offset
			// (but you could also use log or other...)
			return (offsetScale.apply(step)) + "%";
		}
	};

	public enum GradientType {
		LINEAR,
		RADIAL;
	}

	public enum SpreadMethod {
		pad,
		repeat,
		reflect;
	}

	/**
	 * Define the number of colors defining the gradient. The default value is
	 * 2.
	 * 
	 * @param steps
	 * @return
	 */
	public GradientBuilder steps(final int steps) {
		this.steps = steps;
		return this;
	}

	/**
	 * @return a scale appropriate to compute the svg Color stop offset from a
	 *         step.
	 */
	protected Scale<?> createOffsetScaleFromSteps() {
		return D3.scale.linear().domain(0, steps - 1).range(0, 100);
	}

	// TODO: gradientTransform (rotate the gradient before applied)
	// TODO: gradientUnits
	// TODO: xlink:href (inherit from another gradient)

	private GradientBuilder(final SVGDocumentContainer widget) {
		super();
		this.widget = widget;
	}

	public static GradientBuilder createHorizontalLinearGradient(final SVGDocumentContainer widget, final String gradientId) {
		GradientBuilder builder = new GradientBuilder(widget);
		widget.defs().select("#" + gradientId).remove();
		builder.gradientSelection = widget.defs().append("linearGradient")
				.attr("id", gradientId)
				.attr("x1", "0%")
				.attr("y1", "0%")
				.attr("x2", "100%")
				.attr("y2", "0%");
		builder.gradientSelection.selectAll("stop").remove();
		return builder;
	}

	public GradientBuilder build(final DatumFunction<Color> colorFunction) {

		UpdateSelection stopSelection = gradientSelection.selectAll("stop")
				.data(D3.range(steps));
		// remove old stops
		stopSelection.exit().remove();
		// add new ones
		stopSelection.enter()
				.append("stop")
				.attr("offset", offsetAccessor)
				.attr("stop-color", colorFunction)
				.attr("stop-opacity", 1.0);

		return this;
	}

	public static GradientBuilder createVerticalLinearGradient(final SVGDocumentContainer widget, final String gradientId) {
		GradientBuilder builder = new GradientBuilder(widget);
		widget.defs().select("#" + gradientId).remove();
		builder.gradientSelection = widget.defs()
				.append("linearGradient")
				.attr("id", gradientId)
				.attr("x1", "0%")
				.attr("y1", "0%")
				.attr("x2", "0%")
				.attr("y2", "100%");
		return builder;
	}

	public GradientBuilder appendStop(final int offsetPercent, final Color stopColor) {
		return appendStop(offsetPercent, stopColor, 1.0);
	}

	public GradientBuilder appendStop(final int offsetPercent, final Color stopColor, final double stopOpacity) {
		this.gradientSelection.append("stop")
				.attr("offset", offsetPercent + "%")
				.attr("stop-color", stopColor.toHexaString())
				.attr("stop-opacity", stopOpacity);
		return this;
	}

	public GradientBuilder setSpreadMethod(final SpreadMethod method) {
		this.gradientSelection.attr("spread-method", method.name());
		return this;
	}

	public static GradientBuilder createLinearGradient(final SVGDocumentContainer widget, final String gradientId) {
		GradientBuilder builder = new GradientBuilder(widget);
		widget.defs().select("#" + gradientId).remove();
		builder.gradientSelection = widget.defs().append("linearGradient")
				.attr("id", gradientId)
				.attr("x1", "0%")
				.attr("y1", "0%")
				.attr("x2", "0%")
				.attr("y2", "100%");
		builder.gradientSelection.selectAll("stop").remove();
		return builder;
	}

	/**
	 * @return
	 */
	public String toFillUrl() {
		return "url('#" + gradientSelection.attr("id") + "')";
	}

}
