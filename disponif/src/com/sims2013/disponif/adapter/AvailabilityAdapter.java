package com.sims2013.disponif.adapter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
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
	
	static class Holder{
		ImageView mCategoryIcon;
		TextView mStartTime;
		TextView mEndTime;
		TextView mCategoryAndType;
		Button mMoreButton;
	}

	public AvailabilityAdapter(Fragment context, int textViewResourceId,
			List<Availability> objects) {
		super(context.getActivity(), textViewResourceId, objects);
		mLayout = textViewResourceId;
		mAvailabilities = (ArrayList<Availability>) objects;
		mFragment = context;
	}

	private Fragment mFragment;
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
		Holder holder;
		if (convertView == null) {
			LayoutInflater inflater = (mFragment.getActivity()).getLayoutInflater();
			convertView = inflater.inflate(mLayout, parent, false);
			holder = new Holder();
			holder.mCategoryIcon = (ImageView)convertView.findViewById(R.id.item_category_icon);
			holder.mStartTime = (TextView)convertView.findViewById(R.id.item_availability_startDate);
			holder.mEndTime = (TextView)convertView.findViewById(R.id.item_availability_endDate);
			holder.mCategoryAndType = (TextView)convertView.findViewById(R.id.item_availability_category_and_type);
			holder.mMoreButton = (Button)convertView.findViewById(R.id.expandable_toggle_button);
			
			holder.mMoreButton.setOnLongClickListener((OnLongClickListener) mFragment);
			
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
		
		// show The Image
		new DownloadImageTask(holder.mCategoryIcon)
		            .execute("http://disponif.darkserver.fr/server/res/category/"+ av.getCategoryId() +".png");
		
		return convertView;
	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	    ImageView bmImage;

	    public DownloadImageTask(ImageView bmImage) {
	        this.bmImage = bmImage;
	    }

	    protected Bitmap doInBackground(String... urls) {
	        String urldisplay = urls[0];
	        Bitmap mIcon11 = null;
	        try {
	            InputStream in = new java.net.URL(urldisplay).openStream();
	            mIcon11 = BitmapFactory.decodeStream(in);
	        } catch (Exception e) {
	            Log.e("Error", e.getMessage());
	            e.printStackTrace();
	        }
	        return mIcon11;
	    }

	    protected void onPostExecute(Bitmap result) {
	        bmImage.setImageBitmap(result);
	    }
	}
}
