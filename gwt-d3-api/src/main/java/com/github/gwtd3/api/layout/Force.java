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
package com.github.gwtd3.api.layout;

import java.util.List;

import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.behaviour.Drag;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.functions.DatumFunction;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * A force-directed layout with the default settings: size 1×1, link strength 1, friction 0.9, distance 20, charge
 * strength -30, gravity strength 0.1, and theta parameter 0.8. The default nodes and links are the empty array, and
 * when the layout is started, the internal alpha cooling parameter is set to 0.1. The general pattern for constructing
 * force-directed layouts is to set all the configuration properties, and then call start: <br>
 * <p>
 * <blockquote>
 *
 * <pre>
 * var force = d3.layout.force() .nodes(nodes) .links(links) .size([w, h])
 * .start();
 * </pre>
 *
 * </blockquote>
 *
 * <br>
 * <p>
 * Note that, like D3's other layouts, the force-directed layout doesn't mandate a particular visual representation.
 * Most commonly, nodes are mapped to SVG circle elements, and links are mapped to SVG line elements. But you might also
 * display nodes as symbols or images.
 *
 * @author Anuj Sharma (primary)
 * @author Vassilis Virvilis (cleanups, documentation)
 * @author Anthony Schiochet (final review, documentation)
 *
 * @param <T> the type of datum of each node
 */
public class Force<T> extends JavaScriptObject {

    /**
     * Type of force event for {@link Force#on(ForceEventType, DatumFunction)} method.
     */
    public static enum ForceEventType {
        START,
        END,
        TICK;
    }

    /**
     * A node in d3j's force layout, see <a
     * href="https://github.com/mbostock/d3/wiki/Force-Layout#nodes">d3 docs on
     * node</a>. The node class is used in {@link Force}, The class provides
     * accessors for the nodes key attributes, its position, previous position,
     * fixed (immovable) and weight (number of links).
     * <p>
     * These attributes do not need to be set before passing the nodes to the layout; if they are not set, suitable
     * defaults will be initialized by the layout when start is called. However, be aware that if you are storing other
     * data on your nodes, your data attributes should not conflict with the above properties used by the layout.
     *
     * @author Vassilis Virvilis
     * @author Anthony Schiochet
     *
     */
    public static class Node<T> extends JavaScriptObject {

        /**
         * Create a new node containing the given user datum
         *
         * @param userDatum
         * @return
         */
        static final native <T> Node<T> create(T userDatum)/*-{
			return {
				datum : userDatum
			};
        }-*/;

        protected Node() {
            super();
        }

        /**
         * @return the zero-based index of the node within the nodes array.
         */
        public final native int index() /*-{
			return this.index;
        }-*/;

        /**
         * @return the computed x-coordinate of the node position.
         */
        public final native double x()/*-{
			return this.x;
        }-*/;

        /**
         * sets the x coordinate
         */
        public final native void x(double x)/*-{
			this.x = x;
        }-*/;

        /**
         * @return the computed y-coordinate of the node position.
         */
        public final native double y()/*-{
			return this.y;
        }-*/;

        /**
         * sets the y coordinate
         */
        public final native void y(double y)/*-{
			this.y = y;
        }-*/;

        /**
         * @return the previous x coordinate
         */
        public final native double px()/*-{
			return this.px;
        }-*/;

        /**
         * sets the previous the x coordinate
         */
        public final native void px(double px)/*-{
			this.px = px;
        }-*/;

        /**
         * @return the previous y coordinate
         */
        public final native double py()/*-{
			return this.py;
        }-*/;

        /**
         * sets the previous y coordinate
         */
        public final native void py(double py)/*-{
			this.py = py;
        }-*/;

        /**
         * @return a boolean indicating whether node position is locked.
         */
        public final native boolean fixed() /*-{
			return this.fixed;
        }-*/;

        /**
         * sets a boolean indicating whether node position is locked.
         */
        public final native void fixed(boolean fixed) /*-{
			this.fixed = fixed;
        }-*/;

        /**
         * @return the node weight; the number of associated links.
         */
        public final native int weight() /*-{
			return this.weight;
        }-*/;

