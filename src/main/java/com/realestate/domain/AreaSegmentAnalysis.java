package com.realestate.domain;

public class AreaSegmentAnalysis {
    private String areaSegment;
    private int count;
    private double avgPrice;
    private double avgUnitPrice;

    public AreaSegmentAnalysis() {}

    public AreaSegmentAnalysis(String areaSegment, int count, double avgPrice, double avgUnitPrice) {
        this.areaSegment = areaSegment;
        this.count = count;
        this.avgPrice = avgPrice;
        this.avgUnitPrice = avgUnitPrice;
    }

    public String getAreaSegment() {
        return areaSegment;
    }

    public void setAreaSegment(String areaSegment) {
        this.areaSegment = areaSegment;
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