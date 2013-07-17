package com.github.gwtd3.api.layout;

import com.github.gwtd3.api.Sort;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

/*
 * Abstract class HierarchalLayout superclasses layouts such as
 * Trees, Clusters, Packs, Partitions, and Treemaps. Subclasses
 * should override HierarchalLayout's methods if they want something
 * other than default behavior of their layout. Otherwise,
 * HierarchalLayout will default to D3's built-in behavior.
 */

public class HierarchalLayout
    extends Layout<HierarchalLayout> {
    protected HierarchalLayout() {
        super();
    }

    public static native HierarchalLayout sort(Sort sort) /*-{
                                                          return this.sort(sort);
                                                          }-*/;

    public static native HierarchalLayout children(JavaScriptObject f) /*-{
                                                                       return this.children(f);
                                                                       }-*/;

    public static native JsArray<Node> nodes(Node r) /*-{
                                                     return this.nodes(r);
                                                     }-*/;

    public static native JsArray<Link> links(JsArray<Node> n) /*-{
                                                              return this.links(n);
                                                              }-*/;

    public static native HierarchalLayout value(JavaScriptObject f) /*-{
                                                                    return this.value(f);
                                                                    }-*/;
}
