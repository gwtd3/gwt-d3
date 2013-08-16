/**
 * Copyright (c) 2013, Anthony Schiochet and Eric Citaire
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * The names Anthony Schiochet and Eric Citaire may not be used to endorse or promote products
 *   derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL MICHAEL BOSTOCK BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY
 * OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.github.gwtd3.demo.client.testcases.svg;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.svg.Symbol;
import com.github.gwtd3.api.svg.Symbol.Type;
import com.github.gwtd3.demo.client.test.AbstractTestCase;
import com.google.gwt.dom.client.Element;

public class TestSymbol extends AbstractTestCase {

	/**
	 * Return the list of supported symbol types, which match the
	 * {@link Type#values()}.
	 * <p>
	 * 
	 * @return the list of supported symbol types
	 */
	public static final native Array<String> symbolTypes()/*-{
		return $wnd.d3.svg.symbolTypes;
	}-*/;

	@Override
	public void doTest(final com.google.gwt.user.client.ui.ComplexPanel sandbox) {
		// check the symbol types match the Symbol.Type.values
		Array<String> types = symbolTypes();
		for (int i = 0; i < types.length(); i++) {
			String type = types.getString(i);
			System.out.println("SYMBOL TYPE " + type);
			Type typeEnum = Symbol.Type.valueOf(type.toUpperCase().replace('-',
					'_'));
			if (typeEnum == null) {
				fail("the symbol type " + type + " is not implemented");
			}
		}

		Symbol symbol = D3.svg().symbol();
		symbol.size(32);
		symbol.size(new DatumFunction<Integer>() {
			@Override
			public Integer apply(Element context, Value d, int index) {
				return index;
			}
		});

		symbol.type(Type.CIRCLE);
		symbol.type(new DatumFunction<Type>() {
			@Override
			public Type apply(Element context, Value d, int index) {
				return Type.CIRCLE;
			}
		});
	}

}
