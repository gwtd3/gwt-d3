/**
 * Copyright (c) 2013, Anthony Schiochet and Eric Citaire
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * The names Anthony Schiochet and Eric Citaire may not be used to endorse or promote products
 *   derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL MICHAEL BOSTOCK BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY
 * OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
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
