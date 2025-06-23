package com.realestate.domain;

public class DistrictMarketStats {
    private String district;
    private int totalListings;
    private double avgTotalPrice;
    private double avgUnitPrice;
    private double avgArea;

    public DistrictMarketStats() {}

    public DistrictMarketStats(String district, int totalListings, double avgTotalPrice, double avgUnitPrice, double avgArea) {
        this.district = district;
        this.totalListings = totalListings;
        this.avgTotalPrice = avgTotalPrice;
        this.avgUnitPrice = avgUnitPrice;
        this.avgArea = avgArea;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getTotalListings() {
        return totalListings;
    }

    public void setTotalListings(int totalListings) {
        this.totalListings = totalListings;
    }

    public double getAvgTotalPrice() {
        return avgTotalPrice;
    }

    public void setAvgTotalPrice(double avgTotalPrice) {
        this.avgTotalPrice = avgTotalPrice;
    }

    public double getAvgUnitPrice() {
        return avgUnitPrice;
    }

    public void setAvgUnitPrice(double avgUnitPrice) {
        this.avgUnitPrice = avgUnitPrice;
    }

    public double getAvgArea() {
        return avgArea;
    }

    public void setAvgArea(double avgArea) {
        this.avgArea = avgArea;
    }
}