package com.github.gwtd3.demo.client.testcases.time;

import java.util.Date;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.time.Interval;
import com.github.gwtd3.demo.client.test.AbstractTestCase;

import com.google.gwt.core.client.JsDate;
import com.google.gwt.user.client.ui.ComplexPanel;

public class TestTimeIntervals extends AbstractTestCase {

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
}
