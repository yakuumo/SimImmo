package com.xdk.simimmo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.xdk.simimmo.model.Calculs;
import com.xdk.simimmo.model.Pret;

public class Capital extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setRequestedOrientation(
        		ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_capital);
		// Show the Up button in the action bar.
		setupActionBar();
		
		final ImageButton button = (ImageButton) findViewById(R.id.okcap);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	EditText montant = (EditText) findViewById(R.id.mens);
            	double mensualites = Calculs.parseDouble(montant.getText().toString());
            	EditText duree = (EditText) findViewById(R.id.dureecap);
        		long time = Calculs.parseLong(duree.getText().toString());
        		RadioButton annees = (RadioButton) findViewById(R.id.anneescap);
        		if(annees.isChecked()){
        			time *= 12;
        		} 
        		EditText taux = (EditText) findViewById(R.id.tauxcap);
        		double t = Calculs.parseDouble(taux.getText().toString().replace(",", "."))/100;
        		EditText ass = (EditText) findViewById(R.id.asscap);
        		double a = Calculs.parseDouble(ass.getText().toString().replace(",", "."))/100;
        		double capital = Calculs.calculCapital(mensualites,t,time,a);
        		Mensualites.currentPret = new Pret(capital,t,time,a);
            	Intent i = new Intent(getApplicationContext(),DisplayMensualites.class);
                startActivity(i);
            }
        });
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.capital, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
