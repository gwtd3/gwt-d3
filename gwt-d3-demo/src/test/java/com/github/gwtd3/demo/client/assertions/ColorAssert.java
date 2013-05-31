package com.github.gwtd3.demo.client.assertions;

import org.assertj.core.api.AbstractAssert;
import org.openqa.selenium.support.Color;

/**
 * Convenient assertions for a {@link Color}
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class ColorAssert extends AbstractAssert<ColorAssert, Color> {

	public ColorAssert(Color actual, Class<?> selfType) {
		super(actual, selfType);
	}

	public ColorAssert isEqualTo(String expected) {
		return super.isEqualTo(Color.fromString(expected));
	}

}
