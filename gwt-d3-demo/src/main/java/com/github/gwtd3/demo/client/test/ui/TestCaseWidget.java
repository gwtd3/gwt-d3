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

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.constants.ButtonType;
import com.github.gwtd3.demo.client.test.TestCase;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class TestCaseWidget extends Button {

	private final TestCase test;
	private String detailsString;
	private RunUiHandlers handlers;

	public TestCaseWidget(final TestCase test) {
		super();
		this.test = test;
		init();
	}

	/**
	 * 
	 */
	private void init() {
		this.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				handlers.onShowTestResults(detailsString);
			}
		});
	}

	public void setTestName(final String s) {
		this.setText(s);
	}

	public void setTestExecution(final TestExecution e) {
		updateState(e);
	}

	/**
	 * @param phase
	 */
	private void updateState(final TestExecution e) {
		TestPhase phase = e.getPhase();
		setEnabled(true);
		switch (phase) {
		case WAITING:
			setType(ButtonType.DEFAULT);
			setEnabled(false);
			setTitle("");
			break;
		case STARTED:
			setType(ButtonType.WARNING);
			appendDetails("Started at " + nowTime());
			setTitle("Started");
			break;
		case SETTING_UP:
			setType(ButtonType.WARNING);
			appendDetails("Preparing..." + nowTime(), false);
			setTitle("Preparing...");
			break;
		case RUNNING:
			setType(ButtonType.WARNING);
			appendDetails("(" + e.getTimeForLastPhase() + ")");
			appendDetails("Running...", false);
			setTitle("Running...");
			break;
		case TEARING_DOWN:
			setType(ButtonType.WARNING);
			appendDetails("(" + e.getTimeForLastPhase() + ")");
			appendDetails("Tearing down...", false);
			setTitle("Tearing down...");
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
		this.detailsString += string + "\n";
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
			setType(ButtonType.SUCCESS);
			setTitle("Passed");
			break;
		case ERROR:
			setType(ButtonType.DANGER);
			setTitle("Error");
			appendDetails("Error during " + e.getResult().getEndingPhase() + ":");
			appendDetails(stackToString(e));

			break;
		case FAILURE:
			setType(ButtonType.DANGER);
			setTitle("Failure");
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
	 * 
	 */
	public String getDetailsString() {
		return this.detailsString;
	}

	/**
	 * @param testRunner
	 */
	public void setUiHandlers(final RunUiHandlers handlers) {
		this.handlers = handlers;

	}

}
