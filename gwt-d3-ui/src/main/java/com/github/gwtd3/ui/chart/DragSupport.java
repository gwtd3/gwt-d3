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
package com.github.gwtd3.ui.chart;

import com.github.gwtd3.api.Coords;
import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.behaviour.Drag;
import com.github.gwtd3.api.behaviour.Drag.DragEventType;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.scales.ContinuousQuantitativeScale;
import com.github.gwtd3.ui.model.AxisModel;
import com.google.gwt.dom.client.Element;

/**
 * Wrap the complexity of drag events to let the user navigate through the X
 * dimension.
 * <p>
 * Usage:
 * <p>
 * 
 * {@code
 * new DragSupport(model).registerListeners(selection);
 * }
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
class DragSupport {

	private final AxisModel<? extends ContinuousQuantitativeScale<?>> model;
	/**
	 * flag to indicate a drag gesture starting
	 */
	private boolean starting;
	/**
	 * The point when the drag gesture starts, serve as reference for distance
	 * computations.
	 */
	private Coords startPoint;
	/**
	 * Copy of the scale of the X axis when the drag gesture begins
	 */
	private ContinuousQuantitativeScale<?> startXScale;

	/**
	 * The D3 {@link Drag} object.
	 */
	private Drag drag;

	/**
	 * Disable the drag feature.
	 * 
	 */
	private boolean disabled;

	private final class OnDrag implements DatumFunction<Void> {
		@Override
		public Void apply(final Element context, final Value d, final int index) {
			if (disabled) {
				return null;
			}
			// when starting, save the starting Point
			if (starting) {
				starting = false;
				startPoint = D3.eventAsCoords();
			}
			// compute the new X domain according to the X
			// distance
			// of mouse with the starting point
			double xDistance = (D3.eventAsCoords().x()) - startPoint.x();
			Array<?> domain = startXScale.domain();
			double dXDomain = startXScale.invert(xDistance).asDouble() - domain.getNumber(0);
			double newX1 = domain.getNumber(0) - dXDomain;
			double newX2 = domain.getNumber(1) - dXDomain;

			// update the new scale and redraw Xaxis and Series
			model.setVisibleDomain(newX1, newX2);
			return null;
		}
	}

	private final class OnDragEnd implements DatumFunction<Void> {
		@Override
		public Void apply(final Element context, final Value d, final int index) {
			if (disabled) {
				return null;
			}
			starting = false;
			startPoint = null;
			return null;
		}
	}

	private final class OnDragStart implements DatumFunction<Void> {
		@Override
		public Void apply(final Element context, final Value d, final int index) {
			if (disabled) {
				return null;
			}
			starting = true;
			startXScale = model.scale().copy();
			return null;
		}
	}

	public DragSupport(final AxisModel<? extends ContinuousQuantitativeScale<?>> model) {
		this.model = model;
		enable();
	}

	public void disable() {
		disabled = true;
		drag = createDisabledDrag();
	}

	private Drag createDisabledDrag() {
		return D3.behavior().drag();
	}

	public void enable() {
		disabled = false;
		drag = createEnabledDragObject();
	}

	public DragSupport registerListeners(final Selection selection) {
		selection.call(drag);
		return this;
	}

	private Drag createEnabledDragObject() {
		return D3.behavior().drag()
				// on drag start init flags
				.on(DragEventType.dragstart, new OnDragStart())
				.on(DragEventType.dragend, new OnDragEnd())
				.on(DragEventType.drag, new OnDrag());
	}
}