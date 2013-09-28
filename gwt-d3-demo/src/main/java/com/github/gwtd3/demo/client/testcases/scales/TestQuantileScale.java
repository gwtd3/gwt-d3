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
import com.github.gwtd3.api.scales.QuantileScale;
import com.github.gwtd3.demo.client.test.AbstractTestCase;
import com.google.gwt.user.client.ui.ComplexPanel;

public class TestQuantileScale extends AbstractTestCase {

	@Override
	public void doTest(final ComplexPanel sandbox) {
		QuantileScale quantile = D3.scale.quantile();
		// domain and range are null
		assertEquals(0, quantile.domain().length());
		assertEquals(0, quantile.range().length());

		// domain
		quantile.domain(1, 1, 1, 1, 2, 5, 6, 100);
		quantile.range("one", "two", "three", "last one");
		Array<Double> quantiles = quantile.quantiles();

		assertEquals(1.0, quantiles.getNumber(0));
		assertEquals(1.5, quantiles.getNumber(1));
		assertEquals(5.25, quantiles.getNumber(2));

		assertEquals("one", quantile.apply(0.5).asString());
		assertEquals("two", quantile.apply(1.0).asString());
		assertEquals("three", quantile.apply(3.0).asString());
		assertEquals("three", quantile.apply(5.01).asString());
		assertEquals("last one", quantile.apply(6.0).asString());
		assertEquals("last one", quantile.apply(60.0).asString());

		//invertextent
		assertEquals(1.5, quantile.invertExtent("three").getNumber(0));
		assertEquals(5.25, quantile.invertExtent("three").getNumber(1));

		//copy
		QuantileScale copy = quantile.copy();
		copy.domain(1, 2, 3);
		assertEquals(1.0, quantiles.getNumber(0));
		assertEquals(1.5, quantiles.getNumber(1));
		assertEquals(5.25, quantiles.getNumber(2));

	}
}
