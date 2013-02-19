package com.sims2013.disponif.model;

public class Type {

	private int mId;
	private String mName;
	
	public Type() {
		
	}
	
	public Type(int mId, String mName) {
		super();
		this.mId = mId;
		this.mName = mName;
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
}
