/*
 * Copyright (c) 2025 Loan Organizer
 * All rights reserved.
 */
package com.mycompany.loanorganizer;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class with various loan calculation methods
 * 
 * @author Pranav Mishra
 */
public class LoanCalculator {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    
    /**
     * Private constructor to prevent instantiation
     */
    private LoanCalculator() {
        // Utility class should not be instantiated
    }
    
    /**
     * Calculate the total cost of a loan
     * 
     * @param principal The loan principal
     * @param annualInterestRate The annual interest rate
     * @param termInMonths The term of the loan in months
     * @return The total cost of the loan
     */
    public static double calculateTotalLoanCost(double principal, double annualInterestRate, int termInMonths) {
        double monthlyInterest = annualInterestRate / 12;
        double monthlyPayment = principal * (monthlyInterest / (1 - Math.pow(1 + monthlyInterest, -termInMonths)));
        return monthlyPayment * termInMonths;
    }
    
    /**
     * Calculate the monthly payment for a loan
     * 
     * @param principal The loan principal
     * @param annualInterestRate The annual interest rate
     * @param termInMonths The term of the loan in months
     * @return The monthly payment
     */
    public static double calculateMonthlyPayment(double principal, double annualInterestRate, int termInMonths) {
        double monthlyInterest = annualInterestRate / 12;
        return principal * (monthlyInterest / (1 - Math.pow(1 + monthlyInterest, -termInMonths)));
    }
    
    /**
     * Calculate the total interest to be paid over the life of a loan
     * 
     * @param principal The loan principal
     * @param annualInterestRate The annual interest rate
     * @param termInMonths The term of the loan in months
     * @return The total interest
     */
    public static double calculateTotalInterest(double principal, double annualInterestRate, int termInMonths) {
        double totalCost = calculateTotalLoanCost(principal, annualInterestRate, termInMonths);
        return totalCost - principal;
    }
    
    /**
     * Generate a complete amortization schedule for a loan
     * 
     * @param principal The loan principal
     * @param annualInterestRate The annual interest rate
     * @param termInMonths The term of the loan in months
     * @param startDate The start date of the loan
     * @return List of payment details
     */
    public static List<PaymentDetails> generateAmortizationSchedule(
            double principal, double annualInterestRate, int termInMonths, LocalDate startDate) {
        
        List<PaymentDetails> schedule = new ArrayList<>();
        double monthlyPayment = calculateMonthlyPayment(principal, annualInterestRate, termInMonths);
        double remainingBalance = principal;
        double monthlyRate = annualInterestRate / 12;
        
        for (int month = 1; month <= termInMonths; month++) {
            double interestPayment = remainingBalance * monthlyRate;
            double principalPayment = monthlyPayment - interestPayment;
            
            if (month == termInMonths) {
                // Handle rounding issues in the final payment
                principalPayment = remainingBalance;
                monthlyPayment = principalPayment + interestPayment;
            }
            
            remainingBalance -= principalPayment;
            
            if (remainingBalance < 0) {
                remainingBalance = 0;
            }
            
            LocalDate paymentDate = startDate.plusMonths(month);
            
            PaymentDetails details = new PaymentDetails(
                month, paymentDate, monthlyPayment, 
                principalPayment, interestPayment, remainingBalance
            );
            
            schedule.add(details);
        }
        
        return schedule;
    }
    
    /**
     * Calculate how long until a loan is paid off
     * 
     * @param principal The loan principal
     * @param annualInterestRate The annual interest rate
     * @param monthlyPayment The monthly payment amount
     * @return The number of months until payoff
     */
    public static int calculateMonthsUntilPayoff(double principal, double annualInterestRate, double monthlyPayment) {
        if (principal <= 0 || monthlyPayment <= 0) {
            return 0;
        }
        
        double monthlyRate = annualInterestRate / 12;
        
        // If the monthly payment is less than the interest, the loan will never be paid off
        double monthlyInterest = principal * monthlyRate;
        if (monthlyPayment <= monthlyInterest) {
            return -1; // Indicates the loan will never be paid off
        }
        
        // Calculate the number of months
        double n = Math.log(monthlyPayment / (monthlyPayment - principal * monthlyRate)) / 
                  Math.log(1 + monthlyRate);
        
        return (int) Math.ceil(n);
    }
    
    /**
     * Calculate the affordable loan amount based on maximum monthly payment
     * 
     * @param maxMonthlyPayment The maximum monthly payment
     * @param annualInterestRate The annual interest rate
     * @param termInMonths The term of the loan in months
     * @return The affordable loan amount
     */
    public static double calculateAffordableLoanAmount(
            double maxMonthlyPayment, double annualInterestRate, int termInMonths) {
        
        double monthlyRate = annualInterestRate / 12;
        return maxMonthlyPayment / (monthlyRate / (1 - Math.pow(1 + monthlyRate, -termInMonths)));
    }
    
    /**
     * Format a currency amount
     * 
     * @param amount The amount
     * @return Formatted currency string
     */
    public static String formatCurrency(double amount) {
        return String.format("$%.2f", amount);
    }
    
    /**
     * Format a percentage
     * 
     * @param percent The percentage as a decimal
     * @return Formatted percentage string
     */
    public static String formatPercent(double percent) {
        return String.format("%.2f%%", percent * 100);
    }
    
    /**
     * Format a date
     * 
     * @param date The date to format
     * @return Formatted date string
     */
    public static String formatDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }
    
    /**
     * Compare two loans and determine which has the lower total cost
     * 
     * @param loan1 First loan
     * @param loan2 Second loan
     * @return A string indicating which loan has the lower cost
     */
    public static String compareLoanCosts(LoanAccount loan1, LoanAccount loan2) {
        double totalCost1 = calculateTotalLoanCost(
                loan1.getPrincipal(), loan1.getAnnualInterestRate(), loan1.getTermInMonths());
        double totalCost2 = calculateTotalLoanCost(
                loan2.getPrincipal(), loan2.getAnnualInterestRate(), loan2.getTermInMonths());
        
        if (totalCost1 < totalCost2) {
            return String.format("%s has a lower total cost by %s", 
                    loan1.getLoanName(), formatCurrency(totalCost2 - totalCost1));
        } else if (totalCost2 < totalCost1) {
            return String.format("%s has a lower total cost by %s", 
                    loan2.getLoanName(), formatCurrency(totalCost1 - totalCost2));
        } else {
            return "Both loans have the same total cost";
        }
    }
}
