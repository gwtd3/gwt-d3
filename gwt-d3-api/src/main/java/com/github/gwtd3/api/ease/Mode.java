package com.github.gwtd3.api.ease;

public enum Mode {

	/**
	 * The identity function
	 */
	IN("in"),

	/**
	 * reverses the easing direction [1,0]
	 */
	OUT("out"),

	/**
	 * copies and mirrors the easing function from [0,.5] and [.5,1].
	 */
	IN_OUT("in-out"),

	/**
	 * copies and mirrors the easing function from [1,.5] and [.5,0].
	 */
	OUT_IN("out-in");

	private String value;

	private Mode(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
