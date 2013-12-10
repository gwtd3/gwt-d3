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
package com.github.gwtd3.demo.client.testcases.svg;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.svg.Chord;
import com.github.gwtd3.demo.client.test.AbstractTestCase;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;

public class TestChord extends AbstractTestCase {

	@Override
	public void doTest(final com.google.gwt.user.client.ui.ComplexPanel sandbox) {
		Chord chord = D3.svg().chord();

		// constants
		chord.startAngle(5);
		chord.endAngle(5);
		chord.radius(6);

		chord.source(new DatumFunction<ChordDef>() {
			@Override
			public ChordDef apply(Element context, Value d, int index) {
				GWT.log("source" + d);
				return new ChordDef(index * 5, index * 5, index * 5);
			}
		});
		chord.target(new DatumFunction<ChordDef>() {
			@Override
			public ChordDef apply(Element context, Value d, int index) {
				GWT.log("target" + d);
				return new ChordDef(index * 5, index * 5, index * 5);
			}
		});
		
		// chord
		chord.startAngle(new DatumFunction<Double>() {
			@Override
			public Double apply(Element context, Value d, int index) {
				GWT.log("start" + d);
				return d.<ChordDef> as().start;
			}
		}).endAngle(new DatumFunction<Double>() {
			@Override
			public Double apply(Element context, Value d, int index) {
				GWT.log("end" + d);
				return d.<ChordDef> as().end;
			}
		}).radius(new DatumFunction<Double>() {
			@Override
			public Double apply(Element context, Value d, int index) {
				GWT.log("rad" + d);
				return d.<ChordDef> as().radius;
			}
		});

		//
		chord.generate(Array.fromDoubles(0, 0, 0, 0, 0, 0, 0, 0));
	}

	public static class ChordDef {
		double start;
		double end;
		double radius;

		public ChordDef(double start, double end, double radius) {
			super();
			this.start = start;
			this.end = end;
			this.radius = radius;
		}

	}
}
