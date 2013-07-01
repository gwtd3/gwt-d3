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
import com.github.gwtd3.api.scales.LinearScale;
import com.github.gwtd3.demo.client.test.AbstractTestCase;
import com.google.gwt.user.client.ui.ComplexPanel;

public class TestLinearScale extends AbstractTestCase {

	@Override
	public void doTest(final ComplexPanel sandbox) {
		LinearScale scale = D3.scale.linear();
		// get default domain
		Array<?> domain = scale.domain();
		assertEquals(2, domain.length());
		assertEquals(0, domain.getValue(0).asInt());
		assertEquals(1, domain.getValue(1).asInt());

		// set the domain, keep the default range [0,1]
		scale.domain(0, 10);
		assertEquals(0, scale.apply(0).asInt());
		assertEquals(1, scale.apply(10).asInt());
		assertEquals(-1, scale.apply(-10).asInt());
		assertEquals(0.5, scale.apply(5).asDouble());

		// default range
		assertEquals(0.0, scale.range().getNumber(0));
		assertEquals(1.0, scale.range().getNumber(1));

		// set the range
		scale.range(0, 100);
		assertEquals(0.0, scale.range().getNumber(0));
		assertEquals(100.0, scale.range().getNumber(1));

		// apply the function
		assertEquals(0, scale.apply(0).asInt());
		assertEquals(100, scale.apply(10).asInt());
		assertEquals(1000, scale.apply(100).asInt());
		assertEquals(-100, scale.apply(-10).asInt());

		// nice, ticks, etc...

		// TODO polylinear scale
		// scale.domain(0, 1, 20).range(0, 100, 200);

	}
}
