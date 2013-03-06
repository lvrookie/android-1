package com.sims2013.disponif.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.sims2013.disponif.DisponifApplication;
import com.sims2013.disponif.R;
import com.sims2013.disponif.activities.AvailabilityActivity;
import com.sims2013.disponif.adapter.AvailabilityAdapter;
import com.sims2013.disponif.model.Availability;
import com.tjerkw.slideexpandable.library.ActionSlideExpandableListView;

public class AvailabilityListFragment extends GenericFragment{

	private static final int REQUEST_CODE_ADD_AVAILABILITY = 42;
	
	AvailabilityAdapter mAdapter;
	ActionSlideExpandableListView mListView;
	ProgressDialog mProgressDialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
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
		
		mProgressDialog = new ProgressDialog(getActivity());
		
		mListView = (ActionSlideExpandableListView) mView.findViewById(R.id.list);
		registerForContextMenu(mListView);
		
		mProgressDialog.setTitle(getString(R.string.availability_progress_loading_title));
		mProgressDialog.setMessage(getString(R.string.availability_progress_loading_message));
		mProgressDialog.show();
		
		mClient.getUserAvailabilities(DisponifApplication.getAccessToken());
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		int menuItemIndex = item.getItemId();
		if (menuItemIndex == 0) {
			mProgressDialog.setTitle(getString(R.string.availability_progress_remove_title));
			mProgressDialog.setMessage(getString(R.string.availability_progress_remove_message));
			mProgressDialog.show();
//			mClient.removeAvailability(DisponifApplication.getAccessToken(), (Availability)mAdapter.getItem(info.position));
		}
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		
		if (v.getId() == R.id.list) {
			menu.setHeaderTitle(getString(R.string.availability_context_menu_title));
			menu.add(Menu.NONE, 0, 0, getString(R.string.availability_context_menu_item_remove));
		}
		
	}

	@Override
	public void onUserAvailabilitiesReceive(
			ArrayList<Availability> availabilities) {
		mProgressDialog.dismiss();
		if (availabilities != null) {
//			mAdapter = new AvailabilityAdapter(getActivity(), availabilities);

			final int SIZE = 20;
			String[] values = new String[SIZE];
			for(int i=0;i<SIZE;i++) {
				values[i] = "Item "+i;
			}
			mAdapter = new AvailabilityAdapter(getActivity(), R.layout.expandable_list_item, availabilities);
//			mAdapter = new AvailabilityAdapter(getActivity(), R.layout.item_availability, R.id.item_availability_category_and_type, values);
			
			mListView.setAdapter(mAdapter);
//			mListView.setAdapter(buildDummyData());
			mProgressDialog.dismiss();
		}
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);

		inflater.inflate(R.menu.activity_list_menu, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.addAvailabilityMenu) {
			Intent intent = new Intent(getActivity(),
					AvailabilityActivity.class);
			startActivityForResult(intent, REQUEST_CODE_ADD_AVAILABILITY);
		}
		return false;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == REQUEST_CODE_ADD_AVAILABILITY) {
			if (resultCode == Activity.RESULT_OK) {
				refresh();
			}
		}
	}

	@Override
	protected void refresh() {
		mProgressDialog.setTitle(getString(R.string.availability_progress_loading_title));
		mProgressDialog.setMessage(getString(R.string.availability_progress_loading_message));
		mProgressDialog.show();
		mClient.getUserAvailabilities(DisponifApplication.getAccessToken());
	}
	
	@Override
	public void onLogInTokenReceive(String token) {
		super.onLogInTokenReceive(token);
		refresh();
	}

	@Override
	public void onUserAvailabilityRemoved() {
		refresh();
	}
}
