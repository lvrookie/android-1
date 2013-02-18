package com.sims2013.disponif.client;

import org.alexd.jsonrpc.JSONRPCClient;
import org.alexd.jsonrpc.JSONRPCException;
import org.alexd.jsonrpc.JSONRPCThreadedClient;
import org.alexd.jsonrpc.JSONRPCParams.Versions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class Client {
	
	// Error constants
	public static final String ERROR_STRING = "error";
	public static final String ERROR_JSON_STRING = "jsonError";
	
	// Method constants
	private static final String PING_METHOD = "ping";
	private static final String LOGIN_METHOD = "userLogin";
	
	// Param constants
	private static final String ACCESS_TOKEN_PARAM = "accessToken";
	
	// Result constants
	private static final String TOKEN_RESULT = "token";
	
	// Json client object
	private JSONRPCClient mJsonClient;
	
	// Receive listener
	private onReceiveListener mListener;
	
	// Response listener interface
	public interface onReceiveListener {
		public void onPingReceive(String result);
		public void onLogInTokenReceive(String token);
	}
	
	// API Path
	//	private static String HOST = "http://disponif.darkserver.fr/server/api.php";
	
	// Public constructor
	public Client(String host) {
		mJsonClient = JSONRPCClient.create(host, Versions.VERSION_2);
		mJsonClient.setConnectionTimeout(2000);
		mJsonClient.setSoTimeout(2000);
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
}
