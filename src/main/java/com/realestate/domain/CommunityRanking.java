package com.realestate.domain;

public class CommunityRanking {
    private String community;
    private String district;
    private int listingCount;
    private double avgPrice;
    private double minPrice;
    private double maxPrice;

    public CommunityRanking() {}

    public CommunityRanking(String community, String district, int listingCount, double avgPrice, double minPrice, double maxPrice) {
        this.community = community;
        this.district = district;
        this.listingCount = listingCount;
        this.avgPrice = avgPrice;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getListingCount() {
        return listingCount;
    }

    public void setListingCount(int listingCount) {
        this.listingCount = listingCount;
    }

    public double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }
}