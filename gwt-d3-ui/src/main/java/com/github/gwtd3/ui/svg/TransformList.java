package com.github.gwtd3.ui.svg;

import java.util.Stack;

/**
 * A Transform .
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class TransformList {

	/**
	 * 
	 */
	private static final String TRANSFORM_SEPARATOR = " ";
	private final Stack<Transform> transforms = new Stack<Transform>();

	/**
	 * Build and return the transform attribute.
	 * 
	 * @return the transform attribute
	 */
	@Override
	public String toString() {
		String attribute = null;
		if (transforms.size() > 0) {
			attribute = "";
			for (Transform transform : transforms) {
				attribute += transform.command + TransformList.TRANSFORM_SEPARATOR;
			}
			attribute = attribute.trim();
		}
		return attribute;
	}

	/**
	 * Parse the transform attribute into {@link Transform} instances
	 * and populate the given {@link TransformList}.
	 * 
	 * @param transformAttribute
	 */
	public static void parse(final TransformList list, final String transformAttribute) {
		String attribute = transformAttribute == null ? "" : transformAttribute.trim();
		String[] split = attribute.split(TransformList.TRANSFORM_SEPARATOR);
		for (String transform : split) {
			transform = transform == null ? "" : transform.trim();
			if (transform.length() > 0) {
				list.transforms.push(new Transform(transform));
			}
		}
	}

	/**
	 * Push the given transform command in the stack
	 * 
	 * @param transform
	 * @return
	 */
	public void push(final Transform transform) {
		transforms.push(transform);
	}

	/**
	 * Remove all the
	 * 
	 * @return
	 */
	public void clear() {
		transforms.clear();
	}

	/**
	 * @return true if the transforms does not contains any command
	 */
	public boolean isEmpty() {
		return transforms.isEmpty();
	}

}
