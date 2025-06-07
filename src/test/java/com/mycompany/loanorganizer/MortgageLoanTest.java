/*
 * Copyright (c) 2025 Loan Organizer
 * All rights reserved.
 */
package com.mycompany.loanorganizer;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;

/**
 * Unit tests for the MortgageLoan class
 * 
 * @author Loan Organizer Team
 */
public class MortgageLoanTest {
    private MortgageLoan testMortgage;
    private static final double DELTA = 0.01;
    
    @Before
    public void setUp() {
        testMortgage = new MortgageLoan(
            "Home Loan", 
            200000,       // principal 
            0.04,         // 4% interest rate
            360,          // 30 years
            "123 Main St, Anytown, USA",
            250000,       // property value
            50000,        // down payment
            true,         // escrow included
            350.0         // escrow amount
        );
    }
    
    @Test
    public void testConstructor() {
        assertEquals("Home Loan", testMortgage.getLoanName());
        assertEquals("Mortgage", testMortgage.getLoanType());
        assertEquals(200000, testMortgage.getPrincipal(), DELTA);
        assertEquals(0.04, testMortgage.getAnnualInterestRate(), DELTA);
        assertEquals(360, testMortgage.getTermInMonths());
        assertEquals("123 Main St, Anytown, USA", testMortgage.getPropertyAddress());
        assertEquals(250000, testMortgage.getPropertyValue(), DELTA);
        assertEquals(50000, testMortgage.getDownPayment(), DELTA);
        assertTrue(testMortgage.isEscrowIncluded());
        assertEquals(350.0, testMortgage.getEscrowAmount(), DELTA);
    }
    
    @Test
    public void testCalculateLoanToValueRatio() {
        // LTV = Principal / Property Value = 200000 / 250000 = 0.8
        double expectedLTV = 0.8;
        assertEquals(expectedLTV, testMortgage.calculateLoanToValueRatio(), DELTA);
    }
    
    @Test
    public void testCalculateTotalMonthlyPayment() {
        // Base payment should be around $954.83 for a $200k, 30-year mortgage at 4%
        // Plus $350 escrow = $1304.83
        double expectedTotalPayment = testMortgage.calculateMonthlyPayment(360) + 350.0;
        assertEquals(expectedTotalPayment, testMortgage.calculateTotalMonthlyPayment(), DELTA);
    }
    
    @Test
    public void testIsPMIRequired() {
        // With LTV at 0.8, PMI should not be required
        assertFalse(testMortgage.isPMIRequired());
        
        // Create a mortgage with a higher LTV
        MortgageLoan highLTVMortgage = new MortgageLoan(
            "High LTV Loan", 
            190000,       // principal 
            0.04,         // 4% interest rate
            360,          // 30 years
            "123 Main St, Anytown, USA",
            200000,       // property value
            10000,        // down payment
            false,
            0.0
        );
        
        // With LTV at 0.95, PMI should be required
        assertTrue(highLTVMortgage.isPMIRequired());
    }
    
    @Test
    public void testCalculateEquity() {
        // Equity = Property Value - Principal = 250000 - 200000 = 50000
        double expectedEquity = 50000.0;
        assertEquals(expectedEquity, testMortgage.calculateEquity(), DELTA);
    }
    
    @Test
    public void testPropertyGettersAndSetters() {
        // Test setters and getters
        testMortgage.setPropertyAddress("456 New St, Othertown, USA");
        assertEquals("456 New St, Othertown, USA", testMortgage.getPropertyAddress());
        
        testMortgage.setPropertyValue(300000);
        assertEquals(300000, testMortgage.getPropertyValue(), DELTA);
        
        testMortgage.setDownPayment(60000);
        assertEquals(60000, testMortgage.getDownPayment(), DELTA);
        
        testMortgage.setEscrowIncluded(false);
        assertFalse(testMortgage.isEscrowIncluded());
        
        testMortgage.setEscrowAmount(400);
        assertEquals(400, testMortgage.getEscrowAmount(), DELTA);
    }
}
