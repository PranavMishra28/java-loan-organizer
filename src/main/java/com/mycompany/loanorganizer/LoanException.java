/*
 * Copyright (c) 2025 Loan Organizer
 * All rights reserved.
 */
package com.mycompany.loanorganizer;

/**
 * Custom exception class for loan-related validation errors
 * 
 * @author Loan Organizer Team
 */
public class LoanException extends Exception {
    
    /**
     * Default constructor
     */
    public LoanException() {
        super("An error occurred with the loan.");
    }
    
    /**
     * Constructor with a custom message
     * 
     * @param message The error message
     */
    public LoanException(String message) {
        super(message);
    }
    
    /**
     * Constructor with a cause
     * 
     * @param cause The cause of the exception
     */
    public LoanException(Throwable cause) {
        super(cause);
    }
    
    /**
     * Constructor with message and cause
     * 
     * @param message The error message
     * @param cause The cause of the exception
     */
    public LoanException(String message, Throwable cause) {
        super(message, cause);
    }
}
