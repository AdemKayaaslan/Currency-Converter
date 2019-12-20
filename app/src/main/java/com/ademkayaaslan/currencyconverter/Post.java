package com.ademkayaaslan.currencyconverter;

import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("timestamp")
    private long timestamp;

    @SerializedName("rates")
    private Rates rates;

    public long getTimestamp() {
        return timestamp;
    }

    public Rates getRates() {
        return rates;
    }
}
