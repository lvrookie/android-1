package com.sims2013.disponif.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.sims2013.disponif.DisponifApplication;
import com.sims2013.disponif.R;
import com.sims2013.disponif.fragments.ConnectionDialogFragment.RetryConnectDialogContract;
import com.sims2013.disponif.fragments.GenericFragment;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class GenericActivity extends FragmentActivity implements RetryConnectDialogContract{

	protected static final boolean DEBUG_MODE = DisponifApplication.DEBUG_MODE;
	public static final String TAG = "com.sims2013.disponif.activities.GenericActivity";

	protected Fragment mFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_generic);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		
		Log.d(TAG, "onRestart");
	}

	@Override
	public void onRetryClick() {
		((GenericFragment)mFragment).onRetryClick();
	}
}
