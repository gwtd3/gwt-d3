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
package com.github.gwtd3.demo.client.testcases.selection;

import java.util.Arrays;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.functions.KeyFunction;
import com.google.gwt.core.client.JsArrayInteger;
import com.google.gwt.core.client.JsArrayUtils;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.ComplexPanel;

public class TestSelectionData extends AbstractSelectionTest {

	@Override
	public void doTest(final ComplexPanel sandbox) {
		testSelectionDataGetter();
		testSelectionDataSetterArray();
		testSelectionDataSetterFunctionReturningJSO();
		testSelectionDataSetterArrayWithKeyFunction();

		testNestedSelection();
	}

	private final int[][] MATRIX = new int[][] { { 11975, 5871, 8916, 2868 },
			{ 1951, 10048, 2060, 6171 }, { 8010, 16145, 8090, 8045 },
			{ 1013, 990, 940, 6907 } };

	protected Selection givenASimpleSelection() {
		clearSandbox();
		return D3.select(sandbox).append("table").selectAll("tr")
				.data(MATRIX[0]).enter().append("tr");
	}

	/**
	 * Create a nested selection of td elements grouped by tr elements inside a
	 * root table element.
	 * <p>
	 * The td elements are joined with the MATRIX.
	 * 
	 * @return the selection
	 */
	protected Selection givenANestedSelection() {
		clearSandbox();
		Selection tr = D3.select(sandbox).append("table").selectAll("tr")
				.data(MATRIX).enter().append("tr");

		Selection td = tr.selectAll("td")
				.data(new DatumFunction<JsArrayInteger>() {
					@Override
					public JsArrayInteger apply(final Element context,
							final Value d, final int index) {
						int[] as = d.as();
						return JsArrayUtils.readOnlyJsArray(as);
					}
				}).enter().append("td").text(new DatumFunction<String>() {
					@Override
					public String apply(final Element context, final Value d,
							final int index) {
						return d.asString();
					}
				});
		return td;
	}

	/**
	 * 
	 */
	private void testSelectionDataSetterFunctionReturningJSO() {
		Selection selection = givenTrElementsInATable(3);

		selection.data(new DatumFunction<Array<String>>() {
			@Override
			public Array<String> apply(final Element context, final Value d,
					final int index) {
				System.out
						.println("testSelectionDataSetterFunctionReturningPrimitiveArray "
								+ index);
				return Array.fromObjects("0", "1", "2");
			}
		});

		assertDataPropertyEqualsTo("0", selection, 0);
		assertDataPropertyEqualsTo("1", selection, 1);
		assertDataPropertyEqualsTo("2", selection, 2);

	}

	protected Selection givenTrElementsInATable(final int numberOfTRToCreate) {
		clearSandbox();
		Selection table = D3.select(sandbox).append("table");
		for (int i = 0; i < numberOfTRToCreate; i++) {
			table.append("tr");
		}
		Selection selection = table.selectAll("tr");
		assertEquals(numberOfTRToCreate, selection.size());
		return selection;

	}

	/**
	 * 
	 */
	private void testSelectionDataSetterArray() {
		Selection selection = givenTrElementsInATable(3);
		// bytes
		selection.data(new byte[] { 60, 5, 100 });
		assertDataPropertyEqualsTo(60, selection, 0);
		assertDataPropertyEqualsTo(5, selection, 1);
		assertDataPropertyEqualsTo(100, selection, 2);
		// double
		selection.data(new double[] { 61.0, 6.0, 101.0 });
		assertDataPropertyEqualsTo(61, selection, 0);
		assertDataPropertyEqualsTo(6, selection, 1);
		assertDataPropertyEqualsTo(101, selection, 2);

		// float
		selection.data(new float[] { 62.0f, 7.0f, 102.0f });
		assertDataPropertyEqualsTo(62, selection, 0);
		assertDataPropertyEqualsTo(7, selection, 1);
		assertDataPropertyEqualsTo(102, selection, 2);

		// int
		selection.data(new int[] { 63, 8, 103 });
		assertDataPropertyEqualsTo(63, selection, 0);
		assertDataPropertyEqualsTo(8, selection, 1);
		assertDataPropertyEqualsTo(103, selection, 2);

		// long
		// selection.data(new long[] { 64l, 9l, 104l });
		// assertDataPropertyEqualsTo(64, selection, 0);
		// assertDataPropertyEqualsTo(9, selection, 1);
		// assertDataPropertyEqualsTo(104, selection, 2);

		// short
		selection.data(new short[] { 65, 10, 105 });
		assertDataPropertyEqualsTo(65, selection, 0);
		assertDataPropertyEqualsTo(10, selection, 1);
		assertDataPropertyEqualsTo(105, selection, 2);

		// short
		selection.data(new short[] { 66, 11, 106 });
		assertDataPropertyEqualsTo(66, selection, 0);
		assertDataPropertyEqualsTo(11, selection, 1);
		assertDataPropertyEqualsTo(106, selection, 2);

		// Object
		selection.data(new Object[] { "67", "12", "107" });
		assertDataPropertyEqualsTo("67", selection, 0);
		assertDataPropertyEqualsTo("12", selection, 1);
		assertDataPropertyEqualsTo("107", selection, 2);

		// JSO
		selection.data(Array.fromChars('b', 'z', 'g'));
		assertDataPropertyEqualsTo('b', selection, 0);
		assertDataPropertyEqualsTo('z', selection, 1);
		assertDataPropertyEqualsTo('g', selection, 2);

		// List<?>
		selection.data(Arrays.asList(68, 13, 108));
		assertDataPropertyEqualsTo(68, selection, 0);
		assertDataPropertyEqualsTo(13, selection, 1);
		assertDataPropertyEqualsTo(108, selection, 2);
	}

