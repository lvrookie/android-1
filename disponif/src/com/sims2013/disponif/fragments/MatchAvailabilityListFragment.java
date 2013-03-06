package com.sims2013.disponif.fragments;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;

import com.sims2013.disponif.DisponifApplication;
import com.sims2013.disponif.R;
import com.sims2013.disponif.adapter.MatchAvailabilityAdapter;
import com.sims2013.disponif.model.Availability;
import com.tjerkw.slideexpandable.library.ActionSlideExpandableListView;

public class MatchAvailabilityListFragment extends GenericFragment implements 
																		OnLongClickListener{

	public static final String AVAILABILITY_ID = "availability_id";
	
	MatchAvailabilityAdapter mAdapter;
	ActionSlideExpandableListView mListView;
	ProgressDialog mProgressDialog;
	int mCurrentAvailabilityId;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		if (getArguments().containsKey(AVAILABILITY_ID)) {
			mCurrentAvailabilityId = getArguments().getInt(AVAILABILITY_ID);
		} else {
			mCurrentAvailabilityId = -1;
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		super.onCreateView(inflater, container, savedInstanceState);
		
		mView = inflater.inflate(R.layout.fragment_list_availability, container, false);
		initUI();
		return mView;
	}
	
	@Override
	protected void initUI() {
		super.initUI();
		
		mProgressDialog = new ProgressDialog(getActivity());
		
		mListView = (ActionSlideExpandableListView) mView.findViewById(R.id.list);
		
		refresh();
	}
	
	@Override
	public void onMatchAvailabilitiesReceive(
			ArrayList<Availability> availabilities, int startRow, int endRow) {
		mProgressDialog.dismiss();
		if (availabilities != null) {
			mAdapter = new MatchAvailabilityAdapter(this, R.layout.item_availability, availabilities);
			
			mListView.setAdapter(mAdapter);
		}
	}
	
	@Override
	protected void refresh() {
		mProgressDialog.setTitle(getString(R.string.availability_progress_loading_title));
		mProgressDialog.setMessage(getString(R.string.availability_progress_loading_message));
		mProgressDialog.show();
		mClient.getMatchAvailabilities(DisponifApplication.getAccessToken(), mCurrentAvailabilityId, 0, 10);
	}

	@Override
	public void onLogInTokenReceive(String token) {
		super.onLogInTokenReceive(token);
		refresh();
	}

	@Override
	public boolean onLongClick(View arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
