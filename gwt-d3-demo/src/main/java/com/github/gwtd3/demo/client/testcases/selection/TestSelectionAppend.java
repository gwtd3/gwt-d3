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
package com.github.gwtd3.demo.client.testcases.selection;

import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Label;

public class TestSelectionAppend extends AbstractSelectionTest {

    private static final String ATTRIBUTE = "myattr";

    @Override
    public void doTest(final ComplexPanel sandbox) {
        testAppendTagName();
        testAppendFunctionReturningNode();
    }

    private void testAppendTagName() {
        Selection selection = givenASimpleSelection(new Label());
        // WHEN appending
        Selection span = selection.append("span").classed("testClass", true);

        // THEN
        assertEquals(selection.select("span").empty(), false);
        assertNotNull(span.node());
        assertEquals("span", span.node().getTagName().toLowerCase());
        assertEquals("testClass", span.node().getClassName());
    }

    private void testAppendFunctionReturningNode() {
        Selection root = givenASimpleSelection(new Label());
        // WHEN appending
        root.selectAll("ul").data(
                new String[] { "test1", "test2", "test3" })
                .enter()
                .append(new DatumFunction<Node>() {
                    @Override
                    public Node apply(final Element context, final Value d, final int index) {
                        UListElement ulElement = Document.get().createULElement();
                        ulElement.addClassName(d.asString());
                        return ulElement;
                    }
                });
        // a way to test the DatumFunction
        final StringBuilder sb = new StringBuilder();
        // THEN
        root.selectAll("ul").each(new DatumFunction<Void>() {
            @Override
            public Void apply(final Element context, final Value d, final int index) {
                assertNotNull(context);
                assertEquals("ul", context.getTagName().toLowerCase());
                assertEquals("test" + (index + 1), context.getClassName());
                assertEquals("test" + (index + 1), d.asString());
                sb.append(index);
                return null;
            }
        });
        assertEquals("012", sb.toString());
    }

}
