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
import com.github.gwtd3.api.scales.ThresholdScale;
import com.github.gwtd3.demo.client.test.AbstractTestCase;
import com.google.gwt.user.client.ui.ComplexPanel;

public class TestThresholdScale extends AbstractTestCase {

	@Override
	public void doTest(final ComplexPanel sandbox) {
		// default domain and range, and apply functions
		ThresholdScale threshold = D3.scale.threshold();
		Array<?> domain = threshold.domain();
		assertEquals(1, domain.length());
		assertEquals(0.5, domain.getNumber(0));

		Array<?> range = threshold.range();
		assertEquals(2, range.length());
		assertEquals(0d, range.getNumber(0));
		assertEquals(1d, range.getNumber(1));

		assertEquals(0d, threshold.apply(0.49d).asDouble());
		assertEquals(1d, threshold.apply(0.51d).asDouble());

		// change domain and range
		threshold.domain(0.5, 0.8);
		assertEquals(0d, threshold.apply(0.48d).asDouble());
		assertEquals(1d, threshold.apply(0.51d).asDouble());
		// now undefined values are returned for input > 0.8
		assertTrue(threshold.apply(0.81).isUndefined());

		// setting range with 3 values
		threshold.range("a", "b", "c");
		assertEquals("a", threshold.apply(0.48d).asString());
		assertEquals("b", threshold.apply(0.51d).asString());
		assertEquals("c", threshold.apply(0.82d).asString());

		// invert extent
		assertEquals(0.5d, threshold.invertExtent("b").getNumber(0));
		assertEquals(0.8d, threshold.invertExtent("b").getNumber(1));

	}
}
