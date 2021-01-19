package com.revature.project0.db;

import java.util.List;

import com.revature.project0.core.Account;
import com.revature.project0.core.AccountBalance;
import com.revature.project0.core.BankSQLException;
import com.revature.project0.core.BankStateException;
import com.revature.project0.core.UserObject;

public interface DBInterface {

	public void startTransaction() throws BankSQLException;
	
	public void endTransaction() throws BankSQLException;
	
	public void connectIfNot() throws BankSQLException;
	
	public void makeSuperUser(String username, String password) throws BankSQLException;
		
	public void makeUser(String username, String password) throws BankSQLException;
	
	public UserObject lookupUser(String username) throws BankSQLException;
	
	public void closeConnection();
	
	public Account lookupAccount(String username, String accountName) throws BankSQLException;
	
	public List<UserObject> getUsers() throws BankSQLException;
	
	public void updateUser(String username, String password,boolean isSuperUser) throws BankSQLException;
	
	public void deleteUser(String username) throws BankSQLException;
	
	public void createAccount(String username, String accountName) throws BankSQLException;
	
	public void deleteAccount(String username, String accountName) throws BankSQLException;

	public void deposit(String username, String accountName, double amt) throws BankSQLException;
	
	public List<Account> getAccounts() throws BankSQLException;
	
	public List<AccountBalance> getAccountBalances() throws BankSQLException;
	
	public List<AccountBalance> getAccountMyBalances(String username) throws BankSQLException;
	
	public void truncateTable() throws BankSQLException;
}
