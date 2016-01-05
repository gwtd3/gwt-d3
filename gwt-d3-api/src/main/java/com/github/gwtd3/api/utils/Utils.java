package com.github.gwtd3.api.utils;

public class Utils {

    /**
     * @param b the Boolean wrapper
     * @return false if b is null of false, true otherwise
     */
    public static boolean toPrimitive(final Boolean b) {
        return b == null ? false : b.booleanValue();
    }
}
