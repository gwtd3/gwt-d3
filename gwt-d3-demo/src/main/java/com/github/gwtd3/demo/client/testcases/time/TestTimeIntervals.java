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
package com.github.gwtd3.demo.client.testcases.time;

import java.util.Date;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.time.Interval;
import com.github.gwtd3.api.time.Time;
import com.github.gwtd3.demo.client.test.AbstractTestCase;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.datepicker.client.CalendarUtil;

public class TestTimeIntervals extends AbstractTestCase {
	
	private static final int TEST_SECOND = 18;
	private static final int TEST_MINUTE = 5;
	private static final int TEST_HOUR = 9;
	private static final int TEST_DATE = 11;
	private static final int TEST_MONTH = 0;
	private static final int TEST_YEAR = 1979;
	private static final int TEST_MILLISECOND = 123;
	
	private enum Field {
		MONTH,
		DAY_OF_MONTH,
		HOUR_OF_DAY,
		MINUTE,
		SECOND,
		MILLISECOND
	};
	private Date date = new Date();

	private static final long SECOND = 1000;// millis
	private static final long MINUTE = 60 * SECOND;// 60 000
	private static final long HOUR = 60 * MINUTE;// 3 600 000
	private static final long DAY = 24 * HOUR;// 86 400 000
	private static final long WEEK = 7 * DAY;//
	private static final long MONTH = 7 * DAY;
	private static final long YEAR = 7 * DAY;

	@Override
	public void doTest(ComplexPanel sandbox) {
		testInterval(SECOND, D3.time().second());
		testInterval(MINUTE, D3.time().minute());
		testInterval(HOUR, D3.time().hour());
		// testIntervalDay(DAY, D3.time().day());
		// testInterval(WEEK, D3.time().week());
		// testInterval(WEEK, D3.time().sunday());
		// testInterval(WEEK, D3.time().monday());
		// testInterval(WEEK, D3.time().tueday());
		// testInterval(WEEK, D3.time().wednesday());
		// testInterval(WEEK, D3.time().thursday());
		// testInterval(WEEK, D3.time().friday());
		// testInterval(WEEK, D3.time().saturday());
		// testInterval(MONTH, D3.time().month());
		// testInterval(YEAR, D3.time().year());
    	
		Time time = D3.time();
		testInterval(time.second(), Field.MILLISECOND);
		testInterval(time.minute(), Field.SECOND);
		testInterval(time.hour(), Field.MINUTE);
		testInterval(time.day(), Field.HOUR_OF_DAY);
		testInterval(time.month(), Field.DAY_OF_MONTH);
		testInterval(time.year(), Field.MONTH);
	}

	private void testIntervalDay(long period, Interval interval) {
		System.out.println("period:" + period);
		long now = new Date().getTime();
		// remove millis
		now = now - (now % 1000);
		// remove hours, min, sec
		Date nowDate = new Date(now);
		nowDate.setSeconds(0);
		nowDate.setMinutes(0);
		nowDate.setHours(0);
		long floor = nowDate.getTime();
		System.out.println(floor);
		long ceil = floor + period;
		System.out.println(ceil);
		long startWillFloor = floor + 490;
		System.out.println(startWillFloor);
		long startWillCeil = floor + (long) (period * 0.7);
		System.out.println(startWillCeil);
		testFloor(interval, startWillFloor, floor);
		testApply(interval, now, floor);
		testCeil(interval, startWillFloor, ceil);
		testRound(interval, startWillFloor, floor);
		testRound(interval, startWillCeil, ceil);
		testOffset(interval, startWillFloor, period);
		testOffset(interval, startWillCeil, period);
		// testRange(interval, floor, startWillFloor, period);
		// testRange(interval, floor, startWillCeil, period);
	}

	private void testInterval(long period, Interval interval) {
		System.out.println("period:" + period);
		long now = new Date().getTime();
		System.out.println(now);
		// 86 400 000
		// 82 800 000
		long floor = now - (now % period);
		System.out.println(floor);
		long ceil = floor + period;
		System.out.println(ceil);
		long startWillFloor = floor + 490;
		System.out.println(startWillFloor);
		long startWillCeil = floor + (long) (period * 0.7);
		System.out.println(startWillCeil);
		testFloor(interval, startWillFloor, floor);
		testApply(interval, now, floor);
		testCeil(interval, startWillFloor, ceil);
		testRound(interval, startWillFloor, floor);
		testRound(interval, startWillCeil, ceil);
		testOffset(interval, startWillFloor, period);
		testOffset(interval, startWillCeil, period);
		// testRange(interval, floor, startWillFloor, period);
		// testRange(interval, floor, startWillCeil, period);
	}

