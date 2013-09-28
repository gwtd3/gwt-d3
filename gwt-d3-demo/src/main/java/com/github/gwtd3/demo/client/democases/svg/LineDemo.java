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

import java.util.ArrayList;
import java.util.Stack;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.CheckBox;
import com.github.gwtbootstrap.client.ui.DoubleBox;
import com.github.gwtbootstrap.client.ui.RadioButton;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.UpdateSelection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.svg.Line;
import com.github.gwtd3.api.svg.Line.InterpolationMode;
import com.github.gwtd3.demo.client.DemoCase;
import com.github.gwtd3.demo.client.Factory;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * Original demo is <a href="http://bl.ocks.org/mbostock/3808218">here</a>
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class LineDemo extends FlowPanel implements DemoCase {

	private static class Coords {
		double x, y;
		boolean defined;

		public Coords(int x, int y, boolean defined) {
			super();
			this.x = x;
			this.y = y;
			this.defined = defined;
		}

		public Coords(int x, int y) {
			this(x, y, true);
		}

		public static DatumFunction<Double> xAccessor() {
			return new DatumFunction<Double>() {
				@Override
				public Double apply(Element context, Value d, int index) {
					return d.<Coords> as().x;
				}
			};
		}

		public static DatumFunction<Double> yAccessor() {
			return new DatumFunction<Double>() {
				@Override
				public Double apply(Element context, Value d, int index) {
					return d.<Coords> as().y;
				}
			};
		}

		public static DatumFunction<Boolean> definedAccessor() {
			return new DatumFunction<Boolean>() {
				@Override
				public Boolean apply(Element context, Value d, int index) {
					return d.<Coords> as().defined;
				}
			};

		}

	}

	private boolean showPoints = true;
	private final Stack<Coords> points = new Stack<Coords>();
	private Selection svg;
	private Selection path;
	private Line line;
	protected InterpolationMode mode = InterpolationMode.LINEAR;
	protected int width = 450, height = 320;
	protected double tension;

	public interface Bundle extends ClientBundle {
		public static final Bundle INSTANCE = GWT.create(Bundle.class);

		@Source("LineDemo.css")
		public MyResources css();
	}

	interface MyResources extends CssResource {
		String linedemo();
	}

	/**
	 * 
	 */
	public LineDemo() {
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
		line = D3.svg().line().x(Coords.xAccessor()).y(Coords.yAccessor())
				.defined(Coords.definedAccessor());
		// create the things
		createButton("Add point", IconType.PLUS_SIGN, new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addPoint();
			}
		});
		createButton("Remove point", IconType.MINUS_SIGN, new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				removePoint();
			}
		});
		createButton("Add undefined point", IconType.PLUS_SIGN_ALT,
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						addPoint(false);
					}
				});
		createInterpolationModeWidget(Line.InterpolationMode.values());
		createTensionWidget();
		addShowPointsButton();
		svg = D3.select(this).append("svg").attr("width", width)
				.attr("height", height).append("g");
		path = svg.append("path").classed(Bundle.INSTANCE.css().linedemo(),
				true);
	}

	private void createTensionWidget() {
		HorizontalPanel p = new HorizontalPanel();
		p.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		p.add(new Label("Tension:"));
		DoubleBox ib = new DoubleBox();
		ib.setValue(line.tension());
		ib.addValueChangeHandler(new ValueChangeHandler<Double>() {
			@Override
			public void onValueChange(ValueChangeEvent<Double> event) {
				tension = event.getValue();
				update();
			}
		});
		p.add(ib);
		this.add(p);
	}

	private void addShowPointsButton() {
		CheckBox cb = new CheckBox("Show points");
		this.add(cb);
		cb.setValue(true);
		cb.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				showPoints = event.getValue();
				update();
			}
		});

	}

	private void createButton(String label, IconType icon,
			ClickHandler clickHandler) {
		Button b = new Button();
		b.setIcon(icon);
		b.setText(label);
		b.addClickHandler(clickHandler);
		this.add(b);

	}

	private void createInterpolationModeWidget(InterpolationMode[] values) {
		boolean first = true;
		for (final InterpolationMode mode : values) {
			RadioButton button = new RadioButton("mode");
			button.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					LineDemo.this.mode = mode;
					update();
				}
			});
			button.setText(mode.name());
			this.add(button);
			if (first) {
				button.setValue(true);
				first = false;
			}
		}

	}

	protected void addPoint() {
		addPoint(true);
	}

	protected void addPoint(boolean defined) {
		points.push(new Coords(Random.nextInt(width), Random.nextInt(height),
				defined));
		update();
	}

	protected void removePoint() {
		if (!points.empty())
			points.pop();
		update();
	}

	public void update() {
		line.interpolate(mode);
		line.tension(tension);
		path.attr("d", line.generate(points));

		UpdateSelection updateSelection = svg.selectAll("circle").data(
				showPoints ? points : new ArrayList<Coords>());
		updateSelection.enter().append("circle")
				.attr("cx", new DatumFunction<Double>() {
					@Override
					public Double apply(Element context, Value d, int index) {
						return d.<Coords> as().x;
					}
				}).attr("cy", new DatumFunction<Double>() {
					@Override
					public Double apply(Element context, Value d, int index) {
						return d.<Coords> as().y;
					}
				}).attr("r", 10);
		updateSelection.exit().remove();
	}

	@Override
	public void stop() {
		points.clear();
	}

	public static Factory factory() {
		return new Factory() {
			@Override
			public DemoCase newInstance() {
				return new LineDemo();
			}
		};
	}
}
