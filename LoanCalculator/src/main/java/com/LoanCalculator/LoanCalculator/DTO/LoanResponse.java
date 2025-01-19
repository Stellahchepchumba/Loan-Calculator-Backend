package com.LoanCalculator.LoanCalculator.DTO;

import java.util.List;

public class LoanResponse {
    private double monthlyPayment;
    private List<String> amortizationSchedule;

    public LoanResponse(double monthlyPayment, List<String> amortizationSchedule) {
        this.monthlyPayment = monthlyPayment;
        this.amortizationSchedule = amortizationSchedule;
    }

    public double getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public List<String> getAmortizationSchedule() {
        return amortizationSchedule;
    }

    public void setAmortizationSchedule(List<String> amortizationSchedule) {
        this.amortizationSchedule = amortizationSchedule;
    }
}
