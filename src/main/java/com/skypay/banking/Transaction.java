package com.skypay.banking;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Transaction {
    private final LocalDate date;
    private final int amount;
    private final int balance;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public Transaction(LocalDate date, int amount, int balance) {
        this.date = Objects.requireNonNull(date, "Transaction date cannot be null");
        this.amount = amount;
        this.balance = balance;
    }
    
    public String getFormattedDate() {
        return date.format(DATE_FORMATTER);
    }
    
    public int getAmount() {
        return amount;
    }
    
    public int getBalance() {
        return balance;
    }
}