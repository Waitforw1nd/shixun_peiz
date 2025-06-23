package com.realestate.domain;

public class DecadePriceTrend {
    private String builtDecade;
    private int count;
    private double avgPrice;
    private double avgUnitPrice;

    public DecadePriceTrend() {}

    public DecadePriceTrend(String builtDecade, int count, double avgPrice, double avgUnitPrice) {
        this.builtDecade = builtDecade;
        this.count = count;
        this.avgPrice = avgPrice;
        this.avgUnitPrice = avgUnitPrice;
    }

    public String getBuiltDecade() {
        return builtDecade;
    }

    public void setBuiltDecade(String builtDecade) {
        this.builtDecade = builtDecade;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public double getAvgUnitPrice() {
        return avgUnitPrice;
    }

    public void setAvgUnitPrice(double avgUnitPrice) {
        this.avgUnitPrice = avgUnitPrice;
    }
}