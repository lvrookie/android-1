package com.sims2013.disponif.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.widget.ProfilePictureView;
import com.sims2013.disponif.R;
import com.sims2013.disponif.Utils.DisponIFUtils;
import com.sims2013.disponif.model.Availability;

public class AvailabilityAdapter extends ArrayAdapter<Availability> {
	
	static class Holder{
		ProfilePictureView mProfilePicture;
		TextView mStartTime;
		TextView mEndTime;
		TextView mCategoryAndType;
	}

	public AvailabilityAdapter(Context context, int textViewResourceId,
			List<Availability> objects) {
		super(context, textViewResourceId, objects);
		mLayout = textViewResourceId;
		mAvailabilities = (ArrayList<Availability>) objects;
		mContext = context;
	}

	private Context mContext;
	private int mLayout;
	private ArrayList<Availability> mAvailabilities = new ArrayList<Availability>();

//	public AvailabilityAdapter(Context context, ArrayList<Availability> items) {
//		super(context, items);
//		mContext = context;
//		mAvailabilities = items;
//	}

	@Override
	public int getCount() {
		return mAvailabilities.size();
	}

	@Override
	public Availability getItem(int i) {
		return mAvailabilities.get(i);
	}

	@Override
	public long getItemId(int index) {
		return mAvailabilities.get(index).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		AvailabilityCellView view;
//		if (convertView == null) {
//			view = new AvailabilityCellView(mContext);
//		} else {
//			view = (AvailabilityCellView) convertView;
//		}
//		view.setObject(mAvailabilities.get(position));
//		return view;
		Holder holder;
		if (convertView == null) {
			LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
			convertView = inflater.inflate(mLayout, parent, false);
			holder = new Holder();
			holder.mProfilePicture = (ProfilePictureView)convertView.findViewById(R.id.item_availability_user_profile_picture);
			holder.mStartTime = (TextView)convertView.findViewById(R.id.item_availability_startDate);
			holder.mEndTime = (TextView)convertView.findViewById(R.id.item_availability_endDate);
			holder.mCategoryAndType = (TextView)convertView.findViewById(R.id.item_availability_category_and_type);
			
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		
		Availability av = mAvailabilities.get(position);
		
		holder.mCategoryAndType.setText(av.getDescription());
		holder.mStartTime.setText("du "
				+ DisponIFUtils.datetimeToFrDate(getContext(),
						av.getStartTime())
		+ " - "
		+ DisponIFUtils.datetimeToFrTime(getContext(),
				av.getStartTime()));
		holder.mEndTime.setText("au "
				+ DisponIFUtils.datetimeToFrDate(getContext(),
				av.getEndTime())
		+ " - "
		+ DisponIFUtils.datetimeToFrTime(getContext(),
				av.getEndTime()));
		return convertView;
		
		
	}
}
