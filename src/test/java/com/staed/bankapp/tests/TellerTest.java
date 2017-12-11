package com.staed.bankapp.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.staed.bankapp.Account;
import com.staed.bankapp.Teller;

import org.junit.jupiter.api.BeforeEach;

class TellerTest {
    Teller b1, b2;
    Account acc1, acc2, acc3, acc4;

    @BeforeEach
    public void newInstance() {
    	acc1 = new Account("John", 0);
        acc2 = new Account("May", 100);
        acc3 = new Account("Nono", 5);
        acc4 = new Account("Duck", 1000);
        
        HashMap<Integer, Account> accounts = new HashMap<>();
        accounts.put(acc1.getID(), acc1);
        accounts.put(acc2.getID(), acc2);
        accounts.put(acc3.getID(), acc3);
        accounts.put(acc4.getID(), acc4);
    	
    	b1 = new Teller(accounts);
    	b2 = new Teller();
    }

    @DisplayName("Teller: Test Initialization")
    @Test
    public void testInit() {
    	assertEquals(0, b2.withdraw(1, 1));
    	
    	int mayID = b1.getAccountID("May");
    	assertEquals(2, b1.withdraw(mayID, 2));
    	assertEquals(98, Integer.parseInt(b1.getBalance(mayID)));
    }
    
    @DisplayName("Teller: Test getBalance")
    @Test
    public void testBalance() {
    	assertNull(b1.getBalance(1000000));
    	assertNull(b1.getBalance(0));
    	
    	int thousand = b1.getAccountID("Duck");
    	assertEquals("1,000", b1.getBalance(thousand));
    }
    
    @DisplayName("Teller: Test deposit")
    @Test
    public void testDeposit() {
    	int johnID = b1.getAccountID("john");
    	assertTrue(b1.deposit(johnID, 5));
    	assertFalse(b1.deposit(johnID, -5));
    }
    
    @DisplayName("Teller: Test Open New Accounts")
    @Test
    public void testOpenAccounts() {
    	Account account = new Account("Daffy Duck", 9);
    	b1.openAccount(account);
    	assertTrue(b1.getAccountID("Daffy Duck") > 0);
    	assertEquals("9", b1.getBalance(b1.getAccountID("Daffy Duck")));
    	
    	b1.openAccount("Hilton", 5);
    	assertTrue(b1.getAccountID("Hilton") > 0);
    	assertEquals("5", b1.getBalance(b1.getAccountID("Hilton")));
    }
    
    @DisplayName("Teller: Test Load and Write")
    @Test
    public void testLoadWrite() {
    	Path path = Paths.get("src\\test\\java\\com\\staed\\resources\\accounts.json");
    	
    	b1.openAccount("Daffy Duck", 9);
    	b1.openAccount("Hilton", 5);
    	b1._writeTo(path);
    	
    	Teller b3 = new Teller(path);
    	assertTrue(b1.getAccountID("John") > 0);
    	assertEquals("0", b1.getBalance(b1.getAccountID("John")));
    	
    	assertTrue(b1.getAccountID("May") > 0);
    	assertEquals("100", b1.getBalance(b1.getAccountID("May")));
    	
    	assertTrue(b1.getAccountID("Nono") > 0);
    	assertEquals("5", b1.getBalance(b1.getAccountID("Nono")));
    	
    	assertTrue(b1.getAccountID("Duck") > 0);
    	assertEquals("1,000", b1.getBalance(b1.getAccountID("duck")));
    	
    	assertTrue(b1.getAccountID("Daffy Duck") > 0);
    	assertEquals("9", b1.getBalance(b1.getAccountID("daffy duck")));
    	
    	assertTrue(b1.getAccountID("Hilton") > 0);
    	assertEquals("5", b1.getBalance(b1.getAccountID("hiLToN")));
    	
    }
    
    @DisplayName("Teller: Test toString")
    @Test
    public void testToString() {
    	assertTrue(b1.toString().replaceAll("/\\r?\\n|\\r|s*/g", "") != null);
    }
    
    @DisplayName("Teller: Test Transfer")
    @Test
    public void testTransfer() {
    	b1.openAccount("Daffy Duck", 9);
    	b1.openAccount("Hilton", 5);
    	
    	assertTrue(b1.transfer("Daffy Duck", "HILTON", 2));
    	assertEquals("7", b1.getBalance(b1.getAccountID("Daffy Duck")));
    	assertEquals("7", b1.getBalance(b1.getAccountID("Hilton")));
    	
    	assertFalse(b1.transfer("Daffy Duck", "Hilton", 100));
    	assertFalse(b1.transfer("Daffy Duck", "foiajlasmvls", 2));
    }
}