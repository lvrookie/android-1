package com.sims2013.disponif;

import android.app.Application;
import android.util.Log;

public class DisponifApplication extends Application {
	public static final boolean DEBUG_MODE = true;
	private static final String TAG = "com.sims2013.disponif.DisponifApplication";
	
	private static String mAccessToken;
	
	public static void setAccessToken(String token){
		if (DEBUG_MODE) {
			Log.i(TAG, "Token registered : " + token);
		}
		mAccessToken = token;
	}
	
	public static String getAccessToken(){
		return mAccessToken;
	}
}
