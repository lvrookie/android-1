package com.sims2013.disponif.fragments;

import java.util.Calendar;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment implements
		TimePickerDialog.OnTimeSetListener {

	public static final String TAG = "com.sims2013.disponif.fragments.TimePickerFragment";
	public static final String EXTRA_CALLER_ID = "com.sims2013.disponif.fragments.TimePickerFragment.EXTRA_CALLER_ID";
	public static final String HOUR_INIT = "com.sims2013.disponif.fragments.TimePickerFragment.HOUR_INIT";
	private OnTimeRangeSelected mListener;

	private int mCallerId;

	public static TimePickerFragment newInstance(Bundle b) {
		TimePickerFragment f = new TimePickerFragment();
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

	public interface OnTimeRangeSelected {
		public void onTimeRangeSelected(int hourOfDay, int minute, int callerId);
	}

	public void setListener(OnTimeRangeSelected listener) {
		mListener = listener;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current time as the default values for the picker
		final Calendar c = Calendar.getInstance();
		if (getArguments().containsKey(HOUR_INIT)) {
			c.setTimeInMillis(getArguments().getLong(HOUR_INIT));
		}
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		// Create a new instance of TimePickerDialog and return it
		return new TimePickerDialog(getActivity(), this, hour, minute,
				DateFormat.is24HourFormat(getActivity()));
	}

	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		mListener.onTimeRangeSelected(hourOfDay, minute, mCallerId);
	}
	
	public int getCallerId() {
		return mCallerId;
	}

	public void setCallerId(int mCallerId) {
		this.mCallerId = mCallerId;
	}
}