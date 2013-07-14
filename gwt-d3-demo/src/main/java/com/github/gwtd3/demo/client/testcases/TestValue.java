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
package com.github.gwtd3.demo.client.testcases;

import com.github.gwtd3.api.Coords;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.demo.client.test.AbstractTestCase;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ComplexPanel;

public class TestValue extends AbstractTestCase {

	@Override
	public void doTest(final ComplexPanel sandbox) {
		// typeof
		assertEquals("function", getFunction().typeof());
		assertEquals("number", getInt().typeof());
		assertEquals("object", getNewNumber().typeof());
		assertEquals("number", getDecimal().typeof());
		assertEquals("number", getInfinityMinus().typeof());
		assertEquals("number", getInfinityPlus().typeof());
		assertEquals("number", getZero().typeof());
		assertEquals("string", getStringWithInt().typeof());// should we ?
		assertEquals("number", getNaN().typeof());
		assertEquals("string", getStringEmpty().typeof());
		assertEquals("object", getNewStringObject().typeof());
		assertEquals("string", getString().typeof());
		assertEquals("string", getStringWithFalse().typeof());
		assertEquals("string", getStringWithTrue().typeof());
		assertEquals("object", getNull().typeof());
		assertEquals("undefined", getUndefined().typeof());
		assertEquals("boolean", getTrue().typeof());
		assertEquals("boolean", getFalse().typeof());
		assertEquals("object", getNewBooleanTrue().typeof());
		assertEquals("object", getNewBooleanFalse().typeof());

		// is boolean
		assertTrue(getTrue().isBoolean());
		assertTrue(getFalse().isBoolean());
		assertTrue(getNewBooleanTrue().isBoolean());
		assertTrue(getNewBooleanFalse().isBoolean());
		assertTrue(getZero().isBoolean());
		assertTrue(getStringEmpty().isBoolean());

		assertFalse(getString().isBoolean());
		assertFalse(getStringWithFalse().isBoolean());
		assertFalse(getStringWithTrue().isBoolean());

		assertFalse(getInt().isBoolean());
		assertFalse(getNewNumber().isBoolean());
		assertFalse(getDecimal().isBoolean());
		assertFalse(getInfinityMinus().isBoolean());
		assertFalse(getInfinityPlus().isBoolean());
		assertFalse(getStringWithInt().isBoolean());// should we ?
		assertFalse(getNaN().isBoolean());

		assertFalse(getFunction().isBoolean());
		assertFalse(getNull().isBoolean());
		assertFalse(getUndefined().isBoolean());

		// is number
		// assertTrue(getInt().isNumber());
		// assertTrue(getNewNumber().isNumber());
		// assertTrue(getDecimal().isNumber());
		// assertTrue(getInfinityMinus().isNumber());
		// assertTrue(getInfinityPlus().isNumber());
		// assertTrue(getZero().isNumber());
		// assertTrue(getStringWithInt().isNumber());// should we ?
		// assertFalse(getNaN().isNumber());
		// assertFalse(getStringEmpty().isNumber());
		// assertFalse(getFunction().isNumber());
		// assertFalse(getString().isNumber());
		// assertFalse(getStringWithFalse().isNumber());
		// assertFalse(getStringWithTrue().isNumber());
		// assertFalse(getNull().isNumber());
		// assertFalse(getUndefined().isNumber());
		// assertFalse(getTrue().isNumber());
		// assertFalse(getFalse().isNumber());
		// assertTrue(getNewBooleanTrue().isNumber());
		// assertTrue(getNewBooleanFalse().isNumber());

		// is String
		assertFalse(getFunction().isString());
		assertFalse(getInt().isString());
		assertFalse(getNewNumber().isString());
		assertFalse(getDecimal().isString());
		assertFalse(getInfinityMinus().isString());
		assertFalse(getInfinityPlus().isString());
		assertFalse(getZero().isString());
		assertTrue(getStringWithInt().isString());// should we ?
		assertFalse(getNaN().isString());
		assertTrue(getStringEmpty().isString());
		assertTrue(getNewStringObject().isString());
		assertTrue(getString().isString());
		assertTrue(getStringWithFalse().isString());
		assertTrue(getStringWithTrue().isString());
		assertFalse(getNull().isString());
		assertFalse(getUndefined().isString());
		assertFalse(getTrue().isString());
		assertFalse(getFalse().isString());
		assertFalse(getNewBooleanTrue().isString());
		assertFalse(getNewBooleanFalse().isString());

		// is function
		assertTrue(getFunction().isFunction());
		assertFalse(getInt().isFunction());
		assertFalse(getNewNumber().isFunction());
		assertFalse(getDecimal().isFunction());
		assertFalse(getInfinityMinus().isFunction());
		assertFalse(getInfinityPlus().isFunction());
		assertFalse(getZero().isFunction());
		assertFalse(getStringWithInt().isFunction());// should we ?
		assertFalse(getNaN().isFunction());
		assertFalse(getStringEmpty().isFunction());
		assertFalse(getString().isFunction());
		assertFalse(getStringWithFalse().isFunction());
		assertFalse(getStringWithTrue().isFunction());
		assertFalse(getNull().isFunction());
		assertFalse(getUndefined().isFunction());
		assertFalse(getTrue().isFunction());
		assertFalse(getFalse().isFunction());
		assertFalse(getNewBooleanTrue().isFunction());
		assertFalse(getNewBooleanFalse().isFunction());

		// casting from number
		Value value = getNewNumber();
		assertEquals(55, value.asInt());
		assertEquals(55, value.asByte());
		assertEquals(55F, value.asFloat());
		assertEquals(55, value.asLong());
		assertEquals(55.0, value.asDouble());
		assertEquals(55, value.asChar());
		assertEquals(55, value.asShort());
		assertEquals(true, value.asBoolean());
		assertEquals("55", value.asString());
		assertEquals(55D, value.asJsDate().getTime());

		value = getInt();
		assertEquals(5, value.asInt());
		assertEquals(5, value.asByte());
		assertEquals(5F, value.asFloat());
		assertEquals(5, value.asLong());
		assertEquals(5.0, value.asDouble());
		assertEquals(5, value.asChar());
		assertEquals(5, value.asShort());
		assertEquals(true, value.asBoolean());
		assertEquals("5", value.asString());
		assertEquals(5D, value.asJsDate().getTime());

		value = getZero();
		assertEquals(0, value.asInt());
		assertEquals(0, value.asByte());
		assertEquals(0F, value.asFloat());
		assertEquals(0, value.asLong());
		assertEquals(0D, value.asDouble());
		assertEquals(0, value.asChar());
		assertEquals(0, value.asShort());
		assertEquals(false, value.asBoolean());
		assertEquals("0", value.asString());
		assertEquals(0D, value.asJsDate().getTime());

		value = getNaN();
		assertEquals(0, value.asInt());
		assertEquals(0, value.asByte());
		assertTrue(Float.isNaN(value.asFloat()));
		assertEquals(0, value.asLong());
		assertTrue(Double.isNaN(value.asDouble()));
		assertEquals(0, value.asChar());
		assertEquals(0, value.asShort());
		assertEquals(false, value.asBoolean());
		assertEquals("NaN", value.asString());
		assertTrue(Double.isNaN(value.asJsDate().getTime()));

		value = getInfinityPlus();
		// int n = (int) Double.POSITIVE_INFINITY;
		// assertEquals(n, value.asInt());
		// assertEquals(n, value.asByte());
		assertEquals(Float.POSITIVE_INFINITY, value.asFloat());
		assertEquals(Long.MAX_VALUE, value.asLong());
		assertEquals(Double.POSITIVE_INFINITY, value.asDouble());
		// assertEquals(n, value.asChar());
		// assertEquals(n, value.asShort());
		assertEquals(true, value.asBoolean());
		assertEquals("Infinity", value.asString());
		assertTrue(Double.isNaN(value.asJsDate().getTime()));

		value = getDecimal();
		int n = (int) value.asDouble();
		assertEquals(n, value.asInt());
		assertEquals(n, value.asByte());
		assertEquals(12.5F, value.asFloat());
		assertEquals(12L, value.asLong());
		assertEquals(12.5D, value.asDouble());
		assertEquals(n, value.asChar());
		assertEquals(n, value.asShort());
		assertEquals(true, value.asBoolean());
		assertEquals("12.5", value.asString());
		assertEquals(12.0D, value.asJsDate().getTime());

		// casting from null
		value = getNull();
		assertEquals(0, value.asInt());
		assertEquals(0, value.asByte());
		assertEquals(0F, value.asFloat());
		assertEquals(0L, value.asLong());
		assertEquals(0D, value.asDouble());
		assertEquals(0, value.asChar());
		assertEquals(0, value.asShort());
		assertEquals(false, value.asBoolean());
		assertEquals(null, value.asString());
		assertEquals(0D, value.asJsDate().getTime());

		// casting from undefined
		value = getUndefined();
		assertEquals(0, value.asInt());
		assertEquals(0, value.asByte());
		assertTrue(Float.isNaN(value.asFloat()));
		assertEquals(0L, value.asLong());
		assertTrue(Double.isNaN(value.asDouble()));
		assertEquals(0, value.asChar());
		assertEquals(0, value.asShort());
		assertEquals(false, value.asBoolean());
		assertEquals(null, value.asString());
		assertTrue(Double.isNaN(value.asJsDate().getTime()));

		// casting from boolean
		value = getFalse();
		assertEquals(0, value.asInt());
		assertEquals(0, value.asByte());
		assertEquals(0F, value.asFloat());
		assertEquals(0L, value.asLong());
		assertEquals(0D, value.asDouble());
		assertEquals(0, value.asChar());
		assertEquals(0, value.asShort());
		assertEquals(false, value.asBoolean());
		assertEquals("false", value.asString());
		assertEquals(0D, value.asJsDate().getTime());

		value = getNewBooleanFalse();
		assertEquals(0, value.asInt());
		assertEquals(0, value.asByte());
		assertEquals(0F, value.asFloat());
		assertEquals(0L, value.asLong());
		assertEquals(0D, value.asDouble());
		assertEquals(0, value.asChar());
		assertEquals(0, value.asShort());
		assertEquals(false, value.asBoolean());
		assertEquals("false", value.asString());
		assertEquals(0D, value.asJsDate().getTime());

		value = getTrue();
		assertEquals(1, value.asInt());
		assertEquals(1, value.asByte());
		assertEquals(1F, value.asFloat());
		assertEquals(1L, value.asLong());
		assertEquals(1D, value.asDouble());
		assertEquals(1, value.asChar());
		assertEquals(1, value.asShort());
		assertEquals(true, value.asBoolean());
		assertEquals("true", value.asString());
		assertEquals(1D, value.asJsDate().getTime());

		value = getNewBooleanTrue();
		assertEquals(1, value.asInt());
		assertEquals(1, value.asByte());
		assertEquals(1F, value.asFloat());
		assertEquals(1L, value.asLong());
		assertEquals(1D, value.asDouble());
		assertEquals(1, value.asChar());
		assertEquals(1, value.asShort());
		assertEquals(true, value.asBoolean());
		assertEquals("true", value.asString());
		assertEquals(1D, value.asJsDate().getTime());

		// casting from string
		value = getStringWithInt();
		assertEquals(5, value.asInt());
		assertEquals(5, value.asByte());
		assertEquals(5F, value.asFloat());
		assertEquals(5, value.asLong());
		assertEquals(5D, value.asDouble());
		assertEquals(5, value.asChar());
		assertEquals(5, value.asShort());
		assertEquals(true, value.asBoolean());
		assertEquals("5", value.asString());
		// FF: ok, Chrome: ko
		// see : https://code.google.com/p/v8/issues/detail?id=2602
		// assertTrue(Double.isNaN(value.asJsDate().getTime()));

		value = getStringWithDate();
		assertEquals(0, value.asInt());
		assertEquals(0, value.asByte());
		assertTrue(Float.isNaN(value.asFloat()));
		assertEquals(0, value.asLong());
		assertTrue(Double.isNaN(value.asDouble()));
		assertEquals(0, value.asChar());
		assertEquals(0, value.asShort());
		assertEquals(true, value.asBoolean());
		assertEquals("October 13, 1975 11:13:00", value.asString());
		// Chrome bug
		// see https://code.google.com/p/chromium/issues/detail?id=5704
		// assertEquals(182423580000D, value.asJsDate().getTime());

		value = getStringEmpty();
		assertEquals(0, value.asInt());
		assertEquals(0, value.asByte());
		assertEquals(0F, value.asFloat());
		assertEquals(0, value.asLong());
		assertEquals(0D, value.asDouble());
		assertEquals(0, value.asChar());
		assertEquals(0, value.asShort());
		assertEquals(false, value.asBoolean());
		assertEquals("", value.asString());
		assertTrue(Double.isNaN(value.asJsDate().getTime()));

		value = getString();
		assertEquals(0, value.asInt());
		assertEquals(0, value.asByte());
		assertTrue(Float.isNaN(value.asFloat()));
		assertEquals(0, value.asLong());
		assertTrue(Double.isNaN(value.asDouble()));
		assertEquals(0, value.asChar());
		assertEquals(0, value.asShort());
		assertEquals(true, value.asBoolean());
		assertEquals("foobar", value.asString());
		assertTrue(Double.isNaN(value.asJsDate().getTime()));

		value = getStringWithFalse();
		assertEquals(0, value.asInt());
		assertEquals(0, value.asByte());
		assertTrue(Float.isNaN(value.asFloat()));
		assertEquals(0L, value.asLong());
		assertTrue(Double.isNaN(value.asDouble()));
		assertEquals(0, value.asChar());
		assertEquals(0, value.asShort());
		assertEquals(true, value.asBoolean());
		assertEquals("false", value.asString());
		assertTrue(Double.isNaN(value.asJsDate().getTime()));

		value = getStringWithTrue();
		assertEquals(0, value.asInt());
		assertEquals(0, value.asByte());
		assertTrue(Float.isNaN(value.asFloat()));
		assertEquals(0L, value.asLong());
		assertTrue(Double.isNaN(value.asDouble()));
		assertEquals(0, value.asChar());
		assertEquals(0, value.asShort());
		assertEquals(true, value.asBoolean());
		assertEquals("true", value.asString());
		assertTrue(Double.isNaN(value.asJsDate().getTime()));

		// casting from function
		value = getFunction();
		assertEquals(0, value.asInt());
		assertEquals(0, value.asByte());
		assertTrue(Float.isNaN(value.asFloat()));
		assertEquals(0L, value.asLong());
		assertTrue(Double.isNaN(value.asDouble()));
		assertEquals(0, value.asChar());
		assertEquals(0, value.asShort());
		assertEquals(true, value.asBoolean());
		assertTrue(value.asString().contains("function"));
		assertTrue(Double.isNaN(value.asJsDate().getTime()));

		// object wrapper
		value = Value.create(getCoords(10, 12));
		assertTrue(value.getProperty("x").isDefined());
		assertEquals(10, value.getProperty("x").asInt());
		assertEquals(12, value.getProperty("y").asInt());
		assertTrue(value.getProperty("fake").isUndefined());
		assertFalse(value.getProperty("x").isBoolean());
		assertFalse(value.getProperty("x").isString());
		assertFalse(value.getProperty("x").isFunction());

	}

