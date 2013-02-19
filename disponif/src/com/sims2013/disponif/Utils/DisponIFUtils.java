package com.sims2013.disponif.Utils;

import android.content.Context;
import android.widget.Toast;


public class DisponIFUtils {
	public static void makeToast(Context context, String text) {
		makeToast(context, text,Toast.LENGTH_LONG);
	}

	public static void makeToast(Context activity, String string,
			int lengthShort) {
		Toast.makeText(activity, string, lengthShort).show();
	}
}
