package com.github.gwtd3.api.dsv;

import com.github.gwtd3.api.core.ObjectAccessor;

/**
 * An accessor function which is passed to {@link Dsv#parse(String, DsvObjectAccessor)}.
 * 
 * @author <a href="mailto:eric.citaire@gmail.com">Eric Citaire</a>
 * 
 * @param <T>
 */
public interface DsvObjectAccessor<T> extends ObjectAccessor<DsvRow, T> {}
