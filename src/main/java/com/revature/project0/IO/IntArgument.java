package com.revature.project0.IO;

public class IntArgument implements Argument {

	private int arg;
	
	public IntArgument(int arg) {
		this.arg=arg;
	}
	
	public String getStringArg() {
		return Integer.toString(arg);
	}

	public int getIntArg() {
		return arg;
	}

	@Override
	public String toString() {
		return "IntArgument [arg=" + arg + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + arg;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IntArgument other = (IntArgument) obj;
		if (arg != other.arg)
			return false;
		return true;
	}
	
	public double getFloatArg() {
		return arg;
	}

	public boolean isStringArg() {
		return true;
	}

	public boolean isIntArg() {
		return true;
	}

	public boolean isFloatArg() {
		return true;
	}

}
