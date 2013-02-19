package com.sims2013.disponif.client;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.sims2013.disponif.R;
import com.sims2013.disponif.model.Category;

public class ClientTestActivity extends Activity implements Client.onReceiveListener {

	TextView mTv;
	Client mClient;
	
	String mToken;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client_test);
		
		mTv = (TextView)findViewById(R.id.textViewTest);
		mTv.setText("Connexion en cours ...");
		mClient = new Client("http://disponif.darkserver.fr/server/api.php");
		mClient.setListener(this);
		mClient.logIn("AAACEdEose0cBANZAgtJ4Kkl0ysdmPseomISP6TBGqHgjViV7ILNP92Q5KErR4tq5kMoRM7BHlNBecroOhqs0qdlEsz5InXvyBw3HC61ZCeZC7NmLFrq");
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
//		mTv.setText(token);
		if (token == Client.ERROR_STRING) {
			mTv.setText("Erreur de connexion !!");
		} else {
			mTv.setText("Connexion réussie. Récupération des catégories.");
			mToken = token;
			mClient.getAllCategories(mToken);
		}
	}

	@Override
	public void onAvailabilityAdded(Boolean result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCategoriesReceive(ArrayList<Category> categories) {
		mTv.setText(categories.get(0).toString());
//		if (categories == Client.ERROR_STRING) {
//			mTv.setText("Erreur lors de la récupération des catégories.");
//		} else {
//			mTv.setText(categories);
//		}
	}


}
