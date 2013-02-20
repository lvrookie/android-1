package com.sims2013.disponif.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.sims2013.disponif.DisponifApplication;
import com.sims2013.disponif.R;
import com.sims2013.disponif.Utils.DisponIFUtils;
import com.sims2013.disponif.adapter.AvailabilityAdapter;
import com.sims2013.disponif.model.Availability;

public class AvailabilityListFragment extends ListFragment {
	private static final boolean DEBUG_MODE = DisponifApplication.DEBUG_MODE;

	AvailabilityAdapter mAdapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Give some text to display if there is no data. In a real
		// application this would come from a resource.
		setEmptyText(getString(R.string.availability_list_no_availabilities));

		// We have a menu item to show in action bar.
		setHasOptionsMenu(true);
		
		ArrayList<Availability> dummy_items = new ArrayList<Availability>();
		
		if (DEBUG_MODE) {
			dummy_items.add(new Availability(0, "user0", 0, 0,0,0,0,0/* privacy */, "status", 0, "option", "startTime", "endTime"));
			dummy_items.add(new Availability(1, "user1", 1, 1,0,0,0,1/* privacy */, "status", 0, "option", "startTime", "endTime"));
			dummy_items.add(new Availability(2, "user2", 2, 2,0,0,0,1/* privacy */, "status", 0, "option", "startTime", "endTime"));
			dummy_items.add(new Availability(3, "user3", 3, 3,0,0,0,0/* privacy */, "status", 0, "option", "startTime", "endTime"));
			dummy_items.add(new Availability(4, "user4", 4, 4,0,0,0,1/* privacy */, "status", 0, "option", "startTime", "endTime"));
		}
		
		mAdapter = new AvailabilityAdapter(getActivity(), dummy_items);

		setListAdapter(mAdapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		DisponIFUtils.makeToast(getActivity(), mAdapter.getItem(position)
				.toString(), Toast.LENGTH_SHORT);
	}
}
