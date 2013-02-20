package com.sims2013.disponif.activities;

import android.os.Bundle;

import com.sims2013.disponif.R;
import com.sims2013.disponif.fragments.AvailabilityListFragment;

public class AvailabilityListActivity extends GenericActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null) {
			// Add the fragment on initial activity setup
			mFragment = new AvailabilityListFragment();
			getSupportFragmentManager().beginTransaction().add(android.R.id.content, mFragment).commit();
		} else {
			// Or set the fragment from restored state info
			mFragment = (AvailabilityListFragment) getSupportFragmentManager().findFragmentById(android.R.id.content);
		}
		setTitle(getResources().getString(R.string.availability_list_title));
	}
}
