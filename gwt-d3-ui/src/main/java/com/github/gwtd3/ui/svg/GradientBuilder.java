/**
 * 
 */
package com.github.gwtd3.ui.svg;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.core.Color;
import com.github.gwtd3.api.core.Datum;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.UpdateSelection;
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

	private final SVGDocument widget;
	private Selection gradientSelection;
	private int steps = 2;
	private final Scale<?> offsetScale = createOffsetScaleFromSteps();

	private final DatumFunction<String> offsetAccessor = new DatumFunction<String>() {
		@Override
		public String apply(final Element context, final Datum d, final int index) {
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

	private GradientBuilder(final SVGDocument widget) {
		super();
		this.widget = widget;
	}

	public static GradientBuilder createHorizontalLinearGradient(final SVGDocument widget, final String gradientId) {
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

	public static GradientBuilder createVerticalLinearGradient(final SVGDocument widget, final String gradientId) {
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

	public static GradientBuilder createLinearGradient(final SVGDocument widget, final String gradientId) {
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
