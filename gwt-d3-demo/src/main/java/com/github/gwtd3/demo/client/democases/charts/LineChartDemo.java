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
package com.github.gwtd3.demo.client.democases.charts;

import java.util.Date;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.demo.client.DemoCase;
import com.github.gwtd3.demo.client.Factory;
import com.github.gwtd3.demo.client.democases.test.Data;
import com.github.gwtd3.demo.client.democases.test.DataLoader;
import com.github.gwtd3.ui.Slider;
import com.github.gwtd3.ui.chart.LineChart;
import com.github.gwtd3.ui.event.RangeChangeEvent;
import com.github.gwtd3.ui.event.RangeChangeEvent.RangeChangeHandler;
import com.github.gwtd3.ui.model.BasePointBuilder;
import com.google.common.collect.Range;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class LineChartDemo extends Composite implements DemoCase {

    private static LineChartDemoUiBinder uiBinder = GWT.create(LineChartDemoUiBinder.class);

    interface LineChartDemoUiBinder extends UiBinder<Widget, LineChartDemo> {}

    @UiField(provided = true)
    LineChart<Data> chart;

    @UiField
    Slider xrange;

    private double timeRange;

    private final DataPoint point = new DataPoint();

    public LineChartDemo() {
        createChart();
        initWidget(uiBinder.createAndBindUi(this));
        loadData();
        installListener();
    }

    private void installListener() {
        chart.model().xModel().addRangeChangeHandler(new RangeChangeHandler() {
            @Override
            public void onRangeChange(final RangeChangeEvent event) {
                onTimeRangeChanged(event.getNewRange());
            }

        });
    }

    private void createChart() {
        this.chart = new LineChart<Data>(point);
        // configure
        chart.xAxis().formatter(new DatumFunction<String>() {
            @Override
            public String apply(final Element context, final Value d, final int index) {
                return DateTimeFormat.getShortTimeFormat().format(new Date((long) d.asDouble()));
            }
        });

    }

    private class DataPoint extends BasePointBuilder<Data> {
        @Override
        public double x(final Data value) {
            // System.out.println(value.getDate() + " " +
            // value.getDate().getTime());
            return value.getDate().getTime();
        }

        @Override
        public double y(final Data value) {
            return value.getPrice();
        }
    }

    private void loadData() {
        DataLoader.loadData(new AsyncCallback<Array<Data>>() {
            @Override
            public void onSuccess(final Array<Data> result) {
                updateSliders(result);
                updateChart(result);
            }

            @Override
            public void onFailure(final Throwable caught) {
                throw new RuntimeException(caught);
            }
        });
    }

    protected void updateSliders(final Array<Data> result) {
        // x and y
        double timeMin = D3.min(result, point.getXAccessor()).asDouble();
        double timeMax = D3.max(result, point.getXAccessor()).asDouble();
        timeRange = (timeMax - timeMin);
        double halfRange = timeRange / 2;
        // System.out.println(timeMin.asDouble() + " " + timeMax.asDouble());
        xrange.setMin(timeMin);
        xrange.setMax(timeMax);
        xrange.setValue(timeMin + halfRange);

        double priceMin = D3.min(result, point.getYAccessor()).asDouble();
        double priceMax = D3.max(result, point.getYAccessor()).asDouble();
        chart.model().yModel().setVisibleDomain(priceMin, priceMax);
        chart.model().xModel().setVisibleDomain(timeMin, timeMax);
    }

    @UiHandler("xrange")
    public void onVisibleRangeUpperChanged(final ValueChangeEvent<Double> e) {
        updateAxisRange();
    }

    private void updateAxisRange() {
        chart.model().xModel()
                .setVisibleDomain(xrange.getValue() - (timeRange / 4), xrange.getValue() + (timeRange / 4));
    }

    private void updateChart(final Array<Data> result) {
        // fill the serie
        System.out.println("Data loaded");
        // values
        Range<Double> domain = chart.model().xModel().visibleDomain();
        Double lower = domain.lowerEndpoint();
        Double upper = domain.upperEndpoint();
        Double range = upper - lower;
        range = range / 8;
        chart.model().serie("tf1").values(result.asList())
                .putNamedRange("diff1", Range.closed(lower + range, upper - range));

    }

    private void onTimeRangeChanged(final Range<?> newRange) {

    }

    public static Factory factory() {
        return new Factory() {
            @Override
            public DemoCase newInstance() {
                return new LineChartDemo();
            }
        };
    }

    @Override
    public void start() {}

    @Override
    public void stop() {}

}
