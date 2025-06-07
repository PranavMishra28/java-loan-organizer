/*
 * Copyright (c) 2025 Loan Organizer
 * All rights reserved.
 */
package com.mycompany.loanorganizer;

import static org.junit.Assert.*;
import org.junit.Test;
import java.time.LocalDate;
import java.util.List;

/**
 * Unit tests for the LoanCalculator utility class
 * 
 * @author Loan Organizer Team
 */
public class LoanCalculatorTest {
    private static final double DELTA = 0.01;
    
    @Test
    public void testCalculateTotalLoanCost() {
        // $10,000 loan at 5% for 3 years
        // Monthly payment should be about $299.71
        // Total cost = $299.71 * 36 = $10,789.56
        double totalCost = LoanCalculator.calculateTotalLoanCost(10000, 0.05, 36);
        assertEquals(10789.56, totalCost, 1.0); // Using larger delta due to rounding
    }
    
    @Test
    public void testCalculateMonthlyPayment() {
        // $10,000 loan at 5% for 3 years should be about $299.71 per month
        double monthlyPayment = LoanCalculator.calculateMonthlyPayment(10000, 0.05, 36);
        assertEquals(299.71, monthlyPayment, DELTA);
        
        // Test with 0% interest
        double zeroInterestPayment = LoanCalculator.calculateMonthlyPayment(10000, 0, 36);
        assertEquals(277.78, zeroInterestPayment, 0.01); // Should be principal / months
    }
    
    @Test
    public void testCalculateTotalInterest() {
        // $10,000 loan at 5% for 3 years
        // Total payments = $299.71 * 36 = $10,789.56
        // Total interest = $10,789.56 - $10,000 = $789.56
        double totalInterest = LoanCalculator.calculateTotalInterest(10000, 0.05, 36);
        assertEquals(789.56, totalInterest, 1.0);
    }
    
    @Test
    public void testGenerateAmortizationSchedule() {
        List<PaymentDetails> schedule = LoanCalculator.generateAmortizationSchedule(
            10000, 0.05, 36, LocalDate.now());
        
        // Check schedule size
        assertEquals(36, schedule.size());
        
        // First payment check
        PaymentDetails firstPayment = schedule.get(0);
        assertEquals(1, firstPayment.getMonth());
        assertEquals(299.71, firstPayment.getMonthlyPayment(), DELTA);
        assertEquals(41.67, firstPayment.getInterestPayment(), 0.01); // First month interest: 10000 * 0.05/12 = 41.67
        
        // Last payment should have zero balance
        PaymentDetails lastPayment = schedule.get(35);
        assertEquals(36, lastPayment.getMonth());
        assertEquals(0.0, lastPayment.getRemainingBalance(), DELTA);
    }
    
    @Test
    public void testCalculateMonthsUntilPayoff() {
        // $10,000 loan at 5% with $299.71 monthly payment should take 36 months
        int months = LoanCalculator.calculateMonthsUntilPayoff(10000, 0.05, 299.71);
        assertEquals(36, months);
        
        // Too small payment should return -1 (never paid off)
        int neverPayoff = LoanCalculator.calculateMonthsUntilPayoff(10000, 0.05, 40);
        assertEquals(-1, neverPayoff);
        
        // Zero or negative inputs should return 0
        assertEquals(0, LoanCalculator.calculateMonthsUntilPayoff(0, 0.05, 100));
        assertEquals(0, LoanCalculator.calculateMonthsUntilPayoff(10000, 0.05, 0));
    }
    
    @Test
    public void testCalculateAffordableLoanAmount() {
        // If max monthly payment is $300 at 5% for 36 months,
        // affordable amount should be about $10,000
        double loanAmount = LoanCalculator.calculateAffordableLoanAmount(300, 0.05, 36);
        assertEquals(10000, loanAmount, 10); // Within $10 of expected
    }
    
    @Test
    public void testFormatters() {
        // Test currency format
        assertEquals("$1,234.56", LoanCalculator.formatCurrency(1234.56));
        
        // Test percentage format
        assertEquals("5.25%", LoanCalculator.formatPercent(0.0525));
        
        // Test date format
        LocalDate testDate = LocalDate.of(2025, 6, 7);
        assertEquals("06/07/2025", LoanCalculator.formatDate(testDate));
    }
    
    @Test
    public void testCompareLoanCosts() {
        LoanAccount loan1 = new LoanAccount("Loan 1", "Personal", 10000, 0.05, 36);
        LoanAccount loan2 = new LoanAccount("Loan 2", "Personal", 10000, 0.06, 36);
        
        String comparison = LoanCalculator.compareLoanCosts(loan1, loan2);
        assertTrue(comparison.contains("Loan 1 has a lower total cost"));
    }
}
