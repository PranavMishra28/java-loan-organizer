/*
 * Copyright (c) 2025 Loan Organizer
 * All rights reserved.
 */
package com.mycompany.loanorganizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Simple GUI for Loan Organizer application
 * 
 * @author Loan Organizer Team
 * @version 1.0
 */
public class LoanOrganizerGUI extends JFrame {
    
    private JTabbedPane tabbedPane;
    private JPanel loanDetailsPanel;
    private JPanel amortizationPanel;
    private JPanel comparisonPanel;
    
    // Input fields for loan details
    private JTextField loanNameField;
    private JComboBox<String> loanTypeCombo;
    private JTextField principalField;
    private JTextField interestRateField;
    private JTextField termField;
    private JButton calculateButton;
    
    // Results display
    private JLabel monthlyPaymentLabel;
    private JLabel totalInterestLabel;
    private JLabel totalPaymentLabel;
    private JTextArea amortizationTextArea;
    
    public LoanOrganizerGUI() {
        setTitle("Loan Organizer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        
        // Create loan details panel
        createLoanDetailsPanel();
        
        // Create amortization panel
        createAmortizationPanel();
        
        // Create comparison panel
        createComparisonPanel();
        
        // Add panels to tabbed pane
        tabbedPane.addTab("Loan Details", loanDetailsPanel);
        tabbedPane.addTab("Amortization Schedule", amortizationPanel);
        tabbedPane.addTab("Loan Comparison", comparisonPanel);
        
        // Add tabbed pane to frame
        getContentPane().add(tabbedPane);
    }
    
    private void createLoanDetailsPanel() {
        loanDetailsPanel = new JPanel();
        loanDetailsPanel.setLayout(new BorderLayout());
        
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Add input components
        inputPanel.add(new JLabel("Loan Name:"));
        loanNameField = new JTextField("New Loan");
        inputPanel.add(loanNameField);
        
        inputPanel.add(new JLabel("Loan Type:"));
        loanTypeCombo = new JComboBox<>(new String[]{"Personal", "Auto", "Mortgage"});
        inputPanel.add(loanTypeCombo);
        
        inputPanel.add(new JLabel("Principal Amount ($):"));
        principalField = new JTextField("10000");
        inputPanel.add(principalField);
        
        inputPanel.add(new JLabel("Annual Interest Rate (%):"));
        interestRateField = new JTextField("5.0");
        inputPanel.add(interestRateField);
        
        inputPanel.add(new JLabel("Term (months):"));
        termField = new JTextField("60");
        inputPanel.add(termField);
        
        // Add calculate button
        calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateLoan();
            }
        });
        
        // Results panel
        JPanel resultsPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        resultsPanel.setBorder(BorderFactory.createTitledBorder("Results"));
        
        resultsPanel.add(new JLabel("Monthly Payment:"));
        monthlyPaymentLabel = new JLabel("$0.00");
        resultsPanel.add(monthlyPaymentLabel);
        
        resultsPanel.add(new JLabel("Total Interest:"));
        totalInterestLabel = new JLabel("$0.00");
        resultsPanel.add(totalInterestLabel);
        
        resultsPanel.add(new JLabel("Total Payment:"));
        totalPaymentLabel = new JLabel("$0.00");
        resultsPanel.add(totalPaymentLabel);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(calculateButton);
        
        loanDetailsPanel.add(inputPanel, BorderLayout.NORTH);
        loanDetailsPanel.add(resultsPanel, BorderLayout.CENTER);
        loanDetailsPanel.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void createAmortizationPanel() {
        amortizationPanel = new JPanel(new BorderLayout());
        
        amortizationTextArea = new JTextArea();
        amortizationTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(amortizationTextArea);
        
        amortizationPanel.add(scrollPane, BorderLayout.CENTER);
    }
    
    private void createComparisonPanel() {
        comparisonPanel = new JPanel();
        comparisonPanel.setLayout(new BorderLayout());
        
        JPanel inputPanel = new JPanel(new GridLayout(0, 4, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Loan 1
        inputPanel.add(new JLabel("Loan 1 - Principal:"));
        JTextField loan1Principal = new JTextField("10000");
        inputPanel.add(loan1Principal);
        
        inputPanel.add(new JLabel("Interest Rate (%):"));
        JTextField loan1Rate = new JTextField("5.0");
        inputPanel.add(loan1Rate);
        
        // Loan 2
        inputPanel.add(new JLabel("Loan 2 - Principal:"));
        JTextField loan2Principal = new JTextField("10000");
        inputPanel.add(loan2Principal);
        
        inputPanel.add(new JLabel("Interest Rate (%):"));
        JTextField loan2Rate = new JTextField("4.5");
        inputPanel.add(loan2Rate);
        
        // Term options
        inputPanel.add(new JLabel("Term (months):"));
        JTextField termMonths = new JTextField("60");
        inputPanel.add(termMonths);
        
        // Compare button
        JButton compareButton = new JButton("Compare Loans");
        
        // Results area
        JTextArea comparisonResults = new JTextArea();
        comparisonResults.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(comparisonResults);
        
        compareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double p1 = Double.parseDouble(loan1Principal.getText());
                    double r1 = Double.parseDouble(loan1Rate.getText()) / 100;
                    double p2 = Double.parseDouble(loan2Principal.getText());
                    double r2 = Double.parseDouble(loan2Rate.getText()) / 100;
                    int term = Integer.parseInt(termMonths.getText());
                    
                    LoanAccount loan1 = new LoanAccount("Loan 1", "Personal", p1, r1, term);
                    LoanAccount loan2 = new LoanAccount("Loan 2", "Personal", p2, r2, term);
                    
                    double payment1 = loan1.calculateMonthlyPayment(term);
                    double payment2 = loan2.calculateMonthlyPayment(term);
                    double interest1 = loan1.calculateTotalInterest();
                    double interest2 = loan2.calculateTotalInterest();
                    
                    StringBuilder sb = new StringBuilder();
                    sb.append("LOAN COMPARISON\n\n");
                    sb.append(String.format("Loan 1 ($%.2f at %.2f%%):\n", p1, r1 * 100));
                    sb.append(String.format("  Monthly Payment: $%.2f\n", payment1));
                    sb.append(String.format("  Total Interest: $%.2f\n", interest1));
                    sb.append(String.format("  Total Cost: $%.2f\n\n", p1 + interest1));
                    
                    sb.append(String.format("Loan 2 ($%.2f at %.2f%%):\n", p2, r2 * 100));
                    sb.append(String.format("  Monthly Payment: $%.2f\n", payment2));
                    sb.append(String.format("  Total Interest: $%.2f\n", interest2));
                    sb.append(String.format("  Total Cost: $%.2f\n\n", p2 + interest2));
                    
                    if (p1 + interest1 < p2 + interest2) {
                        sb.append("Loan 1 costs less overall by $").append(String.format("%.2f", (p2 + interest2) - (p1 + interest1)));
                    } else if (p2 + interest2 < p1 + interest1) {
                        sb.append("Loan 2 costs less overall by $").append(String.format("%.2f", (p1 + interest1) - (p2 + interest2)));
                    } else {
                        sb.append("Both loans have the same overall cost.");
                    }
                    
                    comparisonResults.setText(sb.toString());
                    
                } catch (NumberFormatException ex) {
                    comparisonResults.setText("Error: Please enter valid numbers for all fields.");
                }
            }
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(compareButton);
        
        comparisonPanel.add(inputPanel, BorderLayout.NORTH);
        comparisonPanel.add(scrollPane, BorderLayout.CENTER);
        comparisonPanel.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void calculateLoan() {
        try {
            // Get values from input fields
            String loanName = loanNameField.getText();
            String loanType = (String) loanTypeCombo.getSelectedItem();
            double principal = Double.parseDouble(principalField.getText());
            double annualRate = Double.parseDouble(interestRateField.getText()) / 100; // Convert to decimal
            int termMonths = Integer.parseInt(termField.getText());
            
            // Create loan object
            LoanAccount loan = new LoanAccount(loanName, loanType, principal, annualRate, termMonths);
            
            // Calculate results
            double monthlyPayment = loan.calculateMonthlyPayment(termMonths);
            double totalInterest = loan.calculateTotalInterest();
            double totalPayment = principal + totalInterest;
            
            // Display results
            monthlyPaymentLabel.setText(String.format("$%.2f", monthlyPayment));
            totalInterestLabel.setText(String.format("$%.2f", totalInterest));
            totalPaymentLabel.setText(String.format("$%.2f", totalPayment));
            
            // Update amortization schedule
            List<PaymentDetails> schedule = loan.generateAmortizationSchedule();
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("AMORTIZATION SCHEDULE FOR %s\n\n", loanName));
            sb.append(String.format("Principal: $%.2f, Rate: %.2f%%, Term: %d months\n\n", 
                    principal, annualRate * 100, termMonths));
            sb.append("Month\tPayment\t\tPrincipal\tInterest\tRemaining Balance\n");
            
            for (PaymentDetails payment : schedule) {
                sb.append(String.format("%d\t$%.2f\t\t$%.2f\t\t$%.2f\t\t$%.2f\n",
                        payment.getMonth(),
                        payment.getMonthlyPayment(),
                        payment.getPrincipalPayment(),
                        payment.getInterestPayment(),
                        payment.getRemainingBalance()));
            }
            
            amortizationTextArea.setText(sb.toString());
            
            // Switch to amortization tab
            tabbedPane.setSelectedIndex(1);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                    "Please enter valid numbers for principal, interest rate, and term.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Main method to launch the application
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Set UI look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Create and display GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoanOrganizerGUI gui = new LoanOrganizerGUI();
                gui.setLocationRelativeTo(null);  // Center on screen
                gui.setVisible(true);
            }
        });
    }
}
