package com.github.gwtd3.api.svg;

import com.github.gwtd3.api.Coords;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.layout.Node;
import com.github.gwtd3.api.layout.TreeLayout;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * Diagonal generator. Assumes the input data is an object with named attributes matching the accessors
 * {@link Diagonal#source()} and {@link Diagonal#target()}. The returned function generates the path data for a cubic
 * Bezier connecting the source and target points; the tangents are specified to produce smooth fan-in and fan-out when
 * connecting nodes, as in a node-link diagram.
 * 
 * Diagonals default to Cartesian orientations, but can be used in radial and other orientations using
 * {@link Diagonal#projection(DatumFunction)}.
 * 
 * @author <a href="mailto:evanshi09@gmail.com">Evan Shi</a>
 * 
 */
public class Diagonal
    extends PathDataGenerator {
    protected Diagonal() {
        super();
    }

    /**
     * The projection converts the starting or ending point returned by the source and target accessors
     * {@link #source()} and {@link #target()}, returning a two-element array of numbers. The default accessor assumes
     * that the input point is an object with x and y attributes.
     * 
     * The default accessor is thus compatible with D3's various {@link Node} layouts, including {@link TreeLayout},
     * partition and cluster. For example, to produce a radial diagonal see the <a
     * href="https://github.com/mbostock/d3/wiki/SVG-Shapes#wiki-diagonal_projection">d3 API reference</a>
     * 
     * The projection is invoked in a similar manner as other value functions in D3. The function is passed two
     * arguments, the current source or target point (derived from the
     * current data, d) and the current index (i).
     * 
     * @return the accessor function registered with the diagonal generator
     */
    public final native DatumFunction<?> projection() /*-{
		return this.projection();
    }-*/;

    /**
     * The projection converts the starting or ending point returned by the source and target accessors
     * {@link #source()} and {@link #target()}, returning a two-element array of numbers. The default accessor assumes
     * that the input point is an object with x and y attributes.
     * 
     * The default accessor is thus compatible with D3's various {@link Node} layouts, including {@link TreeLayout},
     * partition and cluster. For example, to produce a radial diagonal see the <a
     * href="https://github.com/mbostock/d3/wiki/SVG-Shapes#wiki-diagonal_projection">d3 API reference</a>
     * 
     * The projection is invoked in a similar manner as other value functions in D3. The function is passed two
     * arguments, the current source or target point (derived from the
     * current data, d) and the current index (i).
     * 
     * @param a datum function
     * @return this diagonal object
     */
    public final native Diagonal projection(DatumFunction<?> df) /*-{
		return this
				.projection(function(d, i) {
					return df.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
    }-*/;

    /**
     * Returns the current source accessor, which can either be a function or constant object. The default accessor is a
     * function taking a JSO with a source attribute as described in <a
     * href="https://github.com/mbostock/d3/wiki/SVG-Shapes#wiki-diagonal_source">d3 API reference</a>.
     * 
     * @return the current source accessor
     */
    public final native JavaScriptObject source() /*-{
		return this.source();
    }-*/;

    /**
     * Returns the current target accessor, which can either be a function or constant object. The default accessor is a
     * function taking a JSO with a target attribute as described in <a
     * href="https://github.com/mbostock/d3/wiki/SVG-Shapes#wiki-target">d3 API reference</a>.
     * 
     * @return the current target accessor
     */
    public final native JavaScriptObject target() /*-{
		return this.target();
    }-*/;

    /**
     * Sets the source accessor to be constant. Per the <a
     * href="https://github.com/mbostock/d3/wiki/SVG-Shapes#wiki-diagonal_source">d3 API reference</a> the source
     * describes a point of x, y coordinates, represented by the {@link Coords} class.
     * 
     * @param a constant source
     * @return the diagonal object
     */
    public final native Diagonal source(Coords source) /*-{
		return this.source(source);
    }-*/;

    /**
     * Sets the target accessor to be constant. Per the <a
     * href="https://github.com/mbostock/d3/wiki/SVG-Shapes#wiki-diagonal_target">d3 API reference</a> the target
     * describes a point of x, y coordinates, represented by the {@link Coords} class.
     * 
     * @param a constant target
     * @return the diagonal object
     */
    public final native Diagonal target(Coords target) /*-{
		return this.target(target);
    }-*/;

    /**
     * Sets the source accessor function. The default accessor function takes in a JSO with a source attribute and
     * returns it. The source-accessor is invoked in the same manner as other value functions in D3. The function is
     * passed two arguments, the current datum (d) and the current index (i). It is also possible to specify the
     * source-accessor as a constant {@link #source(Coords)} rather than a function.
     * 
     * @param the source accessor function
     * @return the diagonal object
     */
    public final native Diagonal source(DatumFunction<?> df) /*-{
		return this
				.source(function(d, i) {
					return df.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
    }-*/;

    /**
     * Sets the target accessor function. The default accessor function takes in a JSO with a target attribute and
     * returns it. The target-accessor is invoked in the same manner as other value functions in D3. The function is
     * passed two arguments, the current datum (d) and the current index (i). It is also possible to specify the
     * target-accessor as a constant {@link #target(Coords)} rather than a function.
     * 
     * @param the target accessor function
     * @return the diagonal object
     */
    public final native Diagonal target(DatumFunction<?> df) /*-{
		return this
				.target(function(d, i) {
					return df.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
				});
    }-*/;
}
