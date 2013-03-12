package com.sims2013.disponif.adapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sims2013.disponif.R;
import com.sims2013.disponif.Utils.BitmapManager;
import com.sims2013.disponif.Utils.DisponIFUtils;
import com.sims2013.disponif.activities.MatchAvailabilityListActivity;
import com.sims2013.disponif.fragments.MatchAvailabilityListFragment;
import com.sims2013.disponif.model.Availability;

public class AvailabilityAdapter extends ArrayAdapter<Availability> {
	
	private static class AvailabilityHolder{
		ImageView mCategoryIcon;
		TextView mCatSimple;
		TextView mTypeSimple;
		TextView mTimeSimple;
		TextView mDate;
		TextView mDescription;
		ImageView mLiveIcon;
		Button mMoreButton;
		Button mMatchAvailabilitiesButton;
	}

	public AvailabilityAdapter(Fragment context, int textViewResourceId,
			List<Availability> objects) {
		super(context.getActivity(), textViewResourceId, objects);
		mLayout = textViewResourceId;
		mAvailabilities = (ArrayList<Availability>) objects;
		mFragment = context;
	}

	protected Fragment mFragment;
	protected int mLayout;
	protected ArrayList<Availability> mAvailabilities = new ArrayList<Availability>();

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
	public View getView(final int position, View convertView, ViewGroup parent) {
		AvailabilityHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (mFragment.getActivity()).getLayoutInflater();
			convertView = inflater.inflate(mLayout, parent, false);
			holder = new AvailabilityHolder();
			holder.mCategoryIcon = (ImageView)convertView.findViewById(R.id.item_category_icon);
			holder.mDate = (TextView)convertView.findViewById(R.id.item_availability_date);
			holder.mDescription = (TextView)convertView.findViewById(R.id.item_availability_description);
			holder.mMoreButton = (Button)convertView.findViewById(R.id.expandable_toggle_button);
			holder.mCatSimple = (TextView)convertView.findViewById(R.id.item_availability_cat_simple);
			holder.mTypeSimple = (TextView)convertView.findViewById(R.id.item_availability_type_simple);
			holder.mTimeSimple = (TextView)convertView.findViewById(R.id.item_availability_time_simple);
			holder.mMatchAvailabilitiesButton = (Button)convertView.findViewById(R.id.item_match_availibilities_button);
			holder.mLiveIcon = (ImageView)convertView.findViewById(R.id.item_availability_simple_live);
			
			holder.mMatchAvailabilitiesButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {

					if (v.getId() == R.id.item_match_availibilities_button){
						Intent intent = new Intent(mFragment.getActivity(), MatchAvailabilityListActivity.class);
						intent.putExtra(MatchAvailabilityListFragment.EXTRA_AVAILABILITY_ID, mAvailabilities.get(position).getId());
						intent.putExtra(MatchAvailabilityListFragment.EXTRA_CATEGORY_NAME, mAvailabilities.get(position).getCategoryName());
						intent.putExtra(MatchAvailabilityListFragment.EXTRA_DESCRIPTION, mAvailabilities.get(position).getDescription());
						intent.putExtra(MatchAvailabilityListFragment.EXTRA_CATEGORY_ID, mAvailabilities.get(position).getCategoryId());
						intent.putExtra(MatchAvailabilityListFragment.EXTRA_END_TIME, mAvailabilities.get(position).getEndTime());
						intent.putExtra(MatchAvailabilityListFragment.EXTRA_START_TIME, mAvailabilities.get(position).getStartTime());
						intent.putExtra(MatchAvailabilityListFragment.EXTRA_TYPE_NAME, mAvailabilities.get(position).getTypeName());
						mFragment.getActivity().startActivity(intent);
					}
				}
			});
			
			holder.mMoreButton.setOnLongClickListener((OnLongClickListener) mFragment);
			
			convertView.setTag(holder);
		} else {
			holder = (AvailabilityHolder) convertView.getTag();
		}
		
		Availability av = mAvailabilities.get(position);
		
		holder.mDescription.setText(av.getDescription());
		holder.mDate.setText("Du "
				+ DisponIFUtils.datetimeToFrDate(getContext(),
						av.getStartTime())
		+ " à "
		+ DisponIFUtils.datetimeToFrTime(getContext(),
				av.getStartTime()) + " au "
				+ DisponIFUtils.datetimeToFrDate(getContext(),
				av.getEndTime())
		+ " à "
		+ DisponIFUtils.datetimeToFrTime(getContext(),
				av.getEndTime()));
		
		String catTxt = av.getCategoryName();
		if (!av.getTypeName().isEmpty()) {
			catTxt += " - ";
		}
		holder.mCatSimple.setText(catTxt);
		holder.mTypeSimple.setText(av.getTypeName());
		
		Date startDate = DisponIFUtils.stringToDate(av.getStartTime());
		Date today = new Date();
		int diffInDays = (int)( (startDate.getTime() - today.getTime()) 
                / (1000 * 60 * 60 * 24) );
		int diffInHours = Math.round(( (startDate.getTime() - today.getTime()) 
                / (1000 * 60 * 60) )) + 1; 
		
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(today);   
		int currHour = calendar.get(Calendar.HOUR_OF_DAY);
		
		if (diffInHours <= 0) {
			holder.mTimeSimple.setVisibility(View.GONE);
			holder.mLiveIcon.setVisibility(View.VISIBLE);
		} else if (diffInHours<(24-currHour)) {
			holder.mLiveIcon.setVisibility(View.GONE);
			holder.mTimeSimple.setVisibility(View.VISIBLE);
			holder.mTimeSimple.setText(mFragment.getString(R.string.availability_date_simple_today));
		} else if (diffInDays == 0){
			holder.mLiveIcon.setVisibility(View.GONE);
			holder.mTimeSimple.setVisibility(View.VISIBLE);
			holder.mTimeSimple.setText(mFragment.getString(R.string.availability_date_simple, 1)); 
		} else {
			holder.mLiveIcon.setVisibility(View.GONE);
			holder.mTimeSimple.setVisibility(View.VISIBLE);
			holder.mTimeSimple.setText(mFragment.getString(R.string.availability_date_simple, diffInDays));
		}
		
		BitmapManager.setBitmap(holder.mCategoryIcon, "http://disponif.darkserver.fr/server/res/category/"+ av.getCategoryId() +".png");
		
		return convertView;
	}
}
