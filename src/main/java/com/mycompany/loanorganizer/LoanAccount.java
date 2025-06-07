/*
 * Copyright (c) 2025 Loan Organizer
 * All rights reserv    /**
     * Constructor with principal and interest rate
     * 
     * @param principal The loan amount
     * @param annualInterestRate The annual interest rate (decimal)
     * @throws LoanException If parameters are invalid
     */
    public LoanAccount(double principal, double annualInterestRate) throws LoanException {
        this();
        validatePrincipal(principal);
        validateInterestRate(annualInterestRate);
        this.principal = principal;
        this.annualInterestRate = annualInterestRate;
    }kage com.mycompany.loanorganizer;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * LoanAccount class represents a general loan with methods for calculations
 * and tracking of loan information.
 * 
 * @author Loan Organizer Team
 * @version 1.0
 */
public class LoanAccount {
    // Class constants
    private static double defaultInterestRate = 5.0;
    private static final double MIN_PRINCIPAL = 1.0;
    private static final double MAX_PRINCIPAL = 10000000.0; // 10 million
    private static final double MIN_INTEREST_RATE = 0.0;
    private static final double MAX_INTEREST_RATE = 0.5; // 50%
    private static final int MIN_TERM = 1; // 1 month
    private static final int MAX_TERM = 600; // 50 years
    
    // Instance variables
    private String loanName;
    private String loanType;
    private double principal;
    private double annualInterestRate;
    private int termInMonths;
    private LocalDate startDate;
    private LocalDate maturityDate;
    private List<Payment> paymentHistory;
    private boolean isActive;
    
    /**
     * Default constructor
     */
    public LoanAccount() {
        this.principal = 0.0;
        this.annualInterestRate = defaultInterestRate;
        this.termInMonths = 0;
        this.loanName = "New Loan";
        this.loanType = "General";
        this.startDate = LocalDate.now();
        this.maturityDate = null;
        this.paymentHistory = new ArrayList<>();
        this.isActive = true;
    }
    
    /**
     * Constructor with principal and interest rate
     * 
     * @param principal The loan amount
     * @param annualInterestRate The annual interest rate (percentage)
     */
    public LoanAccount(double principal, double annualInterestRate) {
        this();
        this.principal = principal;
        this.annualInterestRate = annualInterestRate;
    }
    
    /**
     * Constructor with all basic loan parameters
     * 
     * @param loanName The name of the loan
     * @param loanType The type of loan
     * @param principal The loan amount
     * @param annualInterestRate The annual interest rate (percentage)
     * @param termInMonths The term of the loan in months
     * @param startDate The start date of the loan
     */
    public LoanAccount(String loanName, String loanType, double principal, 
            double annualInterestRate, int termInMonths, LocalDate startDate) {
        this(principal, annualInterestRate);
        this.loanName = loanName;
        this.loanType = loanType;
        this.termInMonths = termInMonths;
        this.startDate = startDate;
        this.maturityDate = startDate.plusMonths(termInMonths);
    }
    
