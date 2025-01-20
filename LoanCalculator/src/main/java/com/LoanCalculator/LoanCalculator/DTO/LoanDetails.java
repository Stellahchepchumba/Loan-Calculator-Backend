package com.LoanCalculator.LoanCalculator.DTO;

public class LoanDetails {
    private double principal;
    private double interestRate;
    private int years;

    public double getPrincipal() {
        return principal;
    }

    public void setPrincipal(double principal) {
        this.principal = principal;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    @Override
    public String toString() {
        return "LoanDetails{" +
                "principal=" + principal +
                ", interestRate=" + interestRate +
                ", years=" + years +
                '}';
    }
}
