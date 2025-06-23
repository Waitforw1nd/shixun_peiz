package com.realestate.domain;

public class PriceAreaCorrelation {
    private String district;
    private int count;
    private double priceAreaCorr;
    private double unitPriceAreaCorr;

    public PriceAreaCorrelation() {}

    public PriceAreaCorrelation(String district, int count, double priceAreaCorr, double unitPriceAreaCorr) {
        this.district = district;
        this.count = count;
        this.priceAreaCorr = priceAreaCorr;
        this.unitPriceAreaCorr = unitPriceAreaCorr;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPriceAreaCorr() {
        return priceAreaCorr;
    }

    public void setPriceAreaCorr(double priceAreaCorr) {
        this.priceAreaCorr = priceAreaCorr;
    }

    public double getUnitPriceAreaCorr() {
        return unitPriceAreaCorr;
    }

    public void setUnitPriceAreaCorr(double unitPriceAreaCorr) {
        this.unitPriceAreaCorr = unitPriceAreaCorr;
    }
}