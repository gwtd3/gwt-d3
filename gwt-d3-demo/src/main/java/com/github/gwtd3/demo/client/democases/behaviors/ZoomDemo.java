package com.github.gwtd3.demo.client.democases.behaviors;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.JsArrays;
import com.github.gwtd3.api.behaviour.Zoom;
import com.github.gwtd3.api.behaviour.Zoom.ZoomEventType;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.demo.client.DemoCase;
import com.github.gwtd3.demo.client.Factory;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;

public class ZoomDemo extends FlowPanel implements DemoCase {

	private Selection svg;
	private Selection scaleLabel;
	private Selection translateLabel;
	private Selection g;

	public ZoomDemo() {
		super();
		init();
	}

	private void init() {

		// create zoom behavior
		Zoom zoom = D3.behavior().zoom().on(ZoomEventType.zoom, new OnZoom());

		Selection selection = D3.select(this);

		// create text box
		scaleLabel = selection.append("div").text("scale:");
		translateLabel = selection.append("div").text("translate:");

		int w = 1600;
		int h = 800;

		svg = selection.append("svg:svg").attr("width", w).attr("height", h);

		g = svg.append("svg:g");

		g.selectAll("circle").data(JsArrays.asJsArray(32, 57, 112, 293))
				.enter().append("circle").attr("cy", 90)
				.attr("cx", new DatumFunction<Integer>() {
					@Override
					public Integer apply(final Element context, final Value d,
							final int index) {
						return d.asInt();
					}
				})// String
				.attr("r", new DatumFunction<Double>() {
					@Override
					public Double apply(final Element context, final Value d,
							final int index) {
						return Math.sqrt(d.asDouble());
					}
				});// sqrt

		svg.call(zoom);

	}

	public class OnZoom implements DatumFunction<Void> {

		@Override
		public Void apply(final Element context, final Value d, final int index) {

			// print the current scale and translate
			scaleLabel.text("scale:" + D3.zoomEvent().scale());
			translateLabel.text("translate:" + D3.zoomEvent().translate());

			// apply appropriate translation and scaling when panning and
			// zooming
			g.attr("transform", "translate(" + D3.zoomEvent().translate() + ")"
					+ "scale(" + D3.zoomEvent().scale() + ")");

			return null;

		}

	}

	public void start() {

	}

	public void stop() {

	}

	/**
	 * @return
	 */
	public static Factory factory() {
		return new Factory() {
			@Override
			public ZoomDemo newInstance() {
				return new ZoomDemo();
			}
		};
	}

}