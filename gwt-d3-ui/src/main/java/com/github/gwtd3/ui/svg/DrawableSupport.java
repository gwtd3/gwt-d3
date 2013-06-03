package com.github.gwtd3.ui.svg;

import com.github.gwtd3.ui.Drawable;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;

/**
 * Implements the logic of scheduling redraws
 * for {@link Drawable}.
 * 
 * @author <a href="mailto:schiochetanthoni@gmail.com">Anthony Schiochet</a>
 * 
 */
public class DrawableSupport {

    private Drawable delegate;
    private boolean redrawScheduled;

    public DrawableSupport(final Drawable delegate) {
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
            Scheduler.get().scheduleFinally(new ScheduledCommand() {
                @Override
                public void execute() {
                    redrawScheduled = false;
                    delegate.redraw();
                }
            });
        }
    }

}
