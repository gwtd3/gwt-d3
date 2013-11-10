package com.github.gwtd3.api.layout;


/**
 * The cluster layout produces dendrograms: node-link diagrams that place leaf
 * nodes of the tree at the same depth.
 * <p>
 * For example, a cluster layout can be used to organize software classes in a
 * package hierarchy: <img
 * src="https://github.com/mbostock/d3/wiki/cluster.png"/>
 * <p>
 * Like other classes in D3, layouts follow the method chaining pattern where
 * setter methods return the layout itself, allowing multiple setters to be
 * invoked in a concise statement.
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Cluster extends HierarchicalLayout {

	protected Cluster() {
	}

	public final native Cluster size(int width, int height)/*-{
		return this.size([width, height]);
	}-*/;

}
