package com.sims2013.disponif.client;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.sims2013.disponif.R;

public class ClientTestActivity extends Activity implements Client.onReceiveListener {

	TextView mTv;
	Client mClient;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client_test);
		
		mTv = (TextView)findViewById(R.id.textViewTest);
		mClient = new Client("http://disponif.darkserver.fr/server/api.php");
		mClient.setListener(this);
		mClient.logIn("AAACEdEose0cBAH9YPT0WHMhum7cYV8mrmFq5nhFmu1DmyZCQKVKjLwNvV0YwmLVlRNlXSxOZBthlBUsoStqM9DEKJDpc3qRR1orwBIFK0cOaw8jRrU");
//		mClient.ping();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_client_test, menu);
		return true;
	}

	@Override
	public void onPingReceive(String result) {
		mTv.setText(result);
	}

	@Override
	public void onLogInTokenReceive(String token) {
		mTv.setText(token);
		
	}

	@Override
	public void onAvailabilityAdded(Boolean result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCategoriesReceive(ArrayList<String> categories) {
		// TODO Auto-generated method stub
		
	}

}