	private void testSelectionDataSetterArrayWithKeyFunction() {
		Selection selection = givenTrElementsInATable(3);
		selection.data(new byte[] { 60, 5, 100 });
		KeyFunction<Integer> func = new KeyFunction<Integer>() {
			@Override
			public Integer map(final Element context,
					final Array<?> newDataArray, final Value datum,
					final int index) {
				return datum.asInt();
			}
		};

		// bytes
		selection.data(new byte[] { 60, 5, 100 }, func);
		assertDataPropertyEqualsTo(60, selection, 0);
		assertDataPropertyEqualsTo(5, selection, 1);
		assertDataPropertyEqualsTo(100, selection, 2);
		// double
		selection.data(new double[] { 5.0, 60.0, 100.0 }, func);
		assertDataPropertyEqualsTo(60, selection, 0);
		assertDataPropertyEqualsTo(5, selection, 1);
		assertDataPropertyEqualsTo(100, selection, 2);

		// float
		selection.data(new float[] { 5.0f, 60.0f, 100.0f }, func);
		assertDataPropertyEqualsTo(60, selection, 0);
		assertDataPropertyEqualsTo(5, selection, 1);
		assertDataPropertyEqualsTo(100, selection, 2);

		// int
		selection.data(new int[] { 60, 5, 100 }, func);
		assertDataPropertyEqualsTo(60, selection, 0);
		assertDataPropertyEqualsTo(5, selection, 1);
		assertDataPropertyEqualsTo(100, selection, 2);

		// long
		selection.data(new long[] { 60l, 5l, 100l }, func);
		assertDataPropertyEqualsTo(60, selection, 0);
		assertDataPropertyEqualsTo(5, selection, 1);
		assertDataPropertyEqualsTo(100, selection, 2);

		// short
		selection.data(new short[] { 60, 5, 100 }, func);
		assertDataPropertyEqualsTo(60, selection, 0);
		assertDataPropertyEqualsTo(5, selection, 1);
		assertDataPropertyEqualsTo(100, selection, 2);

		// short
		selection.data(new short[] { 60, 5, 100 }, func);
		assertDataPropertyEqualsTo(60, selection, 0);
		assertDataPropertyEqualsTo(5, selection, 1);
		assertDataPropertyEqualsTo(100, selection, 2);

		// Object
		selection.data(new Object[] { "67", "12", "107" });
		selection.data(new Object[] { "67", "12", "107" }, func);
		assertDataPropertyEqualsTo("67", selection, 0);
		assertDataPropertyEqualsTo("12", selection, 1);
		assertDataPropertyEqualsTo("107", selection, 2);

		// JSO
		selection.data(Array.fromChars('b', 'z', 'g'));
		selection.data(Array.fromChars('b', 'z', 'g'), func);
		assertDataPropertyEqualsTo('b', selection, 0);
		assertDataPropertyEqualsTo('z', selection, 1);
		assertDataPropertyEqualsTo('g', selection, 2);

		// List<?>
		selection.data(Arrays.asList(67, 13, 108));
		selection.data(Arrays.asList(67, 13, 108), func);
		assertDataPropertyEqualsTo(67, selection, 0);
		assertDataPropertyEqualsTo(13, selection, 1);
		assertDataPropertyEqualsTo(108, selection, 2);
	}

	protected void assertDataPropertyEqualsTo(final int expectedData,
			final Selection selection, final int elementIndex) {
		Array<Array<Element>> array = selection.asElementArray();
		assertEquals(expectedData, array.get(0).get(elementIndex)
				.getPropertyInt(Selection.DATA_PROPERTY));

	}

	protected void assertDataPropertyEqualsTo(final String expectedData,
			final Selection selection, final int elementIndex) {
		Array<Array<Element>> array = selection.asElementArray();
		assertEquals(expectedData, array.get(0).get(elementIndex)
				.getPropertyString(Selection.DATA_PROPERTY));
	}

	/**
	 * 
	 */
	private void testSelectionDataGetter() {
		// given a 2-dim selection
		Selection selection = givenANestedSelection();
		Array<Integer> data = selection.data();
		assertEquals(MATRIX[0][0], (int) data.getNumber(0));
		assertEquals(MATRIX[0][1], (int) data.getNumber(1));
		// with a single group selection
		selection = givenASimpleSelection();
		data = selection.data();
		assertEquals(MATRIX[0][0], (int) data.getNumber(0));
		assertEquals(MATRIX[0][1], (int) data.getNumber(1));

	}

	private void testNestedSelection() {
		clearSandbox();
		Selection tr = D3.select(sandbox).append("table").selectAll("tr")
				.data(MATRIX).enter().append("tr");

		// mapping second level
		System.out.println("creating second level divs");
		Selection td = tr.selectAll("td").data(new DatumFunction<Array<?>>() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * com.github.gwtd3.api.functions.DatumFunction#apply(com.google
			 * .gwt.dom.client.Element, com.github.gwtd3.api.core.Datum, int)
			 */
			@Override
			public Array<?> apply(final Element context, final Value d,
					final int index) {
				System.out.println(context + " " + d.asString() + " " + index);
				int[] as = d.as();
				System.out.println(as);
				return JsArrayUtils.readOnlyJsArray(as).cast();
				// return JsArrays.asJsArray(Arrays.asList("J", "K", "L"));
			}
		}).enter().append("td").text(new DatumFunction<String>() {
			@Override
			public String apply(final Element context, final Value d,
					final int index) {
				return d.asString();
			}
		});

	}
}
