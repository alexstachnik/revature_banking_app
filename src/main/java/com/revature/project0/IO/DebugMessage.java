package com.revature.project0.IO;

public class DebugMessage {

	private String message;
	
	private boolean isError;
	
	private boolean isSuccess;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isError() {
		return isError;
	}

	public void setError(boolean isError) {
		this.isError = isError;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	public DebugMessage() {
	}

	@Override
	public String toString() {
		return "DebugMessage [message=" + message + ", isError=" + isError + ", isSuccess=" + isSuccess + "]";
	}
}
