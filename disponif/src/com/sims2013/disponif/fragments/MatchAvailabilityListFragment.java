package com.sims2013.disponif.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
	int mCurrentAvailabilityId;

	private boolean mRefreshingList;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		if (getArguments().containsKey(AVAILABILITY_ID)) {
			mCurrentAvailabilityId = getArguments().getInt(AVAILABILITY_ID);
		} else {
			mCurrentAvailabilityId = -1;
		}
		setHasOptionsMenu(true);
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
		mListView = (ActionSlideExpandableListView) mView.findViewById(R.id.list);
		refresh();
	}
	
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		
		MenuItem refreshMenuItem = menu.findItem(R.id.menu_item_refresh);

		if (getRefreshMode()) {
			refreshMenuItem
					.setActionView(R.layout.action_bar_indeterminate_progress); 
		} else {
			refreshMenuItem.setActionView(null);
			// Default
		}
	}
	
	private boolean getRefreshMode() {
		return mRefreshingList;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_item_refresh) {
			refresh();
		}
		return true;
	}
	

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.activity_match_availability_list_menu, menu);
	}
	
	@Override
	public void onMatchAvailabilitiesReceive(
			ArrayList<Availability> availabilities, int startRow, int endRow) {
		mRefreshingList = false;
		shouldShowProgressDialog(false);
		if (availabilities != null) {
			mAdapter = new MatchAvailabilityAdapter(this, R.layout.item_match_availability, availabilities);
			mListView.setAdapter(mAdapter);
		}
		mListView.setEmptyView(mView.findViewById(R.id.empty_list));
		getActivity().invalidateOptionsMenu();
	}
	
	@Override
	protected void refresh() {
		mRefreshingList = true;
		getActivity().invalidateOptionsMenu();
		shouldShowProgressDialog(true);
		mClient.getMatchAvailabilities(DisponifApplication.getAccessToken(), mCurrentAvailabilityId, 0, 10);
	}

	@Override
	public void onLogInTokenReceive(String token) {
		super.onLogInTokenReceive(token);
		refresh();
	}

	@Override
	public boolean onLongClick(View arg0) {
		return false;
	}

}
