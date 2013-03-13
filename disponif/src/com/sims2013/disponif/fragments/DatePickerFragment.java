package com.sims2013.disponif.fragments;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {

	public static final String TAG = "com.sims2013.disponif.fragments.DatePickerFragment";
	public static final String EXTRA_CALLER_ID = "com.sims2013.disponif.fragments.DatePickerFragment.EXTRA_CALLER_ID";
	public static final String DATE_INIT = "com.sims2013.disponif.fragments.DatePickerFragment.DATE_INIT";
	
	private int mCallerId;

	public static DatePickerFragment newInstance(Bundle b) {
		DatePickerFragment f = new DatePickerFragment();
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			setCallerId(getArguments().getInt(EXTRA_CALLER_ID));
		}
	}
	
	public interface OnDateSelected {
		public void onDateSelected(DatePicker view, int year, int month, int day, int callerID);
	}	
	
	public void setListener(OnDateSelected listener) {
		mListener = listener;
	}	

	private OnDateSelected mListener;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		if (getArguments().containsKey(DATE_INIT)) {
			c.setTimeInMillis(getArguments().getLong(DATE_INIT));
		}
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	public void onDateSet(DatePicker view, int year, int month, int day) {
		mListener.onDateSelected(view, year, month, day, mCallerId);
	}

	public int getCallerId() {
		return mCallerId;
	}

	public void setCallerId(int mCallerId) {
		this.mCallerId = mCallerId;
	}
}