package com.sims2013.disponif.fragments;

import java.util.ArrayList;
import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.sims2013.disponif.R;
import com.sims2013.disponif.fragments.DatePickerFragment.OnDateSelected;
import com.sims2013.disponif.fragments.TimePickerFragment.OnTimeRangeSelected;

public class AvailabilityFragment extends Fragment implements OnClickListener,
		OnDateSelected, OnTimeRangeSelected {

	Button mSubmitButton;
	Button mDateButtonFrom;
	Button mHourButtonFrom;
	Button mDateButtonTo;
	Button mHourButtonTo;
	Spinner mTypeSpinner;
	EditText mPlaceET;

	TextView mErrorDatesTv;
	
	ImageView mDateFromErrorImage;
	ImageView mDateToErrorImage;
	
	
	Calendar mDateFrom;
	Calendar mDateTo;

	ArrayList<SubmitErrors> checkFieldsErrors;

	private enum SubmitErrors {
		ERROR_END_DATE_BEFORE_START_DATE, ERROR_NO_PLACE_GIVEN, ERROR_MISSING_INFORMATION
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_availability, container,
				false);

		mSubmitButton = (Button) view
				.findViewById(R.id.availability_button_submit);
		mDateButtonFrom = (Button) view
				.findViewById(R.id.availability_button_date_from);
		mHourButtonFrom = (Button) view
				.findViewById(R.id.availability_button_hour_from);
		mDateButtonTo = (Button) view
				.findViewById(R.id.availability_button_date_to);
		mHourButtonTo = (Button) view
				.findViewById(R.id.availability_button_hour_to);
		mPlaceET = (EditText) view.findViewById(R.id.availability_place_et);
		mTypeSpinner = (Spinner) view
				.findViewById(R.id.availability_spinner_type);

		mSubmitButton.setOnClickListener(this);
		mDateButtonFrom.setOnClickListener(this);
		mDateButtonTo.setOnClickListener(this);
		mHourButtonFrom.setOnClickListener(this);
		mHourButtonTo.setOnClickListener(this);
		
		mDateFromErrorImage = (ImageView) view
		.findViewById(R.id.availability_error_date_from);
		mDateToErrorImage = (ImageView) view
		.findViewById(R.id.availability_error_date_to);
		
		mErrorDatesTv = (TextView) view.findViewById(R.id.availability_error_dates);
		
		mPlaceET.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				mPlaceET.setError(null);
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				
			}
		});

		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				getActivity(), R.array.dummy_disponibility_type_array,
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
					&& getActivity().getFragmentManager() != null) {
				Bundle b = new Bundle();
				b.putInt(DatePickerFragment.EXTRA_CALLER_ID,
						mDateButtonFrom.getId());
				final DatePickerFragment datePicker = DatePickerFragment
						.newInstance(b);
				datePicker.setListener(this);
				datePicker.show(getActivity().getFragmentManager(),
						DatePickerFragment.TAG);
			}
		} else if (v.getId() == mDateButtonTo.getId()) {
			if (getActivity() != null && !isDetached()
					&& getActivity().getFragmentManager() != null) {
				Bundle b = new Bundle();
				b.putInt(DatePickerFragment.EXTRA_CALLER_ID,
						mDateButtonTo.getId());
				final DatePickerFragment datePicker = DatePickerFragment
						.newInstance(b);
				datePicker.setListener(this);
				datePicker.show(getActivity().getFragmentManager(),
						DatePickerFragment.TAG);
			}
		} else if (v.getId() == mHourButtonFrom.getId()) {
			if (getActivity() != null && !isDetached()
					&& getActivity().getFragmentManager() != null) {
				Bundle b = new Bundle();
				b.putInt(TimePickerFragment.EXTRA_CALLER_ID,
						mHourButtonFrom.getId());
				final TimePickerFragment timePicker = TimePickerFragment
						.newInstance(b);
				timePicker.setListener(this);
				timePicker.show(getActivity().getFragmentManager(),
						TimePickerFragment.TAG);
			}

		} else if (v.getId() == mHourButtonTo.getId()) {
			if (getActivity() != null && !isDetached()
					&& getActivity().getFragmentManager() != null) {
				Bundle b = new Bundle();
				b.putInt(TimePickerFragment.EXTRA_CALLER_ID,
						mHourButtonTo.getId());
				final TimePickerFragment timePicker = TimePickerFragment
						.newInstance(b);
				timePicker.setListener(this);
				timePicker.show(getActivity().getFragmentManager(),
						TimePickerFragment.TAG);
			}
		} else if (v.getId() == mSubmitButton.getId()) {
			boolean isValid = checkFields();
			if (isValid) {
				submitDisponibility();
			}
		}
	}

	private void submitDisponibility() {
		// TODO Auto-generated method stub

	}

	private boolean checkFields() {
		boolean isValid = true;

		mErrorDatesTv.setVisibility(View.INVISIBLE);
		mDateFromErrorImage.setVisibility(View.INVISIBLE);
		mDateToErrorImage.setVisibility(View.INVISIBLE);
		mPlaceET.setError(null);
		
		checkFieldsErrors = new ArrayList<SubmitErrors>();
		if (mDateButtonFrom.getText().equals(
				getString(R.string.availability_choose_date_from))) {
			addFieldError(SubmitErrors.ERROR_MISSING_INFORMATION);
			mDateFromErrorImage.setVisibility(View.VISIBLE);
			isValid = false;
		}
		if (mDateButtonTo.getText().equals(
				getString(R.string.availability_choose_date_to))) {
			addFieldError(SubmitErrors.ERROR_MISSING_INFORMATION);
			mDateToErrorImage.setVisibility(View.VISIBLE);
			isValid = false;
		}
		if (mHourButtonFrom.getText().equals(
				getString(R.string.availability_choose_hour_from))) {
			addFieldError(SubmitErrors.ERROR_MISSING_INFORMATION);
			mDateFromErrorImage.setVisibility(View.VISIBLE);
			isValid = false;
		}
		if (mHourButtonTo.getText().equals(
				getString(R.string.availability_choose_hour_to))) {
			addFieldError(SubmitErrors.ERROR_MISSING_INFORMATION);
			mDateToErrorImage.setVisibility(View.VISIBLE);
			isValid = false;
		}
		if (isValid && mDateTo != null && mDateFrom != null
				&& mDateTo.before(mDateFrom)) {
			isValid = false;
			addFieldError(SubmitErrors.ERROR_END_DATE_BEFORE_START_DATE);
			mErrorDatesTv.setVisibility(View.VISIBLE);
		}
		if (TextUtils.isEmpty(mPlaceET.getText())) {
			isValid = false;
			mPlaceET.setError(getString(R.string.availability_error_required_field));
			addFieldError(SubmitErrors.ERROR_NO_PLACE_GIVEN);
		}
		return isValid;
	}

	private void addFieldError(SubmitErrors errorMissingInformation) {
		if (!checkFieldsErrors.contains(errorMissingInformation)) {
			checkFieldsErrors.add(SubmitErrors.ERROR_MISSING_INFORMATION);
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
						| DateUtils.FORMAT_SHOW_WEEKDAY
						| DateUtils.FORMAT_ABBREV_MONTH));

		if (callerId == mDateButtonFrom.getId()) {
			mDateFrom = c;
			mDateButtonFrom.setText(title);
			mHourButtonFrom.setEnabled(true);
		} else if (callerId == mDateButtonTo.getId()) {
			mDateTo = c;
			mDateButtonTo.setText(title);
			mHourButtonTo.setEnabled(true);
		}
	}

	@Override
	public void onTimeRangeSelected(int hourOfDay, int minute, int callerId) {
		if (callerId == mHourButtonFrom.getId()) {
			mDateFrom.set(Calendar.HOUR_OF_DAY, hourOfDay);
			mDateFrom.set(Calendar.MINUTE, minute);
			mHourButtonFrom.setText(hourOfDay + ":" + ((minute<10) ? "0" + minute : minute));

			mDateFromErrorImage.setVisibility(View.INVISIBLE);
		} else if (callerId == mHourButtonTo.getId()) {
			mDateTo.set(Calendar.HOUR_OF_DAY, hourOfDay);
			mDateTo.set(Calendar.MINUTE, minute);
			mHourButtonTo.setText(hourOfDay + ":" + ((minute<10) ? "0" + minute : minute));
			
			mDateToErrorImage.setVisibility(View.INVISIBLE);
		}
	}
}
