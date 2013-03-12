package com.sims2013.disponif.Utils;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

public class BitmapManager {
	
	private static LruCache<String, Bitmap> mMemoryCache = null;
	
	public static void setBitmap(final ImageView imgView, final String URL) {
		
		initMemoryCache();
		
		if (getBitmapFromMemCache(URL) == null) {
			new BitmapWorkerTask(imgView).execute(URL);
		} else {
			imgView.setBackgroundResource(0);
			imgView.setImageBitmap(getBitmapFromMemCache(URL));
		}
	}
	
	public static void setBitmap(final ImageView imgView, final String URL, int background) {
		
		initMemoryCache();
		
		if (getBitmapFromMemCache(URL) == null) {
			new BitmapWorkerTask(imgView, background).execute(URL);
		} else {
			imgView.setBackgroundResource(background);
			imgView.setImageBitmap(getBitmapFromMemCache(URL));
		}
	}
	
	public static void cacheBitmap(final String URL) {
		
		initMemoryCache();
		
		if (getBitmapFromMemCache(URL) == null) {
			new BitmapWorkerTask(null).execute(URL);
		}
	}   
	
	private static void initMemoryCache() {
		if (mMemoryCache == null) {
			final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		    final int cacheSize = maxMemory / 8;
		    mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
		        @Override
		        protected int sizeOf(String key, Bitmap bitmap) {
		            return bitmap.getByteCount() / 1024;
		        }
		    };
		}
	}
	
	private static void addBitmapToMemoryCache(String key, Bitmap bmp) {
		if (getBitmapFromMemCache(key) == null) {
	        mMemoryCache.put(key, bmp);
	    }
	}
	
	private static Bitmap getBitmapFromMemCache(String key) {
	    return mMemoryCache.get(key);
	}
	
	private static class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
	    ImageView bmImage;
	    int ressourceId;

	    public BitmapWorkerTask(ImageView bmImage) {
	        this.bmImage = bmImage;
	        this.ressourceId = 0;
	    }
	    
	    public BitmapWorkerTask(ImageView bmImage, int ressource) {
	        this.bmImage = bmImage;
	        this.ressourceId = ressource;
	    }

	    protected Bitmap doInBackground(String... urls) {
	        String urldisplay = urls[0];
	        Bitmap mIcon11 = null;
	        try {
	        	if (getBitmapFromMemCache(urldisplay) == null) {
		            InputStream in = new java.net.URL(urldisplay).openStream();
		            mIcon11 = BitmapFactory.decodeStream(in);
		            addBitmapToMemoryCache(urldisplay, mIcon11);
	        	} else {
	        		mIcon11 = getBitmapFromMemCache(urldisplay);
	        	}
	        } catch (Exception e) {
	        	if (e.getMessage() != null) {
		            Log.e("Error", e.getMessage());
				}
	            e.printStackTrace();
	        }
	        return mIcon11;
	    }

	    protected void onPostExecute(Bitmap result) {
	    	if (bmImage != null) {
	    		bmImage.setBackgroundResource(ressourceId);
		        bmImage.setImageBitmap(result);
	    	}
	    }
	}
}
