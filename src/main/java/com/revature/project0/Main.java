package com.revature.project0;

import com.revature.project0.IO.CommandIO;
import com.revature.project0.IO.IORunner;
import com.revature.project0.core.BankSQLException;
import com.revature.project0.core.Password;
import com.revature.project0.db.MemoryBackend;

public class Main {

	public static void main(String[] args) {
		
		MemoryBackend mb = new MemoryBackend();
		try {
			mb.makeSuperUser("default", Password.hash("password"));
		} catch (BankSQLException e) {
			e.printStackTrace();
		}
		IORunner runner = new IORunner(mb);
		runner.run(new CommandIO());
	}

}
