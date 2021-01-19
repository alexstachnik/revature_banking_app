package com.revature.project0.core;

public class Account {

	private double balance;
	private String name;
	private String user;
	private int accountID;
	
	public Account(int accountID,double balance, String name, String user) {
		this.balance = balance;
		this.name = name;
		this.user = user;
		this.accountID=accountID;
	}
	
	public int getAccountID() {
		return accountID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUser() {
		return user;
	}

	public void String(String user) {
		this.user = user;
	}

	public Account() {
		
	}
	
	
	
}