        /**
         * @return the user datum of this node
         */
        public final native T datum()/*-{
			return this.datum;
        }-*/;

        /**
         * @param datum the user datum of this node
         */
        public final native void datum(final T datum)/*-{
			this.datum = datum;
        }-*/;
    }

    /**
     * Each link has the following attributes:
     * <ul>
     * <li>source - the source node (an element in nodes).
     * <li>target - the target node (an element in nodes).
     * </ul>
     *
     * Note: the values of the source and target attributes may be initially specified as indexes into the nodes array;
     * these will be replaced by references after the call to {@link Force#start()}.
     * <p>
     * Link objects may have additional fields that you specify; this data can be used to compute link strength and
     * distance on a per-link basis using an accessor function.
     * <p>
     *
     * @param <T>
     */
    public static class Link<T> extends JavaScriptObject {
        protected Link() {
            super();
        }

        /**
         * Create a basic link object starting at one coordinate and ending at
         * another
         *
         * @param the starting coordinates
         * @param the ending coordinates
         * @return the link object
         */
        public static final native <T> Link<T> create(T source, T target) /*-{
			return {
				source : source,
				target : target
			};
        }-*/;

        /**
         * @return the end node
         */
        public final native Node<T> target() /*-{
			return this.target;
        }-*/;

        /**
         * @return the start node
         */
        public final native Node<T> source() /*-{
			return this.source;
        }-*/;
    }

    protected Force() {
    }

    /**
     * @return the current size, which defaults to 1×1.
     */
    public final native Array<Short> size() /*-{
		return this.size();
    }-*/;

    /**
     * Sets the available layout size.
     * <p>
     * The size affects two aspects of the force-directed layout: the gravitational center, and the initial random
     * position. The center of gravity is simply [ width / 2, height / 2 ]. When nodes are added to the force layout, if
     * they do not have x and y attributes already set, then these attributes are initialized using a uniform random
     * distribution in the range [0, width] and [0, height], respectively.
     *
     * @param size
     *            to set
     * @return the force layout object.
     */
    public final native Force<T> size(Array<Double> size) /*-{
		return this.size(size);
    }-*/;

    /**
     * Sets the available layout size.
     * <p>
     * The size affects two aspects of the force-directed layout: the gravitational center, and the initial random
     * position. The center of gravity is simply [ width / 2, height / 2 ]. When nodes are added to the force layout, if
     * they do not have x and y attributes already set, then these attributes are initialized using a uniform random
     * distribution in the range [0, width] and [0, height], respectively.
     *
     * @param width
     * @param height
     * @return the force layout object.
     */
    public final native Force<T> size(double width, double height) /*-{
		return this.size([ width, height ]);
    }-*/;

    /**
     *
     * @return the layout's current link distance, which defaults to 20.
     */
    public final native double linkDistance() /*-{
		return this.linkDistance();
    }-*/;

    /**
     * Sets the target distance between linked nodes to the specified constant
     * value. All links are the same distance.
     *
     * @param distance
     *            to set
     * @return the force layout object.
     */
    public final native Force<T> linkDistance(double distance) /*-{
		return this.linkDistance(distance);
    }-*/;

    /**
     * Sets the target distance between linked nodes.
     * <p>
     * The given function is evaluated for each link (in order), being passed the link and its index, with the context
     * as the force layout; the function's return value is then used to set each link's distance. The function is
     * evaluated whenever the layout {@link #start()}s.
     *
     * @param callback
     *            function that returns link distance for each link in the
     *            document
     * @return the force layout object.
     */
    public final native Force<T> linkDistance(DatumFunction<Double> callback) /*-{
		try {
			return this
					.linkDistance(function(d, i) {
						try {
							var r = callback.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
							return r;
						} catch (e) {
							return null;
						}
					});
		} catch (e) {
			return null;
		}
    }-*/;

    /**
     * @return the layout's current link strength, which defaults to 1.
     */
    public final native double linkStrength() /*-{
		return this.linkStrength();
    }-*/;

    /**
     * Sets the strength (rigidity) of all links to the specified constant value
     * in the range [0,1].
     *
     * @return the force layout object.
     */
    public final native Force<T> linkStrength(double strength) /*-{
		return this.linkStrength(strength);
    }-*/;

