package com.revature.project0.core;

public class BankSQLException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5565278866533815802L;

	public BankSQLException() {
		super();
	}
	
	public BankSQLException(String msg) {
		super(msg);
	}
	
	public BankSQLException(String msg,Exception e) {
		super(msg,e);
	}
	
}
