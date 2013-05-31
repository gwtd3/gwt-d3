package com.github.gwtd3.demo.client.testcases.arrays;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.JsArrays;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.demo.client.test.AbstractTestCase;

import com.google.gwt.user.client.ui.ComplexPanel;

/**
 * Test D3 array functions.
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class TestD3Arrays extends AbstractTestCase {

    @Override
    public void doTest(final ComplexPanel sandbox) {
        testMaxAndMin();

    }

    private void testMaxAndMin() {
        assertEquals(200, D3.max(JsArrays.asJsArray(52, 200, 31)).asInt());
        assertEquals(52, D3.max(Array.create("52", "200", "31")).asInt());
        assertEquals(205, D3.max(JsArrays.asJsArray(52, 200, 31), Callbacks.add(5)).asInt());
        assertEquals(205, D3.max(Array.create("52", "200", "31"), Callbacks.add(5)).asInt());

        assertEquals(31, D3.min(JsArrays.asJsArray(52, 200, 31)).asInt());
        assertEquals(200, D3.min(Array.create("52", "200", "31")).asInt());
        assertEquals(36, D3.min(JsArrays.asJsArray(52, 200, 31), Callbacks.add(5)).asInt());
        assertEquals(36, D3.min(Array.create("52", "200", "31"), Callbacks.add(5)).asInt());

        System.out.println(D3.max(Array.create()));
        assertTrue(D3.min(Array.create()).isUndefined());
        assertTrue(D3.max(Array.create()).isUndefined());

    }

}
