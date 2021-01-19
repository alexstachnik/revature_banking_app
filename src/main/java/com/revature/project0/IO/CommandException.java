package com.revature.project0.IO;

public class CommandException extends Exception {
	
	private static final long serialVersionUID = -8730452238430621743L;

	public CommandException() {
		super();
	}
	
	public CommandException(String msg) {
		super(msg);
	}
	
	public CommandException(String msg,Throwable e) {
		super(msg,e);
	}

}
