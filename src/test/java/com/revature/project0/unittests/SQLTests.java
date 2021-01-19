package com.revature.project0.unittests;

import static org.junit.Assert.*;


import org.junit.Test;

import com.revature.project0.core.BankSQLException;
import com.revature.project0.db.DBInterface;
import com.revature.project0.db.SQL_DB;

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
	
	@Test
	public void testCreateAccount() throws BankSQLException {
		SQL_DB db = new SQL_DB();
		db.connectIfNot();
		db.truncateTable();
		
	}
}
