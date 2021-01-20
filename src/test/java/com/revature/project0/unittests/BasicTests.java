package com.revature.project0.unittests;

import static org.junit.Assert.*;


import junit.framework.TestCase;

import org.junit.Test;

public class BasicTests {

	@Test
	public void successfulLogin() {
		TestHelper helper = new TestHelper();
		
		helper.addCommand("login default password\n");
		helper.addExpected(new ExpectedOutput(true,false,null));
		helper.addCommand("logout\n");
		helper.addExpected(new ExpectedOutput(true,false,null));
		helper.addCommand("quit\n");
		helper.run();
	}
	
	@Test
	public void failedLogin() {
		TestHelper helper = new TestHelper();
		
		helper.addCommand("login bar password\n");
		helper.addExpected(new ExpectedOutput(false,true,""));
		helper.run();
	}
	
	@Test
	public void invalidCommand() {
		TestHelper helper = new TestHelper();
		
		helper.addCommand("garbage\n");
		helper.addExpected(new ExpectedOutput(false, true, null));
	}
	
	@Test
	public void testInitialBalanceIsEmpty() {
		TestHelper helper = new TestHelper();
		
		helper.addCommand("login default password\n");
		helper.addExpected(new ExpectedOutput(true, false, null));
		helper.addCommand("balance\n");
		helper.addExpected(new ExpectedOutput(false, false, "BEGIN LIST"));
		helper.addExpected(new ExpectedOutput(false, false, "END LIST"));
		helper.run();
	}
	
	@Test
	public void testDeleteUser() {
		TestHelper helper = new TestHelper();
		BasicTests.setup(helper);
		
		helper.addCommand("login default password\n");
		helper.addExpected(new ExpectedOutput(true, false, null));
		helper.addCommand("delete_user normal\n");
		helper.addExpected(new ExpectedOutput(true, false, null));
		helper.addCommand("logout\n");
		helper.addExpected(new ExpectedOutput(true, false, null));
		helper.addCommand("quit\n");
		helper.addExpected(new ExpectedOutput(true, false, null));
		helper.run();
	}
	
	@Test
	public void testDepositWithdraw() {
		TestHelper helper = new TestHelper();
		BasicTests.setup(helper);
		
		helper.setDebug(true);
		helper.addCommand("login default password\n");
		helper.addExpected(new ExpectedOutput(true, false, null));
		helper.addCommand("balance\n");
		helper.addExpected(new ExpectedOutput(false, false, "BEGIN LIST"));
		helper.addExpected(new ExpectedOutput(false,false,"account1\t5.20"));
		helper.addExpected(new ExpectedOutput(false,false,"account2\t5.00"));
		helper.addExpected(new ExpectedOutput(false, false, "END LIST"));
		helper.addCommand("deposit account1 5.2\n");
		helper.addExpected(new ExpectedOutput(true, false, null));
		helper.addCommand("balance\n");
		helper.addExpected(new ExpectedOutput(false, false, "BEGIN LIST"));
		helper.addExpected(new ExpectedOutput(false,false,"account1\t10.40"));
		helper.addExpected(new ExpectedOutput(false,false,"account2\t5.00"));
		helper.addExpected(new ExpectedOutput(false, false, "END LIST"));
		helper.addCommand("withdraw account1 2.4\n");
		helper.addExpected(new ExpectedOutput(true, false, null));
		helper.addCommand("balance\n");
		helper.addExpected(new ExpectedOutput(false, false, "BEGIN LIST"));
		helper.addExpected(new ExpectedOutput(false,false,"account1\t8.00"));
		helper.addExpected(new ExpectedOutput(false,false,"account2\t5.00"));
		helper.addExpected(new ExpectedOutput(false, false, "END LIST"));
		
		helper.addCommand("withdraw account1 10.4\n");
		helper.addExpected(new ExpectedOutput(false, true, null));
		helper.addCommand("withdraw account1 -3\n");
		helper.addExpected(new ExpectedOutput(false, true, null));
		helper.addCommand("deposit account1 -3\n");
		helper.addExpected(new ExpectedOutput(false, true, null));
		
		
		helper.addCommand("logout\n");
		helper.addExpected(new ExpectedOutput(true, false, null));
		helper.addCommand("quit\n");
		helper.addExpected(new ExpectedOutput(true, false, null));
		helper.run();
	}
	
