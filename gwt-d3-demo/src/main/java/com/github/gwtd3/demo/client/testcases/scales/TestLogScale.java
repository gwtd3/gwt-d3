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
package com.github.gwtd3.demo.client.testcases.scales;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.scales.LogScale;
import com.github.gwtd3.demo.client.test.AbstractTestCase;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;

public class TestLogScale extends AbstractTestCase {

	@Override
	public void doTest(final ComplexPanel sandbox) {
		LogScale scale = D3.scale.log();
		// get default domain
		assertEquals(2, scale.domain().length());
		assertEquals(1, scale.domain().getValue(0).asInt());
		assertEquals(10, scale.domain().getValue(1).asInt());

		// set the domain, keep the default range [0,1]
		scale.domain(10, 100);
		assertEquals(2, scale.domain().length());
		assertEquals(10, scale.domain().getValue(0).asInt());
		assertEquals(100, scale.domain().getValue(1).asInt());

		scale.domain("5", "6");
		assertEquals(2, scale.domain().length());
		assertEquals("5", scale.domain().getValue(0).asString());
		assertEquals("6", scale.domain().getValue(1).asString());

		scale.domain(-1, 0, 1).range(
				Array.fromObjects(new String[] { "red", "white", "blue" }));
		assertEquals(3, scale.domain().length());
		assertEquals(-1, scale.domain().getValue(0).asInt());
		assertEquals(0, scale.domain().getValue(1).asInt());
		assertEquals(1, scale.domain().getValue(2).asInt());

		// default range
		scale = D3.scale.log();
		assertEquals(0.0, scale.range().getNumber(0));
		assertEquals(1.0, scale.range().getNumber(1));

		// set the range
		scale.range(0, 100);
		assertEquals(0.0, scale.range().getNumber(0));
		assertEquals(100.0, scale.range().getNumber(1));

		scale.range(0, 100, 200);
		assertEquals(0.0, scale.range().getNumber(0));
		assertEquals(100.0, scale.range().getNumber(1));
		assertEquals(200.0, scale.range().getNumber(2));

		scale.range("blah", "bloh", "bluh");
		assertEquals("blah", scale.range().getString(0));
		assertEquals("bloh", scale.range().getString(1));
		assertEquals("bluh", scale.range().getString(2));

		// range round
		scale.rangeRound(0, 100);
		assertEquals(0.0, scale.range().getNumber(0));
		assertEquals(100.0, scale.range().getNumber(1));

		scale.rangeRound(0, 100, 200);
		assertEquals(0.0, scale.range().getNumber(0));
		assertEquals(100.0, scale.range().getNumber(1));
		assertEquals(200.0, scale.range().getNumber(2));

		scale.rangeRound("blah", "bloh", "bluh");
		assertEquals("blah", scale.range().getString(0));
		assertEquals("bloh", scale.range().getString(1));
		assertEquals("bluh", scale.range().getString(2));

		// clamp
		assertEquals(false, scale.clamp());
		scale.clamp(true);
		assertEquals(true, scale.clamp());

		// ticks
		scale = D3.scale.log();
		scale.domain(10, 100);
		assertEquals(10, scale.ticks().length());
		assertEquals(10.0, scale.ticks().getNumber(0));
		assertEquals(20.0, scale.ticks().getNumber(1));
		assertEquals(100.0, scale.ticks().getNumber(9));

		scale.domain(10, 1000);
		// assertEquals(17, scale.ticks().length());

		// tickFormat
		scale = D3.scale.log();
		scale.domain(10, 100);
		assertEquals("1e+1", scale.tickFormat().format(10));
		assertEquals("1e+1", scale.tickFormat(2).format(10));
		assertEquals("1e+2", scale.tickFormat(2).format(100));
		assertEquals("$50.00", scale.tickFormat(20, "$,.2f").format(50));
		String format = scale.tickFormat(20, new DatumFunction<String>() {
			@Override
			public String apply(Element context, Value d, int index) {
				System.out.println("FORMATTER " + d.asDouble());
				return "blah";
			}
		}).format(50);
		System.out.println("FORMATTER " + format);

		// nice
		scale = D3.scale.log();
		scale.domain(1.02, 4.98);
		assertEquals(1.02, scale.domain().getNumber(0));
		assertEquals(4.98, scale.domain().getNumber(1));
		scale.nice();
		assertEquals(1.0, scale.domain().getNumber(0));
		assertEquals(10.0, scale.domain().getNumber(1));

		// apply the function
		scale = D3.scale.log();
		scale.domain(1, 10).range(0, 10);
		assertEquals(0, scale.apply(0).asInt());
		assertEquals(10, scale.apply(10).asInt());
		assertEquals(20, scale.apply(100).asInt());
		assertEquals(0, scale.apply(-10).asInt());

		// invert
		assertEquals(1410065408, scale.invert(100).asInt());
		assertEquals(0, scale.invert(-100).asInt());

		// copy
		scale.domain(1, 2);
		LogScale copy = scale.copy();
		copy.domain(2, 3);
		assertEquals(1.0, scale.domain().getNumber(0));
		assertEquals(2.0, scale.domain().getNumber(1));

		// base
		assertEquals(10, scale.base());
		scale.base(5);
		assertEquals(5, scale.base());

	}
}
