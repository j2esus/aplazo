package com.aplazo.scheme.utils;

public enum SchemeDefault {
    SCHEME_5x10(10, 5),
    SCHEME_5x12(12, 5),
    SCHEME_8x16(16, 8);

    private double percentage;
    private int numOfPayments;

    SchemeDefault(int percentage, int numOfPayments) {
        this.percentage = percentage;
        this.numOfPayments = numOfPayments;
    }

    public double getPercentage() {
        return percentage;
    }

    public int getNumOfPayments() {
        return numOfPayments;
    }
}
