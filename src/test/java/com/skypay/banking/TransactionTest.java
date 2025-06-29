package com.skypay.banking;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {
    
    @Test
    void constructorStoresValuesCorrectly() {
        LocalDate date = LocalDate.of(2023, 5, 15);
        int amount = 1000;
        int balance = 5000;
        
        Transaction transaction = new Transaction(date, amount, balance);
        
        assertEquals(amount, transaction.getAmount());
        assertEquals(balance, transaction.getBalance());
    }
    
    @Test
    void formatsDateCorrectly() {
        LocalDate date = LocalDate.of(2023, 5, 15);
        Transaction transaction = new Transaction(date, 1000, 5000);
        
        DateTimeFormatter expectedFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String expectedFormattedDate = date.format(expectedFormatter);
        
        assertEquals(expectedFormattedDate, transaction.getFormattedDate());
    }
    
    @Test
    void constructorThrowsExceptionForNullDate() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            new Transaction(null, 1000, 5000);
        });
        
        assertTrue(exception.getMessage().contains("date cannot be null"));
    }
}

