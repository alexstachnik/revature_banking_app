package com.revature.project0.IO;

public class DoubleArgument implements Argument {
	
	private double arg;
	
	public DoubleArgument(double arg) {
		this.arg=arg;
	}

	public String getStringArg() {
		return Double.toString(arg);
	}

	public int getIntArg() {
		throw new IllegalArgumentException("Not an integer" + Double.toString(arg));
	}

	public boolean isStringArg() {
		return true;
	}

	public boolean isIntArg() {
		return false;
	}

	public boolean isFloatArg() {
		return true;
	}

	public double getFloatArg() {
		return arg;
	}

}
