/*
 * Copyright (c) 2025 Loan Organizer
 * All rights reserved.
 */
package com.mycompany.loanorganizer;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * The LoanAccount class represents a loan with various properties and methods
 * for calculating payments, interest, and amortization schedules.
 *
 * @author Pranav Mishra
 * @version 2.0
 */
public class LoanAccount {
    private static double defaultInterestRate = 0.05; // 5% default interest rate
    private double annualInterestRate;
    private double principal;
    private String loanName;
    private String loanType;
    private int termInMonths;
    private LocalDate startDate;
    private LocalDate maturityDate;
    private List<Payment> paymentHistory;

    /**
     * Default constructor
     */
    public LoanAccount() {
        this.annualInterestRate = defaultInterestRate;
        this.paymentHistory = new ArrayList<>();
        this.startDate = LocalDate.now();
    }

    /**
     * Constructor with parameters
     * 
     * @param loanName The name of the loan
     * @param loanType The type of loan (e.g., Auto, Personal, Mortgage)
     * @param principal The loan principal amount
     * @param annualInterestRate The annual interest rate (decimal)
     * @param termInMonths The term of the loan in months
     */
    public LoanAccount(String loanName, String loanType, double principal, 
                       double annualInterestRate, int termInMonths) {
        this.loanName = loanName;
        this.loanType = loanType;
        this.principal = principal;
        this.annualInterestRate = annualInterestRate;
        this.termInMonths = termInMonths;
        this.paymentHistory = new ArrayList<>();
        this.startDate = LocalDate.now();
        this.maturityDate = startDate.plusMonths(termInMonths);
    }
    
    /**
     * Calculate the monthly payment amount for the loan
     * 
     * @param numberOfPayments The number of payments to be made
     * @return The monthly payment amount
     */
    public double calculateMonthlyPayment(int numberOfPayments){
        double monthlyInterest = annualInterestRate / 12;
        double monthlyPayment = principal * (monthlyInterest / (1 - Math.pow(1 + monthlyInterest, -numberOfPayments)));
        return monthlyPayment;
    }
    
    /**
     * Calculate the total interest paid over the life of the loan
     * 
     * @return The total interest amount
     */
    public double calculateTotalInterest() {
        double monthlyPayment = calculateMonthlyPayment(termInMonths);
        double totalPayments = monthlyPayment * termInMonths;
        return totalPayments - principal;
    }
    
    /**
     * Generate an amortization schedule for the loan
     * 
     * @return List of payment details for each month
     */
    public List<PaymentDetails> generateAmortizationSchedule() {
        List<PaymentDetails> schedule = new ArrayList<>();
        double monthlyPayment = calculateMonthlyPayment(termInMonths);
        double remainingBalance = principal;
        double monthlyRate = annualInterestRate / 12;
        
        for (int month = 1; month <= termInMonths; month++) {
            double interestPayment = remainingBalance * monthlyRate;
            double principalPayment = monthlyPayment - interestPayment;
            
            remainingBalance -= principalPayment;
            
            LocalDate paymentDate = startDate.plusMonths(month);
            
            PaymentDetails details = new PaymentDetails(
                month, paymentDate, monthlyPayment, principalPayment, 
                interestPayment, remainingBalance
            );
            
            schedule.add(details);
        }
        
        return schedule;
    }
    
    /**
     * Record a payment made on the loan
     * 
     * @param amount The payment amount
     * @param date The date of the payment
     * @param notes Additional notes about the payment
     */
    public void recordPayment(double amount, LocalDate date, String notes) {
        Payment payment = new Payment(amount, date, notes);
        paymentHistory.add(payment);
    }
    
    /**
     * Calculate the remaining balance at a specific point in time
     * 
     * @param asOfDate The date to calculate the balance for
     * @return The remaining balance
     */
    public double calculateRemainingBalance(LocalDate asOfDate) {
        if (asOfDate.isBefore(startDate)) {
            return principal;
        }
        
        if (asOfDate.isAfter(maturityDate)) {
            return 0.0;
        }
        
        long monthsPassed = ChronoUnit.MONTHS.between(startDate, asOfDate);
        double monthlyPayment = calculateMonthlyPayment(termInMonths);
        double remainingBalance = principal;
        double monthlyRate = annualInterestRate / 12;
        
        for (int i = 0; i < monthsPassed; i++) {
            double interestPayment = remainingBalance * monthlyRate;
            double principalPayment = monthlyPayment - interestPayment;
            remainingBalance -= principalPayment;
        }
        
        return remainingBalance;
    }
    
