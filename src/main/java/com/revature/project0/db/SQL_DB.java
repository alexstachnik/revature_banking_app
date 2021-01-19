package com.revature.project0.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.revature.project0.core.Account;
import com.revature.project0.core.AccountBalance;
import com.revature.project0.core.BankSQLException;
import com.revature.project0.core.BankStateException;
import com.revature.project0.core.UserObject;

public class SQL_DB implements DBInterface {
	
	private Connection conn;
	
	public SQL_DB() {
		conn = null;
	}
	
	public void connectIfNot() throws BankSQLException {
		if (conn != null) {
			return;
		}
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@mydb.cdqjre4nzaux.us-east-1.rds.amazonaws.com:1521:ORCL", "admin", "congrats-unlovely-wiry");
		} catch (SQLException e) {
			throw new BankSQLException("SQL Error connecting",e);
		}
	}
	
	public void closeConnection()  {
		if (conn != null) {
			try {
				conn.close();
			} catch(SQLException e) {}
		}
	}

	public void startTransaction() throws BankSQLException {
		try {
			conn.setAutoCommit(false);
			Statement stmt = conn.createStatement();
			stmt.execute("lock table bank_users in exclusive mode");
		} catch (SQLException e) {
			try {
				conn.rollback();
				conn.setAutoCommit(true);
			} catch (SQLException e2) {
				throw new BankSQLException("Double fault when starting transaction",e2);
			}
			throw new BankSQLException("Could not start transaction",e);
		}
		
	}

	public void endTransaction() throws BankSQLException {
		try {
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			throw new BankSQLException("Error commiting transaction");
		}
	}
	
	
	
	public void makeSuperUser(String username, String password) throws BankSQLException {
		try {
			PreparedStatement stmt=conn.prepareStatement("insert into bank_users (username,id,isSuperuser,password_hash) values (?,user_id_seq.nextval,?,?)");
			stmt.setString(1, username);
			stmt.setInt(2, 1);
			stmt.setString(3, password);
			stmt.execute();
		} catch (SQLException e) {
			throw new BankSQLException("Error adding superuser",e);
		}
	}

	public void makeUser(String username, String password) throws BankSQLException {
		try {
			PreparedStatement stmt = conn.prepareStatement("insert into bank_users (username,id,isSuperuser,password_hash) values (?,user_id_seq.nextval,?,?)");
			stmt.setString(1, username);
			stmt.setInt(2, 0);
			stmt.setString(3, password);
			stmt.execute();
		} catch (SQLException e) {
			throw new BankSQLException("Error adding user",e);
		}
		
	}

	public UserObject lookupUser(String username) throws BankSQLException {
		try {
			PreparedStatement stmt = conn.prepareStatement("select id,username,password_hash,isSuperuser from bank_users where username=?");
			stmt.setString(1, username);
			ResultSet results = stmt.executeQuery();
			if (!results.next()) {
				return null;
			}
			int id=results.getInt(1);
			String hash=results.getString(3);
			int isSuperuser=results.getInt(4);
			return new UserObject(id,username,hash,isSuperuser==1);
		} catch (SQLException e) {
			throw new BankSQLException("Error looking up user",e);
		}
	}

	public Account lookupAccount(String username, String accountName) throws BankSQLException {
		try {
			PreparedStatement stmt = conn.prepareStatement("select accountname,username,balance,id from bank_accounts where username=? and accountname=?");
			stmt.setString(1, username);
			stmt.setString(2, accountName);
			ResultSet results = stmt.executeQuery();
			if (!results.next()) {
				return null;
			}
			double balance = results.getDouble(3);
			int id = results.getInt(4);
			return new Account(id,balance,accountName,username);
		} catch (SQLException e) {
			throw new BankSQLException("Error looking up account",e);
		}
	}

	public List<UserObject> getUsers() throws BankSQLException {
		List<UserObject> users = new ArrayList<UserObject>();
		try {
			PreparedStatement stmt = conn.prepareStatement("select id,username,password_hash,isSuperuser from bank_users");
			ResultSet results = stmt.executeQuery();
			while (results.next()) {
				int id=results.getInt(1);
				String username=results.getString(2);
				String hash=results.getString(3);
				int isSuperuser=results.getInt(4);
				users.add(new UserObject(id,username,hash,isSuperuser==1));
			}
		} catch (SQLException e) {
			throw new BankSQLException("Error getting users",e);
		}
		return users;
	}

	public void updateUser(String username, String password, boolean isSuperUser)
			throws BankSQLException {
		// TODO Auto-generated method stub
		
	}

	public void deleteUser(String username) throws BankSQLException {
		// TODO Auto-generated method stub
		
	}

	public void createAccount(String username, String accountName) throws BankSQLException {
		try {
			PreparedStatement stmt = conn.prepareStatement("insert into bank_accounts accountName, userName, balance, id values (?,?,?,account_id_sequence.nextval)");
			stmt.setString(1, accountName);
			stmt.setString(2, username);
			stmt.setDouble(3, 0);
			stmt.execute();
		} catch (SQLException e) {
			throw new BankSQLException("Could not create account",e);
		}
	}

	public void deleteAccount(String username, String accountName) throws BankSQLException {
		// TODO Auto-generated method stub
		
	}

	public void deposit(String username, String accountName, double amt) throws BankSQLException {
		// TODO Auto-generated method stub
		
	}

	public List<Account> getAccounts() throws BankSQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<AccountBalance> getAccountBalances() throws BankSQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public List<AccountBalance> getAccountMyBalances(String username) throws BankSQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void truncateTable() throws BankSQLException {
		try {
			Statement stmt = conn.createStatement();
			stmt.execute("truncate table bank_users");
			System.out.println("truncated");
		} catch (SQLException e) {
			throw new RuntimeException("Error truncating table",e);
		}
	}

}
