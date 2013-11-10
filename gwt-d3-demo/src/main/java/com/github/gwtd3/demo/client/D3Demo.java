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
package com.github.gwtd3.demo.client;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.demo.client.democases.ArcTween;
import com.github.gwtd3.demo.client.democases.AxisComponent;
import com.github.gwtd3.demo.client.democases.ChordDiagram;
import com.github.gwtd3.demo.client.democases.GeneralUpdatePattern1;
import com.github.gwtd3.demo.client.democases.GeneralUpdatePattern2;
import com.github.gwtd3.demo.client.democases.GeneralUpdatePattern3;
import com.github.gwtd3.demo.client.democases.LorenzSystem;
import com.github.gwtd3.demo.client.democases.TreeDemo;
import com.github.gwtd3.demo.client.democases.behaviors.DragMultiples;
import com.github.gwtd3.demo.client.democases.behaviors.ZoomDemo;
import com.github.gwtd3.demo.client.democases.geom.HullDemo;
import com.github.gwtd3.demo.client.democases.geom.MitchellBestCandidate;
import com.github.gwtd3.demo.client.democases.layout.ClusterDendogram;
import com.github.gwtd3.demo.client.democases.svg.LineDemo;
import com.github.gwtd3.demo.client.democases.svg.SymbolDemo;
import com.github.gwtd3.demo.client.test.ui.TestRunner;
import com.github.gwtd3.demo.client.test.ui.TestSessionContainer;
import com.github.gwtd3.demo.client.testcases.D3TestSuite;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.debug.client.DebugInfo;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class D3Demo implements EntryPoint {

	public static final String DEMO_CONTAINER_ID = "demoContainer";
	private ComplexPanel demoContainer;
	private DemoCase currentDemo;
	private TestSessionContainer testContainer;

	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {
		DebugInfo.setDebugIdPrefix("");
		GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void onUncaughtException(final Throwable e) {
				GWT.log("Uncaught error", e);
				Window.alert("Error. Go to see the logs");
			}
		});

		DockLayoutPanel container = new DockLayoutPanel(Unit.PX);
		container.setSize("100%", "100%");

		container.addNorth(new Label("GWT-D3 : A thin GWT wrapper around D3.",
				false), 20);
		container.addNorth(new Label("D3 API version: " + D3.version(), false),
				20);

		FlowPanel p = new FlowPanel();
		ComplexPanel buttonContainer = new VerticalPanel();
		buttonContainer.add(new TestButton());
		// buttonContainer.add(new DemoButton("Arc", ArcDemo.factory()));
		buttonContainer.add(new DemoButton("Axis Component", AxisComponent
				.factory()));
		buttonContainer.add(new DemoButton("Line Demo", LineDemo.factory()));
		buttonContainer
		.add(new DemoButton("Symbol Demo", SymbolDemo.factory()));
		buttonContainer.add(new DemoButton("General Update Pattern I",
				GeneralUpdatePattern1.factory()));
		buttonContainer.add(new DemoButton("General Update Pattern II",
				GeneralUpdatePattern2.factory()));
		buttonContainer.add(new DemoButton("General Update Pattern III",
				GeneralUpdatePattern3.factory()));
		buttonContainer.add(new DemoButton("Arc Tween", ArcTween.factory()));
		buttonContainer.add(new DemoButton("Chord diagram", ChordDiagram
				.factory()));
		buttonContainer.add(new DemoButton("Lorenz System", LorenzSystem
				.factory()));

		buttonContainer.add(new DemoButton("Mitchell's Best Candidate",
				MitchellBestCandidate.factory()));
		//		buttonContainer.add(new DemoButton("Shape Tweening", ShapeTweeningDemo
		//				.factory()));
		buttonContainer.add(new DemoButton("Convex Hull", HullDemo.factory()));

		buttonContainer.add(new DemoButton("Drag Multiples", DragMultiples
				.factory()));
		buttonContainer.add(new DemoButton("Zoom", ZoomDemo.factory()));
		buttonContainer.add(new DemoButton("Collapsible Tree", TreeDemo
				.factory()));

		buttonContainer.add(new DemoButton("Cluster Dendogram", ClusterDendogram
				.factory()));

		p.add(buttonContainer);
		container.addWest(p, 200);

		demoContainer = new FlowPanel();
		demoContainer.ensureDebugId(D3Demo.DEMO_CONTAINER_ID);
		demoContainer.setSize("100%", "100%");
		demoContainer.getElement().getStyle().setOverflow(Overflow.AUTO);
		container.add(demoContainer);

		RootLayoutPanel.get().add(container);

		container.forceLayout();
	}

	public class DemoButton extends Button {

		public DemoButton(final String title, final Factory demoClass) {
			super(title, new DemoClickHandler(demoClass));
			ensureDebugId(demoClass.id());
		}

	}

	public class DemoClickHandler implements ClickHandler {
		private final Factory demoClass;

		public DemoClickHandler(final Factory demoClass) {
			super();
			this.demoClass = demoClass;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt
		 * .event.dom.client.ClickEvent)
		 */
		@Override
		public void onClick(final ClickEvent event) {
			stopCurrentDemo();

			DemoCase demo = demoClass.newInstance();
			demoContainer.add(demo);
			currentDemo = demo;
			demo.start();
		}

	}

	public class TestButton extends Button {
		public static final String ID = "testSuiteButton";

		public TestButton() {
			super("Test Suite", new ClickHandler() {
				private boolean firstTime = true;
				private TestRunner runner;

				@Override
				public void onClick(final ClickEvent event) {
					stopCurrentDemo();
					if (firstTime) {
						testContainer = new TestSessionContainer();
						runner = new TestRunner(testContainer);
						runner.setTests(D3TestSuite.get().getTests());
						firstTime = false;
					}
					demoContainer.add(testContainer);
				}
			});
			ensureDebugId(TestButton.ID);
		}
	}

	private void stopCurrentDemo() {
		if (currentDemo != null) {
			currentDemo.stop();
			demoContainer.remove(currentDemo);
			currentDemo = null;
		} else if ((testContainer != null)
				&& testContainer.getParent().equals(demoContainer)) {
			demoContainer.remove(testContainer);
		}

	}

}
