package com.sims2013.disponif.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.sims2013.disponif.DisponifApplication;
import com.sims2013.disponif.R;
import com.sims2013.disponif.activities.AvailabilityActivity;
import com.sims2013.disponif.adapter.AvailabilityAdapter;
import com.sims2013.disponif.model.Availability;
import com.tjerkw.slideexpandable.library.ActionSlideExpandableListView;

public class AvailabilityListFragment extends GenericFragment implements
		OnLongClickListener {

	private static final int REQUEST_CODE_ADD_AVAILABILITY = 42;

	AvailabilityAdapter mAdapter;
	ActionSlideExpandableListView mListView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().getWindow().getAttributes().windowAnimations = R.style.slideLeft;
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		super.onCreateView(inflater, container, savedInstanceState);

		mView = inflater.inflate(R.layout.fragment_list_availability,
				container, false);
		initUI();
		return mView;
	}

	@Override
	protected void initUI() {
		super.initUI();
		mListView = (ActionSlideExpandableListView) mView
				.findViewById(R.id.list);
		registerForContextMenu(mListView);
		refresh();
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		int menuItemIndex = item.getItemId();
		if (menuItemIndex == 0) {
			shouldShowProgressDialog(true,
					getString(R.string.availability_progress_remove_title),
					getString(R.string.availability_progress_remove_message));
			mClient.removeAvailability(DisponifApplication.getAccessToken(),
					(Availability) mAdapter.getItem(info.position));
		}
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		if (v.getId() == R.id.list) {
			menu.setHeaderTitle(getString(R.string.availability_context_menu_title));
			menu.add(Menu.NONE, 0, 0,
					getString(R.string.availability_context_menu_item_remove));
		}

	}

	@Override
	public void onUserAvailabilitiesReceive(
			ArrayList<Availability> availabilities) {
		shouldShowProgressDialog(false);
		if (availabilities != null) {
			// mAdapter = new MatchAvailabilityAdapter(this,
			// R.layout.item_match_availability, availabilities);
			mAdapter = new AvailabilityAdapter(this,
					R.layout.item_availability, availabilities);

			mListView.setAdapter(mAdapter);
		}
		mListView.setEmptyView(mView.findViewById(R.id.empty_list));
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
		return true;
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
		shouldShowProgressDialog(true);
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

	@Override
	public boolean onLongClick(View arg0) {
		return false;
	}
	
	

}
