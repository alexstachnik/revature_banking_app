package com.revature.project0.core;

public class AccountBalance implements Comparable<AccountBalance> {

	private String accountName;
	private double accountBalance;
	private String username;
	
	
	
	
	public AccountBalance(String accountName, double accountBalance, String username) {
		super();
		this.accountName = accountName;
		this.accountBalance = accountBalance;
		this.username = username;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public double getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String toString() {
		return "AccountBalance [accountName=" + accountName + ", accountBalance=" + accountBalance + ", username="
				+ username + "]";
	}
	
	public int compareTo(AccountBalance o) {
		int x;
		x = this.username.compareTo(o.getUsername());
		if (x == 0) {
			x = this.accountName.compareTo(o.getAccountName());
			if (x == 0) {
				x = Double.compare(accountBalance, o.getAccountBalance());
			}
		}
		return x;
	}
	
	
}
