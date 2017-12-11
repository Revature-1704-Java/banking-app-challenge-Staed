package com.staed.bankapp.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.staed.bankapp.Account;

import org.junit.jupiter.api.BeforeEach;

class AccountTest {
    Account acc1, acc2, acc3;

    @BeforeEach
    public void newInstance() {
        acc1 = new Account("John", 0);
        acc2 = new Account("May", 100);
        acc3 = new Account("Nono", 5);
    }

    @DisplayName("Account: Test for Unique IDs")
    @Test
    public void testID() {
        assertTrue(acc1.getID() != acc2.getID());
        assertTrue(acc2.getID() != acc3.getID());
        assertTrue(acc1.getID() != acc3.getID());
    }

    @DisplayName("Account: Test for Correct Initiatization")
    @Test
    public void testInit() {
        assertEquals("john", acc1.getName());
        assertEquals("may", acc2.getName());
        assertEquals("nono", acc3.getName());

        assertEquals(0, acc1.getFunds());
        assertEquals(100, acc2.getFunds());
        assertEquals(5, acc3.getFunds());
    }

    @DisplayName("Account: Test for Deposit and Withdrawal")
    @Test
    public void testInteraction() {
        assertTrue(acc1.deposit(10));
        assertEquals(10, acc1.getFunds());

        assertFalse(acc1.deposit(-5));

        assertEquals(0, acc1.withdraw(-1));
        assertEquals(5, acc1.withdraw(5));
        assertEquals(0, acc1.withdraw(100));
    }
    
    @DisplayName("Account: Test toString/toJSON")
    @Test
    public void testToFormat() {
    	String expected = "ID:" + acc1.getID() + ",{name:john, balance:0}";
    	assertEquals(expected, acc1.toString());
    	
    	JSONObject obj = acc1.toJSON();
    	assertEquals(obj.get("name"), "john");
    	assertEquals(obj.get("balance"), "0");
    }
}
