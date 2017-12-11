package com.staed.bankapp;

import org.json.simple.JSONObject;

public class Account {
    private static int totalAccounts = 0;
    private final int id;
    private String name;
    private int currentFunds;

    public Account(String name, int currentFunds) {
        this.name = name.toLowerCase();

        if (currentFunds >= 0)
            this.currentFunds = currentFunds;
        else
            this.currentFunds = 0;

        totalAccounts++;
        id = totalAccounts;
    }

    public int getID() {
        return new Integer(id);
    }

    public String getName() {
        return name;
    }

    public int getFunds() {
        return currentFunds;
    }

    public synchronized boolean deposit(int amount) {
        if (amount >= 0) {
            currentFunds += amount;
            return true;
        } else {
            System.out.println("Can't deposit a negative amount");
            return false;
        }
    }

    public synchronized int withdraw(int amount) {
        if (amount < 0) {
            System.out.println("Can't withdraw a negative amount");
            return 0;
        } else if (amount < currentFunds) {
            currentFunds -= amount;
            return amount;
        } else {
        	System.out.println("Your balance isn't large enough. Withdrawal canceled");
            return 0;
        }
    }
    
    public String toString() {
    	return "ID:" + id + ",{name:" + name + ", balance:" + currentFunds + "}";
    }
    
    public JSONObject toJSON() {
    	JSONObject obj = new JSONObject();
    	obj.put("id", id);
    	obj.put("name", name);
    	obj.put("balance", Integer.toString(currentFunds));
    	return obj;
    }
}