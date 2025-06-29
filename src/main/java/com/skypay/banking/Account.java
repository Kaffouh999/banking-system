package com.skypay.banking;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Account implements AccountService {
    private final List<Transaction> transactions;
    private int balance;
    
    public Account() {
        this.transactions = new ArrayList<>();
        this.balance = 0;
    }
    
    @Override
    public void deposit(int amount) {
        deposit(amount, LocalDate.now());
    }
    
    public void deposit(int amount, LocalDate date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        
        Objects.requireNonNull(date, "Transaction date cannot be null");
        
        balance += amount;
        transactions.add(new Transaction(date, amount, balance));
    }
    
    @Override
    public void withdraw(int amount) {
        withdraw(amount, LocalDate.now());
    }
    
    public void withdraw(int amount, LocalDate date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        
        Objects.requireNonNull(date, "Transaction date cannot be null");
        
        if (amount > balance) {
            throw new IllegalStateException("Insufficient funds: available balance is " + balance);
        }
        
        balance -= amount;
        transactions.add(new Transaction(date, -amount, balance));
    }
    
    @Override
    public void printStatement() {
        System.out.println("Date       || Amount || Balance");
        
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction transaction = transactions.get(i);
            System.out.println(transaction.getFormattedDate() + " || " + 
                               transaction.getAmount() + "   || " + 
                               transaction.getBalance());
        }
    }
}