package com.LoanCalculator.LoanCalculator.Controller;

import com.LoanCalculator.LoanCalculator.DTO.LoanDetails;
import com.LoanCalculator.LoanCalculator.DTO.LoanResponse;
import com.LoanCalculator.LoanCalculator.Service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    
    @PostMapping("/calculate")
    public ResponseEntity<LoanResponse> calculateLoan(
            @RequestBody LoanDetails loanDetails,
            @RequestParam(required = false, defaultValue = "monthly") String frequency) {
        try {
            double payment;
            List<String> schedule;

            if ("yearly".equalsIgnoreCase(frequency)) {
                payment = loanService.calculateYearlyPayment(
                        loanDetails.getPrincipal(),
                        loanDetails.getInterestRate(),
                        loanDetails.getYears());
                schedule = loanService.getYearlyAmortizationSchedule(
                        loanDetails.getPrincipal(),
                        loanDetails.getInterestRate(),
                        loanDetails.getYears());
            } else {
                payment = loanService.calculateMonthlyPayment(
                        loanDetails.getPrincipal(),
                        loanDetails.getInterestRate(),
                        loanDetails.getYears());
                schedule = loanService.getAmortizationSchedule(
                        loanDetails.getPrincipal(),
                        loanDetails.getInterestRate(),
                        loanDetails.getYears());
            }

            LoanResponse response = new com.LoanCalculator.LoanCalculator.DTO.LoanResponse(payment, schedule);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); 
        }
    }


    @PostMapping("/export")
    public ResponseEntity<byte[]> exportSchedule(
            @RequestBody com.LoanCalculator.LoanCalculator.DTO.LoanDetails loanDetails,
            @RequestParam(required = false, defaultValue = "monthly") String frequency) {

        try {
            List<String> schedule;

            if ("yearly".equalsIgnoreCase(frequency)) {
                schedule = loanService.getYearlyAmortizationSchedule(
                        loanDetails.getPrincipal(),
                        loanDetails.getInterestRate(),
                        loanDetails.getYears());
            } else {
                schedule = loanService.getAmortizationSchedule(
                        loanDetails.getPrincipal(),
                        loanDetails.getInterestRate(),
                        loanDetails.getYears());
            }

            String fileName = loanService.exportToCSV(schedule);
            File file = new File(fileName);

            byte[] fileContent = Files.readAllBytes(file.toPath());

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
            headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");

            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

