package com.sims2013.disponif.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.sims2013.disponif.R;
import com.sims2013.disponif.client.DisponibilityFragment;

public class DisponibilityActivity extends FragmentActivity{
	private DisponibilityFragment mFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_home);

		if (savedInstanceState == null) {
			// Add the fragment on initial activity setup
			mFragment = new DisponibilityFragment();
			getSupportFragmentManager().beginTransaction().add(android.R.id.content, mFragment).commit();
		} else {
			// Or set the fragment from restored state info
			mFragment = (DisponibilityFragment) getSupportFragmentManager().findFragmentById(android.R.id.content);
		}
	}
}
