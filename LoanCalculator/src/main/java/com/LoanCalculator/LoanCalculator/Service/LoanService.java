package com.LoanCalculator.LoanCalculator.Service;

import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoanService {

    public double calculateMonthlyPayment(double principal, double annualRate, int termYears) {
        double monthlyRate = (annualRate / 100) / 12; 
        int totalMonths = termYears * 12; 
        return (principal * monthlyRate * Math.pow(1 + monthlyRate, totalMonths)) /
                (Math.pow(1 + monthlyRate, totalMonths) - 1);
    }

    public List<String> getAmortizationSchedule(double principal, double annualRate, int termYears) {
        double monthlyPayment = calculateMonthlyPayment(principal, annualRate, termYears);
        double balance = principal;
        double monthlyRate = (annualRate / 100) / 12;

        List<String> schedule = new ArrayList<>();
        schedule.add("Month,Payment,Principal,Interest,Remaining Balance");

        int totalMonths = termYears * 12;
        for (int month = 1; month <= totalMonths; month++) {
            double interest = balance * monthlyRate;
            double principalPaid = monthlyPayment - interest;
            balance -= principalPaid;

            schedule.add(String.format("%d,%.2f,%.2f,%.2f,%.2f",
                    month, monthlyPayment, principalPaid, interest, Math.max(balance, 0)));
        }

        return schedule;
    }

    public String exportToCSV(List<String> schedule) {
        String fileName = "Amortization_Schedule.csv";

        try (FileWriter fileWriter = new FileWriter(fileName)) {
            for (String line : schedule) {
                fileWriter.write(line + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while writing the CSV file: " + fileName, e);
        }

        return fileName;
    }

    public double calculateYearlyPayment(double principal, double annualRate, int termYears) {
        double yearlyRate = annualRate / 100; 
        return (principal * yearlyRate * Math.pow(1 + yearlyRate, termYears)) /
                (Math.pow(1 + yearlyRate, termYears) - 1);
    }

    public List<String> getYearlyAmortizationSchedule(double principal, double annualRate, int termYears) {
        double yearlyPayment = calculateYearlyPayment(principal, annualRate, termYears);
        double balance = principal;
        double yearlyRate = annualRate / 100;

        List<String> schedule = new ArrayList<>();
        schedule.add("Year,Payment,Principal,Interest,Remaining Balance");

        for (int year = 1; year <= termYears; year++) {
            double interest = balance * yearlyRate;
            double principalPaid = yearlyPayment - interest;
            balance -= principalPaid;

            schedule.add(String.format("%d,%.2f,%.2f,%.2f,%.2f",
                    year, yearlyPayment, principalPaid, interest, Math.max(balance, 0)));
        }

        return schedule;
    }
}
