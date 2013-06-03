/**
 * 
 */
package com.github.gwtd3.demo.client.democases.charts;

import com.github.gwtd3.api.scales.LinearScale;
import com.github.gwtd3.api.svg.Axis.Orientation;
import com.github.gwtd3.demo.client.DemoCase;
import com.github.gwtd3.demo.client.Factory;
import com.github.gwtd3.ui.chart.ChartAxis;
import com.github.gwtd3.ui.model.AxisModel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class AxisDemo extends Composite implements DemoCase {

	private static AxisDemoUiBinder uiBinder = GWT.create(AxisDemoUiBinder.class);

	interface AxisDemoUiBinder extends UiBinder<Widget, AxisDemo> {
	}

	@UiField(provided = true)
	ChartAxis<LinearScale> axis;
	private final AxisModel<LinearScale> model;

	public AxisDemo() {
		model = AxisModel.createLinear();
		axis = new ChartAxis<LinearScale>(model, Orientation.LEFT);
		initWidget(AxisDemo.uiBinder.createAndBindUi(this));

	}

	@Override
	public void start() {
	}

	@Override
	public void stop() {

	}

	/**
	 * @return
	 */
	public static Factory factory() {
		return new Factory() {
			@Override
			public DemoCase newInstance() {
				return new AxisDemo();
			}
		};
	}

}
