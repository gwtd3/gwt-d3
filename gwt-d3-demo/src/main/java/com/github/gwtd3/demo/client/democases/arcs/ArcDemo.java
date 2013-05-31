/**
 * 
 */
package com.github.gwtd3.demo.client.democases.arcs;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.svg.Arc;
import com.github.gwtd3.demo.client.DemoCase;
import com.github.gwtd3.demo.client.Factory;
import com.github.gwtd3.demo.client.ui.SVGD3Widget;
import com.github.gwtd3.demo.client.ui.Slider;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class ArcDemo extends Composite implements DemoCase {

    private static ArcDemoUiBinder uiBinder = GWT.create(ArcDemoUiBinder.class);

    interface ArcDemoUiBinder extends UiBinder<Widget, ArcDemo> {}

    @UiField
    SVGD3Widget svgWidget;

    Selection svg;

    @UiField
    Slider innerRadius;
    @UiField
    Slider outerRadius;
    @UiField
    Slider startAngle;
    @UiField
    Slider endAngle;

    private final Arc arc;

    public ArcDemo() {
        initWidget(ArcDemo.uiBinder.createAndBindUi(this));
        svgWidget.translate(200, 200);
        arc = D3.svg().arc().innerRadius(40)
                .outerRadius(100)
                .startAngle(0)
                .endAngle(Math.PI);
        svg = svgWidget.g();
        svg.append("path");
        update();
    }

    @UiHandler("innerRadius")
    public void onInnerRadius(final ValueChangeEvent<Double> e) {
        update();
    }

    @UiHandler("outerRadius")
    public void onOuterRadius(final ValueChangeEvent<Double> e) {
        update();
    }

    @UiHandler("startAngle")
    public void onStartAngle(final ValueChangeEvent<Double> e) {
        update();
    }

    @UiHandler("endAngle")
    public void onEndAngle(final ValueChangeEvent<Double> e) {
        update();
    }

    /**
	 * 
	 */
    private void update() {
        if (innerRadius.getValue() != null) {
            arc.innerRadius(innerRadius.getValue());
        }
        if (outerRadius.getValue() != null) {
            arc.outerRadius(outerRadius.getValue());
        }
        if (startAngle.getValue() != null) {
            arc.startAngle(startAngle.getValue());
        }
        if (endAngle.getValue() != null) {
            arc.endAngle(endAngle.getValue());
        }
        svg.select("path")
                .attr("d", arc);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.github.gwtd3.demo.client.DemoCase#start()
     */
    @Override
    public void start() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.github.gwtd3.demo.client.DemoCase#stop()
     */
    @Override
    public void stop() {
        // TODO Auto-generated method stub

    }

    /**
     * @return
     */
    public static Factory factory() {
        return new Factory() {
            @Override
            public DemoCase newInstance() {
                return new ArcDemo();
            }
        };
    }

}
