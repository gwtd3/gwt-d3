package com.github.gwtd3.api.layout;

import com.github.gwtd3.api.Size;
import com.github.gwtd3.api.Sort;
import com.github.gwtd3.api.arrays.Array;
import com.google.gwt.core.client.JavaScriptObject;

public class TreeLayout
    extends Layout<TreeLayout> {
    protected TreeLayout() {
        super();
    }

    public final native TreeLayout separation(JavaScriptObject f) /*-{
                                                                     return this.separation(f);
                                                                     }-*/;

    public final native TreeLayout size(Size c) /*-{
                                                              return this.size(c);
                                                              }-*/;

    public final native TreeLayout nodeSize(Size s) /*-{
                                                                return this.nodeSize(s);
                                                                }-*/;

    public final native TreeLayout sort(Sort sort) /*-{
                                                          return this.sort(sort);
                                                          }-*/;

    public final native TreeLayout children(JavaScriptObject f) /*-{
                                                                       return this.children(f);
                                                                       }-*/;

    public final native Array<Node> nodes(Node r) /*-{
                                                     return this.nodes(r);
                                                     }-*/;

    public final native Array<Link> links(Array<Node> n) /*-{
                                                              return this.links(n);
                                                              }-*/;

    public final native TreeLayout value(JavaScriptObject f) /*-{
                                                                    return this.value(f);
                                                                    }-*/;
}
