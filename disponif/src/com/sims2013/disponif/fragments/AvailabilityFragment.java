package com.sims2013.disponif.fragments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.sims2013.disponif.DisponifApplication;
import com.sims2013.disponif.R;
import com.sims2013.disponif.Utils.DisponIFUtils;
import com.sims2013.disponif.adapter.CategorySpinnerAdapter;
import com.sims2013.disponif.adapter.TypeSpinnerAdapter;
import com.sims2013.disponif.fragments.DatePickerFragment.OnDateSelected;
import com.sims2013.disponif.fragments.TimePickerFragment.OnTimeRangeSelected;
import com.sims2013.disponif.model.Availability;
import com.sims2013.disponif.model.Category;
import com.sims2013.disponif.model.Type;

public class AvailabilityFragment extends GenericFragment implements
		OnClickListener, OnDateSelected, OnTimeRangeSelected {

	Button mSubmitButton;
	Button mDateButtonFrom;
	Button mHourButtonFrom;
	Button mDateButtonTo;
	Button mHourButtonTo;
	Spinner mActivitySpinner;
	Spinner mTypeSpinner;
	EditText mPlaceET;
	EditText mDescriptionET;
	LinearLayout mTypeSpinnerLayout;
	TextView mErrorDatesTv;
	
	RadioButton mPublicRadio;
	RadioButton mPrivateRadio;

	ImageView mDateFromErrorImage;
	ImageView mDateToErrorImage;

	Calendar mDateFrom;
	Calendar mDateTo;
	
	Boolean mIsHourFromSelected;
	Boolean mIsHourToSelected;

	ArrayList<Category> mCategories;
	Category mCurrentCategory;
	Type mCurrentType;

	ArrayList<SubmitErrors> checkFieldsErrors;

	onAvailabilityAddedListener mListener;
	private CategorySpinnerAdapter mCategoryAdapter;
	private boolean mShouldHideTypeSpinner;


	private enum SubmitErrors {
		ERROR_NO_PLACE_GIVEN, ERROR_MISSING_INFORMATION, ERROR_END_DATE_BEFORE_START_DATE, ERROR_PAST_START_DATE, ERROR_NO_DESCRIPTION_GIVEN
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().getWindow().getAttributes().windowAnimations = R.style.slideRight;
		mListener = (onAvailabilityAddedListener) getActivity();
		mDateFrom = null;
		mDateTo = null;
		mIsHourFromSelected = false;
		mIsHourToSelected = false;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_availability, container,
				false);
		initUI();
		return mView;
	}

	@Override
	protected void initUI() {
		super.initUI();
		mCurrentCategory = null;
		mCurrentType = null;

		mSubmitButton = (Button) mView
				.findViewById(R.id.availability_button_submit);
		mDateButtonFrom = (Button) mView
				.findViewById(R.id.availability_button_date_from);
		mHourButtonFrom = (Button) mView
				.findViewById(R.id.availability_button_hour_from);
		mDateButtonTo = (Button) mView
				.findViewById(R.id.availability_button_date_to);
		mHourButtonTo = (Button) mView
				.findViewById(R.id.availability_button_hour_to);
		mPlaceET = (EditText) mView.findViewById(R.id.availability_place_et);
		mDescriptionET = (EditText) mView
				.findViewById(R.id.availability_description_et);
		mActivitySpinner = (Spinner) mView
				.findViewById(R.id.availability_spinner_category);
		mTypeSpinner = (Spinner) mView
				.findViewById(R.id.availability_spinner_type);
		mTypeSpinnerLayout = (LinearLayout) mView.findViewById(R.id.availability_spinner_type_ll);

		mSubmitButton.setOnClickListener(this);
		mDateButtonFrom.setOnClickListener(this);
		mDateButtonTo.setOnClickListener(this);
		mHourButtonFrom.setOnClickListener(this);
		mHourButtonTo.setOnClickListener(this);

		mPublicRadio = (RadioButton) mView.findViewById(R.id.radioPrivacyPublic);
		mPrivateRadio = (RadioButton) mView.findViewById(R.id.radioPrivacyPrivate);
		
		mDateFromErrorImage = (ImageView) mView
				.findViewById(R.id.availability_error_date_from);
		mDateToErrorImage = (ImageView) mView
				.findViewById(R.id.availability_error_date_to);

		mErrorDatesTv = (TextView) mView
				.findViewById(R.id.availability_error_dates);

		mPlaceET.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				mPlaceET.setError(null);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {

			}
		});
		mDescriptionET.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				mDescriptionET.setError(null);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {

			}
		});

		mActivitySpinner.setEnabled(false);
		mTypeSpinner.setEnabled(false);

		mActivitySpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						mCurrentCategory = mCategories.get(position);
						Type noType = new Type();
						noType.setName("Tout");
						noType.setId(Availability.TYPE_NO_TYPE);
						ArrayList<Type> typesArray = new ArrayList<Type>(mCategories.get(position)
								.getTypes());
						typesArray.add(0, noType);
						TypeSpinnerAdapter adapter = new TypeSpinnerAdapter(
								getActivity(), typesArray);
						mTypeSpinner.setAdapter(adapter);
						mTypeSpinner.setEnabled(true);
						if (mCurrentCategory.getTypes() ==null || mCurrentCategory.getTypes().isEmpty()) {
							shouldHideTypeSpinner(true);
						} else {
							shouldHideTypeSpinner(false);
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}

				});

		mTypeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				mCurrentType = (Type) mTypeSpinner.getAdapter().getItem(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}

		});

		// Call ws to get all the categories to fill the spinners.
		mClient.getAllCategories(DisponifApplication.getAccessToken());
		shouldShowProgressDialog(true);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == mDateButtonFrom.getId()) {
			if (getActivity() != null && !isDetached()
					&& getActivity().getFragmentManager() != null) {
				Bundle b = new Bundle();
				b.putInt(DatePickerFragment.EXTRA_CALLER_ID,
						mDateButtonFrom.getId());
				if (mDateFrom != null) {
					b.putLong(DatePickerFragment.DATE_INIT, mDateFrom.getTimeInMillis());
				}
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
				if (mDateFrom != null && mDateTo == null) {
					b.putLong(DatePickerFragment.DATE_INIT, mDateFrom.getTimeInMillis());
				} else if (mDateTo != null) {
					b.putLong(DatePickerFragment.DATE_INIT, mDateTo.getTimeInMillis());
				}
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
				if (mIsHourFromSelected) {
					b.putLong(TimePickerFragment.HOUR_INIT, mDateFrom.getTimeInMillis());
				}
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
				if (mIsHourFromSelected && !mIsHourToSelected) {
					b.putLong(TimePickerFragment.HOUR_INIT, mDateFrom.getTimeInMillis());
				} else if (mIsHourToSelected) {
					b.putLong(TimePickerFragment.HOUR_INIT, mDateTo.getTimeInMillis());
				}
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
			} else {
				if (checkFieldsErrors != null && !checkFieldsErrors.isEmpty()) {
					String errorMessage = "";
					for (SubmitErrors error : checkFieldsErrors) {
						if (!TextUtils.isEmpty(errorMessage)) {
							errorMessage += "\n";
						}
						switch (error) {
						case ERROR_NO_PLACE_GIVEN:
							errorMessage += "- "
									+ getString(R.string.availability_toast_error_no_place);
							break;
						case ERROR_MISSING_INFORMATION:
							errorMessage += "- "
									+ getString(R.string.availability_toast_error_required_field);
							break;
						case ERROR_END_DATE_BEFORE_START_DATE:
							errorMessage += "- "
									+ getString(R.string.availability_dates_error);
							break;
						case ERROR_PAST_START_DATE:
							errorMessage += "- "
									+ getString(R.string.availability_past_start_date);
							break;
						case ERROR_NO_DESCRIPTION_GIVEN:
							errorMessage += "- "
									+ getString(R.string.availability_toast_error_no_description);
							break;
						}
					}
					DisponIFUtils.makeToast(getActivity(), errorMessage);
				}
			}
		}
	}

	// Method calling the ws to send the new availability
	@SuppressLint("SimpleDateFormat")
	private void submitDisponibility() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Availability av = new Availability();
		av.setCategoryId(mCurrentCategory.getId());
		if (!mShouldHideTypeSpinner) {
			av.setTypeId(mCurrentType.getId());
		} else {
			av.setTypeId(Availability.TYPE_NO_TYPE);
		}
		av.setDescription(mDescriptionET.getText().toString());

		av.setStartTime(sdf.format(mDateFrom.getTime()));
		av.setEndTime(sdf.format(mDateTo.getTime()));

		if (mPublicRadio.isChecked()) {
			av.setPrivacy(Availability.PRIVACY_PUBLIC);
		} else {
			av.setPrivacy(Availability.PRIVACY_PRIVATE);
		}
		
		mClient.addAvailability(DisponifApplication.getAccessToken(), av);
		shouldShowProgressDialog(true, "Ajout", "Ajout de la disponibilité ...");
	}

	// Method used to check the validity of the fields before calling the ws to
	// send it
	private boolean checkFields() {
		boolean isValid = true;

		mErrorDatesTv.setVisibility(View.INVISIBLE);
		mDateFromErrorImage.setVisibility(View.INVISIBLE);
		mDateToErrorImage.setVisibility(View.INVISIBLE);
		mPlaceET.setError(null);
		mDescriptionET.setError(null);

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
			mErrorDatesTv.setText(getString(R.string.availability_dates_error));
		}
		if (isValid && mDateFrom.before(Calendar.getInstance())) {
			isValid = false;
			addFieldError(SubmitErrors.ERROR_PAST_START_DATE);
			mErrorDatesTv.setVisibility(View.VISIBLE);
			mErrorDatesTv
					.setText(getString(R.string.availability_past_start_date));
		}
		if (TextUtils.isEmpty(mPlaceET.getText())) {
			isValid = false;
			mPlaceET.setError(getString(R.string.availability_error_required_field));
			addFieldError(SubmitErrors.ERROR_NO_PLACE_GIVEN);
		}
		if (TextUtils.isEmpty(mDescriptionET.getText())) {
			isValid = false;
			mDescriptionET
					.setError(getString(R.string.availability_error_required_field));
			addFieldError(SubmitErrors.ERROR_NO_DESCRIPTION_GIVEN);
		}
		return isValid;
	}

	// Method used to add an error to be displayed by a toast.
	private void addFieldError(SubmitErrors errorMissingInformation) {
		if (!checkFieldsErrors.contains(errorMissingInformation)) {
			checkFieldsErrors.add(errorMissingInformation);
		}
	}

	// Method called when the user has chosen a date
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

	// Method called when the user has chosen an hour
	@Override
	public void onTimeRangeSelected(int hourOfDay, int minute, int callerId) {
		if (callerId == mHourButtonFrom.getId()) {
			mDateFrom.set(Calendar.HOUR_OF_DAY, hourOfDay);
			mDateFrom.set(Calendar.MINUTE, minute);
			mHourButtonFrom.setText(hourOfDay + ":"
					+ ((minute < 10) ? "0" + minute : minute));
			mIsHourFromSelected = true;
			mDateFromErrorImage.setVisibility(View.INVISIBLE);
		} else if (callerId == mHourButtonTo.getId()) {
			mDateTo.set(Calendar.HOUR_OF_DAY, hourOfDay);
			mDateTo.set(Calendar.MINUTE, minute);
			mHourButtonTo.setText(hourOfDay + ":"
					+ ((minute < 10) ? "0" + minute : minute));
			mIsHourToSelected = true;
			mDateToErrorImage.setVisibility(View.INVISIBLE);
		}
	}

	// Method called when the user receive all the categories to fill the
	// sppinners.
	@Override
	public void onCategoriesReceive(ArrayList<Category> categories) {
		super.onCategoriesReceive(categories);
		if (categories != null && categories.size() > 0) {
			// DisponIFUtils.makeToast(getActivity(), "receive categories");
			mCategories = categories;
			mCategoryAdapter = new CategorySpinnerAdapter(
					getActivity(), mCategories);
			mActivitySpinner.setAdapter(mCategoryAdapter);
			mActivitySpinner.setEnabled(true);
			mCurrentCategory = mCategories.get(0);
			if (mCurrentCategory.getTypes() == null || mCurrentCategory.getTypes().size() == 0) {
				shouldHideTypeSpinner(true);
			} else {
				shouldHideTypeSpinner(false);
			}
		}
	}

	// Method called when the user's availability has been added.
	// it has to be implemented by the availability list fragment
	// to refresh the list.
	@Override
	public void onAvailabilityAdded(int result) {
		shouldShowProgressDialog(false);
		super.onAvailabilityAdded(result);
		if (result != -1) {
			mListener.onAvailabilityAdded();
		} else {
			DisponIFUtils.makeToast(getActivity(),
					"Erreur pendant l'ajout de la dispo");
		}
	}
	
	public void shouldHideTypeSpinner(boolean shouldHide){
		mShouldHideTypeSpinner = shouldHide;
		Animation a;
		if (shouldHide) {
//			mTypeSpinnerLayout.setVisibility(View.GONE);
			a = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_left);
			a.setFillAfter(true);
			a.setAnimationListener(new Animation.AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {}
				@Override
				public void onAnimationRepeat(Animation animation) {}
				@Override
				public void onAnimationEnd(Animation animation) {
					mTypeSpinnerLayout.setVisibility(View.GONE);
				}
			});
			if (mTypeSpinnerLayout.getVisibility() != View.GONE) {
				mTypeSpinnerLayout.startAnimation(a);
			}
		} else {
//			mTypeSpinnerLayout.setVisibility(View.VISIBLE);
			a = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_left);
			a.setFillAfter(true);
			a.setAnimationListener(new Animation.AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
					mTypeSpinnerLayout.setVisibility(View.VISIBLE);
				}
				@Override
				public void onAnimationRepeat(Animation animation) {}
				@Override
				public void onAnimationEnd(Animation animation) {}
			});
			if (mTypeSpinnerLayout.getVisibility() != View.VISIBLE) {
				mTypeSpinnerLayout.startAnimation(a);
				mCurrentType = (Type) mTypeSpinner.getAdapter().getItem(0);
			}
		}
	}

	// Interface to be implemented by the availability list fragment.
	public interface onAvailabilityAddedListener {
		void onAvailabilityAdded();
	}

	@Override
	protected void refresh() {
		mClient.getAllCategories(DisponifApplication.getAccessToken());
	}

}
