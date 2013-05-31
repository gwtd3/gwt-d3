/**
 * 
 */
package com.github.gwtd3.demo.client.democases;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.JsArrays;
import com.github.gwtd3.api.core.Datum;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.demo.client.DemoCase;
import com.github.gwtd3.demo.client.Factory;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class StupidExample extends FlowPanel implements DemoCase {

    private Selection svg;

    /**
	 * 
	 */
    public StupidExample() {
        super();
    }

    @Override
    public void start() {
        Selection selection = D3.select(this);

        int w = 960;
        int h = 800;
        svg = selection.append("svg:svg").attr("width", w).attr("height", h)
                .append("svg:g").attr("transform", "translate(40,0)");

        svg.selectAll("circle").data(JsArrays.asJsArray(32, 57, 112, 293))
                .enter()
                .append("circle")
                .attr("cy", 90)
                .attr("cx", new DatumFunction<Integer>() {
                    @Override
                    public Integer apply(final Element context, final Datum d, final int index) {
                        return d.asInt();
                    }
                })// String
                .attr("r", new DatumFunction<Double>() {
                    @Override
                    public Double apply(final Element context, final Datum d, final int index) {
                        return Math.sqrt(d.asDouble());
                    }
                });// sqrt

        svg.selectAll("circle").data(JsArrays.asJsArray(12, 32, 44))
                .enter().append("circle")
                .attr("cy", 90)
                .attr("cx", new DatumFunction<Integer>() {
                    @Override
                    public Integer apply(final Element context, final Datum d, final int index) {
                        return d.asInt();
                    }
                })// String
                .attr("r", new DatumFunction<Double>() {
                    @Override
                    public Double apply(final Element context, final Datum d, final int index) {
                        return Math.sqrt(d.asDouble());
                    }
                });// sqrt

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.github.gwtd3.demo.client.D3Demo#stop()
     */
    @Override
    public void stop() {
        svg = null;

    }

    public static Factory factory() {
        return new Factory() {
            @Override
            public DemoCase newInstance() {
                return new StupidExample();
            }
        };
    }
}
