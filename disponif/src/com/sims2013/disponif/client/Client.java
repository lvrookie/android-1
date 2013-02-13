package com.sims2013.disponif.client;

import org.alexd.jsonrpc.JSONRPCClient;
import org.alexd.jsonrpc.JSONRPCException;
import org.alexd.jsonrpc.JSONRPCThreadedClient;
import org.alexd.jsonrpc.JSONRPCParams.Versions;

import android.os.AsyncTask;

public class Client {
	
	// API Path
	private static String HOST = "http://disponif.darkserver.fr/server/api.php";
	
	// Response listener interface
	public interface onStringReceiveListener {
		public void onPingReceive(String result);
	}
	
	// Ping method
	public static void ping(final Client.onStringReceiveListener listener) {
		final String method = "ping";
		
		new AsyncTask<String, Void, String>(){

			@Override
			protected String doInBackground(String... params) {
				JSONRPCClient client = JSONRPCThreadedClient.create(HOST, Versions.VERSION_2);
				client.setConnectionTimeout(2000);
		        client.setSoTimeout(2000);
		        try {
		        	String res = client.callString(method);
		        	return res;
		        } catch (JSONRPCException e) {
		            return "error";
		        }
			}

			@Override
			protected void onPostExecute(String result) {
				listener.onPingReceive(result);
				super.onPostExecute(result);
			}
		}.execute();
		
	}

}
