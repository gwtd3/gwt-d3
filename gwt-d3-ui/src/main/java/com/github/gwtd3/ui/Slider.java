/**
 * 
 */
package com.github.gwtd3.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.touch.client.Point;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Slider extends FocusWidget implements HasValue<Double>, HasValueChangeHandlers<Double> {

	/**
	 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
	 * 
	 */
	public enum Orientation {
		HORIZONTAL,
		VERTICAL;
	}

	private Double currentValue;

	private final Orientation orientation;

	private final Widget cursor;

	private Double min = 1.0;

	private Double max = 100.0;

	private static class DnDSupport {

		protected boolean started;
		protected Point startPoint;
		protected Point endPoint;

	}

	private final DnDSupport support = new DnDSupport();

	/**
	 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
	 * 
	 */
	public static class DefaultCursor extends FocusWidget {
		/**
		 * 
		 */
		public DefaultCursor() {
			super();
			setElement(Document.get().createDivElement());
			init();
		}

		protected void init() {
			getElement().getStyle().setBorderColor("red");
			getElement().getStyle().setBorderWidth(1, Unit.PX);
			getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
			getElement().getStyle().setBackgroundColor("red");
			getElement().getStyle().setProperty("width", "9px");
			getElement().getStyle().setProperty("height", "9px");
			getElement().getStyle().setProperty("minWidth", "9px");
			getElement().getStyle().setProperty("minHeight", "9px");

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.google.gwt.event.dom.client.HasDragStartHandlers#addDragStartHandler(com.google.gwt.event.dom.client.DragStartHandler)
		 */
		@Override
		public HandlerRegistration addDragStartHandler(final DragStartHandler handler) {
			return addBitlessDomHandler(handler, DragStartEvent.getType());

		}

	}

	/**
	 * A default horizontal slider between 1 and 100 initialized at 50.
	 */
	public Slider() {
		this(createDefaultCursor());
	}

	public Slider(final Widget cursor) {
		this(cursor, Orientation.HORIZONTAL, 1.0, 100.0, 50);
	}

	public Slider(final Orientation orientation) {
		this(createDefaultCursor(), orientation, 1.0, 100.0, 50);
	}

	public Slider(final Orientation orientation, final double min, final double max, final double value) {
		this(createDefaultCursor(), orientation, min, max, value);
	}

	public Slider(final double min, final double max, final double value) {
		this(createDefaultCursor(), Orientation.HORIZONTAL, min, max, value);
	}

	/**
	 * @return
	 */
	private static Widget createDefaultCursor() {
		// return new DefaultCursor();
		return new Label("O");
	}

	public Slider(final Widget cursor, final Orientation orientation) {
		this(cursor, orientation, 1.0, 100.0, 50);
	}

	public Slider(final Widget cursor, final Orientation orientation, final double min, final double max, final double value) {
		super();
		this.setElement(Document.get().createDivElement());
		this.cursor = cursor;
		getElement().appendChild(cursor.getElement());
		setup();
		// default values
		this.min = min;
		this.max = max >= min ? max : min;
		this.orientation = orientation;
		this.setValue(value);
	}

	/**
	 * 
	 */
	private void setup() {
		// styles of the slider outer
		// getElement().getStyle().setHeight(1, Unit.PX);
		getElement().getStyle().setProperty("minWidth", (cursor.getOffsetWidth() * 2) + "px");
		setWidth("100px");
		setHeight("1px");

		getElement().getStyle().setMarginTop(5, Unit.PX);
		getElement().getStyle().setMarginRight(0, Unit.PX);
		getElement().getStyle().setMarginBottom(10, Unit.PX);
		getElement().getStyle().setMarginLeft(10, Unit.PX);

		getElement().getStyle().setBorderWidth(1, Unit.PX);
		getElement().getStyle().setBorderColor("black");
		getElement().getStyle().setBorderStyle(BorderStyle.SOLID);

		getElement().getStyle().setOverflow(Overflow.VISIBLE);

		getElement().getStyle().setPosition(Position.RELATIVE);

		sinkEvents(Event.ONCLICK | Event.ONMOUSEWHEEL);
		addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				updateMouseEvent(event);
			}
		});

		addMouseWheelHandler(new MouseWheelHandler() {
			@Override
			public void onMouseWheel(final MouseWheelEvent event) {
				int deltaY = -event.getDeltaY();
				setValue(currentValue + deltaY, true);
			}
		});
		// configure cursor
		// cursor.getElement().setDraggable(Element.DRAGGABLE_TRUE);
		cursor.getElement().getStyle().setBackgroundColor("white");
		cursor.getElement().getStyle().setPosition(Position.ABSOLUTE);
		cursor.getElement().getStyle().setCursor(Cursor.POINTER);
		cursor.getElement().getStyle().setZIndex(1000);

	}

	/**
	 * @param event
	 */
	protected void updateMouseEvent(final MouseEvent<?> event) {
		int x = event.getRelativeX(getElement());
		int y = event.getRelativeY(getElement());
		Double value = pointToValue(x, y);
		setValue(value, true);
	}

	/**
	 * @param x
	 * @param y
	 * @return
	 */
	protected double pointToValue(final int x, final int y) {
		switch (orientation) {
		case HORIZONTAL:
			return (((double) x / getOffsetWidth()) * (max - min)) + min;
		case VERTICAL:
			return new Double((double) y / getOffsetHeight());
		default:
			throw new IllegalStateException("illegal orientation " + orientation);
		}
	}

	/**
	 * @param currentValue2
	 * @return
	 */
	private Point valueToPoint(final Double v) {
		double range = max - min;
		double percent = (v - min) / range;
		switch (orientation) {
		case HORIZONTAL:
			return new Point(percent * getOffsetWidth(), 0);
		case VERTICAL:
			return new Point(0, percent * getOffsetHeight());
		default:
			throw new IllegalStateException("illegal orientation " + orientation);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.event.logical.shared.HasValueChangeHandlers#addValueChangeHandler(com.google.gwt.event.logical.shared.ValueChangeHandler)
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

	/**
	 * 
	 */
	private void updateCursorPosition() {
		Point point = valueToPoint(currentValue);
		// set the cursor pos at the value point minus half the cursor size
		if (orientation == Orientation.HORIZONTAL) {
			cursor.getElement().getStyle().setLeft(point.getX() - (cursor.getOffsetWidth() / 2), Unit.PX);
			cursor.getElement().getStyle().setTop(-(cursor.getOffsetHeight() / 2), Unit.PX);
		}
		else if (orientation == Orientation.VERTICAL) {
			cursor.getElement().getStyle().setLeft(-(cursor.getOffsetWidth() / 2), Unit.PX);
			cursor.getElement().getStyle().setTop(point.getY() - (cursor.getOffsetHeight() / 2), Unit.PX);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.user.client.ui.HasValue#setValue(java.lang.Object, boolean)
	 */
	@Override
	public void setValue(final Double value, final boolean fireEvents) {
		this.currentValue = value;
		ensureValueInRange();
		updateCursorPosition();
		ValueChangeEvent.fire(Slider.this, currentValue);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.user.client.ui.Widget#onLoad()
	 */
	@Override
	protected void onLoad() {
		super.onLoad();
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				updateCursorPosition();
			}
		});

		// MouseHandler mouseHandler = new MouseHandler();
		// addDomHandler(mouseHandler, MouseDownEvent.getType());
		// addDomHandler(mouseHandler, MouseUpEvent.getType());
		// addDomHandler(mouseHandler, MouseMoveEvent.getType());
		// addDomHandler(mouseHandler, MouseOverEvent.getType());
		// addDomHandler(mouseHandler, MouseOutEvent.getType());

		addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(final MouseDownEvent event) {
				GWT.log("mousedown");
				support.started = true;
				support.startPoint = new Point(event.getRelativeX(getElement()), event.getRelativeY(getElement()));
				support.endPoint = null;
				DOM.setCapture(cursor.getElement());
			}
		});
		addMouseUpHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(final MouseUpEvent event) {
				GWT.log("mouseup");
				support.started = false;
				support.startPoint = null;
				support.endPoint = new Point(event.getRelativeX(getElement()), event.getRelativeY(getElement()));
				DOM.releaseCapture(cursor.getElement());
			}
		});
		// ((HasMouseMoveHandlers) cursor).
		addMouseMoveHandler(new MouseMoveHandler() {
			@Override
			public void onMouseMove(final MouseMoveEvent event) {
				if (support.started) {
					updateMouseEvent(event);
				}
			}
		});

	}

	public void setMax(final double max) {
		this.max = max;
		if (this.max < min) {
			this.max = min;
		}
		if (ensureValueInRange()) {
			updateCursorPosition();
			ValueChangeEvent.fire(Slider.this, currentValue);
		}
	}

	public void setMin(final double min) {
		this.min = min;
		if (this.min > max) {
			this.min = max;
		}
		if (ensureValueInRange()) {
			updateCursorPosition();
			ValueChangeEvent.fire(Slider.this, currentValue);
		}

	}

	/**
	 * Ensure the value is lower than max and greater than min,
	 * and correct it if necessary.
	 * 
	 * @return true if the value was corrected
	 */
	private boolean ensureValueInRange() {
		boolean updated = false;
		if (this.currentValue < min) {
			this.currentValue = min;
			updated = true;
		}
		if (this.currentValue > max) {
			this.currentValue = max;
			updated = true;
		}
		return updated;
	}

	/**
	 * @return the max
	 */
	public double getMax() {
		return max;
	}

	/**
	 * @return the min
	 */
	public double getMin() {
		return min;
	}
}
