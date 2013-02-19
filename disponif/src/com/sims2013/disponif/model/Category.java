package com.sims2013.disponif.model;

import java.util.ArrayList;

public class Category {

	private int mId;
	private String mName;
	ArrayList<Type> mTypes;
	
	public Category() {
		
	}
	
	public Category(int mId, String mName, ArrayList<Type> mTypes) {
		super();
		this.mId = mId;
		this.mName = mName;
		this.mTypes = mTypes;
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

	public ArrayList<Type> getTypes() {
		return mTypes;
	}

	public void setTypes(ArrayList<Type> mTypes) {
		this.mTypes = mTypes;
	}
	
	
	
	
}
