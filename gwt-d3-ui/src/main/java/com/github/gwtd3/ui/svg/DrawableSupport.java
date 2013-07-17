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
package com.github.gwtd3.ui.svg;

import com.github.gwtd3.ui.Drawable;
import com.github.gwtd3.ui.DrawableWidget;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;

/**
 * Implements the logic of scheduling redraw calls
 * for {@link Drawable}.
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class DrawableSupport {

    /**
     * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
     * 
     */
    private final class MayCallRedraw implements ScheduledCommand {
        @Override
        public void execute() {
            if (delegate.asWidget().isAttached()) {
                redrawScheduled = false;
                delegate.redraw();
            }
            else {
                scheduleRedrawCall();
            }
        }
    }

    private final DrawableWidget delegate;
    private boolean redrawScheduled;

    public DrawableSupport(final DrawableWidget delegate) {
        super();
        this.delegate = delegate;
    }

    public void scheduleRedraw() {
        // if already scheduled, return
        if (redrawScheduled) {
            return;
        }
        else {
            // scheduling means registering a scheduler command
            // to be executed once after all events listeners
            // has been executed
            redrawScheduled = true;
            scheduleRedrawCall();
        }
    }

    /**
	 * 
	 */
    private void scheduleRedrawCall() {
        Scheduler.get().scheduleDeferred(new MayCallRedraw());
    }

}
