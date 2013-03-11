package com.sims2013.disponif.Utils;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;


public class DisponIFUtils {
	public static void makeToast(Context context, String text) {
		makeToast(context, text,Toast.LENGTH_LONG);
	}

	public static void makeToast(Context activity, String string,
			int lengthShort) {
		Toast.makeText(activity, string, lengthShort).show();
	}
	
	// Return String value of a key in JSONObject if it exists and if it's not null. 
	// Empty String else.
	public static String getJSONString(JSONObject obj, String key) throws JSONException {
		if (obj.has(key) && !obj.isNull(key)) {
			return obj.getString(key);
		} else {
			return "";
		}
	}
	
	// Return int value of a key in JSONObject if it exists and if it's not null. 
	// -1 else.
	public static int getJSONInt(JSONObject obj, String key) throws JSONException {
		if (obj.has(key) && !obj.isNull(key)) {
			return obj.getInt(key);
		} else {
			return -1;
		}
	}
	
	// Return float value of a key in JSONObject if it exists and if it's not null. 
	// -1f else.
	public static float getJSONFloat (JSONObject obj, String key) throws JSONException {
		if (obj.has(key) && !obj.isNull(key)) {
			return (float)obj.getDouble(key);
		} else {
			return -1f;
		}
	}
	
	// Convert a Calendar object to a String with the format "yyyy-MM-dd HH:mm:ss"
	public static String calendarToDatetime(Calendar calendar) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRANCE);
		return format.format(calendar.getTime());
	}

	// Convert a String with the format "yyyy-MM-dd HH:mm:ss" to a French date String
	// Exemple : "2013-02-20 12:10:30" will be convert to "20 f�vr 2013"
	public static String datetimeToFrDate(Context context, String timeToFormat) {
		String finalDateTime = "";          

	    SimpleDateFormat iso8601Format = new SimpleDateFormat(
	            "yyyy-MM-dd HH:mm:ss", Locale.FRANCE);

	    Date date = null;
	    if (timeToFormat != null) {
	        try {
	            date = iso8601Format.parse(timeToFormat);
	        } catch (ParseException e) {
	            date = null;
	        }

	        if (date != null) {
	            long when = date.getTime();
	            int flags = 0;
	            flags |= android.text.format.DateUtils.FORMAT_SHOW_DATE;
	            flags |= android.text.format.DateUtils.FORMAT_ABBREV_MONTH;
	            flags |= android.text.format.DateUtils.FORMAT_SHOW_YEAR;

	            finalDateTime = android.text.format.DateUtils.formatDateTime(context,
	            when + TimeZone.getDefault().getOffset(when), flags);               
	        }
	    }
	    return finalDateTime;
	}
	
	// Convert a String with the format "yyyy-MM-dd HH:mm:ss" to a French time String
	// Exemple : "2013-02-20 12:10:30" will be convert to "12:10"
	public static String datetimeToFrTime(Context context, String timeToFormat) {
		String finalDateTime = "";          

	    SimpleDateFormat iso8601Format = new SimpleDateFormat(
	            "yyyy-MM-dd HH:mm:ss", Locale.FRANCE);

	    Date date = null;
	    if (timeToFormat != null) {
	        try {
	            date = iso8601Format.parse(timeToFormat);
	        } catch (ParseException e) {
	            date = null;
	        }

	        if (date != null) {
	            long when = date.getTime();
	            int flags = 0;
	            flags |= android.text.format.DateUtils.FORMAT_SHOW_TIME;

	            finalDateTime = android.text.format.DateUtils.formatDateTime(context,
	            when + TimeZone.getDefault().getOffset(when), flags);               
	        }
	    }
	    return finalDateTime;
	}
	
	@SuppressLint("SimpleDateFormat")
	public static Date stringToDate(String sDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
			return formatter.parse(sDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
    }
	
	public static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	    ImageView bmImage;

	    public DownloadImageTask(ImageView bmImage) {
	        this.bmImage = bmImage;
	    }

	    protected Bitmap doInBackground(String... urls) {
	        String urldisplay = urls[0];
	        Bitmap mIcon11 = null;
	        try {
	            InputStream in = new java.net.URL(urldisplay).openStream();
	            mIcon11 = BitmapFactory.decodeStream(in);
	        } catch (Exception e) {
	            Log.e("Error", e.getMessage());
	            e.printStackTrace();
	        }
	        return mIcon11;
	    }

	    protected void onPostExecute(Bitmap result) {
	        this.bmImage.setBackgroundResource(0);
	        bmImage.setImageBitmap(result);
	    }
	}
}
