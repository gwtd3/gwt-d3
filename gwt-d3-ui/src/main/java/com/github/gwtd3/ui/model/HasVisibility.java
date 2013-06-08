package com.github.gwtd3.ui.model;

/**
 * Objects that can define the visiblity of another object.
 * 
 * @author SCHIOCA
 * 
 * @param <T>
 */
public interface HasVisibility<T> {

    public boolean isVisible(T object);
}
