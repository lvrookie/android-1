package com.sims2013.disponif.client;

import java.util.ArrayList;

import org.alexd.jsonrpc.JSONRPCClient;
import org.alexd.jsonrpc.JSONRPCException;
import org.alexd.jsonrpc.JSONRPCParams.Versions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class Client {
	
	// Error constants
	public static final String ERROR_STRING = "error";
	public static final String ERROR_JSON_STRING = "jsonError";
	
	// Method names
	private static final String PING_METHOD = "ping";
	private static final String LOGIN_METHOD = "userLogin";
	private static final String ADD_AVAILABILITY_METHOD = "availabilityAdd";
	private static final String GET_CATEGORIES = "getAllCategories";
	
	// Param keys
	private static final String ACCESS_TOKEN_PARAM = "accessToken";
	
	private static final String TOKEN_PARAM = "token";
	private static final String CATEGORY_PARAM = "category";
	private static final String START_TIME_PARAM = "startTime";
	private static final String END_TIME_PARAM = "endTime";
	private static final String ADDRESS_PARAM = "address";
	
	// Result keys
	private static final String TOKEN_RESULT = "token";
	private static final String STATE_RESULT = "state";
	private static final String CATEGORIES_RESULT = "categories";
	
	// Json client object
	private JSONRPCClient mJsonClient;
	
	// Receive listener
	private onReceiveListener mListener;
	
	// Response listener interface
	public interface onReceiveListener {
		public void onPingReceive(String result);
		public void onLogInTokenReceive(String result);
		public void onAvailabilityAdded(Boolean result);
//		public void onCategoriesReceive(ArrayList<String> categories);
		public void onCategoriesReceive(String categories);
	}
	
	// API Path
	//	private static String HOST = "http://disponif.darkserver.fr/server/api.php";
	
	// Public constructor
	public Client(String host) {
		mJsonClient = JSONRPCClient.create(host, Versions.VERSION_2);
		mJsonClient.setConnectionTimeout(10000);
		mJsonClient.setSoTimeout(10000);
	}
	
	// Listener setter
	public void setListener(onReceiveListener listener) {
		this.mListener = listener;
	}
	
	// Ping method
	public void ping() {		
		new AsyncTask<String, Void, String>(){

			@Override
			protected String doInBackground(String... params) {
		        try {
		        	String res = mJsonClient.callString(PING_METHOD);
		        	return res;
		        } catch (JSONRPCException e) {
		            return ERROR_STRING;
		        }
			}

			@Override
			protected void onPostExecute(String result) {
				mListener.onPingReceive(result);
				super.onPostExecute(result);
			}
		}.execute();
	}
	
	//LogIn method
	public void logIn(final String token) {
		new AsyncTask<String, Void, String>(){

			@Override
			protected String doInBackground(String... params) {
		        try {
		        	JSONObject JSObjet = new JSONObject();
		        	JSObjet.put(ACCESS_TOKEN_PARAM, token);
		        	JSONObject res = mJsonClient.callJSONObject(LOGIN_METHOD, JSObjet);
		        	return res.getString(TOKEN_RESULT);
		        } catch (JSONRPCException e) {
		            return ERROR_STRING;
		        } catch (JSONException e) {
		        	return ERROR_JSON_STRING;
		        }
			}

			@Override
			protected void onPostExecute(String result) {
				mListener.onLogInTokenReceive(result);
				super.onPostExecute(result);
			}
		}.execute();
	}
	
	// Availability add method
	public void addAvailability(
			final String token, 
			final int category, 
			final String startTime, 
			final String endTime,
			final String address) {
		new AsyncTask<String, Void, Boolean>(){

			@Override
			protected Boolean doInBackground(String... params) {
		        try {
		        	JSONObject JSObjet = new JSONObject();
		        	JSObjet.put(TOKEN_PARAM, token);
		        	JSObjet.put(CATEGORY_PARAM, category);
		        	JSObjet.put(START_TIME_PARAM, startTime);
		        	JSObjet.put(END_TIME_PARAM, endTime);
		        	JSObjet.put(ADDRESS_PARAM, address);
		        	JSONObject res = mJsonClient.callJSONObject(ADD_AVAILABILITY_METHOD, JSObjet);
		        	return res.getBoolean(STATE_RESULT);
		        } catch (JSONRPCException e) {
		            return false;
		        } catch (JSONException e) {
		        	return false;
		        }
			}

			@Override
			protected void onPostExecute(Boolean result) {
				mListener.onAvailabilityAdded(result);
				super.onPostExecute(result);
			}
		}.execute();
	}
	
	// Get categories method
	public void getCategories(final String token) {
		new AsyncTask<String, Void, ArrayList<String>>(){

			@Override
			protected ArrayList<String> doInBackground(String... params) {
		        try {
		        	JSONObject JSObjet = new JSONObject();
		        	JSObjet.put(TOKEN_PARAM, token);
		        	JSONObject res = mJsonClient.callJSONObject(GET_CATEGORIES, JSObjet);
		        	JSONArray cats = res.getJSONArray(CATEGORIES_RESULT);
		        	ArrayList<String> catsArray = new ArrayList<String>();
		        	if (cats.length() > 0) {
		        		for (int i = 0; i < cats.length(); ++i) {
		        			catsArray.add(cats.getString(i));
		        		}
		        	}
		        	return catsArray;
		        } catch (JSONRPCException e) {
		            return null;
		        } catch (JSONException e) {
		        	return null;
		        }
			}

			@Override
			protected void onPostExecute(ArrayList<String> result) {
//				mListener.onCategoriesReceive(result);
				super.onPostExecute(result);
			}
		}.execute();
	}
	
	// Get categories method
		public void getAllCategories(final String token) {
			new AsyncTask<String, Void, String>(){

				@Override
				protected String doInBackground(String... params) {
			        try {
			        	JSONObject JSObjet = new JSONObject();
			        	JSObjet.put(TOKEN_PARAM, token);
			        	JSONObject res = mJsonClient.callJSONObject(GET_CATEGORIES, JSObjet);
//			        	JSONArray cats = res.getJSONArray(CATEGORIES_RESULT);
//			        	ArrayList<String> catsArray = new ArrayList<String>();
//			        	if (cats.length() > 0) {
//			        		for (int i = 0; i < cats.length(); ++i) {
//			        			catsArray.add(cats.getString(i));
//			        		}
//			        	}
			        	return res.toString();
			        } catch (JSONRPCException e) {
			            return e.getMessage();
			        } catch (JSONException e) {
			        	return ERROR_STRING;
			        }
				}

				@Override
				protected void onPostExecute(String result) {
					mListener.onCategoriesReceive(result);
					super.onPostExecute(result);
				}
			}.execute();
		}
}
