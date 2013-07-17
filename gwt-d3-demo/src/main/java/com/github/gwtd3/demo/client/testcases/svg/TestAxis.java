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
package com.github.gwtd3.demo.client.testcases.svg;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.scales.LinearScale;
import com.github.gwtd3.api.svg.Axis;
import com.github.gwtd3.api.svg.Axis.Orientation;
import com.github.gwtd3.demo.client.test.AbstractTestCase;

import com.google.gwt.core.client.JavaScriptObject;

public class TestAxis extends AbstractTestCase {

	@Override
	public void doTest(final com.google.gwt.user.client.ui.ComplexPanel sandbox) {
		Axis axis = D3.svg().axis();

		// default scale
		LinearScale s = axis.scale();
		assertNotNull(s);
		int asInt = (int) s.domain().getNumber(0);
		assertEquals(0, asInt);
		// default orientation
		assertEquals(Orientation.BOTTOM, axis.orient());
		// set orientation
		axis.orient(Orientation.TOP);
		assertEquals(Orientation.TOP, axis.orient());

		// tick size
		axis.tickSize(6);
		axis.tickSize(6, 0);
		axis.tickSize(6, 3, 0);

		// tick subdivide
		assertEquals(0, axis.tickSubdivide());
		axis.tickSubdivide(9);
		assertEquals(9, axis.tickSubdivide());

		// ticks
		assertEquals(1, axis.ticks().length());
		axis.ticks(10);
		assertEquals(10, axis.ticks().getValue(0).asInt());
		JavaScriptObject tickFunction = JavaScriptObject.createFunction();
		axis.ticks(tickFunction, 15);
		assertEquals(tickFunction, axis.ticks().getValue(0).as());
		assertEquals(15, axis.ticks().getValue(1).asInt());

		// apply
		Selection svg = D3.select(sandbox).append("svg").attr("width", 100)
				.attr("height", 100).append("g")
				.attr("transform", "translate(32,50)");

		Axis axis2 = D3.svg().axis().apply(svg);
	}
}
