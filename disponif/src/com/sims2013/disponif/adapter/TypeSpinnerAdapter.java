package com.sims2013.disponif.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.sims2013.disponif.R;
import com.sims2013.disponif.model.Type;

public class TypeSpinnerAdapter extends BaseAdapter implements SpinnerAdapter{

	private Activity mActivity;
	private ArrayList<Type> mItems;
	
	public TypeSpinnerAdapter(Activity activity, ArrayList<Type> items) {
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
		View spinView;
	    if( convertView == null ){
	        LayoutInflater inflater = mActivity.getLayoutInflater();
	        spinView = inflater.inflate(R.layout.item_spinner_type_layout, null);
	        spinView.findViewById(R.id.item_category_icon).setVisibility(View.GONE);
	    } else {
	         spinView = convertView;
	    }
	    TextView t1 = (TextView) spinView.findViewById(R.id.spinnerTextView);
	    t1.setText(String.valueOf(mItems.get(position).getName()));
	    return spinView;
	}

}
