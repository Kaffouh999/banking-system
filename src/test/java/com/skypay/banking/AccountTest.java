package com.skypay.banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    
    private Account account;
    private final LocalDate testDate1 = LocalDate.of(2012, 1, 10);
    private final LocalDate testDate2 = LocalDate.of(2012, 1, 13);
    private final LocalDate testDate3 = LocalDate.of(2012, 1, 14);
    
    @BeforeEach
    void setUp() {
        account = new Account();
    }
    
    @Test
    void depositIncreasesBalance() {
        account.deposit(1000, testDate1);
        account.deposit(2000, testDate2);

        assertDoesNotThrow(() -> account.withdraw(3000, testDate3));
    }
    
    @Test
    void depositWithCurrentDate() {
        assertDoesNotThrow(() -> account.deposit(1000));
    }

    @Test
    void depositZeroShouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(0);
        });
        
        assertTrue(exception.getMessage().contains("must be positive"));
    }
    
    @Test
    void depositNegativeAmountShouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(-100);
        });
        
        assertTrue(exception.getMessage().contains("must be positive"));
    }
    
    @Test
    void depositNullDateShouldThrowException() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            account.deposit(100, null);
        });
        
        assertTrue(exception.getMessage().contains("date cannot be null"));
    }
    
    @Test
    void withdrawDecreasesBalance() {
        account.deposit(1000, testDate1);
        account.withdraw(300, testDate2);
        account.withdraw(700, testDate3);

        // Assert that balance is now 0 (indirectly, by verifying we can't withdraw more)
        Exception exception = assertThrows(IllegalStateException.class, () -> account.withdraw(1, LocalDate.now()));
        assertTrue(exception.getMessage().contains("Insufficient funds"));
    }
    
    @Test
    void withdrawWithCurrentDate() {
        account.deposit(1000);
        assertDoesNotThrow(() -> account.withdraw(500));
    }
    
    @Test
    void withdrawZeroShouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(0);
        });
        
        assertTrue(exception.getMessage().contains("must be positive"));
    }
    
    @Test
    void withdrawNegativeAmountShouldThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(-50);
        });
        
        assertTrue(exception.getMessage().contains("must be positive"));
    }
    
    @Test
    void withdrawNullDateShouldThrowException() {
        account.deposit(100);
        
        Exception exception = assertThrows(NullPointerException.class, () -> {
            account.withdraw(50, null);
        });
        
        assertTrue(exception.getMessage().contains("date cannot be null"));
    }
    
    @Test
    void withdrawMoreThanBalanceShouldThrowException() {
        account.deposit(100);
        
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            account.withdraw(200);
        });
        
        assertTrue(exception.getMessage().contains("Insufficient funds"));
    }
    
    @Test
    void withdrawExactBalanceAmount() {
        account.deposit(100);
        assertDoesNotThrow(() -> account.withdraw(100));
    }
    
    @Test
    void printStatementShowsTransactionsInReverseChronologicalOrder() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        
        try {
            account.deposit(1000, testDate1); 
            account.deposit(2000, testDate2); 
            account.withdraw(500, testDate3); 
            
            account.printStatement();
            
            String output = outputStream.toString();
            
            assertTrue(output.contains("Date       || Amount || Balance"));
            
            int date1Pos = output.indexOf("10/01/2012");
            int date2Pos = output.indexOf("13/01/2012");
            int date3Pos = output.indexOf("14/01/2012");
            
            assertTrue(date3Pos < date2Pos);
            assertTrue(date2Pos < date1Pos);
            
            assertTrue(output.contains("14/01/2012 || -500   || 2500"));
            assertTrue(output.contains("13/01/2012 || 2000   || 3000"));
            assertTrue(output.contains("10/01/2012 || 1000   || 1000"));
            
        } finally {
            System.setOut(originalOut);
        }
    }
}

