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

package com.github.gwtd3.demo.client.democases.behaviors;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.behaviour.Zoom;
import com.github.gwtd3.api.behaviour.Zoom.ZoomEventType;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.scales.LinearScale;
import com.github.gwtd3.api.svg.Axis;
import com.github.gwtd3.api.svg.Axis.Orientation;
import com.github.gwtd3.demo.client.DemoCase;
import com.github.gwtd3.demo.client.Factory;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * This demo is a replica of Mike Bostock's <a
 * href="http://bl.ocks.org/mbostock/3892919">Pan-Zoom demo</a>
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a> <br />
 *         <a href="https://github.com/augbog">Augustus Yuan</a>
 * 
 * */
public class ZoomDemo extends FlowPanel implements DemoCase {

	private Selection svg;
	private Selection scaleLabel;
	private Selection translateLabel;
	private Selection g;
	private Axis xAxis;
	private Axis yAxis;

	// set margins
	final Margin margin = new Margin(20, 20, 30, 40);
	final int width = 960 - margin.left - margin.right;
	final int height = 500 - margin.top - margin.bottom;

	public ZoomDemo() {
		super();
		init();
	}

	private void init() {

		LinearScale x = D3.scale.linear().domain(-width / 2, width / 2)
				.range(0.0, width);

		LinearScale y = D3.scale.linear().domain(-height / 2, height / 2)
				.range(height, 0.0);

		// set the x axis
		xAxis = D3.svg().axis().scale(x).orient(Orientation.BOTTOM)
				.tickSize(-height);

		// set the y axis
		yAxis = D3.svg().axis().scale(y).orient(Orientation.LEFT).ticks(5)
				.tickSize(-width);

		// create zoom behavior
		Zoom zoom = D3.behavior().zoom().x(x).y(y)
				.on(ZoomEventType.ZOOM, new OnZoom());

		Selection selection = D3.select(this);

		// create text box
		scaleLabel = selection.append("div").text("scale:");
		translateLabel = selection.append("div").text("translate:");

		svg = selection.append("svg:svg")
				.attr("width", width + margin.left + margin.right)
				.attr("height", height + margin.top + margin.bottom)
				.attr("class", "zoom");

		// calling zoom on group element
		g = svg.append("g").attr("class", "zoom").call(zoom);

		// append rectangle with class zoom
		// see D3Demo.css to see styling applied
		g.append("rect").attr("width", width).attr("height", height)
				.attr("class", "zoom");

		g.append("g").attr("class", "zoom x axis")
				.attr("transform", "translate(0," + height + ")").call(xAxis);

		g.append("g").attr("class", "zoom y axis").call(yAxis);

	}

	public class OnZoom implements DatumFunction<Void> {

		@Override
		public Void apply(final Element context, final Value d, final int index) {

			// print the current scale and translate
			scaleLabel.text("scale:" + D3.zoomEvent().scale());
			translateLabel.text("translate:" + D3.zoomEvent().translate());

			// apply zoom and panning on x and y axes
			g.select(".zoom.x.axis").call(xAxis);
			g.select(".zoom.y.axis").call(yAxis);

			return null;

		}

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.gwtd3.demo.client.DemoCase#start()
	 */
	@Override
	public void start() {

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.github.gwtd3.demo.client.DemoCase#stop()
	 */
	@Override
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

}