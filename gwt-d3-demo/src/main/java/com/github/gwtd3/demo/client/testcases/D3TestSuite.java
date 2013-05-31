/**
 * 
 */
package com.github.gwtd3.demo.client.testcases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.gwtd3.demo.client.test.AbstractTestCase;
import com.github.gwtd3.demo.client.testcases.arrays.TestArrays;
import com.github.gwtd3.demo.client.testcases.arrays.TestD3Arrays;
import com.github.gwtd3.demo.client.testcases.csv.TestCsv;
import com.github.gwtd3.demo.client.testcases.d3.TestColors;
import com.github.gwtd3.demo.client.testcases.d3.TestD3;
import com.github.gwtd3.demo.client.testcases.d3.TestInterpolators;
import com.github.gwtd3.demo.client.testcases.scales.TestLinearScale;
import com.github.gwtd3.demo.client.testcases.scales.TestThresholdScale;
import com.github.gwtd3.demo.client.testcases.scales.TestTimeScale;
import com.github.gwtd3.demo.client.testcases.selection.TestSelectionAttr;
import com.github.gwtd3.demo.client.testcases.selection.TestSelectionClassed;
import com.github.gwtd3.demo.client.testcases.svg.TestArc;
import com.github.gwtd3.demo.client.testcases.svg.TestArea;
import com.github.gwtd3.demo.client.testcases.svg.TestAxis;
import com.github.gwtd3.demo.client.testcases.svg.TestLine;
import com.github.gwtd3.demo.client.testcases.time.TestTimeFormat;
import com.github.gwtd3.demo.client.testcases.time.TestTimeIntervals;
import com.github.gwtd3.demo.client.testcases.time.TestTimeScales;
import com.github.gwtd3.demo.client.testcases.tsv.TestTsv;

/**
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class D3TestSuite {

	List<AbstractTestCase> tests = new ArrayList<AbstractTestCase>();

	public static D3TestSuite get() {
		D3TestSuite suite = new D3TestSuite();
		suite.tests = Arrays.asList(
				// arrays
				new TestArrays(), new TestD3Arrays(),
				// utils
				new TestValue(),
				// D3
				new TestD3(), new TestColors(), new TestInterpolators(),
				// selections
				new TestSelectionAttr(), new TestSelectionClassed(),
				// scales
				new TestLinearScale(), new TestThresholdScale(), new TestTimeScale(),
				// svg
				new TestAxis(), new TestLine(), new TestArea(), new TestArc(),
				// time
				new TestTimeFormat(), new TestTimeScales(), new TestTimeIntervals(),
				// csv
				new TestCsv(),
				// tsv
				new TestTsv());
		return suite;
	}

	/**
	 * @return the tests
	 */
	public List<AbstractTestCase> getTests() {
		return tests;
	}
}
