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