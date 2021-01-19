package com.revature.project0.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.revature.project0.core.BankSQLException;
import com.revature.project0.core.UserObject;
import com.revature.project0.core.Account;
import com.revature.project0.core.AccountBalance;

public class MemoryBackend implements DBInterface {

	private Map<String,UserObject> users;
	private Map<String,Account> accounts;
	private int newUserID;
	private int newAccountID;
	private boolean locked;
	
	public MemoryBackend() {
		this.users = new HashMap<String,UserObject>();
		this.accounts = new HashMap<String,Account>();
		this.newUserID=1;
		this.newAccountID=1;
		this.locked = false;
	}
	
	public void closeConnection() {
		
	}

	public void connectIfNot() throws BankSQLException {
		
	}

	public void createAccount(String username,String accountName) throws BankSQLException {
		Account a = new Account(this.newAccountID++,0.0,accountName,username);
		accounts.put(makeAccountKey(accountName,username),a);
	}

	public void deleteAccount(String username, String accountName) throws BankSQLException {
		accounts.remove(makeAccountKey(accountName,username));
	}

	public void deleteUser(String username) throws BankSQLException {
		users.remove(username);
	}
	
	public void deposit(String username, String accountName, double amt) throws BankSQLException {
		Account account = this.accounts.get(makeAccountKey(accountName,username));
		double balance = account.getBalance();
		account.setBalance(balance+amt);
	}
	
	public List<Account> getAllAccounts() throws BankSQLException {
		return new ArrayList<Account>(accounts.values()); 
	}
	
	public Account lookupAccount(String username,String accountName) throws BankSQLException {
		return accounts.get(makeAccountKey(accountName,username));
	}

	public UserObject lookupUser(String username) throws BankSQLException {
		return this.users.get(username);
	}

	public static String makeAccountKey(String name, String user) {
		return name+" "+user;
	}

	public void makeSuperUser(String username, String password) throws BankSQLException {
		UserObject user = new UserObject(this.newUserID++,username,password,true);
		this.users.put(username,user);
	}

	public void makeUser(String username, String password) throws BankSQLException {
		UserObject user = new UserObject(this.newUserID++,username,password,false);
		this.users.put(username,user);
	}

	public void updateUser(String username, String password, boolean isSuperUser) throws BankSQLException {
		UserObject user = users.get(username);
		user.setHash(password);
		user.setSuperuser(isSuperUser);
	}

	public List<UserObject> getUsers() {
		List<UserObject> userlist = new ArrayList<UserObject>(users.values());
		Collections.sort(userlist);
		return userlist;
	}

	public List<Account> getAccounts() throws BankSQLException {
		return new ArrayList<Account>(this.accounts.values());
	}

	public List<AccountBalance> getAccountMyBalances(String username) throws BankSQLException {
		List<AccountBalance> balances = new ArrayList<AccountBalance>();
		for (Account a : this.accounts.values()) {
			if (a.getUser() == username) {
				balances.add(new AccountBalance(a.getName(),a.getBalance(),username));
			}
		}
		Collections.sort(balances);
		return balances;
	}
	
	public List<AccountBalance> getAccountBalances() throws BankSQLException {
		List<AccountBalance> balances = new ArrayList<AccountBalance>();
		for (Account a : this.accounts.values()) {
			balances.add(new AccountBalance(a.getName(),a.getBalance(),a.getUser()));
		}
		Collections.sort(balances);
		return balances;
	}

	public void startTransaction() throws BankSQLException {
		if (this.locked) {
			throw new BankSQLException("Double lock");
		}
		this.locked=true;
	}

	public void endTransaction() throws BankSQLException {
		this.locked=false;
	}

	
	public void truncateTable() throws BankSQLException {
		if (this.locked) {
			throw new BankSQLException("locked db");
		}
		this.users = new HashMap<String,UserObject>();
		this.accounts = new HashMap<String,Account>();
		this.newUserID=1;
		this.newAccountID=1;
	}
}
