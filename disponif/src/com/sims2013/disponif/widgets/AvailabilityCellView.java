package com.sims2013.disponif.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sims2013.disponif.R;
import com.sims2013.disponif.model.Availability;

public class AvailabilityCellView extends RelativeLayout{
	ImageView mProfilePicture;
	TextView mCategoryAndType;
	TextView mUsername;
	ImageView mPrivacy;
	
	public AvailabilityCellView(Context context) {
		this(context, null);
	}

	public AvailabilityCellView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public AvailabilityCellView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);

		LayoutInflater.from(context).inflate(
				R.layout.item_availability, this, true);
		
		mProfilePicture = (ImageView) findViewById(R.id.item_availability_user_profile_picture);
		mPrivacy= (ImageView) findViewById(R.id.item_availability_privacy);
		mUsername = (TextView) findViewById(R.id.item_availability_username);
		mCategoryAndType= (TextView) findViewById(R.id.item_availability_category_and_type);
	}
	
	public void setObject(Object object){
		Availability availability = (Availability) object;
		mUsername.setText("UserId = " + availability.getUserId());
		mCategoryAndType.setText("Category : " + availability.getCategoryId() + " - Type : " + availability.getTypeId());
		switch (availability.getPrivacy()) {
		case 0:
			mPrivacy.setImageDrawable(getResources().getDrawable(R.drawable.privacy_private)); 
			break;
		case 1:
			mPrivacy.setImageDrawable(getResources().getDrawable(R.drawable.privacy_public)); 
			break;
		}
	}
}
