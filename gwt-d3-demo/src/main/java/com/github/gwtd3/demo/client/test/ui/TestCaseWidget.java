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