    /**
     * Sets the strength (rigidity) of links in the range [0,1].
     * <p>
     * The given function is evaluated for each link (in order), being passed the link and its index, with the this
     * context as the force layout; the function's return value is then used to set each link's strength. The function
     * is evaluated whenever the layout {@link #start()}s.
     *
     * @return the force layout object.
     */
    public final native Force<T> linkStrength(DatumFunction<Double> callback) /*-{
		try {
			return this
					.linkStrength(function(d, i) {
						try {
							var r = callback.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
							return r;
						} catch (e) {
							return null;
						}
					});
		} catch (e) {
			return null;
		}
    }-*/;

    /**
     * @return the current friction coefficient, which defaults to 0.9.
     */
    public final native double friction() /*-{
		return this.friction();
    }-*/;

    /**
     * Sets the friction coefficient to the specified value. The name of this
     * parameter is perhaps misleading; it does not correspond to a standard
     * physical coefficient of friction. Instead, it more closely approximates
     * velocity decay: at each tick of the simulation, the particle velocity is
     * scaled by the specified friction. Thus, a value of 1 corresponds to a
     * frictionless environment, while a value of 0 freezes all particles in
     * place. Values outside the range [0,1] are not recommended and may have
     * destabilizing effects.
     *
     * @return the force layout object.
     */
    public final native Force<T> friction(double friction) /*-{
		return this.friction(friction);
    }-*/;

    /**
     *
     * @return the current charge strength, which defaults to -30.
     */
    public final native double charge() /*-{
		return this.charge();
    }-*/;

    /**
     * Sets the charge strength to the specified constant value for all nodes. <br>
     * <p>
     * A negative value results in node repulsion, while a positive value results in node attraction. For graph layout,
     * negative values should be used; for n-body simulation, positive values can be used. All nodes are assumed to be
     * infinitesimal points with equal charge and mass. Charge forces are implemented efficiently via the Barnes–Hut
     * algorithm, computing a quadtree for each tick. Setting the charge force to zero disables computation of the
     * quadtree, which can noticeably improve performance if you do not need n-body forces.
     *
     * @return the force layout object.
     */
    public final native Force<T> charge(double x) /*-{
		return this.charge(x);
    }-*/;

    /**
     * Sets sets the charge strength per node.
     * <p>
     * The given function is evaluated for each node (in order), being passed the node and its index, with the this
     * context as the force layout; the function's return value is then used to set each node's charge. The function is
     * evaluated whenever the {@link #start()}s. <br>
     * <p>
     *
     * A negative value results in node repulsion, while a positive value results in node attraction. For graph layout,
     * negative values should be used; for n-body simulation, positive values can be used. All nodes are assumed to be
     * infinitesimal points with equal charge and mass. Charge forces are implemented efficiently via the Barnes–Hut
     * algorithm, computing a quadtree for each tick. Setting the charge force to zero disables computation of the
     * quadtree, which can noticeably improve performance if you do not need n-body forces.
     *
     * @return the force layout object.
     */
    public final native Force<T> charge(DatumFunction<Double> callback) /*-{
		try {
			return this
					.charge(function(d, i) {
						try {

							var r = callback.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
							return r;
						} catch (e) {
							return null;
						}
					});
		} catch (e) {
			return null;
		}
    }-*/;

    /**
     * @return he current maximum charge distance, which defaults to infinity.
     */
    public final native double chargeDistance() /*-{
		return this.chargeDistance();
    }-*/;

    /**
     * Sets the maximum distance over which charge forces are applied to nodes.
     * <p>
     * Specifying a finite charge distance improves the performance of the force layout and produces a more localized
     * layout; distance-limited charge forces are especially useful in conjunction with custom gravity.
     *
     * @return the force layout object.
     */
    public final native Force<T> chargeDistance(double x) /*-{
		return this.chargeDistance(x);
    }-*/;

    /**
     *
     * @return the current theta value, which defaults to 0.8.
     */
    public final native double theta() /*-{
		return this.theta();
    }-*/;

