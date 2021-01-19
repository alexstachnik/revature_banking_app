package com.revature.project0.core;

public class BankRegisterException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3624242541048500108L;
	
	public BankRegisterException() {
		super();
	}
	public BankRegisterException(String msg) {
		super(msg);
	}
	public BankRegisterException(String msg, Exception e) {
		super(msg,e);
	}

}
