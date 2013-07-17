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
package com.github.gwtd3.demo.client.democases.test;

import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.dsv.DsvCallback;
import com.github.gwtd3.api.dsv.DsvObjectAccessor;
import com.github.gwtd3.api.dsv.DsvRow;
import com.github.gwtd3.api.dsv.DsvRows;
import com.github.gwtd3.api.time.TimeFormat;
import com.github.gwtd3.api.xhr.XmlHttpRequest;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class DataLoader {

    public static void loadData(final AsyncCallback<Array<Data>> callback) {
        D3.csv("demo-data/readme.csv", new DsvObjectAccessor<Data>() {
            final TimeFormat format = D3.time().format("%b %Y");

            @Override
            public Data apply(final DsvRow d, final int index) {
                Value value = d.get("symbol");
                if ("S&P 500".equals(value.asString())) {
                    String symbol = d.get("symbol").asString();
                    JsDate date = format.parse(d.get("date").asString());
                    double price = d.get("price").asDouble();
                    return new Data(symbol, date, price);
                } else {
                    return null;
                }
            }
        }, new DsvCallback<Data>() {
            @Override
            public void get(final JavaScriptObject error, final DsvRows<Data> values) {
                if (error != null) {
                    XmlHttpRequest xhrError = error.cast();
                    String message = xhrError.status() + " (" + xhrError.statusText() + ")";
                    callback.onFailure(new RuntimeException(message));
                }
                else {
                    callback.onSuccess(values);
                }
            }
        });
    }
}
