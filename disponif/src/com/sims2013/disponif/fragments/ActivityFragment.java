package com.sims2013.disponif.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
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

	private CommentAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().getWindow().getAttributes().windowAnimations = R.style.slideRight;
		
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
		BitmapManager.setBitmap(mCategoryIcon,
				"http://disponif.darkserver.fr/server/res/category/" + 1
						+ ".png");
		mCategoryTypeTv.setText("Change to correct activity info");
		mDateTv.setText("Change to correct activity info");
		mDescriptionTv.setText("Change to correct activity info");
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
		mRequestedActivityId = REQUESTED_AVAILABILITY_NONE;
		if (result != null) {
			float scale = getResources().getDisplayMetrics().density;
			LayoutParams lp = new LayoutParams(DisponIFUtils.dipToPixel(50,scale), DisponIFUtils.dipToPixel(50,scale));
			lp.setMargins(DisponIFUtils.dipToPixel(3,scale), 0, DisponIFUtils.dipToPixel(3,scale), 0);
			
			ImageView iv = new ImageView(getActivity());
			iv.setPadding(DisponIFUtils.dipToPixel(1,scale), DisponIFUtils.dipToPixel(1,scale), DisponIFUtils.dipToPixel(1,scale), DisponIFUtils.dipToPixel(1,scale));

			mUsersPicturesLL.removeAllViews();
			mUsersPicturesLL.addView(iv, lp);

			BitmapManager.setBitmap(iv, "http://graph.facebook.com/"
					+ DisponifApplication.getFacebookId() + "/picture?type=large",
					R.drawable.bkg_white_gray_border);
			
			for (User user : result.getUsers()) {
				Log.d(TAG, "adding user "+ user.getName() + " picture");
				iv = new ImageView(getActivity());
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
		if (state) {
			refresh();
		} else {
			DisponIFUtils.makeToast(getActivity(), "Echec lors de l'envoi du commentaire.");
		}
	}

}
