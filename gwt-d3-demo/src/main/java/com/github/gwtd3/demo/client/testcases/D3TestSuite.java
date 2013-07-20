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
package com.github.gwtd3.demo.client.testcases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.gwtd3.demo.client.test.AbstractTestCase;
import com.github.gwtd3.demo.client.testcases.arrays.TestArrays;
import com.github.gwtd3.demo.client.testcases.arrays.TestD3Arrays;
import com.github.gwtd3.demo.client.testcases.core.TestFormat;
import com.github.gwtd3.demo.client.testcases.core.TestMath;
import com.github.gwtd3.demo.client.testcases.csv.TestCsv;
import com.github.gwtd3.demo.client.testcases.d3.TestColors;
import com.github.gwtd3.demo.client.testcases.d3.TestD3;
import com.github.gwtd3.demo.client.testcases.scales.TestLinearScale;
import com.github.gwtd3.demo.client.testcases.scales.TestLogScale;
import com.github.gwtd3.demo.client.testcases.scales.TestPowScale;
import com.github.gwtd3.demo.client.testcases.scales.TestThresholdScale;
import com.github.gwtd3.demo.client.testcases.scales.TestTimeScale;
import com.github.gwtd3.demo.client.testcases.selection.TestSelectionAttr;
import com.github.gwtd3.demo.client.testcases.selection.TestSelectionClassed;
import com.github.gwtd3.demo.client.testcases.selection.TestSelectionContents;
import com.github.gwtd3.demo.client.testcases.selection.TestSelectionControls;
import com.github.gwtd3.demo.client.testcases.selection.TestSelectionData;
import com.github.gwtd3.demo.client.testcases.selection.TestSelectionData2;
import com.github.gwtd3.demo.client.testcases.selection.TestSelectionHtml;
import com.github.gwtd3.demo.client.testcases.selection.TestSelectionProperty;
import com.github.gwtd3.demo.client.testcases.selection.TestSelectionStyle;
import com.github.gwtd3.demo.client.testcases.selection.TestSelectionText;
import com.github.gwtd3.demo.client.testcases.selection.TestSubselections;
import com.github.gwtd3.demo.client.testcases.svg.TestArc;
import com.github.gwtd3.demo.client.testcases.svg.TestArea;
import com.github.gwtd3.demo.client.testcases.svg.TestAxis;
import com.github.gwtd3.demo.client.testcases.svg.TestLine;
import com.github.gwtd3.demo.client.testcases.time.TestTimeFormat;
import com.github.gwtd3.demo.client.testcases.time.TestTimeIntervals;
import com.github.gwtd3.demo.client.testcases.transition.TestEasing;
import com.github.gwtd3.demo.client.testcases.transition.TestInterpolators;
import com.github.gwtd3.demo.client.testcases.transition.TestTransition;
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
				new TestArrays(),
				new TestD3Arrays(),
				// utils
				new TestValue(),
				// D3
				new TestD3(), new TestColors(),
				// selections
				new TestSubselections(), new TestSelectionContents(), new TestSelectionAttr(), new TestSelectionClassed(),
				new TestSelectionData(), new TestSelectionData2(), new TestSelectionProperty(), new TestSelectionText(),
				new TestSelectionHtml(), new TestSelectionControls(), new TestSelectionStyle(),
				//Transitions
				new TestTransition(), new TestInterpolators(),new TestEasing(),
				//Math
				new TestMath(),
				//Format
				new TestFormat(),
				// Scales
				new TestLinearScale(), new TestLogScale(), new TestPowScale(), new TestThresholdScale(), new TestTimeScale(),
				// svg
				new TestAxis(), new TestLine(), new TestArea(), new TestArc(),
				// time
				new TestTimeFormat(), new TestTimeIntervals(),
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
