package com.sims2013.disponif.adapter;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.sims2013.disponif.model.Availability;

public class MatchAvailabilityAdapter extends ArrayAdapter<Availability> {

	public MatchAvailabilityAdapter(Context context, int textViewResourceId,
			List<Availability> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}

}
