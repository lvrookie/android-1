package com.sims2013.disponif.activities;

import android.os.Bundle;

import com.sims2013.disponif.fragments.ActivityFragment;

public class ActivityActivity extends GenericActivity {

	public static final String EXTRA_ACTIVITY_NAME = "com.sims2013.disponif.activities.ActivityActivity.EXTRA_ACTIVITY_NAME";
	public static final String EXTRA_REQUESTED_AVAILABILITY_ID = "com.sims2013.disponif.activities.ActivityActivity.EXTRA_REQUESTED_AVAILABILITY_ID";
	public static final String EXTRA_AVAILABILITY_ID = "com.sims2013.disponif.activities.ActivityActivity.EXTRA_AVAILABILITY_ID";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState == null) {
			// Add the fragment on initial activity setup
			mFragment = new ActivityFragment();
			mFragment.setArguments(getIntent().getExtras());
			getSupportFragmentManager().beginTransaction()
					.add(android.R.id.content, mFragment).commit();
		} else {
			// Or set the fragment from restored state info
			mFragment = (ActivityFragment) getSupportFragmentManager()
					.findFragmentById(android.R.id.content);
		}
		setTitle(getIntent().getExtras().getString(EXTRA_ACTIVITY_NAME));
	}
	
	
}
