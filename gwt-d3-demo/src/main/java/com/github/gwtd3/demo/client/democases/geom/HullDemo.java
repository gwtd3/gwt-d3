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
package com.github.gwtd3.demo.client.democases.geom;

import com.github.gwtd3.api.Arrays;
import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.arrays.ForEachCallback;
import com.github.gwtd3.api.core.Random;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.UpdateSelection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.geom.Hull;
import com.github.gwtd3.demo.client.DemoCase;
import com.github.gwtd3.demo.client.Factory;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * Original demo is <a href="http://bl.ocks.org/mbostock/3808218">here</a>
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class HullDemo extends FlowPanel implements DemoCase {

	public interface Bundle extends ClientBundle {
		public static final Bundle INSTANCE = GWT.create(Bundle.class);

		@Source("HullDemo.css")
		public MyResources css();
	}

	interface MyResources extends CssResource {
		String hulldemo();
	}

	private static class Coords {
		double x, y;

		public Coords(double x, double y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return x + "," + y;
		}

		public static DatumFunction<Double> xAccessor() {
			return new DatumFunction<Double>() {
				@Override
				public Double apply(Element context, Value d, int index) {
					return d.<Coords> as().x;
				}
			};
		}

		public static DatumFunction<Double> yAccessor() {
			return new DatumFunction<Double>() {
				@Override
				public Double apply(Element context, Value d, int index) {
					return d.<Coords> as().y;
				}
			};
		}

	}

	private Selection svg;
	private Selection hull;
	private Selection circle;
	private Array<Coords> vertices;
	private Hull hullModel;

	/**
	 * 
	 */
	public HullDemo() {
		super();

		Bundle.INSTANCE.css().ensureInjected();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.gwtd3.demo.client.D3Demo#start()
	 */
	@Override
	public void start() {
		int width = 960, height = 500;

		final Random randomX = Random.normal(width / 2d, 60d), randomY = Random
				.normal(height / 2d, 60d);

		vertices = Arrays.range(100).map(new ForEachCallback<Coords>() {
			@Override
			public Coords forEach(Object thisArg, Value element, int index,
					Array<?> array) {
				return new Coords(randomX.generate(), randomY.generate());
			};
		});

		svg = D3.select(this).append("svg").attr("width", width)
				.attr("height", height)
				.on(BrowserEvents.MOUSEMOVE, new DatumFunction<Void>() {
					@Override
					public Void apply(Element context, Value d, int index) {
						vertices.set(
								0,
								new Coords(D3.mouseX(context), D3
										.mouseY(context)));
						redraw();
						return null;
					}
				}).on(BrowserEvents.CLICK, new DatumFunction<Void>() {
					@Override
					public Void apply(Element context, Value d, int index) {
						vertices.push(new Coords(D3.mouseX(context), D3
								.mouseY(context)));
						redraw();
						return null;
					}
				});

		svg.append("rect").attr("width", width).attr("height", height)
				.attr("class", Bundle.INSTANCE.css().hulldemo());

		hull = svg.append("path").attr("class",
				Bundle.INSTANCE.css().hulldemo());

		circle = svg.selectAll("circle");

		hullModel = D3.geom().hull().x(Coords.xAccessor())
				.y(Coords.yAccessor());

		redraw();
	}

	private void redraw() {
		try {
			hull.datum(hullModel.apply(vertices)).attr("d",
					new DatumFunction<String>() {
						@Override
						public String apply(Element context, Value d, int index) {
							return "M" + d.<Array<Coords>> as().join("L") + "Z";
						}
					});
			UpdateSelection circles = circle.data(vertices);
			circles.enter().append("circle").attr("r", 3);
			circle = circles.attr("transform", new DatumFunction<String>() {
				@Override
				public String apply(Element context, Value d, int index) {
					return "translate(" + d.<Coords> as() + ")";
				}
			}).attr("class", Bundle.INSTANCE.css().hulldemo());
			circle = circles;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void stop() {
	}

	public static Factory factory() {
		return new Factory() {
			@Override
			public DemoCase newInstance() {
				return new HullDemo();
			}
		};
	}
}
