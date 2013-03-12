package com.sims2013.disponif.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sims2013.disponif.R;
import com.sims2013.disponif.Utils.BitmapManager;
import com.sims2013.disponif.model.Comment;

public class CommentAdapter extends ArrayAdapter<Comment> {

	ArrayList<Comment> mCommentList;
	Activity mContext;
	int mLayout;
	
	public CommentAdapter(Activity context, int resource,
			int textViewResourceId, List<Comment> objects) {
		super(context, resource, textViewResourceId, objects);
		mLayout = resource;
		mContext = context;
		mCommentList = (ArrayList<Comment>) objects;
	}
	
	private static class CommentHolder{
		ImageView mUserProfilePicture;
		TextView mComment;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		CommentHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = mContext.getLayoutInflater();
			convertView = inflater.inflate(mLayout, parent, false);
			holder = new CommentHolder();
			holder.mUserProfilePicture = (ImageView)convertView.findViewById(R.id.comment_profile_picture);
			holder.mComment = (TextView) convertView.findViewById(R.id.comment_text);
			convertView.setTag(holder);
		} else {
			holder = (CommentHolder) convertView.getTag();
		}
		
		Comment comment = mCommentList.get(position);
		holder.mComment.setText(comment.getMessage());
		BitmapManager.setBitmap(holder.mUserProfilePicture, "http://graph.facebook.com/"
				+ comment.getUser().getFacebookId() + "/picture?type=", R.drawable.bkg_white_gray_border);
		return convertView;
	}
	
	@Override
	public boolean isEnabled(int position) {
		return false;
	}
}
