package com.sims2013.disponif.model;


public class Availability {

	private int mId;
	private String mUserId;
	private int mCategoryId;
	private int mTypeId;
	private float mLatitude;
	private float mLongitude;
	private int mRadius;
	private int mPrivacy;
	private String mStatus;
	private int mMaxParticipant;
	private String mOption;
	private String mStartTime;
	private String mEndTime;
	
	public Availability(int mId, String mUserId, int mCategoryId, int mTypeId,
			float mLatitude, float mLongitude, int mRadius, int mPrivacy,
			String mStatus, int mMaxParticipant, String mOption,
			String mStartTime, String mEndTime) {
		super();
		this.mId = mId;
		this.mUserId = mUserId;
		this.mCategoryId = mCategoryId;
		this.mTypeId = mTypeId;
		this.mLatitude = mLatitude;
		this.mLongitude = mLongitude;
		this.mRadius = mRadius;
		this.mPrivacy = mPrivacy;
		this.mStatus = mStatus;
		this.mMaxParticipant = mMaxParticipant;
		this.mOption = mOption;
		this.mStartTime = mStartTime;
		this.mEndTime = mEndTime;
	}
	public int getId() {
		return mId;
	}
	public void setId(int mId) {
		this.mId = mId;
	}
	public String getUserId() {
		return mUserId;
	}
	public void setUserId(String mUserId) {
		this.mUserId = mUserId;
	}
	public int getCategoryId() {
		return mCategoryId;
	}
	public void setCategoryId(int mCategoryId) {
		this.mCategoryId = mCategoryId;
	}
	public int getTypeId() {
		return mTypeId;
	}
	public void setTypeId(int mTypeId) {
		this.mTypeId = mTypeId;
	}
	public float getLatitude() {
		return mLatitude;
	}
	public void setLatitude(float mLatitude) {
		this.mLatitude = mLatitude;
	}
	public float getLongitude() {
		return mLongitude;
	}
	public void setLongitude(float mLongitude) {
		this.mLongitude = mLongitude;
	}
	public int getRadius() {
		return mRadius;
	}
	public void setRadius(int mRadius) {
		this.mRadius = mRadius;
	}
	public int getPrivacy() {
		return mPrivacy;
	}
	public void setPrivacy(int mPrivacy) {
		this.mPrivacy = mPrivacy;
	}
	public String getStatus() {
		return mStatus;
	}
	public void setStatus(String mStatus) {
		this.mStatus = mStatus;
	}
	public int getMaxParticipant() {
		return mMaxParticipant;
	}
	public void setMaxParticipant(int mMaxParticipant) {
		this.mMaxParticipant = mMaxParticipant;
	}
	public String getOption() {
		return mOption;
	}
	public void setOption(String mOption) {
		this.mOption = mOption;
	}
	public String getStartTime() {
		return mStartTime;
	}
	public void setStartTime(String mStartTime) {
		this.mStartTime = mStartTime;
	}
	public String getEndTime() {
		return mEndTime;
	}
	public void setEndTime(String mEndTime) {
		this.mEndTime = mEndTime;
	}
}
