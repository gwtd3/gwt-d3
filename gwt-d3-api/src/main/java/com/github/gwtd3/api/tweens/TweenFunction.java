package com.github.gwtd3.api.tweens;

import com.github.gwtd3.api.core.Datum;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.interpolators.Interpolator;

import com.google.gwt.dom.client.Element;

/**
 * A tween function used.
 * 
 * @see <a href="https://github.com/mbostock/d3/wiki/Transitions#wiki-attrTween">Official attrTween function</a>
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public interface TweenFunction<T> {

    Interpolator<T> apply(Element context, Datum datum, int index, Value attributeValue);
}
