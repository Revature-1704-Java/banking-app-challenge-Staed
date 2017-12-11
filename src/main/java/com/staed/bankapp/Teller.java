package com.staed.bankapp;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;

public class Teller extends Bank {
	public Teller() {
		super();
	}
	
	public Teller(Path path) {
		super(path);
	}
    
    public Teller(HashMap<Integer, Account> accounts) {
    	this.accounts = accounts;
    }
    
    @Override
    public int getAccountID(String name) {
    	return super.getAccountID(name);
    }

    public boolean openAccount(String name, int currentFunds) {
        Account customer = new Account(name.toLowerCase(), currentFunds);
        accounts.put(customer.getID(), customer);
        return true;
    }
    
    public boolean openAccount(Account account) {
    	accounts.put(account.getID(), account);
    	return true;
    }
    
    public String getBalance(int id) {
    	return super.getBalance(id);
    }
    
    protected int getValue(int id) {
    	Account acct = accounts.get(id);
    	if (acct != null)
    		return acct.getFunds();
    	else
    		return -1;
    }
    
    @Override
    public int withdraw(int id, int amount) {
    	return super.withdraw(id, amount);
    }
    
    @Override
    public boolean deposit(int id, int amount) {
    	return super.deposit(id, amount);
    }
    
    @Override
    public synchronized HashMap<Integer, Account> loadAccounts() {
    	return super.loadAccounts();
    }
    
    @Override
    public void writeAccounts() {
    	super.writeAccounts();
    }
    
    @Override
    public synchronized void _writeTo(Path path) {
    	super._writeTo(path);
    }
    
    @Override
    public boolean transfer(String src, String dest, int amount) {
    	int srcID = getAccountID(src);
    	int destID = getAccountID(dest);
    	if (srcID == 0 || destID == 0)
    		return false;
    	if (getValue(srcID) < amount) {
    		System.out.println("Insufficent balance for transfer.");
    		return false;
    	}
    	
    	accounts.get(srcID).withdraw(amount);
    	accounts.get(destID).deposit(amount);
    	return true;
    }
}