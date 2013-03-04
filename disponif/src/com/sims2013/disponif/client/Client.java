package com.sims2013.disponif.client;

import java.util.ArrayList;

import org.alexd.jsonrpc.JSONRPCClient;
import org.alexd.jsonrpc.JSONRPCException;
import org.alexd.jsonrpc.JSONRPCParams.Versions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.sims2013.disponif.Utils.DisponIFUtils;
import com.sims2013.disponif.model.Availability;
import com.sims2013.disponif.model.Category;
import com.sims2013.disponif.model.Type;

public class Client {
	
	// Error constants
	public static final String ERROR_STRING = "error";
	public static final String ERROR_JSON_STRING = "jsonError";
	
	// Json client object
	private JSONRPCClient mJsonClient;
	
	// Receive listener
	private onReceiveListener mListener;
	
	// Namespace for ping method
	private class ping {
		// Method name
		public static final String METHOD = "ping";
	}
	
	// Namespace for logIn method
	private class logIn {
		// Method name
		public static final String METHOD = "userLogin";
		// Parameter keys
		public static final String PARAM_TOKEN = "accessToken";
		// Result keys
		public static final String RESULT_TOKEN = "token";
	}

	// Namespace for addAvailability method
	private class addAvailability {
		// Method name
		public static final String METHOD = "saveAvailability";
		// Parameter keys
		public static final String PARAM_TOKEN = "token";
		public static final String PARAM_CATEGORY_ID = "category_id";
//		public static final String PARAM_TYPE_ID = "type_id";
		public static final String PARAM_START_TIME = "startTime";
		public static final String PARAM_END_TIME = "endTime";
		public static final String PARAM_DESCRIPTION = "description";
//		public static final String PARAM_MAX_PARTICIPANT = "maxParticipant";
//		public static final String PARAM_LATITUDE = "latitude";
//		public static final String PARAM_LONGITUDE = "longitude";
//		public static final String PARAM_RADIUS = "radius";
//		public static final String PARAM_PRIVACY = "privacy";
		// Result keys
		public static final String RESULT_STATE = "state";
		public static final String RESULT_ID = "id";
	}
	
	// Namespace for getCategories method
	private class getCategories {
		// Method name
		public static final String METHOD = "getAllCategories";
		// Parameter keys
		public static final String PARAM_TOKEN = "token";
		// Result keys
		public static final String RESULT_CATEGORIES = "categories";
		public static final String RESULT_CATEGORY_ID = "id";
		public static final String RESULT_CATEGORY_RADIUS = "defaultRadius";
		public static final String RESULT_CATEGORY_NAME = "name";
		public static final String RESULT_TYPES = "@type";
		public static final String RESULT_TYPE_ID = "id";
		public static final String RESULT_TYPE_NAME = "name";
		
	}
	
	private class getUserAvailabilities {
		public static final String METHOD = "getAvailabilities";
		
//		public static final String PARAM_TOKEN = "token";
		
		public static final String RESULT_AVAILABILITIES = "availabilities";
		
		public static final String RESULT_ID = "id";
		public static final String RESULT_CATEGORY_ID = "category_id";
		public static final String RESULT_TYPE_ID = "type_id";
		public static final String RESULT_START_TIME = "startTime";
		public static final String RESULT_END_TIME = "endTime";
		public static final String RESULT_OPTION = "option";
		public static final String RESULT_DESCRIPTION = "description";
		public static final String RESULT_MAX_PARTICIPANT = "maxParticipant";
		public static final String RESULT_LATITUDE = "latitude";
		public static final String RESULT_LONGITUDE = "longitude";
		public static final String RESULT_RADIUS = "radius";
		public static final String RESULT_PRIVACY = "privacy";
		
	}
	
	// Response listener interface
	public interface onReceiveListener {
		public void onPingReceive(String result);
		public void onLogInTokenReceive(String result);
		public void onAvailabilityAdded(int id);
		public void onCategoriesReceive(ArrayList<Category> categories);
		public void onUserAvailabilitiesReceive(ArrayList<Availability> availbilities);
	}
	
	// API Path
	// private static String HOST = "http://disponif.darkserver.fr/server/api.php";
	
