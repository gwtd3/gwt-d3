package com.github.gwtd3.demo.client;

public abstract class Factory {

	public abstract DemoCase newInstance();

	public String id() {
		String name = this.getClass().getName();
		name = name.substring(name.lastIndexOf('.') + 1);
		// inner class
		if (name.contains("$")) {
			name = name.substring(0, name.lastIndexOf("$"));
		}
		return name;
	}
}
