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
package com.github.gwtd3.demo.client.testcases.behaviors;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.behaviour.Zoom;
import com.github.gwtd3.api.behaviour.Zoom.ZoomEventType;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.demo.client.test.AbstractTestCase;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;

/**
 * Test Zoom API
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class TestZoom extends AbstractTestCase {

	private static final double DELTA = 0.001d;

	@Override
	public void doTest(final ComplexPanel sandbox) {
		testCreate();
	}

	private void testCreate() {
		Zoom zoom = D3.behavior().zoom();

		assertNull(zoom.center());
		zoom.center(5, 6);
		assertNotNull(zoom.center());

		assertEquals(960.0, zoom.size().getNumber(0), DELTA);
		assertEquals(500.0, zoom.size().getNumber(1), DELTA);
		zoom.size(400, 300);
		assertEquals(400.0, zoom.size().getNumber(0), DELTA);
		assertEquals(300.0, zoom.size().getNumber(1), DELTA);

		zoom.event(D3.select("body"));
		zoom.event(D3.select("body").transition());

		zoom.on(ZoomEventType.ZOOMSTART, noopListener);
		zoom.on(ZoomEventType.ZOOM, noopListener);
		zoom.on(ZoomEventType.ZOOMEND, noopListener);

		zoom.scale();
		zoom.scale(5.0);

		zoom.scaleExtent();
		zoom.scaleExtent(Array.fromDoubles(5.0, 4.0));
		zoom.translate();
		zoom.translate(Array.fromDoubles(5.0, 6.0));

	}

	private final DatumFunction<Void> noopListener = new DatumFunction<Void>() {
		@Override
		public Void apply(Element context, Value d, int index) {

			return null;
		};
	};

}
