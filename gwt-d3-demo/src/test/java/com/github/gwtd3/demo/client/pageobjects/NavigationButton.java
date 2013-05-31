package com.github.gwtd3.demo.client.pageobjects;

/**
 * A clickable element that when clicked, reveal a part of the application.
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 * @param <S>
 *            the part this button reveals.
 * @param <P>
 *            the parent of this {@link PageObject}
 */
public abstract class NavigationButton<S, P extends PageObject<?>> extends ElementPageObject<P> {
	/**
	 * Create the button using the given parent and the button id of the element
	 * in the parent.
	 * 
	 * @param parent
	 * @param buttonId
	 */
	public NavigationButton(P parent, String buttonId) {
		super(parent, parent.findClickableById(buttonId, 1));
	}

	/**
	 * Click the element and reveal it.
	 * 
	 * @return
	 */
	public S click() {
		clickOnElement();
		return navigateToPageObject();
	}

	/**
	 * Create the part of the application that is revealed when the button is
	 * clicked.
	 * 
	 * @return the part of the application
	 */
	protected abstract S navigateToPageObject();

}
