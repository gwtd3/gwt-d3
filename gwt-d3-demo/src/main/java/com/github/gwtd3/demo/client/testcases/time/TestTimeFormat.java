package com.github.gwtd3.demo.client.testcases.time;

import java.util.Date;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.time.TimeFormat;
import com.github.gwtd3.demo.client.test.AbstractTestCase;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.user.client.ui.ComplexPanel;

public class TestTimeFormat extends AbstractTestCase {

    @Override
    public void doTest(final ComplexPanel sandbox) {
        parse();
        format();
        utc();
        iso();
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
}
