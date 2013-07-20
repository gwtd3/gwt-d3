package com.github.gwtd3.demo.client.testcases.core;

import com.github.gwtd3.api.core.Random;
import com.github.gwtd3.api.core.Transform;
import com.github.gwtd3.demo.client.test.AbstractTestCase;
import com.google.gwt.user.client.ui.ComplexPanel;

public class TestMath extends AbstractTestCase {

	@Override
	public void doTest(final ComplexPanel sandbox) {
		testRandoms();
		testTransform();
	}

	private void testRandoms() {
		//NB: smoke tests
		Random.normal().generate();
		Random.normal(1000).generate();
		Random.normal(1000,40).generate();

		Random.logNormal().generate();
		Random.logNormal(1000).generate();
		Random.logNormal(1000,40).generate();

		Random.irwinHall(456).generate();

	}

	private void testTransform() {
		// empty
		Transform tr = Transform.parse("");
		assertEquals("translate(0,0)rotate(0)skewX(0)scale(1,1)", tr.toString());

		// rotate
		tr = Transform.parse("rotate(45)");
		assertEquals(45.0, tr.rotate(), 0.0001d);
		tr.rotate(60);
		assertEquals(60.0, tr.rotate(), 0.0001d);
		assertTrue(tr.toString().contains("rotate(60)"));

		// scale
		tr = Transform.parse("scale(2,3)");
		assertEquals(2d, tr.scale().getNumber(0), 0.0001d);
		assertEquals(3d, tr.scale().getNumber(1), 0.0001d);
		tr.scale(5);
		assertEquals(5d, tr.scale().getNumber(0), 0.0001d);
		assertEquals(0d, tr.scale().getNumber(1), 0.0001d);
		assertTrue(tr.toString().contains("scale(5,0)"));

		// translate
		tr = Transform.parse("translate(10,15)");
		assertEquals(10d, tr.translate().getNumber(0), 0.0001d);
		assertEquals(15d, tr.translate().getNumber(1), 0.0001d);
		tr.translate(16,17);
		assertEquals(16d, tr.translate().getNumber(0), 0.0001d);
		assertEquals(17d, tr.translate().getNumber(1), 0.0001d);
		assertTrue(tr.toString().contains("translate(16,17)"));

		// translate
		tr = Transform.parse("skewX(45)");
		assertEquals(45d, tr.skew(), 0.0001d);
		tr.skew(60);
		assertEquals(60d, tr.skew(), 0.0001d);
		assertTrue(tr.toString().contains("skewX(60)"));

		//mixed
		tr = Transform.parse("translate(30,50)rotate(24)skewX(47)scale(12,16)");
		assertEquals(47d, tr.skew(), 0.0001d);
		assertEquals(24d, tr.rotate(), 0.0001d);
		assertEquals(30d, tr.translate().getNumber(0), 0.0001d);
		assertEquals(50d, tr.translate().getNumber(1), 0.0001d);
		assertEquals(12d, tr.scale().getNumber(0), 0.0001d);
		assertEquals(16d, tr.scale().getNumber(1), 0.0001d);

	}
}
