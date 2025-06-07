/*
 * Copyright (c) 2025 Loan Organizer
 * All rights reserved.
 */
package com.mycompany.loanorganizer;

/**
 * Represents an auto loan with specific features for vehicle financing
 * 
 * @author Pranav Mishra
 */
public class AutoLoan extends LoanAccount {
    private String vehicleMake;
    private String vehicleModel;
    private int vehicleYear;
    private String vin;
    private double vehicleValue;
    private boolean isNew;
    
    /**
     * Default constructor
     */
    public AutoLoan() {
        super();
        this.setLoanType("Auto");
    }
    
    /**
     * Constructor with parameters for auto loan
     * 
     * @param loanName The name of the loan
     * @param principal The loan principal amount
     * @param annualInterestRate The annual interest rate
     * @param termInMonths The term of the loan in months
     * @param vehicleMake The make of the vehicle
     * @param vehicleModel The model of the vehicle
     * @param vehicleYear The year of the vehicle
     * @param vin The vehicle identification number
     * @param vehicleValue The value of the vehicle
     * @param isNew Whether the vehicle is new
     */
    public AutoLoan(String loanName, double principal, double annualInterestRate,
                   int termInMonths, String vehicleMake, String vehicleModel,
                   int vehicleYear, String vin, double vehicleValue, boolean isNew) {
        super(loanName, "Auto", principal, annualInterestRate, termInMonths);
        this.vehicleMake = vehicleMake;
        this.vehicleModel = vehicleModel;
        this.vehicleYear = vehicleYear;
        this.vin = vin;
        this.vehicleValue = vehicleValue;
        this.isNew = isNew;
    }
    
    /**
     * Calculate the loan-to-value ratio for the vehicle
     * 
     * @return The loan-to-value ratio
     */
    public double calculateLoanToValueRatio() {
        return getPrincipal() / vehicleValue;
    }
    
    /**
     * Estimate the current vehicle value considering depreciation
     * 
     * @param ageInYears The age of the vehicle in years
     * @return The estimated current value
     */
    public double estimateCurrentValue(int ageInYears) {
        double depreciation = isNew ? 0.20 : 0.10; // New cars depreciate faster in the first year
        
        // Simple depreciation model
        double currentValue = vehicleValue;
        for (int i = 0; i < ageInYears; i++) {
            if (i == 0 && isNew) {
                currentValue *= (1 - depreciation);
            } else {
                currentValue *= 0.90; // 10% depreciation per year after the first
            }
        }
        
        return currentValue;
    }
    
    /**
     * Check if the loan is underwater (loan balance > vehicle value)
     * 
     * @param ageInYears The age of the vehicle in years
     * @return True if the loan is underwater
     */
    public boolean isLoanUnderwater(int ageInYears) {
        double currentValue = estimateCurrentValue(ageInYears);
        double currentBalance = calculateRemainingBalance(getStartDate().plusYears(ageInYears));
        return currentBalance > currentValue;
    }
    
    /**
     * Get the vehicle make
     * 
     * @return The vehicle make
     */
    public String getVehicleMake() {
        return vehicleMake;
    }
    
    /**
     * Set the vehicle make
     * 
     * @param vehicleMake The vehicle make
     */
    public void setVehicleMake(String vehicleMake) {
        this.vehicleMake = vehicleMake;
    }
    
    /**
     * Get the vehicle model
     * 
     * @return The vehicle model
     */
    public String getVehicleModel() {
        return vehicleModel;
    }
    
    /**
     * Set the vehicle model
     * 
     * @param vehicleModel The vehicle model
     */
    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }
    
    /**
     * Get the vehicle year
     * 
     * @return The vehicle year
     */
    public int getVehicleYear() {
        return vehicleYear;
    }
    
    /**
     * Set the vehicle year
     * 
     * @param vehicleYear The vehicle year
     */
    public void setVehicleYear(int vehicleYear) {
        this.vehicleYear = vehicleYear;
    }
    
    /**
     * Get the vehicle identification number
     * 
     * @return The VIN
     */
    public String getVin() {
        return vin;
    }
    
    /**
     * Set the vehicle identification number
     * 
     * @param vin The VIN
     */
    public void setVin(String vin) {
        this.vin = vin;
    }
    
    /**
     * Get the vehicle value
     * 
     * @return The vehicle value
     */
    public double getVehicleValue() {
        return vehicleValue;
    }
    
    /**
     * Set the vehicle value
     * 
     * @param vehicleValue The vehicle value
     */
    public void setVehicleValue(double vehicleValue) {
        this.vehicleValue = vehicleValue;
    }
    
    /**
     * Check if the vehicle is new
     * 
     * @return True if the vehicle is new
     */
    public boolean isNew() {
        return isNew;
    }
    
    /**
     * Set whether the vehicle is new
     * 
     * @param isNew True if the vehicle is new
     */
    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }
    
    @Override
    public String toString() {
        return String.format("Auto Loan: %s - $%.2f at %.2f%% for %d months on %d %s %s",
                getLoanName(), getPrincipal(), getAnnualInterestRate() * 100, 
                getTermInMonths(), vehicleYear, vehicleMake, vehicleModel);
    }
}
