package com.sims2013.disponif.client;

import java.util.ArrayList;

import org.alexd.jsonrpc.JSONRPCClient;
import org.alexd.jsonrpc.JSONRPCParams.Versions;
import org.json.JSONArray;
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
		
		public static final String PARAM_TOKEN = "token";
		
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
	
	private class removeAvailability {
		public static final String METHOD = "removeAvailability";
		
		public static final String PARAM_TOKEN = "token";
		public static final String PARAM_ID = "id";
		
		public static final String RESULT_STATE = "state";
	}
	
	// Response listener interface
	public interface onReceiveListener {
		public void onPingReceive(String result);
		public void onLogInTokenReceive(String result);
		public void onAvailabilityAdded(int id);
		public void onCategoriesReceive(ArrayList<Category> categories);
		public void onUserAvailabilitiesReceive(ArrayList<Availability> availbilities);
		public void onUserAvailabilityRemoved();
		public void onNetworkError(String errorMessage);
		public void onTokenExpired();
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
	
	private void throwError(String errorMessage) {
		if (errorMessage.contains("\"code\":-32010") || errorMessage.contains("\"code\":-32000")){
			mListener.onTokenExpired();
		} else {
			mListener.onNetworkError(errorMessage);
		}
	}
	
	// Ping method
	public void ping() {		
		new AsyncTask<String, Void, String>(){

			String errorMessage;
			
			@Override
			protected String doInBackground(String... params) {
		        try {
		        	String res = mJsonClient.callString(ping.METHOD);
		        	Log.v("ClientJSON - ping", res);
		        	return res;
		        } catch (Exception e) {
		        	Log.v("ClientJSON - ping - error", e.getMessage());
		        	errorMessage = e.getMessage();
		            return ERROR_STRING;
		        }
			}

			@Override
			protected void onPostExecute(String result) {
				if (result == ERROR_STRING) {
					throwError(errorMessage);
				} else {
					mListener.onPingReceive(result);
				}
				super.onPostExecute(result);
			}
		}.execute();
	}
	
	//LogIn method
	public void logIn(final String token) {
		new AsyncTask<String, Void, String>(){

			String errorMessage;
			
			@Override
			protected String doInBackground(String... params) {
		        try {
		        	JSONObject JSObjet = new JSONObject();
		        	JSObjet.put(logIn.PARAM_TOKEN, token);
		        	JSONObject res = mJsonClient.callJSONObject(logIn.METHOD, JSObjet);
		        	Log.v("ClientJSON - logIn", res.toString());
		        	return res.getString(logIn.RESULT_TOKEN);
		        } catch (Exception e) {
		        	Log.v("ClientJSON - logIn - error", e.getMessage());
		        	errorMessage = e.getMessage();
		            return ERROR_STRING;
		        }
			}

			@Override
			protected void onPostExecute(String result) {
				if (result.equals(ERROR_STRING)) {
					throwError(errorMessage);
				} else {
					mListener.onLogInTokenReceive(result);
				}
				super.onPostExecute(result);
			}
		}.execute();
	}
	
	// Availability add method
	public void addAvailability(
			final String token, 
			final Availability availability) {
		new AsyncTask<String, Void, Integer>(){

			String errorMessage;
			
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
		        		errorMessage = "SERVER ISSUE"; 
		        		return -1;
		        	}
		        } catch (Exception e) {
		        	Log.v("ClientJSON - addAvailability - error", e.getMessage());
		        	errorMessage = e.getMessage();
		            return -1;
		        } 
			}

			@Override
			protected void onPostExecute(Integer result) {
				if (result == -1) {
					throwError(errorMessage);
				} else {
					mListener.onAvailabilityAdded(result);
				}
				super.onPostExecute(result);
			}
		}.execute();
	}
	
	// Get categories method
	public void getAllCategories(final String token) {
		new AsyncTask<String, Void, ArrayList<Category>>(){

			String errorMessage;
			
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
		        } catch (Exception e) {
		        	Log.v("ClientJSON - getCategories - error", e.getMessage());
		        	errorMessage = e.getMessage();
		            return null;
		        } 
			}
			
			@Override
			protected void onPostExecute(ArrayList<Category> result) {
				if (result == null) {
					throwError(errorMessage);
				} else {
					mListener.onCategoriesReceive(result);
				}
				super.onPostExecute(result);
			}
		}.execute();
	}
	
	// Get availabilities by user
	public void getUserAvailabilities(final String token) {
		new AsyncTask<String, Void, ArrayList<Availability>>(){

			String errorMessage;
			
			@Override
			protected ArrayList<Availability> doInBackground(String... params) {
		        try {
		        	JSONObject JSObjet = new JSONObject();
		        	JSObjet.put(getUserAvailabilities.PARAM_TOKEN, token);
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
		        } catch (Exception e) {
		        	Log.v("ClientJSON - getUserAvailabilities", e.getMessage());
		        	errorMessage = e.getMessage();
		            return null;
		        } 
			}

			@Override
			protected void onPostExecute(ArrayList<Availability> result) {
				if (result == null) {
					throwError(errorMessage);
				}else {
					mListener.onUserAvailabilitiesReceive(result);
				}
				super.onPostExecute(result);
			}
		}.execute();
	}
	
	// Remove availability method
	public void removeAvailability(final String token, final Availability availability) {
		new AsyncTask<String, Void, Boolean>(){

			String errorMessage;
			
			@Override
			protected Boolean doInBackground(String... params) {
		        try {
		        	JSONObject JSObjet = new JSONObject();
		        	JSObjet.put(removeAvailability.PARAM_TOKEN, token);
		        	JSObjet.put(removeAvailability.PARAM_ID, availability.getId());
		        	JSONObject res = mJsonClient.callJSONObject(removeAvailability.METHOD, JSObjet);
		        	Log.v("ClientJSON - removeAvailability", res.toString());
		        	Boolean state = res.getBoolean(removeAvailability.RESULT_STATE);
		        	if (!state) {
		        		errorMessage = "REMOVED_FAILED";
		        	}
		        	return state;
		        } catch (Exception e) {
		        	Log.v("ClientJSON - removeAvailability - error", e.getMessage());
		        	errorMessage = e.getMessage();
		            return false;
		        }
			}

			@Override
			protected void onPostExecute(Boolean result) {
				if (!result) {
					throwError(errorMessage);
				} else {
					mListener.onUserAvailabilityRemoved();
				}
				super.onPostExecute(result);
			}
		}.execute();
	}
	
	// Get match availabilities
	public void getMatchAvailabilities(final Availability availability) {
		
	}
}
