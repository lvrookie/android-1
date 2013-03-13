package com.sims2013.disponif.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sims2013.disponif.R;
import com.sims2013.disponif.Utils.BitmapManager;
import com.sims2013.disponif.Utils.DisponIFUtils;
import com.sims2013.disponif.model.Availability;
import com.sims2013.disponif.model.User;

public class MatchAvailabilityAdapter extends ArrayAdapter<Availability> {

	private static class MatchAvailabilityHolder {
		ImageView mProfilePictureView;
		TextView mUserName;
		TextView mDate;
		TextView mDescription;
		Button mMoreButton;
		TextView mJoinActivity;
	}

	public MatchAvailabilityAdapter(Fragment context, int textViewResourceId,
			List<Availability> objects) {
		super(context.getActivity(), textViewResourceId, objects);
		mLayout = textViewResourceId;
		mAvailabilities = (ArrayList<Availability>) objects;
		mFragment = context;
	}

	public interface onJoinActivityClickedListener {
		public void onJoinActivityClicked(int requestedAvailabilityPosition);
	}
	
	protected Fragment mFragment;
	protected int mLayout;
	protected ArrayList<Availability> mAvailabilities = new ArrayList<Availability>();

	@Override
	public int getCount() {
		return mAvailabilities.size();
	}

	@Override
	public Availability getItem(int i) {
		return mAvailabilities.get(i);
	}

	@Override
	public long getItemId(int index) {
		return mAvailabilities.get(index).getId();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		MatchAvailabilityHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (mFragment.getActivity())
					.getLayoutInflater();
			convertView = inflater.inflate(mLayout, parent, false);
			holder = new MatchAvailabilityHolder();
			holder.mProfilePictureView = (ImageView) convertView
					.findViewById(R.id.item_match_availability_profile_picture);
			holder.mDate = (TextView) convertView
					.findViewById(R.id.item_match_availability_date);
			holder.mDescription = (TextView) convertView
					.findViewById(R.id.item_match_availability_description);
			holder.mMoreButton = (Button) convertView
					.findViewById(R.id.expandable_toggle_button);
			holder.mUserName = (TextView) convertView
					.findViewById(R.id.item_match_availability_name);
			holder.mMoreButton
					.setOnLongClickListener((OnLongClickListener) mFragment);
			holder.mJoinActivity = (TextView) convertView
					.findViewById(R.id.item_availability_join_activity);
			holder.mJoinActivity.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (v.getId() == R.id.item_availability_join_activity) {
						((onJoinActivityClickedListener) mFragment).onJoinActivityClicked(position);
					}
				}
			});

			convertView.setTag(holder);
		} else {
			holder = (MatchAvailabilityHolder) convertView.getTag();
		}

		Availability av = mAvailabilities.get(position);

		holder.mDescription.setText(av.getDescription());
		holder.mDate
				.setText("Du "
						+ DisponIFUtils.datetimeToFrDate(getContext(),
								av.getStartTime())
						+ " à "
						+ DisponIFUtils.datetimeToFrTime(getContext(),
								av.getStartTime())
						+ " au "
						+ DisponIFUtils.datetimeToFrDate(getContext(),
								av.getEndTime())
						+ " à "
						+ DisponIFUtils.datetimeToFrTime(getContext(),
								av.getEndTime()));

		User user = av.getUser();
		if (user != null) {
			holder.mUserName.setText(av.getUser().getName() + " "
					+ av.getUser().getSurname());
			BitmapManager.setBitmap(holder.mProfilePictureView,
					"http://graph.facebook.com/" + av.getUser().getFacebookId()
							+ "/picture?type=large",
					R.drawable.bkg_white_gray_border);
			holder.mJoinActivity.setText("Rejoindre " + user.getName() + " " + user.getSurname());
		} else {
			BitmapManager
					.setBitmap(
							holder.mProfilePictureView,
							"http://www.theprprofessional.com/wp-content/uploads/2011/11/facebook-profile-picture.jpg");
		}

		// holder.mTypeSimple.setText(av.getTypeName());
		//
		// Date startDate = DisponIFUtils.stringToDate(av.getStartTime());
		// Date today = new Date();
		//
		// int diffInDays = (int)( (startDate.getTime() - today.getTime())
		// / (1000 * 60 * 60 * 24) );
		//
		// if (diffInDays == 0) {
		// holder.mTimeSimple.setText("Aujourd'hui");
		// } else {
		// holder.mTimeSimple.setText("Dans " + diffInDays + " jours");
		// }

		return convertView;
	}

}
