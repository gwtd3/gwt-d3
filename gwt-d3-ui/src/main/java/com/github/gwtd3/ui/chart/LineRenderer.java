package com.github.gwtd3.ui.chart;

import java.util.Collection;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.JsArrays;
import com.github.gwtd3.api.core.Datum;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.svg.Line;
import com.github.gwtd3.api.svg.Line.InterpolationMode;
import com.github.gwtd3.ui.model.PointViewer;
import com.google.gwt.dom.client.Element;

/**
 * Render a list of double values as connected points,
 * from an x and y axis model.
 * 
 * @author SCHIOCA
 * 
 */
public class LineRenderer<T> {

    private Line lineGenerator;

    private PointViewer<T> builder;

    public LineRenderer(final PointViewer<T> builder) {
        super();
        this.builder = builder;
        // create the line generator which will use the scale functions
        lineGenerator = D3.svg().line().interpolate(InterpolationMode.BASIS)
                // convert the domain object to a pixel distance
                .x(new DatumFunction<Double>() {
                    @Override
                    public Double apply(final Element context, final Datum d, final int index) {
                        double x = LineRenderer.this.builder.x(d.<T> as());
                        // System.out.println(x);
                        return x;
                    }
                })
                // convert the domain to a pixel distance
                .y(new DatumFunction<Double>() {
                    @Override
                    public Double apply(final Element context, final Datum d, final int index) {
                        double y = LineRenderer.this.builder.y(d.<T> as());
                        // System.out.println(y);
                        return y;
                    }
                })
                .defined(new DatumFunction<Boolean>() {
                    @Override
                    public Boolean apply(final Element context, final Datum d, final int index) {
                        return builder.isVisible(d.<T> as());
                    }
                }
                );
    }

    public String generatePath(final Collection<T> values) {
        // System.out.println("values are " + values.size());
        return lineGenerator.apply(JsArrays.asJsArray(values));
    }
}
