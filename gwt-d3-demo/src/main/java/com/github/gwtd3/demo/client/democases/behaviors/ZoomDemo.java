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
import com.google.gwt.core.client.JsArrayNumber;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;

public class ZoomDemo extends FlowPanel implements DemoCase {

	private Selection svg;

	public ZoomDemo() {
		super();
		init();
	}

	private void init() {
		// create zoom behavior
		Zoom zoom = D3.behavior().zoom().on(ZoomEventType.zoom, new OnZoom());

		Selection selection = D3.select(this);

		int w = 1600;
		int h = 800;
		svg = selection.append("svg:svg").attr("width", w).attr("height", h)
				.append("svg:g").attr("fill", "red");
		// .data(Coords.create((double) w / 2, (double) h / 2));

		svg.selectAll("circle").data(JsArrays.asJsArray(32, 57, 112, 293))
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

			System.out.println("Scale: " + D3.event().getScale());

			// Currently this selects the div outside and makes it pan smoothly
			// but in the wrong place...
			JsArrayNumber translate = D3.mouse(getElement());

			// I originally tried this but it glitches a lot
			// JsArrayNumber translate = D3.mouse(context);

			System.out.println("x: " + translate.get(0) + ", y: "
					+ translate.get(1));
			System.out.println("Context is: " + context);

			// width of all circles is 185
			// cy = 90

			double[] newTranslate = {
					(translate.get(0)) * D3.event().getScale(),
					(translate.get(1)) * D3.event().getScale() };

			// I apply the zoom to the <g> element but it only allows me to zoom
			// when hovering over the circles as opposed to the whole group...

			svg.attr("transform",
					"translate(" + JsArrays.asJsArray(newTranslate) + ")"
							+ " scale(" + D3.event().getScale() + ")");

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