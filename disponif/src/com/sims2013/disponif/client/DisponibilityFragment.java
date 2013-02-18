package com.sims2013.disponif.client;

import java.util.Calendar;

import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.sims2013.disponif.R;
import com.sims2013.disponif.fragments.DatePickerFragment;
import com.sims2013.disponif.fragments.DatePickerFragment.OnDateSelected;
import com.sims2013.disponif.fragments.TimePickerFragment;
import com.sims2013.disponif.fragments.TimePickerFragment.OnTimeRangeSelected;

public class DisponibilityFragment extends Fragment implements OnClickListener,
		OnDateSelected, OnTimeRangeSelected {

	Button mSubmitButton;
	Button mDateButtonFrom;
	Button mHourButtonFrom;
	Button mDateButtonTo;
	Button mHourButtonTo;
	Spinner mTypeSpinner;
	EditText mPlaceET;

	public enum DISPONIBILITY_TYPE {
		DISPONIBILITY_TYPE_SPORT, DISPONIBILITY_TYPE_CULTURAL, DISPONIBILITY_TYPE_VIDEO_GAME, DISPONIBILITY_TYPE_PARTY
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_disponibility,
				container, false);

		mSubmitButton = (Button) view.findViewById(R.id.dispo_button_submit);
		mDateButtonFrom = (Button) view
				.findViewById(R.id.dispo_button_date_from);
		mHourButtonFrom = (Button) view
				.findViewById(R.id.dispo_button_hour_from);
		mDateButtonTo = (Button) view.findViewById(R.id.dispo_button_date_from);
		mHourButtonTo = (Button) view.findViewById(R.id.dispo_button_hour_from);
		mPlaceET = (EditText) view.findViewById(R.id.dispo_place_et);
		mTypeSpinner = (Spinner) view.findViewById(R.id.dispo_spinner_type);

		mDateButtonFrom.setOnClickListener(this);
		mDateButtonTo.setOnClickListener(this);
		mHourButtonFrom.setOnClickListener(this);
		mHourButtonTo.setOnClickListener(this);

		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				getActivity(), R.array.disponibility_type_array,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		mTypeSpinner.setAdapter(adapter);
		return view;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == mDateButtonFrom.getId()) {
			if (getActivity() != null && !isDetached()
					&& getActivity().getSupportFragmentManager() != null) {
				Bundle b = new Bundle();
				b.putInt(DatePickerFragment.EXTRA_CALLER_ID,
						mDateButtonFrom.getId());
				final DatePickerFragment datePicker = DatePickerFragment
						.newInstance(b);
				datePicker.setListener(this);
				datePicker.show(getActivity().getFragmentManager(),
						DatePickerFragment.TAG);
				// mTimeButton.setText(getResources().getString(R.string.cart_date_fragment_choose_time));
				// mTimeButton.setEnabled(false);
			}
		} else if (v.getId() == mDateButtonTo.getId()) {
			if (getActivity() != null && !isDetached()
					&& getActivity().getSupportFragmentManager() != null) {
				final DatePickerFragment datePicker = DatePickerFragment
						.newInstance(null);
				datePicker.setListener(this);
				datePicker.show(getActivity().getFragmentManager(),
						DatePickerFragment.TAG);
				// mTimeButton.setText(getResources().getString(R.string.cart_date_fragment_choose_time));
				// mTimeButton.setEnabled(false);
			}
		} else if (v.getId() == mHourButtonFrom.getId()
				|| v.getId() == mHourButtonTo.getId()) {
			if (getActivity() != null && !isDetached()
					&& getActivity().getSupportFragmentManager() != null) {
				Bundle b = new Bundle();
				final TimePickerFragment timePicker = TimePickerFragment
						.newInstance(b);
				timePicker.setListener(this);
				timePicker.show(getActivity().getFragmentManager(),
						TimePickerFragment.TAG);
			}
		}
	}

	@Override
	public void onDateSelected(DatePicker view, int year, int monthOfYear,
			int dayOfMonth, int callerId) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, monthOfYear);
		c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		StringBuilder title = new StringBuilder();
		title.append(DateUtils.formatDateTime(view.getContext(),
				c.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_WEEKDAY
						| DateUtils.FORMAT_SHOW_WEEKDAY
						| DateUtils.FORMAT_ABBREV_MONTH));

		if (callerId == mDateButtonFrom.getId()) {
			mDateButtonFrom.setText(title);
		} else if (callerId == mDateButtonTo.getId()) {
			mDateButtonTo.setText(title);
		}
	}

	@Override
	public void onTimeRangeSelected(String value) {
		// TODO Auto-generated method stub

	}
}
