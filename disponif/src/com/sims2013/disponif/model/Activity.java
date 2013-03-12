package com.sims2013.disponif.model;

import java.util.ArrayList;

public class Activity {
	private int mId;
	private String mStatus;
	private ArrayList<User> mUsers;
	private ArrayList<Comment> mComments;
	
	public Activity() {
		
	}
	
	public String getStatus() {
		return mStatus;
	}
	public void setStatus(String mStatus) {
		this.mStatus = mStatus;
	}
	public int getId() {
		return mId;
	}
	public void setId(int mId) {
		this.mId = mId;
	}
	public ArrayList<User> getUsers() {
		return mUsers;
	}
	public void setUsers(ArrayList<User> mUsers) {
		this.mUsers = mUsers;
	}
	public ArrayList<Comment> getComments() {
		return mComments;
	}
	public void setComments(ArrayList<Comment> mComments) {
		this.mComments = mComments;
	}
	
	
}