	// Public constructor
	public Client(String host) {
		mJsonClient = JSONRPCClient.create(host, Versions.VERSION_2);
		mJsonClient.setConnectionTimeout(20000);
		mJsonClient.setSoTimeout(20000);
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
		        	String res = mJsonClient.callString(ping.METHOD);
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
		        	JSObjet.put(logIn.PARAM_TOKEN, token);
		        	JSONObject res = mJsonClient.callJSONObject(logIn.METHOD, JSObjet);
		        	Log.v("ClientJSON - getCategories", res.toString());
		        	return res.getString(logIn.RESULT_TOKEN);
		        } catch (JSONRPCException e) {
		        	Log.v("ClientJSON - logIn", e.getMessage());
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
			final Availability availability) {
		new AsyncTask<String, Void, Integer>(){

			@Override
			protected Integer doInBackground(String... params) {
		        try {
		        	JSONObject JSObjet = new JSONObject();
		        	JSObjet.put(addAvailability.PARAM_TOKEN, token);
		        	JSObjet.put(addAvailability.PARAM_CATEGORY_ID, availability.getCategoryId());
//		        	JSObjet.put(addAvailability.PARAM_TYPE_ID, availability.getTypeId());
		        	JSObjet.put(addAvailability.PARAM_START_TIME, availability.getStartTime());
		        	JSObjet.put(addAvailability.PARAM_END_TIME, availability.getEndTime());
		        	JSObjet.put(addAvailability.PARAM_DESCRIPTION, availability.getDescription());
//		        	JSObjet.put(addAvailability.PARAM_MAX_PARTICIPANT, availability.getMaxParticipant());
//		        	JSObjet.put(addAvailability.PARAM_LATITUDE, availability.getLatitude());
//		        	JSObjet.put(addAvailability.PARAM_LONGITUDE, availability.getLongitude());
//		        	JSObjet.put(addAvailability.PARAM_RADIUS, availability.getRadius());
//		        	JSObjet.put(addAvailability.PARAM_PRIVACY, availability.getPrivacy());
		        	JSONObject res = mJsonClient.callJSONObject(addAvailability.METHOD, JSObjet);
		        	Log.v("ClientJSON - addAvailability", res.toString());
		        	Boolean state = res.getBoolean(addAvailability.RESULT_STATE);
		        	if (state) {
		        		return res.getInt(addAvailability.RESULT_ID);
		        	} else {
		        		return -1;
		        	}
		        } catch (JSONRPCException e) {
		        	Log.v("ClientJSON - addAvailability", e.getMessage());
		            return -1;
		        } catch (JSONException e) {
		        	return -1;
		        }
			}

			@Override
			protected void onPostExecute(Integer result) {
				mListener.onAvailabilityAdded(result);
				super.onPostExecute(result);
			}
		}.execute();
	}
	
	// Get categories method
	public void getAllCategories(final String token) {
		new AsyncTask<String, Void, ArrayList<Category>>(){

			@Override
			protected ArrayList<Category> doInBackground(String... params) {
		        try {
		        	JSONObject JSObjet = new JSONObject();
		        	JSObjet.put(getCategories.PARAM_TOKEN, token);
		        	JSONObject res = mJsonClient.callJSONObject(getCategories.METHOD, JSObjet);
		        	Log.v("ClientJSON - getCategories", res.toString());
		        	JSONArray cats = res.getJSONArray(getCategories.RESULT_CATEGORIES);
		        	ArrayList<Category> catsArray = new ArrayList<Category>();
		        	for (int i = 0; i < cats.length(); ++ i) {
		        		Category cat = new Category();
		        		JSONObject catJSON = cats.getJSONObject(i);
		        		cat.setId(catJSON.getInt(getCategories.RESULT_CATEGORY_ID));
		        		cat.setRadius(catJSON.getInt(getCategories.RESULT_CATEGORY_RADIUS));
		        		cat.setName(catJSON.getString(getCategories.RESULT_CATEGORY_NAME));
		        		JSONArray typesArray = catJSON.getJSONArray(getCategories.RESULT_TYPES);
		        		for (int j = 0; j < typesArray.length(); ++ j) {
		        			Type type = new Type();
		        			JSONObject typeObject = typesArray.getJSONObject(j);
		        			type.setId(typeObject.getInt(getCategories.RESULT_TYPE_ID));
		        			type.setName(typeObject.getString(getCategories.RESULT_TYPE_NAME));
		        			cat.addType(type);
		        		}
		        		catsArray.add(cat);
		        	}
		        	return catsArray;
		        } catch (JSONRPCException e) {
		        	Log.v("ClientJSON - getCategories", e.getMessage());
		            return null;
		        } catch (JSONException e) {
		        	return null;
		        }
			}
			
			@Override
			protected void onPostExecute(ArrayList<Category> result) {
				mListener.onCategoriesReceive(result);
				super.onPostExecute(result);
			}
		}.execute();
	}
	