    /**
     * Sets the Barnes–Hut approximation criterion to the specified value.
     * Unlike links, which only affect two linked nodes, the charge force is
     * global: every node affects every other node, even if they are on
     * disconnected subgraphs. <br>
     * <p>
     * To avoid quadratic performance slowdown for large graphs, the force layout uses the Barnes–Hut approximation
     * which takes O(n log n) per tick. For each tick, a quadtree is created to store the current node positions; then
     * for each node, the sum charge force of all other nodes on the given node are computed. For clusters of nodes that
     * are far away, the charge force is approximated by treating the distance cluster of nodes as a single, larger
     * node. Theta determines the accuracy of the computation: if the ratio of the area of a quadrant in the quadtree to
     * the distance between a node to the quadrant's center of mass is less than theta, all nodes in the given quadrant
     * are treated as a single, larger node rather than computed individually.
     *
     * @return the force layout object.
     */
    public final native Force<T> theta(double x) /*-{
		return this.theta(x);
    }-*/;

    /**
     *
     * @return the current gravitational strength, which defaults to 0.1.
     */
    public final native double gravity() /*-{
		return this.gravity();
    }-*/;

    /**
     * Sets the gravitational strength to the specified value. The name of this
     * parameter is perhaps misleading; it does not corresponding to physical
     * gravity (which can be simulated using a positive charge parameter).
     * Instead, gravity is implemented as a weak geometric constraint similar to
     * a virtual spring connecting each node to the center of the layout's size.
     * This approach has nice properties: near the center of the layout, the
     * gravitational strength is almost zero, avoiding any local distortion of
     * the layout; as nodes get pushed farther away from the center, the
     * gravitational strength becomes stronger in linear proportion to the
     * distance. Thus, gravity will always overcome repulsive charge forces at
     * some threshold, preventing disconnected nodes from escaping the layout. * <br>
     * <p>
     *
     * Gravity can be disabled by setting the gravitational strength to zero. If you disable gravity, it is recommended
     * that you implement some other geometric constraint to prevent nodes from escaping the layout, such as
     * constraining them within the layout's bounds.
     *
     * @return the force layout object.
     */
    public final native Force<T> gravity(double x) /*-{
		return this.gravity(x);
    }-*/;

    /**
     *
     * @return the current nods array, which defaults to the empty array.
     */
    public final native Array<Node<T>> nodes() /*-{
		return this.nodes();
    }-*/;

    /**
     * Sets the layout's associated nodes to the specified array.
     * <p>
     * Nodes attributes do not need to be set before passing the nodes to the layout; if they are not set, suitable
     * defaults will be initialized by the layout when start is called. However, be aware that if you are storing other
     * data on your nodes, your data attributes should not conflict with the above properties used by the layout.
     *
     * @return the force layout object.
     */
    public final Force<T> nodes(final List<Node<T>> nodes) {
        return this.nodes(Array.fromIterable(nodes));
    }

    /**
     * Sets the layout's associated nodes to the specified array.
     * <p>
     * Nodes attributes do not need to be set before passing the nodes to the layout; if they are not set, suitable
     * defaults will be initialized by the layout when start is called. However, be aware that if you are storing other
     * data on your nodes, your data attributes should not conflict with the above properties used by the layout.
     *
     * @return the force layout object.
     */
    public final native Force<T> nodes(Array<Node<T>> nodes) /*-{
		return this.nodes(nodes);
    }-*/;

    /**
     *
     * @return the links current array, which defaults to the empty array.
     */
    public final native Array<Link<T>> links() /*-{
		return this.links();
    }-*/;

    /**
     * Sets the layout's associated links to the specified array.
     * <p>
     * Note: the values of the source and target attributes may be initially specified as indexes into the nodes array;
     * these will be replaced by references after the call to start. Link objects may have additional fields that you
     * specify; this data can be used to compute link strength and distance on a per-link basis using an accessor
     * function.
     *
     * @return the force layout object.
     */
    public final native Force<T> links(Array<Link<T>> links) /*-{
		return this.links(links);
    }-*/;

