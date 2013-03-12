package com.sims2013.disponif.fragments;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.sims2013.disponif.DisponifApplication;
import com.sims2013.disponif.R;
import com.sims2013.disponif.Utils.DisponIFUtils;
import com.sims2013.disponif.client.Client;
import com.sims2013.disponif.model.Availability;
import com.sims2013.disponif.model.Category;

public abstract class GenericFragment extends Fragment implements
		Client.onReceiveListener {

	protected static final boolean DEBUG_MODE = DisponifApplication.DEBUG_MODE;
	public static final String TAG = "com.sims2013.disponif.fragments.GenericFragment";

	protected Client mClient;
	protected UiLifecycleHelper uiHelper;
	protected boolean mTokenIsValid = false;
	protected boolean mConnectedToFacebook = false;

	protected ConnectionDialogFragment mConnectionDialogFragment;
	ProgressDialog mProgressDialog;

	protected View mView;

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

	protected void initUI() {
		mProgressDialog = new ProgressDialog(getActivity());
		mProgressDialog.setCancelable(false);
	}

	@Override
	public void onLogInTokenReceive(String token) {
		mTokenIsValid = true;
		DisponifApplication.setAccessToken(token);
		if (mConnectionDialogFragment!= null && !mConnectionDialogFragment.isDetached()) {
			mConnectionDialogFragment.dismiss();
		}
		shouldShowProgressDialog(false);
		refresh();
	}

	@Override
	public void onPingReceive(String result) {
	}

	@Override
	public void onCategoriesReceive(ArrayList<Category> categories) {
		shouldShowProgressDialog(false);
	}

	@Override
	public void onAvailabilityAdded(int id) {
		shouldShowProgressDialog(false);
	}

	@Override
	public void onUserAvailabilitiesReceive(
			ArrayList<Availability> availbilities) {
		shouldShowProgressDialog(false);
	}

	// This method handle the case where the user is connected to facebook
	// Check connection to server
	public void onFacebookSessionOpened(Session session) {
		mConnectedToFacebook = true;
	}

	// This method handle the case where the user is disconnected from facebook.
	public void onFacebookSessionClosed() {
		mConnectedToFacebook = false;
	}

	public void onRetryClick() {
		mConnectionDialogFragment.displayTryingToReachServer();
		mClient.logIn(DisponifApplication.getAccessToken());
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

	protected abstract void refresh();

	@Override
	public void onNetworkError(String errorMessage) {
		shouldShowProgressDialog(false);
		if (mConnectionDialogFragment != null
				&& !mConnectionDialogFragment.isDetached()
				&& mConnectionDialogFragment.isShowing()) {
			// if the fragment is already displayed, just show the retry button
			mConnectionDialogFragment.displayServerUnreachable();
		} else {
			if (!isDetached()) {
				DisponIFUtils.makeToast(getActivity(),
						getString(R.string.connection_network_error));
			}
		}
	}

	@Override
	public void onTokenExpired() {
		shouldShowProgressDialog(false);
		mTokenIsValid = false;
		if (mConnectionDialogFragment != null
				&& mConnectionDialogFragment.isShowing()) {
			// if the fragment is already displayed, just show the retry button
			mConnectionDialogFragment.displayServerUnreachable();
		} else {
			// the fragment is not displayed. We have to ask the server for
			// another
			// token using the facebook session.
			// Note : facebook session loss is handled by onSessionStateChanged,
			// so no check needed here.

			// First, display the connection dialog fragment
			FragmentManager fm = getActivity().getSupportFragmentManager();
			mConnectionDialogFragment = (ConnectionDialogFragment) fm
					.findFragmentByTag(ConnectionDialogFragment.TAG);
			if (mConnectionDialogFragment == null) {
				Bundle b = new Bundle();
				b.putString(ConnectionDialogFragment.EXTRA_DIALOG_TITLE,
						getString(R.string.connection_session_lost));
				mConnectionDialogFragment = ConnectionDialogFragment
						.newInstance(b);
			}
			if (!getActivity().isFinishing()
					&& !mConnectionDialogFragment.isDetached()) {
				mConnectionDialogFragment
						.show(fm, ConnectionDialogFragment.TAG);
			} else {
				if (DEBUG_MODE) {
					Log.e(TAG,
							"activity isFinishing() impossible to show dialog ");
				}
			}

			// Display the message "Your session is lost"

			// Then call the ws to log in and get a new access token
			mClient.logIn(Session.getActiveSession().getAccessToken());
		}
	}

	@Override
	public void onUserAvailabilityRemoved() {
		shouldShowProgressDialog(false);
	}

	@Override
	public void onMatchAvailabilitiesReceive(
			ArrayList<Availability> availabilities, int startRow, int endRow) {
		shouldShowProgressDialog(false);

	}

	protected void shouldShowProgressDialog(boolean shouldShow) {
		if (shouldShow) {
			shouldShowProgressDialog(true,
					getString(R.string.availability_progress_loading_title),
					getString(R.string.availability_progress_loading_message));
		} else {
			mProgressDialog.dismiss();
		}
	}

	protected void shouldShowProgressDialog(boolean shouldShow, String title,
			String message) {
		if (shouldShow) {
			mProgressDialog.setTitle(title);
			mProgressDialog.setMessage(message);
			mProgressDialog.show();
		} else {
			mProgressDialog.dismiss();
		}
	}
	
	@Override
	public void onCommentAdded(Boolean state) {
	}

	@Override
	public void onActivityReceived(com.sims2013.disponif.model.Activity result) {
		shouldShowProgressDialog(false);
	}
}
