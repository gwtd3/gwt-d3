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
/**
 * 
 */
package com.github.gwtd3.demo.client.democases;

import java.util.Arrays;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.UpdateSelection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.functions.KeyFunction;
import com.github.gwtd3.demo.client.DemoCase;
import com.github.gwtd3.demo.client.Factory;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * Original demo is <a href="http://bl.ocks.org/mbostock/3808218">here</a>
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class GeneralUpdatePattern2 extends FlowPanel implements DemoCase {

	private Timer timer;
	private Selection svg;

	public interface Bundle extends ClientBundle {
		public static final Bundle INSTANCE = GWT.create(Bundle.class);

		@Source("GeneralUpdatePattern2Styles.css")
		public MyResources css();
	}

	interface MyResources extends CssResource {
		String update();

		String enter();
	}

	/**
	 * 
	 */
	public GeneralUpdatePattern2() {
		super();

		Bundle.INSTANCE.css().ensureInjected();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.gwtd3.demo.client.D3Demo#start()
	 */
	@Override
	public void start() {
		String source = "abcdefghijklmnopqrstuvwxyz";
		final char[] alphabet = new char[source.length()];
		source.getChars(0, source.length(), alphabet, 0);

		int width = 960, height = 500;

		svg = D3.select(this).append("svg").attr("width", width)
				.attr("height", height).append("g")
				.attr("transform", "translate(32," + (height / 2) + ")");

		// The initial display.
		update(alphabet);

		timer = new Timer() {
			@Override
			public void run() {
				// Grab a random sample of letters from the alphabet, in
				// alphabetical order.
				D3.shuffle(alphabet);
				char[] range = new char[(int) Math.floor(Math.random() * 26)];
				System.arraycopy(alphabet, 0, range, 0, range.length);
				Arrays.sort(range);
				update(range);
			}
		};
		timer.scheduleRepeating(1500);
	}

	public void update(final char[] data) {

		// DATA JOIN
		// Join new data with old elements, if any.
		UpdateSelection selection = svg.selectAll("text").data(
				Array.fromChars(data), new KeyFunction<Integer>() {
					@Override
					public Integer map(final Element context,
							final Array<?> newDataArray, final Value datum,
							final int index) {
						return datum.asInt();
					}
				});

		// UPDATE
		// Update old elements as needed.
		selection.attr("class", Bundle.INSTANCE.css().update());

		// ENTER
		// Create new elements as needed.
		selection.enter().append("text")
				.attr("class", Bundle.INSTANCE.css().enter())
				.attr("dy", ".35em").text(new DatumFunction<String>() {
					@Override
					public String apply(final Element context,
							final Value datum, final int index) {
						return "" + datum.asChar();
					}
				});

		// ENTER + UPDATE
		// Appending to the enter selection expands the update selection to
		// include
		// entering elements; so, operations on the update selection after
		// appending to
		// the enter selection will apply to both entering and updating nodes.
		selection.attr("x", new DatumFunction<Integer>() {
			@Override
			public Integer apply(final Element context, final Value datum,
					final int index) {
				return index * 32;
			}
		});

		// EXIT
		// Remove old elements as needed.
		selection.exit().remove();
	}

	@Override
	public void stop() {
		timer.cancel();
		timer = null;
	}

	public static Factory factory() {
		return new Factory() {
			@Override
			public DemoCase newInstance() {
				return new GeneralUpdatePattern2();
			}
		};
	}
}
