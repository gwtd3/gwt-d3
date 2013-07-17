package com.github.gwtd3.api.svg;

import com.github.gwtd3.api.functions.DatumFunction;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayNumber;

public class Diagonal extends PathDataGenerator{
    protected Diagonal() {
        super();
    }
    
    public final native Diagonal projection(JavaScriptObject proj) /*-{
        return this.projection(proj);
    }-*/;

    public final native Diagonal projection(DatumFunction<?> datumFunction) /*-{
        return this.projection(
                    function(d, i) {
                           return datumFunction.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
                       });
    }-*/;
}
