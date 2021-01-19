package com.revature.project0.core;

public class UserObject implements Comparable<UserObject> {

	private String username;
	private String hash;
	private boolean isSuperuser;
	private int userID;
	
	public UserObject(int userID, String username, String hash, boolean isSuperuser) {
		this.username = username;
		this.hash = hash;
		this.isSuperuser = isSuperuser;
		this.userID = userID;
	}

	@Override
	public String toString() {
		return "UserObject [username=" + username + ", hash=" + hash + ", isSuperuser=" + isSuperuser + ", userID="
				+ userID + "]";
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}



	public boolean isSuperuser() {
		return isSuperuser;
	}



	public void setSuperuser(boolean isSuperuser) {
		this.isSuperuser = isSuperuser;
	}

	public int compareTo(UserObject o) {
		int x;
		x = this.username.compareTo(o.getUsername());
		if (x == 0) {
			x = this.hash.compareTo(o.getHash());
			if (x == 0) {
				x = Integer.compare(this.userID, o.getUserID());
				if (x == 0) {
					x = Boolean.compare(this.isSuperuser, o.isSuperuser());
				}
			}
		}
		return x;
	}
	
	
	
}
