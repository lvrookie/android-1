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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.facebook.android.Util;
import com.sims2013.disponif.DisponifApplication;
import com.sims2013.disponif.R;
import com.sims2013.disponif.Utils.DisponIFUtils;
import com.sims2013.disponif.activities.AvailabilityActivity;
import com.sims2013.disponif.adapter.AvailabilityAdapter;
import com.sims2013.disponif.client.Client;
import com.sims2013.disponif.model.Availability;

public class AvailabilityListFragment extends GenericFragment implements 
																OnItemClickListener{

	private static final int REQUEST_CODE_ADD_AVAILABILITY = 42;
	
	AvailabilityAdapter mAdapter;
	ListView mListView;
	Client mClient;
	ProgressDialog mProgressDialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list_availability, container, false);
		
		mProgressDialog = new ProgressDialog(getActivity());
		
		mListView = (ListView)view.findViewById(R.id.listFragment);
		mListView.setOnItemClickListener(this);
		registerForContextMenu(mListView);
		

		mProgressDialog.setTitle("Chargement");
		mProgressDialog.setMessage("Récupération des disponibilités ...");
		mProgressDialog.show();
		
		
		mClient = new Client("http://disponif.darkserver.fr/server/api.php");
		mClient.setListener(this);
		mClient.getUserAvailabilities(DisponifApplication.getAccessToken());
		
		return view;
	}
	
	
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		int menuItemIndex = item.getItemId();
		if (menuItemIndex == 0) {
			mProgressDialog.setTitle("Suppression");
			mProgressDialog.setMessage("Suppression de la disponibilité ...");
			mProgressDialog.show();
			mClient.removeAvailability(DisponifApplication.getAccessToken(), (Availability)mAdapter.getItem(info.position));
		}
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		
		if (v.getId() == R.id.listFragment) {
			menu.setHeaderTitle("Menu");
			menu.add(Menu.NONE, 0, 0, "Supprimer");
		}
		
	}

	@Override
	public void onUserAvailabilitiesReceive(
			ArrayList<Availability> availabilities) {
		if (availabilities != null) {
			mAdapter = new AvailabilityAdapter(getActivity(), availabilities);
			mListView.setAdapter(mAdapter);
			mProgressDialog.dismiss();
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		DisponIFUtils.makeToast(getActivity(), mAdapter.getItem(position).toString());
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
		super.refresh();
		
		mClient.getUserAvailabilities(DisponifApplication.getAccessToken());
	}

	@Override
	public void onUserAvailabilityRemoved() {
		mClient.getUserAvailabilities(DisponifApplication.getAccessToken());
		
	}

	

}
