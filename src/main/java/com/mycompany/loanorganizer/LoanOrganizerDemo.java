/*
 * Copyright (c) 2025 Loan Organizer
 * All rights reserved.
 */
package com.mycompany.loanorganizer;

import java.time.LocalDate;
import java.util.List;

/**
 * A demo application showcasing the various features of the loan organizer
 * 
 * @author Pranav Mishra
 */
public class LoanOrganizerDemo {

    /**
     * Main method to run the demo
     * 
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("       LOAN ORGANIZER DEMONSTRATION");
        System.out.println("============================================");
        
        // Demonstrate basic loan functionality
        demonstrateBasicLoan();
        
        // Demonstrate mortgage loan
        demonstrateMortgageLoan();
        
        // Demonstrate auto loan
        demonstrateAutoLoan();
        
        // Demonstrate loan comparison
        demonstrateLoanComparison();
        
        // Demonstrate loan calculator utility
        demonstrateLoanCalculator();
    }
    
    /**
     * Demonstrate basic loan functionality
     */
    private static void demonstrateBasicLoan() {
        System.out.println("\n--- BASIC LOAN DEMONSTRATION ---");
        
        // Create a basic loan
        LoanAccount personalLoan = new LoanAccount(
                "Personal Loan", "Personal", 10000, 0.075, 36);
        
        // Display basic loan info
        System.out.println("Loan Details:");
        System.out.println("Name: " + personalLoan.getLoanName());
        System.out.println("Type: " + personalLoan.getLoanType());
        System.out.println("Principal: " + LoanCalculator.formatCurrency(personalLoan.getPrincipal()));
        System.out.println("Interest Rate: " + LoanCalculator.formatPercent(personalLoan.getAnnualInterestRate()));
        System.out.println("Term: " + personalLoan.getTermInMonths() + " months");
        
        // Calculate and display payment info
        double monthlyPayment = personalLoan.calculateMonthlyPayment(personalLoan.getTermInMonths());
        System.out.println("Monthly Payment: " + LoanCalculator.formatCurrency(monthlyPayment));
        
        double totalInterest = personalLoan.calculateTotalInterest();
        System.out.println("Total Interest: " + LoanCalculator.formatCurrency(totalInterest));
        
        // Record some payments and display history
        System.out.println("\nRecording payments...");
        personalLoan.recordPayment(monthlyPayment, LocalDate.now().minusMonths(2), "First payment");
        personalLoan.recordPayment(monthlyPayment, LocalDate.now().minusMonths(1), "Second payment");
        personalLoan.recordPayment(monthlyPayment + 100, LocalDate.now(), "Third payment with extra principal");
        
        System.out.println("\nPayment History:");
        List<Payment> payments = personalLoan.getPaymentHistory();
        for (Payment payment : payments) {
            System.out.println(payment);
        }
        
        // Display remaining balance
        double remainingBalance = personalLoan.calculateRemainingBalance(LocalDate.now());
        System.out.println("\nRemaining Balance: " + LoanCalculator.formatCurrency(remainingBalance));
        
        // Calculate savings with extra payments
        double extraPayment = 100.0;
        double savings = personalLoan.calculateSavingsWithExtraPayments(extraPayment);
        System.out.println("\nSavings with $100 extra monthly payment: " + 
                LoanCalculator.formatCurrency(savings));
    }
    
