package com.sims2013.disponif.fragments;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.sims2013.disponif.DisponifApplication;
import com.sims2013.disponif.R;
import com.sims2013.disponif.Utils.BitmapManager;
import com.sims2013.disponif.Utils.DisponIFUtils;
import com.sims2013.disponif.activities.ActivityActivity;
import com.sims2013.disponif.adapter.CommentAdapter;
import com.sims2013.disponif.model.Activity;
import com.sims2013.disponif.model.User;

public class ActivityFragment extends GenericFragment implements OnClickListener {

	private static final int REQUESTED_AVAILABILITY_NONE = -1;
	
	int mRequestedActivityId;
	int mActivityId;
	Activity mActivity;

	// UI references
	TextView mCategoryTypeTv;
	TextView mDateTv;
	TextView mDescriptionTv;

	LinearLayout mUsersPicturesLL;
	ListView mCommentsList;

	EditText mAddCommentET;
	Button mAddCommentBt;

	ImageView mProfilePicture;
	ImageView mCategoryIcon;
	ImageView mLiveIcon;
	
	onActivityLeavedListener mListener;

	private CommentAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().getWindow().getAttributes().windowAnimations = R.style.slideRight;
		mListener = (onActivityLeavedListener) getActivity();
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_activity, container, false);
		initUI();
		return mView;
	}

	@Override
	public void onStart() {
		super.onStart();
		mRequestedActivityId = getArguments().getInt(
				ActivityActivity.EXTRA_REQUESTED_AVAILABILITY_ID, REQUESTED_AVAILABILITY_NONE);
		refresh();
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);

		inflater.inflate(R.menu.activity_activity_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.leaveActivityMenu) {
			shouldShowProgressDialog(true, "Chargement", "Sortie de l'activité ...");
			mClient.leaveActivity(DisponifApplication.getAccessToken(), mActivityId);
		}
		return true;
	}
	
	@Override
	protected void initUI() {
		super.initUI();

		mAddCommentBt = (Button) mView
				.findViewById(R.id.activity_comments_add_bt);
		mAddCommentBt.setOnClickListener(this);
		mAddCommentET = (EditText) mView
				.findViewById(R.id.activity_comments_et);
		mAddCommentET.setOnKeyListener(new View.OnKeyListener()
		{
			@Override
			public boolean onKey(View arg0, int keyCode, KeyEvent event) {
				if ( (event.getAction() == KeyEvent.ACTION_DOWN  ) &&
			         (keyCode           == KeyEvent.KEYCODE_ENTER)   ) {           
					mAddCommentBt.performClick();
					return true;
				}
				return false;
			}
		} );
		mUsersPicturesLL = (LinearLayout) mView
				.findViewById(R.id.activity_users_layout);

		mProfilePicture = (ImageView) mView
				.findViewById(R.id.activity_profile_picture);
		mCategoryIcon = (ImageView) mView
				.findViewById(R.id.activity_category_icon);
		mLiveIcon = (ImageView) mView.findViewById(R.id.activity_live_icon);

		mCategoryTypeTv = (TextView) mView
				.findViewById(R.id.activity_category_type);
		mDateTv = (TextView) mView.findViewById(R.id.activity_date);
		mDescriptionTv = (TextView) mView
				.findViewById(R.id.activity_description);

		mCommentsList = (ListView) mView
				.findViewById(R.id.activity_comments_list);

		// TODO : Change this to activity info !!
		BitmapManager.setBitmap(mProfilePicture, "http://graph.facebook.com/"
				+ DisponifApplication.getFacebookId() + "/picture?type=large",
				R.drawable.bkg_white_gray_border);
	}


	@Override
	protected void refresh() {
		// Call ws to get activityInfo
		int availabilityId = getArguments().getInt(
				ActivityActivity.EXTRA_AVAILABILITY_ID);

		if (mRequestedActivityId == REQUESTED_AVAILABILITY_NONE) {
			mClient.getActivity(DisponifApplication.getAccessToken(),
					availabilityId);
		} else {
			mClient.joinActivity(DisponifApplication.getAccessToken(),
					availabilityId, mRequestedActivityId);
		}
		shouldShowProgressDialog(true, "Chargement", "Récupération des infos de l'activité ...");
	}
	
	@Override
	public void onActivityReceived(Activity result) {
		super.onActivityReceived(result);
		mActivity = result;
		mRequestedActivityId = REQUESTED_AVAILABILITY_NONE;
		if (result != null) {
			float scale = getResources().getDisplayMetrics().density;
			LayoutParams lp = new LayoutParams(DisponIFUtils.dipToPixel(50,scale), DisponIFUtils.dipToPixel(50,scale));
			lp.setMargins(DisponIFUtils.dipToPixel(3,scale), 0, DisponIFUtils.dipToPixel(3,scale), 0);
			
			mUsersPicturesLL.removeAllViews();
			for (User user : result.getUsers()) {
				Log.d(TAG, "adding user "+ user.getName() + " picture");
				ImageView iv = new ImageView(getActivity());
				iv.setPadding(DisponIFUtils.dipToPixel(1,scale), DisponIFUtils.dipToPixel(1,scale), DisponIFUtils.dipToPixel(1,scale), DisponIFUtils.dipToPixel(1,scale));
				mUsersPicturesLL.addView(iv, lp);
				BitmapManager.setBitmap(iv, "http://graph.facebook.com/"
						+ user.getFacebookId() + "/picture?type=large",
						R.drawable.bkg_white_gray_border);
			}
			mActivityId = result.getId();
			mAdapter = new CommentAdapter(getActivity(),
					R.layout.item_comment, R.id.comment_text, result.getComments());
			mCommentsList.setAdapter(mAdapter);
		}
		mCommentsList.setEmptyView(mView.findViewById(R.id.empty_comment_list));
		mCommentsList.setSelection(mAdapter.getCount()-1);
		
		if (result.getAvailability().getTypeId() != -1) {
			mCategoryTypeTv.setText(result.getAvailability().getCategoryName() + " - " + result.getAvailability().getTypeName());
		} else {
			mCategoryTypeTv.setText(result.getAvailability().getCategoryName());
		}	
		
		Date startDate = DisponIFUtils.stringToDate(result.getAvailability().getStartTime());
		Date today = new Date();
		int diffInDays = (int) ((startDate.getTime() - today.getTime()) / (1000 * 60 * 60 * 24));
		int diffInHours = Math
				.round(((startDate.getTime() - today.getTime()) / (1000 * 60 * 60))) + 1;

		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(today);
		int currHour = calendar.get(Calendar.HOUR_OF_DAY);

		if (diffInHours <= 0) {
			mDateTv.setVisibility(View.GONE);
			mLiveIcon.setVisibility(View.VISIBLE);
		} else if (diffInHours < (24 - currHour)) {
			mLiveIcon.setVisibility(View.GONE);
			mDateTv
					.setText(getString(R.string.availability_date_simple_today));
		} else if (diffInDays == 0) {
			mLiveIcon.setVisibility(View.GONE);
			mDateTv.setText(getString(R.string.availability_date_simple,
					1));
		} else {
			mLiveIcon.setVisibility(View.GONE);
			mDateTv.setText(getString(R.string.availability_date_simple,
					diffInDays));
		}
		
		mDescriptionTv.setText(result.getAvailability().getDescription());
		
		BitmapManager.setBitmap(mCategoryIcon,
				"http://disponif.darkserver.fr/server/res/category/" + result.getAvailability().getCategoryId()
						+ ".png");
		
		getActivity().invalidateOptionsMenu();
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.activity_comments_add_bt) {
			if (!TextUtils.isEmpty(mAddCommentET.getText())) {
				mClient.addComment(DisponifApplication.getAccessToken(), mActivityId, mAddCommentET.getText().toString());
				shouldShowProgressDialog(true, "Patientez...", "Envoi du message en cours...");
			}
		}
	}
	
	@Override
	public void onCommentAdded(Boolean state) {
		super.onCommentAdded(state);
		mAddCommentET.setText("");
		if (state) {
			refresh();
		} else {
			DisponIFUtils.makeToast(getActivity(), "Echec lors de l'envoi du commentaire.");
		}
	}

	@Override
	public void onActivityLeft(Boolean state) {
		super.onActivityLeft(state);
		shouldShowProgressDialog(false);
		if (state) {
			mListener.onActivityLeaved();
		} else {
			DisponIFUtils.makeToast(getActivity(), "Vous ne pouvez pas sortir de l'activité");
		}
	}
	
	// Interface to be implemented by the availability list fragment.
	public interface onActivityLeavedListener {
		void onActivityLeaved();
	}

}
