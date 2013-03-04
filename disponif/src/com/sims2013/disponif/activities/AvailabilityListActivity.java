package com.sims2013.disponif.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_list_menu, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getItemId() == R.id.addAvailabilityMenu) {
			Intent intent = new Intent(this,
					AvailabilityActivity.class);
			startActivity(intent);
		}
		return false;
	}
	
	
}
