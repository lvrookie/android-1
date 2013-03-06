package com.sims2013.disponif.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.widget.ProfilePictureView;
import com.sims2013.disponif.R;
import com.sims2013.disponif.model.Availability;

public class AvailabilityCellView extends LinearLayout {

	private ProfilePictureView mProfilePicture;
	private Button mPrivacy;
	private TextView mStartTime;
	private TextView mEndTime;
	private TextView mCategoryAndType;
	private View mDropdown;

	public AvailabilityCellView(Context context) {
		this(context, null);
	}

	public AvailabilityCellView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public AvailabilityCellView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);

		LayoutInflater.from(context).inflate(R.layout.item_availability, this,
				true);

//		mProfilePicture = (ProfilePictureView) findViewById(R.id.item_availability_user_profile_picture);
		mPrivacy = (Button) findViewById(R.id.expandable_toggle_button);
//		mStartTime = (TextView) findViewById(R.id.item_availability_startDate);
//		mEndTime = (TextView) findViewById(R.id.item_availability_endDate);
//		mCategoryAndType = (TextView) findViewById(R.id.item_availability_category_and_type);
		mDropdown = findViewById(R.id.expandable);
	}

	public void setObject(Object object) {

		Availability availability = (Availability) object;

//		Session session = Session.getActiveSession();
//		Request request = Request.newMeRequest(session,
//				new Request.GraphUserCallback() {
//
//					@Override
//					public void onCompleted(GraphUser user, Response response) {
//						// If the response is successful
//						if (user != null) {
//							// Set the id for the ProfilePictureView
//							// view that in turn displays the profile
//							// picture.
//							mProfilePicture.setProfileId(user.getId());
//						}
//						if (response.getError() != null) {
//							// Handle errors, will do so later.
//						}
//					}
//				});
//		request.executeAsync();

//		mStartTime.setText("du "
//				+ DisponIFUtils.datetimeToFrDate(getContext(),
//						availability.getStartTime())
//				+ " - "
//				+ DisponIFUtils.datetimeToFrTime(getContext(),
//						availability.getStartTime()));
//		mEndTime.setText("au "
//				+ DisponIFUtils.datetimeToFrDate(getContext(),
//						availability.getEndTime())
//				+ " - "
//				+ DisponIFUtils.datetimeToFrTime(getContext(),
//						availability.getEndTime()));
//		mCategoryAndType.setText("Category : " + availability.getCategoryId()
//				+ "     Type : " + availability.getTypeId());

		// switch (availability.getPrivacy()) {
		// case 0:
		// mHolder.mPrivacy.setImageDrawable(getResources().getDrawable(
		// R.drawable.privacy_private));
		// break;
		// case 1:
		// mHolder.mPrivacy.setImageDrawable(getResources().getDrawable(
		// R.drawable.privacy_public));
		// break;
		// }
	}
}
