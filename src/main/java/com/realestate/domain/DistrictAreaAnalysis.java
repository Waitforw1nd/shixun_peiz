package com.realestate.domain;

public class DistrictAreaAnalysis {
    private String district;
    private String areaSegment;
    private int count;
    private double avgPrice;

    public DistrictAreaAnalysis() {}

    public DistrictAreaAnalysis(String district, String areaSegment, int count, double avgPrice) {
        this.district = district;
        this.areaSegment = areaSegment;
        this.count = count;
        this.avgPrice = avgPrice;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
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
}