    /**
     * Starts the simulation; this method must be called when the layout is
     * first created, after assigning the nodes and links. In addition, it
     * should be called again whenever the nodes or links change. Internally,
     * the layout uses a cooling parameter alpha which controls the layout
     * temperature: as the physical simulation converges on a stable layout, the
     * temperature drops, causing nodes to move more slowly. Eventually, alpha
     * drops below a threshold and the simulation stops completely, freeing the
     * CPU and avoiding battery drain. The layout can be reheated using {@link #resume()} or by restarting; this happens
     * automatically when using the {@link #drag()} behavior. <br>
     * <p>
     * On start, the layout initializes various attributes on the associated nodes. The index of each node is computed
     * by iterating over the array, starting at zero. The initial x and y coordinates, if not already set externally to
     * a valid number, are computed by examining neighboring nodes: if a linked node already has an initial position in
     * x or y, the corresponding coordinates are applied to the new node. This increases the stability of the graph
     * layout when new nodes are added, rather than using the default which is to initialize the position randomly
     * within the layout's size. The previous px and py position is set to the initial position, if not already set,
     * giving new nodes an initial velocity of zero. Finally, the fixed boolean defaults to false. <br>
     * <p>
     * The layout also initializes the source and target attributes on the associated links: for convenience, these
     * attributes may be specified as a numeric index rather than a direct link, such that the nodes and links can be
     * read-in from a JSON file or other static description that may not allow circular linking. The source and target
     * attributes on incoming links are only replaced with the corresponding entries in nodes if these attributes are
     * numbers; thus, these attributes on existing links are unaffected when the layout is restarted. The
     * {@link #linkDistance()} and {@link #linkStrength()} are also computed on start.
     *
     * @return the force layout object.
     */
    public final native Force<T> start() /*-{
		return this.start();
    }-*/;

    /**
     * @return the current alpha value.
     */
    public final native double alpha() /*-{
		return this.alpha();
    }-*/;

    /**
     * Sets the force layout's cooling parameter, alpha to the constant value
     * specified. If value is greater than zero, this method also restarts the
     * force layout if it is not already running, dispatching a {@link ForceEventType#START} event
     * and enabling the tick timer. If value is nonpositive, and the force
     * layout is running, this method stops the force layout on the next tick
     * and dispatches an {@link ForceEventType#END} event.
     *
     * @return the force layout object.
     */
    public final native Force<T> alpha(double x) /*-{
		return this.alpha(x);
    }-*/;

    /**
     * Equivalent to: <br>
     * <p>
     *
     * <pre>
     * force.alpha(.1);
     * </pre>
     *
     * <br>
     * <p>
     * Sets the cooling parameter alpha to 0.1. This method sets the internal alpha parameter to 0.1, and then restarts
     * the timer. Typically, you don't need to call this method directly; it is called automatically by {@link #start()}
     * . It is also called automatically by {@link #drag()} during a drag gesture.
     *
     * @return the force layout object.
     */
    public final native Force<T> resume() /*-{
		return this.resume();
    }-*/;

    /**
     * Equivalent to: <br>
     * <p>
     *
     * <pre>
     * force.alpha(0);
     * </pre>
     *
     * <br>
     * <p>
     * Terminates the simulation, setting the cooling parameter alpha to zero. This can be used to stop the simulation
     * explicitly, for example, if you want to show animation or allow other interaction. If you do not stop the layout
     * explicitly, it will still stop automatically after the layout's cooling parameter decays below some threshold.
     *
     * @return the force layout object.
     */
    public final native Force<T> stop() /*-{
		return this.stop();
    }-*/;

    /**
     * Runs the force layout simulation one step. This method can be used in
     * conjunction with {@link #start()} and {@link #stop()} to compute a static layout. For example: <br>
     * <p>
     *
     * <pre>
     * force.start();
     * for (var i = 0; i &lt; n; ++i)
     *     force.tick();
     * force.stop();
     * </pre>
     *
     * <br>
     * <p>
     * The number of iterations depends on the graph size and complexity. The choice of initial positions can also have
     * a dramatic impact on how quickly the graph converges on a good solution. For example, here the nodes are arranged
     * along the diagonal: <br>
     * <p>
     *
     * <pre>
     * var n = nodes.length;
     * nodes.forEach(function(d, i) {
     *   d.x = d.y = width / n * i;
     * });
     * </pre>
     *
     * <br>
     * <p>
     * If you do not initialize the positions manually, the force layout will initialize them randomly, resulting in
     * somewhat unpredictable behavior.
     */
    public final native void tick() /*-{
		this.tick();
    }-*/;

