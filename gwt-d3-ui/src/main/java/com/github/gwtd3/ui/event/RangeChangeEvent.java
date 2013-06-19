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
package com.github.gwtd3.ui.event;

import com.google.common.collect.Range;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class RangeChangeEvent extends GwtEvent<RangeChangeEvent.RangeChangeHandler> {

    public static Type<RangeChangeHandler> TYPE = new Type<RangeChangeHandler>();
    private final Range<?> newRange;
    private final Range<?> oldRange;

    public interface RangeChangeHandler extends EventHandler {
        void onRangeChange(RangeChangeEvent event);
    }

    public interface RangeChangeHasHandlers extends HasHandlers {
        HandlerRegistration addRangeChangeHandler(RangeChangeHandler handler);
    }

    public RangeChangeEvent(final Range<?> newRange, final Range<?> oldRange) {
        this.newRange = newRange;
        this.oldRange = oldRange;
    }

    public Range<?> getNewRange() {
        return newRange;
    }

    public Range<?> getOldRange() {
        return oldRange;
    }

    @Override
    protected void dispatch(final RangeChangeHandler handler) {
        handler.onRangeChange(this);
    }

    @Override
    public Type<RangeChangeHandler> getAssociatedType() {
        return TYPE;
    }

    public static Type<RangeChangeHandler> getType() {
        return TYPE;
    }

    public static void fire(final HasHandlers source, final Range<?> newRange, final Range<?> oldRange) {
        source.fireEvent(new RangeChangeEvent(newRange, oldRange));
    }
}
