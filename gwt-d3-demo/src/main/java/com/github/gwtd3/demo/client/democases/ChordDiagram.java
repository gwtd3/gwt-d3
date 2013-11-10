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
package com.github.gwtd3.demo.client.democases;

import com.github.gwtd3.api.Arrays;
import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.arrays.ForEachCallback;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.layout.Chord;
import com.github.gwtd3.api.layout.Chord.ChordItem;
import com.github.gwtd3.api.layout.Chord.Group;
import com.github.gwtd3.api.scales.OrdinalScale;
import com.github.gwtd3.demo.client.DemoCase;
import com.github.gwtd3.demo.client.Factory;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayNumber;
import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.FlowPanel;

public class ChordDiagram extends FlowPanel implements DemoCase {

	public interface Bundle extends ClientBundle {
		public static final Bundle INSTANCE = GWT.create(Bundle.class);

		@Source("ChordDiagramStyles.css")
		public MyResources css();
	}

	interface MyResources extends CssResource {
		String chord();
	}

	public ChordDiagram() {
		super();
		Bundle.INSTANCE.css().ensureInjected();
	}

	@Override
	public void start() {

		final MyResources css = Bundle.INSTANCE.css();

		// From http://mkweb.bcgsc.ca/circos/guide/tables/
		JsArray<JsArrayNumber> matrix = matrix();

		Chord chord = D3.layout().chord().padding(.05)
				.sortSubgroups(Arrays.descending()).matrix(matrix);

		int width = 960;
		int height = 500;
		double innerRadius = Math.min(width, height) * .41;
		final double outerRadius = innerRadius * 1.1;

		final OrdinalScale fill = D3.scale
				.ordinal()
				.domain(Arrays.range(4))
				.range(Array.fromObjects("#000000", "#FFDD89", "#957244",
						"#F26223"));

		final Selection svg = D3
				.select(this)
				.append("svg")
				.attr("width", width)
				.attr("height", height)
				.append("g")
				.attr("transform",
						"translate(" + (width / 2) + "," + (height / 2) + ")");

		DatumFunction<String> indexFunction = new DatumFunction<String>() {
			@Override
			public String apply(final Element context, final Value d,
					final int index) {
				try {
					int i = d.<Group> as().index();
					Value apply = fill.apply(i);
					String string = apply.asString();
					return string;
				} catch (Exception e) {
					e.printStackTrace();
					return "blah2";
				}
			}
		};

		svg.append("g")
		.selectAll("path")
		.data(chord.groups())
		.enter()
		.append("path")
		.style("fill", indexFunction)
		.style("stroke", indexFunction)
		.attr("d",
				D3.svg().arc().innerRadius(innerRadius)
				.outerRadius(outerRadius))
				.on("mouseover", fade(css, svg, .1))
				.on("mouseout", fade(css, svg, 1));

		// Returns an array of tick angles and labels, given a group.
		DatumFunction<Array<GroupTick>> groupTicks = new DatumFunction<Array<GroupTick>>() {
			@Override
			public Array<GroupTick> apply(final Element context, final Value d,
					final int index) {
				final Group g = d.<Group> as();
				final double k = (g.endAngle() - g.startAngle()) / g.value();
				return Arrays.range(0, g.value(), 1000).<Array<Double>> cast()
						.map(new ForEachCallback<GroupTick>() {
							@Override
							public GroupTick forEach(final Object thisArg,
									final Value v, final int index,
									final Array<?> array) {
								double angle = (v.asDouble() * k)
										+ g.startAngle();
								String label = (index % 5) != 0 ? null : (v
										.asDouble() / 1000) + "k";
								return GroupTick.create(angle, label);
							}
						});
			}
		};

		Selection ticks;
		try {
			ticks = svg.append("g").selectAll("g").data(chord.groups()).enter()
					.append("g").selectAll("g").data(groupTicks).enter()
					.append("g").attr("transform", new DatumFunction<String>() {
						@Override
						public String apply(final Element context,
								final Value d, final int index) {
							GroupTick groupTick = d.<GroupTick> as();
							return "rotate("
							+ (((groupTick.angle() * 180) / Math.PI) - 90)
							+ ")" + "translate(" + outerRadius + ",0)";
						}
					});
			ticks.append("line").attr("x1", 1).attr("y1", 0).attr("x2", 5)
			.attr("y2", 0).style("stroke", "#000");

			ticks.append("text").attr("x", 8).attr("dy", ".35em")
			.attr("transform", new DatumFunction<String>() {
				@Override
				public String apply(final Element context,
						final Value d, final int index) {
					return d.<GroupTick> as().angle() > Math.PI ? "rotate(180)translate(-16)"
							: null;
				}
			}).style("text-anchor", new DatumFunction<String>() {
				@Override
				public String apply(final Element context,
						final Value d, final int index) {
					return d.<GroupTick> as().angle() > Math.PI ? "end"
							: null;
				}
			}).text(new DatumFunction<String>() {
				@Override
				public String apply(final Element context,
						final Value d, final int index) {
					return d.<GroupTick> as().label();
				}
			});

			svg.append("g").attr("class", css.chord()).selectAll("path")
			.data(chord.chords()).enter().append("path")
			.attr("d", D3.svg().chord().radius(innerRadius))
			.style("fill", new DatumFunction<String>() {
				@Override
				public String apply(final Element context,
						final Value d, final int index) {
					return fill.apply(d.<ChordItem> as().target().index())
							.asString();
				}
			}).style("opacity", 1);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static final native JsArray<JsArrayNumber> matrix() /*-{
		return [ [ 11975, 5871, 8916, 2868 ], [ 1951, 10048, 2060, 6171 ],
				[ 8010, 16145, 8090, 8045 ], [ 1013, 990, 940, 6907 ] ];
	}-*/;

	private static final native String call(JavaScriptObject f) /*-{
		f();
	}-*/;

	private DatumFunction<Void> fade(final MyResources css,
			final Selection svg, final double opacity) {
		return new DatumFunction<Void>() {
			@Override
			public Void apply(final Element context, final Value d, final int i) {
				svg.selectAll("." + css.chord() + " path")
				.<Array<ChordItem>> cast()
				.filter(new ForEachCallback<Boolean>() {
					@Override
					public Boolean forEach(final Object thisArg,
							final Value v, final int index,
							final Array<?> array) {
						return (v.as(ChordItem.class).source().index() != i)
								&& (v.as(ChordItem.class).target().index() != i);
					}
				}).<Selection> cast().transition()
				.style("opacity", opacity);
				return null;
			}
		};
	}

	@Override
	public void stop() {
	}

	private static class GroupTick extends JavaScriptObject {
		protected GroupTick() {
			super();
		}

		public static final native GroupTick create(double angle, String label) /*-{
			return {
				angle : angle,
				label : label
			};
		}-*/;

		public final native double angle() /*-{
			return this.angle;
		}-*/;

		public final native String label() /*-{
			return this.label;
		}-*/;

	}

	public static Factory factory() {
		return new Factory() {
			@Override
			public DemoCase newInstance() {
				return new ChordDiagram();
			}
		};
	}
}
