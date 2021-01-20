package com.revature.project0.unittests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.revature.project0.core.BankSQLException;
import com.revature.project0.db.DBInterface;
import com.revature.project0.db.SQL_DB;

import com.revature.project0.core.Account;
import com.revature.project0.core.AccountBalance;

public class SQLTests {

	@Test
	public void testCreateUser() throws BankSQLException {
		SQL_DB db = new SQL_DB();
		db.connectIfNot();
		db.truncateTable();
		db.startTransaction();
		db.makeSuperUser("super_user", "foo");
		db.makeUser("regular_user", "pass");
		System.out.println(db.lookupUser("super_user"));
		System.out.println(db.lookupUser("regular_user"));
		System.out.println(db.lookupUser("notauser"));
		db.endTransaction();
		db.closeConnection();
	}
	
	public static void setup(SQL_DB db) throws BankSQLException {

		db.connectIfNot();
		db.truncateTable();
		db.startTransaction();
	}
	
	public static void shutdown(SQL_DB db) throws BankSQLException {
		db.endTransaction();
		db.closeConnection();		
	}

	@Test(expected = BankSQLException.class)
	public void testDuplicateUser() throws BankSQLException {
		SQL_DB db = new SQL_DB();
		try {
			setup(db);
			
			db.makeSuperUser("super_user", "foo");
			db.makeUser("super_user", "foo");
			
		} finally {
			shutdown(db);
		}
	}
	
	@Test
	public void testDeleteCascade() throws BankSQLException {
		SQL_DB db = new SQL_DB();
		try {
			setup(db);
			
			db.makeSuperUser("super_user", "foo");
			db.createAccount("super_user", "my_new_account");
			db.deleteUser("super_user");
			
		} finally {
			shutdown(db);
		}
	}
	
	
	@Test
	public void testCreateAccount() throws BankSQLException {
		SQL_DB db = new SQL_DB();
		try {
			setup(db);
			
			db.makeSuperUser("super_user", "foo");
			System.out.println(db.lookupUser("super_user"));
			db.createAccount("super_user", "my_new_account");
			
		} finally {
			shutdown(db);
		}
	}
	
	@Test
	public void testDeposit() throws BankSQLException {
		SQL_DB db = new SQL_DB();
		try {
			setup(db);
			
			db.makeSuperUser("super_user","foo");
			db.createAccount("super_user", "my_new_account");
			db.deposit("super_user","my_new_account", 10);
			db.deposit("super_user","my_new_account", 5);
			List<Account> accts = db.getAccounts();
			for (Account acct : accts) {
				System.out.println(acct);
			}
			
			List<AccountBalance> accts2 = db.getAccountBalances();
			for (AccountBalance acct : accts2) {
				System.out.println(acct);
			}
			
			List<AccountBalance> accts3 = db.getAccountMyBalances("super_user");
			for (AccountBalance acct : accts3) {
				System.out.println(acct);
			}
			
		} finally {
			shutdown(db);
		}
	}

	@Test
	public void testDeleteAccount() throws BankSQLException {
		SQL_DB db = new SQL_DB();
		try {
			setup(db);
			
			db.makeSuperUser("super_user","foo");
			db.createAccount("super_user", "my_new_account");
			db.deleteAccount("super_user", "my_new_account");
			
		} finally {
			shutdown(db);
		}
	}
	
	
	@Test
	public void testUpdateUser() throws BankSQLException {
		SQL_DB db = new SQL_DB();
		db.connectIfNot();
		db.truncateTable();
		db.startTransaction();
		db.makeSuperUser("super_user", "foo");
		db.makeSuperUser("otheruser", "bar");
		db.updateUser("super_user", "newpassword", false);
		db.endTransaction();
		db.closeConnection();
	}
	
	@Test
	public void testDeleteUser() throws BankSQLException {
		SQL_DB db = new SQL_DB();
		db.connectIfNot();
		db.truncateTable();
		db.startTransaction();
		db.makeSuperUser("super_user", "foo");
		db.makeSuperUser("otheruser", "bar");
		db.deleteUser("super_user");
		db.endTransaction();
		db.closeConnection();
	}
}
