package com.sims2013.disponif.Utils;

import org.json.JSONException;
import org.json.JSONObject;

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
	
	public static String getJSONString(JSONObject obj, String key) throws JSONException {
		if (obj.has(key) && !obj.isNull(key)) {
			return obj.getString(key);
		} else {
			return "";
		}
	}
	
	public static int getJSONInt(JSONObject obj, String key) throws JSONException {
		if (obj.has(key) && !obj.isNull(key)) {
			return obj.getInt(key);
		} else {
			return -1;
		}
	}
	
	public static float getJSONFloat (JSONObject obj, String key) throws JSONException {
		if (obj.has(key) && !obj.isNull(key)) {
			return (float)obj.getDouble(key);
		} else {
			return -1f;
		}
	}
}
