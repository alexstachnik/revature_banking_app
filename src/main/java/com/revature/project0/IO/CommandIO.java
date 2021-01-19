package com.revature.project0.IO;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CommandIO implements UserIO {

	private Scanner scanner;
	
	public CommandIO() {
		this.scanner = new Scanner(System.in);
	}
	
	public Command getCommand() throws IOException {
		System.out.print("> ");
		if (!scanner.hasNext()) {
			throw new IOException("IO Closed");
		}
		
		try {
			String line=scanner.nextLine();
			Command c = Command.ParseCommand(line);
			return c;
		} catch (NoSuchElementException e) {
			throw new IOException("Could not get line");
		} catch (IllegalStateException e) {
			throw new IOException("IO Closed");
		}
	}

	public void closeIO() {
		scanner.close();
	}

	public void sendLine(String line) {
		System.out.println(line);
	}
	
	public void printException(Exception e) {
		System.out.println(e.getMessage());
	}
	
	public void sendFatalException(Exception e) {
		throw new RuntimeException(e);
	}

	public void sendSuccess(String line) {
		System.out.println(line);
	}

	public void sendDebug(String line) {
		
	}
}