    /**
     * Registers the specified listener to receive events of the specified type
     * from the force layout. Currently, only "start", "tick", and "end" events
     * are supported. "tick" events are dispatched for each tick of the
     * simulation. Listen to tick events to update the displayed positions of
     * nodes and links. For example, if you initially display the nodes and
     * links like so: <br>
     * <p>
     *
     * <pre>
     * var link = vis.selectAll(&quot;line&quot;).data(links).enter().append(&quot;line&quot;);
     *
     * var node = vis.selectAll(&quot;circle&quot;).data(nodes).enter().append(&quot;circle&quot;)
     *         .attr(&quot;r&quot;, 5);
     * </pre>
     *
     * <br>
     * <p>
     * You can set their positions on tick: <br>
     * <p>
     *
     * <pre>
     * force.on("tick", function() {
     *   link.attr("x1", function(d) { return d.source.x; })
     *       .attr("y1", function(d) { return d.source.y; })
     *       .attr("x2", function(d) { return d.target.x; })
     *       .attr("y2", function(d) { return d.target.y; });
     *
     *   node.attr("cx", function(d) { return d.x; })
     *       .attr("cy", function(d) { return d.y; });
     * });
     * </pre>
     *
     * <br>
     * <p>
     * In this case, we've stored the selections node and link on initialization, so that we don't need to reselect the
     * nodes on every tick. If you prefer, you can display nodes and links differently; for example, you might use
     * symbols rather than circles. <br>
     * <p>
     * The "end" event is dispatched when the simulations internal alpha cooling parameter reaches zero.
     *
     * @param type the type of event to listen to
     * @param callback
     * @return
     */
    public native final Selection on(ForceEventType type, DatumFunction<?> callback) /*-{
		try {
			var name = type.@com.github.gwtd3.api.layout.Force.ForceEventType::name()()
					.toLowerCase();
			;
			return this
					.on(
							name,
							function(d, i) {
								try {

									var r = callback.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
									return r;
								} catch (e) {
									return null;
								}
							});
		} catch (e) {
			return null;
		}
    }-*/;

    /**
     * Bind a behavior to nodes to allow interactive dragging, either using the
     * mouse or touch. Use this in conjunction with the call operator on the
     * nodes; for example, say node.call(force.drag) on initialization. The drag
     * event sets the fixed attribute of nodes on mouseover, such that as soon
     * as the mouse is over a node, it stops moving. Fixing on mouseover, rather
     * than on mousedown, makes it easier to catch moving nodes. When a
     * mousedown event is received, and on each subsequent mousemove until
     * mouseup, the node center is set to the current mouse position. In
     * addition, each mousemove triggers a {@link #resume()} of the force layout, reheating
     * the simulation. If you want dragged nodes to remain fixed after dragging,
     * set the fixed attribute to true on dragstart, as in the sticky force
     * layout example. <br>
     * <p>
     * Implementation note: the mousemove and mouseup event listeners are registered on the current window, such that
     * when the user starts dragging a node, they can continue to drag the node even if the mouse leaves the window.
     * Each event listener uses the "force" namespace, so as to avoid collision with other event listeners you may wish
     * to bind to nodes or to the window. If a node is moved by the drag behavior, the subsequent click event that would
     * be triggered by the final mouseup is captured and the default behavior prevented. If you register a click event
     * listener, you can ignore these clicks on drag by seeing if the default behavior was prevented: <br>
     * <p>
     *
     * <pre>
     * selection.on("click", function(d) {
     *   if (d3.event.defaultPrevented) return; // ignore drag
     *   otherwiseDoAwesomeThing();
     * });
     * </pre>
     *
     * <br>
     * <p>
     * See the collapsible force layout and divergent forces for examples.
     *
     * @return
     */
    public final native Drag drag() /*-{
		return this.drag();
    }-*/;
}
