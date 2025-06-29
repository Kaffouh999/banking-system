package com.skypay.banking;

/**
 * Interface for basic banking operations
 */
public interface AccountService {
    /**
     * Deposits money into the account
     * 
     * @param amount the amount to deposit (must be positive)
     * @throws IllegalArgumentException if amount is not positive
     */
    void deposit(int amount);
    
    /**
     * Withdraws money from the account
     * 
     * @param amount the amount to withdraw (must be positive)
     * @throws IllegalArgumentException if amount is not positive
     * @throws IllegalStateException if there are insufficient funds
     */
    void withdraw(int amount);
    
    /**
     * Prints the account statement showing transaction history
     */
    void printStatement();
}