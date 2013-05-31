package com.github.gwtd3.demo.client.pageobjects;

import com.github.gwtd3.demo.client.D3Demo;
import com.github.gwtd3.demo.client.democases.behaviors.DragMultiples;
import org.openqa.selenium.By;

/**
 * Represents the base class for any demo in gwt-d3 demo.
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class AbstractDemoPage<D extends AbstractDemoPage<D>> extends ElementPageObject<DemoApplication> {
	private final NavigationButton<D, DemoApplication> button;

	protected AbstractDemoPage(DemoApplication parent) {
		super(parent, By.id(D3Demo.DEMO_CONTAINER_ID));
		// the demo button has the id of the demo that it is pointing to
		this.button = new DemoButton(parent, DragMultiples.class.getSimpleName());
	}

	class DemoButton extends NavigationButton<D, DemoApplication> {
		public DemoButton(DemoApplication parent, String buttonId) {
			super(parent, buttonId);
		}

		@SuppressWarnings("unchecked")
		@Override
		protected D navigateToPageObject() {
			return (D) AbstractDemoPage.this;
		}

	}

	protected D reveal() {
		return button.click();
	}

}
