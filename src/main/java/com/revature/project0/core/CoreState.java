package com.revature.project0.core;

import java.util.List;

import com.revature.project0.IO.Argument;
import com.revature.project0.db.DBInterface;

public class CoreState {

	private boolean loggedIn;
	private UserObject user;
	
	private DBInterface dbinterface;
	
	public CoreState(DBInterface db) {
		this.loggedIn=false;
		this.user=null;
		this.dbinterface = db;
	}
	
	public void setDBinterface(DBInterface db) {
		this.dbinterface=db;
	}
	
	public boolean isLoggedIn() {
		return loggedIn;
	}
	
	public void registerSuperuser(String username, String password) throws BankStateException, BankRegisterException, BankSQLException {
		if (loggedIn && user.isSuperuser()) {
			dbinterface.connectIfNot();
			this.dbinterface.startTransaction();
			try {
				UserObject user = dbinterface.lookupUser(username);
				if (user != null) {
					throw new BankRegisterException("User already exists");
				}
				dbinterface.makeSuperUser(username,password);
			} finally {
				this.dbinterface.endTransaction();
			}
		} else {
			throw new BankRegisterException("Not logged in as a superuser");
		}
	}
	
	public void register(String username, String password) throws BankRegisterException, BankSQLException, BankStateException {
		dbinterface.connectIfNot();
		
		this.dbinterface.startTransaction();
		try {
			UserObject user = dbinterface.lookupUser(username);
			if (user != null) {
				throw new BankRegisterException("User already exists");
			}
			dbinterface.makeUser(username,password);
		} finally {
			this.dbinterface.endTransaction();
		}
	}
	
	public void login(String username, String password) throws BankLoginException, BankSQLException {
		if (loggedIn) {
			throw new BankLoginException("Already logged in");
		}
		
		dbinterface.connectIfNot();
		this.user = dbinterface.lookupUser(username);
		
		if (user == null) {
			throw new BankLoginException("No such user");
		}
		
		if (!Password.hash(password).equals(this.user.getHash())) {
			throw new BankLoginException("Incorrect password");
		}
		
		loggedIn = true;
	}
	
	public void logout() {
		dbinterface.closeConnection();
		loggedIn = false;
	}

	public void createAccount(String accountName) throws BankSQLException, BankStateException {
		if (!loggedIn) {
			throw new BankStateException("Not logged in");
		}
		
		this.dbinterface.startTransaction();
		try {
			if (this.dbinterface.lookupAccount(this.user.getUsername(), accountName) != null) {
				throw new BankStateException("Account already exists");
			}
			this.dbinterface.createAccount(this.user.getUsername(), accountName);
		} finally {
			this.dbinterface.endTransaction();
		}
	}

	public void deleteAccount(String accountName)  throws BankSQLException, BankStateException {
		if (!loggedIn) {
			throw new BankStateException("Not logged in");
		}
		
		this.dbinterface.startTransaction();		
		try {
			if (this.dbinterface.lookupAccount(this.user.getUsername(), accountName) == null) {
				throw new BankStateException("No such account");
			}
			Account acct = this.dbinterface.lookupAccount(this.user.getUsername(), accountName);
			if (acct.testBalance(0)) {
				this.dbinterface.deleteAccount(this.user.getUsername(), accountName);
			} else {
				throw new BankStateException("Account not empty");
			}
		} finally {
			this.dbinterface.endTransaction();
		}
	}

	public void withdrawal(String accountName, double withdrawAmt)  throws BankSQLException, BankStateException {
		if (!loggedIn) {
			throw new BankStateException("Not logged in");
		}
		if (withdrawAmt < 0) {
			throw new BankStateException("Cannot withdraw negative dollars");
		}
		
		this.dbinterface.startTransaction();
		try {
			Account acct = this.dbinterface.lookupAccount(this.user.getUsername(), accountName);
			if (acct == null) {
				throw new BankStateException("No such account");
			}
			if (acct.getBalance()<withdrawAmt) {
				throw new BankStateException("Insufficient funds");
			}
			this.dbinterface.deposit(this.user.getUsername(),accountName, -withdrawAmt);
		} finally {
			this.dbinterface.endTransaction();
		}
	}

	public void deposit(String accountName, double depositAmt)  throws BankSQLException, BankStateException {
		if (!loggedIn) {
			throw new BankStateException("Not logged in");
		}
		
		if (depositAmt < 0) {
			throw new BankStateException("Cannot deposit negative dollars");
		}
		
		this.dbinterface.startTransaction();
		try {
			if (this.dbinterface.lookupAccount(this.user.getUsername(), accountName) == null) {
				throw new BankStateException("No such account");
			}
			this.dbinterface.deposit(this.user.getUsername(),accountName, depositAmt);
		} finally {
			this.dbinterface.endTransaction();
		}
	}

	public void deleteUser(String username)  throws BankSQLException, BankStateException {
		if (!(loggedIn && user.isSuperuser())) {
			throw new BankStateException("Must be superuser to delete users");
		}
		
		this.dbinterface.startTransaction();
		try {
			if (this.dbinterface.lookupUser(username) == null) {
				throw new BankStateException("No such user");
			}
			this.dbinterface.deleteUser(username);
		} finally {
			this.dbinterface.endTransaction();
		}
	}

	public void updateUser(String username, String password, boolean isSuperuser)  throws BankSQLException, BankStateException {
		if (!(loggedIn && user.isSuperuser())) {
			throw new BankStateException("Must be superuser to update users");
		}
		
		this.dbinterface.startTransaction();
		try {
			if (this.dbinterface.lookupUser(username) == null) {
				throw new BankStateException("No such user");
			}
			this.dbinterface.updateUser(username,password,isSuperuser);
		} finally {
			this.dbinterface.endTransaction();
		}
		
	}

	public List<AccountBalance> getAllMyBalances()  throws BankSQLException, BankStateException {
		if (!loggedIn) {
			throw new BankStateException("Not logged in");
		}
		return this.dbinterface.getAccountMyBalances(this.user.getUsername());
	}
	
	public List<AccountBalance> getAllBalances()  throws BankSQLException, BankStateException {
		if (!(loggedIn && user.isSuperuser())) {
			throw new BankStateException("Must be superuser to view accounts");
		}
		return this.dbinterface.getAccountBalances();
	}

	public List<UserObject> getUsers()  throws BankSQLException, BankStateException {
		if (!(loggedIn && user.isSuperuser())) {
			throw new BankStateException("Must be superuser to view users");
		}
		return this.dbinterface.getUsers();
	}
	
}
