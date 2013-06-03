/**
 * 
 */
package com.github.gwtd3.ui.svg;

import org.vectomatic.dom.svg.impl.SVGGElement;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class GContainer extends SVGContainer implements HasTransform {

    /**
	 * 
	 */
    public GContainer() {
        super("g");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.github.gwtd3.ui.svg.HasTransform#transform()
     */
    @Override
    public TransformBuilder transform() {
        return TransformBuilder.parse(this);
    }

    /**
     * Set the transform attribute of the g element.
     * <p>
     * For convenience, consider using {@link #transform()} method.
     * 
     * @param transform
     */
    public void setTransform(final String transform) {
        getElement().setAttribute("transform", transform);
    }

    public SVGGElement getGElement() {
        return this.getElement().cast();
    }
}
