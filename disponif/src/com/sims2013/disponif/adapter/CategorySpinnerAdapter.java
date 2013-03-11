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
import com.sims2013.disponif.Utils.BitmapManager;
import com.sims2013.disponif.model.Category;

public class CategorySpinnerAdapter extends BaseAdapter implements
		SpinnerAdapter {


	public CategorySpinnerAdapter(Activity mActivity, ArrayList<Category> mItems) {
		super();
		this.mActivity = mActivity;
		this.mItems = mItems;
	}

	private static class ViewHolder {
		TextView name;
		ImageView icon;
	}

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
			convertView = inflater.inflate(R.layout.item_spinner_type_layout, parent, false);
			holder = new ViewHolder();
			holder.name = (TextView) convertView
					.findViewById(R.id.spinnerTextView);
			holder.icon = (ImageView) convertView
					.findViewById(R.id.item_category_icon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		BitmapManager.setBitmap(holder.icon, "http://disponif.darkserver.fr/server/res/category/"+ mItems.get(position).getId() +".png");
		holder.name.setText(mItems.get(position).getName());
		return convertView;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = mActivity.getLayoutInflater();
			convertView = inflater.inflate(R.layout.item_spinner_type_layout, parent, false);
			holder = new ViewHolder();
			holder.name = (TextView) convertView
					.findViewById(R.id.spinnerTextView);
			holder.icon = (ImageView) convertView
					.findViewById(R.id.item_category_icon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		BitmapManager.setBitmap(holder.icon, "http://disponif.darkserver.fr/server/res/category/"+ mItems.get(position).getId() +".png");
		holder.name.setText(mItems.get(position).getName());

		return convertView;
	}
}
