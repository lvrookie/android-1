package com.sims2013.disponif.fragments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.sims2013.disponif.DisponifApplication;
import com.sims2013.disponif.R;
import com.sims2013.disponif.Utils.DisponIFUtils;
import com.sims2013.disponif.adapter.CategorySpinnerAdapter;
import com.sims2013.disponif.adapter.TypeSpinnerAdapter;
import com.sims2013.disponif.model.Availability;
import com.sims2013.disponif.model.Category;
import com.sims2013.disponif.model.Type;

public class ActivityFragment extends GenericFragment {

//	Button mSubmitButton;
//	Button mDateButtonFrom;
//	Button mHourButtonFrom;
//	Button mDateButtonTo;
//	Button mHourButtonTo;
//	Spinner mActivitySpinner;
//	Spinner mTypeSpinner;
//	EditText mPlaceET;
//	EditText mDescriptionET;
//	LinearLayout mTypeSpinnerLayout;
//	TextView mErrorDatesTv;
//
//	ImageView mDateFromErrorImage;
//	ImageView mDateToErrorImage;
//
//	Calendar mDateFrom;
//	Calendar mDateTo;
//
//	ArrayList<Category> mCategories;
//	Category mCurrentCategory;
//	Type mCurrentType;
//
//	ArrayList<SubmitErrors> checkFieldsErrors;
//
//	onAvailabilityAddedListener mListener;
//	private CategorySpinnerAdapter mCategoryAdapter;
//	private boolean mShouldHideTypeSpinner;
//
//
//	private enum SubmitErrors {
//		ERROR_NO_PLACE_GIVEN, ERROR_MISSING_INFORMATION, ERROR_END_DATE_BEFORE_START_DATE, ERROR_PAST_START_DATE, ERROR_NO_DESCRIPTION_GIVEN
//	}
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		getActivity().getWindow().getAttributes().windowAnimations = R.style.slideRight;
//		mListener = (onAvailabilityAddedListener) getActivity();
//	}
//
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_activity, container,
				false);
		initUI();
		return mView;
	}
