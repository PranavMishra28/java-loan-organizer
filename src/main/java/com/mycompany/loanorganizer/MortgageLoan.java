/*
 * Copyright (c) 2025 Loan Organizer
 * All rights reserved.
 */
package com.mycompany.loanorganizer;

/**
 * Represents a mortgage loan with specific features for home loans
 * 
 * @author Pranav Mishra
 */
public class MortgageLoan extends LoanAccount {
    private String propertyAddress;
    private double propertyValue;
    private double downPayment;
    private boolean escrowIncluded;
    private double escrowAmount;
    
    /**
     * Default constructor
     */
    public MortgageLoan() {
        super();
        this.setLoanType("Mortgage");
    }
    
    /**
     * Constructor with parameters for mortgage loan
     * 
     * @param loanName The name of the loan
     * @param principal The loan principal amount
     * @param annualInterestRate The annual interest rate
     * @param termInMonths The term of the loan in months
     * @param propertyAddress The address of the property
     * @param propertyValue The value of the property
     * @param downPayment The down payment made
     * @param escrowIncluded Whether escrow is included
     * @param escrowAmount The escrow amount if included
     */
    public MortgageLoan(String loanName, double principal, double annualInterestRate,
                        int termInMonths, String propertyAddress, double propertyValue,
                        double downPayment, boolean escrowIncluded, double escrowAmount) {
        super(loanName, "Mortgage", principal, annualInterestRate, termInMonths);
        this.propertyAddress = propertyAddress;
        this.propertyValue = propertyValue;
        this.downPayment = downPayment;
        this.escrowIncluded = escrowIncluded;
        this.escrowAmount = escrowAmount;
    }
    
    /**
     * Calculate loan-to-value ratio
     * 
     * @return The loan-to-value ratio
     */
    public double calculateLoanToValueRatio() {
        return getPrincipal() / propertyValue;
    }
    
    /**
     * Calculate the total monthly payment including escrow if applicable
     * 
     * @return The total monthly payment
     */
    public double calculateTotalMonthlyPayment() {
        double basePayment = calculateMonthlyPayment(getTermInMonths());
        return escrowIncluded ? basePayment + escrowAmount : basePayment;
    }
    
    /**
     * Check if private mortgage insurance (PMI) is required
     * 
     * @return True if PMI is required
     */
    public boolean isPMIRequired() {
        // PMI is typically required if LTV is greater than 80%
        return calculateLoanToValueRatio() > 0.8;
    }
    
    /**
     * Calculate the equity in the property
     * 
     * @return The equity amount
     */
    public double calculateEquity() {
        return propertyValue - getPrincipal();
    }
    
    /**
     * Get the property address
     * 
     * @return The property address
     */
    public String getPropertyAddress() {
        return propertyAddress;
    }
    
    /**
     * Set the property address
     * 
     * @param propertyAddress The property address
     */
    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress;
    }
    
    /**
     * Get the property value
     * 
     * @return The property value
     */
    public double getPropertyValue() {
        return propertyValue;
    }
    
    /**
     * Set the property value
     * 
     * @param propertyValue The property value
     */
    public void setPropertyValue(double propertyValue) {
        this.propertyValue = propertyValue;
    }
    
    /**
     * Get the down payment
     * 
     * @return The down payment
     */
    public double getDownPayment() {
        return downPayment;
    }
    
    /**
     * Set the down payment
     * 
     * @param downPayment The down payment
     */
    public void setDownPayment(double downPayment) {
        this.downPayment = downPayment;
    }
    
    /**
     * Check if escrow is included
     * 
     * @return True if escrow is included
     */
    public boolean isEscrowIncluded() {
        return escrowIncluded;
    }
    
    /**
     * Set whether escrow is included
     * 
     * @param escrowIncluded True if escrow is included
     */
    public void setEscrowIncluded(boolean escrowIncluded) {
        this.escrowIncluded = escrowIncluded;
    }
    
    /**
     * Get the escrow amount
     * 
     * @return The escrow amount
     */
    public double getEscrowAmount() {
        return escrowAmount;
    }
    
    /**
     * Set the escrow amount
     * 
     * @param escrowAmount The escrow amount
     */
    public void setEscrowAmount(double escrowAmount) {
        this.escrowAmount = escrowAmount;
    }
    
    @Override
    public String toString() {
        return String.format("Mortgage Loan: %s - $%.2f at %.2f%% for %d months on property at %s",
                getLoanName(), getPrincipal(), getAnnualInterestRate() * 100, getTermInMonths(), propertyAddress);
    }
}
