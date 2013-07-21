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
package com.github.gwtd3.api.time;

import java.util.Date;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsDate;

/**
 * Time class performing simple time arithmetic.
 * <p>
 * Intervals are created using {@link D3#time()} factory methods, such as
 * {@link Time#second()}.
 * <p>
 * Note: intervals created by the factory methods of D3().time() are intervals
 * based on local time. Use the {@link Interval#utc()} method to convert the
 * interval to a UTC based interval.
 * 
 * <p>
 * Time intervals are irregular! For example, there are 60 seconds in a minute,
 * but 24 hours in a day. Even more confusing, some days have 23 or 25 hours due
 * to daylight saving time, and the standard Gregorian calendar uses months of
 * differing lengths. And then there are leap years and leap seconds!
 * <p>
 * To simplify manipulation of and iteration over time intervals, D3 provides a
 * handful of time utilities in addition to the time scale and format.
 * <p>
 * The utilities support both local time and UTC time. Local time is determined
 * by the browser's JavaScript runtime; arbitrary time zone support would be
 * nice, but requires access to the Olson zoneinfo files.
 * <p>
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class Interval extends JavaScriptObject {

	protected Interval() {

	}

	// ============= UTC ============================

	/**
	 * Returns a corresponding time interval in UTC rather than local time. For
	 * example, D3().time().day().range(start, stop) returns local time days
	 * between start and stop, while D3().time().day().utc().range(start, stop)
	 * returns UTC days between start and stop.
	 * 
	 * @return the UTC interval
	 */
	public final native Interval utc()/*-{
		return this.utc;
	}-*/;

	// ============= apply ============================

	/**
	 * Alias for {@link #floor(JsDate)}.
	 * 
	 * @param date
	 * @return
	 */
	public final JsDate apply(JsDate date) {
		return floor(date);
	}

	/**
	 * Alias for {@link #floor(Date)}.
	 * 
	 * @param date
	 * @return
	 */
	public final Date apply(Date date) {
		return floor(date);
	}

	/**
	 * Alias for {@link #floor(double)}.
	 * 
	 * @param date
	 * @return
	 */
	public final double apply(double date) {
		return floor(date);
	}

	// =============== floor methods =======================

	/**
	 * Return the latest time interval before or equal to the specified date.
	 * For example, D3.time().day().floor(new Date()) returns midnight (12:00
	 * AM) on the current day, in local time.
	 * 
	 * @param date
	 *            the date to convert
	 * @return the date.
	 */
	public native final JsDate floor(JsDate date)/*-{
		return this.floor(date);
	}-*/;

	/**
	 * Alias for {@link #floor(JsDate)} for a Java date.
	 * 
	 * @param date
	 *            the date to convert
	 * @return the date
	 */
	public final Date floor(Date date) {
		return new Date((long) this.floor(JsDate.create(date.getTime())).getTime());
	};

	/**
	 * Alias for {@link #floor(JsDate)} for a timestamp.
	 * 
	 * @param date
	 * @return the date
	 */
	public final double floor(double date) {
		return this.floor(JsDate.create(date)).getTime();
	};

	// ================== round methods ======================

	/**
	 * Returns the closest time interval to the specified date.
	 * <p>
	 * For example, d3.time().day().round(new Date()) returns midnight (12:00
	 * AM) on the current day if it is on or before noon, and midnight of the
	 * following day if it is after noon.
	 * 
	 * @param date
	 *            the date to convert
	 * @return the date
	 */
	public final native JsDate round(JsDate date)/*-{
		return this.round(date);
	}-*/;

	/**
	 * Alias for {@link #round(JsDate)} for a Java date.
	 * 
	 * @param date
	 *            the date to convert
	 * @return the date
	 */
	public final Date round(Date date) {
		return new Date((long) this.round(JsDate.create(date.getTime())).getTime());
	};

	/**
	 * Alias for {@link #round(JsDate)} for a timestamp.
	 * 
	 * @param date
	 * @return the date
	 */
	public final double round(double date) {
		return this.round(JsDate.create(date)).getTime();
	};

	// ================== ceil methods ======================

	/**
	 * Returns the earliest time interval after the specified date.
	 * <p>
	 * For example, d3.time.day.ceil(new Date()) returns midnight (12:00 AM) on
	 * the following day, in local time.
	 * <p>
	 * 
	 * @param date
	 *            the date to convert
	 * @return the date
	 */
	public final native JsDate ceil(JsDate date)/*-{
		return this.ceil(date);
	}-*/;

	/**
	 * Alias for {@link #ceil(JsDate)} for a Java date.
	 * 
	 * @param date
	 *            the date to convert
	 * @return the date
	 */
	public final Date ceil(Date date) {
		return new Date((long) this.ceil(JsDate.create(date.getTime())).getTime());
	};

	/**
	 * Alias for {@link #round(JsDate)} for a timestamp.
	 * 
	 * @param date
	 * @return the date
	 */
	public final double ceil(double date) {
		return this.ceil(JsDate.create(date)).getTime();
	};

	// ================== offset methds ======================

	/**
	 * Returns a new date equal to date plus step intervals.
	 * <p>
	 * If step is negative, then the returned date will be before the specified
	 * date; if step is zero, then a copy of the specified date is returned.
	 * <p>
	 * This method does not round the specified date to the interval. For
	 * example, if it is currently 5:34 PM, then d3.time.day.offset(new Date(),
	 * 1) returns 5:34 PM tomorrow (even if Daylight Savings Time changes!).
	 * 
	 * @param start
	 *            the start date
	 * @param step
	 *            the number of intervals to add to the start date
	 * @return the computed date
	 */
	public final native JsDate offset(JsDate start, int step)/*-{
		return this.offset(start, step);
	}-*/;

	/**
	 * Alias for {@link #offset(JsDate, int)} for double.
	 * 
	 * @param start
	 *            the start date
	 * @param step
	 *            the number of intervals to add to the start date
	 * @return the computed date
	 */
	public final double offset(double start, int step) {
		return this.offset(JsDate.create(start), step).getTime();
	}

	/**
	 * Alias for {@link #offset(JsDate, int)} for java Date.
	 * 
	 * @param start
	 *            the start date
	 * @param step
	 *            the number of intervals to add to the start date
	 * @return the computed date
	 */
	public final Date offset(Date start, int step) {
		return new Date((long) this.offset(start.getTime(), step));
	}

	// ================== range methods ======================

	/**
	 * Returns every time interval after or equal to start and before stop.
	 * <p>
	 * 
	 * @param start
	 *            the start time
	 * @param stop
	 *            the stop time
	 * @return the interval
	 */
	public final native Array<JsDate> range(JsDate start, JsDate stop)/*-{
		return this.range(start, stop);
	}-*/;

	/**
	 * Alias for {@link #range(JsDate start, JsDate stop)} for java Date.
	 * <p>
	 * 
	 * @param start
	 *            the start time
	 * @param stop
	 *            the stop time
	 * @return the interval
	 */
	public final Array<JsDate> range(Date start, Date stop) {
		return this.range(start.getTime(), stop.getTime());
	}

	/**
	 * Alias for {@link #range(JsDate start, JsDate stop)} for double.
	 * <p>
	 * 
	 * @param start
	 *            the start time
	 * @param stop
	 *            the stop time
	 * @return the interval
	 */
	public final Array<JsDate> range(double start, double stop) {
		return this.range(JsDate.create(start), JsDate.create(stop));
	}

	/**
	 * Returns every time interval after or equal to start and before stop.
	 * <p>
	 * 
	 * @param start
	 *            the start time
	 * @param stop
	 *            the stop time
	 * @return the interval
	 */
	public final native Array<JsDate> range(JsDate start, JsDate stop, double step)/*-{
		return this.range(start, stop, step);
	}-*/;

	/**
	 * Alias for {@link #range(JsDate start, JsDate stop, double step)} for java Date.
	 * <p>
	 * 
	 * @param start
	 *            the start time
	 * @param stop
	 *            the stop time
	 * @return the interval
	 */
	public final Array<JsDate> range(Date start, Date stop, double step) {
		return this.range(start.getTime(), stop.getTime(), step);
	}

	/**
	 * Alias for {@link #range(JsDate start, JsDate stop, double step)} for double.
	 * <p>
	 * 
	 * @param start
	 *            the start time
	 * @param stop
	 *            the stop time
	 * @return the interval
	 */
	public final Array<JsDate> range(double start, double stop, double step) {
		return this.range(JsDate.create(start), JsDate.create(stop), step);
	}

}
