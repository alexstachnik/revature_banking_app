package com.revature.project0.IO;

public class StringArgument implements Argument {

	private String arg;
	
	public StringArgument(String arg) {
		this.arg = arg;
	}
	
	public String getStringArg() {
		return arg;
	}

	public int getIntArg() {
		throw new IllegalArgumentException("Not an int: "+arg);
	}

	@Override
	public String toString() {
		return "StringArgument [arg=" + arg + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arg == null) ? 0 : arg.hashCode());
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
		StringArgument other = (StringArgument) obj;
		if (arg == null) {
			if (other.arg != null)
				return false;
		} else if (!arg.equals(other.arg))
			return false;
		return true;
	}

	public boolean isStringArg() {
		return true;
	}

	public boolean isIntArg() {
		return false;
	}

	public boolean isFloatArg() {
		return false;
	}

	public double getFloatArg() {
		throw new IllegalArgumentException("Not an float: "+arg);
	}

}
