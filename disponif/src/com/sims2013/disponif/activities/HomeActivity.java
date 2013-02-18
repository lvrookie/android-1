package com.sims2013.disponif.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.sims2013.disponif.R;
import com.sims2013.disponif.fragments.HomeFragment;


/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class HomeActivity extends FragmentActivity {

	private HomeFragment mFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_home);

		if (savedInstanceState == null) {
			// Add the fragment on initial activity setup
			mFragment = new HomeFragment();
			getSupportFragmentManager().beginTransaction().add(android.R.id.content, mFragment).commit();
		} else {
			// Or set the fragment from restored state info
			mFragment = (HomeFragment) getSupportFragmentManager().findFragmentById(android.R.id.content);
		}
	}

}
