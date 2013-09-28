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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.github.gwtd3.demo.client.test.TestCase;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.ComplexPanel;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class TestRunner implements RunUiHandlers {

	private final List<TestCase> tests = new ArrayList<TestCase>();
	private final ComplexPanel sandbox;
	private final TestSessionContainer container;

	public TestRunner(final TestSessionContainer container) {
		super();
		this.container = container;
		container.setUiHandlers(this);
		sandbox = createSandbox();
	}

	public <T extends TestCase> void setTests(final Collection<T> tests) {
		this.tests.addAll(tests);
		int i = 0;
		for (TestCase test : tests) {
			prepareWidget(test);
			resetWidget(i);
			i++;
		}
	}

	/**
	 * @param test
	 */
	private void prepareWidget(final TestCase test) {
		TestCaseWidget widget = new TestCaseWidget(test);
		widget.setTestName(getName(test));
		widget.setUiHandlers(this);
		container.addUnitTestWidget(widget);
	}

	/**
	 * @param widget
	 */
	private void resetWidget(final int i) {
		container.setTestExecution(i, new TestExecution(TestPhase.WAITING,
				null, 0, 0));
	}

	/**
	 * @return
	 */
	private ComplexPanel createSandbox() {
		return container.getSandbox();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.gwtd3.demo.client.tests.RunUiHandlers#start()
	 */
	@Override
	public void start() {
		stopped = false;
		firstErrorTest = -1;
		if (tests.size() == 0) {
			return;
		}
		for (int i = 1; i < tests.size(); i++) {
			resetWidget(i);
		}
		totalStartTime = new Date().getTime();
		run(0);

	}

	private long totalStartTime = 0;
	private long testStartTime = 0;
	private long phaseStartTime = 0;
	private boolean stopped = false;
	private int firstErrorTest = -1;

	/**
	 * @return
	 */
	private long testElapsedTime() {
		return new Date().getTime() - testStartTime;
	}

	/**
	 * @return
	 */
	private long phaseElapsedTime() {
		long elapsed = new Date().getTime() - phaseStartTime;
		phaseStartTime = new Date().getTime();
		return elapsed;
	}

	/**
	 * @param i
	 */
	private void run(final int i) {
		if ((i >= tests.size()) || stopped) {
			finishSuite();
			return;
		}
		TestCase test = tests.get(i);
		testStartTime = new Date().getTime();
		phaseStartTime = testStartTime;
		doSetUp(test, i);
		container.started(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.gwtd3.demo.client.tests.RunUiHandlers#stop()
	 */
	@Override
	public void stop() {
		stopped = true;
	}

	/**
	 * @param test
	 * @return
	 */
	private String getName(final TestCase test) {
		int lastIndexOf = test.getClass().getName().lastIndexOf(".");
		if (lastIndexOf >= 0) {
			return test.getClass().getName().substring(lastIndexOf + 1);
		} else {
			return test.getClass().getName();
		}
	}

	private void doSetUp(final TestCase test, final int i) {
		container.setTestExecution(i, new TestExecution(TestPhase.SETTING_UP,
				null, phaseElapsedTime(), testElapsedTime()));
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				try {
					test.setUp(sandbox);
					doTest(test, i);
				} catch (Throwable t) {
					handleThrowable(i, test, t, TestPhase.SETTING_UP);
				}
			}
		});
	}

	/**
	 * @param test
	 */
	private void doTest(final TestCase test, final int i) {
		container.setTestExecution(i, new TestExecution(TestPhase.RUNNING,
				null, phaseElapsedTime(), testElapsedTime()));

		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				try {
					test.doTest(sandbox);
					doTearDown(test, i);
				} catch (Throwable t) {
					handleThrowable(i, test, t, TestPhase.RUNNING);
				}

			}
		});
	}

	private void doTearDown(final TestCase test, final int i) {
		container.setTestExecution(i, new TestExecution(TestPhase.TEARING_DOWN,
				null, phaseElapsedTime(), testElapsedTime()));
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				try {
					test.tearDown(sandbox);
					doFinish(test, i);
				} catch (Throwable t) {
					handleThrowable(i, test, t, TestPhase.RUNNING);
				}

			}

		});
	}

	private void doFinish(final TestCase test, final int i) {
		container.setTestExecution(i, new TestExecution(TestPhase.FINISHED,
				TestResult.createSuccess(), phaseElapsedTime(),
				testElapsedTime()));
		run(i + 1);
	}

	private void handleThrowable(final int i, final TestCase test,
			final Throwable t, final TestPhase endingPhase) {
		if (firstErrorTest == -1) {
			firstErrorTest = i;
		}
		if (t instanceof AssertionError) {
			container.setTestExecution(i, new TestExecution(TestPhase.FINISHED,
					new TestResult(endingPhase, TestResultType.FAILURE, t),
					phaseElapsedTime(), testElapsedTime()));

		} else if (t instanceof Throwable) {
			container.setTestExecution(i, new TestExecution(TestPhase.FINISHED,
					new TestResult(endingPhase, TestResultType.ERROR, t),
					phaseElapsedTime(), testElapsedTime()));
		}
		GWT.log("FAILED: " + getName(test) + "", t);
		run(i + 1);
	}

	private void finishSuite() {
		if (firstErrorTest != -1) {
			container.openDetails(firstErrorTest);
		}
		container.started(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.github.gwtd3.demo.client.test.ui.RunUiHandlers#onShowTestResults(
	 * java.lang.String)
	 */
	@Override
	public void onShowTestResults(final String results) {
		container.showDetails(results);
	}
}
