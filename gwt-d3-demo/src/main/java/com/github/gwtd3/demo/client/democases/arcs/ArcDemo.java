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
package com.github.gwtd3.demo.client.democases.arcs;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.svg.Arc;
import com.github.gwtd3.demo.client.DemoCase;
import com.github.gwtd3.demo.client.Factory;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * FIXME find another Slider component
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class ArcDemo extends Composite implements DemoCase {

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
//
//	private static ArcDemoUiBinder uiBinder = GWT.create(ArcDemoUiBinder.class);
//
//	interface ArcDemoUiBinder extends UiBinder<Widget, ArcDemo> {
//	}
//
//	@UiField
//	SVGDocumentContainer svgWidget;
//
//	Selection svg;
//
//	@UiField
//	Slider innerRadius;
//	@UiField
//	Slider outerRadius;
//	@UiField
//	Slider startAngle;
//	@UiField
//	Slider endAngle;
//
//	private final Arc arc;
//
//	public ArcDemo() {
//		initWidget(ArcDemo.uiBinder.createAndBindUi(this));
//
//		// svgWidget.translate(200, 200);
//		arc = D3.svg().arc().innerRadius(40)
//				.outerRadius(100)
//				.startAngle(0)
//				.endAngle(Math.PI);
//		svg = svgWidget.select().append("g").attr("transform", "translate(200,200)");
//		svg.append("path");
//		update();
//	}
//
//	@UiHandler("innerRadius")
//	public void onInnerRadius(final ValueChangeEvent<Double> e) {
//		update();
//	}
//
//	@UiHandler("outerRadius")
//	public void onOuterRadius(final ValueChangeEvent<Double> e) {
//		update();
//	}
//
//	@UiHandler("startAngle")
//	public void onStartAngle(final ValueChangeEvent<Double> e) {
//		update();
//	}
//
//	@UiHandler("endAngle")
//	public void onEndAngle(final ValueChangeEvent<Double> e) {
//		update();
//	}
//
//	/**
//	 * 
//	 */
//	private void update() {
//		if (innerRadius.getValue() != null) {
//			arc.innerRadius(innerRadius.getValue());
//		}
//		if (outerRadius.getValue() != null) {
//			arc.outerRadius(outerRadius.getValue());
//		}
//		if (startAngle.getValue() != null) {
//			arc.startAngle(startAngle.getValue());
//		}
//		if (endAngle.getValue() != null) {
//			arc.endAngle(endAngle.getValue());
//		}
//		svg.select("path")
//				.attr("d", arc);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see com.github.gwtd3.demo.client.DemoCase#start()
//	 */
//	@Override
//	public void start() {
//		// TODO Auto-generated method stub
//
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see com.github.gwtd3.demo.client.DemoCase#stop()
//	 */
//	@Override
//	public void stop() {
//		// TODO Auto-generated method stub
//
//	}
//
//	/**
//	 * @return
//	 */
//	public static Factory factory() {
//		return new Factory() {
//			@Override
//			public DemoCase newInstance() {
//				return new ArcDemo();
//			}
//		};
//	}

}
