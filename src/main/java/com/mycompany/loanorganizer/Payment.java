/*
 * Copyright (c) 2025 Loan Organizer
 * All rights reserved.
 */
package com.mycompany.loanorganizer;

import java.time.LocalDate;

/**
 * Represents a payment made on a loan
 * 
 * @author Pranav Mishra
 */
public class Payment {
    private double amount;
    private LocalDate date;
    private String notes;
    
    /**
     * Constructor for a payment
     * 
     * @param amount The payment amount
     * @param date The payment date
     * @param notes Additional notes about the payment
     */
    public Payment(double amount, LocalDate date, String notes) {
        this.amount = amount;
        this.date = date;
        this.notes = notes;
    }
    
    /**
     * Get the payment amount
     * 
     * @return The payment amount
     */
    public double getAmount() {
        return amount;
    }
    
    /**
     * Set the payment amount
     * 
     * @param amount The payment amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    /**
     * Get the payment date
     * 
     * @return The payment date
     */
    public LocalDate getDate() {
        return date;
    }
    
    /**
     * Set the payment date
     * 
     * @param date The payment date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    /**
     * Get notes about the payment
     * 
     * @return The notes
     */
    public String getNotes() {
        return notes;
    }
    
    /**
     * Set notes about the payment
     * 
     * @param notes The notes
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    @Override
    public String toString() {
        return String.format("Payment: $%.2f on %s - %s", amount, date, notes);
    }
}
