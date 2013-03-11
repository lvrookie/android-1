package com.sims2013.disponif.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.sims2013.disponif.R;
import com.sims2013.disponif.model.Category;

public class CategorySpinnerAdapter extends ArrayAdapter<Category> implements
		SpinnerAdapter {

	public CategorySpinnerAdapter(Activity context, int textViewResourceId,
			List<Category> objects) {
		super(context, textViewResourceId, objects);
		mLayout = textViewResourceId;
		this.mActivity = context;
		this.mItems = (ArrayList<Category>) objects;
	}

	private static class ViewHolder {
		TextView name;
		ImageView icon;
	}

	protected int mLayout;
	private Activity mActivity;
	private ArrayList<Category> mItems;

	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public Category getItem(int position) {
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
			convertView = inflater.inflate(mLayout, parent, false);
			holder = new ViewHolder();
			holder.name = (TextView) convertView
					.findViewById(R.id.spinnerTextView);
			holder.icon = (ImageView) convertView
					.findViewById(R.id.item_category_icon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

//		new DownloadImageTask(holder.icon)
//				.execute("http://disponif.darkserver.fr/server/res/category/"
//						+ mItems.get(position).getId() + ".png");
		holder.icon.setImageBitmap(mItems.get(position).getIcon());
		holder.name.setText(mItems.get(position).getName());
		return convertView;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = mActivity.getLayoutInflater();
			convertView = inflater.inflate(mLayout, parent, false);
			holder = new ViewHolder();
			holder.name = (TextView) convertView
					.findViewById(R.id.spinnerTextView);
			holder.icon = (ImageView) convertView
					.findViewById(R.id.item_category_icon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.icon.setImageBitmap(mItems.get(position).getIcon());
		holder.name.setText(mItems.get(position).getName());
		
		return convertView;
	}
}
