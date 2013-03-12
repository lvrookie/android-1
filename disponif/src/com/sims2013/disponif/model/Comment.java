package com.sims2013.disponif.model;

public class Comment {

	private User mUser;
	private String mMessage;
	private String mDate;
	
	public Comment() {
		
	}
	
	public User getUser() {
		return mUser;
	}
	public void setUser(User mUser) {
		this.mUser = mUser;
	}
	public String getMessage() {
		return mMessage;
	}
	public void setMessage(String mMessage) {
		this.mMessage = mMessage;
	}

	public String getDate() {
		return mDate;
	}

	public void setDate(String mDate) {
		this.mDate = mDate;
	}
	
	
	
}
