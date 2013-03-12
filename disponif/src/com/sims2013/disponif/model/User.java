package com.sims2013.disponif.model;

public class User {
	
	private int mId;
	private String mName;
	private String mSurname;
	private String mFacebookId;
	
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
	
	public String getFacebookId() {
		return mFacebookId;
	}

	public void setFacebookId(String mFacebookId) {
		this.mFacebookId = mFacebookId;
	}

	@Override
	public String toString() {
		return "User [mId=" + mId + ", mName=" + mName + ", mSurname="
				+ mSurname + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + mId;
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
		User other = (User) obj;
		if (mId != other.mId)
			return false;
		return true;
	}
	
	

}
