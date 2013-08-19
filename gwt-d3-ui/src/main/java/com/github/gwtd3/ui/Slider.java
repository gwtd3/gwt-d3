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
package com.github.gwtd3.ui;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.behaviour.Drag;
import com.github.gwtd3.api.behaviour.Drag.DragEventType;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.scales.LinearScale;
import com.github.gwtd3.api.svg.Symbol;
import com.github.gwtd3.api.svg.Symbol.Type;
import com.github.gwtd3.ui.svg.ISVGDocument;
import com.github.gwtd3.ui.svg.SVGDocumentWidget;
import com.github.gwtd3.ui.svg.SVGResources;
import com.github.gwtd3.ui.svg.SVGStyles;
import com.google.common.base.Objects;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.HasValue;

/**
 * A component that allows the user to change a numeric value using the mouse.
 * <p>
 * The slider is composed of a rule (a rigid line) and a cursor (a button-like shape). To change the value, the user can drag the cursor along the rule, use the mouse wheel (TODO),
 * or directly click on the rule. The current value may be shown to the user (TODO) or not.
 * <p>
 * This component can be disabled using the {@link #setEnabled(boolean)} method.
 * <p>
 * This component fires {@link ValueChangeEvent} to external listeners when the value is changed by the user in any mean.
 * <p>
 * Styling may be customized by providing you own instance of {@link Resources} (TODO).
 * <p>
 * 
 * TODO: show a label + allow show/hide label<br>
 * TODO: support for mouse wheel<br>
 * TODO: set some fixed increment (to round value to integers, for instance...) <br>
 * TODO: custom styling<br>
 * 
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Slider extends SVGDocumentWidget implements HasValue<Double>, HasValueChangeHandlers<Double>, HasEnabled,
		ISVGDocument {

	private static final int DEFAULT_WIDTH = 100;
	/**
	 * 
	 */
	private static final int RULE_THICKNESS = 6;

	/**
	 * The click handler for the rule
	 */
	private class ClickOnRuleListener implements DatumFunction<Void> {
		@Override
		public Void apply(final Element context, final Value d, final int index) {
			updateValueFromMouseCoords();
			return null;
		}

	}

	/**
	 * The drag handler for the cursor.
	 * 
	 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
	 * 
	 */
	private class DragListener implements DatumFunction<Void> {
		@Override
		public Void apply(final Element context, final Value d, final int index) {
			updateValueFromMouseCoords();
			return null;
		}

	}

	/**
	 * The orientation of the slider,
	 * which is horizontal or vertical.
	 * 
	 */
	public enum Orientation {
		/**
		 * horizontal slider
		 */
		HORIZONTAL,
		/**
		 * vertical slider
		 */
		VERTICAL;
	}

	/**
	 * the resources of the slider
	 */
	public static interface Resources extends SVGResources {
		/**
		 * @return the stylesheet
		 */
		@Override
		@Source("Slider.css")
		Styles svgStyles();
	}

	/**
	 * Styles for the {@link Slider} component.
	 * 
	 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
	 * 
	 */
	public static interface Styles extends SVGStyles {

		/**
		 * the class name for the slider
		 * 
		 * @return
		 */
		String slider();

		/**
		 * The class name for the rule.
		 * 
		 * @return the class name
		 */
		String rule();

		/**
		 * The class name for the cursor.
		 * 
		 * @return the class name
		 */
		String cursor();

		/**
		 * The class name for the label
		 * 
		 * @return the class name
		 */
		String label();

		/**
		 * class name to apply to each component (cursor, rule, label)
		 * when the widget is disabled.
		 * 
		 * @return the class name
		 */
		String disabled();
	}

	private static final int DEFAULT_SIZE = 100;

	private static final int DEFAULT_CURSOR_SIZE = 8;

	private int size = Slider.DEFAULT_SIZE;

	private int cursorSize = Slider.DEFAULT_CURSOR_SIZE;

	private Double currentValue;

	private final Orientation orientation;

	private Selection cursor;
	private Selection rule;
	private Selection label;

	private boolean disabled;

	private final Styles styles;

	// internal variables
	private Symbol cursorGenerator;

	/**
	 * cursor size has changed
	 */
	private boolean cursorChanged = false;

	/**
	 * the value has changed (or min or max)
	 */
	private boolean valueChanged = false;

	/**
	 * disabled state changed
	 */
	private boolean disabledChanged = false;

	/**
	 * the size of the rule has changed
	 */
	private boolean sizeChanged = false;

	private final LinearScale scale;

	// ======= constructors ================

	/**
	 * A default horizontal slider between 1 and 100 initialized at 50
	 * that use the default {@link Resources}.
	 */
	public Slider() {
		this(Orientation.HORIZONTAL);
	}

	/**
	 * A default horizontal slider between 1 and 100 initialized at 50
	 * that use the given {@link Resources}.
	 * 
	 * @param resources
	 *            the custom resources
	 */
	public Slider(final Resources resources) {
		this(resources, Orientation.HORIZONTAL);
	}

	/**
	 * A default slider with the given orientation
	 * with values ranging between 1 and 100 initialized at 50
	 * that use the given {@link Resources}.
	 * 
	 * @param resources
	 *            the custom resources
	 * @param orientation
	 *            the orientation
	 */
	public Slider(final Resources resources, final Orientation orientation) {
		this(resources, orientation, 1.0, 100.0, 50);
	}

	/**
	 * A default slider with the given orientation
	 * with values ranging between 1 and 100 initialized at 50
	 * that use the default {@link Resources}.
	 * 
	 * @param orientation
	 *            the orientation
	 */
	public Slider(final Orientation orientation) {
		this(orientation, 1.0, 100.0, 50);
	}

	/**
	 * A default horizontal slider with the given range of values
	 * that use the default {@link Resources}.
	 * 
	 * @param min
	 *            the minimum value
	 * @param max
	 *            the maximum value
	 * @param value
	 *            the current value
	 */
	@UiConstructor
	public Slider(final double min, final double max, final double value) {
		this(Orientation.HORIZONTAL, min, max, value);
	}

	/**
	 * A default slider with the given orientation
	 * with the given range of values
	 * that use the default {@link Resources}.
	 * 
	 * @param orientation
	 *            the orientation
	 * @param min
	 *            the minimum value
	 * @param max
	 *            the maximum value
	 * @param value
	 *            the current value
	 */
	public Slider(final Orientation orientation, final double min, final double max, final double value) {
		this((Resources) GWT.create(Resources.class), orientation, min, max, value);
	}

	/**
	 * A slider with the given orientation
	 * with the given range of values
	 * that use the given {@link Resources}.
	 * 
	 * @param resources
	 * @param orientation
	 *            the orientation
	 * @param min
	 *            the minimum value
	 * @param max
	 *            the maximum value
	 * @param value
	 *            the current value
	 */
	public Slider(final Resources resources, final Orientation orientation, final double min, final double max, final double value) {
		super(resources);
		setWidth("" + Slider.DEFAULT_WIDTH);
		setHeight("22");
		this.orientation = orientation;
		this.styles = resources.svgStyles();
		addStyleName(this.styles.slider());
		// default values
		this.scale = D3.scale.linear().domain(min, Math.max(min, max));
		setSize(Slider.DEFAULT_SIZE);
		this.setValue(value);
	}

	// ========== lifecycle methods ==============
	@Override
	protected void onSelectionAttached(final Selection selection) {
		super.onSelectionAttached(selection);

		// set some constraints
		// create the rule + attach the styles
		rule = select().append("rect")
				.attr("x", 0)
				.attr("y", 0)
				.attr("rx", 3)
				.attr("ry", 3)
				.classed(styles.rule(), true)
				.on(BrowserEvents.MOUSEDOWN, new ClickOnRuleListener());

		// .on(BrowserEvents.MOUSEWHEEL, new MouseWheelOnRuleListener());

		// create the symbol generator as a circle
		// TODO: possibly make this customizable
		this.cursorGenerator = D3.svg().symbol().type(Type.CIRCLE).size(cursorSize);
		// create the cursor as a path depending cursor generator
		// this.cursor = select().append("path")
		// .classed(styles.cursor(), true)
		// .attr("transform", computeCursorTranslationValue())
		// // the data passed in no sense: we dont care about the data,
		// // since all accessors are constants
		// .attr("d", cursorGenerator.generate(D3.identity()));
		this.cursor = select().append("circle")
				.classed(styles.cursor(), true);

		// install drag
		Drag drag = D3.behavior().drag()
				// .origin(D3.identity())
				.on(DragEventType.drag, new DragListener());
		// .origin(D3.identity())
		;
		// register listeners
		cursor.call(drag);

		cursorChanged = sizeChanged = disabledChanged = valueChanged = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.gwtd3.ui.D3Widget#redraw()
	 */
	@Override
	public void redraw() {
		super.redraw();

		int width = 0;
		int height = 0;
		int x = 0;
		int y = 0;

		// update the size of the component and layout
		if (cursorChanged || sizeChanged) {
			// compute component size
			int smalldim = (cursorSize * 2) + Slider.RULE_THICKNESS;

			if (isHorizontal()) {
				width = size;
				height = smalldim;
				x = cursorSize;
				y = (smalldim / 2) - (Slider.RULE_THICKNESS / 2);
				this.scale.range(cursorSize, size - cursorSize);
			}
			else {
				width = smalldim;
				height = size;
				x = (smalldim / 2) - (Slider.RULE_THICKNESS / 2);
				y = cursorSize;
				this.scale.range(size - cursorSize, cursorSize);
			}

			setViewBox(0, 0, width, height);
			rule.attr("width", width - (2 * cursorSize))
					.attr("height", height - (2 * cursorSize));
			rule.attr("x", x);
			rule.attr("y", y);

		}

		// update the cursor rendition
		if (cursorChanged) {
			cursor.attr("r", cursorSize);
			// cursor.call(cursorGenerator);
		}

		// update the cursor position
		if (valueChanged) {
			int cx = 0;
			int cy = 0;
			int cursorPosition = scale.apply(currentValue).asInt();
			int smallPosition = (Slider.RULE_THICKNESS / 2) + cursorSize;
			if (isHorizontal()) {
				// modulate x from currentvalue
				cx = cursorPosition;
				cy = smallPosition;
			}
			else {
				// modulate y from currentvalue
				cx = smallPosition;
				cy = cursorPosition;
			}
			cursor.attr("cx", cx);
			cursor.attr("cy", cy);
		}

		if (disabledChanged) {
			cursor.classed(styles.disabled(), !isEnabled());
			rule.classed(styles.disabled(), !isEnabled());
			// label.classed(styles.disabled(), !isEnabled());
		}

		valueChanged = false;
		sizeChanged = false;
		disabledChanged = false;
		cursorChanged = false;
	}

	// =========== internal methods =============

	/**
	 * @return true if this slider is horizontal
	 */
	private boolean isHorizontal() {
		return orientation == Orientation.HORIZONTAL;
	}

	/**
	 * Compute the new current value of the slider
	 * according to the coordionates of the last mouse event
	 */
	protected void updateValueFromMouseCoords() {
		double newValue = 0;
		if (isHorizontal()) {
			double mouseX = D3.mouseX(this.getElement());
			newValue = scale.invert(mouseX).asDouble();
			// newValue = Math.max();
		}
		else {
			double mouseY = D3.mouseY(this.getElement());
			newValue = scale.invert(mouseY).asDouble();
		}

		setValue(newValue, true);
	}

	/**
	 * Ensure the value is lower than max and greater than min,
	 * and correct it if necessary.
	 * 
	 * @return true if the value was corrected
	 */
	private boolean ensureCurrentValueInRange() {
		double oldValue = currentValue;
		this.currentValue = Math.max(currentValue, getMin());
		this.currentValue = Math.min(currentValue, getMax());
		return currentValue.doubleValue() != oldValue;
	}

	// ============ interface methods =========

	/**
	 * Set the cursor size in pixels. The given
	 * 
	 * @param cursorSize
	 *            the cursorSize to set
	 */
	public void setCursorSize(final int cursorSize) {
		this.cursorSize = cursorSize;
		cursorChanged = true;
		scheduleRedraw();
	}

	/**
	 * Set the size of the slider in pixels, which is interpreted differently according to the orientation.
	 * <p>
	 * The size of the slider measures the length in pixels (with a scale of 1,1) of the rule of the slider.
	 * 
	 * @param sizeInPixels
	 */
	public void setSize(final int sizeInPixels) {
		this.size = sizeInPixels;
		sizeChanged = true;
		scheduleRedraw();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.event.logical.shared.HasValueChangeHandlers#addValueChangeHandler(com.google.gwt.event.logical
	 * .shared.ValueChangeHandler)
	 */
	@Override
	public HandlerRegistration addValueChangeHandler(final ValueChangeHandler<Double> handler) {
		return this.addHandler(handler, ValueChangeEvent.getType());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.user.client.ui.HasValue#getValue()
	 */
	@Override
	public Double getValue() {
		return currentValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.user.client.ui.HasValue#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(final Double value) {
		setValue(value, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.user.client.ui.HasValue#setValue(java.lang.Object, boolean)
	 */
	@Override
	public void setValue(final Double value, final boolean fireEvents) {
		if (Objects.equal(value, currentValue)) {
			return;
		}
		this.currentValue = value;
		this.valueChanged = true;
		scheduleRedraw();
		ensureCurrentValueInRange();
		if (fireEvents) {
			scheduleEvent();
		}

	}

	private boolean scheduled = false;

	private void scheduleEvent() {
		if (scheduled) {
			return;
		}
		scheduled = true;
		Scheduler.get().scheduleFinally(new ScheduledCommand() {
			@Override
			public void execute() {
				scheduled = false;
				// ValueChangeEvent.fire(Slider.this, currentValue);
			}
		});
		ValueChangeEvent.fire(Slider.this, currentValue);
	}

	/**
	 * The maximum this slider can set the value.
	 * 
	 * @param max
	 *            the new maximum value
	 */
	public void setMax(final double max) {
		double min = getMin();
		scale.domain(Math.min(min, max), max);
		valueChanged = true;
		scheduleRedraw();
		if (ensureCurrentValueInRange()) {
			scheduleEvent();
		}
	}

	/**
	 * The minimum this slider can set the value.
	 * 
	 * @param min
	 *            the new minimum value
	 */
	public void setMin(final double min) {
		double max = getMax();
		scale.domain(min, Math.max(min, max));
		valueChanged = true;
		if (ensureCurrentValueInRange()) {
			scheduleEvent();
		}
		scheduleRedraw();
	}

	/**
	 * The maximum this slider can set the value.
	 * 
	 * @return the max the maximum
	 */
	public double getMax() {
		return scale.domain().getNumber(1);
	}

	/**
	 * The minimum this slider can set the value.
	 * 
	 * @return the min the minimum
	 */
	public double getMin() {
		return scale.domain().getNumber(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.user.client.ui.HasEnabled#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return !disabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.user.client.ui.HasEnabled#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(final boolean enabled) {
		this.disabled = !enabled;
		this.disabledChanged = true;
		scheduleRedraw();
	}

}
