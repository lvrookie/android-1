package com.sims2013.disponif.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sims2013.disponif.R;
import com.sims2013.disponif.Utils.DisponIFUtils;
import com.sims2013.disponif.model.Availability;
import com.sims2013.disponif.model.User;

public class MatchAvailabilityAdapter extends AvailabilityAdapter {

	private static class AvailabilityHolder {
		ImageView mProfilePictureView;
		TextView mUserName;
		TextView mStartTime;
		TextView mEndTime;
		TextView mDescription;
		Button mMoreButton;
	}

	public MatchAvailabilityAdapter(Fragment context, int textViewResourceId,
			List<Availability> objects) {
		super(context, textViewResourceId, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		AvailabilityHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (mFragment.getActivity())
					.getLayoutInflater();
			convertView = inflater.inflate(mLayout, parent, false);
			holder = new AvailabilityHolder();
			holder.mProfilePictureView = (ImageView) convertView
					.findViewById(R.id.item_match_availability_profile_picture);
			holder.mStartTime = (TextView) convertView
					.findViewById(R.id.item_match_availability_startDate);
			holder.mEndTime = (TextView) convertView
					.findViewById(R.id.item_match_availability_endDate);
			holder.mDescription = (TextView) convertView
					.findViewById(R.id.item_match_description);
			holder.mMoreButton = (Button) convertView
					.findViewById(R.id.expandable_toggle_button);
			holder.mUserName = (TextView) convertView
					.findViewById(R.id.item_match_availability_name);

			holder.mMoreButton
					.setOnLongClickListener((OnLongClickListener) mFragment);

			convertView.setTag(holder);
		} else {
			holder = (AvailabilityHolder) convertView.getTag();
		}

		Availability av = mAvailabilities.get(position);

		holder.mDescription.setText(av.getDescription());
		holder.mStartTime.setText("du "
				+ DisponIFUtils.datetimeToFrDate(getContext(),
						av.getStartTime())
				+ " - "
				+ DisponIFUtils.datetimeToFrTime(getContext(),
						av.getStartTime()));
		holder.mEndTime
				.setText("au "
						+ DisponIFUtils.datetimeToFrDate(getContext(),
								av.getEndTime())
						+ " - "
						+ DisponIFUtils.datetimeToFrTime(getContext(),
								av.getEndTime()));

		User user = av.getUser();
		if (user != null) {
			holder.mUserName.setText(av.getUser().getName() + " "
					+ av.getUser().getSurname());
			new DisponIFUtils.DownloadImageTask(holder.mProfilePictureView)
					.execute("http://graph.facebook.com/"
							+ av.getUser().getFacebookId()
							+ "/picture?type=large");
		} else {
			new DisponIFUtils.DownloadImageTask(holder.mProfilePictureView)
			.execute("http://www.theprprofessional.com/wp-content/uploads/2011/11/facebook-profile-picture.jpg");
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
