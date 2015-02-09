package com.github.gwtd3.api.layout;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.Sort;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.functions.DatumFunction;

/**
 * Per the <a href="https://github.com/mbostock/d3/wiki/Partition-Layout">d3 API
 * reference</a>, The <b>partition layout</b> produces adjacency diagrams: a
 * space-filling variant of a node-link tree diagram. Rather than drawing a link
 * between parent and child in the hierarchy, nodes are drawn as solid areas
 * (either arcs or rectangles), and their placement relative to other nodes
 * reveals their position in the hierarchy. The size of the nodes encodes a
 * quantitative dimension that would be difficult to show in a node-link
 * diagram.
 * 
 * @author <a href="mailto:Benjamin.DeLillo@One-Geek.com">Benjamin DeLillo</a>
 * 
 */
public class Partition extends HierarchicalLayout{
    protected Partition() {
        super();
    }

    /**
	 * Sets the specified children accessor function. The default children
	 * accessor function assumes the input data is an object with a children
	 * array. The children accessor is first invoked for root node in the
	 * hierarchy. If the accessor returns null, then the node is assumed to be a
	 * leaf node at the layout traversal terminates. Otherwise, the accessor
	 * should return an array of data elements representing the child nodes.
	 * 
	 * @param df a datum function describing how to compute children
	 * @return this partition object
	 */
    public final native Partition children(DatumFunction<Array<Node>> df) /*-{
        return this
                .children(function(d)) {
                    return df.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},0);
                });
    }-*/;

    /**
	 * Sets the available layout size to the specified two-element array of
	 * numbers representing x and y.
	 * 
	 * @param a two-element array of width and height of partition
	 * @return this partition object
	 */
    public final native Partition size(double width, double height) /*-{
        return this.size([ x, y ]);
    }-*/;

    /**
	 * Returns the current partition size, which defaults to 1×1.
	 * 
	 * @return a two-element array representing the current size of the partition
	 */
    public final native Array<Double> size() /*-{
        return this.size();
    }-*/;

    /**
	 * Sets the sort order of sibling nodes for the layout using the specified
	 * comparator function. The comparator function is invoked for pairs of
	 * nodes, being passed the input data for each node. The default comparator
	 * is null, which disables sorting and uses tree traversal order. Sorting by
	 * the node's name or key is common and can be done easily using
	 * {@link D3#ascending()} or {@link D3#descending()}.
	 * 
	 * @param {@link Sort} a predefined sorting convention
	 * @return this partition object
	 */
    public final native Partition sort(Sort sort) /*-{
        return this.sort(sort);
    }-*/;
    
	/**
	 * Sets the value accessor to the specified function. The value accessor is
	 * invoked for each input data element, and must return a number
	 * representing the numeric value of the node. This value is used to set the
	 * area of each node proportionally to the value.
	 * 
	 * @param df a datum function describing how to access node values
	 * @return this partition object
	 */
    public final native Partition value(DatumFunction<?> df) /*-{
        return this
                .value(function(d, i) {
                    return df.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
                });
    }-*/;

    /**
	 * Returns the current value accessor which defaults to null, meaning that
	 * the value attribute is not computed.
	 * 
	 * @return the current datum function registered for calculating node values
	 */
    public final native DatumFunction<?> value() /*-{
        return this.value();
    }-*/;
}

