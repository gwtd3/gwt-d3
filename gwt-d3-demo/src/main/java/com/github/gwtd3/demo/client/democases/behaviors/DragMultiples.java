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

import com.github.gwtd3.api.Arrays;
import com.github.gwtd3.api.Coords;
import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.arrays.ForEachCallback;
import com.github.gwtd3.api.behaviour.Drag;
import com.github.gwtd3.api.behaviour.Drag.DragEventType;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.demo.client.DemoCase;
import com.github.gwtd3.demo.client.Factory;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class DragMultiples extends FlowPanel implements DemoCase {

	public DragMultiples() {
		super();
		init();
	}

	public static final int SQUARE_WIDTH = 238, SQUARE_HEIGHT = 123,
			CIRCLE_RADIUS = 20;

	/**
	 * 
	 */
	private void init() {

		Drag drag = D3
				.behavior()
				.drag()
				// the origin will be set with the data of svg
				// on mousedown
				.origin(D3.identity())
				.on(Drag.DragEventType.DRAG, new OnDragMove())
				.on(DragEventType.DRAGSTART, new OnDragStart())
				.on(DragEventType.DRAGEND, new OnDragEnd());
		Selection svg = D3
				.select(this)
				.selectAll("svg")
				// set the data as the center of the squares
				.data(Arrays.range(16).map(new ForEachCallback<Coords>() {
					@Override
					public Coords forEach(final Object thisArg,
							final Value element, final int index,
							final Array<?> array) {
						return Coords.create(SQUARE_WIDTH / 2,
								SQUARE_HEIGHT / 2);
					}
				})).enter().append("svg").attr("width", SQUARE_WIDTH)
				.attr("height", SQUARE_HEIGHT).style("float", "left")
				.style("border", "solid 1px #aaa");

		svg.append("circle").attr("r", CIRCLE_RADIUS)
				.attr("cx", new DatumFunction<Double>() {
					@Override
					public Double apply(final Element context, final Value d,
							final int index) {
						return d.as(Coords.class).x();
					}
				}).attr("cy", new DatumFunction<Double>() {
					@Override
					public Double apply(final Element context, final Value d,
							final int index) {
						return d.as(Coords.class).y();
					}
				}).style("cursor", "pointer")
				// listeners are registered
				.call(drag);

	}

	private class OnDragMove implements DatumFunction<Void> {
		@Override
		public Void apply(final Element context, final Value d, final int index) {
			// change color of the element being dragged
			D3.select(context).attr("fill", "green");
			Coords datum = d.as();
			// compute the new x and y using the mouse position
			// note: the mouse position has been adjusted to the drag 'origin'
			double newX = Math.max(CIRCLE_RADIUS, Math.min(SQUARE_WIDTH
					- CIRCLE_RADIUS, D3.eventAsCoords().x()));
			double newY = Math.max(CIRCLE_RADIUS, Math.min(SQUARE_HEIGHT
					- CIRCLE_RADIUS, D3.eventAsCoords().y()));
			// update the datum itself, to adjust the origin
			datum.x(newX).y(newY);
			// update the position of the circle
			D3.select(context).attr("cx", datum.x()).attr("cy", datum.y());
			return null;
		}
	}

	public class OnDragEnd implements DatumFunction<Void> {

		@Override
		public Void apply(final Element context, final Value d, final int index) {
			// remove fill attributes
			D3.select(context).attr("fill", "");
			return null;
		}

	}

	public class OnDragStart implements DatumFunction<Void> {

		@Override
		public Void apply(final Element context, final Value d, final int index) {
			D3.select(context).attr("fill", "red");
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.gwtd3.demo.client.DemoCase#start()
	 */
	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.gwtd3.demo.client.DemoCase#stop()
	 */
	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	/**
	 * @return
	 */
	public static Factory factory() {
		return new Factory() {
			@Override
			public DragMultiples newInstance() {
				return new DragMultiples();
			}
		};
	}

}
