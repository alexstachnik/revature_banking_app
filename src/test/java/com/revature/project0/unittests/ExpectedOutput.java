package com.revature.project0.unittests;

import com.revature.project0.IO.DebugMessage;

public class ExpectedOutput {

	private boolean isSuccess;
	private boolean isFailure;
	private String msg;
	
	public ExpectedOutput (boolean isSuccess, boolean isFailure, String msg) {
		this.isSuccess=isSuccess;
		this.isFailure=isFailure;
		this.msg=msg;
	}
	
	@Override
	public String toString() {
		return "ExpectedOutput [isSuccess=" + isSuccess + ", isFailure=" + isFailure + ", msg=" + msg + "]";
	}

	public boolean isMatch(DebugMessage msgIn) {
		if (this.isSuccess && msgIn.isSuccess()) {
			return true;
		}
		
		if (this.isFailure && msgIn.isError()) {
			return true;
		}
		
		if (this.msg == null) {
			return msgIn.getMessage()==null;
		} else {
			return this.msg.equals(msgIn.getMessage());
		}
	}

	public boolean isSucess() {
		return isSuccess;
	}

	public void setSucess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public boolean isFailure() {
		return isFailure;
	}

	public void setFailure(boolean isFailure) {
		this.isFailure = isFailure;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
