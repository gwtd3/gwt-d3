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
