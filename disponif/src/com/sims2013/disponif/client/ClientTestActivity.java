package com.sims2013.disponif.client;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.sims2013.disponif.R;
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
		mClient.logIn("AAAEVFBxgKkMBALvAq5WJDaoQgBUPknhpugZCFRbQFO4Ew894ComEMVXrIcZB1Pr26wlBpL400p5lIbi4OKdDioGi9tKEY49iTXgbgUYZBOmfZB9ilXki");
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
//			mTv.setText("Connexion réussie. Envoie d'une dispo");
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
//		mTv.setText(categories.get(0).toString());
//		if (categories == Client.ERROR_STRING) {
//			mTv.setText("Erreur lors de la récupération des catégories.");
//		} else {
//			mTv.setText(categories);
//		}
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


}
