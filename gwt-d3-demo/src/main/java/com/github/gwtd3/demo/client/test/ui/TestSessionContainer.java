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
