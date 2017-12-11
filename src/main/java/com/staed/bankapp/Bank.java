package com.staed.bankapp;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public abstract class Bank {
	protected HashMap<Integer, Account> accounts;
    protected static Path filePath = Paths.get("src\\main\\java\\com\\staed\\resources\\accounts.json");
    
    public Bank() {
        accounts = new HashMap<>();
    }
    
    public Bank(Path path) {
    	filePath = path;
    	accounts = loadAccounts();
    }
    
    protected int getAccountID(String name) {
    	int ret = 0;
    	
    	for(Iterator<Account> iter = accounts.values().iterator(); iter.hasNext();) {
    		Account acct = iter.next();
    		if ((name.toLowerCase()).equals(acct.getName())) {
    			ret = acct.getID();
    			break;
    		}
    	}
    		
    	return ret;
    }
    
    protected String getBalance(int id) {
    	Account acct = accounts.get(id);
    	if (acct != null) {
    		DecimalFormat fmt = new DecimalFormat("#,###,###,###");
    		return fmt.format(acct.getFunds());
    	} else {
    		System.out.println("That account doesn't exist");
    		return null;
    	}
    		
    }

    protected int withdraw(int id, int amount) {
    	Account acct = accounts.get(id);
    	if (acct != null)
    		return acct.withdraw(amount);
    	else {
    		System.out.println("That account doesn't exist");
    		return 0;
    	}
    }

    protected boolean deposit(int id, int amount) {
        return accounts.get(id).deposit(amount);
    }
    
    protected synchronized HashMap<Integer, Account> loadAccounts() {
    	HashMap<Integer, Account> loadedAccounts = new HashMap<>();
    	JSONParser parser = new JSONParser();
    	
    	try {
			JSONArray objs = (JSONArray) parser.parse(new FileReader(filePath.toString()));
			Iterator<JSONObject> iter = objs.iterator();
			
			while(iter.hasNext()) {
				JSONObject obj = iter.next();
				
				String name = (String) obj.get("name");
				int balance = Integer.parseInt((String) obj.get("balance"));
				
				Account account = new Account(name, balance);
				loadedAccounts.put(account.getID(), account);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	
    	return loadedAccounts;
    }
    
    protected void writeAccounts() {
    	_writeTo(filePath);
    }
    
    protected synchronized void _writeTo(Path path) {
    	JSONArray objs = new JSONArray();
    	
    	Iterator<Account> iter = accounts.values().iterator();
    	while(iter.hasNext()) {
    		Account account = iter.next();
    		objs.add(account.toJSON());
    	}
    	
    	try (FileWriter fw = new FileWriter(path.toString())) {
			fw.write(objs.toJSONString());
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public String toString() {
    	StringBuilder result = new StringBuilder();
    	Iterator<Account> iter = accounts.values().iterator();
    	while (iter.hasNext()) {
    		Account account = iter.next();
    		result.append(account.toString() + "\n");
    	}
    	return result.toString();
    }
    
    protected abstract boolean transfer(String src, String dest, int amount);
}
