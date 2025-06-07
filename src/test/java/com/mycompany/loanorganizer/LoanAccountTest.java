/*
 * Copyright (c) 2025 Loan Organizer
 * All rights reserved.
 */
package com.mycompany.loanorganizer;

import java.time.LocalDate;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.time.temporal.ChronoUnit;

/**
 * Unit tests for the LoanAccount class
 * 
 * @author Loan Organizer Team
 */
public class LoanAccountTest {
    private LoanAccount testLoan;
    private static final double DELTA = 0.01; // Delta for double comparisons
    
    @Before
    public void setUp() {
        testLoan = new LoanAccount("Test Loan", "Personal", 10000, 0.05, 60);
    }
    
    @Test
    public void testConstructor() {
        assertEquals("Test Loan", testLoan.getLoanName());
        assertEquals("Personal", testLoan.getLoanType());
        assertEquals(10000, testLoan.getPrincipal(), DELTA);
        assertEquals(0.05, testLoan.getAnnualInterestRate(), DELTA);
        assertEquals(60, testLoan.getTermInMonths());
        assertNotNull(testLoan.getStartDate());
        assertNotNull(testLoan.getMaturityDate());
        assertTrue(testLoan.isActive());
    }
    
    @Test
    public void testCalculateMonthlyPayment() {
        // Expected monthly payment with $10,000 principal, 5% annual interest, 60 months
        // Formula: P * (r * (1 + r)^n) / ((1 + r)^n - 1)
        // where P = principal, r = monthly rate, n = number of payments
        double expectedPayment = 188.71; // Verified with external calculator
        double actualPayment = testLoan.calculateMonthlyPayment(60);
        assertEquals(expectedPayment, actualPayment, 0.01);
    }
    
    @Test
    public void testCalculateTotalInterest() {
        // Total payment - Principal = Total Interest
        // 188.71 * 60 - 10000 = 11322.6 - 10000 = 1322.6
        double expectedInterest = 1322.6;
        double actualInterest = testLoan.calculateTotalInterest();
        assertEquals(expectedInterest, actualInterest, 1.0); // Using larger delta due to rounding
    }
    
    @Test
    public void testGenerateAmortizationSchedule() {
        List<PaymentDetails> schedule = testLoan.generateAmortizationSchedule();
        
        // Check schedule size
        assertEquals(60, schedule.size());
        
        // Test first payment breakdown
        PaymentDetails firstPayment = schedule.get(0);
        assertEquals(1, firstPayment.getMonth());
        assertEquals(188.71, firstPayment.getMonthlyPayment(), 0.01);
        assertEquals(41.67, firstPayment.getInterestPayment(), 0.01); // First month interest: 10000 * 0.05/12 = 41.67
        assertEquals(147.04, firstPayment.getPrincipalPayment(), 0.01); // 188.71 - 41.67 = 147.04
        assertEquals(9852.96, firstPayment.getRemainingBalance(), 0.01); // 10000 - 147.04 = 9852.96
        
        // Test last payment should have zero balance
        PaymentDetails lastPayment = schedule.get(59);
        assertEquals(60, lastPayment.getMonth());
        assertEquals(0.0, lastPayment.getRemainingBalance(), 0.01);
    }
    
    @Test
    public void testRecordPayment() {
        // Record a payment
        boolean result = testLoan.recordPayment(200.0, LocalDate.now(), "Test payment");
        assertTrue(result);
        
        // Verify payment was recorded
        List<Payment> payments = testLoan.getPaymentHistory();
        assertEquals(1, payments.size());
        assertEquals(200.0, payments.get(0).getAmount(), DELTA);
        assertEquals("Test payment", payments.get(0).getNotes());
        
        // Test negative payment
        result = testLoan.recordPayment(-100.0, LocalDate.now(), "Invalid payment");
        assertFalse(result);
        
        // Verify payment wasn't recorded
        payments = testLoan.getPaymentHistory();
        assertEquals(1, payments.size());
    }
    
    @Test
    public void testCalculateRemainingBalance() {
        // Test balance at start date
        double balanceAtStart = testLoan.calculateRemainingBalance(testLoan.getStartDate());
        assertEquals(10000.0, balanceAtStart, DELTA);
        
        // Test balance after 12 months
        double balanceAfterOneYear = testLoan.calculateRemainingBalance(
                testLoan.getStartDate().plusMonths(12));
        
        // After 12 months, we expect significant principal reduction
        // This is a rough estimate based on amortization
        assertTrue(balanceAfterOneYear < 10000.0);
        assertTrue(balanceAfterOneYear > 7000.0); 
        
        // Test balance after term
        double balanceAtEnd = testLoan.calculateRemainingBalance(
                testLoan.getStartDate().plusMonths(60));
        assertEquals(0.0, balanceAtEnd, DELTA);
        
        // Test balance after maturity date
        double balanceAfterMaturity = testLoan.calculateRemainingBalance(
                testLoan.getStartDate().plusMonths(100));
        assertEquals(0.0, balanceAfterMaturity, DELTA);
    }
    
    @Test
    public void testCalculateSavingsWithExtraPayments() {
        // Test savings with $50 extra per month
        double extraPayment = 50.0;
        double savings = testLoan.calculateSavingsWithExtraPayments(extraPayment);
        
        // Savings should be positive
        assertTrue(savings > 0);
        
        // With $50 extra per month on a 5-year loan with 5% interest,
        // savings should be approximately $200-300
        assertTrue(savings > 200.0);
        assertTrue(savings < 400.0);
    }
}
