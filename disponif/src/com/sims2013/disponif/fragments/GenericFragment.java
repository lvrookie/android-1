package com.sims2013.disponif.fragments;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.sims2013.disponif.DisponifApplication;
import com.sims2013.disponif.client.Client;
import com.sims2013.disponif.model.Availability;
import com.sims2013.disponif.model.Category;

public class GenericFragment extends Fragment implements
		Client.onReceiveListener {

	protected static final boolean DEBUG_MODE = DisponifApplication.DEBUG_MODE;
	public static final String TAG = "com.sims2013.disponif.fragments.GenericFragment";

	protected Client mClient;
	protected UiLifecycleHelper uiHelper;

	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mClient = new Client(DisponifApplication.DISPONIF_SERVER);
		mClient.setListener(this);

		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

	@Override
	public void onStart() {
		super.onStart();

		initUI();
	}

	protected void initUI() {

	}

	@Override
	public void onLogInTokenReceive(String token) {
	}

	@Override
	public void onPingReceive(String result) {
	}

	@Override
	public void onCategoriesReceive(ArrayList<Category> categories) {
	}

	@Override
	public void onAvailabilityAdded(int id) {
	}

	@Override
	public void onUserAvailabilitiesReceive(
			ArrayList<Availability> availbilities) {
	}

	// This method handle the case where the user is connected to facebook
	// Check connection to server
	public void onFacebookSessionOpened(Session session) {

	}

	// This method handle the case where the user is disconnected from facebook.
	public void onFacebookSessionClosed() {

	}

	// This method is called when the user is logged on FB and on the server
	protected void onConnectedToServer() {

	}

	public void onRetryClick() {

	}

	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
		if (state.isOpened()) {
			if (DEBUG_MODE) {
				Log.i(TAG, "Facebook login success");
			}
			onFacebookSessionOpened(session);
		} else if (state.isClosed()) {
			if (DEBUG_MODE) {
				Log.i(TAG, "Facebook login fail");
			}
			onFacebookSessionClosed();
		}
	}
	
	protected void refresh() {
		
	}

	@Override
	public void onNetworkError(String errorMessage) {
		
	}

	@Override
	public void onTokenExpired() {
		
	}
}
