package com.github.gwtd3.ui;

import java.util.Stack;

import com.github.gwtd3.api.core.Selection;

/**
 * Helper class for seting the transform attribute of a SVG element.
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Transform {

    private Stack<TransformCommand> transforms = new Stack<TransformCommand>();
    private Selection selection;

    /**
     * Build the transform attribute and return it
     * @return
     */
    public String buildAttribute() {
        String attribute = null;
        if (transforms.size() > 0) {
            attribute = "";
            for (TransformCommand transform : transforms) {
                attribute += transform.command + ",";
            }
            attribute = attribute.substring(0, attribute.length() - 1);
        }
        return attribute;
    }

    public static Transform parse(final String transformAttribute) {
        Transform result = new Transform();
        String[] split = transformAttribute.split(",");
        for (String transform : split) {
            transform = transform == null ? "" : transform.trim();
            if (transform.length() > 0) {
                result.transforms.push(new TransformCommand(transform));
            }
        }
        return result;
    }

    /**
     * Parse the transform attribute of the selection
     * into a stack of {@link TransformCommand}.
     */
    public static Transform parse(final Selection selection) {
        String transformAttribute = selection.attr("transform");
        Transform result = parse(transformAttribute);
        result.selection = selection;
        return result;
    }

    /**
     * Push the given transform command in the stack
     * @param transform
     * @return
     */
    private Transform push(final String transform) {
        transforms.push(new TransformCommand(transform));
        applyTransform();
        return this;
    }

    /**
     * Remove all the
     * @return
     */
    public Transform clear() {
        transforms.clear();
        applyTransform();
        return this;
    }

    /**
     * Set the stack of transforms into the transform
     * attribute of the selection
     */
    private void applyTransform() {
        String attribute = buildAttribute();
        if (selection != null) {
            selection.attr("transform", attribute);
        }
    }

    /**
     * Mini wrapper for a transform command
     * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
     * 
     */
    private static class TransformCommand {
        private String command;

        public TransformCommand(final String command) {
            super();
            this.command = command;
        }

    }

    /**
     * Push a translation of the given x and y
     * in the current selection.
     * 
     * @param x the x
     * @param y the y
     * @return this instance
     */
    public Transform pushTranslate(final int x, final int y) {
        return push(translate(x, y));
    }

    /**
     * Push a translation of the given x
     * in the current selection.
     * 
     * @param x the x
     * @param y the y
     * @return this instance
     */
    public Transform pushTranslate(final int x) {
        return push(translate(x));
    }

    private String translate(final int x, final int y) {
        return "translate(" + x + "," + y + ")";
    }

    private String translate(final int x) {
        return "translate(" + x + ")";
    }

    /**
     * @return true if the transforms does not contains any command
     */
    public boolean isEmpty() {
        return transforms.isEmpty();
    }

}
