package com.sims2013.disponif;

import android.app.Application;
import android.util.Log;

public class DisponifApplication extends Application {
	public static final boolean DEBUG_MODE = true;
	public static final String DISPONIF_SERVER = "http://disponif.darkserver.fr/server/api.php";
	private static final String TAG = "com.sims2013.disponif.DisponifApplication";

	private static String mAccessToken;
	private static String mFacebookId;
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	public static void setAccessToken(String token){
		if (DEBUG_MODE) {
			Log.i(TAG, "Token registered : " + token);
		}
		mAccessToken = token;
	}
	
	public static String getAccessToken(){
		return mAccessToken;
	}
	
	public static String getFacebookId(){
		return mFacebookId;
	}
	
	public static void setFacebookId(String id){
		mFacebookId = id;
	}
}
