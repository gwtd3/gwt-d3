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
package com.github.gwtd3.demo.client.democases;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.Interpolators;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Transition;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.interpolators.CallableInterpolator;
import com.github.gwtd3.api.interpolators.Interpolator;
import com.github.gwtd3.api.svg.Arc;
import com.github.gwtd3.api.tweens.TweenFunction;
import com.github.gwtd3.demo.client.DemoCase;
import com.github.gwtd3.demo.client.Factory;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * Original demo is <a href="http://bl.ocks.org/mbostock/3808218">here</a>
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class ArcTween extends FlowPanel implements DemoCase {

	private static final String INTRO_TEXT = "This demonstrate the SVG Arc API with Transition API: here, a complex transition is constructed from a custom interpolator. "
			+ "Moreover, the centroid() method of Arc is illustrated to show how easy it is to display labels in a donut chart.";
	private Timer timer;
	private Selection svg;
	private Arc arc;
	private Selection centroidText;

	/**
	 * 
	 */
	public ArcTween() {
		super();
		this.add(new Label(INTRO_TEXT));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.gwtd3.demo.client.D3Demo#start()
	 */

	@Override
	public void start() {
		int width = 960, height = 500;

		final double TWO_PI = 2 * Math.PI; // http://tauday.com/tau-manifesto

		// the arc function defines constants for inner and outer radius, and
		// start angle
		// but the end angle is deliberately kept undefined
		arc = D3.svg().arc().innerRadius(180).outerRadius(240).startAngle(0);

		// Create the SVG container, and apply a transform such that the origin
		// is the center of the canvas. This way, we don't need to position
		// arcs individually.
		svg = D3.select(this)
				.append("svg")
				.attr("width", width)
				.attr("height", height)
				.append("g")
				.attr("transform",
						"translate(" + (width / 2) + "," + (height / 2) + ")");

		// construct a a stupid object containing the
		// property "endAngle" as a constant.
		Arc json = Arc.constantArc().endAngle(TWO_PI);
		// Add the background arc, from 0 to 100%
		// Here, the path d attribute is filled using the arc function,
		// which will received in parameter the object passed to datum.
		// This function will used the default accessors of the Arc objects,
		// those accessors assuming that the data passed contains
		// attributes named as the accessors.
		svg.append("path")
		// pass a data representing a constant endAngle arc
				.datum(json).style("fill", "#ddd").attr("d", arc);

		// set the angle to 12.7%
		json.endAngle(.127 * TWO_PI);
		// Add the foreground arc in orange, currently showing 12.7%.
		final Selection foreground = svg.append("path").datum(json)
				.style("fill", "orange").attr("d", arc);

		centroidText = svg.append("text").text("centroid").datum(json)
				.style("fill", "white").style("stroke", "black")
				.style("font-size", "30px");
		final int textWidth = getTextWidth(centroidText.node());
		// Every so often, start a transition to a new random angle. Use //
		// transition.call // (identical to selection.call) so that we can
		// encapsulate the logic // for // tweening the arc in a separate
		// function
		// below.

		timer = new Timer() {

			@Override
			public void run() {
				Transition transition = foreground.transition().duration(750);
				final double newAngle = Math.random() * TWO_PI;
				doTransition(transition, newAngle);
				centroidText.transition().duration(750)
						.attr("transform", new DatumFunction<String>() {
							@Override
							public String apply(Element context, Value d,
									int index) {
								Arc newArc = Arc.copy(d.<Arc> as()).endAngle(
										newAngle);
								Array<Double> point = arc.centroid(newArc,
										index);
								return "translate("
										+ (point.getNumber(0) - textWidth / 2)
										+ "," + point.getNumber(1) + ")";
							}
						});
			}
		};
		timer.scheduleRepeating(1500);
	}

	private static final native int getTextWidth(Element e)/*-{
		return e.getBBox().width;
	}-*/;

	public static interface TransitionFunction {
		public void apply(Transition t, Object... objects);
	}

	/**
	 * @param transition
	 * @param d
	 */
	protected void doTransition(final Transition transition,
			final double newAngle) {

		transition.attrTween("d", new TweenFunction<String>() {
			@Override
			public Interpolator<String> apply(final Element context,
					final Value datum, final int index,
					final Value currentAttributeValue) {
				try {
					final Arc arcDatum = datum.as();
					final double endAngle = arcDatum.endAngle();
					return new CallableInterpolator<String>() {
						private final Interpolator<Double> interpolator = Interpolators
								.interpolateNumber(endAngle, newAngle);

						@Override
						public String interpolate(final double t) {
							double interpolated = interpolator.interpolate(t);
							arcDatum.endAngle(interpolated);
							return arc.generate(arcDatum);
						}
					};
				} catch (Exception e) {
					GWT.log("Error during transition", e);
					throw new IllegalStateException("Error during transition",
							e);
				}
			}
		});

		// transition.attrTween("d", "blah");
	}

	@Override
	public void stop() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		arc = null;

	}

	public static Factory factory() {
		return new Factory() {
			@Override
			public DemoCase newInstance() {
				return new ArcTween();
			}
		};
	}
}
