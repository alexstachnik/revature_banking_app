package com.revature.project0.IO;

import java.io.IOException;

public interface UserIO {

	public Command getCommand() throws IOException;
	
	public void sendLine(String line);
	
	public void sendSuccess(String line);
	
	public void sendDebug(String line);
	
	public void printException(Exception e);
	
	public void sendFatalException(Exception e);
	
	public void closeIO();
	
}
