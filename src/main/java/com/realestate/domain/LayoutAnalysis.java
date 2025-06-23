package com.realestate.domain;

public class LayoutAnalysis {
    private String layout;
    private int count;
    private double avgPrice;
    private double avgArea;
    private double avgUnitPrice;

    public LayoutAnalysis() {}

    public LayoutAnalysis(String layout, int count, double avgPrice, double avgArea, double avgUnitPrice) {
        this.layout = layout;
        this.count = count;
        this.avgPrice = avgPrice;
        this.avgArea = avgArea;
        this.avgUnitPrice = avgUnitPrice;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
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

    public double getAvgArea() {
        return avgArea;
    }

    public void setAvgArea(double avgArea) {
        this.avgArea = avgArea;
    }

    public double getAvgUnitPrice() {
        return avgUnitPrice;
    }

    public void setAvgUnitPrice(double avgUnitPrice) {
        this.avgUnitPrice = avgUnitPrice;
    }
}