package com.staed.bankapp.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.staed.bankapp.ATM;

class ATMTest {
	ATM atm;
	
	@BeforeEach
	void initalize() {
		Path path = Paths.get("src\\test\\java\\com\\staed\\resources\\accounts.json");
		atm = new ATM(path);
	}

	@DisplayName("ATM: Test valid requests")
	@Test
	void testValid() {
		int nonoID = atm.getAccountID("nono");
		assertEquals("5", atm.getBalance(nonoID));
		assertEquals(1, atm.withdraw(nonoID, 1));
		assertTrue(atm.deposit(nonoID, 3));
		
		assertFalse(atm.transfer("nono", "john", 1));
	}

}
