package com.github.gwtd3.demo.client.assertions;

import org.assertj.core.api.AbstractAssert;
import com.github.gwtd3.demo.client.pageobjects.PageObject;

public class PageObjectAssert<S extends PageObjectAssert<S, A>, A extends PageObject<?>> extends AbstractAssert<S, A> {

	protected PageObjectAssert(A actual, Class<?> selfType) {
		super(actual, selfType);
	}

}