	private static final Coords getCoords(final int x, final int y) {
		return Coords.create(x, y);
	}

	private boolean die(final int num) {
		Window.alert("line " + num);
		return true;
	}

	private static final native boolean isFinite(JsDate date) /*-{
		return isFinite(date);
	}-*/;

	// strings
	private final native Value getStringWithInt()/*-{
		return {
			datum : '5'
		};
	}-*/;

	private final native Value getStringEmpty()/*-{
		return {
			datum : ''
		};
	}-*/;

	private final native Value getString()/*-{
		return {
			datum : 'foobar'
		};
	}-*/;

	private final native Value getNewStringObject()/*-{
		return {
			datum : new String('foobar')
		};
	}-*/;

	private final native Value getStringWithTrue()/*-{
		return {
			datum : 'true'
		};
	}-*/;

	private final native Value getStringWithFalse()/*-{
		return {
			datum : 'false'
		};
	}-*/;

	private final native Value getStringWithDate()/*-{
		return {
			datum : "October 13, 1975 11:13:00"
		};
	}-*/;

	// function
	private final native Value getFunction()/*-{
		return {
			datum : function() {
				return;
			}
		};
	}-*/;

	// numbers
	private final native Value getInt()/*-{
		return {
			datum : 5
		};
	}-*/;

