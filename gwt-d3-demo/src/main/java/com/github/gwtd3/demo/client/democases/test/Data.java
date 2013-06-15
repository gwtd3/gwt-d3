package com.github.gwtd3.demo.client.democases.test;

import com.google.gwt.core.client.JsDate;

/**
 * An exemple data parsed from the file test-data/readme.csv
 * 
 */
public class Data {
    private final String symbol;

    private final JsDate date;

    private final double price;

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Data [date=" + date.getTime() + ", price=" + price + "]";
    }

    public Data(final String symbol, final JsDate date, final double price) {
        super();
        this.symbol = symbol;
        this.date = date;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public JsDate getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }
}