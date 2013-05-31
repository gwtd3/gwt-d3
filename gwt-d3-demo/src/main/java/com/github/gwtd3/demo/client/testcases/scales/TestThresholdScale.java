package com.github.gwtd3.demo.client.testcases.scales;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.scales.Domain;
import com.github.gwtd3.demo.client.test.AbstractTestCase;

import com.google.gwt.user.client.ui.ComplexPanel;

public class TestThresholdScale extends AbstractTestCase {

	@Override
	public void doTest(final ComplexPanel sandbox) {
		Domain domain = D3.scale.threshold().domain();
		assertEquals(1, domain.length());
		assertEquals(0.5, domain.get(0).asDouble());
	}
}
