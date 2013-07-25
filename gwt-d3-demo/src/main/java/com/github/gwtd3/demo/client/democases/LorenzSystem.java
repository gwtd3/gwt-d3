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
package com.github.gwtd3.demo.client.democases;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.functions.TimerFunction;
import com.github.gwtd3.api.scales.LinearScale;
import com.github.gwtd3.demo.client.DemoCase;
import com.github.gwtd3.demo.client.Factory;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class LorenzSystem extends FlowPanel implements DemoCase {

	private boolean stopped = false;
	private final int n = 30;
	private final double width = 960, height = 500;
	private final double δτ = 0.003, ρ = 28, σ = 10, β = 8 / 3;
	private double x = .5;
	private double y = .5;
	private double z = 10;
	private Timer timer;
	private Context2d context;
	private LinearScale color;
	private TimerFunction timerFunction;

	public LorenzSystem() {
		super();

		Selection canvas = D3.select(this).append("canvas").attr("width", width).attr("height", height);
		color = D3.scale.linear().domain(0, 20, 30, 50).range("yellow", "orange", "brown", "purple");
		context = canvas.node().<CanvasElement> cast().getContext2d();
		context.setLineWidth(.2);
		context.setFillStyle("rgba(0,0,0,.03)");

		timerFunction = new TimerFunction() {
			@Override
			public boolean execute() {
				context.save();
				context.setGlobalCompositeOperation("lighter");
				context.translate(width / 2, height / 2);
				context.scale(12, 14);
				context.rotate(30);
				for (int i = 0; i < n; ++i) {
					context.setStrokeStyle(color.apply(z).asString());
					context.beginPath();
					context.moveTo(x, y);
					x += δτ * σ * (y - x);
					y += δτ * ((x * (ρ - z)) - y);
					z += δτ * ((x * y) - (β * z));
					context.lineTo(x, y);
					context.stroke();
				}
				context.restore();
				return stopped;
			}
		};
		timer = new Timer() {
			@Override
			public void run() {
				context.fillRect(0, 0, width, height);
			}
		};

	}

	@Override
	public void start() {
		stopped = false;
		D3.timer(timerFunction);
		timer.scheduleRepeating(100);
	}

	@Override
	public void stop() {
		stopped = true;
		timer.cancel();
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	public static Factory factory() {
		return new Factory() {
			@Override
			public DemoCase newInstance() {
				return new LorenzSystem();
			}
		};
	}

}
