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
