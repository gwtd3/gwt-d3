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
package com.github.gwtd3.demo.client.democases.svg;

import java.util.Stack;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.github.gwtd3.api.Colors;
import com.github.gwtd3.api.Coords;
import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Transform;
import com.github.gwtd3.api.svg.Symbol;
import com.github.gwtd3.api.svg.Symbol.Type;
import com.github.gwtd3.demo.client.DemoCase;
import com.github.gwtd3.demo.client.Factory;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * Original demo is <a href="http://bl.ocks.org/mbostock/3808218">here</a>
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class SymbolDemo extends FlowPanel implements DemoCase {

	private final Stack<Coords> points = new Stack<Coords>();
	private Selection svg;
	private Symbol symbols;
	protected int width = 450, height = 320;
	private final MyResources css;

	public interface Bundle extends ClientBundle {
		public static final Bundle INSTANCE = GWT.create(Bundle.class);

		@Source("SymbolDemo.css")
		public MyResources css();
	}

	interface MyResources extends CssResource {
		String symboldemo();
	}

	/**
	 * 
	 */
	public SymbolDemo() {
		super();

		css = Bundle.INSTANCE.css();
		css.ensureInjected();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.gwtd3.demo.client.D3Demo#start()
	 */
	@Override
	public void start() {
		symbols = D3.svg().symbol();
		// create the things
		createButton("Add symbol", IconType.PLUS_SIGN, new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addSymbol();
			}
		});
		svg = D3.select(this).append("svg").attr("width", width)
				.attr("height", height).append("g");
	}

	private void createButton(String label, IconType icon,
			ClickHandler clickHandler) {
		Button b = new Button();
		b.setIcon(icon);
		b.setText(label);
		b.addClickHandler(clickHandler);
		this.add(b);

	}

	protected void addSymbol() {
		symbols.type(Type.values()[Random.nextInt(Type.values().length)]);
		symbols.size(Random.nextInt(2500) + 25);

		svg.append("path")
				.classed(css.symboldemo(), true)
				.attr("transform",
						Transform
								.parse("")
								.translate(Random.nextInt(width),
										Random.nextInt(height)).toString())
				.attr("d", symbols.generate(1.0))
				.style("fill",
						Colors.rgb(Random.nextInt(255), Random.nextInt(255),
								Random.nextInt(255)).toHexaString());
	}

	@Override
	public void stop() {
		points.clear();
	}

	public static Factory factory() {
		return new Factory() {
			@Override
			public DemoCase newInstance() {
				return new SymbolDemo();
			}
		};
	}
}
