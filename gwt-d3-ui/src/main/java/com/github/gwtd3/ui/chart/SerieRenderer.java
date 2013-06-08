package com.github.gwtd3.ui.chart;

import com.github.gwtd3.api.core.Datum;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.ui.chart.LineChart.Styles;
import com.github.gwtd3.ui.data.DefaultSelectionUpdater;
import com.github.gwtd3.ui.data.SelectionUpdater;
import com.github.gwtd3.ui.model.PointViewer;
import com.github.gwtd3.ui.model.Serie;
import com.google.gwt.dom.client.Element;

public class SerieRenderer<T> extends DefaultSelectionUpdater implements SelectionUpdater {

    private final LineRenderer<T> renderer;
    private final Styles styles;

    public SerieRenderer(final PointViewer<T> builder, final Styles styles) {
        // FIXME: let the subclass decide which element
        super("." + styles.line());
        this.styles = styles;
        this.renderer = new LineRenderer<T>(builder);
    }

    @Override
    public String getElementName() {
        // creates a SVG path
        return "path";
    }

    @Override
    public void afterEnter(final Selection selection) {
        super.afterEnter(selection);
        // configure the new created path
        selection.classed(styles.line(), true);
    }

    @Override
    public void onJoinEnd(final Selection selection) {
        super.onJoinEnd(selection);

        // setting the attribute d of the path
        selection.attr("d", new DatumFunction<String>() {
            @Override
            public String apply(final Element context, final Datum d, final int index) {
                return renderer.generatePath(asSerie(d).values());
            }
        });
    }

    private Serie<T> asSerie(final Value v) {
        return v.as();
    }
}
