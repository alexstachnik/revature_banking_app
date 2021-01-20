package com.revature.project0.unittests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.revature.project0.IO.DebugMessage;
import com.revature.project0.IO.IORunner;
import com.revature.project0.IO.StringIO;
import com.revature.project0.core.BankSQLException;
import com.revature.project0.core.Password;
import com.revature.project0.db.DBInterface;
import com.revature.project0.db.MemoryBackend;
import com.revature.project0.db.SQL_DB;

public class TestHelper {
	
	private DBInterface db;
	private StringBuilder sb;
	private List<ExpectedOutput> expected;
	private boolean debug;
	
	public TestHelper() {
		sb = new StringBuilder();
		expected = new ArrayList<ExpectedOutput>();
		db = new SQL_DB();
		try {
			db.connectIfNot();
			db.truncateTable();
			db.makeSuperUser("default", Password.hash("password"));
		} catch (BankSQLException e) {
			throw new RuntimeException(e);
		}
		debug=false;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public void addExpected(ExpectedOutput output) {
		expected.add(output);
	}
	
	public void addCommand(String cmd) {
		sb.append(cmd);
	}
	
	public void run() {
		StringIO io = new StringIO(false,sb.toString());
		new IORunner(db).run(io);
		for (int i=0;i<expected.size();++i) {
			DebugMessage m = io.getOutput();
			if (this.debug) {
				System.out.println(m);
			}
			if (expected.get(i) == null) {
				assertTrue(m==null);
			} else {
				assertTrue(expected.get(i).isMatch(m));
			}
		}
		
	}
	
}
