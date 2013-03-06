package com.sims2013.disponif.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sims2013.disponif.R;
import com.sims2013.disponif.Utils.DisponIFUtils;
import com.sims2013.disponif.model.Availability;

public class AvailabilityAdapter extends ArrayAdapter<Availability> {
	
	private static class AvailabilityHolder{
		ImageView mCategoryIcon;
		TextView mTypeSimple;
		TextView mTimeSimple;
		TextView mStartTime;
		TextView mEndTime;
		TextView mDescription;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		AvailabilityHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (mFragment.getActivity()).getLayoutInflater();
			convertView = inflater.inflate(mLayout, parent, false);
			holder = new AvailabilityHolder();
			holder.mCategoryIcon = (ImageView)convertView.findViewById(R.id.item_category_icon);
			holder.mStartTime = (TextView)convertView.findViewById(R.id.item_availability_startDate);
			holder.mEndTime = (TextView)convertView.findViewById(R.id.item_availability_endDate);
			holder.mDescription = (TextView)convertView.findViewById(R.id.item_availability_description);
			holder.mMoreButton = (Button)convertView.findViewById(R.id.expandable_toggle_button);
			holder.mTypeSimple = (TextView)convertView.findViewById(R.id.item_availability_type_simple);
			holder.mTimeSimple = (TextView)convertView.findViewById(R.id.item_availability_time_simple);
			holder.mMatchAvailabilitiesButton = (Button)convertView.findViewById(R.id.item_match_availibilities_button);
			
			holder.mMoreButton.setOnLongClickListener((OnLongClickListener) mFragment);
			
			convertView.setTag(holder);
		} else {
			holder = (AvailabilityHolder) convertView.getTag();
		}
		
		Availability av = mAvailabilities.get(position);
		
		holder.mDescription.setText(av.getDescription());
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
		
		holder.mTypeSimple.setText(av.getTypeName());
		
		Date startDate = DisponIFUtils.stringToDate(av.getStartTime());
		Date today = new Date();
		
		int diffInDays = (int)( (startDate.getTime() - today.getTime()) 
                / (1000 * 60 * 60 * 24) );
                
		if (diffInDays == 0) {
			holder.mTimeSimple.setText("Aujourd'hui");
		} else {
			holder.mTimeSimple.setText("Dans " + diffInDays + " jours");
		}
		
		// show The Image
		new DisponIFUtils.DownloadImageTask(holder.mCategoryIcon)
		            .execute("http://disponif.darkserver.fr/server/res/category/"+ av.getCategoryId() +"_48x48.png");
		
		return convertView;
	}
	
	

	
}
