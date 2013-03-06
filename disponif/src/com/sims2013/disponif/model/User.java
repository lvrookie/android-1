package com.sims2013.disponif.model;

public class User {
	
	private int mId;
	private String mName;
	private String mSurname;
	
	public User() {
		
	}
	
	public User(int mId, String mName, String mSurname) {
		super();
		this.mId = mId;
		this.mName = mName;
		this.mSurname = mSurname;
	}

	public int getId() {
		return mId;
	}

	public void setId(int mId) {
		this.mId = mId;
	}

	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
	}

	public String getSurname() {
		return mSurname;
	}

	public void setSurname(String mSurname) {
		this.mSurname = mSurname;
	}

	@Override
	public String toString() {
		return "User [mId=" + mId + ", mName=" + mName + ", mSurname="
				+ mSurname + "]";
	}

}
