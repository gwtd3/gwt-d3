package com.github.gwtd3.api.svg;

import com.github.gwtd3.api.Coords;
import com.github.gwtd3.api.functions.DatumFunction;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * Provides java access to d3js' SVG diagonals
 * 
 * @author <a href="mailto:evanshi09@gmail.com">Evan Shi</a>
 *
 */
public class Diagonal extends PathDataGenerator{
    protected Diagonal() {
        super();
    }
    
    /**
     * Applies the diagonal function to produce a string describing the path
     * 
     * @param source beginning of diagonal
     * @param target end of diagonal
     * @return resulting string descibing diagonal
     */
    public final native String apply(Coords source, Coords target) /*-{
        return this.call(this, { source : source, target : target });
    }-*/;
    

    /**
     * Registers a callback datum function for diagonal's projection
     * 
     * @param datumFunction the callback
     * @return this diagonal object
     */
    public final native Diagonal projection(DatumFunction<?> datumFunction) /*-{
        return this.projection(
                    function(d, i) {
                           return datumFunction.@com.github.gwtd3.api.functions.DatumFunction::apply(Lcom/google/gwt/dom/client/Element;Lcom/github/gwtd3/api/core/Value;I)(this,{datum:d},i);
                       });
    }-*/;
}