	private void testRange(Interval interval, long base, long start, long period) {
		// init stop = start + a bit less than 5 period more
		double stop = start + (5 * period);
		Array<JsDate> result = interval.range(JsDate.create(start), JsDate.create(stop));
		assertEquals(5, result.length());
		assertEquals((double) base + (1 * period), result.getObject(0).getTime());
		assertEquals((double) base + (2 * period), result.getObject(1).getTime());
		assertEquals((double) base + (3 * period), result.getObject(2).getTime());
		assertEquals((double) base + (4 * period), result.getObject(3).getTime());
		assertEquals((double) base + (5 * period), result.getObject(4).getTime());

		result = interval.range(JsDate.create(start), JsDate.create(stop), 2);
		assertEquals((double) base + (2 * period), result.getObject(0).getTime());
		assertEquals((double) base + (4 * period), result.getObject(1).getTime());
		assertEquals((double) base + (5 * period), result.getObject(2).getTime());
		assertEquals(3, result.length());

	}

	private void testOffset(Interval interval, long start, long period) {
		assertEquals(start + (3 * period), interval.offset(new Date(start), 3).getTime());
		assertEquals(start - (2 * period), interval.offset(new Date(start), -2).getTime());
		assertEquals((double) start + (3 * period), interval.offset(start, 3));
		assertEquals((double) start - (2 * period), interval.offset(start, -2));
		assertEquals((double) start + (3 * period), interval.offset(JsDate.create(start), 3).getTime());
		assertEquals((double) start - (2 * period), interval.offset(JsDate.create(start), -2).getTime());
	}

	private void testCeil(Interval interval, long timestamp, long expected) {
		assertEquals((double) expected, interval.ceil(timestamp));
		assertEquals(expected, interval.ceil(new Date(timestamp)).getTime());
		assertEquals((double) expected, interval.ceil(JsDate.create(timestamp)).getTime());

	}

	private void testRound(Interval interval, long timestamp, long expected) {
		assertEquals((double) expected, interval.round(timestamp));
		assertEquals(expected, interval.round(new Date(timestamp)).getTime());
		assertEquals((double) expected, interval.round(JsDate.create(timestamp)).getTime());
	}

	private void testApply(Interval interval, long timestamp, long expected) {
		assertEquals((double) expected, interval.apply(timestamp));
		assertEquals(expected, interval.apply(new Date(timestamp)).getTime());
		assertEquals((double) expected, interval.apply(JsDate.create(timestamp)).getTime());
	}

	private void testFloor(Interval interval, long timestamp, long expected) {
		assertEquals((double) expected, interval.floor(timestamp));
		assertEquals(expected, interval.floor(new Date(timestamp)).getTime());
		assertEquals((double) expected, interval.floor(JsDate.create(timestamp)).getTime());

	}

	@SuppressWarnings("deprecation")
	private void truncateCalendar(Field field) {
			switch (field) {
			case MONTH:
				date.setMonth(0);
			case DAY_OF_MONTH:
				date.setDate(1);
			case HOUR_OF_DAY:
				date.setHours(0);
			case MINUTE:
				date.setMinutes(0);
			case SECOND:
				date.setSeconds(0);
			case MILLISECOND:
				long t = date.getTime();
				t -= t % 1000;
				date.setTime(t);
				break;
			default:
				throw new IllegalArgumentException("Unsupported field: " + field.name());
			}
	}

	@SuppressWarnings("deprecation")
	private void resetCalendar() {
		date.setYear(TEST_YEAR);
		date.setMonth(TEST_MONTH);
		date.setDate(TEST_DATE);
		date.setHours(TEST_HOUR);
		date.setMinutes(TEST_MINUTE);
		date.setSeconds(TEST_SECOND);
    	long t = date.getTime();
    	t -= t % 1000;
    	date.setTime(t + TEST_MILLISECOND);
	}

	private void testInterval(Interval interval, Field field) {
    	resetCalendar();
    	Date someDate = CalendarUtil.copyDate(date);
    	
    	truncateCalendar(field);
    	Date expectedDate = CalendarUtil.copyDate(date);
    	
		Date actualDate = interval.apply(someDate);
    	
		assertEquals(expectedDate.getTime(), actualDate.getTime());
	}
}
