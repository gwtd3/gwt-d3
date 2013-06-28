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
package com.github.gwtd3.demo.client.testcases.tsv;

import static junit.framework.Assert.*;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.dsv.DsvArrayAccessor;
import com.github.gwtd3.api.dsv.DsvCallback;
import com.github.gwtd3.api.dsv.DsvObjectAccessor;
import com.github.gwtd3.api.dsv.DsvRow;
import com.github.gwtd3.api.dsv.DsvRows;
import com.github.gwtd3.demo.client.test.AbstractTestCase;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.user.client.ui.ComplexPanel;

public class TestTsv extends AbstractTestCase {

	@Override
	public void doTest(final ComplexPanel sandbox) {
		testTsvParse();
		testTsvParseWithAccessor();
		testTsvParseRows();
		testTsvParseRowsWithAccessor();
		testTsvWithAccessorAndCallback();
		testTsvWithCallback();
		testTsvWithChainingAccessorAndCallback();
	}

	private void testTsvParse() {
		DsvRows<DsvRow> rows = D3.<DsvRow> tsv().parse( //
				"Name\tAge\n" + //
						"Paul\t25\n" + //
						"John\t38\n" + //
						"Jane\t15\n" + //
						"Bruce\t48\n" + //
						"Emma\t28\n");
		assertEquals(5, rows.length());
		DsvRow jane = rows.getObject(2);
		assertEquals("Jane", jane.get("Name").asString());
		assertEquals(15, jane.get("Age").asInt());
	}

	private void testTsvParseWithAccessor() {
		DsvRows<Person> rows = D3.<Person> tsv().parse( //
				"Name\tAge\n" + //
						"Paul\t25\n" + //
						"John\t38\n" + //
						"Jane\t15\n" + //
						"Bruce\t48\n" + //
						"Emma\t28\n", new PersonAccessor());
		assertEquals(5, rows.length());
		Person jane = rows.getObject(2);
		assertEquals("Jane", jane.getName());
		assertEquals(15, jane.getAge());
	}

	private void testTsvParseRows() {
		DsvRows<JsArrayString> rows = D3.tsv().parseRows( //
				"Paul\t25\n" + //
						"John\t38\n" + //
						"Jane\t15\n" + //
						"Bruce\t48\n" + //
						"Emma\t28\n");
		assertEquals(5, rows.length());
		JsArrayString jane = rows.getObject(2);
		assertEquals("Jane", jane.get(0));
		assertEquals("15", jane.get(1));
	}

	private void testTsvParseRowsWithAccessor() {
		DsvRows<Person> rows = D3.<Person> tsv().parseRows( //
				"Paul\t25\n" + //
						"John\t38\n" + //
						"Jane\t15\n" + //
						"Bruce\t48\n" + //
						"Emma\t28\n", new PersonArrayAccessor());
		assertEquals(5, rows.length());
		Person jane = rows.getObject(2);
		assertEquals("Jane", jane.getName());
		assertEquals(15, jane.getAge());
	}

	private void testTsvWithAccessorAndCallback() {
		// FIXME : we are not really sure if accessor and callback are actually called
		PersonAccessor accessor = new PersonAccessor();
		PersonCallback callback = new PersonCallback();
		D3.tsv("test-data/test.tsv", accessor, callback);
	}

	private void testTsvWithCallback() {
		// FIXME : we are not really sure if callback is actually called
		PersonRowCallback callback = new PersonRowCallback();
		D3.tsv("test-data/test.tsv", callback);
	}

	private void testTsvWithChainingAccessorAndCallback() {
		// FIXME : we are not really sure if accessor and callback are actually called
		PersonAccessor accessor = new PersonAccessor();
		PersonCallback callback = new PersonCallback();
		D3.<Person> tsv("test-data/test.tsv").row(accessor).get(callback);
	}
}

class Person {
	private final String name;

	private final int age;

	public Person(final String name, final int age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + "]";
	}
}

class PersonAccessor implements DsvObjectAccessor<Person> {
	@Override
	public Person apply(final DsvRow row, final int index) {
		return new Person(row.get("Name").asString(), row.get("Age").asInt());
	}
}

class PersonCallback implements DsvCallback<Person> {
	@Override
	public void get(final JavaScriptObject error, final DsvRows<Person> rows) {
		assertNull(error);
		assertEquals(5, rows.length());
		Person jane = rows.getObject(2);
		assertEquals("Jane", jane.getName());
		assertEquals(15, jane.getAge());
	}
}

class PersonArrayAccessor implements DsvArrayAccessor<Person> {
	@Override
	public Person parse(final JsArrayString row, final int index) {
		return new Person(row.get(0), Integer.parseInt(row.get(1)));
	}
}

class PersonRowCallback implements DsvCallback<DsvRow> {
	@Override
	public void get(final JavaScriptObject error, final DsvRows<DsvRow> rows) {
		assertNull(error);
		assertEquals(5, rows.length());
		DsvRow jane = rows.getObject(2);
		assertEquals("Jane", jane.get("Name").asString());
		assertEquals(15, jane.get("Age").asInt());
	}
}