    /**
     * Set the default interest rate for new loans
     * 
     * @param interestRate The default interest rate
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
     * @throws LoanException If the interest rate is invalid
     */
    public void setAnnualInterestRate(double annualInterestRate) throws LoanException {
        validateInterestRate(annualInterestRate);
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
     * @throws LoanException If principal is invalid
     */
    public void setPrincipal(double principal) throws LoanException {
        validatePrincipal(principal);
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
     * @throws LoanException If the term is invalid
     */
    public void setTermInMonths(int termInMonths) throws LoanException {
        validateTerm(termInMonths);
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
    }hronoUnit;
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
        double monthlyPayment = principal * ( monthlyInterest / (1 - Math.pow(1 + monthlyInterest, -numberOfPayments)));
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
        
        if (maturityDate != null && asOfDate.isAfter(maturityDate)) {
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
        
        return Math.max(0, remainingBalance);
    }
    
    /**
     * Check if the loan is active
     * 
     * @return True if the loan is active, false otherwise
     */
    public boolean isActive() {
        return isActive;
    }
    
    /**
     * Set the loan as active or inactive
     * 
     * @param active True to set as active, false for inactive
     */
    public void setActive(boolean active) {
        this.isActive = active;
    }
    
    /**
     * Format a currency amount
     * 
     * @param amount The amount to format
     * @return The formatted amount as a string
     */
    public static String formatCurrency(double amount) {
        return String.format("$%.2f", amount);
    }
    
    /**
     * Set the default interest rate for all loans
     * 
     * @param interestRate The default interest rate to set
     */
    public static void setDefaultInterestRate(double interestRate) {
        defaultInterestRate = interestRate;
    }
    
    /**
     * Set the annual interest rate for all loan objects statically
     * 
     * @param rate The annual interest rate to set
     */
    public static void setAnnualInterestRate(double rate) {
        defaultInterestRate = rate;
    }
    
    /**
     * Validate the loan principal amount
     * 
     * @param principal The principal to validate
     * @throws LoanException If the principal is invalid
     */
    private void validatePrincipal(double principal) throws LoanException {
        if (principal <= 0) {
            throw new LoanException("Principal amount must be greater than zero");
        }
    }
    
    /**
     * Validate the interest rate
     * 
     * @param interestRate The interest rate to validate
     * @throws LoanException If the interest rate is invalid
     */
    private void validateInterestRate(double interestRate) throws LoanException {
        if (interestRate < 0) {
            throw new LoanException("Interest rate cannot be negative");
        }
        if (interestRate > 1.0) {
            throw new LoanException("Interest rate should be expressed as a decimal (e.g., 0.05 for 5%)");
        }
    }
    
    /**
     * Validate the term in months
     * 
     * @param termInMonths The term to validate
     * @throws LoanException If the term is invalid
     */
    private void validateTerm(int termInMonths) throws LoanException {
        if (termInMonths <= 0) {
            throw new LoanException("Loan term must be greater than zero");
        }
        if (termInMonths > 600) { // 50 years max
            throw new LoanException("Loan term exceeds maximum allowed (600 months)");
        }
    }
    
    /**
     * Main method to demonstrate loan calculations
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Creating objects loan1 an loan2
        LoanAccount loan1 = new LoanAccount();
        LoanAccount loan2 = new LoanAccount();
        
        // Assigning the principal amounts for the respective objects
        loan1.principal = 5000;
        loan2.principal = 31000;
        
        // Setting the annual interest rate
        setAnnualInterestRate(0.01);
        
        // Formatting the monthly payments for the respective loan objects according to the number of months
        String loan1_three_years = String.format("%.2f",loan1.calculateMonthlyPayment(36));
        String loan1_five_years = String.format("%.2f",loan1.calculateMonthlyPayment(60));
        String loan1_six_years = String.format("%.2f",loan1.calculateMonthlyPayment(72));
        String loan2_three_years = String.format("%.2f",loan2.calculateMonthlyPayment(36));
        String loan2_five_years = String.format("%.2f",loan2.calculateMonthlyPayment(60));
        String loan2_six_years = String.format("%.2f",loan2.calculateMonthlyPayment(72));
        
        // Printing the output
        System.out.println("Monthly payments for loan1 of $5000.00 and loan2 for 3,5 and 6 year loans at 1% interest.");
        System.out.println("Loan\t3years\t5years\t6years");
        System.out.println("Loan1\t" + loan1_three_years+ "\t" + loan1_five_years + "\t" + loan1_six_years);
        System.out.println("Loan2\t" + loan2_three_years+ "\t" + loan2_five_years + "\t" + loan2_six_years);
        
        // Newline
        System.out.println();
        
        // Setting the annual interest rate
        setAnnualInterestRate(0.05);
        
        // Formatting the monthly payments for the respective loan objects according to the number of months
        loan1_three_years = String.format("%.2f",loan1.calculateMonthlyPayment(36));
        loan1_five_years = String.format("%.2f",loan1.calculateMonthlyPayment(60));
        loan1_six_years = String.format("%.2f",loan1.calculateMonthlyPayment(72));
        loan2_three_years = String.format("%.2f",loan2.calculateMonthlyPayment(36));
        loan2_five_years = String.format("%.2f",loan2.calculateMonthlyPayment(60));
        loan2_six_years = String.format("%.2f",loan2.calculateMonthlyPayment(72));
        
        // Printing the output
        System.out.println("Monthly payments for loan1 of $5000.00 and loan2 for 3,5 and 6 year loans at 5% interest.");
        System.out.println("Loan\t3years\t5years\t6years");
        System.out.println("Loan1\t" + loan1_three_years+"\t" + loan1_five_years + "\t" +  loan1_six_years);
        System.out.println("Loan2\t" + loan2_three_years+"\t" + loan2_five_years + "\t" + loan2_six_years);
        
        System.out.println("\nNote: For more comprehensive demonstrations, please run the LoanOrganizerDemo class.");
    }
    
    /**
     * Validate the loan principal amount
     * 
     * @param principal The principal to validate
     * @throws LoanException If the principal is invalid
     */
    private void validatePrincipal(double principal) throws LoanException {
        if (principal <= 0) {
            throw new LoanException("Principal amount must be greater than zero");
        }
    }
    
    /**
     * Validate the interest rate
     * 
     * @param interestRate The interest rate to validate
     * @throws LoanException If the interest rate is invalid
     */
    private void validateInterestRate(double interestRate) throws LoanException {
        if (interestRate < 0) {
            throw new LoanException("Interest rate cannot be negative");
        }
        if (interestRate > 1.0) {
            throw new LoanException("Interest rate should be expressed as a decimal (e.g., 0.05 for 5%)");
        }
    }
    
    /**
     * Validate the term in months
     * 
     * @param termInMonths The term to validate
     * @throws LoanException If the term is invalid
     */
    private void validateTermInMonths(int termInMonths) throws LoanException {
        if (termInMonths <= 0) {
            throw new LoanException("Loan term must be greater than zero");
        }
        if (termInMonths > 600) { // 50 years max
            throw new LoanException("Loan term exceeds maximum allowed (600 months)");
        }
    }
    
    /**
     * Validate loan name
     * 
     * @param loanName The loan name to validate
     * @throws LoanException If the loan name is invalid
     */
    private void validateLoanName(String loanName) throws LoanException {
        if (loanName == null || loanName.trim().isEmpty()) {
            throw new LoanException("Loan name cannot be empty");
        }
    }
    
    /**
     * Validate loan type
     * 
     * @param loanType The loan type to validate
     * @throws LoanException If the loan type is invalid
     */
    private void validateLoanType(String loanType) throws LoanException {
        if (loanType == null || loanType.trim().isEmpty()) {
            throw new LoanException("Loan type cannot be empty");
        }
    }
}