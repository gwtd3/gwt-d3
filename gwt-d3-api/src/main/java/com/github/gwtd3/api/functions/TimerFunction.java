package com.github.gwtd3.api.functions;

import com.github.gwtd3.api.D3;

/**
 * A function to be used with {@link D3#timer()}.
 * <p>
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public interface TimerFunction {

	/**
	 * Return true to stop the timer, false to continue.
	 * 
	 * @return true to stop the timer, false to continue.
	 */
	boolean execute();
}
