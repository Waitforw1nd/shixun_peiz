package com.realestate.domain;

public class DecorationAnalysis {
    private String decoration;
    private int count;
    private double avgPrice;
    private double avgUnitPrice;

    public DecorationAnalysis() {}

    public DecorationAnalysis(String decoration, int count, double avgPrice, double avgUnitPrice) {
        this.decoration = decoration;
        this.count = count;
        this.avgPrice = avgPrice;
        this.avgUnitPrice = avgUnitPrice;
    }

    public String getDecoration() {
        return decoration;
    }

    public void setDecoration(String decoration) {
        this.decoration = decoration;
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