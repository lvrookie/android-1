package com.sims2013.disponif.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;
import com.sims2013.disponif.R;


public class HomeFragment extends Fragment {

	public static final String TAG = "com.sims2013.disponif.fragments.LoginFragment";

	private UiLifecycleHelper uiHelper;
	

	private TextView mWelcomeMessage; 
	private ProfilePictureView mProfilePictureView; 

	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);

		LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
		
		mWelcomeMessage = (TextView) view.findViewById(R.id.home_welcome_tv);
		mProfilePictureView = (ProfilePictureView) view.findViewById(R.id.selection_profile_pic);
		
		authButton.setFragment(this);

		return view;
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

	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		if (state.isOpened()) {
			Log.i(TAG, "Logged in...");
			mProfilePictureView.setVisibility(View.VISIBLE);
			makeMeRequest(Session.getActiveSession());
		} else if (state.isClosed()) {
			mProfilePictureView.setVisibility(View.GONE);
            mWelcomeMessage.setText(getString(R.string.home_disconnected));
			Log.i(TAG, "Logged out...");
		}
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
	                    // view that in turn displays the profile picture.
	                    mProfilePictureView.setProfileId(user.getId());
	                    // Set the Textview's text to the user's name.
	                    mWelcomeMessage.setText(getString(R.string.home_welcome, user.getName()));
	                }
	            }
	            if (response.getError() != null) {
	                // Handle errors, will do so later.
	            }
	        }
	    });
	    request.executeAsync();
	} 
}