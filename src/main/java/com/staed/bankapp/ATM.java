package com.staed.bankapp;

import java.nio.file.Path;
import java.util.Iterator;

public class ATM extends Bank{
	public ATM(Path path) {
		super(path);
	}
	
	@Override
	public int getAccountID(String name) {
		return super.getAccountID(name);
    }
	
	public String getBalance(int id) {
		return super.getBalance(id);
	}
	
	public int withdraw(int id, int amount) {
		return super.withdraw(id, amount);
	}
	
	public boolean deposit(int id, int amount) {
		return super.deposit(id, amount);
	}

	@Override
	public boolean transfer(String src, String dest, int amount) {
		System.out.println("ATMs can't handle transfers. Please see a teller.");
		return false;
	}

}
