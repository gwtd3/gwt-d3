package com.github.gwtd3.ui.model;

import java.util.List;

/**
 * Interface for objects able to provide a list of values T.
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 * @param <T>
 *            the type of produced value
 */
public interface ValueProvider<T> {

	List<T> getValues();
}
