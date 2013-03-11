package com.sims2013.disponif.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.sims2013.disponif.R;
import com.sims2013.disponif.fragments.MatchAvailabilityListFragment;

public class MatchAvailabilityListActivity extends GenericActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null) {
			// Add the fragment on initial activity setup
			mFragment = new MatchAvailabilityListFragment();
			
			Intent intent = getIntent();
			if (intent.hasExtra(MatchAvailabilityListFragment.AVAILABILITY_ID)) {
				int avId = intent.getExtras().getInt(MatchAvailabilityListFragment.AVAILABILITY_ID);
				Bundle arg = new Bundle();
				arg.putInt(MatchAvailabilityListFragment.AVAILABILITY_ID, avId);
				mFragment.setArguments(arg);
			}
			getSupportFragmentManager().beginTransaction().add(android.R.id.content, mFragment).commit();
		} else {
			// Or set the fragment from restored state info
			mFragment = (MatchAvailabilityListFragment) getSupportFragmentManager().findFragmentById(android.R.id.content);
		}
		setTitle(getResources().getString(R.string.availability_match_list_title));
	}
}
