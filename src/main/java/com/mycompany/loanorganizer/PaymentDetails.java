/*
 * Copyright (c) 2025 Loan Organizer
 * All rights reserved.
 */
package com.mycompany.loanorganizer;

import java.time.LocalDate;

/**
 * Contains details about a specific payment in an amortization schedule
 * 
 * @author Pranav Mishra
 */
public class PaymentDetails {
    private int month;
    private LocalDate paymentDate;
    private double monthlyPayment;
    private double principalPayment;
    private double interestPayment;
    private double remainingBalance;
    
    /**
     * Constructor for payment details
     * 
     * @param month The month number
     * @param paymentDate The payment date
     * @param monthlyPayment The total monthly payment
     * @param principalPayment The principal portion of payment
     * @param interestPayment The interest portion of payment
     * @param remainingBalance The remaining balance after payment
     */
    public PaymentDetails(int month, LocalDate paymentDate, double monthlyPayment, 
                         double principalPayment, double interestPayment, double remainingBalance) {
        this.month = month;
        this.paymentDate = paymentDate;
        this.monthlyPayment = monthlyPayment;
        this.principalPayment = principalPayment;
        this.interestPayment = interestPayment;
        this.remainingBalance = remainingBalance;
    }
    
    /**
     * Get the month number
     * 
     * @return The month number
     */
    public int getMonth() {
        return month;
    }
    
    /**
     * Get the payment date
     * 
     * @return The payment date
     */
    public LocalDate getPaymentDate() {
        return paymentDate;
    }
    
    /**
     * Get the monthly payment amount
     * 
     * @return The monthly payment
     */
    public double getMonthlyPayment() {
        return monthlyPayment;
    }
    
    /**
     * Get the principal portion of the payment
     * 
     * @return The principal payment
     */
    public double getPrincipalPayment() {
        return principalPayment;
    }
    
    /**
     * Get the interest portion of the payment
     * 
     * @return The interest payment
     */
    public double getInterestPayment() {
        return interestPayment;
    }
    
    /**
     * Get the remaining balance after this payment
     * 
     * @return The remaining balance
     */
    public double getRemainingBalance() {
        return remainingBalance;
    }
}
