package com.github.gwtd3.demo.client.testcases.csv;

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

public class TestCsv extends AbstractTestCase {

	@Override
	public void doTest(final ComplexPanel sandbox) {
		testCsvParse();
		testCsvParseWithAccessor();
		testCsvParseRows();
		testCsvParseRowsWithAccessor();
		testCsvWithAccessorAndCallback();
		testCsvWithCallback();
		testCsvWithChainingAccessorAndCallback();
	}

	private void testCsvParse() {
		DsvRows<DsvRow> rows = D3.<DsvRow> csv().parse( //
				"Name,Age\n" + //
						"Paul,25\n" + //
						"John,38\n" + //
						"Jane,15\n" + //
						"Bruce,48\n" + //
						"Emma,28\n");
		assertEquals(5, rows.length());
		DsvRow jane = rows.getObject(2);
		assertEquals("Jane", jane.get("Name").asString());
		assertEquals(15, jane.get("Age").asInt());
	}

	private void testCsvParseWithAccessor() {
		DsvRows<Person> rows = D3.<Person> csv().parse( //
				"Name,Age\n" + //
						"Paul,25\n" + //
						"John,38\n" + //
						"Jane,15\n" + //
						"Bruce,48\n" + //
						"Emma,28\n", new PersonAccessor());
		assertEquals(5, rows.length());
		Person jane = rows.getObject(2);
		assertEquals("Jane", jane.getName());
		assertEquals(15, jane.getAge());
	}

	private void testCsvParseRows() {
		DsvRows<JsArrayString> rows = D3.csv().parseRows( //
				"Paul,25\n" + //
						"John,38\n" + //
						"Jane,15\n" + //
						"Bruce,48\n" + //
						"Emma,28\n");
		assertEquals(5, rows.length());
		JsArrayString jane = rows.getObject(2);
		assertEquals("Jane", jane.get(0));
		assertEquals("15", jane.get(1));
	}

	private void testCsvParseRowsWithAccessor() {
		DsvRows<Person> rows = D3.<Person> csv().parseRows( //
				"Paul,25\n" + //
						"John,38\n" + //
						"Jane,15\n" + //
						"Bruce,48\n" + //
						"Emma,28\n", new PersonArrayAccessor());
		assertEquals(5, rows.length());
		Person jane = rows.getObject(2);
		assertEquals("Jane", jane.getName());
		assertEquals(15, jane.getAge());
	}

	private void testCsvWithAccessorAndCallback() {
		// FIXME : we are not really sure if accessor and callback are actually called
		PersonAccessor accessor = new PersonAccessor();
		PersonCallback callback = new PersonCallback();
		D3.csv("test-data/test.csv", accessor, callback);
	}

	private void testCsvWithCallback() {
		// FIXME : we are not really sure if callback is actually called
		PersonRowCallback callback = new PersonRowCallback();
		D3.csv("test-data/test.csv", callback);
	}

	private void testCsvWithChainingAccessorAndCallback() {
		// FIXME : we are not really sure if accessor and callback are actually called
		PersonAccessor accessor = new PersonAccessor();
		PersonCallback callback = new PersonCallback();
		D3.<Person> csv("test-data/test.csv").row(accessor).get(callback);
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