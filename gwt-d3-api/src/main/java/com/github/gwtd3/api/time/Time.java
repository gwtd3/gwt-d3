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

import com.google.gwt.core.client.JavaScriptObject;

/**
 * D3 includes a helper module for parsing and formatting dates modeled after the venerable <a
 * href="http://pubs.opengroup.org/onlinepubs/009695399/functions/strptime.html">strptime</a> and <a
 * href="http://pubs.opengroup.org/onlinepubs/007908799/xsh/strftime.html">strftime</a> C-library standards. These
 * functions are also notably available in Python's <a href="http://docs.python.org/library/time.html">time</a> module.
 * 
 * @author <a href="mailto:eric.citaire@gmail.com">Eric Citaire</a>
 * 
 */
public class Time extends JavaScriptObject {

    protected Time() {
        super();
    }

    /**
     * Constructs a new local time formatter using the given <i>specifier</i>. The specifier string may contain the
     * following directives.
     * <ul>
     * <li> <code>%a</code> - abbreviated weekday name.
     * <li> <code>%A</code> - full weekday name.
     * <li> <code>%b</code> - abbreviated month name.
     * <li> <code>%B</code> - full month name.
     * <li> <code>%c</code> - date and time, as "%a %b %e %H:%M:%S %Y".
     * <li> <code>%d</code> - zero-padded day of the month as a decimal number [01,31].
     * <li> <code>%e</code> - space-padded day of the month as a decimal number [ 1,31]; equivalent to %_d.
     * <li> <code>%H</code> - hour (24-hour clock) as a decimal number [00,23].
     * <li> <code>%I</code> - hour (12-hour clock) as a decimal number [01,12].
     * <li> <code>%j</code> - day of the year as a decimal number [001,366].
     * <li> <code>%m</code> - month as a decimal number [01,12].
     * <li> <code>%M</code> - minute as a decimal number [00,59].
     * <li> <code>%p</code> - either AM or PM.
     * <li> <code>%S</code> - second as a decimal number [00,61].
     * <li> <code>%U</code> - week number of the year (Sunday as the first day of the week) as a decimal number [00,53].
     * <li> <code>%w</code> - weekday as a decimal number [0(Sunday),6].
     * <li> <code>%W</code> - week number of the year (Monday as the first day of the week) as a decimal number [00,53].
     * <li> <code>%x</code> - date, as "%m/%d/%y".
     * <li> <code>%X</code> - time, as "%H:%M:%S".
     * <li> <code>%y</code> - year without century as a decimal number [00,99].
     * <li> <code>%Y</code> - year with century as a decimal number.
     * <li> <code>%Z</code> - time zone offset, such as "-0700".
     * <li> <code>%%</code> - a literal "%" character.
     * </ul>
     * For %U, all days in a new year preceding the first Sunday are considered to be in week 0. For %W, all days in a
     * new year preceding the first Monday are considered to be in week 0. In some implementations of strftime and
     * strptime (as in Python), a directive may include an optional field width or precision; this feature is not yet
     * implemented in D3, but may be added in the future. Also note that the JavaScript environment does not provide a
     * standard API for accessing locale-specific date and time formatters, so D3's implementation is fixed to a locale
     * at compile time based on the $LOCALE environment variable.
     * <p>
     * The % sign indicating a directive may be immediately followed by a padding modifier:
     * <p>
     * <ul>
     * <li> <code>0</code> - zero-padding
     * <li> <code>_</code> - space-padding
     * <li> <code>-</code> - disable padding
     * </ul>
     * <p>
     * If no padding modifier is specified, the default is <code>0</code> for all directives, except for <code>%e</code>
     * which defaults to <code>_</code>).
     * 
     * @see <a href="https://github.com/mbostock/d3/wiki/Time-Formatting#wiki-format">Official API documentation</a>
     * 
     * @param specifier
     *            the specifier string.
     * @return the formatted string.
     */
    public final native TimeFormat format(String specifier) /*-{
		return this.format(specifier);
    }-*/;

    /**
     * Create a TimeFormat builder.
     * 
     * @return the builter.
     */
    public final native TimeFormat.Builder format() /*-{
		return this.format;
    }-*/;

