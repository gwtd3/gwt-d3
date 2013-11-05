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
package com.github.gwtd3.demo.client.testcases.svg;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.scales.LinearScale;
import com.github.gwtd3.api.scales.OrdinalScale;
import com.github.gwtd3.api.svg.Axis;
import com.github.gwtd3.api.svg.Axis.Orientation;
import com.github.gwtd3.api.time.Interval;
import com.github.gwtd3.demo.client.test.AbstractTestCase;
import com.google.gwt.dom.client.Element;

public class TestAxis extends AbstractTestCase {

    @Override
    public void doTest(final com.google.gwt.user.client.ui.ComplexPanel sandbox) {
        Axis axis = D3.svg().axis();

        // default scale
        LinearScale s = axis.scale();
        assertNotNull(s);
        int asInt = (int) s.domain().getNumber(0);
        assertEquals(0, asInt);
        // default orientation
        assertEquals(Orientation.BOTTOM, axis.orient());
        // set orientation
        axis.orient(Orientation.TOP);
        assertEquals(Orientation.TOP, axis.orient());

        // set scale
        axis.scale(D3.scale.log());

        // ticks
        assertEquals(1, axis.ticks().length());
        assertEquals(10, axis.ticks().getValue(0).asInt());

        axis.ticks(12);
        assertEquals(12, axis.ticks().getValue(0).asInt());

        axis.ticks(15, "blah");
        assertEquals(15, axis.ticks().getValue(0).asInt());
        assertEquals("blah", axis.ticks().getValue(1).asString());

        Interval interval = D3.time().day();
        axis.ticks(interval, 10);
        assertEquals(interval, axis.ticks().getValue(0).as());
        assertEquals(10, axis.ticks().getValue(1).asInt());

        // FIXME: smoke test to be cross checked
        DatumFunction<String> f = new DatumFunction<String>() {
            @Override
            public String apply(final Element context, final Value d, final int index) {
                return "index" + index;
            }
        };
        axis.ticks(8, f);
        assertEquals(8, axis.ticks().getValue(0).asInt());
        assertEquals(f, axis.ticks().getValue(1).as());

        // tick values
        assertNull(axis.tickValues());
        axis.tickValues(1, 2, 3);
        assertEquals(1, axis.tickValues().getValue(0).asInt());
        assertEquals(2, axis.tickValues().getValue(1).asInt());
        assertEquals(3, axis.tickValues().getValue(2).asInt());

        // tick subdivide : replaced
        // assertEquals(0, axis.tickSubdivide());
        // axis.tickSubdivide(9);
        // assertEquals(9, axis.tickSubdivide());

        // tick size
        assertEquals(6, axis.tickSize());
        assertEquals(6, axis.innerTickSize());
        assertEquals(6, axis.outerTickSize());
        axis.tickSize(6);
        axis.innerTickSize(5);
        axis.outerTickSize(5);
        assertEquals(5, axis.innerTickSize());
        assertEquals(5, axis.outerTickSize());

        axis.tickSize(6, 0);
        axis.tickSize(6, 3, 2);

        // tick padding
        assertEquals(3, axis.tickPadding());
        axis.tickPadding(5);
        assertEquals(5, axis.tickPadding());

        // tick format
        assertNull(axis.tickFormat());
        axis.tickFormat(D3.format("3"));
        assertNotNull(axis.tickFormat());

        // index in tick format
        OrdinalScale s2 = D3.scale.ordinal();
        axis = D3.svg().axis().scale(s2);
        s2.domain(5, 15, 20, 100);
        s2.range(1, 2, 3, 4);
        final StringBuffer counter = new StringBuffer();
        axis.tickFormat(new DatumFunction<String>() {

            @Override
            public String apply(final Element context, final Value d, final int index) {
                System.out.println("INDEX " + index + " : " + d.asString());
                assertTrue(index >= 0 && index < 4);
                counter.append("x");
                if (index % 2 == 0) {
                    return "";
                }
                return "" + index;
            }
        });
        // apply
        Selection svg = D3.select(sandbox).append("svg").attr("width", 100)
                .attr("height", 100).append("g")
                .attr("transform", "translate(32,50)");

        axis.apply(svg);
        assertEquals(4, counter.length());

        Axis axis2 = D3.svg().axis().apply(svg);
    }
}
