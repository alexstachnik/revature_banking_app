package com.revature.project0.core;

public class Account implements Comparable<Account>{

	private double balance;
	private String name;
	private String user;
	@Override
	public String toString() {
		return "Account [balance=" + balance + ", name=" + name + ", user=" + user + ", accountID=" + accountID + "]";
	}

	private int accountID;
	
	public Account(int accountID,double balance, String name, String user) {
		this.balance = balance;
		this.name = name;
		this.user = user;
		this.accountID=accountID;
	}
	
	public boolean testBalance(double other) {
		return Math.abs(this.balance - other) < 0.01;
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

	@Override
	public int compareTo(Account o) {
		int x;
		x = this.user.compareTo(o.getUser());
		if (x == 0) {
			x = Integer.compare(accountID,o.getAccountID());
			if (x == 0) {
				x = Double.compare(balance, o.getBalance());
			}
		}
		return x;
	}
	
	
	
}
