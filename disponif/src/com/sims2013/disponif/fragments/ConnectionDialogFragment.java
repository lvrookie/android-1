package com.sims2013.disponif.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sims2013.disponif.R;

public class ConnectionDialogFragment extends DialogFragment {

	public static final String TAG = "com.sims2013.disponif.fragments.ConnectionDialogFragment";
	static final String EXTRA_DIALOG_TITLE = "com.sims2013.disponif.fragments.ConnectionDialogFragment.EXTRA_DIALOG_TITLE";
	
	TextView mMessage;
	ProgressBar mProgressBar;
	Button mButton;
	
	private String mTitle;
	
	public interface RetryConnectDialogContract {
		void onRetryClick();
	}
	
	public static ConnectionDialogFragment newInstance(Bundle b) {
		ConnectionDialogFragment f = new ConnectionDialogFragment();
		f.setArguments(b);
		return f;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		super.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Dialog);
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
		
		if (getArguments()!= null) {
			mTitle = getArguments().getString(EXTRA_DIALOG_TITLE,"NoTitle");
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Log.d(TAG, "onCreateView");
		
		View v = inflater.inflate(R.layout.connect_server_view, container, false);
		getDialog().setCanceledOnTouchOutside(false);
		
		mMessage = (TextView) v.findViewById(R.id.connect_server_message);
		mButton = (Button) v.findViewById(R.id.connect_server_retry_bt);
		mProgressBar = (ProgressBar) v.findViewById(R.id.connect_server_progress_bar);
		
		getDialog().setTitle(mTitle);
		
		if (mTitle.equals(getString(R.string.connection_session_lost))) {
			displaySessionLost();
		} else  if (mTitle.equals(getString(R.string.connection_logging))) {
			displayTryingToReachServer();
		}
		return v;
	}
	
	protected void displayServerUnreachable() {
		mMessage.setText(getString(R.string.connection_lost));
		mProgressBar.findViewById(R.id.connect_server_progress_bar).setVisibility(View.GONE);
		mButton.setVisibility(View.VISIBLE);
		mButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						((RetryConnectDialogContract) getActivity()).onRetryClick();
					}
				});
	}

	protected void displayTryingToReachServer() {
		mMessage.setText(getString(R.string.connecting_to_server));
		mProgressBar.setVisibility(View.VISIBLE);
		mButton.setVisibility(View.GONE);
	}

	public boolean isShowing() {
		return getDialog().isShowing();
	}

	public void displaySessionLost() {
		mMessage.setText(getString(R.string.connection_token_expired));
		mProgressBar.setVisibility(View.VISIBLE);
		mButton.setVisibility(View.GONE);
	}

}