//
//	@Override
//	protected void initUI() {
//		super.initUI();
//		mCurrentCategory = null;
//		mCurrentType = null;
//
//		mSubmitButton = (Button) mView
//				.findViewById(R.id.availability_button_submit);
//		mDateButtonFrom = (Button) mView
//				.findViewById(R.id.availability_button_date_from);
//		mHourButtonFrom = (Button) mView
//				.findViewById(R.id.availability_button_hour_from);
//		mDateButtonTo = (Button) mView
//				.findViewById(R.id.availability_button_date_to);
//		mHourButtonTo = (Button) mView
//				.findViewById(R.id.availability_button_hour_to);
//		mPlaceET = (EditText) mView.findViewById(R.id.availability_place_et);
//		mDescriptionET = (EditText) mView
//				.findViewById(R.id.availability_description_et);
//		mActivitySpinner = (Spinner) mView
//				.findViewById(R.id.availability_spinner_category);
//		mTypeSpinner = (Spinner) mView
//				.findViewById(R.id.availability_spinner_type);
//		mTypeSpinnerLayout = (LinearLayout) mView.findViewById(R.id.availability_spinner_type_ll);
//
//		mDateFromErrorImage = (ImageView) mView
//				.findViewById(R.id.availability_error_date_from);
//		mDateToErrorImage = (ImageView) mView
//				.findViewById(R.id.availability_error_date_to);
//
//		mErrorDatesTv = (TextView) mView
//				.findViewById(R.id.availability_error_dates);
//
//		mPlaceET.addTextChangedListener(new TextWatcher() {
//
//			@Override
//			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
//					int arg3) {
//				mPlaceET.setError(null);
//			}
//
//			@Override
//			public void beforeTextChanged(CharSequence arg0, int arg1,
//					int arg2, int arg3) {
//
//			}
//
//			@Override
//			public void afterTextChanged(Editable arg0) {
//
//			}
//		});
//		mDescriptionET.addTextChangedListener(new TextWatcher() {
//
//			@Override
//			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
//					int arg3) {
//				mDescriptionET.setError(null);
//			}
//
//			@Override
//			public void beforeTextChanged(CharSequence arg0, int arg1,
//					int arg2, int arg3) {
//
//			}
//
//			@Override
//			public void afterTextChanged(Editable arg0) {
//
//			}
//		});
//
//		mActivitySpinner.setEnabled(false);
//		mTypeSpinner.setEnabled(false);
//
//		mActivitySpinner
//				.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//					@Override
//					public void onItemSelected(AdapterView<?> arg0, View arg1,
//							int position, long arg3) {
//						mCurrentCategory = mCategories.get(position);
//						TypeSpinnerAdapter adapter = new TypeSpinnerAdapter(
//								getActivity(), mCategories.get(position)
//										.getTypes());
//						mTypeSpinner.setAdapter(adapter);
//						mTypeSpinner.setEnabled(true);
//						if (mCurrentCategory.getTypes() ==null || mCurrentCategory.getTypes().isEmpty()) {
//							shouldHideTypeSpinner(true);
//						} else {
//							shouldHideTypeSpinner(false);
//						}
//					}
//
//					@Override
//					public void onNothingSelected(AdapterView<?> arg0) {
//					}
//
//				});
//
//		mTypeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//			@Override
//			public void onItemSelected(AdapterView<?> arg0, View arg1,
//					int position, long arg3) {
//				mCurrentType = mCurrentCategory.getTypes().get(position);
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//			}
//
//		});
//
//		// Call ws to get all the categories to fill the spinners.
//		mClient.getAllCategories(DisponifApplication.getAccessToken());
//		shouldShowProgressDialog(true);
//	}
//
//	// Method calling the ws to send the new availability
//	@SuppressLint("SimpleDateFormat")
//	private void submitDisponibility() {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Availability av = new Availability();
//		av.setCategoryId(mCurrentCategory.getId());
//		if (!mShouldHideTypeSpinner) {
//			av.setTypeId(mCurrentType.getId());
//		} else {
//			av.setTypeId(Availability.TYPE_NO_TYPE);
//		}
//		av.setDescription(mDescriptionET.getText().toString());
//
//		av.setStartTime(sdf.format(mDateFrom.getTime()));
//		av.setEndTime(sdf.format(mDateTo.getTime()));
//
//		mClient.addAvailability(DisponifApplication.getAccessToken(), av);
//		shouldShowProgressDialog(true, "Ajout", "Ajout de la disponibilité ...");
//	}
//
//	// Method used to check the validity of the fields before calling the ws to
//	// send it
//	private boolean checkFields() {
//		boolean isValid = true;
//
//		mErrorDatesTv.setVisibility(View.INVISIBLE);
//		mDateFromErrorImage.setVisibility(View.INVISIBLE);
//		mDateToErrorImage.setVisibility(View.INVISIBLE);
//		mPlaceET.setError(null);
//		mDescriptionET.setError(null);
//
//		checkFieldsErrors = new ArrayList<SubmitErrors>();
//		if (mDateButtonFrom.getText().equals(
//				getString(R.string.availability_choose_date_from))) {
//			addFieldError(SubmitErrors.ERROR_MISSING_INFORMATION);
//			mDateFromErrorImage.setVisibility(View.VISIBLE);
//			isValid = false;
//		}
//		if (mDateButtonTo.getText().equals(
//				getString(R.string.availability_choose_date_to))) {
//			addFieldError(SubmitErrors.ERROR_MISSING_INFORMATION);
//			mDateToErrorImage.setVisibility(View.VISIBLE);
//			isValid = false;
//		}
//		if (mHourButtonFrom.getText().equals(
//				getString(R.string.availability_choose_hour_from))) {
//			addFieldError(SubmitErrors.ERROR_MISSING_INFORMATION);
//			mDateFromErrorImage.setVisibility(View.VISIBLE);
//			isValid = false;
//		}
//		if (mHourButtonTo.getText().equals(
//				getString(R.string.availability_choose_hour_to))) {
//			addFieldError(SubmitErrors.ERROR_MISSING_INFORMATION);
//			mDateToErrorImage.setVisibility(View.VISIBLE);
//			isValid = false;
//		}
//		if (isValid && mDateTo != null && mDateFrom != null
//				&& mDateTo.before(mDateFrom)) {
//			isValid = false;
//			addFieldError(SubmitErrors.ERROR_END_DATE_BEFORE_START_DATE);
//			mErrorDatesTv.setVisibility(View.VISIBLE);
//			mErrorDatesTv.setText(getString(R.string.availability_dates_error));
//		}
//		if (isValid && mDateFrom.before(Calendar.getInstance())) {
//			isValid = false;
//			addFieldError(SubmitErrors.ERROR_PAST_START_DATE);
//			mErrorDatesTv.setVisibility(View.VISIBLE);
//			mErrorDatesTv
//					.setText(getString(R.string.availability_past_start_date));
//		}
//		if (TextUtils.isEmpty(mPlaceET.getText())) {
//			isValid = false;
//			mPlaceET.setError(getString(R.string.availability_error_required_field));
//			addFieldError(SubmitErrors.ERROR_NO_PLACE_GIVEN);
//		}
//		if (TextUtils.isEmpty(mDescriptionET.getText())) {
//			isValid = false;
//			mDescriptionET
//					.setError(getString(R.string.availability_error_required_field));
//			addFieldError(SubmitErrors.ERROR_NO_DESCRIPTION_GIVEN);
//		}
//		return isValid;
//	}
//
//	// Method used to add an error to be displayed by a toast.
//	private void addFieldError(SubmitErrors errorMissingInformation) {
//		if (!checkFieldsErrors.contains(errorMissingInformation)) {
//			checkFieldsErrors.add(errorMissingInformation);
//		}
//	}
//
//
//	// Method called when the user receive all the categories to fill the
//	// sppinners.
//	@Override
//	public void onCategoriesReceive(ArrayList<Category> categories) {
//		super.onCategoriesReceive(categories);
//		if (categories != null && categories.size() > 0) {
//			// DisponIFUtils.makeToast(getActivity(), "receive categories");
//			mCategories = categories;
//			mCategoryAdapter = new CategorySpinnerAdapter(
//					getActivity(), mCategories);
//			mActivitySpinner.setAdapter(mCategoryAdapter);
//			mActivitySpinner.setEnabled(true);
//			mCurrentCategory = mCategories.get(0);
//			if (mCurrentCategory.getTypes() == null || mCurrentCategory.getTypes().size() == 0) {
//				shouldHideTypeSpinner(true);
//			} else {
//				shouldHideTypeSpinner(false);
//			}
//		}
//	}
//
//	// Method called when the user's availability has been added.
//	// it has to be implemented by the availability list fragment
//	// to refresh the list.
//	@Override
//	public void onAvailabilityAdded(int result) {
//		shouldShowProgressDialog(false);
//		super.onAvailabilityAdded(result);
//		if (result != -1) {
//			mListener.onAvailabilityAdded();
//		} else {
//			DisponIFUtils.makeToast(getActivity(),
//					"Erreur pendant l'ajout de la dispo");
//		}
//	}
//	
//	public void shouldHideTypeSpinner(boolean shouldHide){
//		mShouldHideTypeSpinner = shouldHide;
//		Animation a;
//		if (shouldHide) {
////			mTypeSpinnerLayout.setVisibility(View.GONE);
//			a = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_left);
//			a.setFillAfter(true);
//			a.setAnimationListener(new Animation.AnimationListener() {
//				@Override
//				public void onAnimationStart(Animation animation) {}
//				@Override
//				public void onAnimationRepeat(Animation animation) {}
//				@Override
//				public void onAnimationEnd(Animation animation) {
//					mTypeSpinnerLayout.setVisibility(View.GONE);
//				}
//			});
//			if (mTypeSpinnerLayout.getVisibility() != View.GONE) {
//				mTypeSpinnerLayout.startAnimation(a);
//			}
//		} else {
////			mTypeSpinnerLayout.setVisibility(View.VISIBLE);
//			a = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_left);
//			a.setFillAfter(true);
//			a.setAnimationListener(new Animation.AnimationListener() {
//				@Override
//				public void onAnimationStart(Animation animation) {
//					mTypeSpinnerLayout.setVisibility(View.VISIBLE);
//				}
//				@Override
//				public void onAnimationRepeat(Animation animation) {}
//				@Override
//				public void onAnimationEnd(Animation animation) {}
//			});
//			if (mTypeSpinnerLayout.getVisibility() != View.VISIBLE) {
//				mTypeSpinnerLayout.startAnimation(a);
//				mCurrentType = mCurrentCategory.getTypes().get(0);
//			}
//		}
//	}
//
//	// Interface to be implemented by the availability list fragment.
//	public interface onAvailabilityAddedListener {
//		void onAvailabilityAdded();
//	}
//
//	@Override
//	protected void refresh() {
//		mClient.getAllCategories(DisponifApplication.getAccessToken());
//	}

	@Override
	protected void refresh() {
		// TODO Auto-generated method stub
		
	}

}
