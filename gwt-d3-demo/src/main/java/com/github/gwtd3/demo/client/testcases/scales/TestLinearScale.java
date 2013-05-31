package com.github.gwtd3.demo.client.testcases.scales;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.scales.Domain;
import com.github.gwtd3.api.scales.LinearScale;
import com.github.gwtd3.demo.client.test.AbstractTestCase;

import com.google.gwt.user.client.ui.ComplexPanel;

public class TestLinearScale extends AbstractTestCase {

		@Override
	public void doTest(final ComplexPanel sandbox) {
		LinearScale scale = D3.scale.linear();
		// get default domain
		Domain domain = scale.domain();
		assertEquals(2, domain.length());
		assertEquals(0, domain.get(0).asInt());
		assertEquals(1, domain.get(1).asInt());

		// set the domain, keep the default range [0,1]
		scale.domain(0, 10);
		assertEquals(0, scale.apply(0).asInt());
		assertEquals(1, scale.apply(10).asInt());
		assertEquals(-1, scale.apply(-10).asInt());
		assertEquals(0.5, scale.apply(5).asDouble());

		// set the range
		scale.range(0, 100);
		assertEquals(0, scale.apply(0).asInt());
		assertEquals(100, scale.apply(10).asInt());
		assertEquals(1000, scale.apply(100).asInt());
		assertEquals(-100, scale.apply(-10).asInt());

		// polylinear scale
		// scale.domain(0, 1, 20).range(0, 100, 200);

	}
}