    /**
     * Demonstrate mortgage loan functionality
     */
    private static void demonstrateMortgageLoan() {
        System.out.println("\n\n--- MORTGAGE LOAN DEMONSTRATION ---");
        
        // Create a mortgage loan
        MortgageLoan mortgage = new MortgageLoan(
                "Home Loan", 250000, 0.045, 360, 
                "123 Main Street, Anytown, USA",
                300000, 50000, true, 450);
        
        // Display mortgage details
        System.out.println("Mortgage Details:");
        System.out.println(mortgage);
        System.out.println("Property Address: " + mortgage.getPropertyAddress());
        System.out.println("Property Value: " + LoanCalculator.formatCurrency(mortgage.getPropertyValue()));
        System.out.println("Down Payment: " + LoanCalculator.formatCurrency(mortgage.getDownPayment()));
        System.out.println("Escrow Included: " + (mortgage.isEscrowIncluded() ? "Yes" : "No"));
        System.out.println("Escrow Amount: " + LoanCalculator.formatCurrency(mortgage.getEscrowAmount()));
        
        // Calculate and display loan-to-value ratio
        double ltv = mortgage.calculateLoanToValueRatio();
        System.out.println("Loan-to-Value Ratio: " + LoanCalculator.formatPercent(ltv));
        System.out.println("PMI Required: " + (mortgage.isPMIRequired() ? "Yes" : "No"));
        
        // Calculate and display payment details
        double baseMonthlyPayment = mortgage.calculateMonthlyPayment(mortgage.getTermInMonths());
        double totalMonthlyPayment = mortgage.calculateTotalMonthlyPayment();
        
        System.out.println("\nMonthly Principal & Interest: " + 
                LoanCalculator.formatCurrency(baseMonthlyPayment));
        System.out.println("Total Monthly Payment (with escrow): " + 
                LoanCalculator.formatCurrency(totalMonthlyPayment));
        
        // Calculate and display equity
        double equity = mortgage.calculateEquity();
        System.out.println("Current Equity: " + LoanCalculator.formatCurrency(equity));
        
        // Generate amortization schedule for first few payments
        System.out.println("\nAmortization Schedule (first 3 months):");
        List<PaymentDetails> schedule = mortgage.generateAmortizationSchedule();
        System.out.println("Month\tPayment\t\tPrincipal\tInterest\tRemaining Balance");
        for (int i = 0; i < 3 && i < schedule.size(); i++) {
            PaymentDetails payment = schedule.get(i);
            System.out.printf("%d\t%s\t%s\t%s\t%s%n",
                    payment.getMonth(),
                    LoanCalculator.formatCurrency(payment.getMonthlyPayment()),
                    LoanCalculator.formatCurrency(payment.getPrincipalPayment()),
                    LoanCalculator.formatCurrency(payment.getInterestPayment()),
                    LoanCalculator.formatCurrency(payment.getRemainingBalance()));
        }
        System.out.println("... (remaining payments omitted)");
    }
    
    /**
     * Demonstrate auto loan functionality
     */
    private static void demonstrateAutoLoan() {
        System.out.println("\n\n--- AUTO LOAN DEMONSTRATION ---");
        
        // Create an auto loan
        AutoLoan autoLoan = new AutoLoan(
                "Car Loan", 25000, 0.0399, 60,
                "Toyota", "Camry", 2025, "ABC123XYZ456",
                30000, true);
        
        // Display auto loan details
        System.out.println("Auto Loan Details:");
        System.out.println(autoLoan);
        System.out.println("Vehicle: " + autoLoan.getVehicleYear() + " " + 
                autoLoan.getVehicleMake() + " " + autoLoan.getVehicleModel());
        System.out.println("VIN: " + autoLoan.getVin());
        System.out.println("Vehicle Value: " + LoanCalculator.formatCurrency(autoLoan.getVehicleValue()));
        System.out.println("Is New: " + (autoLoan.isNew() ? "Yes" : "No"));
        
        // Calculate and display loan-to-value ratio
        double ltv = autoLoan.calculateLoanToValueRatio();
        System.out.println("Loan-to-Value Ratio: " + LoanCalculator.formatPercent(ltv));
        
        // Calculate and display payment details
        double monthlyPayment = autoLoan.calculateMonthlyPayment(autoLoan.getTermInMonths());
        System.out.println("Monthly Payment: " + LoanCalculator.formatCurrency(monthlyPayment));
        
        // Demonstrate depreciation and underwater status
        System.out.println("\nVehicle Depreciation and Loan Status:");
        System.out.println("Year\tVehicle Value\tLoan Balance\tUnderwater?");
        
        for (int year = 0; year <= 5; year++) {
            double vehicleValue = autoLoan.estimateCurrentValue(year);
            LocalDate futureDate = autoLoan.getStartDate().plusYears(year);
            double loanBalance = autoLoan.calculateRemainingBalance(futureDate);
            boolean isUnderwater = autoLoan.isLoanUnderwater(year);
            
            System.out.printf("%d\t%s\t%s\t%s%n",
                    year,
                    LoanCalculator.formatCurrency(vehicleValue),
                    LoanCalculator.formatCurrency(loanBalance),
                    isUnderwater ? "Yes" : "No");
        }
    }
    
