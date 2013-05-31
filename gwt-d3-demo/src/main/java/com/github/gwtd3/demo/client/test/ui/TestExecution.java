/**
 * 
 */
package com.github.gwtd3.demo.client.test.ui;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class TestExecution {
	private final TestPhase phase;
	private final TestResult result;
	private final long timeForLastPhase;
	private final long totalTimeSinceStart;

	public TestExecution(final TestPhase phase, final TestResult result, final long timeForLastPhase, final long totalTimeSinceStart) {
		super();
		this.phase = phase;
		this.result = result;
		this.timeForLastPhase = timeForLastPhase;
		this.totalTimeSinceStart = totalTimeSinceStart;
	}

	public TestPhase getPhase() {
		return phase;
	}

	public TestResult getResult() {
		return result;
	}

	/**
	 * @return the timeForLastPhase
	 */
	public String getTimeForLastPhase() {
		return timeForLastPhase + "ms";
	}

	/**
	 * @return the totalTimeSinceStart
	 */
	public String getTotalTimeSinceStart() {
		return totalTimeSinceStart + "ms";
	}
}
