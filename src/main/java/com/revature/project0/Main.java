package com.revature.project0;

import com.revature.project0.IO.CommandIO;
import com.revature.project0.IO.IORunner;
import com.revature.project0.core.BankSQLException;
import com.revature.project0.core.Password;
import com.revature.project0.db.MemoryBackend;
import com.revature.project0.db.SQL_DB;

public class Main {

	
	public static void main(String[] args) {
		
		SQL_DB db = new SQL_DB();
		
		/*
		try {
			mb.makeSuperUser("default", Password.hash("password"));
		} catch (BankSQLException e) {
			e.printStackTrace();
		}
		*/
		IORunner runner = new IORunner(db);
		runner.run(new CommandIO());
	}

}
