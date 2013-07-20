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
package com.github.gwtd3.demo.client.testcases.transition;

import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Transition;
import com.github.gwtd3.api.core.Transition.EventType;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.ease.Easing;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.interpolators.CallableInterpolator;
import com.github.gwtd3.api.interpolators.Interpolator;
import com.github.gwtd3.api.tweens.TweenFunction;
import com.github.gwtd3.demo.client.testcases.selection.AbstractSelectionTest;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Label;

public class TestTransition extends AbstractSelectionTest {

	private final DatumFunction<String> callback = new DatumFunction<String>() {
		@Override
		public String apply(final Element context, final Value d, final int index) {
			return "16px";
		}
	};

	@Override
	public void doTest(final ComplexPanel sandbox) {
		// NOTE: all this is only smoke tests

		Selection selection = givenASimpleSelection(new Label("blah"));
		//
		Transition transition = selection.transition().duration(100);
		transition.attr("foo", callback);
		transition.attr("bar", 16);
		transition.attr("bar", "32px");
		transition.attrTween("bar", new TweenFunction<String>() {
			@Override
			public Interpolator<String> apply(final Element context, final Value datum, final int index, final Value value) {
				return new CallableInterpolator<String>() {
					@Override
					public String interpolate(final double t) {
						return (int) (t * 10) + "px";
					}
				};
			}
		});
		transition
		.delay(1000)
		.delay(new DatumFunction<Integer>() {
			@Override
			public Integer apply(final Element context, final Value d, final int index) {
				return 100;
			}
		})
		.duration(new DatumFunction<Integer>() {
			@Override
			public Integer apply(final Element context, final Value d, final int index) {
				return 123;
			}
		})
		.each(EventType.START, new DatumFunction<Void>() {
			@Override
			public Void apply(final Element context, final Value d, final int index) {
				return null;
			}
		})
		.each(EventType.END, new DatumFunction<Void>() {
			@Override
			public Void apply(final Element context, final Value d, final int index) {
				return null;
			}
		})
		.ease(Easing.back(12))
		.style("font-size", new DatumFunction<String>() {
			@Override
			public String apply(final Element context, final Value d, final int index) {
				return "";
			}
		});
	}

}
