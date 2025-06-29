package com.skypay.banking;

import java.time.LocalDate;

public class BankApplication {
    public static void main(String[] args) {
        System.out.println("=== Banking System Demo ===\n");
        
        Account account = new Account();
        
        account.deposit(1000, LocalDate.of(2012, 1, 10));
        System.out.println("Deposited 1000 on 10/01/2012");
        
        account.deposit(2000, LocalDate.of(2012, 1, 13));
        System.out.println("Deposited 2000 on 13/01/2012");
        
        account.withdraw(500, LocalDate.of(2012, 1, 14));
        System.out.println("Withdrew 500 on 14/01/2012");
        
        System.out.println("\nFinal Account Statement:");
        account.printStatement();
    }
}