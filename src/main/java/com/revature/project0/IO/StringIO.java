package com.revature.project0.IO;

import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

public class StringIO implements UserIO {
	
	private Scanner scanner;
	private boolean throwOnError;
	private Deque<DebugMessage> output;
	
	public StringIO(boolean throwOnError, String commands) {
		this.throwOnError=throwOnError;
		scanner = new Scanner(commands);
		this.output = new LinkedList<DebugMessage>();
	}

	public Command getCommand() throws IOException {		
		if (!scanner.hasNext()) {
			throw new IOException("EOF");
		}
		
		String line=scanner.nextLine();
		Command c = Command.ParseCommand(line);
		return c;
	}
	
	public DebugMessage getOutput() {
		return this.output.pollFirst();
	}

	public void sendLine(String line) {
	}
	
	public void printException(Exception e) {
		DebugMessage msg = new DebugMessage();
		msg.setMessage(e.getMessage());
		msg.setError(true);
		output.addLast(msg);
		
		if (throwOnError) {
			throw new RuntimeException("Error",e);
		}
	}
	
	public void sendFatalException(Exception e) {
		DebugMessage msg = new DebugMessage();
		msg.setMessage(e.getMessage());
		msg.setError(true);
		output.addLast(msg);
		
		throw new RuntimeException("Error",e);
	}

	public void closeIO() {
		
	}

	public void sendSuccess(String line) {
		DebugMessage msg = new DebugMessage();
		msg.setMessage(line);
		msg.setSuccess(true);
		this.output.addLast(msg);
	}

	public void sendDebug(String line) {
		DebugMessage msg = new DebugMessage();
		msg.setMessage(line);
		this.output.addLast(msg);
	}

}
