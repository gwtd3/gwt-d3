package com.github.gwtd3.ui.svg;

import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.Shared;

/**
 * Style to be applied on a svg document.
 * 
 * @author SCHIOCA
 * 
 */
@Shared
public interface SVGStyles extends CssResource {
    /**
     * The class to apply to the svg element
     * 
     * @return the class name
     */
    public String svg();
}