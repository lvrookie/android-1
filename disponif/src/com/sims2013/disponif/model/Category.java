package com.sims2013.disponif.model;

import java.util.ArrayList;

import android.graphics.Bitmap;

public class Category {

	private int mId;
	private String mName;
	private int mRadius;
	private Bitmap mIcon;
	ArrayList<Type> mTypes;
	
	public Category() {
		mTypes = new ArrayList<Type>();
	}
	
	public Category(int mId, String mName, int radius, ArrayList<Type> mTypes) {
		super();
		this.mId = mId;
		this.mName = mName;
		this.mTypes = mTypes;
		this.mRadius = radius;
	}
	
	public void addType(Type type) {
		mTypes.add(type);
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

	public int getRadius() {
		return mRadius;
	}

	public void setRadius(int mRadius) {
		this.mRadius = mRadius;
	}

	@Override
	public String toString() {
		return mName;
	}
	
	public Bitmap getIcon() {
		return mIcon;
	}
	
	public void setIcon(Bitmap mIcon) {
		this.mIcon = mIcon;
	}
}
