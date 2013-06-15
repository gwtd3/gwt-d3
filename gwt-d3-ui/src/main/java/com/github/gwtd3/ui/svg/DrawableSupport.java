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
