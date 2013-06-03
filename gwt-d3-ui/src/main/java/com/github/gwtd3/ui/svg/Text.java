/**
 * 
 */
package com.github.gwtd3.ui.svg;

import com.google.gwt.user.client.ui.HasText;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Text extends SVGWidget implements HasText, HasTransform {

    private String text;

    public Text() {
        this("");
    }

    public Text(final String text) {
        super("text");
        this.text = text;
    }

    /**
     * @param text
     *            the text to set
     */
    @Override
    public void setText(final String text) {
        this.text = text;
        scheduleRedraw();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gwt.user.client.ui.HasText#getText()
     */
    @Override
    public String getText() {
        return text;
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
     * @param x
     */
    public void x(final String x) {
        this.getElement().setAttribute("x", x);
    }

    /**
     * @param y
     */
    public void y(final String y) {
        this.getElement().setAttribute("y", y);
    }

    /**
     * @param dx
     */
    public void dx(final String dx) {
        this.getElement().setAttribute("dx", dx);
    }

    /**
     * @param dy
     */
    public void dy(final String dy) {
        this.getElement().setAttribute("dy", dy);
    }

    @Override
    public void redraw() {
        super.redraw();

        select().text(text);
    }

}
