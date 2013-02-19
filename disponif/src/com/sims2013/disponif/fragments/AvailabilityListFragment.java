package com.sims2013.disponif.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.sims2013.disponif.DisponifApplication;
import com.sims2013.disponif.R;
import com.sims2013.disponif.Utils.DisponIFUtils;
import com.sims2013.disponif.model.Category;

public class AvailabilityListFragment extends ListFragment {
	private static final boolean DEBUG_MODE = DisponifApplication.DEBUG_MODE;

	ArrayAdapter<Category> mAdapter;
	public static List<Category> ITEMS = new ArrayList<Category>();

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Give some text to display if there is no data. In a real
		// application this would come from a resource.
		setEmptyText(getString(R.string.availability_list_no_availabilities));

		// We have a menu item to show in action bar.
		setHasOptionsMenu(true);

		// Create an empty adapter we will use to display the loaded data.
		mAdapter = new ArrayAdapter<Category>(getActivity(),
				android.R.layout.simple_list_item_activated_1,
				android.R.id.text1, ITEMS);

		setListAdapter(mAdapter);
		
		if (DEBUG_MODE && mAdapter.isEmpty()) {
			List<Category> dummy_items = new ArrayList<Category>();
			dummy_items.add(new Category(0, "Dispo 1", 1, null));
			dummy_items.add(new Category(1, "Dispo 2", 2, null));
			dummy_items.add(new Category(2, "Dispo 3", 3, null));
			dummy_items.add(new Category(3, "Dispo 4", 4, null));
			dummy_items.add(new Category(4, "Dispo 5", 5, null));
			mAdapter.addAll(dummy_items);
		}

		// Start out with a progress indicator.
		// setListShown(false);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		DisponIFUtils.makeToast(getActivity(), mAdapter.getItem(position)
				.toString(), Toast.LENGTH_SHORT);
	}
}
