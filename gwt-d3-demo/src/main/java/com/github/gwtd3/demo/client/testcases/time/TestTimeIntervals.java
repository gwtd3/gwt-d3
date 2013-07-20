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
import com.github.gwtd3.api.time.Interval;
import com.github.gwtd3.demo.client.test.AbstractTestCase;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.user.client.ui.ComplexPanel;

public class TestTimeIntervals extends AbstractTestCase {

	@Override
	public void doTest(ComplexPanel sandbox) {
		test(D3.time().year(),      "1979-01-11 09:05:18.125", "1979-01-01 00:00:00.000", "1980-01-01 00:00:00.000", "1979-01-01 00:00:00.000", "1982-01-11 09:05:18.125");
		test(D3.time().month(),     "1979-01-11 09:05:18.125", "1979-01-01 00:00:00.000", "1979-02-01 00:00:00.000", "1979-01-01 00:00:00.000", "1979-04-11 09:05:18.125");
		test(D3.time().hour(),      "1979-01-11 09:05:18.125", "1979-01-11 09:00:00.000", "1979-01-11 10:00:00.000", "1979-01-11 09:00:00.000", "1979-01-11 12:05:18.125");
		test(D3.time().minute(),    "1979-01-11 09:05:18.125", "1979-01-11 09:05:00.000", "1979-01-11 09:06:00.000", "1979-01-11 09:05:00.000", "1979-01-11 09:08:18.125");
		test(D3.time().second(),    "1979-01-11 09:05:18.125", "1979-01-11 09:05:18.000", "1979-01-11 09:05:19.000", "1979-01-11 09:05:18.000", "1979-01-11 09:05:21.125");
		test(D3.time().day(),       "1979-01-11 09:05:18.125", "1979-01-11 00:00:00.000", "1979-01-12 00:00:00.000", "1979-01-11 00:00:00.000", "1979-01-14 09:05:18.125");
		test(D3.time().week(),      "1979-01-11 09:05:18.125", "1979-01-07 00:00:00.000", "1979-01-14 00:00:00.000", "1979-01-14 00:00:00.000", "1979-02-01 09:05:18.125");
		test(D3.time().sunday(),    "1979-01-11 09:05:18.125", "1979-01-07 00:00:00.000", "1979-01-14 00:00:00.000", "1979-01-14 00:00:00.000", "1979-02-01 09:05:18.125");
		test(D3.time().monday(),    "1979-01-11 09:05:18.125", "1979-01-08 00:00:00.000", "1979-01-15 00:00:00.000", "1979-01-08 00:00:00.000", "1979-02-01 09:05:18.125");
		test(D3.time().tuesday(),   "1979-01-11 09:05:18.125", "1979-01-09 00:00:00.000", "1979-01-16 00:00:00.000", "1979-01-09 00:00:00.000", "1979-02-01 09:05:18.125");
		test(D3.time().wednesday(), "1979-01-11 09:05:18.125", "1979-01-10 00:00:00.000", "1979-01-17 00:00:00.000", "1979-01-10 00:00:00.000", "1979-02-01 09:05:18.125");
		test(D3.time().thursday(),  "1979-01-11 09:05:18.125", "1979-01-11 00:00:00.000", "1979-01-18 00:00:00.000", "1979-01-11 00:00:00.000", "1979-02-01 09:05:18.125");
		test(D3.time().friday(),    "1979-01-11 09:05:18.125", "1979-01-05 00:00:00.000", "1979-01-12 00:00:00.000", "1979-01-12 00:00:00.000", "1979-02-01 09:05:18.125");
		test(D3.time().saturday(),  "1979-01-11 09:05:18.125", "1979-01-06 00:00:00.000", "1979-01-13 00:00:00.000", "1979-01-13 00:00:00.000", "1979-02-01 09:05:18.125");
	}
	
	private void test(Interval interval, String givenDateStr, String expectedFloorDateStr, String expectedCeilDateStr, String expectedRoundDateStr, String expectedOffset3DateStr) {
		double givenDoubleDate = JsDate.parse(givenDateStr);
		JsDate givenJsDate = JsDate.create(givenDoubleDate);
		Date givenDate = new Date((long) givenDoubleDate);
		
		double expectedFloorDate = JsDate.parse(expectedFloorDateStr);
		assertDateEquals("apply on double", expectedFloorDate, interval.apply(givenDoubleDate));
		assertDateEquals("apply on JsDate", expectedFloorDate, interval.apply(givenJsDate).getTime());
		assertDateEquals("apply on Date", expectedFloorDate, interval.apply(givenDate).getTime());
		
		assertDateEquals("floor on double", expectedFloorDate, interval.floor(givenDoubleDate));
		assertDateEquals("floor on JsDate", expectedFloorDate, interval.floor(givenJsDate).getTime());
		assertDateEquals("floor on Date", expectedFloorDate, interval.floor(givenDate).getTime());
		
		double expectedCeilDate = JsDate.parse(expectedCeilDateStr);
		assertDateEquals("ceil on double", expectedCeilDate, interval.ceil(givenDoubleDate));
		assertDateEquals("ceil on JsDate", expectedCeilDate, interval.ceil(givenJsDate).getTime());
		assertDateEquals("ceil on Date", expectedCeilDate, interval.ceil(givenDate).getTime());
		
		double expectedRoundDate = JsDate.parse(expectedRoundDateStr);
		assertDateEquals("round on double", expectedRoundDate, interval.round(givenDoubleDate));
		assertDateEquals("round on JsDate", expectedRoundDate, interval.round(givenJsDate).getTime());
		assertDateEquals("round on Date", expectedRoundDate, interval.round(givenDate).getTime());
		
		double expectedOffset3Date = JsDate.parse(expectedOffset3DateStr);
		assertDateEquals("offset 3 on double", expectedOffset3Date, interval.offset(givenDoubleDate, 3));
		assertDateEquals("offset 3 on JsDate", expectedOffset3Date, interval.offset(givenJsDate, 3).getTime());
		assertDateEquals("offset 3 on Date", expectedOffset3Date, interval.offset(givenDate, 3).getTime());
		
		JsDate givenJsStart = givenJsDate;
		JsDate givenJsEnd = JsDate.create(expectedOffset3Date);
		assertEquals("range 3 on double", 3, interval.range(givenJsStart.getTime(), givenJsEnd.getTime()).length());
		assertEquals("range 3 on JsDate", 3, interval.range(givenJsStart, givenJsEnd).length());
		Date givenStart = givenDate;
		Date givenEnd = new Date((long) expectedOffset3Date);
		assertEquals("range 3 on Date", 3, interval.range(givenStart, givenEnd).length());
		
		// FIXME : very strange behaviour on this one
//		assertEquals("range 3 with step on double", 2, interval.range(givenJsStart.getTime(), givenJsEnd.getTime(), 2).length());
//		assertEquals("range 3 with step on JsDate", 2, interval.range(givenJsStart, givenJsEnd, 2).length());
//		assertEquals("range 3 with step on Date", 2, interval.range(givenStart, givenEnd, 2).length());
	}
}
