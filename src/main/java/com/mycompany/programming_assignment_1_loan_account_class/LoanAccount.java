/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.programming_assignment_1_loan_account_class;

/**
 *
 * @author Pranav Mishra
 */
public class LoanAccount {
    private static double annualInterestRate;
    private double principal;
    
    public double calculateMonthlyPayment(int numberOfPayments){
        double monthlyInterest = annualInterestRate/12;
        double monthlyPayment = principal * ( monthlyInterest / (1 - Math.pow(1 + monthlyInterest, -numberOfPayments)));
        return monthlyPayment;
    }
    public static void setAnnualInterestRate(double interestRate){
        annualInterestRate = interestRate;
 
    }
    public static void main(String[] args){
        // Creating objects loan1 an loan2
        LoanAccount loan1 = new LoanAccount();
        LoanAccount loan2 = new LoanAccount();
        
        // Assigning the principal amounts for the respective objects
        loan1.principal = 5000;
        loan2.principal = 31000;
        
        // Setting the annual interest rate
        setAnnualInterestRate(0.01);
        
        // Formatting the monthlyPayments for the respective loan objects according to the number of months
        String loan1_three_years = String.format("%.2f",loan1.calculateMonthlyPayment(36));
        String loan1_five_years = String.format("%.2f",loan1.calculateMonthlyPayment(60));
        String loan1_six_years = String.format("%.2f",loan1.calculateMonthlyPayment(72));
        String loan2_three_years = String.format("%.2f",loan2.calculateMonthlyPayment(36));
        String loan2_five_years = String.format("%.2f",loan2.calculateMonthlyPayment(60));
        String loan2_six_years = String.format("%.2f",loan2.calculateMonthlyPayment(72));
        
        // Printing the output
        System.out.println("Monthly payments for loan1  of $5000.00 and loan2 for 3,5 and 6 year loans at 1% interest.");
        System.out.println("Loan\t3years\t5years\t6years");
        System.out.println("Loan1\t" + loan1_three_years+ "\t" + loan1_five_years + "\t" + loan1_six_years);
        System.out.println("Loan2\t" + loan2_three_years+ "\t" + loan2_five_years + "\t" + loan2_six_years);
        
        // Newline
        System.out.println();
        
        // Setting the annual interest rate
        setAnnualInterestRate(0.05);
        
        // Formatting the monthlyPayments for the respective loan objects according to the number of months
        loan1_three_years = String.format("%.2f",loan1.calculateMonthlyPayment(36));
        loan1_five_years = String.format("%.2f",loan1.calculateMonthlyPayment(60));
        loan1_six_years = String.format("%.2f",loan1.calculateMonthlyPayment(72));
        loan2_three_years = String.format("%.2f",loan2.calculateMonthlyPayment(36));
        loan2_five_years = String.format("%.2f",loan2.calculateMonthlyPayment(60));
        loan2_six_years = String.format("%.2f",loan2.calculateMonthlyPayment(72));
        
        // Printing the output
        System.out.println("Monthly payments for loan1  of $5000.00 and loan2 for 3,5 and 6 year loans at 5% interest.");
        System.out.println("Loan\t3years\t5years\t6years");
        System.out.println("Loan1\t" + loan1_three_years+"\t" + loan1_five_years + "\t" +  loan1_six_years);
        System.out.println("Loan2\t" + loan2_three_years+"\t" + loan2_five_years + "\t" + loan2_six_years);
    }
}
