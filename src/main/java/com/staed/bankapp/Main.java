package com.staed.bankapp;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Path p = Paths.get("src\\main\\java\\com\\staed\\resources\\accounts.json");
		Bank b = null;
		int optionPicked = 0;
		boolean quit = false;
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("\n\n\nWelcome to the mini-Bank\nWhat do you want to use: Teller, ATM");
		String buffer = scanner.nextLine().toLowerCase();
		
		while(optionPicked == 0) {
			if (buffer.equals("teller")) {
				optionPicked = 1;
				b = new Teller(p);
				break;
			} else if (buffer.equals("atm")) {
				optionPicked = 2;
				b = new ATM(p);
				break;
			} else {
				System.out.println("That isn't an option, try again.");
			}
			buffer = scanner.nextLine().trim();
		}
		
		System.out.println("Please enter your name");
		buffer = scanner.nextLine().toLowerCase();
		
		String name;
		int accountID = 0;
		while (accountID == 0 && !buffer.equals("quit")) {
			name = buffer;
			int res = b.getAccountID(buffer);
			if (res == 0 && optionPicked == 2) {
				System.out.println("Sorry, that account does not exist. Try a name again or quit?");
				buffer = scanner.nextLine().toLowerCase();
			} else if (res == 0 && optionPicked == 1) {
				System.out.println("That account does not exist. Creating a new account under that name for you with a zero balance.");
				((Teller) b).openAccount(name, 0);
				accountID = b.getAccountID(name);
			} else if (buffer.equals("quit")){
				quit = true;
				break;
			} else {
				accountID = res;
				break;
			}
		}
		
		if (optionPicked == 1) {
			System.out.println("\n\nYou can tell the teller to: balance, withdraw, deposit, transfer, quit\n");
		} else {
			System.out.println("\n\nYou can use the ATM to: balance, withdraw, deposit, quit\n");
		}
		System.out.println("What would you like to do?");
		do {
			if (!quit)
				buffer = scanner.nextLine().trim();
			switch(buffer.split(" ")[0]) {
				case "balance":
					System.out.println("Your balance is $" + b.getBalance(accountID));
					break;
				case "withdraw":
					int wAmount = 0;
					try {
						wAmount = Integer.parseInt(buffer.replace("withdraw ", ""));
					} catch (NumberFormatException e) {
						e.getMessage();
					}
					int res = b.withdraw(accountID, wAmount);
					if (res != 0) {
						System.out.println("You withdrew $" + res);
					}
					break;
				case "deposit":
					int dAmount = 0;
					try {
						dAmount = Integer.parseInt(buffer.replace("deposit ", ""));
					} catch (NumberFormatException e){
						e.getMessage();
					}
					if (b.deposit(accountID, dAmount)) {
						System.out.println("You deposited $" + dAmount);
					}
					break;
				case "transfer":
					if (optionPicked != 1) {
						System.out.println("You can't do that on the ATM. See a teller.");
					} else {
						if (buffer.split(" ").length < 3) {
							System.out.println("Invalid transfer request format. Try again with [transfer recipient_name amount]");
						} else if (b.transfer(buffer, buffer.split(" ")[1], Integer.parseInt(buffer.split(" ")[2]))) {
							System.out.println("Transfer failed.");
						} else {
							System.out.println("Transfer successful");
						}
					}
					break;
				case "quit":
					quit = true;
					break;
				default:
					break;
			}
		} while (!buffer.equals("quit") && !quit);
		
		b._writeTo(p);
		scanner.close();
	
		System.out.println("Goodbye.");
	}
}
