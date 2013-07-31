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
import com.github.gwtd3.api.JsArrays;
import com.github.gwtd3.api.scales.PowScale;
import com.github.gwtd3.api.scales.QuantizeScale;
import com.github.gwtd3.demo.client.test.AbstractTestCase;
import com.google.gwt.user.client.ui.ComplexPanel;

public class TestQuantizeScale extends AbstractTestCase {

	@Override
	public void doTest(final ComplexPanel sandbox) {

		QuantizeScale quantize = D3.scale.quantize();

		// default
		assertEquals(0.0, quantize.apply(0.49));
		assertEquals(1.0, quantize.apply(0.51));
		assertEquals(1.0, quantize.apply(2.5));
		assertEquals(0.0, quantize.apply(-100));

		// range
		quantize.range(0.0, 1.0, 100.0);
		assertEquals(0.0, quantize.apply(-10.0));
		assertEquals(0.0, quantize.apply(0.0));
		assertEquals(1.0, quantize.apply(0.26));
		assertEquals(1.0, quantize.apply(0.5));
		assertEquals(100.0, quantize.apply(1.0));
		assertEquals(100.0, quantize.apply(261));

		// domain
		quantize.domain(0.0, 1.0, 50.0);
		//it takes only the first and last
		assertEquals(0.0, quantize.domain().getNumber(0));
		assertEquals(50.0, quantize.domain().getNumber(1));

		assertEquals(0.0, quantize.apply(-10.0));
		assertEquals(0.0, quantize.apply(0.0));
		assertEquals(1.0, quantize.apply(1.0));
		assertEquals(1.0, quantize.apply(10.0));
		assertEquals(100.0, quantize.apply(50.0));
		assertEquals(100.0, quantize.apply(261));
		
		
		// invert extent
		assertEquals(0.0, quantize.invertExtent(0.0).getNumber(0));
		assertEquals(1.0, quantize.invertExtent(0.0).getNumber(1));
		
		// getters
		assertEquals(0.0, quantize.range().getNumber(0));
		assertEquals(1.0, quantize.range().getNumber(1));
		assertEquals(100.0, quantize.range().getNumber(2));
		
		//copy
		quantize = quantize.copy(); 
		assertEquals(0.0, quantize.range().getNumber(0));
		assertEquals(1.0, quantize.range().getNumber(1));
		assertEquals(100.0, quantize.range().getNumber(2));
		
		
	}
}