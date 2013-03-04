package com.sims2013.disponif.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;

import com.sims2013.disponif.DisponifApplication;
import com.sims2013.disponif.R;
import com.sims2013.disponif.Utils.DisponIFUtils;
import com.sims2013.disponif.adapter.AvailabilityAdapter;
import com.sims2013.disponif.client.Client;
import com.sims2013.disponif.model.Availability;

public class AvailabilityListFragment extends GenericFragment implements OnItemClickListener {

	AvailabilityAdapter mAdapter;
	ListView mListView;
	Client mClient;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list_availability, container, false);
		
		mListView = (ListView)view.findViewById(R.id.listFragment);
		mListView.setOnItemClickListener(this);
		
		mClient = new Client("http://disponif.darkserver.fr/server/api.php");
		mClient.setListener(this);
		mClient.getUserAvailabilities(DisponifApplication.getAccessToken());
		
		return view;
	}
	
	@Override
	public void onUserAvailabilitiesReceive(
			ArrayList<Availability> availbilities) {
		if (availbilities != null) {
			mAdapter = new AvailabilityAdapter(getActivity(), availbilities);
			mListView.setAdapter(mAdapter);
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		DisponIFUtils.makeToast(getActivity(), mAdapter.getItem(position).toString());
	}

}
