/**
 * 
 */
package com.github.gwtd3.ui.svg;

import com.google.common.base.Preconditions;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class TransformBuilder extends TransformList {

	private final HasTransform widget;

	/**
	 * 
	 */
	private TransformBuilder(final HasTransform widget) {
		this.widget = widget;
		String transformAttribute = widget.asWidget().getElement().getAttribute("transform");
		parse(this, transformAttribute);
	}

	/**
	 * Parse the transform attribute of the selection
	 * into a stack of {@link Transform}.
	 */
	public static TransformBuilder parse(final HasTransform widget) {
		Preconditions.checkNotNull(widget);
		return new TransformBuilder(widget);
	}

	/**
	 * Set the stack of transforms into the transform
	 * attribute of the selection
	 */
	private void applyTransform() {
		widget.asWidget().getElement().setAttribute("transform", toString());
	}

	/**
	 * Overriden to call {@link #applyTransform()} each time a new transform is pushed.
	 * 
	 * @see com.github.gwtd3.ui.svg.TransformList#push(com.github.gwtd3.ui.svg.Transform)
	 */
	@Override
	public void push(final Transform transform) {
		super.push(transform);
		applyTransform();
	}

	/**
	 * Push a translation by x and y
	 * 
	 * @param x
	 * @param y
	 * @return this instance
	 */
	public TransformBuilder translate(final int x, final int y) {
		push(Transform.translate(x, y));
		return this;

	}

	/**
	 * Push a translation by x and 0.
	 * 
	 * @param x
	 *            the x
	 * @return this instance
	 */
	public TransformBuilder translate(final int x) {
		push(Transform.translate(x));
		return this;
	}

	/**
	 * Push a scale operation by x and y
	 * 
	 * @param x
	 * @param y
	 * @return this instance
	 */
	public TransformBuilder scale(final int x, final int y) {
		push(Transform.scale(x, y));
		return this;

	}

	/**
	 * Push a scale operation by x and by y = x.
	 * 
	 * @param x
	 *            the x
	 * @return this instance
	 */
	public TransformBuilder scale(final int x) {
		push(Transform.scale(x));
		return this;
	}

	/**
	 * Push a rotation of angle degrees around the point (0,0).
	 * 
	 * @param angle
	 *            in degrees
	 * @return this instance
	 */
	public TransformBuilder rotate(final double angle) {
		push(Transform.rotate(angle));
		return this;
	}

	/**
	 * Push a rotation of angle degrees around the point (cx,cy).
	 * 
	 * @param angle
	 *            in degrees
	 * @param cx
	 *            the x of the center
	 * @param cy
	 *            the y of the center
	 * @return
	 */
	public TransformBuilder pushRotate(final double angle, final int cx, final int cy) {
		push(Transform.rotate(angle, cx, cy));
		return this;
	}
}
