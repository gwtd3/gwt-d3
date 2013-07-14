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

import java.util.Date;

import junit.framework.Assert;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class UnitTestWidget extends Composite {

    public static final String STATUS_INDICATOR_NAME = "testStatusIndicator";
    private static UnitTestWidgetUiBinder uiBinder = GWT.create(UnitTestWidgetUiBinder.class);

    public static final String WAITING_COLOR = "gray";
    public static final String RUNNING_COLOR = "yellow";
    public static final String SUCCESS_COLOR = "green";
    public static final String ERROR_COLOR = "red";

    interface UnitTestWidgetUiBinder extends UiBinder<Widget, UnitTestWidget> {}

    @UiField
    FlowPanel iconField;
    @UiField
    Anchor statusField;
    @UiField
    Label testNameField;
    @UiField
    TextArea detailsField;

    public UnitTestWidget() {
        initWidget(UnitTestWidget.uiBinder.createAndBindUi(this));
        detailsField.setVisible(false);
        iconField.getElement().setAttribute("name", STATUS_INDICATOR_NAME);
    }

    public void setTestName(final String s) {
        this.testNameField.setText(s);
    }

    @UiHandler("statusField")
    void onClick(final ClickEvent e) {
        detailsField.setVisible(!detailsField.isVisible());
    }

    public void setTestExecution(final TestExecution e) {
        updateState(e);
    }

    /**
     * @param phase
     */
    private void updateState(final TestExecution e) {
        TestPhase phase = e.getPhase();
        switch (phase) {
        case WAITING:
            updateIcon(WAITING_COLOR, "Waiting...");
            updateStatus("");
            detailsField.setText("");
            detailsField.setVisible(false);
            break;
        case STARTED:
            updateIcon(RUNNING_COLOR, "Started...");
            appendDetails("Started at " + nowTime());
            updateStatus("Started");
            break;
        case SETTING_UP:
            updateIcon(RUNNING_COLOR, "Preparing...");
            appendDetails("Preparing..." + nowTime(), false);
            updateStatus("Preparing...");
            break;
        case RUNNING:
            updateIcon(RUNNING_COLOR, "Running...");
            appendDetails("(" + e.getTimeForLastPhase() + ")");
            appendDetails("Running...", false);
            updateStatus("Running...");
            break;
        case TEARING_DOWN:
            updateIcon(RUNNING_COLOR, "Finishing...");
            appendDetails("(" + e.getTimeForLastPhase() + ")");
            appendDetails("Tearing down...", false);
            updateStatus("Tearing down...");
            break;
        case FINISHED:
            setFinishedState(e);
            break;
        default:
            throw new IllegalArgumentException("cannot handle " + phase);
        }

    }

    /**
     * @param string
     */
    private void appendDetails(final String string) {
        appendDetails(string, true);
    }

    private void appendDetails(final String string, final boolean newLine) {
        detailsField.setText(detailsField.getText() + string + (newLine ? '\n' : ""));
    }

    /**
     * @return
     */
    private String nowTime() {
        Date now = new Date();
        String s = "";
        s += now.getHours();
        s += ":" + now.getMinutes();
        s += ":" + now.getSeconds();
        return s;
    }

    /**
     * @param e
     */
    private void setFinishedState(final TestExecution e) {
        appendDetails("(" + e.getTimeForLastPhase() + ")");
        appendDetails("Total time: " + e.getTotalTimeSinceStart());

        TestResultType resultType = e.getResult().getType();
        switch (resultType) {
        case SUCCESS:
            updateIcon(SUCCESS_COLOR, "Passed...");
            updateStatus("Passed");
            break;
        case ERROR:
            updateIcon(ERROR_COLOR, "Error...");
            updateStatus("Error");
            appendDetails("Error during " + e.getResult().getEndingPhase() + ":");
            appendDetails(stackToString(e));

            break;
        case FAILURE:
            updateIcon(ERROR_COLOR, "Failure...");
            updateStatus("Failure");
            appendDetails("Failure during " + e.getResult().getEndingPhase() + ":");
            appendDetails(stackToString(e));
            break;
        default:
            throw new IllegalArgumentException("cannot handle " + resultType);
        }
    }

    /**
     * @param e
     * @return
     */
    private String stackToString(final TestExecution e) {
        Throwable throwable = e.getResult().getThrowable();
        String s = "";
        if (throwable != null) {
            s += throwable.toString() + '\n';
            StackTraceElement[] stackTrace = throwable.getStackTrace();
            for (StackTraceElement st : stackTrace) {
                String stack = st.toString();
                if (!stack.startsWith(Assert.class.getName())) {
                    s += stack + '\n';
                }
            }
        }
        return s;
    }

    /**
     * @param text
     */
    private void updateStatus(final String text) {
        statusField.setText(text);
    }

    /**
     * @param color
     * @param title
     */
    private void updateIcon(final String color, final String title) {
        iconField.getElement().getStyle().setBackgroundColor(color);
        iconField.setTitle(title);
    }

    public void openDetails() {
        detailsField.setVisible(true);
    }
}
