package com.ademkayaaslan.currencyconverter;

import com.google.gson.annotations.SerializedName;

public class Rates {

    @SerializedName("TRY")
    private double TRY;

    @SerializedName("USD")
    private double USD;

    @SerializedName("JPY")
    private double JPY;

    public double getTRY() {
        return TRY;
    }

    public double getUSD() {
        return USD;
    }

    public double getJPY() {
        return JPY;
    }
}
