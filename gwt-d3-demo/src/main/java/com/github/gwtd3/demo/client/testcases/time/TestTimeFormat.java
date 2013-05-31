package com.github.gwtd3.demo.client.testcases.time;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.time.Format;
import com.github.gwtd3.demo.client.test.AbstractTestCase;

import com.google.gwt.core.client.JsDate;
import com.google.gwt.user.client.ui.ComplexPanel;

public class TestTimeFormat extends AbstractTestCase {

    @Override
    public void doTest(final ComplexPanel sandbox) {
        Format format = D3.time().format("%b %Y");
        JsDate date = format.parse("Feb 2000");

        assertEquals(2000, date.getFullYear());
        assertEquals(1, date.getMonth());
        assertEquals(1, date.getDate());
        assertEquals(0, date.getHours());
        assertEquals(0, date.getMinutes());
        assertEquals(0, date.getSeconds());
        assertEquals(0, date.getMilliseconds());
    }
}
