package com.sims2013.disponif.fragments;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.sims2013.disponif.DisponifApplication;
import com.sims2013.disponif.R;
import com.sims2013.disponif.client.Client;
import com.sims2013.disponif.model.Availability;
import com.sims2013.disponif.model.Category;

public class GenericFragment extends Fragment implements
		Client.onReceiveListener {

	protected static final boolean DEBUG_MODE = DisponifApplication.DEBUG_MODE;
	public static final String TAG = "com.sims2013.disponif.fragments.GenericFragment";

	private LinearLayout mConnectingToServerView;
	private boolean mIsDisplayingConnectingToServer = false;
	private boolean mIsLoggingToServer = false;

	private UiLifecycleHelper uiHelper;

	private Client mClient;

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
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
		mClient = new Client("http://disponif.darkserver.fr/server/api.php");
		mClient.setListener(this);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (DEBUG_MODE) {
			Log.e(TAG, "onCreateView");
		}
		View v = super.onCreateView(inflater, container, savedInstanceState);

		mConnectingToServerView = (LinearLayout) View.inflate(getActivity(),
				R.layout.connect_server_view, (ViewGroup) v);
		
		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();

		Session session = Session.getActiveSession();
		if (session != null && session.getState().equals(SessionState.OPENED)) {
			if (!mIsLoggingToServer) {
				if (TextUtils.isEmpty(DisponifApplication.getAccessToken())) {
					mIsLoggingToServer = true;
					mClient.logIn(session.getAccessToken());
					if (!mIsDisplayingConnectingToServer) {
						mIsDisplayingConnectingToServer = true;
						displayConnectView();
						
					}
				} else {
					onConnectedToServer();
				}

			}
		} else {
			onFacebookSessionLost();
		}
	}

	private void displayConnectView() {
		LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp.gravity = Gravity.CENTER;
		mConnectingToServerView.setLayoutParams(lp);
		mConnectingToServerView.setGravity(Gravity.CENTER);
		((ViewGroup) getView())
				.addView(mConnectingToServerView, lp);
	}

	@Override
	public void onStart() {
		super.onStart();
		if (mIsDisplayingConnectingToServer) {
			((ViewGroup) getView()).removeView(mConnectingToServerView);
		}
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

	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
		if (state.isOpened()) {
			if (DEBUG_MODE) {
				Log.i(TAG, "Logged in...");
			}
			if (!mIsLoggingToServer
					&& TextUtils.isEmpty(DisponifApplication.getAccessToken())) {
				mClient.logIn(session.getAccessToken());
				if (!mIsDisplayingConnectingToServer) {
					mIsDisplayingConnectingToServer = true;
					displayConnectView();
				}
			}
		} else if (state.isClosed()) {
			if (DEBUG_MODE) {
				Log.i(TAG, "Logged out...");
			}
			onFacebookSessionLost();
		}
	}

	// This method handle the case where the user is disconnected from facebook.
	protected void onFacebookSessionLost() {

	}

	@Override
	public void onLogInTokenReceive(String token) {
		if (token == Client.ERROR_STRING) {
			displayServerUnreachable();
		} else {
			mIsDisplayingConnectingToServer = false;
			((ViewGroup) getView()).removeView(mConnectingToServerView);
			DisponifApplication.setAccessToken(token);
			onConnectedToServer();
		}
	}

	// This method is called when connection to server has been made
	protected void onConnectedToServer() {
		mIsLoggingToServer = false;
	}

	private void displayServerUnreachable() {
		((TextView) mConnectingToServerView
				.findViewById(R.id.connect_server_message))
				.setText(getString(R.string.connection_lost));
		((ProgressBar) mConnectingToServerView
				.findViewById(R.id.connect_server_progress_bar))
				.setVisibility(View.GONE);
		((Button) mConnectingToServerView
				.findViewById(R.id.connect_server_retry_bt))
				.setVisibility(View.VISIBLE);
		((Button) mConnectingToServerView
				.findViewById(R.id.connect_server_retry_bt))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						mClient.logIn(DisponifApplication.getAccessToken());
						displayTryingToReachServer();
					}
				});
	}

	protected void displayTryingToReachServer() {
		((TextView) mConnectingToServerView
				.findViewById(R.id.connect_server_message))
				.setText(getString(R.string.connecting_to_server));
		((ProgressBar) mConnectingToServerView
				.findViewById(R.id.connect_server_progress_bar))
				.setVisibility(View.VISIBLE);
		((Button) mConnectingToServerView
				.findViewById(R.id.connect_server_retry_bt))
				.setVisibility(View.GONE);
	}

	@Override
	public void onPingReceive(String result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAvailabilityAdded(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCategoriesReceive(ArrayList<Category> categories) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUserAvailabilitiesReceive(
			ArrayList<Availability> availbilities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNetworkError(String errorMessage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTokenExpired() {
		// TODO Auto-generated method stub
		
	}
}
