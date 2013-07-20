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