    /**
     * Demonstrate loan comparison functionality
     */
    private static void demonstrateLoanComparison() {
        System.out.println("\n\n--- LOAN COMPARISON DEMONSTRATION ---");
        
        // Create several loans with different terms
        LoanAccount loan1 = new LoanAccount("Loan Option 1", "Personal", 15000, 0.06, 36);
        LoanAccount loan2 = new LoanAccount("Loan Option 2", "Personal", 15000, 0.055, 48);
        LoanAccount loan3 = new LoanAccount("Loan Option 3", "Personal", 15000, 0.049, 60);
        
        // Display details of each loan
        System.out.println("Comparing three $15,000 loans with different terms:\n");
        
        System.out.println("Option 1: " + loan1.getTermInMonths() + " months at " + 
                LoanCalculator.formatPercent(loan1.getAnnualInterestRate()));
        System.out.println("Option 2: " + loan2.getTermInMonths() + " months at " + 
                LoanCalculator.formatPercent(loan2.getAnnualInterestRate()));
        System.out.println("Option 3: " + loan3.getTermInMonths() + " months at " + 
                LoanCalculator.formatPercent(loan3.getAnnualInterestRate()));
        
        // Calculate and display monthly payments
        double payment1 = loan1.calculateMonthlyPayment(loan1.getTermInMonths());
        double payment2 = loan2.calculateMonthlyPayment(loan2.getTermInMonths());
        double payment3 = loan3.calculateMonthlyPayment(loan3.getTermInMonths());
        
        System.out.println("\nMonthly Payments:");
        System.out.println("Option 1: " + LoanCalculator.formatCurrency(payment1));
        System.out.println("Option 2: " + LoanCalculator.formatCurrency(payment2));
        System.out.println("Option 3: " + LoanCalculator.formatCurrency(payment3));
        
        // Calculate and display total interest
        double interest1 = loan1.calculateTotalInterest();
        double interest2 = loan2.calculateTotalInterest();
        double interest3 = loan3.calculateTotalInterest();
        
        System.out.println("\nTotal Interest:");
        System.out.println("Option 1: " + LoanCalculator.formatCurrency(interest1));
        System.out.println("Option 2: " + LoanCalculator.formatCurrency(interest2));
        System.out.println("Option 3: " + LoanCalculator.formatCurrency(interest3));
        
        // Calculate and display total cost
        double totalCost1 = loan1.getPrincipal() + interest1;
        double totalCost2 = loan2.getPrincipal() + interest2;
        double totalCost3 = loan3.getPrincipal() + interest3;
        
        System.out.println("\nTotal Cost:");
        System.out.println("Option 1: " + LoanCalculator.formatCurrency(totalCost1));
        System.out.println("Option 2: " + LoanCalculator.formatCurrency(totalCost2));
        System.out.println("Option 3: " + LoanCalculator.formatCurrency(totalCost3));
        
        // Compare loans using utility method
        System.out.println("\nLoan Comparisons:");
        System.out.println(LoanCalculator.compareLoanCosts(loan1, loan2));
        System.out.println(LoanCalculator.compareLoanCosts(loan2, loan3));
        System.out.println(LoanCalculator.compareLoanCosts(loan1, loan3));
    }
    
    /**
     * Demonstrate loan calculator utility functionality
     */
    private static void demonstrateLoanCalculator() {
        System.out.println("\n\n--- LOAN CALCULATOR UTILITY DEMONSTRATION ---");
        
        // Demonstrate affordable loan amount calculation
        double maxMonthlyPayment = 500.0;
        double interestRate = 0.045;
        int term = 48;
        
        double affordableAmount = LoanCalculator.calculateAffordableLoanAmount(
                maxMonthlyPayment, interestRate, term);
        
        System.out.println("With a maximum monthly payment of " + 
                LoanCalculator.formatCurrency(maxMonthlyPayment) +
                " at " + LoanCalculator.formatPercent(interestRate) +
                " for " + term + " months:");
        System.out.println("Affordable Loan Amount: " + LoanCalculator.formatCurrency(affordableAmount));
        
        // Verify calculation
        double calculatedPayment = LoanCalculator.calculateMonthlyPayment(
                affordableAmount, interestRate, term);
        System.out.println("Verification - Monthly Payment: " + LoanCalculator.formatCurrency(calculatedPayment));
        
        // Demonstrate months until payoff calculation
        double principal = 20000.0;
        double payment = 600.0;
        
        int monthsUntilPayoff = LoanCalculator.calculateMonthsUntilPayoff(
                principal, interestRate, payment);
        
        System.out.println("\nFor a " + LoanCalculator.formatCurrency(principal) + 
                " loan at " + LoanCalculator.formatPercent(interestRate) + 
                " with monthly payments of " + LoanCalculator.formatCurrency(payment) + ":");
        System.out.println("Months until payoff: " + monthsUntilPayoff);
        System.out.println("Years until payoff: " + (monthsUntilPayoff / 12) + 
                " years and " + (monthsUntilPayoff % 12) + " months");
        
        // Demonstrate calculation with insufficient payment
        double lowPayment = 50.0;
        int lowPaymentMonths = LoanCalculator.calculateMonthsUntilPayoff(
                principal, interestRate, lowPayment);
        
        System.out.println("\nFor the same loan with monthly payments of " + 
                LoanCalculator.formatCurrency(lowPayment) + ":");
        
        if (lowPaymentMonths == -1) {
            System.out.println("The loan will never be paid off because the payment is " +
                    "less than the monthly interest.");
        } else {
            System.out.println("Months until payoff: " + lowPaymentMonths);
        }
    }
}
