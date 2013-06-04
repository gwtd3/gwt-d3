/**
 * 
 */
package com.github.gwtd3.demo.client.democases.charts;

import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.scales.LinearScale;
import com.github.gwtd3.api.svg.Axis.Orientation;
import com.github.gwtd3.demo.client.DemoCase;
import com.github.gwtd3.demo.client.Factory;
import com.github.gwtd3.ui.Slider;
import com.github.gwtd3.ui.chart.ChartAxis;
import com.github.gwtd3.ui.model.AxisModel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
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
	ChartAxis<LinearScale> vAxis;
	@UiField(provided = true)
	ChartAxis<LinearScale> hAxis;
	@UiField(provided = true)
	ChartAxis<LinearScale> vAxis2;
	@UiField(provided = true)
	ChartAxis<LinearScale> hAxis2;

	@UiField
	HasText title;

	private final AxisModel<LinearScale> model;

	@UiField
	Slider visibleRangeUpper;
	@UiField
	Slider visibleRangeLower;

	@UiField
	TextBox formatSpecifier;
	@UiField
	HasValue<Boolean> d3Formatter;
	@UiField
	HasValue<Boolean> numberFormatter;

	public AxisDemo() {
		model = AxisModel.createLinear();
		vAxis = new ChartAxis<LinearScale>(model, Orientation.LEFT);
		hAxis = new ChartAxis<LinearScale>(model, Orientation.TOP);
		vAxis2 = new ChartAxis<LinearScale>(model, Orientation.RIGHT);
		hAxis2 = new ChartAxis<LinearScale>(model, Orientation.BOTTOM);
		initWidget(AxisDemo.uiBinder.createAndBindUi(this));

	}

	@Override
	public void start() {
	}

	@Override
	public void stop() {

	}

	@UiHandler("d3Formatter")
	public void onD3FormatterChosen(final ValueChangeEvent<Boolean> e) {
		if (e.getValue().booleanValue() == false) {
			return;
		}
		formatSpecifier.setText("0000.4");
		updateFormatter();
	}

	@UiHandler("numberFormatter")
	public void onNumberFormatterChosen(final ValueChangeEvent<Boolean> e) {
		if (e.getValue().booleanValue() == false) {
			return;
		}
		formatSpecifier.setText("0000.0000");
		updateFormatter();
	}

	@UiHandler("formatSpecifier")
	public void onFormatSpecifierChanged(final KeyUpEvent e) {
		updateFormatter();
	}

	@UiHandler("title")
	public void onTitleChanged(final KeyUpEvent e) {
		hAxis.title(title.getText());
		vAxis.title(title.getText());
		hAxis2.title(title.getText());
		vAxis2.title(title.getText());

	}

	private void updateFormatter() {
		String specifier = formatSpecifier.getText();
		if (d3Formatter.getValue().booleanValue()) {
			vAxis.formatter(D3.format(specifier));
			hAxis.formatter(D3.format(specifier));
			vAxis2.formatter(D3.format(specifier));
			hAxis2.formatter(D3.format(specifier));
		}
		else {
			vAxis.formatter(NumberFormat.getFormat(specifier));
			hAxis.formatter(NumberFormat.getFormat(specifier));
			vAxis2.formatter(NumberFormat.getFormat(specifier));
			hAxis2.formatter(NumberFormat.getFormat(specifier));
		}
	}

	@UiHandler("length")
	public void onLengthChanged(final ValueChangeEvent<Double> e) {
		vAxis.setLength(e.getValue().intValue());
		hAxis.setLength(e.getValue().intValue());
		vAxis2.setLength(e.getValue().intValue());
		hAxis2.setLength(e.getValue().intValue());
	}

	@UiHandler("visibleRangeUpper")
	public void onVisibleRangeUpperChanged(final ValueChangeEvent<Double> e) {
		updateAxisRange();
	}

	@UiHandler("visibleRangeLower")
	public void onVisibleRangeLowerChanged(final ValueChangeEvent<Double> e) {
		updateAxisRange();
	}

	private void updateAxisRange() {
		vAxis.model().setVisibleDomain(visibleRangeLower.getValue(), visibleRangeUpper.getValue());
		hAxis.model().setVisibleDomain(visibleRangeLower.getValue(), visibleRangeUpper.getValue());
		vAxis2.model().setVisibleDomain(visibleRangeLower.getValue(), visibleRangeUpper.getValue());
		hAxis2.model().setVisibleDomain(visibleRangeLower.getValue(), visibleRangeUpper.getValue());
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
