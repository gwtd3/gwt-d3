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
import com.github.gwtd3.api.scales.IdentityScale;
import com.github.gwtd3.demo.client.test.AbstractTestCase;
import com.google.gwt.user.client.ui.ComplexPanel;

public class TestIdentityScale extends AbstractTestCase {

	@Override
	public void doTest(final ComplexPanel sandbox) {
		IdentityScale scale = D3.scale.identity();
		// get default domain
		assertEquals(2, scale.domain().length());
		assertEquals(0, scale.domain().getValue(0).asInt());
		assertEquals(1, scale.domain().getValue(1).asInt());

		// set the domain, keep the default range [0,1]
		scale.domain(0, 10);
		assertEquals(2, scale.domain().length());
		assertEquals(0, scale.domain().getValue(0).asInt());
		assertEquals(10, scale.domain().getValue(1).asInt());

		scale.domain("5", "6");
		assertEquals(2, scale.domain().length());
		assertEquals("5", scale.domain().getValue(0).asString());
		assertEquals("6", scale.domain().getValue(1).asString());

		scale.domain(-1, 0, 1).range(
				Array.fromObjects(new String[] { "red", "white", "blue" }));
		assertEquals(3, scale.domain().length());
		assertEquals(0, scale.domain().getValue(0).asInt());
		assertEquals(0, scale.domain().getValue(1).asInt());
		assertEquals(0, scale.domain().getValue(2).asInt());

		// default range
		scale = D3.scale.identity();
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

		// ticks
		scale.domain(10, 20);
		assertEquals(3, scale.ticks(2).length());
		assertEquals(10.0, scale.ticks(2).getNumber(0));
		assertEquals(15.0, scale.ticks(2).getNumber(1));
		assertEquals(20.0, scale.ticks(2).getNumber(2));

		assertEquals(11, scale.ticks(11).length());
		assertEquals(10.0, scale.ticks(11).getNumber(0));
		assertEquals(11.0, scale.ticks(11).getNumber(1));
		assertEquals(15.0, scale.ticks(11).getNumber(5));
		assertEquals(20.0, scale.ticks(11).getNumber(10));

		assertEquals(6, scale.ticks(4).length());
		assertEquals(10.0, scale.ticks(4).getNumber(0));
		assertEquals(12.0, scale.ticks(4).getNumber(1));
		assertEquals(14.0, scale.ticks(4).getNumber(2));
		assertEquals(16.0, scale.ticks(4).getNumber(3));
		assertEquals(18.0, scale.ticks(4).getNumber(4));
		assertEquals(20.0, scale.ticks(4).getNumber(5));

		// tickFormat
		scale.domain(10, 20);
		assertEquals("10", scale.tickFormat(2).format(10));
		assertEquals("20", scale.tickFormat(2).format(20));

		assertEquals("10.00", scale.tickFormat(200).format(10));
		assertEquals("20.00", scale.tickFormat(200).format(20));

		assertEquals("015.00", scale.tickFormat(200, "06f").format(15));

		// apply the function
		scale.domain(0, 1).range(0, 10);
		assertEquals(0, scale.apply(0).asInt());
		assertEquals(15, scale.apply(15).asInt());
		assertEquals(100, scale.apply(100).asInt());
		assertEquals(-10, scale.apply(-10).asInt());

		// invert
		assertEquals(100, scale.invert(100).asInt());
		assertEquals(-100, scale.invert(-100).asInt());

		// copy
		scale.domain(1, 2);
		IdentityScale copy = scale.copy();
		copy.domain(2, 3);
		assertEquals(1.0, scale.domain().getNumber(0));
		assertEquals(2.0, scale.domain().getNumber(1));

	}
}
