package com.sims2013.disponif.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;
import com.sims2013.disponif.R;
import com.sims2013.disponif.activities.AvailabilityListActivity;

public class HomeFragment extends GenericFragment implements OnClickListener {

	public static final String TAG = "com.sims2013.disponif.fragments.LoginFragment";

	private TextView mWelcomeMessage;
	private ProfilePictureView mProfilePictureView;
	private Button mDisponibilityButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		mView = inflater.inflate(R.layout.fragment_home, container, false);
		initUI();
		return mView;
	}

	private void makeMeRequest(final Session session) {
		// Make an API call to get user data and define a
		// new callback to handle the response.
		Request request = Request.newMeRequest(session,
				new Request.GraphUserCallback() {
					@Override
					public void onCompleted(GraphUser user, Response response) {
						// If the response is successful
						if (session == Session.getActiveSession()) {
							if (user != null) {
								// Set the id for the ProfilePictureView
								// view that in turn displays the profile
								// picture.
								mProfilePictureView.setProfileId(user.getId());
								// Set the Textview's text to the user's name.
								mWelcomeMessage.setText(getString(
										R.string.home_welcome, user.getName()));
							}
						}
						if (response.getError() != null) {
							// Handle errors, will do so later.
						}
					}
				});
		request.executeAsync();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == mDisponibilityButton.getId()) {

			Intent intent = new Intent(getActivity(),
					AvailabilityListActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public void onLogInTokenReceive(String token) {
		super.onLogInTokenReceive(token);
		refresh();
	}


	@Override
	protected void initUI() {
		super.initUI();
		
		LoginButton authButton = (LoginButton) mView
				.findViewById(R.id.authButton);

		mWelcomeMessage = (TextView) mView.findViewById(R.id.home_welcome_tv);
		mProfilePictureView = (ProfilePictureView) mView
				.findViewById(R.id.selection_profile_pic);
		mDisponibilityButton = (Button) mView
				.findViewById(R.id.home_dispo_button);
		mDisponibilityButton.setOnClickListener(this);
		mDisponibilityButton.setVisibility(View.GONE);

		authButton.setFragment(this);
		
		checkFacebookSession();
		
		refresh();
	}

	private void checkFacebookSession() {
		Session session = Session.getActiveSession();

		if (session != null && session.isOpened()) {
			mConnectedToFacebook = true;
		} else {
			mConnectedToFacebook = false;
			mTokenIsValid = false;
		}
	}

	

	@Override
	public void onFacebookSessionOpened(Session session) {
		super.onFacebookSessionOpened(session);

		FragmentManager fm = getActivity().getSupportFragmentManager();
		mDialogFragment = (ConnectionDialogFragment) fm
				.findFragmentByTag(ConnectionDialogFragment.TAG);
		if (mDialogFragment == null) {
			Bundle b = new Bundle();
			b.putString(ConnectionDialogFragment.EXTRA_DIALOG_TITLE, getString(R.string.connection_logging));
			mDialogFragment = ConnectionDialogFragment.newInstance(b);
		}
		if (!getActivity().isFinishing() && !mDialogFragment.isDetached()) {
			mDialogFragment.show(fm, ConnectionDialogFragment.TAG);
		} else {
			if (DEBUG_MODE) {
				Log.e(TAG, "activity isFinishing() impossible to show dialog ");
			}
		}
		mTokenIsValid = false;
		mClient.logIn(session.getAccessToken());
	}

	
	private void connectedToServer() {
		mWelcomeMessage.setVisibility(View.VISIBLE);
		mProfilePictureView.setVisibility(View.VISIBLE);
		mDisponibilityButton.setVisibility(View.VISIBLE);
		makeMeRequest(Session.getActiveSession());
	}
	
	@Override
	public void onFacebookSessionClosed() {
		super.onFacebookSessionClosed();
		mProfilePictureView.setVisibility(View.GONE);
		mWelcomeMessage.setText(getString(R.string.home_disconnected));
		mWelcomeMessage.setVisibility(View.VISIBLE);
		if (DEBUG_MODE) {
			Log.i(TAG, "Logged out...");
		}
		mDisponibilityButton.setVisibility(View.INVISIBLE);
	}

	@Override
	protected void refresh() {
		if (mConnectedToFacebook && mTokenIsValid) {
			connectedToServer();
		} else if (!mConnectedToFacebook) {
			onFacebookSessionClosed();
		} else if (mConnectedToFacebook && !mTokenIsValid) {
			mClient.logIn(Session.getActiveSession().getAccessToken());
		}
	}
}
