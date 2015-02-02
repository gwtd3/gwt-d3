package com.github.gwtd3.api.geom;

import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.layout.Link;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * Voronoi layouts are particularly useful for invisible interactive regions, as
 * demonstrated in Nate Vack’s <a
 * href="http://bl.ocks.org/njvack/1405439">Voronoi picking</a> example. See
 * Tovi Grossman’s paper on <a
 * href="http://www.tovigrossman.com/BubbleCursor">bubble cursors</a> for a
 * related concept.
 *
 * @author anthomi
 *
 */
public class Voronoi extends JavaScriptObject {

    protected Voronoi() {

    }

    /**
     * Sets the clip extent of the Voronoi layout to the specified bounds and
     * returns the layout.
     * <p>
     * Use of a clip extent is strongly recommended, as unclipped polygons may
     * have large coordinates which do not display correctly.
     * <p>
     * Alternatively, you can also employ custom clipping without specifying a
     * size, either in SVG or by post-processing with
     * {@link Polygon#clip(Array)}.
     * <p>
     * To clear the clipping, see {@link #clearClipExtent()}
     * <p>
     *
     * @param x0
     *            the left side of the extent
     * @param y0
     *            the top side of the extent
     * @param x1
     *            the right side of the extent
     * @param y1
     *            the bottom side of the extent
     * @return the current layout
     */
    public final native Voronoi clipExtent(int x0, int y0, int x1, int y1)/*-{
		return this.clipExtent([ [ x0, y0 ], [ x1, y1 ] ]);
    }-*/;

    /**
     * Clear the extent clipping.
     * <p>
     *
     * @return
     */
    public final native Voronoi clearClipExtent()/*-{
		return this.clipExtent(null);
    }-*/;

    /**
     * Get the current clip extent which defaults to null.
     * <p>
     *
     * @return the current clip extent which defaults to null.
     */
    public final native Array<Array<Double>> clipExtent()/*-{
		return this.clipExtent();
    }-*/;

    /**
     * Returns an array of polygons, one for each input vertex in the specified
     * data array.
     * <p>
     * If any vertices are coincident or have NaN positions, the behavior of
     * this method is undefined: most likely, invalid polygons will be returned!
     * You should filter invalid vertices, and consolidate coincident vertices,
     * before computing the tessellation.
     * <p>
     *
     * @param vertices
     *            the array of vertices
     * @return the array of polygons
     */
    public final native <T> Array<T> apply(Array<T> vertices)/*-{
		return this(vertices);
    }-*/;

    /**
     * Sets the x-coordinate accessor.
     * <p>
     * The default accessor consider datum as a two element array and returns
     * the first element.
     *
     * @param xAccessor
     *            the x accessor
     * @return the current layout
     */
    public final native Voronoi x(DatumFunction<Double> xAccessor)/*-{
		return this
				.x(function(d, i) {
					return xAccessor.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
    }-*/;

    /**
     * Sets the y-coordinate accessor.
     * <p>
     * The default accessor consider datum as a two element array and returns
     * the first element.
     *
     * @param yAccessor
     *            the y accessor
     * @return the current layout
     */

    public final native Voronoi y(DatumFunction<Double> yAccessor)/*-{
		return this
				.y(function(d, i) {
					return yAccessor.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
    }-*/;

    /**
     * TODO: make a demo from http://bl.ocks.org/mbostock/1073373 TODO:
     * documentation
     *
     * @return
     * @experimental
     */
    public final native Array<Link> links(Array<?> nodes)/*-{
		return this.links();
    }-*/;

    /**
     * TODO: make a demo from http://bl.ocks.org/mbostock/1073373 TODO:
     * documentation
     *
     * @return
     * @experimental
     */
    public final native Array<Link> triangles(Array<?> nodes)/*-{
		return this.links();
    }-*/;

}
