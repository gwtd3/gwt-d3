package com.github.gwtd3.demo.client.testcases.arrays;

import java.util.Arrays;
import java.util.List;

import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.arrays.ArrayList;
import com.github.gwtd3.demo.client.test.AbstractTestCase;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.ComplexPanel;

/**
 * Test the {@link Array}'s {@link List} implementation.
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class TestArrayList extends AbstractTestCase {

	@Override
	public void doTest(final ComplexPanel sandbox) {

		testAdd();
		testClear();
		testContains();
		testEquals();
		testGet();
		testIndexOf();
		testIsEmpty();
		testIterator();
		testLastIndexOf();
		testRemove();
		testSet();
		testSize();

		// FIXME
		testListIterator();
		testRetain();
		testToArray();
		testToString();
		testSublist();

	}

	private void testSublist() {
	}

	private void testToString() {
	}

	private void testToArray() {
	}

	private void testRetain() {
	}

	private void testListIterator() {

	}

	private void testRemove() {

		Array<Person> array = personArray();
		Person p1 = array.getObject(0);
		Person p2 = array.getObject(1);
		Person p3 = array.getObject(2);

		//
		ArrayList<Person> list = new ArrayList<Person>(array);
		assertEquals(3, list.size());
		list.remove(2);
		assertEquals(2, list.size());
		list.remove(0);
		assertEquals(1, list.size());
		list.remove(0);
		assertEquals(0, list.size());

		//
		list = new ArrayList<Person>(array);
		assertEquals(3, list.size());
		list.remove(p1);
		assertEquals(2, list.size());
		list.remove(p2);
		assertEquals(1, list.size());
		list.remove(p3);
		assertEquals(0, list.size());
	}

	private void testIterator() {
		ArrayList<Person> list = new ArrayList<Person>(personArray());
		int i = 0;
		for (Person person : list) {
			assertEquals(list.get(i), person);
			i++;
		}
	}

	private void testSize() {
		assertEquals(0, new ArrayList<Person>(Array.<Person> create()).size());
		assertEquals(3, new ArrayList<Person>(personArray()).size());

	}

	private void testIsEmpty() {
		assertTrue(new ArrayList<Person>(Array.<Person> create()).isEmpty());
		assertFalse(new ArrayList<Person>(personArray()).isEmpty());
	}

	private void testEquals() {
		Array<Person> nonempty = personArray();
		Array<Person> empty = Array.<Person> create();
		List<Person> list1 = new ArrayList<Person>(nonempty);
		List<Person> list2 = new ArrayList<Person>(nonempty);
		List<Person> list3 = new ArrayList<Person>(empty);
		assertTrue(list1.equals(list2));

		assertFalse(list1.equals(list3));
	}

	private void testLastIndexOf() {
		List<String> fruits =
				new ArrayList<String>(Array.<String> create(Arrays.asList("Banana", "Orange", "Apple", "Mango",
						"Orange",
						"Lemon")));
		assertEquals(4, fruits.lastIndexOf("Orange"));
		assertEquals(-1, fruits.lastIndexOf("blah"));
	}

	private void testIndexOf() {
		List<String> fruits =
				new ArrayList<String>(Array.<String> create(Arrays.asList("Banana", "Orange", "Apple", "Mango",
						"Orange",
						"Lemon")));
		assertEquals(1, fruits.indexOf("Orange"));
		assertEquals(-1, fruits.indexOf("blah"));
	}

	private void testContains() {
		List<Person> list = new ArrayList<Person>(Array.<Person> create());
		Person person = Person.createObject().<Person> cast().setAge(77);
		list.add(person);
		assertTrue(list.contains(person));
		assertFalse(list.contains(Person.createObject().<Person> cast()));
	}

	private void testClear() {
		Array<Person> array = personArray();
		List<Person> list = new ArrayList<Person>(array);
		assertEquals(3, list.size());
		list.clear();
		assertEquals(0, list.size());
	}

	private void testAdd() {
		List<Person> list = new ArrayList<Person>(Array.<Person> create());
		list.add(Person.createObject().<Person> cast().setAge(77));
		assertEquals(1, list.size());
		assertEquals(77, list.get(0).getAge());

	}

	private void testSet() {
		List<String> fruits = new ArrayList<String>(
				Array.create(Arrays.asList("Banana", "Orange", "Apple", "Mango", "Orange", "Lemon"))
				);
		fruits.set(0, "Other");
		assertEquals(6, fruits.size());
		assertEquals("Other", fruits.get(0));
	}

	private void testGet() {
		Array<String> fruits = Array.create("blah", "blih", "bloh", "bluh");
		List<String> list = new ArrayList<String>(fruits);
		assertEquals("blah", list.get(0));
		assertEquals("blih", list.get(1));
		assertEquals("bloh", list.get(2));
		assertEquals("blauh", list.get(3));
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

	static class Person extends JavaScriptObject {
		protected Person() {
			super();
		}

		public final native Person setName(String name) /*-{
			this.name = name;
			return this;
		}-*/;

		public final native String getName() /*-{
			return this.name;
		}-*/;

		public final native Person setAge(int age) /*-{
			this.age = age;
			return this;
		}-*/;

		public final native int getAge() /*-{
			return this.age;
		}-*/;

	}
}
