package com.sims2013.disponif.model;


public class Availability {

	public static final int TYPE_NO_TYPE = -1;
	
	public static final String STATUS_CLOSED = "closed";
	public static final String STATUS_IN_PROGRESS = "in progress";
	public static final String STATUS_OPEN = "open";
	
	private int mId;
	private String mUserId;
	private int mCategoryId;
	private int mTypeId;
	private String mCategoryName;
	private String mTypeName;
	private float mLatitude;
	private float mLongitude;
	private int mRadius;
	private String mPrivacy;
	private String mStatus;
	private int mMaxParticipant;
	private String mOption;
	private String mStartTime;
	private String mEndTime;
	private String mDescription;
	
	private User mUser;
	
	public Availability() {
		
	}
	
	public Availability(int mId, String mUserId, int mCategoryId, int mTypeId,
			float mLatitude, float mLongitude, int mRadius, String mPrivacy,
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
		this.mUser = null;
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
	public String getPrivacy() {
		return mPrivacy;
	}
	public void setPrivacy(String mPrivacy) {
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
	public String getDescription() {
		return mDescription;
	}
	public void setDescription(String mDescription) {
		this.mDescription = mDescription;
	}

	public User getUser() {
		return mUser;
	}

	public void setUser(User mUser) {
		this.mUser = mUser;
	}

	@Override
	public String toString() {
		String user;
		if (mUser != null) {
			user = mUser.toString();
		} else {
			user = "me";
		}
		return "ID : " + mId + "\nDescription : "+mDescription + "\nUser : " + user;
	}

	public String getCategoryName() {
		return mCategoryName;
	}

	public void setCategoryName(String mCategoryName) {
		this.mCategoryName = mCategoryName;
	}

	public String getTypeName() {
		return mTypeName;
	}

	public void setTypeName(String mTypeName) {
		this.mTypeName = mTypeName;
	}
}
