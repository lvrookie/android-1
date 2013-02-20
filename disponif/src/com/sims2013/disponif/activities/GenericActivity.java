package com.sims2013.disponif.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.sims2013.disponif.R;


/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class GenericActivity extends FragmentActivity {

	protected Fragment mFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_generic);
	}
}
