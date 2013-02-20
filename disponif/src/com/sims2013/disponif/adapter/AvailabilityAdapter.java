package com.sims2013.disponif.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sims2013.disponif.model.Availability;
import com.sims2013.disponif.widgets.AvailabilityCellView;

public class AvailabilityAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<Availability> mAvailabilities = new ArrayList<Availability>();

	public AvailabilityAdapter(Context context, ArrayList<Availability> items) {
		mContext = context;
		mAvailabilities = items;
	}

	@Override
	public int getCount() {
		return mAvailabilities.size();
	}

	@Override
	public Object getItem(int i) {
		return mAvailabilities.get(i);
	}

	@Override
	public long getItemId(int index) {
		return mAvailabilities.get(index).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		AvailabilityCellView view;
		if (convertView == null) {
			view = new AvailabilityCellView(mContext);
		} else {
			view = (AvailabilityCellView) convertView;
		}
		view.setObject(mAvailabilities.get(position));
		return view;

	}

}
