package com.sims2013.disponif.fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sims2013.disponif.DisponifApplication;
import com.sims2013.disponif.R;
import com.sims2013.disponif.Utils.BitmapManager;
import com.sims2013.disponif.Utils.DisponIFUtils;
import com.sims2013.disponif.adapter.MatchAvailabilityAdapter;
import com.sims2013.disponif.model.Availability;
import com.tjerkw.slideexpandable.library.ActionSlideExpandableListView;

public class MatchAvailabilityListFragment extends GenericFragment implements
		OnLongClickListener {

	public static final String EXTRA_AVAILABILITY_ID = "com.sims2013.disponif.fragments.MatchAvailabilityListFragment.EXTRA_AVAILABILITY_ID";
	public static final String EXTRA_CATEGORY_ID = "com.sims2013.disponif.fragments.MatchAvailabilityListFragment.EXTRA_CATEGORY_ID";
	public static final String EXTRA_CATEGORY_NAME = "com.sims2013.disponif.fragments.MatchAvailabilityListFragment.EXTRA_CATEGORY_NAME";
	public static final String EXTRA_DESCRIPTION = "com.sims2013.disponif.fragments.MatchAvailabilityListFragment.EXTRA_DESCRIPTION";
	public static final String EXTRA_START_TIME = "com.sims2013.disponif.fragments.MatchAvailabilityListFragment.EXTRA_START_TIME";
	public static final String EXTRA_END_TIME = "com.sims2013.disponif.fragments.MatchAvailabilityListFragment.EXTRA_END_TIME";
	public static final String EXTRA_TYPE_NAME = "com.sims2013.disponif.fragments.MatchAvailabilityListFragment.EXTRA_TYPE_NAME";

	MatchAvailabilityAdapter mAdapter;
	ActionSlideExpandableListView mListView;

	int mCurrentAvailabilityId;
	String mCategoryName;
	int mCategoryId;
	String mDescription;
	String mStartTime;
	String mEndTime;
	String mTypeName;

	// UI references
	ImageView mProfilePicture;
	ImageView mCategoryIcon;
	ImageView mLiveIcon;
	TextView mCategoryNameTv;
	TextView mDescriptionTv;
	TextView mDateTv;
	TextView mDateSimpleTv;

	private boolean mRefreshingList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().getWindow().getAttributes().windowAnimations = R.style.slideRight; 
		setHasOptionsMenu(true);
		mCurrentAvailabilityId = getArguments().getInt(EXTRA_AVAILABILITY_ID,
				-1);
		mCategoryName = getArguments().getString(EXTRA_CATEGORY_NAME);
		mCategoryId = getArguments().getInt(EXTRA_CATEGORY_ID);
		mDescription = getArguments().getString(EXTRA_DESCRIPTION);
		mStartTime = getArguments().getString(EXTRA_START_TIME);
		mEndTime = getArguments().getString(EXTRA_END_TIME);
		mTypeName = getArguments().getString(EXTRA_TYPE_NAME);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		super.onCreateView(inflater, container, savedInstanceState);

		mView = inflater.inflate(R.layout.fragment_list_match_availability,
				container, false);
		initUI();
		return mView;
	}

	@Override
	protected void initUI() {
		super.initUI();
		
		mListView = (ActionSlideExpandableListView) mView
				.findViewById(R.id.list);
		
		mCategoryNameTv = (TextView) mView.findViewById(R.id.selected_availability_category_type);
		mDateSimpleTv = (TextView) mView.findViewById(R.id.selected_availability_time_simple);
		mDateTv = (TextView) mView.findViewById(R.id.selected_availability_date);
		mDescriptionTv = (TextView) mView.findViewById(R.id.selected_availability_description);
		mProfilePicture = (ImageView) mView.findViewById(R.id.selected_availability_profile_picture);
		mCategoryIcon = (ImageView) mView.findViewById(R.id.selected_availability_category_icon);
		mLiveIcon = (ImageView) mView.findViewById(R.id.selected_availability_live_icon);
		
		BitmapManager.setBitmap(mProfilePicture, "http://graph.facebook.com/"
				+ DisponifApplication.getFacebookId()
				+ "/picture?type=large", R.drawable.bkg_white_gray_border);
		
		BitmapManager.setBitmap(mCategoryIcon, "http://disponif.darkserver.fr/server/res/category/"+ mCategoryId +".png");
		String catTxt = mCategoryName;
		if (mTypeName != null && !mTypeName.isEmpty()) {
			catTxt += " - " + mTypeName;
		}
		mCategoryNameTv.setText(catTxt);
		
		Date startDate = DisponIFUtils.stringToDate(mStartTime);
		Date today = new Date();
		int diffInDays = (int)( (startDate.getTime() - today.getTime()) 
                / (1000 * 60 * 60 * 24) );
		int diffInHours = Math.round(( (startDate.getTime() - today.getTime()) 
                / (1000 * 60 * 60) )) + 1; 
		
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(today);   
		int currHour = calendar.get(Calendar.HOUR_OF_DAY);
		
		if (diffInHours <= 0) {
			mDateSimpleTv.setVisibility(View.GONE);
			mLiveIcon.setVisibility(View.VISIBLE);
		} else if (diffInHours<(24-currHour)) {
			mLiveIcon.setVisibility(View.GONE);
			mDateSimpleTv.setText(getString(R.string.availability_date_simple_today));
		} else if (diffInDays == 0){
			mLiveIcon.setVisibility(View.GONE);
			mDateSimpleTv.setText(getString(R.string.availability_date_simple, 1)); 
		} else {
			mLiveIcon.setVisibility(View.GONE);
			mDateSimpleTv.setText(getString(R.string.availability_date_simple, diffInDays));
		}
		
		mDateTv.setText("Du "
				+ DisponIFUtils.datetimeToFrDate(getActivity(),
						mStartTime)
		+ " à "
		+ DisponIFUtils.datetimeToFrTime(getActivity(),
				mStartTime) + " au "
				+ DisponIFUtils.datetimeToFrDate(getActivity(),
				mEndTime)
		+ " à "
		+ DisponIFUtils.datetimeToFrTime(getActivity(),
				mEndTime));
		
		mDescriptionTv.setText(mDescription);
		refresh();
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);

		MenuItem refreshMenuItem = menu.findItem(R.id.menu_item_refresh);

		if (getRefreshMode()) {
			refreshMenuItem
					.setActionView(R.layout.action_bar_indeterminate_progress);
		} else {
			refreshMenuItem.setActionView(null);
			// Default
		}
	}

	private boolean getRefreshMode() {
		return mRefreshingList;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_item_refresh) {
			refresh();
		}
		return true;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.activity_match_availability_list_menu, menu);
	}

	@Override
	public void onMatchAvailabilitiesReceive(
			ArrayList<Availability> availabilities, int startRow, int endRow) {
		super.onMatchAvailabilitiesReceive(availabilities, startRow, endRow);
		mRefreshingList = false;
		if (availabilities != null) {
			mAdapter = new MatchAvailabilityAdapter(this,
					R.layout.item_match_availability, availabilities);
			mListView.setAdapter(mAdapter);
		}
		mListView.setEmptyView(mView.findViewById(R.id.empty_list));
		getActivity().invalidateOptionsMenu();
	}

	@Override
	protected void refresh() {
		mRefreshingList = true;
		getActivity().invalidateOptionsMenu();
		shouldShowProgressDialog(true);
		mClient.getMatchAvailabilities(DisponifApplication.getAccessToken(),
				mCurrentAvailabilityId, 0, 10);
	}

	@Override
	public void onLogInTokenReceive(String token) {
		super.onLogInTokenReceive(token);
		refresh();
	}

	@Override
	public boolean onLongClick(View arg0) {
		return false;
	}

}
