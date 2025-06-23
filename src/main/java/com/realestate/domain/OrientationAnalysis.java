package com.realestate.domain;

public class OrientationAnalysis {
    private String orientation;
    private int count;
    private double avgPrice;
    private double avgUnitPrice;

    public OrientationAnalysis() {}

    public OrientationAnalysis(String orientation, int count, double avgPrice, double avgUnitPrice) {
        this.orientation = orientation;
        this.count = count;
        this.avgPrice = avgPrice;
        this.avgUnitPrice = avgUnitPrice;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
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