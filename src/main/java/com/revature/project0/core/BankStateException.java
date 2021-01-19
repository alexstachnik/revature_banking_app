package com.revature.project0.core;

public class BankStateException extends Exception {

	private static final long serialVersionUID = -5625141067504750720L;

	public BankStateException() {
		super();
	}
	
	public BankStateException(String msg) {
		super(msg);
	}

	public BankStateException(String msg, Throwable e) {
		super(msg,e);
	}
}