    /**
     * Set the default interest rate for all loans
     * 
     * @param interestRate The default interest rate to set
     */
    public static void setDefaultInterestRate(double interestRate){
        defaultInterestRate = interestRate;
    }
    
    /**
     * Get the annual interest rate of the loan
     * 
     * @return The annual interest rate
     */
    public double getAnnualInterestRate() {
        return annualInterestRate;
    }
    
    /**
     * Set the annual interest rate of the loan
     * 
     * @param annualInterestRate The annual interest rate
     */
    public void setAnnualInterestRate(double annualInterestRate) {
        this.annualInterestRate = annualInterestRate;
    }
    
    /**
     * Get the loan principal amount
     * 
     * @return The principal amount
     */
    public double getPrincipal() {
        return principal;
    }
    
    /**
     * Set the loan principal amount
     * 
     * @param principal The principal amount
     */
    public void setPrincipal(double principal) {
        this.principal = principal;
    }
    
    /**
     * Get the loan name
     * 
     * @return The loan name
     */
    public String getLoanName() {
        return loanName;
    }
    
    /**
     * Set the loan name
     * 
     * @param loanName The loan name
     */
    public void setLoanName(String loanName) {
        this.loanName = loanName;
    }
    
    /**
     * Get the loan type
     * 
     * @return The loan type
     */
    public String getLoanType() {
        return loanType;
    }
    
