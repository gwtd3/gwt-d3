/**
 * 
 */
package com.github.gwtd3.demo.client.democases;

import com.github.gwtd3.api.JsArrays;
import com.github.gwtd3.api.core.Datum;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.demo.client.DemoCase;
import com.github.gwtd3.demo.client.Factory;
import com.github.gwtd3.ui.svg.SVGDocumentContainer;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class StupidExample2 implements DemoCase {

	private final SVGDocumentContainer widget;

	/**
	 * 
	 */
	public StupidExample2() {
		widget = new SVGDocumentContainer();
		widget.setSize("900", "400");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.user.client.ui.IsWidget#asWidget()
	 */
	@Override
	public Widget asWidget() {
		// TODO Auto-generated method stub
		return widget;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.gwtd3.demo.client.DemoCase#start()
	 */
	@Override
	public void start() {
		Selection svg = widget.select();

		svg.selectAll("circle").data(JsArrays.asJsArray(32, 57, 112, 293))
				.enter()
				.append("circle")
				.attr("cy", 90)
				.attr("cx", new DatumFunction<Integer>() {
					@Override
					public Integer apply(final Element context, final Datum d, final int index) {
						return d.asInt();
					}
				})// String
				.attr("r", new DatumFunction<Double>() {
					@Override
					public Double apply(final Element context, final Datum d, final int index) {
						return Math.sqrt(d.asDouble());
					}
				});// sqrt

		svg.selectAll("circle").data(JsArrays.asJsArray(12, 32, 44))
				.enter().append("circle")
				.attr("cy", 90)
				.attr("cx", new DatumFunction<Integer>() {
					@Override
					public Integer apply(final Element context, final Datum d, final int index) {
						return d.asInt();
					}
				})// String
				.attr("r", new DatumFunction<Double>() {
					@Override
					public Double apply(final Element context, final Datum d, final int index) {
						return Math.sqrt(d.asDouble());
					}
				});// sqrt
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.gwtd3.demo.client.DemoCase#stop()
	 */
	@Override
	public void stop() {
	}

	public static Factory factory() {
		return new Factory() {
			@Override
			public DemoCase newInstance() {
				return new StupidExample2();
			}
		};
	}

}
