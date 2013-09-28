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
package com.github.gwtd3.demo.client.democases.geom;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.functions.TimerFunction;
import com.github.gwtd3.api.geom.Quadtree.Callback;
import com.github.gwtd3.api.geom.Quadtree.Node;
import com.github.gwtd3.api.geom.Quadtree.RootNode;
import com.github.gwtd3.demo.client.DemoCase;
import com.github.gwtd3.demo.client.Factory;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;

public class MitchellBestCandidate extends FlowPanel implements DemoCase {

	private boolean done = false;

	private static class Circle {
		double x, y, r;

		public Circle(final double x, final double y, final double r) {
			super();
			this.x = x;
			this.y = y;
			this.r = r;
		}

	}

	private static interface CircleGenerator {
		public Circle generate(double k);
	}

	private CircleGenerator bestCircleGenerator(final double maxRadius,
			final double padding) {
		final RootNode<Circle> quadtree = D3.geom().quadtree()
				.x(new DatumFunction<Double>() {
					@Override
					public Double apply(final Element context, final Value d,
							final int index) {
						return d.<Circle> as().x;
					}
				}).y(new DatumFunction<Double>() {
					@Override
					public Double apply(final Element context, final Value d,
							final int index) {
						return d.<Circle> as().y;
					}
				}).extent(0, 0, width, height).apply(Array.<Circle> create());
		return new CircleGenerator() {
			private double minDistance;
			double searchRadius = maxRadius * 2, maxRadius2 = maxRadius
					* maxRadius;
			double bestX, bestY, bestDistance = 0;

			@Override
			public Circle generate(final double k) {
				bestX = bestY = bestDistance = 0;

				for (int i = 0; (i < k) || (bestDistance < padding); ++i) {
					final double x = (Math.random() * width), y = (Math
							.random() * height), rx1 = x - searchRadius, rx2 = x
							+ searchRadius, ry1 = y - searchRadius, ry2 = y
							+ searchRadius;
					minDistance = maxRadius; // minimum distance for this
					// candidate

					quadtree.visit(new Callback<Circle>() {
						@Override
						public boolean visit(final Node<Circle> quad,
								final double x1, final double y1,
								final double x2, final double y2) {
							Circle p = quad.point();
							if (p != null) {
								double dx = x - p.x, dy = y - p.y, d2 = (dx * dx)
										+ (dy * dy), r2 = p.r * p.r;
								if (d2 < r2) {
									minDistance = 0;
									return true;
								}
								// within a circle
								double d = (Math.sqrt(d2) - p.r);
								if (d < minDistance) {
									minDistance = d;
								}
							}
							// outside
							// search
							// radius
							return (minDistance == 0) || (x1 > rx2)
									|| (x2 < rx1) || (y1 > ry2) || (y2 < ry1); // or
						}
					});

					if (minDistance > bestDistance) {
						bestX = x;
						bestY = y;
						bestDistance = minDistance;
					}
				}

				Circle best = new Circle(bestX, bestY, bestDistance - padding);
				quadtree.add(best);
				return best;
			}
		};
	}

	private static class Margin {
		public final int top;
		public final int right;
		public final int bottom;
		public final int left;

		public Margin(final int top, final int right, final int bottom,
				final int left) {
			super();
			this.top = top;
			this.right = right;
			this.bottom = bottom;
			this.left = left;
		}
	}

	int maxRadius = 32, // maximum radius of circle
			padding = 1; // padding between circles; also minimum radius
	Margin margin = new Margin(-maxRadius, -maxRadius, -maxRadius, -maxRadius);
	int width = 960 - margin.left - margin.right, height = 500 - margin.top
			- margin.bottom;
	private Selection svg;

	double k = 1, // initial number of candidates to consider per circle
			m = 10, // initial number of circles to add per frame
			n = 2500; // remaining number of circles to add
	private TimerFunction timerFunction;

	@Override
	public void start() {

		final CircleGenerator newCircle = bestCircleGenerator(maxRadius,
				padding);

		svg = D3.select(this)
				.append("svg")
				.attr("width", width)
				.attr("height", height)
				.append("g")
				.attr("transform",
						"translate(" + margin.left + "," + margin.top + ")");

		timerFunction = new TimerFunction() {

			@Override
			public boolean execute() {
				for (int i = 0; (i < m) && (--n >= 0); ++i) {
					Circle circle = newCircle.generate(k);

					svg.append("circle").attr("cx", circle.x)
							.attr("cy", circle.y).attr("r", 0)
							.style("fill-opacity", (Math.random() + .5) / 2)
							.transition().attr("r", circle.r);

					// As we add more circles, generate more candidates per
					// circle.
					// Since this takes more effort, gradually reduce circles
					// per frame.
					if (k < 500) {
						k *= 1.01;
						m *= .998;
					}
				}
				return n == 0 || done;
			}
		};
		done = false;
		D3.timer(timerFunction);

	}

	@Override
	public void stop() {
		done = true;
	}

	public static Factory factory() {
		return new Factory() {
			@Override
			public DemoCase newInstance() {
				return new MitchellBestCandidate();
			}
		};
	}

}
