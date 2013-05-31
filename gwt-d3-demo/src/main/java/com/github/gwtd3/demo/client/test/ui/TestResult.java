/**
 * 
 */
package com.github.gwtd3.demo.client.test.ui;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class TestResult {

	private final TestPhase endingPhase;

	private final TestResultType type;

	private final Throwable throwable;

	protected TestResult(final TestPhase endingPhase, final TestResultType type, final Throwable throwable) {
		super();
		this.endingPhase = endingPhase;
		this.type = type;
		this.throwable = throwable;
	}

	public static TestResult createSuccess() {
		return new TestResult(TestPhase.FINISHED, TestResultType.SUCCESS, null);
	}

	public static TestResult createFailure(final AssertionError failure) {
		return new TestResult(TestPhase.RUNNING, TestResultType.FAILURE, failure);
	}

	public static TestResult createError(final Throwable error, final TestPhase phase) {
		return new TestResult(phase, TestResultType.ERROR, error);
	}

	public TestPhase getEndingPhase() {
		return endingPhase;
	}

	public TestResultType getType() {
		return type;
	}

	public Throwable getThrowable() {
		return throwable;
	}

}
