package com.revature.project0.core;

public class BankLoginException extends Exception {

	private static final long serialVersionUID = -8814407648693137846L;

	public BankLoginException() {
		super();
	}
	
	public BankLoginException(String msg) {
		super(msg);
	}
	
	public BankLoginException(String msg,Throwable e) {
		super(msg,e);
	}
}
