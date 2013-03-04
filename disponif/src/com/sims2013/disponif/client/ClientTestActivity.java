package com.sims2013.disponif.client;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.sims2013.disponif.R;
import com.sims2013.disponif.Utils.DisponIFUtils;
import com.sims2013.disponif.model.Availability;
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
		mClient.logIn("AAAEVFBxgKkMBAODUlZA6xYeMzHkOd7rGQ6r1kvXFSmD92I9OfSlS5BCVpr3eLNx8HWjB0RHIORUzm9UNi7irTElraZASR24HPPbGbIUJxpJ46N6GDR");
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
		if (token == Client.ERROR_STRING) {
			mTv.setText("Erreur de connexion !!");
		} else {
			mToken = token;
			mTv.setText("Connexion réussie");
//			Availability a = new Availability();
//			a.setCategoryId(1);
//			a.setTypeId(1);
//			a.setStartTime("2013-02-21 14:30:00");
//			a.setEndTime("2013-02-21 16:30:00");
//			a.setDescription("Hey les gars ! J'suis dispo !");
//			mClient.addAvailability(mToken, a);
			
			mTv.setText("Connexion réussie. Récupération des dispos");
			mClient.getUserAvailabilities(mToken);
		}
	}

	@Override
	public void onAvailabilityAdded(int id) {
		if (id != -1) {
			mTv.setText("Dispo ajoutée !");
		} else {
			mTv.setText("Erreur lors de l'ajout de la dispo !!");
		}
	}

	@Override
	public void onCategoriesReceive(ArrayList<Category> categories) {
		mTv.setText(categories.toString());
	}

	@Override
	public void onUserAvailabilitiesReceive(
			ArrayList<Availability> availbilities) {
		if (availbilities != null) {
			mTv.setText(availbilities.toString());
		} else {
			mTv.setText("Erreur lors de la récupération des dispos !!");
		}
	}

	@Override
	public void onNetworkError(String errorMessage) {
		mTv.setText(errorMessage);
	}

	@Override
	public void onTokenExpired() {
		mTv.setText("token expired");
	}


}
