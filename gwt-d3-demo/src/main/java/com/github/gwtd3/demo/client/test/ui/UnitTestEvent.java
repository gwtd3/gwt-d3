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
