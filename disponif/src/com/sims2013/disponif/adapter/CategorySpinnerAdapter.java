package com.sims2013.disponif.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.sims2013.disponif.R;
import com.sims2013.disponif.Utils.DisponIFUtils.DownloadImageTask;
import com.sims2013.disponif.model.Category;

public class CategorySpinnerAdapter extends BaseAdapter implements
		SpinnerAdapter {
	
	private static class ViewHolder {
		TextView name;
		ImageView icon;
	}

	private Activity mActivity;
	private ArrayList<Category> mItems;

	public CategorySpinnerAdapter(Activity activity, ArrayList<Category> items) {
		this.mActivity = activity;
		this.mItems = items;
	}

	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return mItems.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = mActivity.getLayoutInflater();
			convertView = inflater.inflate(R.layout.spin_layout, parent, false);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.spinnerTextView);
			holder.icon = (ImageView) convertView
					.findViewById(R.id.item_category_icon);
			new DownloadImageTask(holder.icon)
			.execute("http://disponif.darkserver.fr/server/res/category/"
					+ mItems.get(position).getId() + ".png");
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.name.setText(mItems.get(position).getName());
		
		return convertView;
	}

}