	private final native Value getZero()/*-{
		return {
			datum : 0
		};
	}-*/;

	private final native Value getDecimal()/*-{
		return {
			datum : 12.5
		};
	}-*/;

	private final native Value getInfinityPlus()/*-{
		return {
			datum : Number.POSITIVE_INFINITY
		};
	}-*/;

	private final native Value getInfinityMinus()/*-{
		return {
			datum : Number.NEGATIVE_INFINITY
		};
	}-*/;

	private final native Value getNaN()/*-{
		return {
			datum : Number.NaN
		};
	}-*/;

	private final native Value getNewNumber()/*-{
		return {
			datum : new Number(55)
		};
	}-*/;

	// null
	private final native Value getNull()/*-{
		return {
			datum : null
		};
	}-*/;

	// undefined
	private final native Value getUndefined()/*-{
		return {
			datum : undefined
		};
	}-*/;

	// boolean
	private final native Value getTrue()/*-{
		return {
			datum : true
		};
	}-*/;

	private final native Value getFalse()/*-{
		return {
			datum : false
		};
	}-*/;

	private final native Value getNewBooleanTrue()/*-{
		return {
			datum : new Boolean(true)
		};
	}-*/;

	private final native Value getNewBooleanFalse()/*-{
		return {
			datum : new Boolean(false)
		};
	}-*/;

	// various objects
	private final native Value getDate()/*-{
		return {
			datum : new Date()
		};
	}-*/;
}