	// Get availabilities by user
	public void getUserAvailabilities(final String token) {
		new AsyncTask<String, Void, ArrayList<Availability>>(){

			@Override
			protected ArrayList<Availability> doInBackground(String... params) {
		        try {
		        	JSONObject JSObjet = new JSONObject();
		        	JSObjet.put(getCategories.PARAM_TOKEN, token);
		        	JSONObject res = mJsonClient.callJSONObject(getUserAvailabilities.METHOD, JSObjet);
		        	Log.v("ClientJSON - getUserAvailabilities", res.toString());
		        	ArrayList<Availability> availabilitiesList = new ArrayList<Availability>();
		        	JSONArray availailitiesArray = res.getJSONArray(getUserAvailabilities.RESULT_AVAILABILITIES);
		        	for (int i = 0; i < availailitiesArray.length(); ++ i) {
		        		JSONObject availabilityObject = availailitiesArray.getJSONObject(i);
		        		Availability availability = new Availability();
		        		availability.setId(DisponIFUtils.getJSONInt(availabilityObject, getUserAvailabilities.RESULT_ID));
		        		availability.setCategoryId(DisponIFUtils.getJSONInt(availabilityObject, getUserAvailabilities.RESULT_CATEGORY_ID));
		        		availability.setTypeId(DisponIFUtils.getJSONInt(availabilityObject, getUserAvailabilities.RESULT_TYPE_ID));
		        		availability.setStartTime(DisponIFUtils.getJSONString(availabilityObject, getUserAvailabilities.RESULT_START_TIME));
		        		availability.setEndTime(DisponIFUtils.getJSONString(availabilityObject, getUserAvailabilities.RESULT_END_TIME));
		        		availability.setMaxParticipant(DisponIFUtils.getJSONInt(availabilityObject, getUserAvailabilities.RESULT_MAX_PARTICIPANT));
		        		availability.setLatitude(DisponIFUtils.getJSONFloat(availabilityObject, getUserAvailabilities.RESULT_LATITUDE));
		        		availability.setLongitude(DisponIFUtils.getJSONFloat(availabilityObject, getUserAvailabilities.RESULT_LONGITUDE));
		        		availability.setRadius(DisponIFUtils.getJSONInt(availabilityObject, getUserAvailabilities.RESULT_RADIUS));
		        		availability.setPrivacy(DisponIFUtils.getJSONInt(availabilityObject, getUserAvailabilities.RESULT_PRIVACY));
		        		
		        		JSONObject option = availabilityObject.getJSONObject(getUserAvailabilities.RESULT_OPTION);
		        		availability.setDescription(DisponIFUtils.getJSONString(option, getUserAvailabilities.RESULT_DESCRIPTION));
		        		
		        		availabilitiesList.add(availability);
		        	}    	
		        	return availabilitiesList;
		        } catch (JSONRPCException e) {
		        	Log.v("ClientJSON - getUserAvailabilities", e.getMessage());
		            return null;
		        } catch (JSONException e) {
		        	Log.v("ClientJSON - getUserAvailabilities", e.getMessage());
		        	return null;
		        }
			}

			@Override
			protected void onPostExecute(ArrayList<Availability> result) {
				mListener.onUserAvailabilitiesReceive(result);
				super.onPostExecute(result);
			}
		}.execute();
	}
}
