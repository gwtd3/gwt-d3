package com.github.gwtd3.api.ease;

import com.google.gwt.core.client.JavaScriptObject;


/**
 * JSNI easing functions.
 * <p>
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 *
 */
public class JavascriptEasingFunction extends JavaScriptObject implements EasingFunction{

	protected JavascriptEasingFunction() {
	}

	/* (non-Javadoc)
	 * @see com.github.gwtd3.api.ease.EasingFunction#ease(double)
	 */
	@Override
	public native final double ease(double t)/*-{
		return this(t);
	}-*/;
}
