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
 * Unit tests for the AutoLoan class
 * 
 * @author Loan Organizer Team
 */
public class AutoLoanTest {
    private AutoLoan testAutoLoan;
    private static final double DELTA = 0.01;
    
    @Before
    public void setUp() {
        testAutoLoan = new AutoLoan(
            "Car Loan", 
            25000,       // principal 
            0.0399,      // 3.99% interest rate
            60,          // 5 years
            "Toyota",
            "Camry",
            2025,
            "ABC123XYZ456",
            30000,       // vehicle value
            true         // is new
        );
    }
    
    @Test
    public void testConstructor() {
        assertEquals("Car Loan", testAutoLoan.getLoanName());
        assertEquals("Auto", testAutoLoan.getLoanType());
        assertEquals(25000, testAutoLoan.getPrincipal(), DELTA);
        assertEquals(0.0399, testAutoLoan.getAnnualInterestRate(), DELTA);
        assertEquals(60, testAutoLoan.getTermInMonths());
        assertEquals("Toyota", testAutoLoan.getVehicleMake());
        assertEquals("Camry", testAutoLoan.getVehicleModel());
        assertEquals(2025, testAutoLoan.getVehicleYear());
        assertEquals("ABC123XYZ456", testAutoLoan.getVin());
        assertEquals(30000, testAutoLoan.getVehicleValue(), DELTA);
        assertTrue(testAutoLoan.isNew());
    }
    
    @Test
    public void testCalculateLoanToValueRatio() {
        // LTV = Principal / Vehicle Value = 25000 / 30000 = 0.8333
        double expectedLTV = 25000.0 / 30000.0;
        assertEquals(expectedLTV, testAutoLoan.calculateLoanToValueRatio(), DELTA);
    }
    
    @Test
    public void testEstimateCurrentValue() {
        // New car should depreciate 20% in first year, then 10% per year
        // Year 0: 30000
        // Year 1: 30000 * 0.8 = 24000
        // Year 2: 24000 * 0.9 = 21600
        // Year 3: 21600 * 0.9 = 19440
        
        double valueAfterOneYear = testAutoLoan.estimateCurrentValue(1);
        assertEquals(24000, valueAfterOneYear, DELTA);
        
        double valueAfterThreeYears = testAutoLoan.estimateCurrentValue(3);
        assertEquals(19440, valueAfterThreeYears, DELTA);
    }
    
    @Test
    public void testIsLoanUnderwater() {
        // After 1 year:
        // Car value = 24000
        // Loan balance should be less than 25000 but more than 20000
        boolean underwaterAfterOneYear = testAutoLoan.isLoanUnderwater(1);
        assertFalse(underwaterAfterOneYear); // Might be close but not underwater yet
        
        // Create a higher LTV loan that would be underwater quickly
        AutoLoan highLTVLoan = new AutoLoan(
            "High LTV Car Loan", 
            29000,       // principal almost equal to value
            0.0599,      // higher interest rate
            60,
            "Toyota",
            "Camry",
            2025,
            "ABC123XYZ456",
            30000,
            true
        );
        
        boolean highLTVUnderwaterAfterOneYear = highLTVLoan.isLoanUnderwater(1);
        assertTrue(highLTVUnderwaterAfterOneYear);
    }
    
    @Test
    public void testVehicleGettersAndSetters() {
        testAutoLoan.setVehicleMake("Honda");
        assertEquals("Honda", testAutoLoan.getVehicleMake());
        
        testAutoLoan.setVehicleModel("Accord");
        assertEquals("Accord", testAutoLoan.getVehicleModel());
        
        testAutoLoan.setVehicleYear(2026);
        assertEquals(2026, testAutoLoan.getVehicleYear());
        
        testAutoLoan.setVin("XYZ987ABC654");
        assertEquals("XYZ987ABC654", testAutoLoan.getVin());
        
        testAutoLoan.setVehicleValue(32000);
        assertEquals(32000, testAutoLoan.getVehicleValue(), DELTA);
        
        testAutoLoan.setNew(false);
        assertFalse(testAutoLoan.isNew());
    }
}