    /**
     * Set the loan type
     * 
     * @param loanType The loan type
     */
    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }
    
    /**
     * Get the term in months
     * 
     * @return Term in months
     */
    public int getTermInMonths() {
        return termInMonths;
    }
    
    /**
     * Set the term in months
     * 
     * @param termInMonths Term in months
     */
    public void setTermInMonths(int termInMonths) {
        this.termInMonths = termInMonths;
        if (this.startDate != null) {
            this.maturityDate = this.startDate.plusMonths(termInMonths);
        }
    }
    
    /**
     * Get the start date of the loan
     * 
     * @return The start date
     */
    public LocalDate getStartDate() {
        return startDate;
    }
    
    /**
     * Set the start date of the loan
     * 
     * @param startDate The start date
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        if (this.termInMonths > 0) {
            this.maturityDate = startDate.plusMonths(termInMonths);
        }
    }
    
    /**
     * Get the maturity date of the loan
     * 
     * @return The maturity date
     */
    public LocalDate getMaturityDate() {
        return maturityDate;
    }
    
    /**
     * Get payment history for the loan
     * 
     * @return List of payments
     */
    public List<Payment> getPaymentHistory() {
        return new ArrayList<>(paymentHistory);
    }
    
    /**
     * Calculate how much money would be saved by making extra payments
     * 
     * @param extraPayment The amount of extra payment per month
     * @return Amount saved in interest
     */
    public double calculateSavingsWithExtraPayments(double extraPayment) {
        double standardTotalInterest = calculateTotalInterest();
        
        // Calculate new term with extra payments
        double monthlyPayment = calculateMonthlyPayment(termInMonths);
        double effectiveMonthlyPayment = monthlyPayment + extraPayment;
        
        double remainingBalance = principal;
        double monthlyRate = annualInterestRate / 12;
        double totalInterestWithExtra = 0;
        int paymentsNeeded = 0;
        
        while (remainingBalance > 0 && paymentsNeeded < termInMonths) {
            double interestPayment = remainingBalance * monthlyRate;
            double principalPayment = effectiveMonthlyPayment - interestPayment;
            
            if (principalPayment >= remainingBalance) {
                totalInterestWithExtra += (remainingBalance * monthlyRate);
                remainingBalance = 0;
            } else {
                remainingBalance -= principalPayment;
                totalInterestWithExtra += interestPayment;
            }
            
            paymentsNeeded++;
        }
        
        return standardTotalInterest - totalInterestWithExtra;
    }
    
    /**
     * Main method to demonstrate the loan account functionality
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args){
        // Creating loan objects with different configurations
        LoanAccount loan1 = new LoanAccount("Car Loan", "Auto", 5000, 0.01, 36);
        LoanAccount loan2 = new LoanAccount("Home Loan", "Mortgage", 31000, 0.01, 60);
        
        // Display loan information and monthly payments
        displayLoanInformation(loan1, loan2);
        
        // Display comparison at different interest rates
        System.out.println("\n--- PAYMENT COMPARISON AT DIFFERENT INTEREST RATES ---");
        
        // 1% interest rate
        displayInterestRateComparison(loan1, loan2, 0.01);
        
        // 5% interest rate
        displayInterestRateComparison(loan1, loan2, 0.05);
        
        // Generate and display amortization schedule for loan1
        System.out.println("\n--- AMORTIZATION SCHEDULE FOR " + loan1.getLoanName() + " ---");
        displayAmortizationSchedule(loan1, 5); // Display first 5 months
        
        // Calculate and display savings with extra payments
        double extraPayment = 50.0;
        System.out.println("\n--- SAVINGS WITH EXTRA PAYMENTS ---");
        System.out.printf("Making an extra $%.2f payment each month on %s:%n", 
                extraPayment, loan1.getLoanName());
        
        double savings = loan1.calculateSavingsWithExtraPayments(extraPayment);
        System.out.printf("Total interest savings: $%.2f%n", savings);
    }
    
    /**
     * Display loan information for two loans
     * 
     * @param loan1 First loan
     * @param loan2 Second loan
     */
    private static void displayLoanInformation(LoanAccount loan1, LoanAccount loan2) {
        System.out.println("=== LOAN ACCOUNT DETAILS ===");
        System.out.println("Loan 1: " + loan1.getLoanName() + " (" + loan1.getLoanType() + ")");
        System.out.printf("Principal: $%.2f, Interest Rate: %.2f%%, Term: %d months%n", 
                loan1.getPrincipal(), loan1.getAnnualInterestRate() * 100, loan1.getTermInMonths());
        
        System.out.println("\nLoan 2: " + loan2.getLoanName() + " (" + loan2.getLoanType() + ")");
        System.out.printf("Principal: $%.2f, Interest Rate: %.2f%%, Term: %d months%n", 
                loan2.getPrincipal(), loan2.getAnnualInterestRate() * 100, loan2.getTermInMonths());
    }
    
    /**
     * Display payment comparison at a specific interest rate
     * 
     * @param loan1 First loan
     * @param loan2 Second loan
     * @param interestRate Interest rate to use
     */
    private static void displayInterestRateComparison(LoanAccount loan1, LoanAccount loan2, double interestRate) {
        // Save original rates
        double origRate1 = loan1.getAnnualInterestRate();
        double origRate2 = loan2.getAnnualInterestRate();
        
        // Set rates for comparison
        loan1.setAnnualInterestRate(interestRate);
        loan2.setAnnualInterestRate(interestRate);
        
        System.out.printf("%nMonthly payments for loans at %.1f%% interest:%n", interestRate * 100);
        System.out.println("Loan\t3years\t5years\t6years");
        
        // Loan 1 calculations
        String loan1_three_years = String.format("%.2f", loan1.calculateMonthlyPayment(36));
        String loan1_five_years = String.format("%.2f", loan1.calculateMonthlyPayment(60));
        String loan1_six_years = String.format("%.2f", loan1.calculateMonthlyPayment(72));
        
        // Loan 2 calculations
        String loan2_three_years = String.format("%.2f", loan2.calculateMonthlyPayment(36));
        String loan2_five_years = String.format("%.2f", loan2.calculateMonthlyPayment(60));
        String loan2_six_years = String.format("%.2f", loan2.calculateMonthlyPayment(72));
        
        // Print results
        System.out.println(loan1.getLoanName() + "\t" + loan1_three_years + "\t" + 
                loan1_five_years + "\t" + loan1_six_years);
        System.out.println(loan2.getLoanName() + "\t" + loan2_three_years + "\t" + 
                loan2_five_years + "\t" + loan2_six_years);
        
        // Restore original rates
        loan1.setAnnualInterestRate(origRate1);
        loan2.setAnnualInterestRate(origRate2);
    }
    
    /**
     * Display an amortization schedule for a loan
     * 
     * @param loan The loan
     * @param monthsToShow Number of months to show
     */
    private static void displayAmortizationSchedule(LoanAccount loan, int monthsToShow) {
        List<PaymentDetails> schedule = loan.generateAmortizationSchedule();
        int months = Math.min(monthsToShow, schedule.size());
        
        System.out.println("Month\tPayment\tPrincipal\tInterest\tRemaining Balance");
        for (int i = 0; i < months; i++) {
            PaymentDetails payment = schedule.get(i);
            System.out.printf("%d\t$%.2f\t$%.2f\t$%.2f\t$%.2f%n",
                    payment.getMonth(),
                    payment.getMonthlyPayment(),
                    payment.getPrincipalPayment(),
                    payment.getInterestPayment(),
                    payment.getRemainingBalance());
        }
        System.out.println("... (remaining payments omitted)");
    }
}
