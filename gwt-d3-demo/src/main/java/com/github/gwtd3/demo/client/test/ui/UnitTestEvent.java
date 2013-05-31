package com.github.gwtd3.demo.client.test.ui;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class UnitTestEvent extends GwtEvent<UnitTestEvent.UnitTestHandler> {

	public static Type<UnitTestHandler> TYPE = new Type<UnitTestHandler>();

	public interface UnitTestHandler extends EventHandler {
		void onUnitTest(UnitTestEvent event);
	}

	public interface UnitTestHasHandlers extends HasHandlers {
		HandlerRegistration addUnitTestHandler(UnitTestHandler handler);
	}

	private final TestPhase phase;
	private TestResult result;

	public UnitTestEvent(final TestPhase phase) {
		super();
		this.phase = phase;
	}

	public UnitTestEvent(final TestPhase phase, final TestResult result) {
		super();
		this.phase = phase;
		this.result = result;
	}

	@Override
	protected void dispatch(final UnitTestHandler handler) {
		handler.onUnitTest(this);
	}

	@Override
	public Type<UnitTestHandler> getAssociatedType() {
		return UnitTestEvent.TYPE;
	}

	public static Type<UnitTestHandler> getType() {
		return UnitTestEvent.TYPE;
	}

}