    /**
     * Construct a linear time scale.
     * 
     * @return time scale
     */
    public final native TimeScale scale()/*-{
		return this.scale();
    }-*/;

    // ================== Intervals ====================

    /**
     * Factory for interval of Seconds (e.g., 01:23:45.0000 AM). Always 1,000
     * milliseconds long.
     * <p>
     * 
     * @return the {@link Interval}
     */
    public final native Interval second()/*-{
		return this.second;
    }-*/;

    /**
     * Factory for interval of Minutes (e.g., 01:02:00 AM). Most browsers do not
     * support leap seconds, so minutes are almost always 60 seconds (6e4
     * milliseconds) long.
     * 
     * <p>
     * 
     * @return the {@link Interval}
     */
    public final native Interval minute()/*-{
		return this.minute;
    }-*/;

    /**
     * Factory for interval of Hours (e.g., 01:00 AM). 60 minutes long (36e5
     * milliseconds). Note that advancing time by one hour can return the same
     * hour number, or skip an hour number, due to Daylight Savings Time.
     * <p>
     * 
     * @return the {@link Interval}
     */
    public final native Interval hour()/*-{
		return this.hour;
    }-*/;

    /**
     * Days (e.g., February 7, 2012 at 12:00 AM). Most days are 24 hours long
     * (864e5 milliseconds); however, with Daylight Savings Time, a day may be
     * 23 or 25 hours long.
     * 
     * <p>
     * 
     * @return the {@link Interval}
     */
    public final native Interval day()/*-{
		return this.day;
    }-*/;

    /**
     * Alias for {@link #sunday}. A week is always 7 days, but ranges between
     * 167 and 169 hours depending on Daylight Savings Time.
     * <p>
     * 
     * @return the {@link Interval}
     */
    public final native Interval week()/*-{
		return this.week;
    }-*/;

    /**
     * Sunday-based weeks (e.g., February 5, 2012 at 12:00 AM).
     * <p>
     * 
     * @return the {@link Interval}
     */
    public final native Interval sunday()/*-{
		return this.sunday;
    }-*/;

    /**
     * Monday-based weeks (e.g., February 6, 2012 at 12:00 AM).
     * <p>
     * 
     * @return the {@link Interval}
     */
    public final native Interval monday()/*-{
		return this.monday;
    }-*/;

    /**
     * Tuesday-based weeks (e.g., February 7, 2012 at 12:00 AM).
     * <p>
     * 
     * @return the {@link Interval}
     */
    public final native Interval tuesday()/*-{
		return this.tuesday;
    }-*/;

    /**
     * Wednesday-based weeks (e.g., February 8, 2012 at 12:00 AM).
     * <p>
     * 
     * @return the {@link Interval}
     */
    public final native Interval wednesday()/*-{
		return this.wednesday;
    }-*/;

    /**
     * Thursday-based weeks (e.g., February 9, 2012 at 12:00 AM).
     * <p>
     * 
     * @return the {@link Interval}
     */
    public final native Interval thursday()/*-{
		return this.thursday;
    }-*/;

    /**
     * Friday-based weeks (e.g., February 10, 2012 at 12:00 AM).
     * <p>
     * 
     * @return the {@link Interval}
     */
    public final native Interval friday()/*-{
		return this.friday;
    }-*/;

    /**
     * Saturday-based weeks (e.g., February 11, 2012 at 12:00 AM).
     * <p>
     * 
     * @return the {@link Interval}
     */
    public final native Interval saturday()/*-{
		return this.saturday;
    }-*/;

    /**
     * Months (e.g., February 1, 2012 at 12:00 AM). Ranges between 28 and 31
     * days.
     * <p>
     * 
     * @return the {@link Interval}
     */
    public final native Interval month()/*-{
		return this.month;
    }-*/;

    /**
     * Years (e.g., January 1, 2012 at 12:00 AM). Normal years are 365 days
     * long; leap years are 366.
     * <p>
     * 
     * @return the {@link Interval}
     */
    public final native Interval year()/*-{
		return this.year;
    }-*/;

}
