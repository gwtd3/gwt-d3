package com.github.gwtd3.demo.client.testcases.time;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.JsArrays;
import com.github.gwtd3.api.time.Range;
import com.github.gwtd3.demo.client.test.AbstractTestCase;

import com.google.gwt.user.client.ui.ComplexPanel;

public class TestTimeScales extends AbstractTestCase {

    @Override
    public void doTest(final ComplexPanel sandbox) {
        Range range = D3.time().scale().range(JsArrays.asJsArray(0, 5)).range();
    }
}
