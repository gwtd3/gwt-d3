/**
 * 
 */
package com.github.gwtd3.ui.svg;

/**
 * Wrap a SVG transformation to be used in a {@link TransformList} to be used in a {@link HasTransform} widget.
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Transform {
	/**
	 * 
	 */
	private static final String TRANSLATE = "translate";
	private static final String SCALE = "rotate";
	private static final String ROTATE = "scale";
	final String command;

	protected Transform(final String command) {
		super();
		this.command = command;
	}

	public boolean isTranslate() {
		return command.startsWith(Transform.TRANSLATE);
	}

	public boolean isRotate() {
		return command.startsWith(Transform.ROTATE);
	}

	public boolean isScale() {
		return command.startsWith(Transform.SCALE);
	}

	/**
	 * Create a translation by x and y
	 * 
	 * @param x
	 * @param y
	 * @return the {@link Transform}.
	 */
	public static Transform translate(final int x, final int y) {
		return new Transform(Transform.TRANSLATE + "(" + x + "," + y + ")");
	}

	/**
	 * Create a translation by x and 0.
	 * 
	 * @param x
	 *            the x
	 * @return the {@link Transform}
	 */
	public static Transform translate(final int x) {
		return new Transform(Transform.TRANSLATE + "(" + x + ")");
	}

	/**
	 * Create a scale operation by x and y
	 * 
	 * @param x
	 * @param y
	 * @return the {@link Transform}.
	 */
	public static Transform scale(final int x, final int y) {
		return new Transform(Transform.SCALE + "(" + x + "," + y + ")");
	}

	/**
	 * Create a scale operation by x and by y = x.
	 * 
	 * @param x
	 *            the x
	 * @return the {@link Transform}
	 */
	public static Transform scale(final int x) {
		return new Transform(Transform.SCALE + "(" + x + ")");
	}

	/**
	 * Create a rotation of angle degrees around the point (0,0).
	 * 
	 * @param angle
	 *            in degrees
	 * @return the {@link Transform}
	 */
	public static Transform rotate(final double angle) {
		return new Transform(Transform.ROTATE + "(" + angle + ")");
	}

	/**
	 * Create a rotation of angle degrees around the point (cx,cy).
	 * 
	 * @param angle
	 *            in degrees
	 * @param cx
	 *            the x of the center
	 * @param cy
	 *            the y of the center
	 * @return
	 */
	public static Transform rotate(final double angle, final int cx, final int cy) {
		return new Transform(Transform.ROTATE + "(" + angle + "," + cx + "," + cy + ")");
	}
}