package com.github.gwtd3.ui.model;

public interface DomainFilter<T> {

	public boolean accept(T value);

}
