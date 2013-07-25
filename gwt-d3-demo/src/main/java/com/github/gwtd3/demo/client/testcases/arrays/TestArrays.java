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
package com.github.gwtd3.demo.client.testcases.arrays;

import java.util.Arrays;
import java.util.List;

import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.arrays.ArrayList;
import com.github.gwtd3.api.arrays.ForEachCallback;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.demo.client.test.AbstractTestCase;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.ComplexPanel;

/**
 * Test native array functions.
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class TestArrays extends AbstractTestCase {

	@Override
	public void doTest(final ComplexPanel sandbox) {
		testCreate();
		testLength();
		testGet();
		testSet();
		testIndexOf();
		testLastIndexOf();
		testPush();
		testReverse();
		testShift();
		testSlice();
		testSort();
		testSplice();
		testUnshift();
		testJoin();
		testPop();

		testForEach();
		testEvery();
		testSome();

		testMap();

		testFilter();

	}

	private void testCreate() {
	}

	private void testMap() {
		Array<Integer> fruits = Array.fromInts(5, 20, 31);
		Array<String> result = fruits.map(new ForEachCallback<String>() {
			@Override
			public String forEach(final Object thisArg, final Value element, final int index, final Array<?> array) {
				return Integer.toHexString(element.asInt());
			}
		});
		assertEquals(3, result.length());
		assertEquals("5", result.getString(0));
		assertEquals("14", result.getString(1));
		assertEquals("1f", result.getString(2));
	}

	private void testSome() {
		Array<Integer> fruits = Array.fromInts(5, 20, 72);
		assertTrue(fruits.some(Callbacks.greaterThan(70)));
		assertFalse(fruits.some(Callbacks.greaterThan(73)));
	}

	private void testEvery() {
		Array<Integer> fruits = Array.fromInts(5, 20, 72);
		assertFalse(fruits.every(Callbacks.greaterThan(10)));
		assertTrue(fruits.every(Callbacks.greaterThan(4)));
	}

	private void testUnshift() {
		Array<String> fruits = Array.fromObjects("Banana", "Orange", "Apple");
		assertEquals(4, fruits.unshift("Lemon"));
		assertEquals("Lemon,Banana,Orange,Apple", fruits.join());

		fruits = Array.fromObjects("Banana", "Orange", "Apple");
		assertEquals(4, fruits.unshift(true));
		assertEquals("true,Banana,Orange,Apple", fruits.join());

		fruits = Array.fromObjects("Banana", "Orange", "Apple");
		assertEquals(4, fruits.unshift(5.6));
		assertEquals("5.6,Banana,Orange,Apple", fruits.join());
	}

	private void testSplice() {
		Array<String> fruits = Array.fromObjects("Banana", "Orange", "Apple");
		Array<String> removed = fruits.splice(1, 1);
		assertEquals("Banana,Apple", fruits.join());
		assertEquals("Orange", removed.join());

		fruits = Array.fromObjects("Banana", "Orange", "Apple");
		removed = fruits.splice(-1, 1);
		assertEquals("Banana,Orange", fruits.join());
		assertEquals("Apple", removed.join());

		fruits = Array.fromObjects("Banana", "Orange", "Apple");
		removed = fruits.splice(1, 2, "Lemon");
		assertEquals("Banana,Lemon", fruits.join());
		assertEquals("Orange,Apple", removed.join());

		fruits = Array.fromObjects("Banana", "Orange", "Apple");
		removed = fruits.splice(1, 0, "Lemon", "Juice");
		assertEquals("Banana,Lemon,Juice,Orange,Apple", fruits.join());
		assertEquals("", removed.join());
	}

	private void testSort() {
		assertEquals("Apple,Banana,Orange", Array.fromObjects("Banana", "Orange", "Apple").sortAlphaAsc().join());
		assertEquals("Orange,Banana,Apple", Array.fromObjects("Banana", "Orange", "Apple").sortAlphaDesc().join());
		assertEquals("23,50,9", Array.fromInts(50, 9, 23).sortAlphaAsc().join());
		assertEquals("9,50,23", Array.fromInts(50, 9, 23).sortAlphaDesc().join());
		assertEquals("9,23,50", Array.fromInts(50, 9, 23).sortNumericAsc().join());
		assertEquals("50,23,9", Array.fromInts(50, 9, 23).sortNumericDesc().join());
	}

	private void testSlice() {
		Array<String> fruits = Array.create(Arrays.asList("Banana", "Orange", "Apple", "Mango", "Orange", "Lemon"));
		Array<String> slicedFruits = fruits.slice(4);
		assertEquals("Orange", slicedFruits.getString(0));
		assertEquals("Lemon", slicedFruits.getString(1));
		slicedFruits = fruits.slice(3, 5);
		assertEquals("Mango", slicedFruits.getString(0));
		assertEquals("Orange", slicedFruits.getString(1));
	}

	private void testShift() {
		Array<?> fruits = Array.fromObjects("Banana", true, 5.6);
		assertEquals("Banana", fruits.shiftString());
		assertEquals(true, fruits.shiftBoolean());
		assertEquals(5.6, fruits.shiftNumber());
	}

	private void testReverse() {
		Array<String> fruits = Array.create(Arrays.asList("Banana", "Orange", "Apple"));
		Array<String> fruits2 = fruits.reverse();
		assertEquals("Apple", fruits2.getString(0));
		assertEquals("Orange", fruits2.getString(1));
		assertEquals("Banana", fruits2.getString(2));
	}

	private void testPush() {
		Array<String> fruits = Array.create(Arrays.asList("Banana", "Orange", "Apple", "Mango", "Orange", "Lemon"));
		assertEquals(7, fruits.push("Other"));
		assertEquals("Other", fruits.getString(6));
		assertEquals(8, fruits.push(true));
		assertEquals(true, fruits.getBoolean(7));
		assertEquals(9, fruits.push(5.6));
		assertEquals(5.6, fruits.getNumber(8));
		JavaScriptObject obj = JavaScriptObject.createObject();
		assertEquals(10, fruits.push(obj));
		assertEquals(obj, fruits.getObject(9));
	}

	private void testSet() {
		List<String> fruits = new ArrayList<String>(Array.create(Arrays.asList("Banana", "Orange", "Apple", "Mango", "Orange", "Lemon")));
		fruits.set(0, "Other");
		assertEquals(6, fruits.size());
		assertEquals("Other", fruits.get(0));
	}

	private void testLength() {
		Array<String> fruits = Array.create(Arrays.asList("Banana", "Orange", "Apple", "Mango", "Orange", "Lemon"));
		assertEquals(6, fruits.length());
		fruits.setLength(8);
		assertEquals(8, fruits.length());
		fruits.setLength(2);
		assertEquals(2, fruits.length());
	}

	private void testPop() {
		Array<String> fruits = Array.create(Arrays.asList("Banana", "Orange", "Apple", "Mango", "Orange", "Lemon"));
		assertEquals(6, fruits.length());
		assertEquals("Lemon", fruits.pop());
		assertEquals(5, fruits.length());
	}

	private void testGet() {
		JavaScriptObject obj = JavaScriptObject.createObject();
		Array<?> fruits = Array.fromObjects(true, 5.6, "Apple", obj);
		assertEquals(true, fruits.getBoolean(0));
		assertEquals(5.6, fruits.getNumber(1));
		assertEquals("Apple", fruits.getString(2));
		assertEquals(obj, fruits.getObject(3));
		assertEquals(obj, fruits.getObject(3));
	}

	private void testJoin() {
		Array<String> fruits = Array.create(Arrays.asList("Banana", "Orange", "Apple", "Mango", "Orange", "Lemon"));
		assertEquals("Banana,Orange,Apple,Mango,Orange,Lemon", fruits.join());
		assertEquals("Banana;Orange;Apple;Mango;Orange;Lemon", fruits.join(";"));
	}

	private void testLastIndexOf() {
		Array<String> fruits = Array.create(Arrays.asList("Banana", "Orange", "Apple", "Mango", "Orange", "Lemon"));
		assertEquals(4, fruits.lastIndexOf("Orange"));
		assertEquals(-1, fruits.lastIndexOf("blah"));
		assertEquals(1, fruits.lastIndexOf("Orange", 2));
		assertEquals(-1, fruits.lastIndexOf("blah", 2));
		assertEquals(1, fruits.lastIndexOf("Orange", -3));
	}

	private void testIndexOf() {
		Array<String> fruits = Array.create(Arrays.asList("Banana", "Orange", "Apple", "Mango", "Orange", "Lemon"));

		assertEquals(1, fruits.indexOf("Orange"));
		assertEquals(-1, fruits.indexOf("blah"));
		assertEquals(4, fruits.indexOf("Orange", 2));
		assertEquals(-1, fruits.indexOf("blah", 2));
		assertEquals(4, fruits.indexOf("Orange", -3));
	}

	private void testFilter() {
		Array<Integer> array = Array.fromInts(5, 20, 72);

		Array<Integer> filteredArray = array.filter(Callbacks.greaterThan(10));
		assertNotNull(filteredArray);
		assertEquals(2, filteredArray.length());
		assertEquals(20, filteredArray.getValue(0).asInt());
		assertEquals(72, filteredArray.getValue(1).asInt());
	}

	private void testForEach() {
		Array<Person> array = personArray();

		ForEachCallback<Void> callback = new ForEachCallback<Void>() {
			@Override
			public Void forEach(final Object thisArg, final Value element, final int index, final Array<?> array) {
				Person person = element.as(Person.class);
				person.setAge(person.getAge() + 1);
				return null;
			}
		};
		array.forEach(callback);

		Person jane = array.getObject(0);
		Person peter = array.getObject(1);
		Person sam = array.getObject(2);

		assertEquals(26, jane.getAge());
		assertEquals(37, peter.getAge());
		assertEquals(54, sam.getAge());
	}

	private static final native Array<Person> personArray() /*-{
		var jane = {
			name : 'Jane',
			age : 25
		};
		var peter = {
			name : 'Peter',
			age : 36
		};
		var sam = {
			name : 'Sam',
			age : 53
		};
		return [ jane, peter, sam ];
	}-*/;

}

class Person extends JavaScriptObject {
	protected Person() {
		super();
	}

	public final native void setName(String name) /*-{
		this.name = name;
	}-*/;

	public final native String getName() /*-{
		return this.name;
	}-*/;

	public final native void setAge(int age) /*-{
		this.age = age;
	}-*/;

	public final native int getAge() /*-{
		return this.age;
	}-*/;
}
