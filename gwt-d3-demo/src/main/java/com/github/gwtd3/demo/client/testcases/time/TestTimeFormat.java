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
import com.github.gwtd3.api.time.Time;
import com.github.gwtd3.api.time.TimeFormat;
import com.github.gwtd3.demo.client.test.AbstractTestCase;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.user.client.ui.ComplexPanel;

public class TestTimeFormat extends AbstractTestCase {
	
//	private static final int[] FIELDS = {
//		Calendar.MONTH,
//		Calendar.DAY_OF_MONTH,
//		Calendar.HOUR_OF_DAY,
//		Calendar.MINUTE,
//		Calendar.SECOND,
//		Calendar.MILLISECOND
//	};
	
	private static final int TEST_SECOND = 18;
	private static final int TEST_MINUTE = 5;
	private static final int TEST_HOUR = 9;
	private static final int TEST_DATE = 11;
	private static final int TEST_MONTH = 0;
	private static final int TEST_YEAR = 1979;
//	private Calendar calendar = Calendar.getInstance();
	
	

    @Override
    public void doTest(final ComplexPanel sandbox) {
        parse();
        format();
        utc();
        iso();
        intervals();
    }

    private void intervals() {
    	Time time = D3.time();
//		testInterval(time.second(), 5);
//		testInterval(time.minute(), 4);
//		testInterval(time.hour(), 3);
//		testInterval(time.day(), 2);
//		testInterval(time.month(), 1);
//		testInterval(time.year(), 0);
		
		// TODO: still some work to do...
	}

	private native static final int getTimeZoneOffset(double time) /*-{
		var d = new Date(time);
		return d.getTimezoneOffset();
    }-*/;

    private void parse() {
        TimeFormat format = D3.time().format("%b %Y");
        JsDate date = format.parse("Feb 2000");

        assertEquals(2000, date.getFullYear());
        assertEquals(1, date.getMonth());
        assertEquals(1, date.getDate());
        assertEquals(0, date.getHours());
        assertEquals(0, date.getMinutes());
        assertEquals(0, date.getSeconds());
        assertEquals(0, date.getMilliseconds());
    }

    private void format() {
        int tzOffset = getTimeZoneOffset(0);
        TimeFormat format = D3.time().format("%Y-%m-%d %H:%M:%S");

        JsDate jsDate = JsDate.create(tzOffset * 60 * 1000);
        String strWithJsDate = format.apply(jsDate);

        Date date = new Date(tzOffset * 60 * 1000);
        String strWithDate = format.apply(date);

        assertEquals("1970-01-01 00:00:00", strWithJsDate);
        assertEquals("1970-01-01 00:00:00", strWithDate);
    }

    private void utc() {
        TimeFormat utcFormat = D3.time().format().utc("%Y-%m-%d %H:%M:%S");
        Date date = new Date(0);
        String str = utcFormat.apply(date);

        assertEquals("1970-01-01 00:00:00", str);
    }

    private void iso() {
        TimeFormat isoFormat = D3.time().format().iso();
        Date date = new Date(0);
        String str = isoFormat.apply(date);

        String expected = D3.time().format().utc("%Y-%m-%dT%H:%M:%S.%LZ").apply(date);
        assertEquals(expected, str);
    }

//	private void resetCalendar() {
//		calendar.set(TEST_YEAR, TEST_MONTH, TEST_DATE, TEST_HOUR, TEST_MINUTE, TEST_SECOND);
//    	calendar.set(Calendar.MILLISECOND, 123);
//	}
//
//	private void truncateCalendar(int field) {
//		for (int i = field; i < FIELDS.length; i++) {
//			switch (FIELDS[i]) {
//			case Calendar.MONTH:
//			case Calendar.HOUR_OF_DAY:
//			case Calendar.MINUTE:
//			case Calendar.SECOND:
//			case Calendar.MILLISECOND:
//				calendar.set(FIELDS[i], 0);
//				break;
//			case Calendar.DAY_OF_MONTH:
//				calendar.set(FIELDS[i], 1);
//				break;
//			default:
//				throw new IllegalArgumentException("Unsupported field: " + FIELDS[i]);
//			}
//		}
//	}
//
//	private void testInterval(Interval interval, int field) {
//    	resetCalendar();
//    	Date someDate = calendar.getTime();
//    	
//    	truncateCalendar(field);
//    	Date expectedDate = calendar.getTime();
//    	
//		Date actualDate = interval.apply(someDate);
//    	
//		assertEquals(expectedDate, actualDate);
//	}
}