	@Test
	public void testSuperuser() {
		TestHelper helper = new TestHelper();
		BasicTests.setup(helper);
		
		helper.addCommand("login super bar\n");
		helper.addExpected(new ExpectedOutput(true, false, null));
		helper.addCommand("view_accounts\n");
		helper.addExpected(new ExpectedOutput(false, false, "BEGIN LIST"));
		helper.addExpected(new ExpectedOutput(false, false, "account1\t5.20\tdefault"));
		helper.addExpected(new ExpectedOutput(false, false, "account2\t5.00\tdefault"));
		helper.addExpected(new ExpectedOutput(false, false, "account1\t3.20\tnormal"));
		helper.addExpected(new ExpectedOutput(false, false, "account2\t5.00\tnormal"));
		helper.addExpected(new ExpectedOutput(false, false, "END LIST"));
		helper.addCommand("register_superuser a b\n");
		helper.addExpected(new ExpectedOutput(true, false, ""));
		
		helper.addCommand("view_users\n");
		helper.addExpected(new ExpectedOutput(false, false, "BEGIN LIST"));
		helper.addExpected(new ExpectedOutput(false, false, "a"));
		helper.addExpected(new ExpectedOutput(false, false, "default"));
		helper.addExpected(new ExpectedOutput(false, false, "normal"));
		helper.addExpected(new ExpectedOutput(false, false, "super"));
		helper.addExpected(new ExpectedOutput(false, false, "END LIST"));
		
		helper.addCommand("logout\n");
		helper.addExpected(new ExpectedOutput(true, false, null));
		
		helper.addCommand("login normal foo\n");
		helper.addExpected(new ExpectedOutput(true, false, null));
		helper.addCommand("view_accounts\n");
		helper.addExpected(new ExpectedOutput(false,true,null));
		helper.addCommand("register_superuser a b\n");
		helper.addExpected(new ExpectedOutput(false,true,null));
		helper.addCommand("delete_user super\n");
		helper.addExpected(new ExpectedOutput(false,true,null));
		
		
		helper.addCommand("view_users\n");
		helper.addExpected(new ExpectedOutput(false,true,null));
		
		helper.addCommand("logout\n");
		helper.addExpected(new ExpectedOutput(true, false, null));
		
		helper.addCommand("quit\n");
		helper.addExpected(new ExpectedOutput(true,false,null));
		
		
		helper.run();
	}
	
	public static void setup(TestHelper helper) {
		helper.addCommand("login default password\n");
		helper.addExpected(new ExpectedOutput(true, false, null));
		helper.addCommand("create_account account1\n");
		helper.addExpected(new ExpectedOutput(true, false, null));
		helper.addCommand("create_account account2\n");
		helper.addExpected(new ExpectedOutput(true, false, null));
		
		helper.addCommand("register_superuser super bar\n");
		helper.addExpected(new ExpectedOutput(true, false, null));
		helper.addCommand("register normal foo\n");
		helper.addExpected(new ExpectedOutput(true, false, null));
		
		helper.addCommand("deposit account1 5.2\n");
		helper.addExpected(new ExpectedOutput(true, false, null));
		helper.addCommand("deposit account2 5\n");
		helper.addExpected(new ExpectedOutput(true, false, null));
		
		helper.addCommand("logout\n");
		helper.addExpected(new ExpectedOutput(true, false, null));
		

		helper.addCommand("login normal foo\n");
		helper.addExpected(new ExpectedOutput(true, false, null));
		helper.addCommand("create_account account1\n");
		helper.addExpected(new ExpectedOutput(true, false, null));
		helper.addCommand("create_account account2\n");
		helper.addExpected(new ExpectedOutput(true, false, null));
		helper.addCommand("deposit account1 3.2\n");
		helper.addExpected(new ExpectedOutput(true, false, null));
		helper.addCommand("deposit account2 5\n");
		helper.addExpected(new ExpectedOutput(true, false, null));
		
		helper.addCommand("logout\n");
		helper.addExpected(new ExpectedOutput(true, false, null));	
	}
	
	@Test
	public void testAccountAmt() {
		TestCase test = new TestAccountAmt();
		test.run();
	}
}
