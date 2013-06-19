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
/**
 * 
 */
package com.github.gwtd3.demo.client.test.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HeaderPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class TestSessionContainer extends Composite {

    public static final String RUN_BUTTON_ID = "runTestButton";
    public static final String STOP_BUTTON_ID = "stopTestButton";

    private static TestSessionContainerUiBinder uiBinder = GWT.create(TestSessionContainerUiBinder.class);

    interface TestSessionContainerUiBinder extends UiBinder<Widget, TestSessionContainer> {}

    private RunUiHandlers uiHandlers;

    @UiField
    HasWidgets container;
    @UiField
    HeaderPanel leftPanel;
    @UiField
    HasWidgets toolbar;
    @UiField
    ComplexPanel testCaseContainer;

    // toolbar
    @UiField
    Button stopButton;
    @UiField
    Button runButton;
    @UiField
    AbsolutePanel sandbox;

    public TestSessionContainer() {
        initWidget(TestSessionContainer.uiBinder.createAndBindUi(this));

        runButton.ensureDebugId(RUN_BUTTON_ID);
        stopButton.ensureDebugId(STOP_BUTTON_ID);
    }

    public ComplexPanel getSandbox() {
        return sandbox;
    }

    @UiHandler("stopButton")
    void onStopClick(final ClickEvent e) {
        uiHandlers.stop();
    }

    @UiHandler("runButton")
    void onRunClick(final ClickEvent e) {
        uiHandlers.start();
    }

    /**
     * @param widget
     */
    public void addUnitTestWidget(final UnitTestWidget widget) {
        testCaseContainer.add(widget);
    }

    /**
     * @param uiHandlers
     *            the uiHandlers to set
     */
    public void setUiHandlers(final RunUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    /**
     * @param i
     * @param testExecution
     */
    public void setTestExecution(final int i, final TestExecution testExecution) {
        ((UnitTestWidget) testCaseContainer.getWidget(i)).setTestExecution(testExecution);
    }

    public void openDetails(final int index) {
        ((UnitTestWidget) testCaseContainer.getWidget(index)).openDetails();
    }
}
